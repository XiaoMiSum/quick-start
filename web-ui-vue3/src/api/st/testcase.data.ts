export interface CaseVO {
  id?: Number
  projectId?: Number
  moduleId: undefined | Number
  name: any | String
  level: String
  precondition?: any | String
  steps: CaseStep[]
  tags?: String[]
  chargeUserId?: Number
  reviewed?: String
}

export type CaseStep = {
  exec?: String
  expected?: String
  actual?: String
}
