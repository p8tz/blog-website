import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login'
import Index from '../components/Index'
import Home from '../components/Home'
import Article from '../components/Article'
import Type from '../components/Type'
import Tag from '../components/Tag'
import Logout from '../components/Logout'
import EditArticle from '../components/EditArticle'
import PutType from '../components/PutType'
import PutTag from '../components/PutTag'

Vue.use(VueRouter)

const routes = []

const router = new VueRouter({
  routes: [
    {
      path: '/',
      redirect: '/admin/login'
    },
    {
      path: '/admin/login',
      component: Login
    },
    {
      path: '/admin/index',
      component: Index,
      redirect: '/admin/home',
      children: [
        {
          path: '/admin/home',
          component: Home
        },
        {
          path: '/admin/article',
          component: Article
        },
        {
          path: '/admin/type',
          component: Type
        },
        {
          path: '/admin/tag',
          component: Tag
        },
        {
          path: '/admin/logout',
          component: Logout
        },
        {
          path: '/admin/article/:id',
          component: EditArticle
        },
        {
          path: '/admin/type/:id',
          component: PutType
        }
        ,
        {
          path: '/admin/tag/:id',
          component: PutTag
        }
      ]
    },
    {
      path: '*',
      redirect: '/admin/login'
    },
  ]
})
// 挂载路由导航守卫
// @param to: 将要访问的路径
// @param from: 代表从哪个路径跳转来的
// @param next: 是一个函数, 表示放行
//      两种方式   next() 放行    next('/login') 强制跳转

router.beforeEach((to, from, next) => {
  if (to.path === '/admin/login') {
    return next()
  }
  const token = window.sessionStorage.getItem('token')
  if (!token) {
    return next('/admin/login')
  }
  next()
})

export default router
