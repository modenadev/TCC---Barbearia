export function salvarAuth(dados) {
    localStorage.setItem('token', dados.token);
    localStorage.setItem('nome', dados.nome);
    localStorage.setItem('email', dados.email);
    localStorage.setItem('perfil', dados.perfil);
}

export function limparAuth() {
    localStorage.removeItem('token');
    localStorage.removeItem('nome');
    localStorage.removeItem('email');
    localStorage.removeItem('perfil');
}

export function estaAutenticado() {
    return !!localStorage
        .getItem('token');
}
export function getPerfil() {
    return localStorage.getItem('perfil');
}

export function getNome() {
    return localStorage.getItem('nome');
}
