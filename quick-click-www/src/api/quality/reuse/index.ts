import request from '@/utils/request'

// 获取复用记录列表
export const getRecords = (originalCaseId: string) => {
  return request.get({
    url: '/quality-center/reuse/records',
    params: { originalCaseId }
  })
}

// 获取复用记录分页列表
export const getPage = (targetId: string, targetType: string) => {
  return request.get({
    url: '/quality-center/reuse/page',
    params: { targetId, targetType }
  })
}

// 统计用例复用次数
export const countReuse = (originalCaseId: string) => {
  return request.get({
    url: '/quality-center/reuse/count',
    params: { originalCaseId }
  })
}

// 获取最常复用的用例列表
export const getMostReusedCases = (projectId: string, limit: number = 10) => {
  return request.get({
    url: '/quality-center/reuse/most-reused',
    params: { projectId, limit }
  })
}