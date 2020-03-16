import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' //NProgress是页面跳转是出现在浏览器顶部的进度条
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'


NProgress.configure({ showSpinner: false })

//白名单
const whiteList = ['/login', '/auth-redirect', '/bind', '/register']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) { //判断是否有token
    if (to.path === '/login') { //如果是登录界面跳转到首页
      next({ path: '/' })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        // 判断是否已获取当前用户的user_info信息
        store.dispatch('GetInfo').then(res => {
          const roles =  res.data.roles
          store.dispatch('GenerateRoutes', { roles }).then(accessRoutes => {
            router.addRoutes(accessRoutes) // 动态添加可访问路由表
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
          })
        })
          .catch(err => {
            store.dispatch('FedLogOut').then(() => {
              Message.error(err)
              next({ path: '/' })
            })
          })
      } else {
        next()
        // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
        // if (hasPermission(store.getters.roles, to.meta.roles)) {
        //   next()
        // } else {
        //   next({ path: '/401', replace: true, query: { noGoBack: true }})
        // }
        // 可删 ↑
      }
    }
  } else {
    // 没有token 白名单放行 否则全部重定向到登录页
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
