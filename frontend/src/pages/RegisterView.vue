<template>
  <section class="section">
    <div class="container register-wrapper">
      <div class="card register-card">
        <h1 class="section-title">Criar conta</h1>
        <p class="section-subtitle">
          Cadastre-se para agendar seus horários.
        </p>

        <form @submit.prevent="cadastrar">
          <div class="field">
            <label>Nome</label>
            <input v-model="nome" class="input" required />
          </div>

          <div class="field">
            <label>E-mail</label>
            <input v-model="email" type="email" class="input" required />
          </div>

          <div class="field">
            <label>Senha</label>
            <input v-model="senha" type="password" class="input" required />
          </div>

          <button class="btn btn-primary full" type="submit" :disabled="loading">
            {{ loading ? 'Criando conta...' : 'Criar conta' }}
          </button>

          <p v-if="erro" class="erro">{{ erro }}</p>
        </form>

        <p class="login-text">
          Já tem conta?
          <router-link to="/login">Entrar</router-link>
        </p>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../services/api'
import { salvarAuth } from '../utils/auth'

const router = useRouter()

const nome = ref('')
const email = ref('')
const senha = ref('')
const erro = ref('')
const loading = ref(false)

const cadastrar = async () => {
  erro.value = ''
  loading.value = true

  try {
    const response = await api.post('/auth/register-cliente', {
      nome: nome.value,
      email: email.value,
      senha: senha.value
    })

    salvarAuth(response.data)
    router.push('/cliente')
  } catch (e) {
    erro.value = 'Erro ao criar conta. Verifique os dados.'
    console.error(e)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-wrapper {
  min-height: 70vh;
  display: grid;
  place-items: center;
}

.register-card {
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

.login-text {
  margin-top: 20px;
  color: var(--text-soft);
  text-align: center;
}

.login-text a {
  color: var(--primary);
  font-weight: 600;
}
</style>