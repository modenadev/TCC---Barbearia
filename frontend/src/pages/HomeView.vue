<template>

  <section class="home-banner">

    <img src="https://images.unsplash.com/photo-1622286342621-4bd786c2447c?q=80&w=2070&auto=format&fit=crop"
      alt="Barbearia" class="banner-bg" />

    <div class="banner-overlay"></div>

    <div class="banner-content">
      <img src="/src/assets/logo.png" alt="RD Barbearia" class="banner-logo" />

    </div>


  </section>
  <section class="hero">
    <div class="container hero-grid">
      <div>
        <h1 class="hero-title">Seu horário na barbearia, com praticidade de app.</h1>
        <p class="hero-text">
          Escolha seu serviço, veja horários em tempo real e agende em poucos segundos.
          Uma experiência moderna, rápida e pensada para mobile.
        </p>

        <div class="hero-actions">
          <router-link to="/agendamento" class="btn btn-primary">Agendar agora</router-link>
          <router-link to="/servicos" class="btn btn-outline">Ver serviços</router-link>
        </div>

        <div class="hero-stats">
          <div class="card stat">
            <strong>100%</strong>
            <span>online</span>
          </div>
          <div class="card stat">
            <strong>24h</strong>
            <span>disponível</span>
          </div>
        </div>


      </div>

      <div class="card hero-card">
        <div class="hero-image"></div>
        <div class="hero-card-content">
          <h3>Experiência premium</h3>
          <p>
            Interface moderna com consulta de disponibilidade, confirmação de horário e fluxo rápido de agendamento.
          </p>
        </div>
      </div>


    </div>
  </section>

  <section class="gallery-section">
    <div class="container">
      <h2 class="gallery-title">Galeria de fotos</h2>
      <h2 class="gallery-subtitle">Confira nossos espaços e serviços</h2>

      <div class="gallery-carousel">
        <div class="gallery-track" :style="{ transform: `translateX(-${slideAtual * 100}%)` }">
          <div v-for="(imagem, index) in imagensGaleria" :key="index" class="gallery-slide">
            <img :src="imagem" alt="Imagem da barbearia" />
          </div>
        </div>

        <button class="gallery-btn prev" @click="voltarSlide">‹</button>
        <button class="gallery-btn next" @click="proximoSlide">›</button>

        <div class="gallery-dots">
          <button v-for="(_, index) in imagensGaleria" :key="index" :class="{ active: index === slideAtual }"
            @click="slideAtual = index"></button>
        </div>
      </div>
    </div>
  </section>

  <section class="contact-section">
    <div class="container">
      <div class="contact-card">

        <div class="contact-form-side">
          <span class="contact-badge">FALE CONOSCO</span>

          <h2 class="contact-title">
            Entre em contato
          </h2>

          <p class="contact-text">
            Tire dúvidas, envie sugestões ou agende seu horário com nossa equipe.
          </p>

          <form class="contact-form">
            <div class="contact-row">
              <input type="text" placeholder="Seu nome" class="contact-input" />
              <input type="text" placeholder="Celular" class="contact-input" />
            </div>

            <input type="email" placeholder="Seu email" class="contact-input full" />

            <input type="text" placeholder="Assunto" class="contact-input full" />

            <textarea placeholder="Digite sua mensagem..." class="contact-textarea"></textarea>

            <button class="contact-btn">
              Enviar mensagem
            </button>
          </form>
        </div>

        <div class="contact-image-side">
          <img src="https://images.unsplash.com/photo-1517832606299-7ae9b720a186?auto=format&fit=crop&w=1200&q=80"
            alt="Barbearia" />
        </div>

      </div>
    </div>
  </section>

  <section class="location-section">
    <div class="container">
      <h1 class="location-title">NOSSAS UNIDADES</h1>
      <div class="location-border">


        <p class="location-text">Mogi Mirim</p>
        <div class="location-top">
          <div class="location-info">
            <p>Rua do mirante, 606</p>
            <p>Mogi Mirim | São Paulo | 13801-100</p>
            <p>TEL (19) 99976-2700</p>
          </div>

          <div class="location-hours">
            <p>Terça a sexta: 9h às 19h</p>
            <p>Sábado e domingo: 9h às 17h</p>
          </div>

          <div class="location-image">
            <img src="https://images.unsplash.com/photo-1517832606299-7ae9b720a186?auto=format&fit=crop&w=600&q=80" />
          </div>
        </div>

        <div class="location-map">
          <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3687.896946264733!2d-46.95102488807479!3d-22.432904279506282!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x94c8f972056a6afd%3A0x82421a75176182ba!2sRd%20Studio%20de%20Beleza%20e%20Barbearia!5e0!3m2!1spt-BR!2sbr!4v1777992864007!5m2!1spt-BR!2sbr"
            width="100%" height="100%" style="border:0;" allowfullscreen="" loading="lazy"
            referrerpolicy="no-referrer-when-downgrade"></iframe>
        </div>



      </div>
    </div>
  </section>

