import { useCache } from '@/hooks/web/useCache'

const { wsCache } = useCache()

const AccessTokenKey = 'ACCESS_TOKEN'

// 获取token
export const getAccessToken = () => {
  // 此处与TokenKey相同，此写法解决初始化时Cookies中不存在TokenKey报错
  return wsCache.get(AccessTokenKey)
}
// 获取token
export const hasToken = () => {
  // 此处与TokenKey相同，此写法解决初始化时Cookies中不存在TokenKey报错
  return !!wsCache.get(AccessTokenKey)
}

// 设置token
export const setToken = (token: string) => {
  wsCache.set(AccessTokenKey, token)
}

// 删除token
export const removeToken = () => {
  wsCache.delete(AccessTokenKey)
}

/** 格式化token（jwt格式） */
export const formatToken = (token: string): string => {
  return 'Bearer ' + token
}
