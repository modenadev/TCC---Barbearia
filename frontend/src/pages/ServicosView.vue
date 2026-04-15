<template>
  <section>
    <h2>Serviços</h2>

    <div v-if="loading">Carregando serviços...</div>

    <div v-else class="grid">
      <div class="card" v-for="servico in servicos" :key="servico.id">
        <h3>{{ servico.nome }}</h3>
        <p>{{ servico.descricao }}</p>
        <p><strong>Preço:</strong> R$ {{ servico.preco }}</p>
        <p><strong>Duração:</strong> {{ servico.duracaoMinutos }} min</p>
      </div>
    </div>
  </section>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import api from '../services/api'

const servicos = ref([])
const loading = ref(true)

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

onMounted(() => {
  carregarServicos()
})
</script>

<style scoped>
.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 16px;
}

.card {
  background: #1a1a1a;
  padding: 20px;
  border-radius: 12px;
  border: 1px solid #333;
}
</style>