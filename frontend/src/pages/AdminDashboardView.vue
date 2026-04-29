<template>
  <section class="section">
    <div class="container">
      <h1 class="section-title">Dashboard do Administrador</h1>
      <p class="section-subtitle">
        Bem-vindo, {{ nome }}.
      </p>

      <div class="grid dashboard-grid">
        <div class="card dash-card">
          <h3>Agendamentos hoje</h3>
          <strong>12</strong>
        </div>

        <div class="card dash-card">
          <h3>Faturamento do mês</h3>
          <strong>R$ 2.850,00</strong>
        </div>

        <div class="card dash-card">
          <h3>Cortes no mês</h3>
          <strong>47</strong>
        </div>

        <div class="card dash-card">
          <h3>No-show</h3>
          <strong>3</strong>
        </div>
      </div>
    </div>
  </section>

  <router-link to="/admin/agendamentos" class="btn btn-primary">
    Ver agendamentos
  </router-link>
</template>

<script setup>
import { getNome } from '../utils/auth'

const nome = getNome()
</script>

<style scoped>
.dashboard-grid {
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
}

.dash-card {
  padding: 24px;
}

.btn-primary {
  margin-left: 440px;
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
</style>