<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item>
        <el-button
          v-hasPermi="['project:node:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
        <el-button
          v-hasPermi="['project:node:remove']"
          plain
          type="danger"
          :disabled="batchButtonVisible"
          @click="handleDelete(multipleSelection)"
        >
          <Icon class="mr-5px" icon="ep:delete" />
          删除
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          刷新
        </el-button>
      </el-form-item>
    </el-form>

    <el-table
      v-loading="loading"
      :data="list"
      highlight-current-row
      stripe
      :tree-props="treeProps"
      row-key="id"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="名称" prop="title" show-overflow-tooltip width="300" />
      <el-table-column label="路径" prop="path" show-overflow-tooltip />
      <el-table-column label="排序" prop="sort" width="100" />
      <el-table-column :width="250" align="center" label="操作">
        <template #default="scope">
          <el-button
            circle
            v-hasPermi="['project:node:add']"
            text
            type="primary"
            @click="openForm('create', scope.row.id)"
          >
            新增
          </el-button>

          <el-button
            circle
            text
            type="primary"
            v-hasPermi="['project:node:update']"
            @click="openForm('update', scope.row)"
          >
            编辑
          </el-button>

          <el-button
            circle
            text
            type="danger"
            v-hasPermi="['project:node:remove']"
            @click="handleDelete(scope.row.id)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </ContentWrap>

  <NodeForm
    ref="formRef"
    @success="handleQuery"
    :project-id="props.currentProject"
    :tree-data="list"
  />
</template>

<script lang="ts" setup>
import NodeForm from './NodeForm.vue'

import * as HTTP from '@/api/project/node'

import { handleTree } from '@/utils/tree'

const message = useMessage() // 消息弹窗

import { defineProps } from 'vue'

const props = defineProps({
  currentProject: String
})

const treeProps = ref({})

const { t } = useI18n() // 国际化

defineOptions({ name: 'ProjectManager' })

let multipleSelection = ref<number[]>([])

const queryParams = reactive({
  projectId: props.currentProject
})
const loading = ref(false)
const list = ref<any>([])

const batchButtonVisible = ref(true)

const queryFormRef = ref() // 搜索的表单

const getList = async () => {
  loading.value = true
  try {
    const data = await HTTP.getList(queryParams)
    list.value = handleTree(data)
  } finally {
    loading.value = false
  }
}

const handleSelectionChange = (val: any[]) => {
  batchButtonVisible.value = val.length === 0
  multipleSelection.value = Array.from(val, ({ id }) => id)
}

const handleQuery = async () => {
  getList()
}

const resetQuery = async () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

const handleDelete = async (ids: any) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    const params = Array.isArray(ids) ? ids.join(',') : ids
    await HTTP.removeData(params)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(async () => {
  await getList()
})
</script>

<style scoped></style>
