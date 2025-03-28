import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'
import { dateFormatter } from '@/utils/formatTime'
import { DICT_TYPE } from '@/utils/dictionary'

import * as HTTP from '@/api/management/developer/file/config'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '配置名',
    field: 'name'
  },
  {
    label: '存储器',
    field: 'storage',
    dictCode: DICT_TYPE.INFRA_FILE_STORAGE,
    search: {
      show: true,
      formItemProps: { style: { width: '200px' } },
      hiddenLabel: true
    }
  },
  {
    label: '备注',
    field: 'remark'
  },
  {
    label: '主配置',
    field: 'master',
    dictCode: DICT_TYPE.INFRA_BOOLEAN_STRING,
    search: {
      formItemProps: { style: { width: '200px' } },
      show: true,
      hiddenLabel: true
    }
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

const { tableMethods, tableObject } = useTable({
  getListApi: HTTP.getFileConfigPage
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
    await HTTP.deleteFileConfig(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch (e) {
    console.log(e)
  }
}

/** 主配置按钮操作 */
export const handleMaster = async (id) => {
  try {
    await message.confirm('是否确认修改配置编号为"' + id + '"的数据项为主配置?')
    await HTTP.updateFileConfigMaster(id)
    message.success(t('common.updateSuccess'))
    await getList()
  } catch (e) {
    console.log(e)
  }
}

/** 测试按钮操作 */
export const handleTest = async (id) => {
  try {
    const response = await HTTP.testFileConfig(id)
    message.alert('测试通过，上传文件成功！访问地址：' + response)
  } catch (e) {
    console.log(e)
  }
}
