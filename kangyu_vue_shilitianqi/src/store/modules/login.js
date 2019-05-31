import Cookies from 'js-cookie'

const login = {
  state: {
    username: Cookies.get('username'),
    role: Cookies.get('role'),
    newrouter: [],
    node: Cookies.get('node'),
    hospitalId: Cookies.get('hospitalId'),
    hospitalname: Cookies.get('hospitalname'),
    phone: Cookies.get('phone'),
    password: Cookies.get('password')
  },
  mutations: {
    SET_USERNAME: (state, username) => {
      state.username = username;
      Cookies.set('username', username);
    },
    SET_ROLE: (state, role) => {
      state.role = role;
      Cookies.set('role', role);
    },
    SET_NEWROUER: (state, newrouter) => {
      state.newrouter = newrouter;
      Cookies.set('newrouter', newrouter);
    },
    SET_NODE: (state, node) => {
      state.node = node;
      Cookies.set('node', node);
    },
    SET_HOSPITAL: (state, hospital) => {
      state.hospitalId = hospital.id;
      state.hospitalname = hospital.hospitalname;
      Cookies.set('hospitalId', hospital.id);
      Cookies.set('hospitalname', hospital.hospitalname);
    },
    SET_PHONE_AND_PASSWORD: function (state, data) {
      state.phone = data.phone || '';
      state.password = data.password || '';
      Cookies.set('phone', data.phone || '');
      Cookies.set('password', data.password || '');
    }
  },
  actions: {
    Logins({commit}, info) {
      return new Promise((resolve, reject) => {
        let data = {};
        commit('SET_USERNAME', info.name);  //将username和role进行存储
        commit('SET_ROLE', info.level);
        commit('SET_NODE', info.node);
        resolve(data);
      }).catch(error => {
        console.log(error)
      });
    },
    Roles({commit}, newrouter) {
      return new Promise((resolve, reject) => {
        commit('SET_NEWROUER', newrouter); //存储最新路由
        resolve(newrouter);
      }).catch(error => {
        console.log(error)
      });
    },
    Logout({commit, state}) {
      return new Promise((resolve, reject) => {
        commit('SET_USERNAME', '');
        commit('SET_ROLE', '');
        commit('SET_NEWROUER', []);
        commit('SET_NODE', 0);
        location.reload();
        resolve();
      }).catch(error => {
        console.log(error)
      });
    },
    Hospital({commit}, data) {
      commit('SET_HOSPITAL', data)
      return new Promise(resolve => {
        resolve(data);
      })
    },
    PhoneAndPassWord({commit}, data) {
      commit('SET_PHONE_AND_PASSWORD', data)
      return new Promise(resolve => {
        resolve(data);
      })
    }
  }
}
export default login;
