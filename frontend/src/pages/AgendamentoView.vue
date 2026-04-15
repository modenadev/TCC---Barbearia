<template>
  <section>
    <h2>Agendamento</h2>

    <form @submit.prevent="buscarHorarios" class="form">
      <div>
        <label>Serviço</label>
        <select v-model="servicoId" required>
          <option value="">Selecione</option>
          <option v-for="servico in servicos" :key="servico.id" :value="servico.id">
            {{ servico.nome }}
          </option>
        </select>
      </div>

      <div>
        <label>Data</label>
        <input type="date" v-model="data" required />
      </div>

      <button type="submit">Buscar horários</button>
    </form>

    <div v-if="horarios.length" class="horarios">
      <h3>Horários disponíveis</h3>
      <button
        v-for="horario in horarios"
        :key="horario.horario"
        :disabled="!horario.disponivel"
        @click="selecionarHorario(horario.horario)"
      >
        {{ horario.horario }}
      </button>
    </div>

    <form v-if="horarioSelecionado" @submit.prevent="agendar" class="form">
      <h3>Finalizar agendamento</h3>

      <div>
        <label>Nome</label>
        <input v-model="nome" required />
      </div>

      <div>
        <label>Telefone</label>
        <input v-model="telefone" required />
      </div>

      <div>
        <label>Email</label>
        <input v-model="email" />
      </div>

      <button type="submit">Confirmar agendamento</button>
    </form>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import api from '../services/api'

const servicos = ref([])
const horarios = ref([])
const servicoId = ref('')
const data = ref('')
const horarioSelecionado = ref('')

const nome = ref('')
const telefone = ref('')
const email = ref('')

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
  } catch (error) {
    console.error(error)
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
    horarios.value = []
    horarioSelecionado.value = ''
    nome.value = ''
    telefone.value = ''
    email.value = ''
  } catch (error) {
    console.error(error)
    alert('Erro ao realizar agendamento.')
  }
}

onMounted(() => {
  carregarServicos()
})
</script>

<style scoped>
.form {
  display: grid;
  gap: 16px;
  max-width: 400px;
  margin-bottom: 24px;
}

input,
select,
button {
  padding: 10px;
  border-radius: 8px;
  border: none;
}

button {
  background: #d4af37;
  color: #111;
  font-weight: bold;
  cursor: pointer;
}

.horarios {
  margin: 24px 0;
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}
</style>