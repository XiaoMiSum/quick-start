import request from '@/config/axios'

const url = '/quality-center/bug/statistics'

// 获取缺陷统计分析数据
export const getStatistics = (params: any) => {
  return request.get({
    url,
    params
  })
}