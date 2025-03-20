import { TableColumn } from '@/components/Table'

import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'
import { formatDate } from '@/utils/formatTime'
import { DICT_TYPE } from '@/utils/dictionary'

import * as HTTP from '@/api/management/developer/job/jobLog'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '日志编号',
    field: 'id',
    component: 'Input'
  },
  {
    label: '任务编号',
    field: 'jobId',
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
    label: '第几次执行',
    field: 'executeIndex'
  },
  {
    label: '执行时间',
    field: 'beginTime',
    formatter: (data: Recordable, _: TableColumn, cellValue: number) => {
      return formatDate(data.beginTime) + ' ~ ' + formatDate(data.endTime)
    }
  },
  {
    label: '执行时长',
    field: 'duration',
    formatter: (_: Recordable, __: TableColumn, cellValue: number) => {
      return cellValue + ' 毫秒'
    }
  },
  {
    label: '状态',
    field: 'status',
    dictCode: DICT_TYPE.INFRA_JOB_LOG_STATUS,
    dictClass: 'number'
  },
  {
    label: '操作',
    field: 'action',
    table: {
      width: 160
    }
  }
])

const { tableMethods, tableObject } = useTable({
  getListApi: HTTP.getJobLogPage
})
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

export { tableObject }

export const { allSchemas } = useCrudSchemas(crudColumns)

export const { getList, setSearchParams } = tableMethods
