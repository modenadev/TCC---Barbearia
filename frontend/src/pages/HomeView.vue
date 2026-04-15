<template>
  <section>
    <h2>Bem-vindo à RD Barbearia</h2>
    <p>Agende seu horário online com facilidade.</p>
    <router-link to="/agendamento">
      <button>Agendar agora</button>
    </router-link>
  </section>
</template>

<style scoped>
button {
  background: #d4af37;
  color: #111;
  border: none;
  padding: 12px 20px;
  border-radius: 8px;
  cursor: pointer;
  font-weight: bold;
}
</style>