import request from '@/config/axios'
import type { UserLoginVO } from './types'

// 登录
export const login = (data: UserLoginVO) => {
  return request.post({ url: '/sign-in', data })
}

// 登出
export const loginOut = () => {
  return request.post({ url: '/sign-out' })
}

// 获取用户权限信息
export const getInfo = () => {
  return request.get({ url: '/user-info' })
}

export const getRouters = () => {
  return request.get({ url: '/user-menus' })
}

export const getCodeApi = () => {}

export const reqCheckApi = () => {}
