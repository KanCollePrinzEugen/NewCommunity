import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Default',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/LoginView')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/RegisterView')
  },
  {
    path: '/sysAdmin',
    name: 'SysAdmin',
    component: () => import('@/views/sys/SysAdminList')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next()
  const item = window.sessionStorage.getItem('token')
  if (!item) return next('/login')
  next()
})

export default router
