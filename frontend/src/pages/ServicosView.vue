<template>
  <section class="section">
    <div class="container">
      <div class="services-header">
        <span class="section-kicker">Nossos serviços</span>
        <h2 class="section-title">Serviços</h2>
        <p class="section-subtitle">
          Escolha o atendimento ideal para você. Valores transparentes, tempo estimado e uma experiência premium.
        </p>
      </div>

      <div v-if="loading">Carregando serviços...</div>

      <div v-else class="services-grid">
        <article
          class="card service-card"
          v-for="servico in servicos"
          :key="servico.id"
        >
          <div class="service-image">
            <img :src="imagemServico(servico.nome)" :alt="servico.nome" />
            <span v-if="servico.nome.includes('+')" class="service-badge">Mais pedido</span>
          </div>

          <div class="service-content">
            <h3>{{ servico.nome }}</h3>
            <p>{{ servico.descricao || 'Serviço premium com atendimento profissional.' }}</p>

            <span class="service-price">R$ {{ formatarPreco(servico.preco) }}</span>

            <div class="service-footer">
              <span>⏱ {{ servico.duracaoMinutos }} min</span>
              <router-link to="/agendamento" class="btn btn-primary">Agendar</router-link>
            </div>
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

const imagens = {
  corte: 'https://images.unsplash.com/photo-1622287162716-f311baa1a2b8?auto=format&fit=crop&w=900&q=80',
  barba: 'https://images.unsplash.com/photo-1503951914875-452162b0f3f1?auto=format&fit=crop&w=900&q=80',
  combo: 'https://images.unsplash.com/photo-1517832606299-7ae9b720a186?auto=format&fit=crop&w=900&q=80',
  pigmentacao: 'https://images.unsplash.com/photo-1621605815971-fbc98d665033?auto=format&fit=crop&w=900&q=80',
  sobrancelha: 'https://images.unsplash.com/photo-1622286342621-4bd786c2447c?auto=format&fit=crop&w=900&q=80'
}

const imagemServico = (nome = '') => {
  const n = nome.toLowerCase()

  if (n.includes('+')) return imagens.combo
  if (n.includes('barba')) return imagens.barba
  if (n.includes('pigment')) return imagens.pigmentacao
  if (n.includes('sobrancelha')) return imagens.sobrancelha
  return imagens.corte
}

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
.services-header {
  margin-bottom: 34px;
}

.section-kicker {
  color: var(--primary);
  text-transform: uppercase;
  letter-spacing: 3px;
  font-size: 13px;
  font-weight: 700;
}

.services-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(290px, 1fr));
  gap: 28px;
}

.service-card {
  overflow: hidden;
  min-height: 420px;
  transition: 0.3s ease;
}

.service-card:hover {
  transform: translateY(-8px);
  border-color: rgba(212, 175, 55, 0.35);
  box-shadow: 0 24px 50px rgba(0, 0, 0, 0.45);
}

.service-image {
  position: relative;
  height: 190px;
  overflow: hidden;
}

.service-image::after {
  content: "";
  position: absolute;
  inset: 0;
  background: linear-gradient(180deg, transparent, rgba(0,0,0,0.55));
}

.service-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: 0.45s ease;
}

.service-card:hover .service-image img {
  transform: scale(1.08);
}

.service-badge {
  position: absolute;
  top: 16px;
  right: 16px;
  z-index: 2;
  background: var(--primary);
  color: #111;
  padding: 7px 12px;
  border-radius: 999px;
  font-size: 12px;
  font-weight: 800;
}

.service-content {
  padding: 22px;
}

.service-content h3 {
  margin: 0 0 10px;
  font-family: 'Poppins', sans-serif;
  font-size: 22px;
}

.service-content p {
  margin: 0 0 18px;
  color: var(--text-soft);
  line-height: 1.6;
}

.service-price {
  display: inline-block;
  width: fit-content;
  padding: 8px 13px;
  border-radius: 999px;
  background: rgba(212, 175, 55, 0.12);
  color: var(--primary);
  font-weight: 800;
}

.service-footer {
  margin-top: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.service-footer span {
  color: var(--text-soft);
}

.btn-primary {
  background: linear-gradient(135deg, #f5c542, #d4af37);
  box-shadow: 0 8px 20px rgba(212, 175, 55, 0.22);
}
</style>