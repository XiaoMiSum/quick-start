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

export const batchRemove = (params: any) => {
  return request.delete({
    url: url,
    params
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

export const batchRemoveTestcase = (params: any) => {
  return request.delete({
    url: url + '/trash',
    params
  })
}

export const recoverTestcase = (params: any) => {
  return request.post({
    url: url + '/trash?',
    data: params
  })
}

// 创建新版本
export const createNewVersion = (data: any) => {
  return request.post({
    url: url + '/version',
    data
  })
}

// 获取用例版本列表
export const getVersionList = (parentId: string) => {
  return request.get({
    url: url + '/versions/' + parentId
  })
}

// 对比用例版本
export const compareVersions = (version1Id: string, version2Id: string) => {
  return request.get({
    url: url + '/compare',
    params: { version1Id, version2Id }
  })
}
