import request from '@/config/axios'

export interface RequirementVO {
  id: string
  title: string
  description: string
  projectId: string
  projectName: string
  moduleId: string
  moduleName: string
  prototypeUrl: string
  createTime: string
  updateTime: string
}

export interface RequirementPageReqVO {
  pageNo: number
  pageSize: number
  title?: string
  projectId?: string
  moduleId?: string
}

// 分页获取需求列表
export const getRequirementPage = (params: RequirementPageReqVO) => {
  return request.get({ url: '/project/requirement/page', params })
}

// 获取需求列表
export const getRequirementList = (params: any) => {
  return request.get({ url: '/project/requirement', params })
}

// 获取需求详情
export const getRequirement = (id: string) => {
  return request.get({ url: `/project/requirement/${id}` })
}

// 创建需求
export const createRequirement = (data: RequirementVO) => {
  return request.post({ url: '/project/requirement', data })
}

// 更新需求
export const updateRequirement = (data: RequirementVO) => {
  return request.put({ url: `/project/requirement`, data })
}

// 删除需求
export const deleteRequirement = (id: string) => {
  return request.delete({ url: `/project/requirement/${id}` })
}