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

export const RESULT_ENUMS = [
  { key: 'NOTSTART', label: '未开始', tag: 'info' },
  { key: 'UNREVIEWED', label: '未评审', tag: 'info' },
  { key: 'ONGOING', label: '进行中', tag: 'success' },
  { key: 'PASSED', label: '已通过', tag: 'success' },
  { key: 'NOPASSED', label: '不通过', tag: 'danger' }
]

export const LINK_ENUMS = [
  { key: 'doc', label: '需求文档', tag: 'info' },
  { key: 'prototype', label: '项目原型', tag: 'success' },
  { key: 'wiki', label: 'Wiki', tag: 'danger' },
  { key: 'ops', label: '运维相关', tag: 'danger' },
  { key: 'dev-env', label: '开发环境', tag: 'danger' },
  { key: 'test-env', label: '测试环境', tag: 'danger' },
  { key: 'uat-env', label: 'UAT环境', tag: 'danger' },
  { key: 'prod-env', label: '正式环境', tag: 'danger' },
  { key: 'database', label: '数据库', tag: 'danger' },
  { key: 'redis', label: 'Redis', tag: 'danger' },
  { key: 'other', label: '其他', tag: 'danger' }
]

export const ENV_ENUMS = [
  { key: 'dev', label: '开发环境', tag: 'danger' },
  { key: 'test', label: '测试环境', tag: 'danger' },
  { key: 'uat', label: 'UAT环境', tag: 'danger' },
  { key: 'prod', label: '正式环境', tag: 'danger' }
]

export const PROTOCOL_ENUMS = [
  { key: 'http', label: 'HTTP', tag: 'danger' },
  { key: 'https', label: 'HTTPS', tag: 'danger' }
]
