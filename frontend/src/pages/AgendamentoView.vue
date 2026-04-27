<template>
  <section class="section">
    <div class="container agendamento-layout">
      <div>
        <h2 class="section-title">Agendar horário</h2>
        <p class="section-subtitle">
          Escolha o serviço, selecione a data e finalize seu agendamento em poucos passos.
        </p>

        <form @submit.prevent="buscarHorarios" class="card form-card">
          <div class="field">
            <label>Serviço</label>
            <select v-model="servicoId" class="select" required>
              <option value="">Selecione</option>
              <option v-for="servico in servicos" :key="servico.id" :value="servico.id">
                {{ servico.nome }}
              </option>
            </select>
          </div>

          <div class="field">
            <label>Data</label>
            <input type="date" v-model="data" class="input" required />
          </div>

          <button type="submit" class="btn btn-primary">Buscar horários</button>
        </form>

        <div v-if="horarios.length" class="card horarios-card">
          <h3>Horários disponíveis</h3>
          <div class="horarios-grid">
            <button
              v-for="horario in horarios"
              :key="horario.horario"
              class="slot"
              :class="{ selected: horarioSelecionado === horario.horario }"
              :disabled="!horario.disponivel"
              @click="selecionarHorario(horario.horario)"
            >
              {{ horario.horario }}
            </button>
          </div>
        </div>
      </div>

      <div class="card resumo-card">
        <h3>Resumo do agendamento</h3>
        <p><strong>Serviço:</strong> {{ nomeServicoSelecionado || 'Não selecionado' }}</p>
        <p><strong>Data:</strong> {{ data || 'Não selecionada' }}</p>
        <p><strong>Horário:</strong> {{ horarioSelecionado || 'Não selecionado' }}</p>

        <form v-if="horarioSelecionado" @submit.prevent="agendar" class="mini-form">
          <div class="field">
            <label>Nome</label> 
            <input v-model="nome" class="input" required />
          </div>

          <div class="field">
            <label>Telefone</label>
            <input v-model="telefone" class="input" required />
          </div>

          <div class="field">
            <label>E-mail</label>
            <input v-model="email" class="input" />
          </div>

          <button type="submit" class="btn btn-primary full">Confirmar agendamento</button>
        </form>
      </div>
    </div>
  </section>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import api from '../services/api'

const servicos = ref([])
const horarios = ref([])
const servicoId = ref('')
const data = ref('')
const horarioSelecionado = ref('')

const nome = ref('')
const telefone = ref('')
const email = ref('')

const nomeServicoSelecionado = computed(() => {
  const servico = servicos.value.find(s => s.id === Number(servicoId.value))
  return servico ? servico.nome : ''
})

const carregarServicos = async () => {
  const response = await api.get('/servicos')
  servicos.value = response.data
}

const buscarHorarios = async () => {
  try {
    const response = await api.get('/agendamentos/disponibilidade', {
      params: {
        data: data.value,
        servicoId: servicoId.value
      }
    })
    horarios.value = response.data
    horarioSelecionado.value = ''
  } catch (error) {
    console.error(error)
    alert('Erro ao buscar horários.')
  }
}

const selecionarHorario = (horario) => {
  horarioSelecionado.value = horario
}

const agendar = async () => {
  try {
    const clienteResponse = await api.post('/clientes', {
      nome: nome.value,
      telefone: telefone.value,
      email: email.value
    })

    const dataHoraInicio = `${data.value}T${horarioSelecionado.value}`

    await api.post('/agendamentos', {
      clienteId: clienteResponse.data.id,
      servicoId: Number(servicoId.value),
      dataHoraInicio,
      observacoes: 'Agendamento realizado pelo cliente'
    })

    alert('Agendamento realizado com sucesso!')
    nome.value = ''
    telefone.value = ''
    email.value = ''
    horarioSelecionado.value = ''
    horarios.value = []
  } catch (error) {
    console.error(error)
    alert('Erro ao realizar agendamento.')
  }
}

onMounted(carregarServicos)
</script>

<style scoped>
.agendamento-layout {
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  gap: 24px;
  align-items: start;
}

.form-card,
.horarios-card,
.resumo-card {
  padding: 24px;
}

.field {
  display: grid;
  gap: 8px;
  margin-bottom: 16px;
}

.field label {
  color: var(--text-soft);
  font-size: 14px;
}

.horarios-card {
  margin-top: 20px;
}

.horarios-card h3,
.resumo-card h3 {
  margin-top: 0;
  font-family: 'Poppins', sans-serif;
}

.horarios-grid {
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
  cursor: pointer;
}

.slot:hover:not(:disabled) {
  border-color: var(--primary);
}

.slot.selected {
  background: var(--primary);
  color: #111;
  border-color: var(--primary);
  font-weight: 700;
}

.slot:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.resumo-card p {
  color: var(--text-soft);
}

.mini-form {
  margin-top: 24px;
}

.full {
  width: 100%;
}

@media (max-width: 900px) {
  .agendamento-layout {
    grid-template-columns: 1fr;
  }
}
</style>