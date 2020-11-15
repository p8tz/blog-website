import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/ant-design-vue.js'
import './assets/css/global.css'
import axios from 'axios'

// hyper text editor
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css' // import styles
import 'quill/dist/quill.snow.css' // for snow theme
import 'quill/dist/quill.bubble.css' // for bubble theme
Vue.use(VueQuillEditor) //, /* { default global options } */

axios.defaults.baseURL = 'http://localhost:8888'
// 请求拦截器, 每个请求之前携带token认证
axios.interceptors.request.use(config => {
  config.headers.token = window.sessionStorage.getItem('token')
  return config
}, error => {

})
// 响应拦截器, 每个ajax请求收到回复之前先检验token是否有效
axios.interceptors.response.use(response => {
  // 如果token失效则重定向到login页面
  // status是后端自定义属性
  if (response.data.status === 404) {
    console.log('token失效')
    router.push('/admin/login')
  }
  return response
}, error => {
  return Promise.reject(error)
})

Vue.prototype.$http = axios

Vue.config.productionTip = false

new Vue({
  router: router,
  render: h => h(App)
}).$mount('#app')
