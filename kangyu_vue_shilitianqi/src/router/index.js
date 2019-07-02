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
        component: () => import('@/page/scenes/scenes_main'),
        meta: {
          routeList: ['场景管理'],
          keepAlive: false,
          show: true,
          role: '1'
        },
      },
      {
        path: '/archives',
        name: '档案管理',
        component: () => import('@/page/archives/archives_list'),
        meta: {
          routeList: ['档案管理'],
          keepAlive: false,
          show: true,
          role: '1'
        },
      },
      {
        path: '/archives_card',
        name: '档案管理',
        component: () => import('@/page/archives/archives_card'),
        meta: {
          routeList: ['档案管理'],
          keepAlive: false,
          role: '1'
        },
        children: [
          {
            path: 'archives_info',
            component: () => import('@/page/archives/archives_info'),
            meta: {
              routeList: ['档案信息', '基本信息'],
              keepAlive: false
            }
          },
          {
            path: 'sub_archives',
            component: () => import('@/page/archives/sub_archives'),
            meta: {
              routeList: ['档案信息', '出生情况记录'],
              keepAlive: false,
              role: '1'
            }
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
