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
  project: string
}

export const useUserStore = defineStore('admin-user', {
  state: (): UserInfoVO => ({
    permissions: [],
    isSetUser: false,
    user: {
      id: 0,
      avatar: '',
      name: ''
    },
    project: wsCache.get(CACHE_KEY.PROJECT)
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
    },
    getProject(): string {
      return this.project
    },
    getDefaultProject(): number {
      return wsCache.get(CACHE_KEY.PROJECT)
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
      if (!wsCache.get(CACHE_KEY.PROJECT)) {
        wsCache.add(CACHE_KEY.PROJECT, userInfo.defaultProject)
      }
      this.project = wsCache.get(CACHE_KEY.PROJECT)
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
    },
    setDefaultProject(id: string) {
      this.project = id
      wsCache.set(CACHE_KEY.PROJECT, id)
    }
  }
})

export const useUserStoreWithOut = () => {
  return useUserStore(store)
}
