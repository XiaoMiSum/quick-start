import request from '@/config/axios'

const url = '/quality-center/bug'

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

/**
 * 指派处理人
 *
 * @param data ids
 * @returns
 */
export const assign = (data: any) => {
  return request.put({
    url: url + '/assigned',
    data
  })
}

/**
 * 确认缺陷
 *
 * @param ids ids
 * @returns
 */
export const batchConfirm = (ids: string[]) => {
  return request.put({
    url: url + '/confirmed',
    data: { ids }
  })
}

/**
 * 确认缺陷
 *
 * @param ids ids
 * @returns
 */
export const confirm = (data: any) => {
  return request.put({
    url: url + '/confirmed',
    data
  })
}

/**
 * 拒绝缺陷
 *
 * @param data ids
 * @returns
 */
export const reject = (data: any) => {
  return request.put({
    url: url + '/rejected',
    data
  })
}

/**
 * 修复缺陷
 *
 * @param data data
 * @returns
 */
export const fix = (data: any) => {
  return request.put({
    url: url + '/fixed',
    data
  })
}

/**
 * 激活缺陷
 *
 * @param id id
 * @returns
 */
export const reopen = (data: any) => {
  return request.put({
    url: url + '/reopened',
    data
  })
}

/**
 * 关闭缺陷
 *
 * @param id id
 * @returns
 */
export const close = (id: string) => {
  return request.put({
    url: url + '/closed/' + id
  })
}

/**
 * 删除缺陷
 *
 * @param id id
 * @returns
 */
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

/**
 * 获取评论
 *
 * @param bugId 缺陷编号
 * @returns
 */
export const getComment = (bugId: string | string[]) => {
  return request.get({
    url: url + '/comment',
    params: { bugId }
  })
}

/**
 * 新增评论
 *
 * @param data 数据
 * @returns
 */
export const addComment = (data: any) => {
  return request.post({
    url: url + '/comment',
    data
  })
}

/**
 * 删除评论
 *
 * @param id 编号
 * @returns
 */
export const removeComment = (id: number) => {
  return request.delete({
    url: url + '/comment/' + id
  })
}
