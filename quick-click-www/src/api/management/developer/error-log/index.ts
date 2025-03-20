import request from '@/config/axios'

export const getPage = (params: any) => {
  return request.get({
    url: '/developer/error-log',
    params
  })
}

export const getData = (id: any) => {
  return request.get({
    url: '/developer/error-log/' + id
  })
}

export const updateData = (data: any) => {
  return request.put({
    url: '/developer/error-log',
    data
  })
}

export const removeData = (id: any) => {
  return request.delete({
    url: '/developer/error-log/' + id
  })
}
