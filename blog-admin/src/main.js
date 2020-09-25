import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/ant-design-vue.js'
import './assets/css/global.css'
import axios from 'axios'

// hype text editor
import VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css' // import styles
import 'quill/dist/quill.snow.css' // for snow theme
import 'quill/dist/quill.bubble.css' // for bubble theme
Vue.use(VueQuillEditor) //, /* { default global options } */

Vue.prototype.$http = axios

Vue.config.productionTip = false

new Vue({
  router: router,
  render: h => h(App)
}).$mount('#app')