</template>

<script setup>
import { onMounted, onUnmounted, ref } from 'vue'

const imagensGaleria = [
  'https://images.unsplash.com/photo-1621605815971-fbc98d665033?auto=format&fit=crop&w=1200&q=80',
  'https://images.unsplash.com/photo-1599351431202-1e0f0137899a?auto=format&fit=crop&w=1200&q=80',
  'https://images.unsplash.com/photo-1512690459411-b9245aed614b?auto=format&fit=crop&w=1200&q=80'
]

const slideAtual = ref(0)
let intervaloGaleria = null

const proximoSlide = () => {
  slideAtual.value = (slideAtual.value + 1) % imagensGaleria.length
}

const voltarSlide = () => {
  slideAtual.value =
    slideAtual.value === 0 ? imagensGaleria.length - 1 : slideAtual.value - 1
}

onMounted(() => {
  intervaloGaleria = setInterval(proximoSlide, 3500)
})

onUnmounted(() => {
  clearInterval(intervaloGaleria)
})

</script>




<style scoped>

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  width: 100%;
  box-sizing: border-box;
}

.home-banner {
  position: relative;
  height: 92vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

.banner-bg {
  position: absolute;
  width: 100%;
  height: 100%;
  object-fit: cover;
  inset: 0;
  transform: scale(1.05);
}

.banner-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(180deg, rgba(0, 0, 0, 0.45), rgba(0, 0, 0, 0.65));
  backdrop-filter: blur(1px);
}

.banner-content {
  position: relative;
  z-index: 2;
  text-align: center;
  padding: 20px;
  width: 100%;
  box-sizing: border-box;
}

.banner-logo {
  width: 600px;
  max-width: 90vw;
  margin-bottom: 12px;
  filter: drop-shadow(0 10px 25px rgba(0, 0, 0, 0.45));
}

/* --- HERO SECTION --- */
.hero {
  padding: 56px 0 40px;
}

.hero-grid {
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  gap: 28px;
  align-items: center;
}

.hero-title {
  font-family: 'Poppins', sans-serif;
  font-size: clamp(36px, 5vw, 62px);
  line-height: 1.05;
  margin: 0 0 18px;
}

.hero-text {
  max-width: 620px;
  color: var(--text-soft);
  line-height: 1.7;
  font-size: 17px;
  margin-bottom: 24px;
}

.hero-actions {
  display: flex;
  gap: 14px;
  margin-bottom: 28px;
  flex-wrap: wrap;
}

.hero-stats {
  display: flex;
  gap: 14px;
  flex-wrap: wrap;
}

.stat {
  min-width: 120px;
  padding: 18px;
}

.stat strong {
  display: block;
  font-size: 22px;
  font-family: 'Poppins', sans-serif;
}

.stat span {
  color: var(--text-soft);
  font-size: 14px;
}

.hero-card {
  overflow: hidden;
}

.hero-image {
  height: 320px;
  background: linear-gradient(180deg, rgba(0, 0, 0, 0.05), rgba(0, 0, 0, 0.35)),
    url('https://images.unsplash.com/photo-1621605815971-fbc98d665033?auto=format&fit=crop&w=1200&q=80') center/cover;
}

.hero-card-content {
  padding: 22px;
}

/* --- GALERIA --- */
.gallery-section {
  padding: 20px 0 70px;
}

.gallery-title {
  font-family: 'Poppins', sans-serif;
  font-size: 28px;
  text-align: center;
  margin-bottom: 22px;
}

.gallery-subtitle {
  color: var(--text-soft);
  text-align: center;
  margin-bottom: 40px;
}

.gallery-subtitle::before,
.gallery-subtitle::after {
  content: "";
  display: inline-block;
  width: 40px;
  height: 1px;
  background: yellow;
  margin: 0 14px 7px;
}

.gallery-carousel {
  position: relative;
  overflow: hidden;
  border-radius: 22px;
  border: 1px solid var(--card-border);
  box-shadow: var(--shadow);
  height: 360px;
}

