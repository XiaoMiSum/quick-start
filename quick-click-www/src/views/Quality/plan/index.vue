<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="" prop="title">
        <el-input
          v-model="queryParams.title"
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
        <el-button
          v-hasPermi="['quality:plan:add']"
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
      <el-table-column align="left" label="计划名称" prop="title" show-overflow-tooltip width="280">
        <template #default="scope">
          <el-button link type="primary" @click="handleGoAssociCase(scope.row.id)">
            {{ scope.row.title }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" label="执行人" prop="executor" show-overflow-tooltip>
        <template #default="scope">
          <user-tag text :value="scope.row.executor" />
        </template>
      </el-table-column>
      <el-table-column align="center" label="测试阶段" prop="stage" show-overflow-tooltip>
        <template #default="scope">
          <ones-tag :value="scope.row.stage" :type="DICT_TYPE.QUALITY_TEST_STAGE" />
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态" prop="reviewer" show-overflow-tooltip>
        <template #default="scope">
          <ones-tag :value="scope.row.status" :type="DICT_TYPE.QUALITY_TEST_STATUS" />
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="预计开始时间"
        prop="expectedStartTime"
        show-overflow-tooltip
        width="170"
      />
      <el-table-column
        align="center"
        label="预计结束时间"
        prop="expectedEndTime"
        show-overflow-tooltip
        width="170"
      />
      <el-table-column
        align="center"
        label="实际开始时间"
        prop="actualStartTime"
        show-overflow-tooltip
        width="170"
      />
      <el-table-column
        align="center"
        label="实际结束时间"
        prop="actualEndTime"
        show-overflow-tooltip
        width="170"
      />
      <el-table-column align="right" label="用例总数" prop="total" width="80">
        <template #default="scope">
          <el-button link type="primary" @click="handleGoAssociCase(scope.row.id)">
            {{ scope.row.statistics.total }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column align="right" label="执行进度" width="115">
        <template #default="scope">
          <el-tooltip content="通过数" placement="top">
            <el-button link type="success" @click="handleGoAssociCase(scope.row.id)">
              {{ scope.row.statistics.passed }}
            </el-button>
          </el-tooltip>
          <el-tooltip content="不通过数" placement="top">
            <el-button link type="danger" @click="handleGoAssociCase(scope.row.id)">
              {{
                scope.row.statistics.total -
                scope.row.statistics.passed -
                scope.row.statistics.preparing -
                scope.row.statistics.skipped
              }}
            </el-button>
          </el-tooltip>
          <el-tooltip content="跳过数" placement="top">
            <el-button link type="info" @click="handleGoAssociCase(scope.row.id)">
              {{ scope.row.statistics.skipped }}
            </el-button>
          </el-tooltip>
          <el-tooltip content="未执行数" placement="top">
            <el-button link type="warning" @click="handleGoAssociCase(scope.row.id)">
              {{ scope.row.statistics.preparing }}
            </el-button>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column align="right" label="通过率" width="90">
        <template #default="scope">
          <el-button link type="primary">
            {{ (scope.row.statistics.passed / (scope.row.statistics.total | 1)) * 100 }} %
          </el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right" label="操作" width="150">
        <template #default="scope">
          <el-button
            v-hasPermi="['quality:plan:update']"
            text
            circle
            type="primary"
            @click="openForm('update', scope.row.id)"
          >
            编辑
          </el-button>

          <el-button
            v-hasPermi="['quality:plan:execute']"
            circle
            text
            type="primary"
            @click="handleGoAssociCase(scope.row.id)"
          >
            执行
          </el-button>

          <el-button
            v-hasPermi="['quality:plan:remove']"
            circle
            text
            type="danger"
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

  <PlanForm ref="formRef" @success="handleQuery" />
</template>

<script lang="ts" setup>
import PlanForm from './PlanForm.vue'

import * as HTTP from '@/api/quality/plan'

import { useGlobalStore } from '@/store/modules/global'
import { DICT_TYPE } from '@/utils/dictionary'

const globalStpre = useGlobalStore()

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const { push } = useRouter() // 路由

defineOptions({ name: 'TestPlan' })

const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  title: '',
  status: undefined,
  projectId: ''
})
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)

const queryFormRef = ref() // 搜索的表单

const getList = async () => {
  loading.value = true
  try {
    queryParams.value.projectId = globalStpre.getCurrentProject
    const data = await HTTP.getPage(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = async () => {
  queryParams.value.pageNo = 1
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
  push('/quality/test-plan/' + id + '/associated-use-cases')
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

const resetQuery2 = async () => {
  queryParams.value = { pageNo: 1, pageSize: 10, title: '', status: undefined, projectId: '' }
  getList()
}

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => globalStpre.getCurrentProject),
  () => {
    resetQuery2()
  },
  { immediate: true, deep: true }
)

/** 初始化 **/
onMounted(async () => {
  await getList()
})
</script>

<style scoped></style>
