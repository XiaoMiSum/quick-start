<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="" prop="name">
        <el-input
          v-model="queryParams.name"
          class="!w-240px"
          clearable
          placeholder="请输入计划名称"
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
        <el-button plain type="primary" @click="openForm('create')">
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" highlight-current-row stripe>
      <el-table-column
        align="center"
        label="计划名称"
        prop="name"
        show-overflow-tooltip
        width="200"
      >
        <template #default="scope">
          <el-button link type="primary" @click="handleGoAssociCase(scope.row.id)">
            {{ scope.row.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" label="执行人" prop="executorUser" show-overflow-tooltip />
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="预计开始时间"
        prop="expectedStartTime"
        show-overflow-tooltip
        width="170"
      />
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="预计结束时间"
        prop="expectedEndTime"
        show-overflow-tooltip
        width="170"
      />
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="实际开始时间"
        prop="actualStartTime"
        show-overflow-tooltip
        width="170"
      />
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="实际结束时间"
        prop="actualEndTime"
        show-overflow-tooltip
        width="170"
      />
      <el-table-column align="right" label="用例总数" prop="total" width="100">
        <template #default="scope">
          <el-button link type="primary">
            {{ scope.row.statistics.total }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column align="right" label="执行进度" width="120">
        <template #default="scope">
          <el-tooltip content="通过数" placement="top">
            <el-button link type="success">
              {{ scope.row.statistics.passed }}
            </el-button>
          </el-tooltip>
          <el-tooltip content="不通过数" placement="top">
            <el-button link type="danger">
              {{
                scope.row.statistics.total -
                scope.row.statistics.passed -
                scope.row.statistics.notstarted -
                scope.row.statistics.skipped
              }}
            </el-button>
          </el-tooltip>
          <el-tooltip content="跳过数" placement="top">
            <el-button link type="info">
              {{ scope.row.statistics.skipped }}
            </el-button>
          </el-tooltip>
          <el-tooltip content="未执行数" placement="top">
            <el-button link type="warning">
              {{ scope.row.statistics.notstarted }}
            </el-button>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column align="right" label="通过率" width="70">
        <template #default="scope">
          <el-button link type="primary">
            {{ (scope.row.statistics.passed / scope.row.statistics.total) * 100 }} %
          </el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-tooltip content="编辑" placement="top">
            <el-button circle plain type="primary" @click="openForm('update', scope.row.id)">
              <Icon icon="ep:edit" />
            </el-button>
          </el-tooltip>
          <el-tooltip content="规划&执行" placement="top">
            <el-button circle plain type="primary" @click="handleGoAssociCase(scope.row.id)">
              <Icon icon="ep:link" />
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

  <PlanForm ref="formRef" @success="handleQuery" />
</template>

<script lang="ts" setup>
import { PlanForm } from '../components'

import { dateFormatter } from '@/utils/formatTime'

import * as HTTP from '@/api/st/plan'

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const { push } = useRouter() // 路由

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

const handleGoAssociCase = async (id: Number) => {
  push('/st/plan/' + id + '/associated-use-cases')
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
})
</script>

<style scoped></style>
