<template>
  <div id="app">
    <nav class="navbar">
      <div class="nav-container">
        <router-link to="/" class="nav-logo-wrapper">
          <img src="/src/assets/logo.png" alt="RD Barbearia" class="nav-logo-img" />
          <span class="nav-logo-text">RD Barbearia</span>
        </router-link>

        <button class="menu-toggle" @click="menuAberto = !menuAberto" :class="{ 'is-active': menuAberto }">
          <span class="bar"></span>
          <span class="bar"></span>
          <span class="bar"></span>
        </button>

        <ul class="nav-links" :class="{ 'open': menuAberto }">
          <li><router-link to="/" @click="fecharMenu">Início</router-link></li>
          <li><router-link to="/servicos" @click="fecharMenu">Serviços</router-link></li>
          <li><router-link to="/agendamento" @click="fecharMenu">Agendar</router-link></li>

          <li v-if="!estaLogado">
            <router-link to="/cadastro" @click="fecharMenu">Criar Conta</router-link>
          </li>

          <li v-if="estaLogado && perfilUsuario === 'CLIENTE'">
            <router-link to="/cliente" @click="fecharMenu">Minha Área</router-link>
          </li>
          <li v-if="estaLogado && perfilUsuario === 'ADMIN'">
            <router-link to="/admin" @click="fecharMenu" class="nav-admin">Painel Admin</router-link>
          </li>



          <li v-if="estaLogado && perfilUsuario === 'ADMIN'" class="nav-notificacoes-container">
            <button class="btn-sininho" @click="abaNotificacoesAberta = !abaNotificacoesAberta">
              🔔
              <span v-if="notificacoes.length > 0" class="badge-notificacoes">
                {{ notificacoes.length }}
              </span>
            </button>

            <div v-if="abaNotificacoesAberta" class="notificacoes-dropdown">
              <div class="dropdown-header">
                <span>Logs do Sistema</span>
                <button v-if="notificacoes.length > 0" class="btn-clear-all" @click="marcarTodasComoLidas">Limpar
                  todas</button>
              </div>

              <ul v-if="notificacoes.length > 0" class="dropdown-list">
                <li v-for="notificacao in notificacoes" :key="notificacao.id" class="dropdown-item">
                  <p class="notificacao-msg">{{ notificacao.mensagem }}</p>
                  <button class="btn-ler-notificacao" @click="limparNotificacao(notificacao.id)">✕</button>
                </li>
              </ul>
              <div v-else class="dropdown-empty">
                <p>Nenhuma nova atualização.</p>
              </div>
            </div>
          </li>

          <li v-if="estaLogado" class="nav-profile-container">
            <div class="profile-trigger" @click="menuPerfilAberto = !menuPerfilAberto">
              <div class="avatar-circle">
                {{ nomeUsuario.charAt(0).toUpperCase() || 'U' }}
              </div>
              <span class="user-greeting">Olá, {{ nomeUsuario }}</span>
              <svg class="chevron" :class="{ 'rotate': menuPerfilAberto }" xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"
                stroke-linejoin="round">
                <polyline points="6 9 12 15 18 9"></polyline>
              </svg>
            </div>

            <div v-if="menuPerfilAberto" class="profile-dropdown">
              <router-link to="/perfil" @click="fecharMenu" class="dropdown-item">⚙️ Meus Dados</router-link>
              <button @click="fazerLogout" class="dropdown-item logout-item">🚪 Sair</button>
            </div>
          </li>

          <li v-else class="nav-auth-item">
            <router-link to="/login" @click="fecharMenu" class="btn-auth login">
              Entrar
            </router-link>
          </li>


        </ul>
      </div>
    </nav>

    <main class="main-content">
      <router-view />
    </main>

    <footer class="main-footer">
      <div class="footer-container">
        <div class="footer-grid">

          <div class="footer-block brand">
            <h3>RD Barbearia</h3>
            <p>O corte perfeito com a praticidade que o seu dia a dia exige. Estilo premium e atendimento qualificado.
            </p>

            <div class="footer-socials">
              <a href="https://wa.me/5519999762700" target="_blank" aria-label="WhatsApp" class="social-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                  <path
                    d="M17.472 14.382c-.297-.149-1.758-.867-2.03-.967-.273-.099-.471-.148-.67.15-.197.297-.767.966-.94 1.164-.173.199-.347.223-.644.075-.297-.15-1.255-.463-2.39-1.475-.883-.788-1.48-1.761-1.653-2.059-.173-.297-.018-.458.13-.606.134-.133.298-.347.446-.52.149-.174.198-.298.298-.497.099-.198.05-.371-.025-.52-.075-.149-.669-1.612-.916-2.207-.242-.579-.487-.5-.669-.51-.173-.008-.371-.01-.57-.01-.198 0-.52.074-.792.372-.272.297-1.04 1.016-1.04 2.479 0 1.462 1.065 2.875 1.213 3.074.149.198 2.096 3.2 5.077 4.487.709.306 1.262.489 1.694.625.712.227 1.36.195 1.871.118.571-.085 1.758-.719 2.006-1.413.248-.694.248-1.289.173-1.413-.074-.124-.272-.198-.57-.347m-5.421 7.403h-.004a9.87 9.87 0 01-5.031-1.378l-.361-.214-3.741.982.998-3.648-.235-.374a9.86 9.86 0 01-1.51-5.26c.001-5.45 4.436-9.884 9.888-9.884 2.64 0 5.122 1.03 6.988 2.898a9.825 9.825 0 012.893 6.994c-.003 5.45-4.437 9.884-9.885 9.884m8.413-18.297A11.815 11.815 0 0012.05 0C5.495 0 .16 5.335.157 11.892c0 2.096.547 4.142 1.588 5.945L0 24l6.335-1.662c1.746.953 3.71 1.456 5.704 1.456h.005c6.554 0 11.89-5.335 11.893-11.893a11.821 11.821 0 00-3.48-8.413z" />
                </svg>
              </a>

              <a href="https://instagram.com" target="_blank" aria-label="Instagram" class="social-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                  <path
                    d="M12 2.163c3.204 0 3.584.012 4.85.07 3.252.148 4.771 1.691 4.919 4.919.058 1.265.069 1.645.069 4.849 0 3.205-.012 3.584-.069 4.849-.149 3.225-1.664 4.771-4.919 4.919-1.266.058-1.644.07-4.85.07-3.204 0-3.584-.012-4.849-.07-3.26-.149-4.771-1.699-4.919-4.92-.058-1.265-.07-1.644-.07-4.849 0-3.204.013-3.583.07-4.849.149-3.227 1.664-4.771 4.919-4.919 1.266-.057 1.645-.069 4.849-.069zM12 0C8.741 0 8.333.014 7.053.072 2.695.272.273 2.69.073 7.051.014 8.333 0 8.741 0 12c0 3.259.014 3.668.072 4.948.2 4.358 2.618 6.78 6.98 6.98 1.281.058 1.689.072 4.948.072 3.259 0 3.668-.014 4.948-.072 4.354-.2 6.782-2.618 6.979-6.98.059-1.28.073-1.689.073-4.948 0-3.259-.014-3.667-.072-4.947-.196-4.354-2.617-6.78-6.979-6.98C15.668.014 15.259 0 12 0zm0 5.838a6.162 6.162 0 100 12.324 6.162 6.162 0 000-12.324zM12 16a4 4 0 110-8 4 4 0 010 8zm6.406-11.845a1.44 1.44 0 100 2.881 1.44 1.44 0 000-2.881z" />
                </svg>
              </a>

              <a href="https://facebook.com" target="_blank" aria-label="Facebook" class="social-icon">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
                  <path
                    d="M24 12.073c0-6.627-5.373-12-12-12s-12 5.373-12 12c0 5.99 4.388 10.954 10.125 11.854v-8.385H7.078v-3.47h3.047V9.43c0-3.007 1.792-4.669 4.533-4.669 1.312 0 2.686.235 2.686.235v2.953H15.83c-1.491 0-1.956.925-1.956 1.874v2.25h3.328l-.532 3.47h-2.796v8.385C19.612 23.027 24 18.062 24 12.073z" />
                </svg>
              </a>
            </div>
          </div>

          <div class="footer-block links">
            <h4>Navegação</h4>
            <ul>
              <li><router-link to="/" @click="fecharMenu">Início</router-link></li>
              <li><router-link to="/servicos" @click="fecharMenu">Serviços</router-link></li>
              <li><router-link to="/agendamento" @click="fecharMenu">Agendar Horário</router-link></li>
              <li>
                <router-link v-if="!estaLogado" to="/cadastro" @click="fecharMenu">Criar Conta</router-link>
                <router-link v-else-if="perfilUsuario === 'ADMIN'" to="/admin" @click="fecharMenu">Painel
                  Admin</router-link>
                <router-link v-else to="/cliente" @click="fecharMenu">Minha Área</router-link>
              </li>
            </ul>
          </div>

          <div class="footer-block contact">
            <h4>Contato</h4>
            <p>📞 (19) 99980-6091</p>
            <p>📍 Mogi Mirim, SP</p>
          </div>

        </div>

        <div class="footer-bottom">
          <p>&copy; 2026 RD Barbearia. Todos os direitos reservados.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue'
