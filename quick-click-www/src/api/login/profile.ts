import request from '@/config/axios'

const url = ''

// 查询用户个人信息
export const getUserProfile = () => {
  return request.get({
    url: url + '/profile'
  })
}

// 修改用户个人信息
export const updateUserProfile = (data: any) => {
  return request.put({
    url: url + '/profile',
    data
  })
}

// 用户密码重置
export const updateUserPassword = (data: any, code: string) => {
  return request.post({
    url: url + '/password?_code=' + code,
    data
  })
}

// 用户头像上传
export const uploadAvatar = (data: any) => {
  return request.put({
    url: url + '/profile/avatar',
    data: data
  })
}

// 查询用户身份验证器信息
export const getUserAuthenticator = (token: any) => {
  return request.get({
    url: url + '/authenticator?_token=' + token
  })
}

// 绑定用户身份验证器信息
export const bindUserAuthenticator = (token: any, code: any) => {
  return request.post({
    url: url + '/authenticator?_token=' + token + '&_code=' + code
  })
}
