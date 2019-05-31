import router from './router'
import {constantRoutes} from './router'
import store from './store'
import {Message} from 'element-ui'
import {getToken} from '@/utils/auth' // get token from cookie

const whiteList = ['/login', '/auth-redirect'] // no redirect whitelist

router.beforeEach((to, from, next) => {
  if (store.getters.role) { //判断role 是否存在
    if (store.getters.newrouter.length !== 0) {
      next();
    } else {
      var routerList = constantRoutes;
      let newrouter = constantRoutes
      if (store.getters.role == '2') {  //判断权限
        var newchildren = routerList[0].children
        for (var i = 0; i < newchildren.length; i++) {
          if (newchildren[i].meta.children) {
            newchildren = newchildren.concat(newchildren[i].meta.children)
          }
        }
        newrouter[0].children = newchildren
      } else {
        let newchildren = routerList[0].children.filter(function (route) {
          if (route.meta != null && route.meta != undefined) {
            if (route.meta.role == store.getters.role) {
              return true
            }
            return false
          } else {
            return true
          }
        });

        for (var i = 0; i < newchildren.length; i++) {
          if (newchildren[i].meta.children != null && newchildren[i].meta.children != undefined) {
            var list = newchildren[i].meta.children;
            var newList = [];
            for (var l = 0; l < list.length; l++) {
              if (list[l].meta.role == store.getters.role) {
                newchildren.push(list[l])
                newList.push(list[l])
              }
            }
            newchildren[i].meta.children = newList
          }
        }

        newrouter[0].children = newchildren
      }
      router.addRoutes(newrouter) //添加动态路由
      store.dispatch('Roles', newrouter).then(res => {
        next({...to})
      })
    }
  } else {
    if (to.path === '/login') {
      next()
    } else {
      next('/login')
    }
  }
})

