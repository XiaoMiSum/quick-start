import request from '@/config/axios'

const url = '/developer/job'

// 查询定时任务调度列表
export const listJob = (params: any) => {
  return request.get({
    url,
    params
  })
}

// 查询定时任务调度详细
export const getJob = (jobId: any) => {
  return request.get({
    url: url + '/' + jobId
  })
}

// 新增定时任务调度
export const addJob = (data: any) => {
  return request.post({
    url,
    data
  })
}

// 修改定时任务调度
export const updateJob = (data: any) => {
  return request.put({
    url,
    data
  })
}

// 删除定时任务调度
export const delJob = (jobId: any) => {
  return request.delete({
    url: url + '/' + jobId
  })
}

// 任务状态修改
export const updateJobStatus = (jobId: any, status: any) => {
  return request.put({
    url: url + '/' + jobId + '?status=' + status
  })
}

// 定时任务立即执行一次
export const runJob = (jobId: any) => {
  return request.put({
    url: url + '/trigger?id=' + jobId
  })
}

// 获得定时任务的下 n 次执行时间
export const getJobNextTimes = (jobId: any) => {
  return request.get({
    url: url + '/get_next_times?id=' + jobId
  })
}
