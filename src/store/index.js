import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      userid: window.sessionStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).userid,
      name: window.sessionStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).name,
      role: window.sessionStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).role,
    }
  },
  mutations: {
    login (state, user) {
      state.user = user
      window.sessionStorage.setItem('user', JSON.stringify(user))
    },
  }
})
