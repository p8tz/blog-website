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
  if (response.data.code === 2001) {
    console.log('token失效')
    router.push('/admin/login')
  }
  return response
}, error => {
  return Promise.reject(error)
})

Vue.prototype.$http = axios

Vue.config.productionTip = false
Date.prototype.format = function (fmt) {
  var o = {
    'M+': this.getMonth() + 1, //月份
    'd+': this.getDate(), //日
    'h+': this.getHours(), //小时
    'm+': this.getMinutes(), //分
    's+': this.getSeconds(), //秒
    'q+': Math.floor((this.getMonth() + 3) / 3), //季度
    'S': this.getMilliseconds() //毫秒
  }
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  for (var k in o) {
    if (new RegExp('(' + k + ')').test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(('' + o[k]).length)))
    }
  }
  return fmt
}
new Vue({
  router: router,
  render: h => h(App)
}).$mount('#app')
