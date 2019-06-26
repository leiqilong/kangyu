import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/page/login/login'
import Layout from '@/layout'


Vue.use(Router)

export const constantRoutes = [
  {
    path: '/layout',
    component: Layout,
    children: [
      {
        name: '首页',
        path: '/home',
        component: () => import('@/layout/components/home'),
        meta: {
          show: true,
          role: '1'
        }
      },
      {
        name: '医生管理',
        path: '/doctor',
        component: () => import('@/page/doctor/doctor_list'),
        meta: {
          show: true,
          role: '1',
          routeList: ['医生管理']
        }
      },

      {
        path: '/tag_warehouse/custom_form_tag',
        name: '标签库管理',
        component: () => import('@/page/tag_warehouse/custom_form_tag'),
        meta: {
          routeList: ['标签库管理'],
          keepAlive: false,
          show: true,
          role: '1',
        },
      },
      {
        path: '/scenes',
        name: '场景管理',
        component: () => import('@/page/scenes/scenes_main.vue'),
        meta: {
          routeList: ['场景管理'],
          keepAlive: false,
          show: true,
          role: '1'
        },
      },

      /*{
        name: '标签库管理',
        path: '/tag_warehouse',
        meta: {
          show: true,
          role: '1',
          children: [
            {
              path: '/tag_warehouse/custom_form_tag',
              name: '自定义表单标签',
              component: () => import('@/page/tag_warehouse/custom_form_tag'),
              meta: {
                routeList: ['标签库管理', '自定义表单标签'],
                keepAlive: false
              },
            },
            {
              path: '/tag_warehouse/message_push_tag',
              name: '消息推送类标签',
              component: () => import('@/page/tag_warehouse/message_push_tag'),
              meta: {
                routeList: ['标签库管理', '消息推送类标签'],
                keepAlive: false
              }
            }
          ]
        }
      }*/
    ]
  }
]

export default new Router({
  routes: [
    {
      path: '/login',
      component: Login,
      hidden: true
    }
  ]
})
