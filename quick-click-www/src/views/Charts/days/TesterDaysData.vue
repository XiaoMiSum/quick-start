<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="" prop="date">
        <el-date-picker
          v-model="queryParams.date"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择日期"
        />
      </el-form-item>
      <el-form-item label="" prop="userId">
        <el-select
          filterable
          v-model="queryParams.userId"
          placeholder="请选择测试人员"
          style="width: 100%"
        >
          <el-option
            v-for="item in users"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
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

    <el-table v-loading="loading" :data="list" highlight-current-row stripe>
      <el-table-column label="日期" align="center" prop="date" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="新增用例数" align="center" prop="testcaseTotal" />
      <el-table-column label="执行用例数" align="center" prop="executeTestcaseTotal" />
      <el-table-column label="新增缺陷数" align="center" prop="newBugTotal" />
      <el-table-column label="验证缺陷数" align="center" prop="validatedBugTotal" />
      <el-table-column label="关闭缺陷数" align="center" prop="closedBugTotal" />
      <el-table-column label="激活缺陷数" prop="reopenedBugTotal" />
      <el-table-column :width="80" align="center" label="操作">
        <template #default="{ row }">
          <el-button
            circle
            text
            type="primary"
            v-hasPermi="['charts:days:update']"
            @click="openDaysFrom(row)"
          >
            编辑
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

    <TesterDaysFrom ref="daysFrom" @success="getList" />
  </ContentWrap>
</template>

<script setup>
import * as Days from '@/api/charts/days'
import { checkPermi } from '@/utils/permission'

import TesterDaysFrom from './TesterDaysFrom.vue'

const active = ref('0')

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

import { useGlobalStore } from '@/store/modules/global'
const globalStore = useGlobalStore()

defineProps({
  users: {
    required: true,
    type: Array
  }
})

defineOptions({ name: 'TesterDaysData' })

const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  userId: undefined,
  date: ''
})
const loading = ref(false)
const list = ref([])
const total = ref(0)

const getList = async () => {
  loading.value = true
  try {
    queryParams.value.projectId = globalStore.getCurrentProject
    const data = await Days.getTester(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}
// 提供 getList 方法，用于获取数据
defineExpose({ getList })

const handleQuery = async () => {
  queryParams.pageNo = 1
  getList()
}

const queryFormRef = ref() // 搜索的表单
const resetQuery = async () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

const daysFrom = ref() // 编辑的表单
const openDaysFrom = (data) => {
  daysFrom.value.open(data)
}

/** 初始化 **/
onMounted(async () => {
  await getList()
})
</script>
