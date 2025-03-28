import { TableColumn } from '@/components/Table'

import type { CrudSchema } from '@/hooks/web/useCrudSchemas'
import { useCrudSchemas } from '@/hooks/web/useCrudSchemas'

import { fileSizeFormatter } from '@/utils'
import { dateFormatter } from '@/utils/formatTime'

import * as HTTP from '@/api/management/developer/file'

const crudColumns = reactive<CrudSchema[]>([
  {
    label: '文件名',
    field: 'name',
    component: 'Input'
  },
  {
    label: '文件路径',
    field: 'path',
    component: 'Input',
    formItemProps: { style: { width: '200px' } },
    search: {
      show: true,
      hiddenLabel: true
    }
  },
  {
    label: 'URL',
    field: 'url',
    component: 'Input'
  },
  {
    label: '文件大小',
    field: 'size',
    formatter: fileSizeFormatter
  },
  {
    label: '文件类型',
    field: 'type',
    formItemProps: { style: { width: '200px' } },
    search: {
      show: true,
      hiddenLabel: true
    }
  },
  {
    label: '文件内容',
    field: 'url',
    formatter: (data: Recordable, __: TableColumn, cellValue: any) => {
      return
      data.type.includes('image')
        ? h(ElImage, {
            class: 'h-80px w-80px',
            lazy: true,
            src: cellValue,
            previewSrcList: [cellValue],
            previewTeleported: true,
            fit: 'cover'
          })
        : data.type.includes('pdf')
          ? h(ElLink, {
              text: '预览',
              type: 'primary',
              href: cellValue,
              underline: false,
              target: '_blank'
            })
          : h(ElLink, {
              text: '下载',
              type: 'primary',
              href: cellValue,
              download: true,
              underline: false,
              target: '_blank'
            })
    },
    form: {
      show: false
    }
  },
  {
    label: '上传时间',
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
  getListApi: HTTP.getFilePage
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
    await HTTP.deleteFile(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
