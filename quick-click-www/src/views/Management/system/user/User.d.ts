import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'

import { dateFormatter } from '@/utils/formatTime'
import { CommonStatus } from '@/utils/constants'
import { DICT_TYPE } from '@/utils/dictionary'

import * as UserApi from '@/api/management/system/user'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '登录名或姓名',
    hiddenLabel: true,
    field: 'username',
    search: {
      show: true,
      hiddenLabel: true
    },
    table: {
      show: false
    }
  },
  {
    label: '登录名',
    field: 'username',
    table: {
      align: 'left',
      headerAlign: 'left'
    }
  },
  {
    label: '用户姓名',
    field: 'name'
  },
  {
    label: '部门',
    field: 'dept.name'
  },
  {
    label: '状态',
    field: 'status',
    dictCode: DICT_TYPE.COMMON_STATUS,
    dictClass: 'number'
  },
  {
    label: '创建时间',
    field: 'createTime',
    formatter: dateFormatter
  },
  {
    label: '操作',
    field: 'action',
    table: {
      width: 200
    }
  }
])

export const HTTP = UserApi
export const message = useMessage() // 消息弹窗
export const { allSchemas } = useCrudSchemas(crudColumns)