import api from './services/api'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const menuAberto = ref(false)
const estaLogado = ref(false)
const perfilUsuario = ref('')
const abaNotificacoesAberta = ref(false)

const notificacoes = ref([])
let intervaloNotificacoes = null

const menuPerfilAberto = ref(false)
const nomeUsuario = ref('Cliente') 

const fecharMenu = () => {
  menuAberto.value = false
  abaNotificacoesAberta.value = false
  menuPerfilAberto.value = false 
}

const checarStatusLogin = () => {
  estaLogado.value = !!localStorage.getItem('token')
  perfilUsuario.value = localStorage.getItem('perfil') || ''

  const nomeSalvo = localStorage.getItem('nome')
  if (nomeSalvo) {

    nomeUsuario.value = nomeSalvo.split(' ')[0]
  } else {
    nomeUsuario.value = perfilUsuario.value === 'ADMIN' ? 'Admin' : 'Cliente'
  }

  gerenciarTemporizadorNotificacoes()
}


const gerenciarTemporizadorNotificacoes = () => {

  if (intervaloNotificacoes) {
    clearInterval(intervaloNotificacoes)
    intervaloNotificacoes = null
  }


  if (estaLogado.value && perfilUsuario.value === 'ADMIN') {
    buscarNotificacoesAdmin()
    intervaloNotificacoes = setInterval(buscarNotificacoesAdmin, 10000)
  } else {
    notificacoes.value = []
  }
}

