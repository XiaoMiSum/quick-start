import request from '@/config/axios'

const url = '/infra/area'

// 上传文件
export const getAreas = () => {
  return request.get({ url: url + '/tree' })
}
