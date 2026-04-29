<template>
  <div>
    <header class="navbar">
      <div class="container navbar-content">

        <div class="brand">
          <img src="/src/assets/logo.png" alt="RD Barbearia" class="logo-img" />
          <div>
          </div>
        </div>

        <nav class="nav-links">
          <router-link to="/">Home</router-link>
          <router-link to="/servicos">Serviços</router-link>
          <router-link to="/agendamento">Agendar</router-link>
          <router-link v-if="!usuarioLogado.token" to="/cadastro" class="login-link"> Cadastro </router-link>
        </nav>

        <div class="nav-right">
          <router-link v-if="!usuarioLogado.token" to="/login" class="login-link">
            Login
          </router-link>

          <router-link v-if="!usuarioLogado.token" to="/cadastro" class="login-link">
            Cadastro
          </router-link>

          <router-link v-if="usuarioLogado.token && usuarioLogado.perfil === 'ADMIN'" to="/admin" class="login-link">
            Painel
          </router-link>

          <router-link v-if="usuarioLogado.token && usuarioLogado.perfil === 'CLIENTE'" to="/cliente"
            class="login-link">
            Minha área
          </router-link>

          <button v-if="usuarioLogado.token" class="logout-btn" @click="logout">
            Sair
          </button>

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

    <footer class="footer">
      <div class="container footer-content">

        <div class="footer-brand">
          <img src="/src/assets/logo.png" alt="RD Barbearia" class="footer-logo" />

          <p>
            Experiência premium em cortes, barba e cuidados masculinos.
            Agende seu horário online com praticidade.
          </p>

          <div class="footer-social">
            <a href="https://instagram.com" target="_blank">
              <Instagram :size="20" />
            </a>

            <a href="https://facebook.com" target="_blank">
              <Facebook :size="20" />
            </a>
          </div>
        </div>

        <div class="footer-links">
          <h4>Navegação</h4>

          <router-link to="/">Home</router-link>
          <router-link to="/servicos">Serviços</router-link>
          <router-link to="/agendamento">Agendar</router-link>
        </div>

        <div class="footer-contact">
          <h4>Contato</h4>

          <p>📍 Mogi Mirim - SP</p>
          <p>📞 (19) 99999-9999</p>
          <p>✉ contato@rdbarbearia.com</p>
        </div>

      </div>

      <div class="footer-bottom">
        © 2026 RD Barbearia — Todos os direitos reservados.
      </div>
    </footer>
  </div>
</template>

<script setup>
import { Instagram, Facebook } from 'lucide-vue-next'
import { useRouter } from 'vue-router'


import { limparAuth } from './utils/auth'
import { usuarioLogado, atualizarUsuarioLogado } from './utils/authState'

const router = useRouter()

const logout = () => {
  limparAuth()
  atualizarUsuarioLogado()
  router.push('/login')
}

</script>

<style scoped>
.login-link {
  color: var(--text-soft);
  font-weight: 600;
}

.logout-btn {
  background: transparent;
  border: none;
  color: var(--text-soft);
  cursor: pointer;
  font: inherit;
}

.logout-btn:hover,
.login-link:hover {
  color: var(--primary);
}

.navbar {
  position: sticky;
  top: 0;
  z-index: 1000;

  background: rgba(32, 32, 32, 0.521);

  backdrop-filter: blur(14px);
  -webkit-backdrop-filter: blur(14px);

  border-bottom: 1px solid rgba(49, 43, 43, 0.04);
}

.navbar::before {
  content: "";

  position: absolute;

  inset: 0;

  background:
    linear-gradient(90deg,
      rgba(212, 175, 55, 0.08),
      transparent,
      rgba(212, 175, 55, 0.05));

  pointer-events: none;
}

.navbar-content {
  height: 85px;
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  align-items: center;
}


.brand {
  display: flex;
  align-items: center;
  margin: 0 0 0 250px;
  gap: 12px;
}


.logo-img {
  height: 105px;


  width: auto;

  object-fit: contain;

  display: block;

  position: relative;

  z-index: 10;

  transform: translateY(30px);

  filter: drop-shadow(0 10px 18px rgba(0, 0, 0, 0.35));
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

.nav-right::before {
  content: "";
  width: 1px;
  height: 24px;
  background: rgb(255, 238, 4);
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

.footer {
  margin-top: 100px;

  background:
    linear-gradient(180deg,
      rgba(255, 255, 255, 0.02),
      rgba(255, 255, 255, 0.01));

  border-top: 1px solid rgba(255, 255, 255, 0.06);

  backdrop-filter: blur(12px);
}

.footer-content {
  padding: 60px 0;

  display: grid;

  grid-template-columns:
    1.4fr 1fr 1fr;

  gap: 40px;
}

.footer-logo {
  width: 120px;

  margin-bottom: 18px;

  object-fit: contain;
}

.footer-brand p {
  color: var(--text-soft);

  line-height: 1.7;

  max-width: 320px;
}

.footer-social {
  display: flex;

  gap: 14px;

  margin-top: 20px;
}

.footer-social a {
  width: 42px;
  height: 42px;

  border-radius: 12px;

  display: flex;
  align-items: center;
  justify-content: center;

  background: rgba(255, 255, 255, 0.04);

  border: 1px solid rgba(255, 255, 255, 0.06);

  color: var(--text-soft);

  transition: 0.3s;
}

.footer-social a:hover {
  color: var(--primary);

  border-color: rgba(212, 175, 55, 0.35);

  transform: translateY(-3px);
}

.footer-links,
.footer-contact {
  display: flex;
  flex-direction: column;
}

.footer-links h4,
.footer-contact h4 {
  margin-top: 0;
  margin-bottom: 20px;

  font-family: 'Poppins', sans-serif;

  font-size: 18px;
}

.footer-links a,
.footer-contact p {
  color: var(--text-soft);

  margin-bottom: 14px;

  transition: 0.3s;
}

.footer-links a:hover {
  color: var(--primary);

  transform: translateX(4px);
}

.footer-bottom {
  border-top: 1px solid rgba(255, 255, 255, 0.06);

  padding: 22px;

  text-align: center;

  color: var(--text-soft);

  font-size: 14px;
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

