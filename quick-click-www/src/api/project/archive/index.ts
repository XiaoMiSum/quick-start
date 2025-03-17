import request from '@/config/axios'

const url = '/project/iteration'

// 查询数据列表
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}

// 获取详情
export const getData = (iterationId: any) => {
  return request.get({
    url: url + '/' + iterationId
  })
}

// 获取归档节点
export const getNode = (iterationId: any) => {
  return request.get({
    url: url + '/node',
    params: { iterationId }
  })
}

// 获取归档用例列表
export const getTestcases = (params: any) => {
  return request.get({
    url: url + '/testcase',
    params
  })
}

// 获取指定归档用例
export const getTestcase = (id: any) => {
  return request.get({
    url: url + '/testcase/' + id
  })
}

// 导出指定归档用例
export const download = (iterationId: any) => {
  return request.download({
    url: url + '/download/' + iterationId
  })
}

// 新增迭代
export const addData = (data: any) => {
  return request.post({
    url,
    data
  })
}

// 更新迭代
export const updateData = (data: any) => {
  return request.put({
    url,
    data
  })
}

// 迭代归档
export const archive = (iterationId: any) => {
  return request.post({
    url: url + '/' + iterationId
  })
}

// 删除迭代
export const removeData = (iterationId: string) => {
  return request.delete({
    url: url + '/' + iterationId
  })
}
