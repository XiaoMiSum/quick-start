import request from '@/config/axios'

const url = '/project'

// 查询详细
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}

export const getSimple = () => {
  return request.get({
    url: url + '/simple'
  })
}

export const getData = (id: String) => {
  return request.get({
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
