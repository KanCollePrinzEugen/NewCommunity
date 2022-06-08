import request from '@/utils/request'

// 获取验证嘛
export function getCode (data) {
  return request({
    url: `/code/${data}`,
    method: 'POST'
  })
}

// 登录
export function getLogin (data) {
  return request({
    url: `/login?userName=${data.loginName}&userPassword=${data.loginPwd}`,
    method: 'POST'
  })
}
