<template>
  <section class="agendamento-banner">
    <img
      src="https://images.unsplash.com/photo-1621605815971-fbc98d665033?auto=format&fit=crop&w=1600&q=80"
      alt="Barbearia"
    />
    <div class="overlay"></div>

    <div class="banner-content">
      <span>AGENDAMENTO</span>
      <h1>Reserve seu horário</h1>
      <p>Escolha o serviço, selecione a data e confirme em poucos passos.</p>
    </div>
  </section>

  <section class="section agendamento-section">
    <div class="container agendamento-layout">
      <div>
        <form @submit.prevent="buscarHorarios" class="card form-card">
          <h2>Escolha seu atendimento</h2>

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

      <aside class="card resumo-card">
        <h3>Resumo do agendamento</h3>

        <div class="resumo-item">
          <span>Serviço</span>
          <strong>{{ servicoSelecionado?.nome || 'Não selecionado' }}</strong>
        </div>

        <div class="resumo-item">
          <span>Data</span>
          <strong>{{ data || 'Não selecionada' }}</strong>
        </div>

        <div class="resumo-item">
          <span>Horário</span>
          <strong>{{ horarioSelecionado || 'Não selecionado' }}</strong>
        </div>

        <div class="resumo-item">
          <span>Duração</span>
          <strong>{{ servicoSelecionado?.duracaoMinutos ? `${servicoSelecionado.duracaoMinutos} min` : '-' }}</strong>
        </div>

        <div class="resumo-item total">
          <span>Valor</span>
          <strong>
            {{ servicoSelecionado?.preco ? `R$ ${formatarPreco(servicoSelecionado.preco)}` : '-' }}
          </strong>
        </div>

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

          <button type="submit" class="btn btn-primary full">
            Confirmar agendamento
          </button>
        </form>
      </aside>
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

const servicoSelecionado = computed(() => {
  return servicos.value.find(s => s.id === Number(servicoId.value))
})

const formatarPreco = (valor) => {
  return Number(valor).toFixed(2).replace('.', ',')
}

const carregarServicos = async () => {
  try {
    const response = await api.get('/servicos')
    servicos.value = response.data
  } catch (error) {
    console.error(error)
    alert('Erro ao carregar serviços.')
  }
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
.agendamento-banner {
  position: relative;
  height: 340px;
  overflow: hidden;
}

.agendamento-banner img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center center;
  transform: scale(1);
}


.agendamento-banner .overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(180deg, rgba(0,0,0,0.35), rgba(0,0,0,0.78));
}

.banner-content {
  position: absolute;
  inset: 0;
  z-index: 2;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 20px;
}

.banner-content span {
  color: var(--primary);
  letter-spacing: 4px;
  font-size: 13px;
  font-weight: 800;
}

.banner-content h1 {
  font-family: 'Poppins', sans-serif;
  font-size: clamp(42px, 6vw, 72px);
  margin: 14px 0 8px;
}

.banner-content p {
  color: var(--text-soft);
  margin: 0;
}

.agendamento-section {
  margin-top: -70px;
  position: relative;
  z-index: 3;
}

.agendamento-layout {
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  gap: 24px;
  align-items: start;
}

.form-card,
.horarios-card,
.resumo-card {
  padding: 28px;
  background: linear-gradient(180deg, rgba(255,255,255,0.05), rgba(255,255,255,0.02));
  backdrop-filter: blur(16px);
  border: 1px solid rgba(255,255,255,0.06);
  box-shadow: 0 20px 50px rgba(0,0,0,0.35);
}

.form-card h2,
.horarios-card h3,
.resumo-card h3 {
  margin-top: 0;
  font-family: 'Poppins', sans-serif;
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

.input,
.select {
  height: 58px;
  transition: border-color 0.3s, box-shadow 0.3s, transform 0.3s;
}

.input:focus,
.select:focus {
  transform: translateY(-2px);
}

.horarios-card {
  margin-top: 20px;
}

.horarios-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.slot {
  min-width: 100px;
  height: 54px;
  border: 1px solid var(--card-border);
  background: rgba(255,255,255,0.03);
  color: var(--text);
  border-radius: 14px;
  cursor: pointer;
  font-weight: 700;
  transition: 0.3s;
}

.slot:hover:not(:disabled) {
  transform: translateY(-3px);
  border-color: var(--primary);
  box-shadow: 0 10px 20px rgba(212,175,55,0.15);
}

.slot.selected {
  background: var(--primary);
  color: #111;
  border-color: var(--primary);
}

.slot:disabled {
  opacity: 0.35;
  cursor: not-allowed;
}

.resumo-card {
  animation: fadeUp 0.5s ease;
}

.resumo-item {
  display: flex;
  justify-content: space-between;
  gap: 16px;
  padding: 14px 0;
  border-bottom: 1px solid rgba(255,255,255,0.06);
}

.resumo-item span {
  color: var(--text-soft);
}

.resumo-item strong {
  text-align: right;
}

.resumo-item.total strong {
  color: var(--primary);
  font-size: 20px;
}

.mini-form {
  margin-top: 24px;
}

.full {
  width: 100%;
}

.btn-primary {
  background: linear-gradient(135deg, #f5c542, #d4af37);
  box-shadow: 0 10px 25px rgba(212,175,55,0.22);
}

@keyframes fadeUp {
  from {
    opacity: 0;
    transform: translateY(18px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 900px) {
  .agendamento-layout {
    grid-template-columns: 1fr;
  }

  .agendamento-section {
    margin-top: -40px;
  }
}
</style>