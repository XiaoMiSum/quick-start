import request from '@/config/axios'

const url = '/quality-center/report'

// 获取测试报告列表
export const getReportList = (params: any) => {
  return request.get({
    url,
    params
  })
}

// 获取测试报告详情
export const getReportDetail = (id: string) => {
  return request.get({
    url: `${url}/${id}`
  })
}

// 生成测试报告
export const generateReport = (data: any) => {
  return request.post({
    url: `${url}/generate`,
    data
  })
}

// 删除测试报告
export const deleteReport = (id: string) => {
  return request.delete({
    url: `${url}/${id}`
  })
}