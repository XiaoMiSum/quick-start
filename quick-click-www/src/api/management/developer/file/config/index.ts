import request from '@/config/axios'

const url = '/developer/file/config'

export interface FileClientConfig {
  basePath: string
  host?: string
  port?: number
  username?: string
  password?: string
  mode?: string
  endpoint?: string
  bucket?: string
  accessKey?: string
  accessSecret?: string
  domain: string
}

export interface FileConfigVO {
  id: number
  name: string
  storage?: number
  master: boolean
  visible: boolean
  config: FileClientConfig
  remark: string
  createTime: Date
}

// 查询文件配置列表
export const getFileConfigPage = (params: PageParam) => {
  return request.get({ url, params })
}

// 查询文件配置详情
export const getFileConfig = (id: number) => {
  return request.get({ url: url + '/' + id })
}

// 更新文件配置为主配置
export const updateFileConfigMaster = (id: number) => {
  return request.put({ url: url + '/master?id=' + id })
}

// 新增文件配置
export const createFileConfig = (data: FileConfigVO) => {
  return request.post({ url, data })
}

// 修改文件配置
export const updateFileConfig = (data: FileConfigVO) => {
  return request.put({ url, data })
}

// 删除文件配置
export const deleteFileConfig = (id: number) => {
  return request.delete({ url: url + '/' + id })
}

// 测试文件配置
export const testFileConfig = (id: number) => {
  return request.get({ url: url + '/test?id=' + id })
}
