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

</template>

<script>
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

  background:
    linear-gradient(180deg,
      rgba(0, 0, 0, 0.45),
      rgba(0, 0, 0, 0.65));

  backdrop-filter: blur(1px);
}

.banner-content {
  position: relative;

  z-index: 2;

  text-align: center;

  padding: 20px;
}

.banner-logo {
  width: 600px;

  max-width: 90vw;

  margin-bottom: 12px;

  filter:
    drop-shadow(0 10px 25px rgba(0, 0, 0, 0.45));
}

.banner-content h1 {
  font-family: 'Poppins', sans-serif;

  font-size: clamp(42px, 7vw, 82px);

  margin: 0;

  font-weight: 800;

  letter-spacing: 2px;
}

.banner-content p {
  color: rgba(255, 255, 255, 0.8);

  font-size: 18px;

  margin: 16px 0 32px;
}

.banner-btn {
  display: inline-flex;

  align-items: center;
  justify-content: center;

  height: 56px;

  padding: 0 32px;

  border-radius: 16px;

  background: var(--primary);

  color: #111;

  font-weight: 700;

  transition: 0.3s;
}

.banner-btn:hover {
  transform: translateY(-3px);

  filter: brightness(1.05);
}

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
  color: yellow;

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

.gallery-carousel::after {
  content: "";
  position: absolute;
  inset: 0;
  background: linear-gradient(180deg,
      rgba(0, 0, 0, 0.05),
      rgba(0, 0, 0, 0.35));
  pointer-events: none;
}

.gallery-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 2;
  width: 44px;
  height: 44px;
  border-radius: 999px;
  border: 1px solid rgba(255, 255, 255, 0.16);
  background: rgba(0, 0, 0, 0.45);
  color: white;
  font-size: 34px;
  cursor: pointer;
  display: grid;
  place-items: center;
  transition: 0.25s;
}

.gallery-btn:hover {
  background: var(--primary);
  color: #111;
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
  cursor: pointer;
}

.gallery-dots button.active {
  width: 28px;
  background: var(--primary);
}

@media (max-width: 768px) {
  .gallery-carousel {
    height: 240px;
  }

  .gallery-title {
    font-size: 22px;
  }
}

.hero {
  padding: 56px 0 40px;
}

.hero-grid {
  display: grid;
  grid-template-columns: 1.1fr 0.9fr;
  gap: 28px;
  align-items: center;
}

.badge {
  display: inline-block;
  padding: 8px 14px;
  border-radius: 999px;
  border: 1px solid rgba(212, 175, 55, 0.18);
  color: var(--primary);
  font-size: 14px;
  margin-bottom: 18px;
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
  background:
    linear-gradient(180deg, rgba(0, 0, 0, 0.05), rgba(0, 0, 0, 0.35)),
    url('https://images.unsplash.com/photo-1621605815971-fbc98d665033?auto=format&fit=crop&w=1200&q=80') center/cover;
}

.hero-card-content {
  padding: 22px;
}

.hero-card-content h3 {
  margin-top: 0;
  font-family: 'Poppins', sans-serif;
}

.hero-card-content p {
  color: var(--text-soft);
  line-height: 1.6;
  margin-bottom: 0;
}

.contact-section {
  padding: 90px 0;
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
}

.contact-form-side {
  padding: 70px;
}

.contact-badge {
  display: inline-block;

  color: var(--primary);

  font-size: 13px;

  letter-spacing: 3px;

  font-weight: 700;

  margin-bottom: 20px;
}

.contact-title {
  font-family: 'Poppins', sans-serif;

  font-size: 48px;

  margin-bottom: 16px;

  line-height: 1.1;
}

.contact-text {
  color: var(--text-soft);

  line-height: 1.7;

  margin-bottom: 40px;

  max-width: 500px;
}

.contact-form {
  display: flex;
  flex-direction: column;
  gap: 22px;
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

  transition: 0.3s;
}

.contact-input {
  height: 60px;
  padding: 0 20px;
}

.contact-textarea {
  min-height: 160px;

  padding: 20px;

  resize: none;
}

.contact-input:focus,
.contact-textarea:focus {
  border-color: var(--primary);

  box-shadow: 0 0 0 4px rgba(255, 204, 0, 0.08);
}

.contact-input::placeholder,
.contact-textarea::placeholder {
  color: rgba(255, 255, 255, 0.45);
}

.contact-btn {
  width: fit-content;

  padding: 16px 34px;

  border: none;

  border-radius: 16px;

  background: var(--primary);

  color: #111;

  font-weight: 700;

  font-size: 15px;

  cursor: pointer;

  transition: 0.3s;
}

.contact-btn:hover {
  transform: translateY(-2px);

  filter: brightness(1.05);
}

.contact-image-side {
  position: relative;
  min-height: 100%;
}

.contact-image-side img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.contact-image-side::after {
  content: "";

  position: absolute;

  inset: 0;

  background: linear-gradient(180deg,
      rgba(0, 0, 0, 0.05),
      rgba(0, 0, 0, 0.35));
}

@media (max-width: 1000px) {
  .contact-card {
    grid-template-columns: 1fr;
  }

  .contact-image-side {
    height: 320px;
  }
}

@media (max-width: 768px) {
  .contact-form-side {
    padding: 40px 24px;
  }

  .contact-title {
    font-size: 34px;
  }

  .contact-row {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 900px) {
  .hero-grid {
    grid-template-columns: 1fr;
  }
}
</style>