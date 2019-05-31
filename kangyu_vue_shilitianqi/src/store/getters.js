const getters = {
  username: state => state.login.username,
  role: state => state.login.role,
  newrouter: state => state.login.newrouter,
  node: state => state.login.node,
  hospitalId: state => state.login.hospitalId,
  hospitalname: state => state.login.hospitalname,
  phone: state => state.login.phone,
  password: state => state.login.password
};

export default getters
