// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import ElementUI from 'element-ui'
import '../theme/index.css'
import './style/common.css'
import store from './store'
import myStore from './utils/mystore'

import './permission'

Vue.use(ElementUI)

Vue.config.productionTip = false

Vue.prototype.$myStore = myStore

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: {App},
  template: '<App/>'
})


