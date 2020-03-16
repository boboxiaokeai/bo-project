import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

//创建axios 实例
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000
})

// request interceptor
service.interceptors.request.use(config => {
  if (store.getters.token) {
    config.headers[ 'X-Access-Token' ] = getToken() // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  if(config.method=='get'){
    if(config.url.indexOf("sys/dict/getDictItems")<0){
      config.params = {
        _t: Date.parse(new Date())/1000,
        ...config.params
      }
    }
  }
  return config
},(error) => {
  console.log(error)
  return Promise.reject(error)
})

const err = (error) => {
  if (error.response) {
    let res = error.response.data
    const token = getToken()
    console.log("------异常响应------",error.response.status)
    switch (error.response.status) {
      case 403:
        Message({
          message: res.message || '拒绝访问',
          type: 'error',
          duration: 5 * 1000
        })
        break
      case 500:
        Message({
          message: res.message || '服务器异常',
          type: 'error',
          duration: 5 * 1000
        })
        break
      case 404:
        Message({
          message: res.message || '很抱歉，资源未找到!',
          type: 'error',
          duration: 5 * 1000
        })
        break
      case 504:
        Message({
          message: res.message || '网络超时!',
          type: 'error',
          duration: 5 * 1000
        })
        break
      case 401:
        if (token) {
          store.dispatch('logout').then(() => {
            setTimeout(() => {
              window.location.reload()
            }, 1500)
          })
        }else{
          MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
            confirmButtonText: 'Re-Login',
            cancelButtonText: 'Cancel',
            type: 'warning'
          }).then(() => {
            store.dispatch('user/resetToken').then(() => {
              location.reload()
            })
          })
        }
        break
      default:
        Message({
          message: res.message,
          type: 'error',
          duration: 5 * 1000
        })
        break
    }
  }
  return Promise.reject(error)
};

// response interceptor
service.interceptors.response.use(
    /**
     * If you want to get http information such as headers or status
     * Please return  response => response
     */
    response => {
      debugger
      const res = response.data
      console.log("请求服务器获取到的数据："+JSON.stringify(res));
      if (res.code !== 200) {
        Message({
          message: res.message || 'Error',
          type: 'error',
          duration: 5 * 1000
        })
        return Promise.reject(new Error(res.message || 'Error'))
      } else {
        return res
      }
    },err)

export default service
