import axios, {
  AxiosError,
  AxiosInstance,
  AxiosRequestHeaders,
  AxiosResponse,
  InternalAxiosRequestConfig
} from 'axios'
import { getAccessToken, removeToken } from '@/utils/auth'

import { ElMessage } from 'element-plus'
import { config } from '@/config/axios/config'
import qs from 'qs'
import { resetRouter } from '@/router'
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'

const { wsCache } = useCache()

const { result_code, base_url, request_timeout } = config

// Axios 无感知刷新令牌，参考 https://www.dashingdog.cn/article/11 与 https://segmentfault.com/a/1190000020210980 实现
// 请求白名单，无须token的接口
const whiteList: string[] = ['/login', '/refresh-token']

// 创建axios实例
const service: AxiosInstance = axios.create({
  baseURL: base_url, // api 的 base_url
  timeout: request_timeout, // 请求超时时间
  withCredentials: false // 禁用 Cookie 等信息
})

// request拦截器
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // 是否需要设置 token
    let isToken = (config!.headers || {}).isToken === false
    whiteList.some((v) => {
      if (config.url) {
        config.url.indexOf(v) > -1
        return (isToken = false)
      }
    })
    if (getAccessToken() && !isToken) {
      ;(config as Recordable).headers['X-Token'] = 'Bearer ' + getAccessToken() // 让每个请求携带自定义token
    }

    ;(config as Recordable).headers['x-project-id'] = wsCache.get(CACHE_KEY.PROJECT)
    const params = config.params || {}
    const data = config.data || false
    if (
      config.method?.toUpperCase() === 'POST' &&
      (config.headers as AxiosRequestHeaders)['Content-Type'] ===
        'application/x-www-form-urlencoded'
    ) {
      config.data = qs.stringify(data)
    }
    // get参数编码
    if (config.method?.toUpperCase() === 'GET' && params) {
      let url = config.url + '?'
      for (const propName of Object.keys(params)) {
        const value = params[propName]
        if (value !== void 0 && value !== null && typeof value !== 'undefined') {
          if (typeof value === 'object') {
            for (const val of Object.keys(value)) {
              const params = propName + '[' + val + ']'
              const subPart = encodeURIComponent(params) + '='
              url += subPart + encodeURIComponent(value[val]) + '&'
            }
          } else {
            url += `${propName}=${encodeURIComponent(value)}&`
          }
        }
      }
      // 给 get 请求加上时间戳参数，避免从缓存中拿数据
      // const now = new Date().getTime()
      // params = params.substring(0, url.length - 1) + `?_t=${now}`
      url = url.slice(0, -1)
      config.params = {}
      config.url = url
    }
    return config
  },
  (error: AxiosError) => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  async (response: AxiosResponse<any>) => {
    const { data } = response
    if (!data) {
      // 返回“[HTTP]请求没有返回值”;
      throw new Error()
    }

    // 二进制数据则直接返回
    if (
      response.request.responseType === 'blob' ||
      response.request.responseType === 'arraybuffer'
    ) {
      return response.data
    }
    // 未设置状态码则默认成功状态
    const code = data.code || result_code
    if (code === 401) {
      // to re-login
      ElMessageBox.alert('当前未登录或登录已过期，请重新登录！', '重新登录', {
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        resetRouter() // 重置静态路由表
        wsCache.clear()
        removeToken()
        location.href = '/login'
        return Promise.reject(new Error(data.msg || 'Error'))
      })
    } else if (code !== 0) {
      ElMessage.error({
        message: data.msg || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject(new Error(data.msg || 'Error'))
    } else {
      return data
    }
  },
  (error: AxiosError) => {
    console.log('err' + error) // for debug
    let { message } = error
    const { t } = useI18n()
    if (message === 'Network Error') {
      message = t('sys.api.errorMessage')
    } else if (message.includes('timeout')) {
      message = t('sys.api.apiTimeoutMessage')
    } else if (message.includes('Request failed with status code')) {
      message = t('sys.api.apiRequestFailed') + message.substr(message.length - 3)
    }
    ElMessage.error(message)
    return Promise.reject(error)
  }
)

export {service}
