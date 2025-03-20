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
/** Titan 订单状态 */
export const TitanOrderStatus = { FAILED: 1, SUCCESSFUL: 0, PENDING: 2 }

/** Titan 交易策略 */
export const TitanTransferStrategy = [
  { value: 'form', label: '表单跳转', params: [] },
  {
    value: 'scan_code',
    label: '扫码支付',
    params: [
      { value: 'app', name: 'App名称', text: '支付宝、微信、云闪付' },
      { value: 'logo', name: 'LOGO', text: 'APP品牌LOGO' }
    ]
  }
]

/**
 * 商品 SPU 状态
 */
export const ProductSpuStatusEnum = {
  RECYCLE: {
    status: -1,
    name: '回收站'
  },
  DISABLE: {
    status: 0,
    name: '下架'
  },
  ENABLE: {
    status: 1,
    name: '上架'
  }
}

/**
 * 优惠劵模板的有限期类型的枚举
 */
export const CouponTemplateValidityTypeEnum = {
  DATE: {
    type: 1,
    name: '固定日期可用'
  },
  TERM: {
    type: 2,
    name: '领取之后可用'
  }
}

/**
 * 优惠劵模板的领取方式的枚举
 */
export const CouponTemplateTakeTypeEnum = {
  USER: {
    type: 1,
    name: '直接领取'
  },
  ADMIN: {
    type: 2,
    name: '指定发放'
  },
  REGISTER: {
    type: 3,
    name: '新人券'
  }
}

/**
 * 营销的商品范围枚举
 */
export const PromotionProductScope = {
  ALL: {
    scope: 1,
    name: '通用劵'
  },
  SPU: {
    scope: 2,
    name: '商品劵'
  },
  CATEGORY: {
    scope: 3,
    name: '品类劵'
  }
}

/**
 * 营销的条件类型枚举
 */
export const PromotionConditionType = {
  PRICE: {
    type: 10,
    name: '满 N 元'
  },
  COUNT: {
    type: 20,
    name: '满 N 件'
  }
}

/**
 * 优惠类型枚举
 */
export const PromotionDiscountTypeEnum = {
  PRICE: {
    type: 1,
    name: '满减'
  },
  PERCENT: {
    type: 2,
    name: '折扣'
  }
}

/**
 * 分销关系绑定模式枚举
 */
export const BrokerageBindModeEnum = {
  ANYTIME: {
    mode: 0,
    name: '没有推广人'
  },
  REGISTER: {
    mode: 1,
    name: '新用户'
  }
}
/**
 * 分佣模式枚举
 */
export const BrokerageEnabledConditionEnum = {
  ALL: {
    condition: 0,
    name: '人人分销'
  },
  ADMIN: {
    condition: 1,
    name: '指定分销'
  }
}
/**
 * 佣金记录业务类型枚举
 */
export const BrokerageRecordBizTypeEnum = {
  ORDER: {
    type: 1,
    name: '获得推广佣金'
  },
  WITHDRAW: {
    type: 2,
    name: '提现申请'
  }
}
/**
 * 佣金提现状态枚举
 */
export const BrokerageWithdrawStatusEnum = {
  AUDITING: {
    status: 0,
    name: '审核中'
  },
  AUDIT_SUCCESS: {
    status: 10,
    name: '审核通过'
  },
  AUDIT_FAIL: {
    status: 20,
    name: '审核不通过'
  },
  WITHDRAW_SUCCESS: {
    status: 11,
    name: '提现成功'
  },
  WITHDRAW_FAIL: {
    status: 21,
    name: '提现失败'
  }
}
/**
 * 佣金提现类型枚举
 */
export const BrokerageWithdrawTypeEnum = {
  WALLET: {
    type: 1,
    name: '钱包'
  },
  BANK: {
    type: 2,
    name: '银行卡'
  },
  WECHAT: {
    type: 3,
    name: '微信'
  },
  ALIPAY: {
    type: 4,
    name: '支付宝'
  }
}

/**
 * 配送方式枚举
 */
export const DeliveryType = {
  EXPRESS: {
    type: 1,
    name: '快递发货'
  },
  PICK_UP: {
    type: 2,
    name: '到店自提'
  }
}

/**
 * 交易订单 - 状态
 */
export const TradeOrderStatus = {
  UNPAID: {
    status: 0,
    name: '待支付'
  },
  UNDELIVERED: {
    status: 10,
    name: '待发货'
  },
  DELIVERED: {
    status: 20,
    name: '已发货'
  },
  COMPLETED: {
    status: 30,
    name: '已完成'
  },
  CANCELED: {
    status: 40,
    name: '已取消'
  }
}
