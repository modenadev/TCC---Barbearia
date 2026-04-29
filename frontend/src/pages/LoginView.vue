<template>
  <section class="section">
    <div class="container login-wrapper">
      <div class="card login-card">
        <h1 class="section-title">Entrar</h1>
        <p class="section-subtitle">
          Faça login para acessar sua área.
        </p>

        <form @submit.prevent="fazerLogin">
          <div class="field">
            <label>E-mail</label>
            <input v-model="email" type="email" class="input" required />
          </div>

          <div class="field">
            <label>Senha</label>
            <input v-model="senha" type="password" class="input" required />
          </div>

          <p class="register-text">
            Ainda não tem conta?
            <router-link to="/cadastro">Criar conta</router-link>
          </p>

          <button class="btn btn-primary full" type="submit" :disabled="loading">
            {{ loading ? 'Entrando...' : 'Entrar' }}
          </button>

          <p v-if="erro" class="erro">{{ erro }}</p>
        </form>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../services/api'
import { salvarAuth } from '../utils/auth'
import { atualizarUsuarioLogado } from '../utils/authState'

const router = useRouter()

const email = ref('')
const senha = ref('')
const erro = ref('')
const loading = ref(false)

const fazerLogin = async () => {
  erro.value = ''
  loading.value = true

  try {
    const response = await api.post('/auth/login', {
      email: email.value,
      senha: senha.value
    })

    localStorage.setItem('token', response.data.token)
    localStorage.setItem('nome', response.data.nome)
    localStorage.setItem('email', response.data.email)
    localStorage.setItem('perfil', response.data.perfil)

    salvarAuth(response.data)
    atualizarUsuarioLogado()

    if (response.data.perfil === 'ADMIN') {
      router.push('/admin')
    } else {
      router.push('/cliente')
    }
  } catch (e) {
    erro.value = 'E-mail ou senha inválidos.'
    console.error(e)
  } finally {
    loading.value = false
  }
}


</script>

<style scoped>
.register-text {
  margin-top: 20px;
  color: var(--text-soft);
  text-align: center;
}

.register-text a {
  color: var(--primary);
  font-weight: 600;
}

.login-wrapper {
  min-height: 70vh;
  display: grid;
  place-items: center;
}

.login-card {
  width: 100%;
  max-width: 460px;
  padding: 28px;
}

.field {
  display: grid;
  gap: 8px;
  margin-bottom: 16px;
}

.full {
  width: 100%;
}

.erro {
  color: #ff6b6b;
  margin-top: 14px;
}
</style>