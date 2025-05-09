export interface CaseVO {
  id?: String
  projectId: String
  nodeId: undefined | String
  title: any | String
  priority: String
  prerequisite?: any | String
  steps: CaseStep[]
  tags?: String[]
  supervisor?: number
  frontendDeveloper?: number | number[]
  backendDeveloper?: number | number[]
}

export type CaseStep = {
  step?: String
  expected?: String
  actual?: String
}
