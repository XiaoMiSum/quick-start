import request from '@/config/axios'

const url = '/quality-center/bug'

// 查询详细
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}

export const getData = (id: string) => {
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

export const confirm = (ids: string[]) => {
  return request.put({
    url: url + '/confirmed',
    data: { ids }
  })
}

export const fix = (data: any) => {
  return request.put({
    url: url + '/fixed',
    data
  })
}

export const reopen = (id: string) => {
  return request.put({
    url: url + '/reopened/' + id
  })
}

export const close = (id: string) => {
  return request.put({
    url: url + '/closeed/' + id
  })
}

export const remove = (id: string) => {
  return request.delete({
    url: url + '/' + id
  })
}

export const download = (params: any) => {
  return request.download({
    url: url + '/download',
    params
  })
}
