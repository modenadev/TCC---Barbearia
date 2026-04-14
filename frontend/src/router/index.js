import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../pages/HomeView.vue'
import ServicosView from '../pages/ServicosView.vue'
import AgendamentoView from '../pages/AgendamentoView.vue'

const routes = [
    { path : '/', name: 'home', component: HomeView },
    { path : '/servicos', name: 'servicos', component: ServicosView },
    { path : '/agendamento', name: 'agendamento', component: AgendamentoView }
]
const router = createRouter({
    history: createWebHistory(),
    routes
})
export default router
