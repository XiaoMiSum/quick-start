import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'
import { InfraJobStatus } from '@/utils/constants'
import { DICT_TYPE } from '@/utils/dictionary'

import * as HTTP from '@/api/management/developer/job'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '任务名称',
    field: 'name',
    component: 'Input',
    search: {
      show: true,
      hiddenLabel: true
    }
  },
  {
    label: '处理器',
    field: 'handlerName',
    align: 'left',
    headerAlign: 'left',
    component: 'Input',
    search: {
      show: true,
      hiddenLabel: true
    }
  },
  {
    label: '处理器参数',
    field: 'handlerParam',
    align: 'left',
    headerAlign: 'left',
    component: 'Input'
  },
  {
    label: 'CRON',
    field: 'cronExpression'
  },
  {
    label: '文件类型',
    field: 'type'
  },
  {
    label: '状态',
    field: 'status',
    dictCode: DICT_TYPE.INFRA_JOB_STATUS,
    dictClass: 'number'
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
  getListApi: HTTP.listJob
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
    await HTTP.delJob(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 修改状态操作 */
export const handleChangeStatus = async (row: any) => {
  try {
    // 修改状态的二次确认
    const text = row.status === InfraJobStatus.STOP ? '开启' : '关闭'
    await message.confirm('确认要' + text + '定时任务 为"' + row.name + '"?', t('common.reminder'))
    const status = row.status === InfraJobStatus.STOP ? InfraJobStatus.NORMAL : InfraJobStatus.STOP
    await HTTP.updateJobStatus(row.id, status)
    message.success(text + '成功')
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status = row.status === InfraJobStatus.STOP ? InfraJobStatus.NORMAL : InfraJobStatus.STOP
  }
}
/** 执行一次 */
export const handleRun = async (row: any) => {
  try {
    // 二次确认
    await message.confirm('确认要立即执行一次' + row.name + '?', t('common.reminder'))
    // 提交执行
    await JobApi.runJob(row.id)
    message.success('执行成功')
    // 刷新列表
    await getList()
  } catch {}
}
