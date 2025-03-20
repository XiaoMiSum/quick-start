import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'
import { DICT_TYPE } from '@/utils/dictionary'
import * as DICT_HTTP from '@/api/management/developer/dictionary'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '字典编码',
    field: 'dictCode',
    component: 'Input',
    search: {
      show: true,
      component: 'Select',
      api: DICT_HTTP.getSimple,
      hiddenLabel: true
    },
    table: {
      show: false
    }
  },
  {
    label: '字典标签',
    field: 'label',
    search: {
      show: true,
      hiddenLabel: true
    }
  },
  {
    label: '字典键值',
    field: 'value',
    search: {
      show: true,
      hiddenLabel: true
    }
  },
  {
    label: '状态',
    field: 'status',
    dictCode: DICT_TYPE.COMMON_STATUS,
    dictClass: 'number'
  },
  {
    label: '排序',
    field: 'sort'
  },
  {
    label: '标签颜色',
    field: 'colorType'
  },
  {
    label: '操作',
    field: 'action',
    table: {
      width: 200
    }
  }
])
export const message = useMessage() // 消息弹窗

export const { allSchemas } = useCrudSchemas(crudColumns)
