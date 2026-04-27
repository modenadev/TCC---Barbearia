<template>
  <section class="section">
    <div class="container">
      <h1 class="section-title">Minha área</h1>
      <p class="section-subtitle">Veja seus agendamentos e gerencie seus horários.</p>

      <div v-if="loading">Carregando agendamentos...</div>

      <div v-else-if="agendamentos.length === 0" class="card empty-card">
        <p>Você ainda não possui agendamentos.</p>
      </div>

      <div v-else class="agendamentos-grid">
        <div
          v-for="agendamento in agendamentos"
          :key="agendamento.id"
          class="card agendamento-card"
        >
          <h3>{{ agendamento.servico.nome }}</h3>

          <p>
            <strong>Data:</strong>
            {{ formatarData(agendamento.dataHoraInicio) }}
          </p>

          <p>
            <strong>Horário:</strong>
            {{ formatarHora(agendamento.dataHoraInicio) }}
          </p>

          <p>
            <strong>Status:</strong>
            {{ agendamento.status }}
          </p>

          <div class="actions">
            <button
              class="btn btn-outline"
              @click="abrirRemarcar(agendamento)"
              :disabled="agendamento.status === 'CANCELADO'"
            >
              Alterar horário
            </button>

            <button
              class="btn btn-danger"
              @click="cancelarAgendamento(agendamento.id)"
              :disabled="agendamento.status === 'CANCELADO'"
            >
              Cancelar
            </button>
          </div>
        </div>
      </div>

      <div v-if="agendamentoSelecionado" class="card remarcar-card">
        <h3>Alterar horário</h3>

        <div class="field">
          <label>Nova data</label>
          <input type="date" v-model="novaData" class="input" />
        </div>

        <button class="btn btn-primary" @click="buscarHorarios">
          Buscar horários
        </button>

        <div v-if="horarios.length" class="horarios-grid">
          <button
            v-for="horario in horarios"
            :key="horario.horario"
            class="slot"
            :disabled="!horario.disponivel"
            @click="remarcar(horario.horario)"
          >
            {{ horario.horario }}
          </button>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import api from '../services/api'

const agendamentos = ref([])
const loading = ref(true)

const agendamentoSelecionado = ref(null)
const novaData = ref('')
const horarios = ref([])

const carregarAgendamentos = async () => {
  try {
    const response = await api.get('/agendamentos/meus')
    console.log("Retorno da API:", response.data)
    agendamentos.value = response.data
  } catch (error) {
    console.error(error)
    alert('Erro ao carregar seus agendamentos.')
  } finally {
    loading.value = false
  }
}

const cancelarAgendamento = async (id) => {
  const confirmar = confirm('Tem certeza que deseja cancelar este agendamento?')

  if (!confirmar) return

  try {
    await api.patch(`/agendamentos/${id}/cancelar`)
    await carregarAgendamentos()
  } catch (error) {
    console.error(error)
    alert('Erro ao cancelar agendamento.')
  }
}

const abrirRemarcar = (agendamento) => {
  agendamentoSelecionado.value = agendamento
  novaData.value = agendamento.dataHoraInicio.substring(0, 10)
  horarios.value = []
}

const buscarHorarios = async () => {
  try {
    const response = await api.get('/agendamentos/disponibilidade', {
      params: {
        data: novaData.value,
        servicoId: agendamentoSelecionado.value.servico.id
      }
    })

    horarios.value = response.data
  } catch (error) {
    console.error(error)
    alert('Erro ao buscar horários.')
  }
}

const remarcar = async (horario) => {
  try {
    await api.patch(`/agendamentos/${agendamentoSelecionado.value.id}/remarcar`, {
      clienteId: agendamentoSelecionado.value.cliente.id,
      servicoId: agendamentoSelecionado.value.servico.id,
      dataHoraInicio: `${novaData.value}T${horario}`,
      observacoes: agendamentoSelecionado.value.observacoes
    })

    alert('Agendamento remarcado com sucesso!')

    agendamentoSelecionado.value = null
    horarios.value = []

    await carregarAgendamentos()
  } catch (error) {
    console.error(error)
    alert('Erro ao remarcar agendamento.')
  }
}

const formatarData = (dataHora) => {
  return new Date(dataHora).toLocaleDateString('pt-BR')
}

const formatarHora = (dataHora) => {
  return new Date(dataHora).toLocaleTimeString('pt-BR', {
    hour: '2-digit',
    minute: '2-digit'
  })
}

onMounted(carregarAgendamentos)
</script>

<style scoped>
.agendamentos-grid {
  display: grid;
  gap: 20px;
}

.agendamento-card,
.empty-card,
.remarcar-card {
  padding: 24px;
}

.agendamento-card h3,
.remarcar-card h3 {
  margin-top: 0;
  font-family: 'Poppins', sans-serif;
}

.agendamento-card p {
  color: var(--text-soft);
}

.actions {
  display: flex;
  gap: 12px;
  margin-top: 18px;
  flex-wrap: wrap;
}

.btn-danger {
  background: #ff5d5d;
  color: white;
}

.btn-danger:disabled,
.btn-outline:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.field {
  display: grid;
  gap: 8px;
  margin-bottom: 16px;
}

.horarios-grid {
  margin-top: 18px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.slot {
  border: 1px solid var(--card-border);
  background: rgba(255, 255, 255, 0.03);
  color: var(--text);
  padding: 12px 16px;
  border-radius: 12px;
  cursor: pointer;
}

.slot:hover:not(:disabled) {
  border-color: var(--primary);
}

.slot:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}
</style>