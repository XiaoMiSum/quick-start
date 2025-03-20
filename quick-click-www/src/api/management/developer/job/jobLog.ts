import request from '@/config/axios'

const url = '/developer/job/log'

// 获得定时任务
export const getJobLog = (id: any) => {
  return request.get({
    url: url + '/' + id
  })
}

// 获得定时任务分页
export const getJobLogPage = (params: any) => {
  return request.get({
    url: url,
    params
  })
}
