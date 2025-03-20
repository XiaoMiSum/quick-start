import request from '@/config/axios'

const url = '/developer/sms/log'

// 获得短信日志分页
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}
export const getData = (id: any) => {
  return request.get({
    url: url + '/' + id
  })
}
