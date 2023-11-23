import request from '@/config/axios'

const url = '/project/member'

// 查询详细
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}

export const addData = (data: any) => {
  return request.post({
    url,
    data
  })
}

export const removeData = (id: number) => {
  return request.delete({
    url: url + '/' + id
  })
}
