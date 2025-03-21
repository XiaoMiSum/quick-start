import request from '@/config/axios'

const url = '/quality-center/testcase'

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

export const batchUpdate = (data: any) => {
  return request.put({
    url: url + '/batch',
    data
  })
}

export const removeData = (id: number) => {
  return request.delete({
    url: url + '/' + id
  })
}

export const batchRemove = (ids: number[]) => {
  return request.delete({
    url: url + '?ids=' + ids
  })
}

export const batchImports = (data: any) => {
  return request.upload({
    url: url + '/imports',
    data
  })
}

export const download = (template: boolean = false) => {
  return request.download({
    url: url + '/download?template=' + template
  })
}

export const getRecycleTestcase = (params: any) => {
  return request.get({
    url: url + '/recycle',
    params
  })
}

export const batchRemoveRecycleTestcase = (ids: number[]) => {
  return request.delete({
    url: url + '/recycle?ids=' + ids
  })
}

export const recoverTestcase = (ids: number[]) => {
  return request.post({
    url: url + '/recycle?ids=' + ids
  })
}
