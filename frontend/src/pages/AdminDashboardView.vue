<template>
  <section class="section">
    <div class="container">
      <h1 class="section-title">Dashboard do Administrador</h1>

      <p class="section-subtitle">
        Bem-vindo, {{ nome }}
      </p>

      <div class="filters">
        <input type="date" v-model="dataInicio" class="filter-input" />

        <input type="date" v-model="dataFim" class="filter-input" />

        <button class="btn btn-primary" @click="filtrarDashboard">
          Filtrar
        </button>
      </div>

      <div class="grid dashboard-grid">
        <div class="card dash-card">
          <h3>Agendamentos Hoje</h3>
          <strong>{{ agendamentosHoje }}</strong>
          <router-link to="/admin/agenda" class="btn btn-secondary">
            Ver agenda
          </router-link>
        </div>

        <div class="card dash-card">
          <h3>Faturamento</h3>
          <strong>R$ {{ faturamento }}</strong>
        </div>

        <div class="card dash-card">
          <h3>Cortes no mês</h3>
          <strong>{{ cortesMes }}</strong>
        </div>

        <div class="card dash-card">
          <h3>No-show</h3>
          <strong>{{ noShow }}</strong>
        </div>
      </div>

      <div class="charts">
        <div class="card chart-card">
          <h2>Agendamentos por dia</h2>
          <Bar :data="barData" :options="chartOptions" />
        </div>

        <div class="card chart-card">
          <h2>Serviços mais escolhidos</h2>
          <Doughnut :data="pieData" :options="pieOptions" />
        </div>
      </div>

      <router-link to="/admin/agendamentos" class="btn btn-primary">
        Ver agendamentos
      </router-link>
    </div>
  </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getNome } from '../utils/auth'

import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  ArcElement,
  CategoryScale,
  LinearScale
} from 'chart.js'

import { Bar, Doughnut } from 'vue-chartjs'

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  ArcElement,
  CategoryScale,
  LinearScale
)

const nome = getNome()

const agendamentosHoje = ref(0)
const faturamento = ref(0)
const cortesMes = ref(0)
const noShow = ref(0)
const dataInicio = ref('')
const dataFim = ref('')

const barData = ref({
  labels: [],
  datasets: [
    {
      label: 'Agendamentos',
      data: [],
      backgroundColor: '#facc15'
    }
  ]
})

const pieData = ref({
  labels: [],
  datasets: [
    {
      data: [],
      backgroundColor: [
        '#facc15',
        '#22c55e',
        '#3b82f6',
        '#ef4444'
      ]
    }
  ]
})

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false
}

const pieOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: 'top'
    }
  }
}

const carregarDashboard = async () => {
  try {

    const token = localStorage.getItem('token')

    const response = await fetch('http://localhost:8080/agendamentos', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })

    const agendamentos = await response.json()

    console.log(agendamentos)

    const hoje = new Date().toISOString().split('T')[0]

    agendamentosHoje.value = agendamentos.filter(a =>
      a.dataHoraInicio.startsWith(hoje)
    ).length

    cortesMes.value = agendamentos.length

    faturamento.value = agendamentos.reduce((total, a) => {
      return total + a.servico.preco
    }, 0)

    noShow.value = agendamentos.filter(
      a => a.status === 'NO_SHOW'
    ).length


    const dias = {}

    agendamentos.forEach(a => {

      const dia = a.dataHoraInicio.split('T')[0]

      dias[dia] = (dias[dia] || 0) + 1
    })

    barData.value = {
      labels: Object.keys(dias),
      datasets: [
        {
          label: 'Agendamentos',
          data: Object.values(dias),
          backgroundColor: '#facc15'
        }
      ]
    }


    const servicos = {}

    agendamentos.forEach(a => {

      const nomeServico = a.servico.nome

      servicos[nomeServico] =
        (servicos[nomeServico] || 0) + 1
    })

    pieData.value = {
      labels: Object.keys(servicos),
      datasets: [
        {
          data: Object.values(servicos),
          backgroundColor: [
            '#facc15',
            '#22c55e',
            '#3b82f6',
            '#ef4444'
          ]
        }
      ]
    }

  } catch (error) {
    console.error(error)
  }
}

const filtrarDashboard = async () => {
  try {
    const response = await fetch('http://localhost:8080/agendamentos', {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    })

    const agendamentos = await response.json()

    let filtrados = agendamentos

    if (dataInicio.value && dataFim.value) {
      filtrados = agendamentos.filter(a => {
        const data = a.dataHoraInicio.split('T')[0]

        return (
          data >= dataInicio.value &&
          data <= dataFim.value
        )
      })
    }

    atualizarDashboard(filtrados)

  } catch (error) {
    console.error(error)
  }
}

const atualizarDashboard = (agendamentos) => {

  agendamentosHoje.value = agendamentos.length

  cortesMes.value = agendamentos.length

  faturamento.value = agendamentos.reduce(
    (total, a) => total + a.servico.preco,
    0
  )

  noShow.value = agendamentos.filter(
    a => a.status === 'NO_SHOW'
  ).length

  const dias = {}

  agendamentos.forEach(a => {
    const data = a.dataHoraInicio.split('T')[0]

    dias[data] = (dias[data] || 0) + 1
  })

  barData.value = {
    labels: Object.keys(dias),
    datasets: [
      {
        label: 'Agendamentos',
        data: Object.values(dias),
        backgroundColor: '#facc15'
      }
    ]
  }

  const servicos = {}

  agendamentos.forEach(a => {
    const nome = a.servico.nome

    servicos[nome] = (servicos[nome] || 0) + 1
  })

  pieData.value = {
    labels: Object.keys(servicos),
    datasets: [
      {
        data: Object.values(servicos),
        backgroundColor: [
          '#facc15',
          '#22c55e',
          '#3b82f6',
          '#ef4444'
        ]
      }
    ]
  }
}

onMounted(() => {

  const hoje = new Date()

  const primeiroDia = new Date(
    hoje.getFullYear(),
    hoje.getMonth(),
    1
  )

  dataInicio.value = primeiroDia
    .toISOString()
    .split('T')[0]

  dataFim.value = hoje
    .toISOString()
    .split('T')[0]

  filtrarDashboard()
})
</script>



<style scoped>
.filters {
  display: flex;
  gap: 16px;
  margin-bottom: 30px;
  flex-wrap: wrap;
  align-items: center;
}

.filter-input {
  height: 45px;
  padding: 0 14px;
  border-radius: 12px;
  border: 1px solid #2a2a2a;
  background: #111;
  color: white;
}

.dashboard-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 20px;
  margin-bottom: 40px;
}

.dash-card {
  padding: 24px;
}

.dash-card h3 {
  margin-top: 0;
  color: var(--text-soft);
  font-size: 16px;
}

.dash-card strong {
  font-size: 28px;
  font-family: 'Poppins', sans-serif;
}

.charts {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 24px;
  margin-bottom: 40px;
}

.chart-card {
  padding: 24px;
  height: 420px;

  display: flex;
  flex-direction: column;
  justify-content: center;
}

.chart-card canvas {
  max-width: 100% !important;
  max-height: 320px !important;
  margin: 0 auto;
}

.chart-card h2 {
  margin-bottom: 20px;
}

.btn-primary {
  display: flex;
  justify-content: center;
  width: fit-content;
  margin: 0 auto;
}

.btn-secondary {
  position: absolute;
  bottom: 16px;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(53, 53, 53, 0.63);
  }

@media (max-width: 900px) {
  .charts {
    grid-template-columns: 1fr;
  }
}
</style>