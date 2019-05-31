import Vue from 'vue';
import Vuex from 'vuex';
import login from './modules/login';
import getters from './getters';

Vue.use(Vuex);

const loginStore = new Vuex.Store({
  modules: {
    login
  },
  getters
});

export default loginStore
