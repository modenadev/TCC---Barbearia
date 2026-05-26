<template>
  <section class="section admin-agenda-section">
    <div class="container">
      <div class="header-actions">
        <h1 class="section-title">Todos os agendamentos</h1>
        <button class="btn btn-outline" @click="carregar">🔄 Atualizar</button>
      </div>

      <div v-if="loading" class="loading-state">
        <div class="spinner"></div>
        <p>Carregando agendamentos...</p>
      </div>

      <div v-else-if="agendamentos.length === 0" class="empty-state">
        <p>Nenhum agendamento encontrado.</p>
      </div>

      <div v-else class="agendamentos-grid">
        <div v-for="agendamento in agendamentos" :key="agendamento.id" class="card agendamento-card">

          <div class="card-header">
            <h3>{{ agendamento.servico.nome }}</h3>
            <span :class="['badge-status', classeStatus(agendamento.status)]">
              {{ agendamento.status }}
            </span>
          </div>

          <div class="card-body">
            <p><strong>👤 Cliente:</strong> {{ agendamento.cliente.nome }}</p>
            <p><strong>📅 Data:</strong> {{ formatarData(agendamento.dataHoraInicio) }}</p>
            <p><strong>⏰ Horário:</strong> {{ formatarHora(agendamento.dataHoraInicio) }}</p>
          </div>

          <div class="card-footer actions">
            <button class="btn btn-outline btn-sm" @click="abrirRemarcar(agendamento)">
              Remarcar
            </button>
            <button class="btn btn-danger btn-sm" @click="cancelar(agendamento.id)">
              Cancelar
            </button>
          </div>
        </div>
      </div>

      <div v-if="selecionado" class="modal-overlay" @click.self="fecharRemarcar">
        <div class="card remarcar-card">
          <div class="modal-header">
            <h3>Remarcar horário</h3>
            <button class="close-btn" @click="fecharRemarcar">✕</button>
          </div>

          <p class="modal-subtitle">Cliente: <strong>{{ selecionado.cliente.nome }}</strong></p>

          <div class="field">
            <label>Selecione a nova data</label>
            <input type="date" v-model="novaData" class="input" />
          </div>

          <button class="btn btn-primary full-btn" @click="buscarHorarios">
            Buscar horários disponíveis
          </button>

          <div v-if="horarios.length" class="horarios-grid">
            <button v-for="horario in horarios" :key="horario.horario" class="slot" :disabled="!horario.disponivel"
              @click="remarcar(horario.horario)">
              {{ horario.horario }}
            </button>
          </div>
          <div v-else-if="novaData && horarios.length === 0" class="empty-slots">
            <p>Busque por uma data para ver os horários.</p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import api from '../services/api'
import Swal from 'sweetalert2' 

const agendamentos = ref([])
const loading = ref(true)
const selecionado = ref(null)
const novaData = ref('')
const horarios = ref([])

const swalConfig = {
  background: '#141416',
  color: '#ffffff',
  confirmButtonColor: '#facc15',
}

const carregar = async () => {
  loading.value = true
  try {
    const response = await api.get('/agendamentos/admin/todos')
    agendamentos.value = response.data
  } catch (error) {
    console.error(error)
    Swal.fire({
      ...swalConfig,
      icon: 'error',
      title: 'Ops!',
      text: 'Ocorreu um erro ao carregar os agendamentos.'
    })
  } finally {
    loading.value = false
  }
}

const cancelar = async (id) => {
  const confirmacao = await Swal.fire({
    ...swalConfig,
    title: 'Cancelar Agendamento?',
    text: "Esta ação não poderá ser desfeita.",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#ef4444', 
    cancelButtonColor: '#374151',  
    confirmButtonText: 'Sim, cancelar!',
    cancelButtonText: 'Voltar'
  })

  if (confirmacao.isConfirmed) {
    try {
      await api.patch(`/agendamentos/${id}/cancelar`)
      await carregar()

      Swal.fire({
        ...swalConfig,
        icon: 'success',
        title: 'Cancelado',
        text: 'O agendamento foi cancelado com sucesso.'
      })
    } catch (error) {
      Swal.fire({
        ...swalConfig,
        icon: 'error',
        title: 'Erro',
        text: 'Não foi possível cancelar o agendamento.'
      })
    }
  }
}

const abrirRemarcar = (agendamento) => {
  selecionado.value = agendamento
  novaData.value = agendamento.dataHoraInicio.substring(0, 10)
  horarios.value = []
}

const fecharRemarcar = () => {
  selecionado.value = null
  novaData.value = ''
  horarios.value = []
}

const buscarHorarios = async () => {
  if (!novaData.value) {
    Swal.fire({
      ...swalConfig,
      icon: 'warning',
      title: 'Atenção',
      text: 'Por favor, selecione uma data.'
    })
    return
  }

  try {
    const response = await api.get('/agendamentos/disponibilidade', {
      params: {
        data: novaData.value,
        servicoId: selecionado.value.servico.id
      }
    })
    horarios.value = response.data

    if (horarios.value.length === 0) {
      Swal.fire({
        ...swalConfig,
        icon: 'info',
        title: 'Data Indisponível',
        text: 'Não há horários disponíveis para esta data. Tente outro dia.'
      })
    }
  } catch (error) {
    Swal.fire({
      ...swalConfig,
      icon: 'error',
      title: 'Erro',
      text: 'Ocorreu um erro ao buscar os horários.'
    })
  }
}

