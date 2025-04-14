<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="" prop="memberName">
        <el-input
          v-model="queryParams.memberName"
          class="!w-240px"
          clearable
          placeholder="请输入用户名称"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
        <el-button
          v-hasPermi="['project:member:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
        <el-button
          v-hasPermi="['project:member:remove']"
          plain
          type="danger"
          :disabled="batchButtonVisible"
          @click="handleDelete(multipleSelection)"
        >
          <Icon class="mr-5px" icon="ep:delete" />
          删除
        </el-button>
      </el-form-item>
    </el-form>

    <el-table
      v-loading="loading"
      :data="list"
      highlight-current-row
      stripe
      row-key="id"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="姓名" prop="username" show-overflow-tooltip width="300" />
      <el-table-column label="岗位" prop="postName" show-overflow-tooltip />
      <el-table-column :width="150" align="center" label="操作">
        <template #default="scope">
          <el-button
            circle
            text
            type="primary"
            v-hasPermi="['project:member:update']"
            @click="openForm('update', scope.row)"
          >
            编辑
          </el-button>

          <el-button
            circle
            text
            type="danger"
            v-hasPermi="['project:member:remove']"
            @click="handleDelete(scope.row.id)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>

  <MemberForm ref="formRef" @success="handleQuery" :project-id="props.currentProject" />
</template>

<script lang="ts" setup>
import MemberForm from './MemberForm.vue'

import * as HTTP from '@/api/project/member'

const message = useMessage() // 消息弹窗

import { defineProps } from 'vue'

const props = defineProps({
  currentProject: {
    type: String,
    required: true
  }
})

const { t } = useI18n() // 国际化

defineOptions({ name: 'ProjectManager' })

let multipleSelection = ref<number[]>([])

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  memberName: '',
  projectId: ''
})
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)

const batchButtonVisible = ref(true)

const queryFormRef = ref() // 搜索的表单

const getList = async () => {
  loading.value = true
  try {
    queryParams.projectId = props.currentProject
    const data = await HTTP.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleSelectionChange = (val: any[]) => {
  batchButtonVisible.value = val.length === 0
  multipleSelection.value = Array.from(val, ({ id }) => id)
}

const handleQuery = async () => {
  queryParams.pageNo = 1
  getList()
}

const resetQuery = async () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, data?: any) => {
  formRef.value.open(type, data)
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

// 提供 getList 方法，用于获取数据
defineExpose({ getList })

/** 初始化 **/
onMounted(async () => {
  await getList()
})
</script>

<style scoped></style>
