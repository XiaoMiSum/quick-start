import request from '@/config/axios'

const url = '/st/review'

// 查询详细
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}

export const getData = (id: any) => {
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

export const getReviewCase = (params: any) => {
  return request.get({
    url: url + '/case',
    params
  })
}

export const getFirstReviewCase = (params: any) => {
  return request.get({
    url: url + '/case/first',
    params
  })
}

export const getReviewCaseExecute = (params: any) => {
  return request.get({
    url: url + '/case/execute',
    params
  })
}

export const reviewCase = (data: any) => {
  return request.post({
    url: url + '/case/execute',
    data
  })
}

export const getLastCase = (params: any) => {
  return request.get({
    url: url + '/case/last',
    params
  })
}

export const getNextCase = (params: any) => {
  return request.get({
    url: url + '/case/next',
    params
  })
}

export const getUnAssociCase = (params: any) => {
  return request.get({
    url: url + '/case/unassociated',
    params
  })
}

export const addAssociCase = (data: any) => {
  return request.post({
    url: url + '/case',
    data
  })
}

export const removeAssociCase = (id: number) => {
  return request.delete({
    url: url + '/case/' + id
  })
}

export const batchRemoveAssociCase = (ids: number) => {
  return request.delete({
    url: url + '/case?ids=' + ids
  })
}
