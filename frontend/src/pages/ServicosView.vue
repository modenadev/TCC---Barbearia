<template>
  <section class="section">
    <div class="container">
      <h2 class="section-title">Serviços</h2>
      <p class="section-subtitle">
        Escolha o atendimento ideal para você. Valores transparentes, tempo estimado e uma interface pensada para decisão rápida.
      </p>

      <div v-if="loading">Carregando serviços...</div>

      <div v-else class="services-grid">
        <article class="card service-card" v-for="servico in servicos" :key="servico.id">
          <div class="service-top">
            <div>
              <h3>{{ servico.nome }}</h3>
              <p>{{ servico.descricao || 'Serviço premium com atendimento profissional.' }}</p>
            </div>
            <span class="service-price">R$ {{ formatarPreco(servico.preco) }}</span>
          </div>

          <div class="service-footer">
            <span>{{ servico.duracaoMinutos }} min</span>
            <router-link to="/agendamento" class="btn btn-primary">Agendar</router-link>
          </div>
        </article>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import api from '../services/api'

const servicos = ref([])
const loading = ref(true)

const formatarPreco = (valor) => {
  return Number(valor).toFixed(2).replace('.', ',')
}

const carregarServicos = async () => {
  try {
    const response = await api.get('/servicos')
    servicos.value = response.data
  } catch (error) {
    console.error('Erro ao carregar serviços:', error)
  } finally {
    loading.value = false
  }
}

onMounted(carregarServicos)
</script>

<style scoped>
.services-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
}

.service-card {
  padding: 22px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 220px;
}

.service-top {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.service-top h3 {
  margin: 0;
  font-family: 'Poppins', sans-serif;
}

.service-top p {
  margin: 0;
  color: var(--text-soft);
  line-height: 1.6;
}

.service-price {
  display: inline-block;
  width: fit-content;
  padding: 8px 12px;
  border-radius: 999px;
  background: rgba(212, 175, 55, 0.12);
  color: var(--primary);
  font-weight: 700;
}

.service-footer {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.service-footer span {
  color: var(--text-soft);
}
</style>