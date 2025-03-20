import request from '@/config/axios'

const url = '/developer/dictionary/value'

export type DictionaryValueVO = {
  id: number | undefined
  sort: number | undefined
  label: string
  value: string
  dictCode: string
  colorType: string
}

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
