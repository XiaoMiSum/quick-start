// 全局通用状态枚举
export const CommonStatus = {
  ENABLE: 1, // 开启
  DISABLE: 0 // 禁用
}

/**
 * 菜单的类型枚举
 */
export const SystemMenuType = {
  DIR: 1, // 目录
  MENU: 2, // 菜单
  BUTTON: 3 // 按钮
}

/**
 * 任务状态的枚举
 */
export const InfraJobStatus = {
  INIT: 0, // 初始化中
  NORMAL: 1, // 运行中
  STOP: 2 // 暂停运行
}

/**
 * 用户的社交平台的类型枚举
 */
export const SystemUserSocialTypeEnum = {
  DINGTALK: {
    title: '钉钉',
    type: 20,
    source: 'dingtalk',
    img: 'https://s1.ax1x.com/2022/05/22/OzMDRs.png'
  },
  WECHAT_ENTERPRISE: {
    title: '企业微信',
    type: 30,
    source: 'wechat_enterprise',
    img: 'https://s1.ax1x.com/2022/05/22/OzMrzn.png'
  }
}
