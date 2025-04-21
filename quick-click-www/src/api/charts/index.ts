import request from '@/config/axios'

const url = '/charts'

// 获取合格率排名
export const getProjectRank = (params: any, type: string) => {
  return request.get({
    url: url + '/project/rank/' + type,
    params
  })
}

// 获取合格率推移图
export const getProjectTrend = (type: string) => {
  return request.get({ url: url + '/project/trend/' + type })
}

// 获取开发人员缺陷数排名
export const getDeveloperRank = (params: any, type: string) => {
  return request.get({ url: url + '/developer/rank/' + type, params })
}

// 获取项目缺陷处理情况
export const getProjectBug = (params: any, type: string) => {
  return request.get({ url: url + '/project/bug/' + type, params })
}

// 获取缺陷激活情况
export const getReopenedBugs = (params: any) => {
  return request.get({ url: url + '/developer/reopened', params })
}

// 获取  缺陷总数 推移
export const getBugTrend = (params: any, type: string) => {
  return request.get({ url: url + '/bug/trend/' + type, params })
}

// 获取  缺陷修复成本
export const getBugsMoney = (params: any) => {
  return request.get({ url: url + '/bug/money', params })
}

// 获取  开发人员缺陷率
export const getBugsRate = (params: any) => {
  return request.get({ url: url + '/bug/rate', params })
}

// 获取  开发人员缺陷率明细
export const getBugsRateDetail = (params: any) => {
  return request.get({ url: url + '/bug/rate/detail', params })
}
