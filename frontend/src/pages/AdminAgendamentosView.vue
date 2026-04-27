<template>
  <section class="section">
    <div class="container">
      <h1 class="section-title">Todos os agendamentos</h1>

      <div v-if="loading">Carregando...</div>

      <div v-else class="agendamentos-grid">
        <div v-for="agendamento in agendamentos" :key="agendamento.id" class="card agendamento-card">
          <h3>{{ agendamento.servico.nome }}</h3>

          <p><strong>Cliente:</strong> {{ agendamento.cliente.nome }}</p>
          <p><strong>Data:</strong> {{ formatarData(agendamento.dataHoraInicio) }}</p>
          <p><strong>Horário:</strong> {{ formatarHora(agendamento.dataHoraInicio) }}</p>
          <p><strong>Status:</strong> {{ agendamento.status }}</p>

          <div class="actions">
            <button class="btn btn-outline" @click="abrirRemarcar(agendamento)">
              Alterar horário
            </button>

            <button class="btn btn-danger" @click="cancelar(agendamento.id)">
              Cancelar
            </button>
          </div>
        </div>
      </div>

      <div v-if="selecionado" class="card remarcar-card">
        <h3>Remarcar horário</h3>

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
const selecionado = ref(null)
const novaData = ref('')
const horarios = ref([])

const carregar = async () => {
  try {
    const response = await api.get('/agendamentos/admin/todos')
    agendamentos.value = response.data
  } catch (error) {
    console.error(error)
    alert('Erro ao carregar agendamentos.')
  } finally {
    loading.value = false
  }
}

const cancelar = async (id) => {
  if (!confirm('Deseja cancelar este agendamento?')) return

  await api.patch(`/agendamentos/${id}/cancelar`)
  await carregar()
}

const abrirRemarcar = (agendamento) => {
  selecionado.value = agendamento
  novaData.value = agendamento.dataHoraInicio.substring(0, 10)
  horarios.value = []
}

const buscarHorarios = async () => {
  const response = await api.get('/agendamentos/disponibilidade', {
    params: {
      data: novaData.value,
      servicoId: selecionado.value.servico.id
    }
  })

  horarios.value = response.data
}

const remarcar = async (horario) => {
  await api.patch(`/agendamentos/${selecionado.value.id}/remarcar`, {
    clienteId: selecionado.value.cliente.id,
    servicoId: selecionado.value.servico.id,
    dataHoraInicio: `${novaData.value}T${horario}`,
    observacoes: selecionado.value.observacoes
  })

  alert('Horário alterado com sucesso!')
  selecionado.value = null
  horarios.value = []
  await carregar()
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

onMounted(carregar)
</script>

<style scoped>
.agendamentos-grid {
  display: grid;
  gap: 20px;
}

.agendamento-card,
.remarcar-card {
  padding: 24px;
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
  background: rgba(255,255,255,0.03);
  color: var(--text);
  padding: 12px 16px;
  border-radius: 12px;
}
</style>