const fazerLogout = () => {
  localStorage.clear()
  checarStatusLogin()
  fecharMenu()
  router.push('/')
}

const buscarNotificacoesAdmin = async () => {
  try {
    const response = await api.get('http://localhost:8080/api/notificacoes/nao-lidas')
    notificacoes.value = response.data
  } catch (error) {
    console.error("Erro ao buscar logs/notificações", error)
  }
}

const limparNotificacao = async (id) => {
  try {
    await api.patch(`http://localhost:8080/api/notificacoes/${id}/marcar-como-lida`)
    notificacoes.value = notificacoes.value.filter(n => n.id !== id)
  } catch (error) {
    console.error(error)
  }
}



const marcarTodasComoLidas = async () => {
  try {
    await api.patch('http://localhost:8080/api/notificacoes/marcar-todas-como-lidas')
    notificacoes.value = []
  } catch (error) {
    console.error(error)
  }
}
watch(() => route.path, () => {
  checarStatusLogin()
  abaNotificacoesAberta.value = false
})

onMounted(() => {
  checarStatusLogin()
})

onUnmounted(() => {
  if (intervaloNotificacoes) {
    clearInterval(intervaloNotificacoes)
  }
})
</script>

<style>
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 75px;
  background-color: #0d0d0e71;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  z-index: 1000;
  backdrop-filter: blur(8px);
}

