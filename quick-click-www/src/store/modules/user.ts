import { store } from '@/store'
import { defineStore } from 'pinia'
import { hasToken, removeToken } from '@/utils/auth'
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'
import { getInfo, loginOut } from '@/api/login'

const { wsCache } = useCache()

interface UserVO {
  id: number
  avatar: string
  name: string
}

interface UserInfoVO {
  permissions: string[]
  isSetUser: boolean
  user: UserVO
}

export const useUserStore = defineStore('admin-user', {
  state: (): UserInfoVO => ({
    permissions: [],
    isSetUser: false,
    user: {
      id: 0,
      avatar: '',
      name: ''
    }
  }),
  getters: {
    getPermissions(): string[] {
      return this.permissions
    },
    getIsSetUser(): boolean {
      return this.isSetUser
    },
    getUser(): UserVO {
      return this.user
    }
  },
  actions: {
    async setUserInfoAction() {
      if (!hasToken()) {
        this.resetState()
        return null
      }
      let userInfo = wsCache.get(CACHE_KEY.USER)
      if (!userInfo) {
        userInfo = await getInfo()
      }
      this.permissions = userInfo.permissions
      this.user = { name: userInfo.name, id: userInfo.id, avatar: userInfo.avatar }
      this.isSetUser = true
      wsCache.set(CACHE_KEY.USER, userInfo)
    },
    async loginOut() {
      await loginOut()
      removeToken()
      wsCache.clear()
      this.resetState()
    },
    resetState() {
      this.permissions = []
      this.isSetUser = false
      this.user = {
        id: 0,
        avatar: '',
        name: ''
      }
    }
  }
})

export const useUserStoreWithOut = () => {
  return useUserStore(store)
}
