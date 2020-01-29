import Vue from 'vue'
import Vuex from 'vuex'

import app from './modules/app'
import user from './modules/user'
// dynamic router permission control (Experimental)
// 从服务端请求路由信息
import permission from './modules/async-router'
import getters from './getters'

// default router permission control
// import permission from './modules/permission'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app,
    user,
    permission
  },
  state: {

  },
  mutations: {

  },
  actions: {

  },
  getters
})
