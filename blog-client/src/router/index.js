import Vue from 'vue'
import VueRouter from 'vue-router'
import AdminLogin from '../components/admin/Login'
import AdminIndex from '../components/admin/Index'
import AdminHome from '../components/admin/Home'
import AdminArticle from '../components/admin/Article'
import AdminType from '../components/admin/Type'
import AdminTag from '../components/admin/Tag'
import AdminLogout from '../components/admin/Logout'
import AdminEditArticle from '../components/admin/EditArticle'
import AdminPutType from '../components/admin/PutType'
import AdminPutTag from '../components/admin/PutTag'

import ShowIndex from '../components/show/Index'

Vue.use(VueRouter)

const routes = []

const router = new VueRouter({
  routes: [
    {
      path: '/',
      redirect: '/index'
    },
    {
      path: '/index',
      component: ShowIndex,
    },




    {
      path: '/admin/login',
      component: AdminLogin
    },
    {
      path: '/admin/index',
      component: AdminIndex,
      redirect: '/admin/home',
      meta: {
        requireAuth: true
      },
      children: [
        {
          path: '/admin/home',
          component: AdminHome,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/admin/article',
          component: AdminArticle,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/admin/type',
          component: AdminType,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/admin/tag',
          component: AdminTag,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/admin/logout',
          component: AdminLogout,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/admin/article/:id',
          component: AdminEditArticle,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/admin/type/:id',
          component: AdminPutType,
          meta: {
            requireAuth: true
          },
        },
        {
          path: '/admin/tag/:id',
          component: AdminPutTag,
          meta: {
            requireAuth: true
          },
        }
      ]
    },
    {
      path: '*',
      redirect: '/index'
    },
  ]
})
// 挂载路由导航守卫
// @param to: 将要访问的路径
// @param from: 代表从哪个路径跳转来的
// @param next: 是一个函数, 表示放行
//      两种方式   next() 放行    next('/login') 强制跳转

router.beforeEach((to, from, next) => {
  if (!to.meta.requireAuth) {
    return next()
  }
  const token = window.sessionStorage.getItem('token')
  if (!token) {
    return next('/admin/login')
  }
  next()
})

export default router
