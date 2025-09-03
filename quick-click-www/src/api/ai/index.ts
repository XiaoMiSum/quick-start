import request from '@/config/axios'

const url = '/ai/agent'

// 分页获取AI智能体列表
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}

// 获取AI智能体详情
export const getData = (id: any) => {
  return request.get({
    url: url + '/' + id
  })
}

// 创建AI智能体
export const addData = (data: any) => {
  return request.post({
    url,
    data
  })
}

// 更新AI智能体
export const updateData = (data: any) => {
  return request.put({
    url,
    data
  })
}

// 删除AI智能体
export const removeData = (id: number) => {
  return request.delete({
    url: url + '/' + id
  })
}