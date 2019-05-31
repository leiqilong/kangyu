function setStore (key, value) {
  localStorage.setItem(key, value);
}

function getStore (key) {
  localStorage.getItem(key);
}

function removeStore(key) {
  localStorage.removeItem(key)
}

export default {
  setStore,
  getStore,
  removeStore
}
