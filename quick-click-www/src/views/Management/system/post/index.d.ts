import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'

import { dateFormatter } from '@/utils/formatTime'
import { CommonStatus } from '@/utils/constants'
import { DICT_TYPE } from '@/utils/dictionary'

import * as PostApi from '@/api/management/system/post'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '岗位名称',
    field: 'name',
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
    label: '岗位编码',
    field: 'code',
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
    label: '显示顺序',
    field: 'sort'
  },
  {
    label: '状态',
    field: 'status',
    dictCode: DICT_TYPE.COMMON_STATUS,
    dictClass: 'number'
  },
  {
    label: '备注',
    field: 'remark',
    table: {
      align: 'left',
      headerAlign: 'left'
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

export const { tableMethods, tableObject } = useTable({
  getListApi: PostApi.listData
})

/** 删除按钮操作 */
export const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await HTTP.delData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

export const { getList, setSearchParams } = tableMethods
export const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
export const { allSchemas } = useCrudSchemas(crudColumns)
