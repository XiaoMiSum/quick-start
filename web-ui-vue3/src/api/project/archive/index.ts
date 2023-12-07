import request from '@/config/axios'

const url = '/project/archive'

// 查询详细
export const getPage = (params: any) => {
  return request.get({
    url,
    params
  })
}

export const getData = (archiveId: any) => {
  return request.get({
    url: url + '/' + archiveId
  })
}

export const getNode = (archiveId: any) => {
  return request.get({
    url: url + '/node',
    params: { archiveId }
  })
}

export const getTestcases = (params) => {
  return request.get({
    url: url + '/testcase',
    params
  })
}

export const getTestcase = (id) => {
  return request.get({
    url: url + '/testcase/' + id
  })
}

export const download = (id: any) => {
  return request.download({
    url: url + '/download/' + id
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
