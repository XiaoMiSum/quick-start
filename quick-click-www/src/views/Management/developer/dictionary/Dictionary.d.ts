import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'
import { DICT_TYPE } from '@/utils/dictionary'
import * as HTTP from '@/api/management/developer/dictionary'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '字典名称',
    field: 'name',
    component: 'Input',
    search: {
      show: true,
      hiddenLabel: true
    },
    table: {
      headerAlign: 'left',
      align: 'left'
    }
  },
  {
    label: '字典编码',
    field: 'code',
    align: 'left',
    headerAlign: 'left',
    search: {
      show: true,
      hiddenLabel: true
    }
  },
  {
    label: '来源',
    field: 'source',
    search: {
      show: true,
      hiddenLabel: true
    }
  },
  {
    label: '状态',
    field: 'status',
    dictCode: DICT_TYPE.COMMON_STATUS,
    dictClass: 'number',
    search: {
      show: true,
      formItemProps: { style: { width: '200px' } },
      hiddenLabel: true
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

const { tableMethods, tableObject } = useTable({
  getListApi: HTTP.getPage
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

export const { allSchemas } = useCrudSchemas(crudColumns)

export const { getList, setSearchParams } = tableMethods

export { tableObject }
/** 删除按钮操作 */
export const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await HTTP.removeData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
