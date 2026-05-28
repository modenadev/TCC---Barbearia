<template>
    <section class="section profile-section">
        <div class="container">
            <h1 class="section-title">Meus Dados</h1>
            <p class="section-subtitle">Atualize suas informações pessoais e senha de acesso.</p>

            <div class="profile-layout">
                <div class="card form-card">
                    <h3>Informações Pessoais</h3>
                    <form @submit.prevent="atualizarDados">
                        <div class="field">
                            <label>Nome Completo</label>
                            <input type="text" v-model="usuario.nome" class="input custom-input" required />
                        </div>

                        <div class="field">
                            <label>E-mail</label>
                            <input type="email" v-model="usuario.email" class="input custom-input" required />
                        </div>

                        <div class="field">
                            <label>Telefone (WhatsApp)</label>
                            <input type="text" v-model="usuario.telefone" class="input custom-input" required />
                        </div>

                        <button type="submit" class="btn btn-primary full-btn" :disabled="salvandoDados">
                            {{ salvandoDados ? 'Salvando...' : 'Salvar Alterações' }}
                        </button>
                    </form>
                </div>

                <div class="card form-card">
                    <h3>Segurança</h3>
                    <form @submit.prevent="atualizarSenha">
                        <div class="field">
                            <label>Senha Atual</label>
                            <input type="password" v-model="senhas.atual" class="input custom-input" required />
                        </div>

                        <div class="field">
                            <label>Nova Senha</label>
                            <input type="password" v-model="senhas.nova" class="input custom-input" required
                                minlength="6" />
                        </div>

                        <div class="field">
                            <label>Confirmar Nova Senha</label>
                            <input type="password" v-model="senhas.confirmacao" class="input custom-input" required
                                minlength="6" />
                        </div>

                        <button type="submit" class="btn btn-outline full-btn" :disabled="salvandoSenha">
                            {{ salvandoSenha ? 'Atualizando...' : 'Alterar Senha' }}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </section>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../services/api'
import Swal from 'sweetalert2'

const swalConfig = {
    background: '#141416',
    color: '#ffffff',
    confirmButtonColor: '#facc15',
}

const usuario = ref({
    nome: '',
    email: '',
    telefone: ''
})

const senhas = ref({
    atual: '',
    nova: '',
    confirmacao: ''
})

const salvandoDados = ref(false)
const salvandoSenha = ref(false)

const carregarDadosUsuario = async () => {
    try {
        const response = await api.get('http://localhost:8080/api/usuarios/meus-dados')
        usuario.value = response.data
    } catch (error) {
        console.error(error)
    }
}

const atualizarDados = async () => {
    salvandoDados.value = true
    try {
        await api.put('http://localhost:8080/api/usuarios/meus-dados', usuario.value)
        localStorage.setItem('nome', usuario.value.nome)
        window.dispatchEvent(new Event('storage'))

        Swal.fire({
            ...swalConfig,
            icon: 'success',
            title: 'Atualizado!',
            text: 'Seus dados foram salvos com sucesso.'
        })
    } catch (error) {
        Swal.fire({ ...swalConfig, icon: 'error', title: 'Erro', text: 'Não foi possível atualizar os dados.' })
    } finally {
        salvandoDados.value = false
    }
}

const atualizarSenha = async () => {
    if (senhas.value.nova !== senhas.value.confirmacao) {
        Swal.fire({ ...swalConfig, icon: 'warning', title: 'Atenção', text: 'As novas senhas não coincidem.' })
        return
    }

    salvandoSenha.value = true
    try {
        await api.patch('http://localhost:8080/api/usuarios/alterar-senha', {
            senhaAtual: senhas.value.atual,
            novaSenha: senhas.value.nova
        })

        Swal.fire({
            ...swalConfig,
            icon: 'success',
            title: 'Senha Alterada!',
            text: 'Sua senha foi atualizada com segurança.'
        })

        senhas.value = { atual: '', nova: '', confirmacao: '' }
    } catch (error) {
        Swal.fire({ ...swalConfig, icon: 'error', title: 'Erro', text: 'Senha atual incorreta ou erro no servidor.' })
    } finally {
        salvandoSenha.value = false
    }
}

onMounted(carregarDadosUsuario)
</script>

<style scoped>
.profile-section {
    padding: 40px 0;
    min-height: 80vh;
}

.section-subtitle {
    color: #9ca3af;
    margin-top: 5px;
    margin-bottom: 30px;
}

.profile-layout {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 30px;
    align-items: start;
}

.form-card {
    background: rgba(20, 20, 22, 0.8);
    border: 1px solid rgba(255, 255, 255, 0.08);
    border-radius: 16px;
    padding: 30px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}

.form-card h3 {
    margin-top: 0;
    margin-bottom: 24px;
    font-family: 'Poppins', sans-serif;
    color: #fff;
    font-size: 20px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.05);
    padding-bottom: 12px;
}

.field {
    display: flex;
    flex-direction: column;
    gap: 8px;
    margin-bottom: 20px;
}

.field label {
    color: #9ca3af;
    font-size: 14px;
}

.custom-input {
    background: rgba(255, 255, 255, 0.03);
    border: 1px solid rgba(255, 255, 255, 0.1);
    color: #fff;
    padding: 14px 16px;
    border-radius: 12px;
    font-size: 15px;
    outline: none;
    transition: 0.3s;
}

.custom-input:focus {
    border-color: var(--primary, #facc15);
    background: rgba(255, 255, 255, 0.08);
}

.full-btn {
    width: 100%;
    padding: 14px;
    border-radius: 12px;
    font-size: 15px;
    font-weight: 600;
    cursor: pointer;
    transition: 0.3s;
    margin-top: 10px;
}

.btn-primary {
    background: var(--primary, #facc15);
    color: #111;
    border: none;
}

.btn-primary:hover {
    filter: brightness(1.1);
    transform: translateY(-2px);
}

.btn-outline {
    background: transparent;
    color: #fff;
    border: 1px solid rgba(255, 255, 255, 0.2);
}

.btn-outline:hover {
    border-color: var(--primary, #facc15);
    color: var(--primary, #facc15);
}

@media (max-width: 768px) {
    .profile-layout {
        grid-template-columns: 1fr;
    }
}
</style>