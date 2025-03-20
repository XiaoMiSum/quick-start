import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'
import { DICT_TYPE } from '@/utils/dictionary'
import { dateFormatter } from '@/utils/formatTime'
import * as HTTP from '@/api/management/developer/error-log'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '应用名称',
    field: 'applicationName',
    component: 'Input'
  },
  {
    label: '请求来源',
    field: 'userIp'
  },
  {
    label: '异常时间',
    field: 'exceptionTime',
    formatter: dateFormatter
  },
  {
    label: '处理状态',
    field: 'status',
    dictCode: DICT_TYPE.INFRA_API_ERROR_LOG_PROCESS_STATUS,
    dictClass: 'number',
    search: {
      show: true,
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
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

export { tableObject }

export const { allSchemas } = useCrudSchemas(crudColumns)

export const { getList, setSearchParams } = tableMethods

/** 删除的操作 **/
export const handleRemove = async (id: number) => {
  try {
    // 操作的二次确认
    await message.delConfirm()
    // 执行操作
    await HTTP.removeData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
