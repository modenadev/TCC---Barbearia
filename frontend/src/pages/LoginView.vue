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
            <a href="#" @click.prevent="recuperarSenha" class="link-esqueci-senha">Esqueci minha senha</a>
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
import Swal from 'sweetalert2' 

const router = useRouter()

const email = ref('')
const senha = ref('')
const erro = ref('')
const loading = ref(false)


const swalConfig = {
  background: '#141416',
  color: '#ffffff',
  confirmButtonColor: '#facc15',
}

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

const recuperarSenha = async () => {
  const { value: emailInput } = await Swal.fire({
    ...swalConfig,
    title: 'Recuperar Senha',
    text: 'Digite o e-mail cadastrado na sua conta:',
    input: 'email',
    inputPlaceholder: 'seu@email.com',
    showCancelButton: true,
    cancelButtonText: 'Cancelar',
    confirmButtonText: 'Enviar senha',
    cancelButtonColor: '#ef4444',
    inputValidator: (value) => {
      if (!value) {
        return 'Você precisa digitar um e-mail!'
      }
    }
  })

  if (emailInput) {
    Swal.fire({
      ...swalConfig,
      title: 'Enviando...',
      text: 'Gerando senha provisória e contatando o WhatsApp...',
      allowOutsideClick: false,
      didOpen: () => {
        Swal.showLoading()
      }
    })

    try {
      await api.post('/auth/esqueci-senha', { email: emailInput })

      Swal.fire({
        ...swalConfig,
        icon: 'success',
        title: 'Enviado!',
        text: 'Se o e-mail estiver cadastrado, você receberá a senha no WhatsApp em instantes.',
      })
    } catch (error) {
      const msgErro = error.response?.data?.message || 'Erro ao tentar recuperar a senha.'
      Swal.fire({ ...swalConfig, icon: 'error', title: 'Ops!', text: msgErro })
    }
  }
}
</script>

<style scoped>
.register-text {
  margin-top: 20px;
  color: var(--text-soft);
  text-align: center;
  margin-bottom: 20px;
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
  text-align: center;
}

.link-esqueci-senha {
  display: block;
  text-align: right;
  margin-top: 4px;
  font-size: 13px;
  color: var(--text-soft, #9ca3af);
  text-decoration: none;
  font-family: 'Poppins', sans-serif;
  transition: color 0.2s;
}

.link-esqueci-senha:hover {
  color: var(--primary);
  text-decoration: underline;
}
</style>