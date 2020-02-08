import request from '@/utils/request'

// 登录方法
export function login(parameter) {
  return request({
    url: '/sys/login',
    method: 'post',
    data: parameter
  })
}
// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/api/user/info',
    method: 'get',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
}
// 退出方法
export function logout(logoutToken) {
  return request({
    url: '/sys/logout',
    method: 'post',
    headers: {
      'Content-Type': 'application/json;charset=UTF-8',
      'X-Access-Token':  logoutToken
    }
  })
}
// 获取验证码
export function getCodeImg() {
  return request({
    url: '/sys/captchaImage',
    method: 'get'
  })
}
