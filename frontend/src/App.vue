<template>
  <div>
    <header class="navbar">
      <div class="container navbar-content">

        <div class="brand">
          <img src="/src/assets/logo.jpeg" alt="RD Barbearia" class="logo-img" />
          <div>
            <strong>RD Barbearia</strong>
          </div>
        </div>

        <nav class="nav-links">
          <router-link to="/">Home</router-link>
          <router-link to="/servicos">Serviços</router-link>
          <router-link to="/agendamento">Agendar</router-link>
        </nav>

        <div class="nav-right">
          <a href="https://instagram.com" target="_blank" class="icon">
            <Instagram :size="20" />
          </a>
          <a href="https://facebook.com" target="_blank" class="icon">
            <Facebook :size="20" />
          </a>
        </div>

      </div>
    </header>

    <main>
      <router-view />
    </main>
  </div>
</template>

<style scoped>
.navbar {
  position: sticky;
  top: 0;
  z-index: 1000;
  background: rgba(71, 71, 71, 0.432);
  backdrop-filter: blur(14px);
  -webkit-backdrop-filter: blur(14px);
}

.navbar-content {
  height: 100px;
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  align-items: center;
}


.brand {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-img {
  height: 55px;
  width: auto;
  object-fit: contain;
  display: block;
}

.brand-logo {
  width: 42px;
  height: 42px;
  border-radius: 14px;
  display: grid;
  place-items: center;
  background: linear-gradient(135deg, var(--primary), #f6d365);
  color: #111;
  font-family: 'Poppins', sans-serif;
  font-weight: 700;
}

.brand strong {
  display: block;
  font-family: 'Poppins', sans-serif;
}

.brand span {
  display: block;
  font-size: 13px;
  color: var(--text-soft);
}

.nav-links {
  display: flex;
  gap: 24px;
}

.nav-links a {
  color: var(--text-soft);
  font-weight: 500;
  transition: 0.2s ease;

  display: flex;
  justify-content: center;
  gap: 40px;
}

.nav-links a {
  color: var(--text-soft);
  font-weight: 600;
  letter-spacing: 1px;
  font-size: 14px;
  text-transform: uppercase;
  transition: 0.3s;
}

.nav-links a:hover {
  color: var(--primary);
}

.nav-right {
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  align-items: center;
}

.icon {
  color: var(--text-soft);
  transition: 0.3s;
}

.icon:hover {
  color: var(--primary);
  transform: translateY(-2px);
}


.nav-links a:hover,
.nav-links a.router-link-exact-active {
  color: var(--text);
}

@media (max-width: 768px) {
  .nav-links {
    gap: 14px;
    font-size: 14px;
  }

  .brand span {
    display: none;
  }

  .brand {
    display: flex;
    align-items: center;
    gap: 12px;
  }
}
</style>

<script setup>
import { Instagram, Facebook } from 'lucide-vue-next'

</script>