import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'
import { DICT_TYPE } from '@/utils/dictionary'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '菜单名称',
    field: 'name',
    component: 'Input',
    search: {
      show: true,
      hiddenLabel: true
    },
    table: {
      align: 'left',
      headerAlign: 'left'
    }
  },
  {
    label: '权限标识',
    field: 'permission',
    table: {
      align: 'left',
      headerAlign: 'left'
    }
  },
  {
    label: '组件路径',
    field: 'component',
    table: {
      align: 'left',
      headerAlign: 'left'
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
    field: 'sort',
    table: {
      align: 'right',
      headerAlign: 'right'
    }
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
