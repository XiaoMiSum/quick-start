export interface CaseVO {
  id?: String
  projectId?: String
  nodeId: undefined | String
  name: any | String
  level: String
  prerequisite?: any | String
  steps: CaseStep[]
  tags?: String[]
  maintainer?: String
  reviewed?: String
}

export type CaseStep = {
  exec?: String
  expected?: String
  actual?: String
}
