import request from '@/config/axios'

const url = '/charts/days'

// 查询开发人员每日数据
export const getDeveloper = (params: any) => {
  return request.get({
    url: url + '/developer',
    params
  })
}

// 更新开发人员每日数据
export const updateDeveloper = (data: any) => {
  return request.put({
    url: url + '/developer',
    data
  })
}
