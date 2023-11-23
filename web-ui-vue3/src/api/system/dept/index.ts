import request from '@/config/axios'

const url = '/dept'

// 查询下拉
export const listSimple = (params?: any) => {
  return request.get({
    url: url + '/simple',
    params
  })
}

// 查询列表
export const listData = (params: any) => {
  return request.get({
    url,
    params
  })
}

// 查询详细
export const getData = (userId: any) => {
  return request.get({
    url: url + '/' + userId
  })
}

// 新增
export const addData = (data: any) => {
  return request.post({
    url,
    data
  })
}

// 修改
export const updateData = (data: any) => {
  return request.put({
    url,
    data
  })
}

// 删除
export const delData = (userId: any) => {
  return request.delete({
    url: url + '/' + userId
  })
}
