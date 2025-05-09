<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="" prop="title">
        <el-input
          v-model="queryParams.title"
          class="!w-240px"
          clearable
          placeholder="请输入产线名称"
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
      </el-form-item>
    </el-form>
    <!-- 操作工具栏 -->
    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['project:list:add']"
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
    <el-table v-loading="loading" :data="list" highlight-current-row stripe>
      <el-table-column label="编号" prop="id" />
      <el-table-column label="产线名称" prop="title" />
      <el-table-column align="center" label="产线总监">
        <template #default="scope">
          <user-tag text :value="scope.row.manager" :users="userList" />
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo" show-overflow-tooltip />
      <el-table-column :width="150" align="center" label="操作">
        <template #default="scope">
          <el-button
            circle
            text
            type="primary"
            v-hasPermi="['project:list:update']"
            @click="openForm('update', scope.row.id)"
          >
            编辑
          </el-button>

          <el-button
            type="danger"
            circle
            text
            v-hasPermi="['project:list:remove']"
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

  <ProductLineForm ref="formRef" @success="handleQuery" />
</template>

<script lang="ts" setup>
import ProductLineForm from './ProductLineForm.vue'
import * as UserApi from '@/api/management/system/user'

import * as HTTP from '@/api/production-line'

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

defineOptions({ name: 'ProductionLine' })

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  title: ''
})
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)

const userList = ref<any>([]) // 用户列表

const queryFormRef = ref() // 搜索的表单

const getList = async () => {
  loading.value = true
  try {
    const data = await HTTP.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
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
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await HTTP.removeData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(async () => {
  await getList()

  userList.value = await UserApi.listSimple()
})
</script>

<style scoped></style>
