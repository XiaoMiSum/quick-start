import request from '@/config/axios'

const url = '/menu'

export interface MenuVO {
  id: number
  name: string
  permission: string
  type: number
  sort: number
  parentId: number
  path: string
  icon: string
  component: string
  componentName?: string
  status: number
  visible: boolean
  keepAlive: boolean
  alwaysShow?: boolean
  createTime: Date
}

// 查询列表
export const listSimpleMenus = () => {
  return request.get({
    url: url + '/simple'
  })
}

// 查询列表
export const listData = (params: any) => {
  return request.get({
    url,
    params
  })
}

// 查询详细
export const getData = (id: number) => {
  return request.get({
    url: url + '/' + id
  })
}

// 新增
export const addData = (data: any) => {
  return request.post({
    url,
    data
  })
}

// 修改
export const updateData = (data: any) => {
  return request.put({
    url,
    data
  })
}

// 删除
export const delData = (id: any) => {
  return request.delete({
    url: url + '/' + id
  })
}
