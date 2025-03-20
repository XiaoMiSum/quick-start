import request from '@/config/axios'

const url = '/developer/sms/template'

export const getSimple = () => {
  return request.get({ url: url + '/simple' })
}

// 获得短信渠道分页
export const getPage = (query: any) => {
  return request.get({
    url,
    params: query
  })
}

// 获得短信渠道详情
export const getData = (id: any) => {
  return request.get({
    url: url + '/' + id
  })
}

// 新增短信渠道信息
export const addData = (data: any) => {
  return request.post({
    url,
    data
  })
}

// 修改短信渠道信息
export const updateData = (data: any) => {
  return request.put({
    url,
    data
  })
}

// 删除短信渠道
export const delData = (id: any) => {
  return request.delete({
    url: url + '/' + id
  })
}

// 获取短信渠道下拉
export const sendData = (data: any) => {
  return request.post({
    url: url + '/send',
    data
  })
}
