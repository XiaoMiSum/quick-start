import request from '@/config/axios'

const url = '/configurer'

export const save = (data: any) => {
  return request.put({
    url,
    data
  })
}

// 查询详细
export const getAll = () => {
  return request.get({
    url
  })
}

// 查询详细
export const getPage = () => {
  return request.get({
    url: url + '/page'
  })
}
