export interface EnumType {
  key: string
  label: string
  tag: string
}

export const ROLE_TYPE_ENUMS = [
  { key: 1, label: '系统内置', tag: 'info' },
  { key: 2, label: '自定义', tag: 'info' }
]

export const COMMON_STATUS_ENUMS = [
  { key: 1, label: '启用', tag: 'success' },
  { key: 0, label: '禁用', tag: 'danger' }
]

export const COMMON_STATUS_ENUM = { DISABLE: 0, ENABLE: 1 }

export const MENU_TYPE_ENUMS = [
  { key: 1, label: '目录', tag: 'danger' },
  { key: 2, label: '菜单', tag: 'success' },
  { key: 3, label: '按钮', tag: 'info' }
]

export const MENU_TYPE_ENUM = { DIR: 1, MENU: 2, BUTTON: 3 }

export const CASE_LEVEL_ENUMS = [
  { key: 'P0', label: 'P0', tag: 'danger' },
  { key: 'P1', label: 'P1', tag: 'warning' },
  { key: 'P2', label: 'P2', tag: 'success' },
  { key: 'P3', label: 'P3', tag: 'info' }
]

export const TEST_STAGE_ENUMS = [
  { key: 'SMOKE_TEST', label: '冒烟测试', tag: 'primary' },
  { key: 'INTEGRATION_TEST', label: '集成测试', tag: 'primary' },
  { key: 'SYSTEM_TEST', label: '系统测试', tag: 'primary' },
  { key: 'REGRESSION_TEST', label: '回归测试', tag: 'primary' },
  { key: 'ACCPETANCE_TEST', label: '验收测试', tag: 'primary' }
]

export const TESTCASE_STATUS = {
  Prepare: 'Prepare',
  Pass: 'Pass',
  Failure: 'Failure',
  Skip: 'Skip',
  Blocking: 'Blocking',
  Underway: 'Underway'
}

export const REVIEW_TESTCASE_STATUS = [
  { key: 'Prepare', label: '未评审', tag: 'warning' },
  { key: 'Pass', label: '通过评审', tag: 'success' },
  { key: 'Failure', label: '不通过', tag: 'danger' },
  { key: 'Skip', label: '跳过评审', tag: 'info' },
  { key: 'Underway', label: '进行中', tag: 'primary' }
]

export const TESTPLAN_TESTCASE_STATUS = [
  { key: 'Prepare', label: '未开始', tag: 'warning' },
  { key: 'Pass', label: '通过测试', tag: 'success' },
  { key: 'Failure', label: '不通过', tag: 'danger' },
  { key: 'Skip', label: '跳过测试', tag: 'info' },
  { key: 'Blocking', label: '阻塞测试', tag: 'danger' },
  { key: 'Underway', label: '进行中', tag: 'primary' }
]

export const REVIEW_STATUS = [
  { key: 'Prepare', label: '未评审', tag: 'warning' },
  { key: 'Underway', label: '进行中', tag: 'primary' },
  { key: 'Pass', label: '评审完成', tag: 'success' },
  { key: 'Failure', label: '评审完成', tag: 'danger' }
]

export const PLAN_STATUS = [
  { key: 'Prepare', label: '未开始', tag: 'warning' },
  { key: 'Underway', label: '进行中', tag: 'primary' },
  { key: 'Pass', label: '测试完成', tag: 'success' },
  { key: 'Failure', label: '测试完成', tag: 'danger' }
]

export const LINK_ENUMS = [
  { key: 'doc', label: '需求文档', tag: 'info' },
  { key: 'prototype', label: '项目原型', tag: 'info' },
  { key: 'wiki', label: 'Wiki', tag: 'info' },
  { key: 'ops', label: '运维相关', tag: 'primary' },
  { key: 'dev-env', label: '开发环境', tag: 'success' },
  { key: 'test-env', label: '测试环境', tag: 'success' },
  { key: 'uat-env', label: 'UAT环境', tag: 'warning' },
  { key: 'prod-env', label: '正式环境', tag: 'danger' },
  { key: 'database', label: '数据库', tag: 'warning' },
  { key: 'redis', label: 'Redis', tag: 'warning' },
  { key: 'other', label: '其他', tag: 'primary' }
]

export const ENV_ENUMS = [
  { key: 'dev', label: '开发环境', tag: 'info' },
  { key: 'test', label: '测试环境', tag: 'success' },
  { key: 'uat', label: 'UAT环境', tag: 'warning' },
  { key: 'prod', label: '正式环境', tag: 'danger' }
]

export const PROTOCOL_ENUMS = [
  { key: 'http', label: 'HTTP', tag: 'primary' },
  { key: 'https', label: 'HTTPS', tag: 'primary' }
]
