import { reactive } from 'vue'
import { AxiosPromise } from 'axios'
import { findIndex } from '@/utils'
import { eachTree, filter, treeMap } from '@/utils/tree'

import { FormSchema } from '@/types/form'
import { TableColumn } from '@/types/table'
import { DescriptionsSchema } from '@/types/descriptions'
import { ComponentOptions, ComponentProps } from '@/types/components'
import { cloneDeep, merge } from 'lodash-es'
import { getBoolDictOptions, getDictOptions, getIntDictOptions } from '@/utils/dictionary'
import OnesTag from '@/components/OnesTag/src/OnesTag.vue'

export type CrudSchema = Omit<TableColumn, 'children'> & {
  search?: CrudSearchParams // 查询的详细配置
  table?: CrudTableParams // 列表的详细配置
  form?: CrudFormParams // 表单的详细配置
  detail?: CrudDescriptionsParams // 详情的详细配置
  children?: CrudSchema[]
  dictCode?: string // 字典类型
  dictClass?: 'string' | 'number' | 'boolean' // 字典数据类型 string | number | boolean
}

type CrudSearchParams = {
  // 是否显示在查询项
  show?: boolean
  // 是否显示表单Label
  hiddenLabel?: boolean
  // 接口
  api?: () => Promise<any>
  // 搜索字段
  field?: string
} & Omit<FormSchema, 'field'>

type CrudTableParams = {
  // 是否显示表头
  show?: boolean
  // 列宽配置
  width?: number | string
  // 列是否固定在左侧或者右侧
  fixed?: 'left' | 'right'
} & Omit<FormSchema, 'field'>
type CrudFormParams = {
  // 是否显示表单项
  show?: boolean
  // 接口
  api?: () => Promise<any>
} & Omit<FormSchema, 'field'>

type CrudDescriptionsParams = {
  // 是否显示表单项
  show?: boolean
} & Omit<DescriptionsSchema, 'field'>

interface AllSchemas {
  searchSchema: FormSchema[]
  tableColumns: TableColumn[]
  formSchema: FormSchema[]
  detailSchema: DescriptionsSchema[]
}

const { t } = useI18n()

// 过滤所有结构
export const useCrudSchemas = (
  crudSchema: CrudSchema[]
): {
  allSchemas: AllSchemas
} => {
  // 所有结构数据
  const allSchemas = reactive<AllSchemas>({
    searchSchema: [],
    tableColumns: [],
    formSchema: [],
    detailSchema: []
  })

  const searchSchema = filterSearchSchema(crudSchema, allSchemas)
  allSchemas.searchSchema = searchSchema || []

  const tableColumns = filterTableSchema(crudSchema)
  allSchemas.tableColumns = tableColumns || []

  const formSchema = filterFormSchema(crudSchema, allSchemas)
  allSchemas.formSchema = formSchema

  const detailSchema = filterDescriptionsSchema(crudSchema)
  allSchemas.detailSchema = detailSchema

  return {
    allSchemas
  }
}

// 过滤 Search 结构
const filterSearchSchema = (crudSchema: CrudSchema[], allSchemas: AllSchemas): FormSchema[] => {
  const searchSchema: FormSchema[] = []

  // 获取字典列表队列
  const searchRequestTask: Array<() => Promise<void>> = []
  eachTree(crudSchema, (schemaItem: CrudSchema) => {
    // 判断是否显示
    if (schemaItem.search?.show) {
      let component = schemaItem?.search?.component || 'Input'
      const options: ComponentOptions[] = []
      const comonentProps: ComponentProps =
        schemaItem.componentProps || schemaItem.search?.componentProps || {}
      if (schemaItem.dictCode) {
        const allOptions: ComponentOptions = { label: '全部', value: '' }
        options.push(allOptions)
        getDictOptions(schemaItem.dictCode).forEach((item: any) => {
          options.push(item)
        })
        comonentProps.options = options
        if (!schemaItem.search?.component) component = 'Select'
      }

      // updated by AKing: 解决了当使用默认的dict选项时，form中事件不能触发的问题
      const searchSchemaItem = merge(
        {
          // 默认为 input
          component,
          ...schemaItem.search,
          labelMessage: schemaItem.labelMessage,
          field: schemaItem.field,
          label: schemaItem.search?.label || schemaItem.label
        },
        { componentProps: comonentProps }
      )

      if (searchSchemaItem.api) {
        searchRequestTask.push(async () => {
          const res = await (searchSchemaItem.api as () => AxiosPromise)()
          if (res) {
            const index = findIndex(allSchemas.searchSchema, (v: FormSchema) => {
              return v.field === searchSchemaItem.field
            })
            if (index !== -1) {
              allSchemas.searchSchema[index]!.componentProps!.options = filterOptions(
                res,
                searchSchemaItem.componentProps.optionsAlias?.labelField
              )
            }
          }
        })
      }
      // 删除不必要的字段
      delete searchSchemaItem.show
      searchSchema.push(searchSchemaItem)
    }
  })
  for (const task of searchRequestTask) {
    task()
  }
  return searchSchema
}

