import request from '@/config/axios'

const url = '/project/env'

// 查询详细
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}

export const getData = (id: any) => {
  return request.post({
    url: url + '/' + id
  })
}

export const addData = (data: any) => {
  return request.post({
    url,
    data
  })
}

export const updateData = (data: any) => {
  return request.put({
    url,
    data
  })
}

export const removeData = (id: number) => {
  return request.delete({
    url: url + '/' + id
  })
}
