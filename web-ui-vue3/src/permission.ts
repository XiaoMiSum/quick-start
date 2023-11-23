import router from './router'
import type { RouteRecordRaw } from 'vue-router'
import { useTitle } from '@/hooks/web/useTitle'
import { useNProgress } from '@/hooks/web/useNProgress'
import { usePageLoading } from '@/hooks/web/usePageLoading'
import { useUserStoreWithOut } from '@/store/modules/user'
import { usePermissionStoreWithOut } from '@/store/modules/permission'
import { hasToken } from '@/utils/auth'

const nprogress = useNProgress()

const loader = usePageLoading()
// 路由不重定向白名单
const whiteList = ['/login', '/register']

// 路由加载前
router.beforeEach(async (to, from, next) => {
  nprogress.start()
  loader.loadStart()
  if (hasToken()) {
    const path = to.path.toString()
    if (path.includes('404')) {
      next()
    } else if (path.includes('/login') || path.includes('/register')) {
      // if is logged in, redirect to the home page
      next({ path: '/index' })
    } else {
      const userStore = useUserStoreWithOut()
      const permissionStore = usePermissionStoreWithOut()
      if (!userStore.getIsSetUser) {
        await userStore.setUserInfoAction()
        // 后端过滤菜单
        await permissionStore.generateRoutes()
        permissionStore.getAddRouters.forEach((route) => {
          router.addRoute(route as unknown as RouteRecordRaw) // 动态添加可访问路由表
        })
        next({ ...to, replace: true })
      } else {
        next()
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login`)
    }
  }
})

router.afterEach((to) => {
  useTitle(to?.meta?.title as string)
  nprogress.done() // 结束Progress
  loader.loadDone()
})