// 过滤 table 结构
const filterTableSchema = (crudSchema: CrudSchema[]): TableColumn[] => {
  const tableColumns = treeMap<CrudSchema>(crudSchema, {
    conversion: (schema: CrudSchema) => {
      if (schema?.table?.show !== false) {
        // add by 芋艿：增加对 enums 数据的支持
        if (!schema.formatter && schema.dictCode) {
          schema.formatter = (_: Recordable, __: TableColumn, cellValue: any) => {
            return h(OnesTag, {
              type: schema.dictCode!, // ! 表示一定不为空
              value: cellValue
            })
          }
        }
        return {
          ...schema.table,
          ...schema
        }
      }
    }
  })

  // 第一次过滤会有 undefined 所以需要二次过滤
  return filter<TableColumn>(tableColumns as TableColumn[], (data) => {
    if (data.children === void 0) {
      delete data.children
    }
    return !!data.field
  })
}

// 过滤 form 结构
const filterFormSchema = (crudSchema: CrudSchema[], allSchemas: AllSchemas): FormSchema[] => {
  const formSchema: FormSchema[] = []

  // 获取字典列表队列
  const formRequestTask: Array<() => Promise<void>> = []

  eachTree(crudSchema, (schemaItem: CrudSchema) => {
    // 判断是否显示
    if (schemaItem?.form?.show !== false) {
      let component = schemaItem?.form?.component || 'Input'
      let defaultValue: any = ''
      if (schemaItem.form?.value) {
        defaultValue = schemaItem.form?.value
      } else {
        if (component === 'InputNumber') {
          defaultValue = 0
        }
      }
      let comonentProps: ComponentProps = {}
      if (schemaItem.dictCode) {
        const options: ComponentOptions[] = []
        if (schemaItem.dictClass && schemaItem.dictClass === 'number') {
          getIntDictOptions(schemaItem.dictCode).forEach((item) => {
            options.push(item)
          })
        } else if (schemaItem.dictClass && schemaItem.dictClass === 'boolean') {
          getBoolDictOptions(schemaItem.dictCode).forEach((item) => {
            options.push(item)
          })
        } else {
          getDictOptions(schemaItem.dictCode).forEach((item: any) => {
            options.push(item)
          })
        }
        comonentProps = {
          options: options
        }
        if (!(schemaItem.form && schemaItem.form.component)) component = 'Select'
      }

      // updated by AKing: 解决了当使用默认的dict选项时，form中事件不能触发的问题
      const formSchemaItem = merge(
        {
          // 默认为 input
          component,
          value: defaultValue,
          ...schemaItem.form,
          field: schemaItem.field,
          label: schemaItem.form?.label || schemaItem.label
        },
        { componentProps: comonentProps }
      )

      if (formSchemaItem.api) {
        formRequestTask.push(async () => {
          const res = await (formSchemaItem.api as () => AxiosPromise)()
          if (res) {
            const index = findIndex(allSchemas.formSchema, (v: FormSchema) => {
              return v.field === formSchemaItem.field
            })
            if (index !== -1) {
              allSchemas.formSchema[index]!.componentProps!.options = filterOptions(
                res,
                formSchemaItem.componentProps.optionsAlias?.labelField
              )
            }
          }
        })
      }

      // 删除不必要的字段
      delete formSchemaItem.show

      formSchema.push(formSchemaItem)
    }
  })

  for (const task of formRequestTask) {
    task()
  }
  return formSchema
}

// 过滤 descriptions 结构
const filterDescriptionsSchema = (crudSchema: CrudSchema[]): DescriptionsSchema[] => {
  const descriptionsSchema: FormSchema[] = []

  eachTree(crudSchema, (schemaItem: CrudSchema) => {
    // 判断是否显示
    if (schemaItem.detail?.show !== false) {
      const descriptionsSchemaItem = {
        ...schemaItem.detail,
        field: schemaItem.field,
        label: schemaItem.detail?.label || schemaItem.label
      }
      if (!schemaItem.formatter && schemaItem.dictCode) {
        schemaItem.formatter = (_: Recordable, __: TableColumn, cellValue: any) => {
          return h(OnesTag, {
            type: schemaItem.dictCode!, // ! 表示一定不为空
            value: cellValue
          })
        }
      }
      if (schemaItem.detail?.dateFormat || schemaItem.formatter == 'formatDate') {
        // 优先使用 detail 下的配置，如果没有默认为 YYYY-MM-DD HH:mm:ss
        descriptionsSchemaItem.dateFormat = schemaItem?.detail?.dateFormat
          ? schemaItem?.detail?.dateFormat
          : 'YYYY-MM-DD HH:mm:ss'
      }

      // 删除不必要的字段
      delete descriptionsSchemaItem.show

      descriptionsSchema.push(descriptionsSchemaItem)
    }
  })

  return descriptionsSchema
}

// 给options添加国际化
const filterOptions = (options: Recordable, labelField?: string) => {
  return options?.map((v: Recordable) => {
    if (labelField) {
      v['labelField'] = t(v.labelField)
    } else {
      v['label'] = t(v.label)
    }
    return v
  })
}

// 将 tableColumns 指定 fields 放到最前面
export const sortTableColumns = (tableColumns: TableColumn[], field: string) => {
  const fieldIndex = tableColumns.findIndex((item) => item.field === field)
  const fieldColumn = cloneDeep(tableColumns[fieldIndex])
  tableColumns.splice(fieldIndex, 1)
  // 添加到开头
  tableColumns.unshift(fieldColumn)
}