const remarcar = async (horario) => {
  try {
    await api.patch(`/agendamentos/${selecionado.value.id}/remarcar`, {
      clienteId: selecionado.value.cliente.id,
      servicoId: selecionado.value.servico.id,
      dataHoraInicio: `${novaData.value}T${horario}`,
      observacoes: selecionado.value.observacoes
    })

    Swal.fire({
      ...swalConfig,
      icon: 'success',
      title: 'Remarcado!',
      text: 'O horário do cliente foi alterado com sucesso.'
    })

    fecharRemarcar()
    await carregar()
  } catch (error) {
    Swal.fire({
      ...swalConfig,
      icon: 'error',
      title: 'Erro',
      text: 'Não foi possível remarcar o horário.'
    })
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

const classeStatus = (status) => {
  const s = status ? status.toUpperCase() : ''
  if (s === 'CONFIRMADO' || s === 'CONCLUIDO') return 'status-green'
  if (s === 'CANCELADO' || s === 'NO_SHOW') return 'status-red'
  if (s === 'PENDENTE') return 'status-yellow'
  return 'status-gray'
}

onMounted(carregar)
</script>

<style scoped>
.admin-agenda-section {
  padding: 40px 0;
  min-height: 80vh;
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  flex-wrap: wrap;
  gap: 15px;
}


.agendamentos-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 24px;
}

.agendamento-card {
  background: rgba(20, 20, 22, 0.8);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 16px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  transition: transform 0.2s, box-shadow 0.2s;
}

.agendamento-card:hover {
  transform: translateY(-4px);
  border-color: rgba(250, 204, 21, 0.3);
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.4);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 16px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  padding-bottom: 12px;
}

.card-header h3 {
  margin: 0;
  font-size: 18px;
  font-family: 'Poppins', sans-serif;
  color: #fff;
}

.card-body p {
  margin: 8px 0;
  color: #9ca3af;
  font-size: 14px;
}

.card-body strong {
  color: #e5e7eb;
}

.card-footer {
  margin-top: auto;
  padding-top: 16px;
}

.actions {
  display: flex;
  gap: 12px;
}

.btn-sm {
  padding: 8px 16px;
  font-size: 13px;
  flex: 1;
  text-align: center;
  justify-content: center;
}

.btn-danger {
  background: transparent;
  color: #ef4444;
  border: 1px solid rgba(239, 68, 68, 0.3);
}

.btn-danger:hover {
  background: rgba(239, 68, 68, 0.1);
  border-color: #ef4444;
}


.badge-status {
  padding: 4px 10px;
  border-radius: 999px;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.5px;
  text-transform: uppercase;
}

.status-green {
  background: rgba(34, 197, 94, 0.15);
  color: #4ade80;
  border: 1px solid rgba(34, 197, 94, 0.2);
}

.status-red {
  background: rgba(239, 68, 68, 0.15);
  color: #f87171;
  border: 1px solid rgba(239, 68, 68, 0.2);
}

.status-yellow {
  background: rgba(250, 204, 21, 0.15);
  color: #fde047;
  border: 1px solid rgba(250, 204, 21, 0.2);
}

.status-gray {
  background: rgba(156, 163, 175, 0.15);
  color: #9ca3af;
  border: 1px solid rgba(156, 163, 175, 0.2);
}


.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.75);
  backdrop-filter: blur(4px);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
  padding: 20px;
}

.remarcar-card {
  background: #111112;
  border: 1px solid rgba(250, 204, 21, 0.3);
  width: 100%;
  max-width: 450px;
  padding: 30px;
  border-radius: 20px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.7);
  animation: modalIn 0.3s ease-out;
}

@keyframes modalIn {
  from {
    opacity: 0;
    transform: translateY(20px) scale(0.95);
  }

  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.modal-header h3 {
  margin: 0;
  font-family: 'Poppins', sans-serif;
  font-size: 22px;
  color: var(--primary, #facc15);
}

.close-btn {
  background: transparent;
  border: none;
  color: #9ca3af;
  font-size: 20px;
  cursor: pointer;
  transition: 0.2s;
}

.close-btn:hover {
  color: #ef4444;
}

.modal-subtitle {
  color: #d1d5db;
  margin-bottom: 24px;
  font-size: 15px;
}

.field {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 20px;
}

.field label {
  color: #9ca3af;
  font-size: 14px;
}

.input {
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.1);
  color: #fff;
  padding: 12px 16px;
  border-radius: 12px;
  font-size: 15px;
  outline: none;
}

.input:focus {
  border-color: var(--primary, #facc15);
}

.full-btn {
  width: 100%;
  padding: 14px;
  border-radius: 12px;
  font-size: 15px;
}

.horarios-grid {
  margin-top: 24px;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(80px, 1fr));
  gap: 10px;
  max-height: 200px;
  overflow-y: auto;
  padding-right: 5px;
}

.slot {
  border: 1px solid rgba(255, 255, 255, 0.1);
  background: rgba(255, 255, 255, 0.03);
  color: #fff;
  padding: 10px;
  border-radius: 10px;
  cursor: pointer;
  font-weight: 600;
  transition: 0.2s;
}

.slot:hover:not(:disabled) {
  border-color: var(--primary, #facc15);
  background: rgba(250, 204, 21, 0.1);
  color: var(--primary, #facc15);
  transform: translateY(-2px);
}

.slot:disabled {
  opacity: 0.3;
  cursor: not-allowed;
  text-decoration: line-through;
}

.empty-slots {
  margin-top: 20px;
  text-align: center;
  color: #9ca3af;
  font-size: 14px;
}


.loading-state,
.empty-state {
  text-align: center;
  padding: 60px 20px;
  color: #9ca3af;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid rgba(255, 255, 255, 0.1);
  border-top-color: var(--primary, #facc15);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 16px;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}
</style>