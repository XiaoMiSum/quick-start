import request from '@/config/axios'

export type DictionaryVO = {
  id: number | undefined
  name: string
  code: string
  status: number
  source: string
}

const url = '/developer/dictionary'

export const getPage = (params: any) => {
  return request.get({ url, params })
}

export const addData = (data: any) => {
  return request.post({ url, data })
}

export const updateData = (data: any) => {
  return request.put({ url, data })
}

export const removeData = (id: any) => {
  return request.delete({
    url: url + '/' + id
  })
}

export const getSimple = () => {
  return request.get({
    url: url + '/simple'
  })
}
