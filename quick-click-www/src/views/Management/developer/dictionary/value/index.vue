<template>
  <ContentWrap>
    <Search
      :schema="allSchemas.searchSchema"
      @search="setSearchParams"
      :show-reset="false"
      :model="queryParams"
    />
    <el-row class="mt-10px" :gutter="10">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['developer:dictionary:value:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <Table
      :columns="allSchemas.tableColumns"
      :loading="tableObject.loading"
      :data="tableObject.tableList"
      :pagination="{
        background: true,
        total: tableObject.total
      }"
      v-model:pageSize="tableObject.pageSize"
      v-model:currentPage="tableObject.currentPage"
    >
      <template #action="{ row }">
        <div class="flex items-center justify-center">
          <el-button
            link
            type="primary"
            @click="openForm('update', row)"
            v-hasPermi="['developer:dictionary:value:update']"
          >
            修改
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(row.id)"
            v-hasPermi="['developer:dictionary:value:remove']"
          >
            删除
          </el-button>
        </div>
      </template>
    </Table>
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <DictDataForm ref="formRef" @success="getList" />
</template>
<script lang="ts" setup>
import * as VALUE_HTTP from '@/api/management/developer/dictionary/value'
import DictDataForm from './ValueForm.vue'

import { allSchemas } from './Dictionary.Value.d'

const { tableMethods, tableObject } = useTable({
  getListApi: VALUE_HTTP.getPage
})

const { getList, setSearchParams } = tableMethods

defineOptions({ name: 'SystemDictData' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const route = useRoute() // 路由

const queryParams = reactive({
  dictCode: route.query.dictCode,
  label: ''
})

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, data?: any) => {
  formRef.value.open(type, data, queryParams.dictCode)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await VALUE_HTTP.removeData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(async () => {
  await setSearchParams(queryParams)
})
</script>
