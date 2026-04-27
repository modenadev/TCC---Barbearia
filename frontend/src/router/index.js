import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../pages/HomeView.vue'
import LoginView from '../pages/LoginView.vue'
import AdminDashboardView from '../pages/AdminDashboardView.vue'
import ClienteDashboardView from '../pages/ClienteDashboardView.vue'
import { estaAutenticado, getPerfil } from '../utils/auth'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminDashboardView,
    meta: { requiresAuth: true, role: 'ADMIN' }
  },
  {
    path: '/cliente',
    name: 'cliente',
    component: ClienteDashboardView,
    meta: { requiresAuth: true, role: 'CLIENTE' }
  },
  
  {
    path: '/servicos',
    name: 'servicos',
    component: () => import('../pages/ServicosView.vue'),
    meta: { requiresAuth: true }
  },

  {
    path: '/agendamento',
    name: 'agendamento',
    component: () => import('../pages/AgendamentoView.vue'),
    meta: { requiresAuth: true }
  }



  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const autenticado = estaAutenticado()
  const perfil = getPerfil()

  if (to.meta.requiresAuth && !autenticado) {
    return next('/login')
  }

  if (to.meta.role && perfil !== to.meta.role) {
    if (perfil === 'ADMIN') return next('/admin')
    if (perfil === 'CLIENTE') return next('/cliente')
    return next('/login')
  }

  next()
})

export default router