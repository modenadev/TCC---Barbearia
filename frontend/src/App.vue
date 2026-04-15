<template>
  <div>
    <nav class="navbar">
      <h1>RD Barbearia</h1>
      <div class="links">
        <router-link to="/">Home</router-link>
        <router-link to="/servicos">Serviços</router-link>
        <router-link to="/agendamento">Agendamento</router-link>
      </div>
    </nav>

    <main class="container">
      <router-view />
    </main>
  </div>
</template>

<style>
body {
  margin: 0;
  font-family: Arial, sans-serif;
  background: #111;
  color: #fff;
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 32px;
  background: #1a1a1a;
  border-bottom: 1px solid #333;
}

.links a {
  margin-left: 16px;
  color: #fff;
  text-decoration: none;
}

.links a:hover {
  color: #d4af37;
}

.container {
  padding: 32px;
}
</style>