.gallery-track {
  display: flex;
  height: 100%;
  transition: transform 0.7s ease;
}

.gallery-slide {
  min-width: 100%;
  height: 100%;
}

.gallery-slide img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.gallery-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 2;
  width: 44px;
  height: 44px;
  border-radius: 999px;
  background: rgba(0, 0, 0, 0.45);
  color: white;
  font-size: 34px;
  cursor: pointer;
  display: grid;
  place-items: center;
}

.gallery-btn.prev {
  left: 18px;
}

.gallery-btn.next {
  right: 18px;
}

.gallery-dots {
  position: absolute;
  z-index: 2;
  left: 50%;
  bottom: 18px;
  transform: translateX(-50%);
  display: flex;
  gap: 8px;
}

.gallery-dots button {
  width: 10px;
  height: 10px;
  border-radius: 999px;
  border: none;
  background: rgba(255, 255, 255, 0.45);
}

.gallery-dots button.active {
  width: 28px;
  background: var(--primary);
}

/* --- CONTATO --- */
.contact-section {
  padding: 60px 0;
}

.contact-card {
  display: grid;
  grid-template-columns: 1fr 0.9fr;
  overflow: hidden;
  border-radius: 28px;
  background: rgba(16, 16, 18, 0.75);
  border: 1px solid rgba(255, 255, 255, 0.06);
  backdrop-filter: blur(16px);
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.35);
  width: 100%;
}

.contact-form-side {
  padding: 60px;
  box-sizing: border-box;
}

.contact-title {
  font-family: 'Poppins', sans-serif;
  font-size: 44px;
  margin-bottom: 16px;
  line-height: 1.1;
}

.contact-form {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.contact-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 18px;
}

.contact-input,
.contact-textarea {
  width: 100%;
  border: none;
  outline: none;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.04);
  border: 1px solid rgba(255, 255, 255, 0.06);
  color: white;
  font-size: 15px;
  box-sizing: border-box;
}

.contact-input {
  height: 55px;
  padding: 0 20px;
}

.contact-textarea {
  min-height: 140px;
  padding: 20px;
  resize: none;
}

.contact-btn {
  width: fit-content;
  padding: 16px 34px;
  border: none;
  border-radius: 16px;
  background: var(--primary);
  color: #111;
  font-weight: 700;
  cursor: pointer;
}

.contact-image-side {
  position: relative;
  width: 100%;
}

.contact-image-side img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* --- LOCALIZAÇÃO --- */
.location-section {
  padding: 40px 0;
}

.location-title {
  font-size: 28px;
  text-align: center;
  margin-bottom: 25px;
}

.location-border {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid rgb(44, 44, 44);
  border-radius: 36px;
  box-sizing: border-box;
}

.location-text {
  font-size: 26px;
  margin: 10px 0 20px 20px;
  font-weight: bold;
}

.location-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 20px 25px 20px;
  gap: 20px;
}

.location-info,
.location-hours {
  flex: 1;
  line-height: 1.6;
}

.location-image {
  width: 300px;
  height: 180px;
  border-radius: 12px;
  overflow: hidden;
  flex-shrink: 0;
}

.location-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.location-map {
  width: 100%;
  height: 320px;
  border-radius: 18px;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}



@media (max-width: 1024px) {
  .contact-card {
    grid-template-columns: 1fr;
  }

  .contact-image-side {
    height: 250px;
  }
}

@media (max-width: 900px) {
  .hero-grid {
    grid-template-columns: 1fr;
    text-align: center;
  }

  .hero-text {
    margin: 0 auto 24px;
  }

  .hero-actions,
  .hero-stats {
    justify-content: center;
  }
}

@media (max-width: 768px) {
  .contact-form-side {
    padding: 30px 20px;
  }

  .contact-title {
    font-size: 32px;
    text-align: center;
  }

  .contact-text {
    text-align: center;
    margin-bottom: 25px;
  }

  .contact-row {
    grid-template-columns: 1fr;
    gap: 18px;
  }

  .contact-btn {
    width: 100%;
  }

  .location-border {
    border-radius: 20px;
    padding: 15px;
  }

  .location-text {
    margin: 10px 0;
    text-align: center;
    font-size: 22px;
  }

  .location-top {
    flex-direction: column;
    text-align: center;
    margin: 0 0 20px 0;
    gap: 16px;
  }

  .location-image {
    width: 100%;
    height: 160px;
  }

  .location-map {
    height: 240px;
  }

  .gallery-carousel {
    height: 220px;
  }
}
</style>