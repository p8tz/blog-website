import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login'
import Index from '../components/Index'
import Home from '../components/Home'
import Blog from '../components/Blog'
import Type from '../components/Type'
import Tag from '../components/Tag'
import Logout from '../components/Logout'
import EditBlog from '../components/EditBlog'

Vue.use(VueRouter)

const routes = []

const router = new VueRouter({
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/index',
      component: Index,
      redirect: '/home',
      children: [
        {
          path: '/home',
          component: Home
        },
        {
          path: '/blog',
          component: Blog
        },
        {
          path: '/type',
          component: Type
        },
        {
          path: '/tag',
          component: Tag
        },
        {
          path: '/logout',
          component: Logout
        },
        {
          path: '/blog/:id',
          component: EditBlog
        }
      ]
    }
  ]
})
// 挂载路由导航守卫
// @param to: 将要访问的路径
// @param from: 代表从哪个路径跳转来的
// @param next: 是一个函数, 表示放行
//      两种方式   next() 放行    next('/login') 强制跳转

router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    return next()
  }
  const token = window.sessionStorage.getItem('token')
  if (!token) {
    return next('/login')
  }
  next()
})

export default router
