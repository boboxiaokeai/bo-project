import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import '@/styles/index.scss' // global css

import 'normalize.css/normalize.css'

import '@/icons' // icon

import permission from './directive/permission'
import './permission' // permission control

Vue.use(permission)
Vue.use(ElementUI, { size:'small' })

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