.nav-container {
  max-width: 1200px;
  height: 100%;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-logo-wrapper {
  display: flex;
  align-items: center;
  gap: 12px;
  text-decoration: none;
  height: 75px;
}

.nav-logo-img {
  height: 105px;
  width: auto;
  object-fit: contain;
  display: block;
  position: relative;
  z-index: 10;
  transform: translateY(30px);
  filter: drop-shadow(0 10px 18px rgba(0, 0, 0, 0.35));
}

.nav-logo-text {
  font-family: 'Poppins', sans-serif;
  color: #fff;
  font-weight: 700;
  font-size: 20px;
  white-space: nowrap;
}

.nav-links {
  display: flex;
  gap: 24px;
  list-style: none;
  margin: 0;
  padding: 0;
  align-items: center;
}

.nav-links a {
  color: rgba(255, 255, 255, 0.8);
  text-decoration: none;
  font-weight: 500;
  font-size: 15px;
  transition: 0.3s;
}

.nav-links a:hover,
.nav-links a.router-link-active {
  color: var(--primary, #facc15);
}

.nav-admin {
  border: 1px solid rgba(255, 255, 255, 0.15);
  padding: 6px 12px;
  border-radius: 8px;
}

.btn-auth {
  background: transparent;
  border: none;
  font-family: 'Poppins', sans-serif;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  padding: 8px 18px;
  border-radius: 8px;
  transition: 0.3s;
  text-decoration: none;
  display: inline-block;
}

.btn-auth.login {
  background-color: var(--primary, #facc15);
  color: #111 !important;
}

.btn-auth.login:hover {
  filter: brightness(1.1);
  transform: translateY(-1px);
}

.btn-auth.logout {
  color: #ef4444;
  border: 1px solid rgba(239, 68, 68, 0.3);
}

.btn-auth.logout:hover {
  background-color: rgba(239, 68, 68, 0.1);
  border-color: #ef4444;
}

.main-content {
  padding-top: 75px;
  min-height: calc(100vh - 75px - 250px);
}


.nav-notificacoes-container {
  position: relative;
  display: flex;
  align-items: center;
}

.btn-sininho {
  background: transparent;
  border: none;
  font-size: 20px;
  cursor: pointer;
  position: relative;
  padding: 6px;
  transition: transform 0.2s;
}

.btn-sininho:hover {
  transform: scale(1.15);
}

.badge-notificacoes {
  position: absolute;
  top: -2px;
  right: -2px;
  background-color: #ef4444;
  color: white;
  font-size: 11px;
  font-weight: 700;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  display: grid;
  place-items: center;
  border: 2px solid #111112;
}

.notificacoes-dropdown {
  position: absolute;
  top: 50px;
  right: -50px;
  background-color: #161618;
  border: 1px solid rgba(255, 255, 255, 0.08);
  width: 340px;
  max-height: 400px;
  border-radius: 14px;
  box-shadow: 0 15px 40px rgba(0, 0, 0, 0.5);
  z-index: 2000;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.dropdown-header {
  padding: 14px 16px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-family: 'Poppins', sans-serif;
  font-size: 14px;
  font-weight: 600;
  color: #fff;
}

.btn-clear-all {
  background: transparent;
  border: none;
  color: var(--primary, #facc15);
  font-size: 12px;
  cursor: pointer;
}

.dropdown-list {
  list-style: none;
  margin: 0;
  padding: 0;
  overflow-y: auto;
  max-height: 320px;
}

.dropdown-item {
  padding: 14px 16px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.03);
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 12px;
  transition: background 0.2s;
}

.dropdown-item:hover {
  background: rgba(255, 255, 255, 0.02);
}

.notificacao-msg {
  margin: 0;
  font-size: 13px;
  color: #d1d5db;
  line-height: 1.5;
  text-align: left;
}

.btn-ler-notificacao {
  background: transparent;
  border: none;
  color: #6b7280;
  cursor: pointer;
  font-size: 12px;
  padding: 2px 4px;
}

.btn-ler-notificacao:hover {
  color: #ef4444;
}

.dropdown-empty {
  padding: 30px 16px;
  text-align: center;
  color: #6b7280;
  font-size: 13px;
}


.menu-toggle {
  display: none;
  flex-direction: column;
  justify-content: space-between;
  width: 28px;
  height: 20px;
  background: transparent;
  border: none;
  cursor: pointer;
  padding: 0;
  z-index: 1001;
}

.menu-toggle .bar {
  width: 100%;
  height: 3px;
  background-color: #fff;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.main-footer {
  background-color: #0b0b0c;
  border-top: 1px solid rgba(255, 255, 255, 0.05);
  padding: 50px 0 25px 0;
  width: 100%;
  box-sizing: border-box;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  width: 100%;
  box-sizing: border-box;
}

.footer-grid {
  display: grid;
  grid-template-columns: 1.5fr 1fr 1fr;
  gap: 40px;
  margin-bottom: 40px;
}

.footer-block h3,
.footer-block h4 {
  font-family: 'Poppins', sans-serif;
  color: #fff;
  margin: 0 0 16px 0;
}

.footer-block h3 {
  font-size: 22px;
  font-weight: 700;
}

.footer-block h4 {
  font-size: 16px;
  font-weight: 600;
  color: var(--primary, #facc15);
}

.footer-block p {
  color: #9ca3af;
  font-size: 14px;
  line-height: 1.6;
  margin: 0;
}

.footer-block.brand p {
  max-width: 320px;
}

.footer-block ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.footer-block li {
  margin-bottom: 12px;
}

.footer-block a {
  color: #9ca3af;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.2s;
}

.footer-block a:hover {
  color: var(--primary, #facc15);
}

.footer-block.contact p {
  margin-bottom: 10px;
}

.footer-bottom {
  border-top: 1px solid rgba(255, 255, 255, 0.06);
  padding-top: 20px;
  text-align: center;
}

.footer-bottom p {
  color: #6b7280;
  font-size: 13px;
  margin: 0;
}

.footer-socials {
  display: flex;
  gap: 16px;
  margin-top: 20px;
}

.social-icon {
  color: #9ca3af;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.08);
  transition: all 0.3s ease;
}

.social-icon svg {
  width: 20px;
  height: 20px;
}

.social-icon:hover {
  color: var(--primary, #facc15);
  background-color: rgba(250, 204, 21, 0.1);
  border-color: var(--primary, #facc15);
  transform: translateY(-3px);
}


/* ==========================================
   MENU DE PERFIL (PÍLULA)
   ========================================== */
.nav-profile-container {
  position: relative;
  margin-left: 10px;
}

.profile-trigger {
  display: flex;
  align-items: center;
  gap: 10px;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  padding: 6px 16px 6px 6px;
  border-radius: 50px;
  cursor: pointer;
  transition: all 0.3s ease;
  user-select: none;
}

.profile-trigger:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: rgba(250, 204, 21, 0.3);
}

.avatar-circle {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: var(--primary, #facc15);
  color: #111;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  font-size: 14px;
}

.user-greeting {
  color: #fff;
  font-size: 14px;
  font-weight: 500;
  font-family: 'Poppins', sans-serif;
}

.chevron {
  width: 16px;
  height: 16px;
  color: #9ca3af;
  transition: transform 0.3s ease;
}

.chevron.rotate {
  transform: rotate(180deg);
}

.profile-dropdown {
  position: absolute;
  top: 60px;
  right: 0;
  background-color: #161618;
  border: 1px solid rgba(255, 255, 255, 0.08);
  width: 200px;
  border-radius: 14px;
  box-shadow: 0 15px 40px rgba(0, 0, 0, 0.5);
  z-index: 2000;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.dropdown-item {
  padding: 14px 20px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.03);
  color: #d1d5db;
  text-decoration: none;
  font-size: 14px;
  display: flex;
  align-items: center;
  gap: 10px;
  transition: background 0.2s;
  background: transparent;
  width: 100%;
  text-align: left;
  font-family: 'Poppins', sans-serif;
  cursor: pointer;
}

.dropdown-item:hover {
  background: rgba(255, 255, 255, 0.05);
  color: var(--primary, #facc15);
}

.logout-item {
  border-bottom: none;
  color: #ef4444;
}

.logout-item:hover {
  background: rgba(239, 68, 68, 0.1);
  color: #ef4444;
}

@media (max-width: 768px) {
  .menu-toggle {
    display: flex;
  }

  .nav-logo-text {
    display: none;
  }

  .nav-logo-img {
    height: 48px;
  }

  .nav-links {
    position: fixed;
    top: 75px;
    left: 0;
    width: 100%;
    background-color: #111112;
    flex-direction: column;
    align-items: center;
    padding: 40px 0;
    gap: 30px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.5);
    border-bottom: 1px solid rgba(255, 255, 255, 0.08);
    transform: translateY(-160%);
    transition: transform 0.4s ease-in-out;
    opacity: 0;
    visibility: hidden;
  }

  .nav-links.open {
    transform: translateY(0);
    opacity: 1;
    visibility: visible;
  }

  .nav-links a {
    font-size: 18px;
    width: 100%;
    text-align: center;
    display: block;
  }

  .nav-auth-item {
    width: 100%;
    display: flex;
    justify-content: center;
    padding: 0 20px;
    box-sizing: border-box;
  }

  .btn-auth {
    width: 80%;
    text-align: center;
    font-size: 18px;
    padding: 12px 0;
  }

  .notificacoes-dropdown {
    position: fixed;
    top: 75px;
    left: 0;
    width: 100%;
    right: 0;
    border-radius: 0 0 14px 14px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.5);
  }

  .menu-toggle.is-active .bar:nth-child(1) {
    transform: translateY(8px) rotate(45deg);
    background-color: var(--primary, #facc15);
  }

  .menu-toggle.is-active .bar:nth-child(2) {
    opacity: 0;
  }

  .menu-toggle.is-active .bar:nth-child(3) {
    transform: translateY(-9px) rotate(-45deg);
    background-color: var(--primary, #fa8f15);
  }

  .main-footer {
    padding: 40px 0 20px 0;
    text-align: center;
  }

  .footer-grid {
    grid-template-columns: 1fr;
    gap: 32px;
    margin-bottom: 30px;
  }

  .footer-block.brand p {
    margin: 0 auto;
  }

  .footer-socials {
    justify-content: center;
    margin-bottom: 10px;
  }
}
</style>