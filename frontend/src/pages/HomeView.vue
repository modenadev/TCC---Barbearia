<template>
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
</template>

<style scoped>
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
  background: rgba(212, 175, 55, 0.12);
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
    linear-gradient(180deg, rgba(0,0,0,0.05), rgba(0,0,0,0.35)),
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

@media (max-width: 900px) {
  .hero-grid {
    grid-template-columns: 1fr;
  }
}
</style>