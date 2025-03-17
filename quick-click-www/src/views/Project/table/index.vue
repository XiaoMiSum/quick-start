<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="" prop="name">
        <el-input
          v-model="queryParams.name"
          class="!w-240px"
          clearable
          placeholder="请输入项目名称"
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
          v-hasPermi="['system:project:add']"
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
      <el-table-column label="项目名称" prop="name" show-overflow-tooltip width="300" />
      <el-table-column label="产品经理" show-overflow-tooltip>
        <template #default="scope">
          <el-tag v-for="(item, index) in scope.row.productManagers" :key="index" class="mr-2">
            {{ item }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="开发人员" show-overflow-tooltip>
        <template #default="scope">
          <el-tag v-for="(item, index) in scope.row.developers" :key="index" class="mr-2">
            {{ item }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="测试人员" show-overflow-tooltip>
        <template #default="scope">
          <el-tag v-for="(item, index) in scope.row.testers" :key="index" class="mr-2">
            {{ item }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo" show-overflow-tooltip />
      <el-table-column :width="150" align="center" label="操作">
        <template #default="scope">
          <el-tooltip content="编辑" placement="top">
            <el-button circle plain type="primary" @click="openForm('update', scope.row.id)">
              <Icon icon="ep:edit" />
            </el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button circle plain type="danger" @click="handleDelete(scope.row.id)">
              <Icon icon="ep:delete" />
            </el-button>
          </el-tooltip>
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

  <ProjectForm ref="formRef" @success="handleQuery" />
</template>

<script lang="ts" setup>
import ProjectForm from './ProjectForm.vue'

import * as HTTP from '@/api/project'

import { useAppStore } from '@/store/modules/app'

const appStore = useAppStore()
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

defineOptions({ name: 'ProjectManager' })

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: '',
  status: undefined
})
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)

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
  appStore.setProjectPick(false)
  await getList()
})
</script>

<style scoped></style>
