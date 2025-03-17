import request from '@/config/axios'

const url = '/user'

export interface UserVO {
  id?: number
  username: string
  name: string
  deptId?: number
  postIds?: string[]
  email?: string
  mobile?: string
  gender?: number
  avatar?: string
  status?: number
  memo?: string
}

// 查询用户列表
export const listUser = (params: any) => {
  return request.get({
    url,
    params
  })
}

// 查询用户列表
export const listSimple = () => {
  return request.get({
    url: url + '/simple'
  })
}

// 查询用户详细
export const getUser = (userId: any) => {
  return request.get({
    url: url + '/' + userId
  })
}

// 新增用户
export const addUser = (data: any) => {
  return request.post({
    url,
    data
  })
}

// 修改用户
export const updateUser = (data: any) => {
  return request.put({
    url,
    data
  })
}

// 删除用户
export const delUser = (userId: any) => {
  return request.delete({
    url: url + '/' + userId
  })
}

// 用户密码重置
export const resetUserPwd = (id, password: any) => {
  const data = {
    id,
    password
  }
  return request.post({
    url: url + '/password',
    data
  })
}
export const resetAuthenticator = (no, code: any) => {
  return request.post({
    url: url + '/' + no + '/authenticator?_code=' + code
  })
}

// 查询用户列表
export const listUserRoles = (id: any) => {
  return request.get({
    url: url + '/' + id + '/role'
  })
} // 查询用户列表
export const updateUserRoles = (data: any) => {
  return request.post({
    url: url + '/role',
    data
  })
}
