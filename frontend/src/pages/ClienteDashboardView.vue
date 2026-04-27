<template>
  <section class="section">
    <div class="container">
      <h1 class="section-title">Área do Cliente</h1>
      <p class="section-subtitle">
        Bem-vindo, {{ nome }}.
      </p>

      <div class="card area-card">
        <h3>Seus próximos agendamentos</h3>
        <p>Aqui depois vamos listar os horários marcados do cliente.</p>
      </div>
    </div>
  </section>
</template>

<script setup>
import { getNome } from '../utils/auth'

const nome = getNome()
</script>

<style scoped>
.area-card {
  padding: 24px;
}
</style>