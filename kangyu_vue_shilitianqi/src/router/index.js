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
        name: '标签库管理',
        path: '/tag_warehouse',
        component: () => import('@/page/tag_warehouse/tag_warehouse'),
        meta: {
          show: true,
          role: '1',
          routeList: ['标签库管理']
        },
        children: [
          {
            path: '/',
            component: () => import('@/page/tag_warehouse/custom_form_tag'),
            meta: {
              routeList: ['标签库管理', '自定义表单标签'],
              keepAlive: false,
              role: '1'
            },
          },
          {
            path: '/message_push_tag',
            component: () => import('@/page/tag_warehouse/message_push_tag'),
            meta: {
              routeList: ['标签库管理', '消息推送类标签'],
              keepAlive: false,
              role: '1'
            },
          }
        ]
      }
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
