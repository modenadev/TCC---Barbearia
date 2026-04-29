import { ref } from 'vue'

export const usuarioLogado = ref({
  token: localStorage.getItem('token'),
  nome: localStorage.getItem('nome'),
  email: localStorage.getItem('email'),
  perfil: localStorage.getItem('perfil')
})

export function atualizarUsuarioLogado() {
  usuarioLogado.value = {
    token: localStorage.getItem('token'),
    nome: localStorage.getItem('nome'),
    email: localStorage.getItem('email'),
    perfil: localStorage.getItem('perfil')
  }
}

export function isLogado() {
  return !!usuarioLogado.value.token
}