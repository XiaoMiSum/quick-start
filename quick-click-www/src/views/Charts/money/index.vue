<template>
  <ContentWrap>
    <Card>
      <template #header>
        <span class="text-30px font-700">
          <el-text tag="b" size="large" style="font-size: 30px">缺陷成本</el-text>
          <el-text tag="b" size="large">
            {{ '（' + month + '）' }}
          </el-text>
        </span>
        <el-date-picker
          v-model="queryParams.start"
          :format="queryParams.start + ' 至 ' + queryParams.end"
          type="month"
          placeholder="选择月"
          :clearable="false"
          @change="changeTime"
        />
      </template>
      <div>
        <el-table
          :data="list1"
          highlight-current-row
          stripe
          border
          :header-cell-style="{ background: '#f4f6f8' }"
          style="width: 100%"
        >
          <el-table-column label="直属上级" align="center" />
          <el-table-column label="人员" align="center">
            <template #default="{ row }">
              <user-tag text :value="row.userId" />
            </template>
          </el-table-column>
          <el-table-column align="center" prop="total">
            <template #header>
              <!-- 自定义表头内容 -->
              缺陷总次数
              <el-tooltip
                placement="top"
                raw-content
                content="开发人员：修复缺陷总次数，若缺陷修复后被激活，再次修复，则计算2次<br />测试人员：关闭缺陷次数 + 激活缺陷次数"
              >
                <icon icon="ep:question-filled" />
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column align="center">
            <template #header>
              <!-- 自定义表头内容 -->
              修复&验证工时
              <el-tooltip
                placement="top"
                raw-content
                content="开发人员：修复缺陷总工时<br />测试人员：关闭缺陷时长 + 激活缺陷时长"
              >
                <icon icon="ep:question-filled" />
              </el-tooltip>
            </template>
            <template #default="{ row }">
              {{ ((row.duration || 0) / 60).toFixed(2) }}
            </template>
          </el-table-column>
          <el-table-column label="总价" align="center" />
        </el-table>
      </div>
    </Card>

    <Card>
      <template #header>
        <span class="text-30px font-700">
          <el-text tag="b" size="large" style="font-size: 30px">开发缺陷率</el-text>
          <el-text tag="b" size="large">
            {{ '（' + month + '）' }}
          </el-text>
        </span>
      </template>

      <div>
        <el-table
          :data="list2"
          highlight-current-row
          stripe
          border
          @row-click="handleBugRateDetail"
          :header-cell-style="{ background: '#f4f6f8' }"
          style="width: 100%"
        >
          <el-table-column label="直属上级" align="center" />
          <el-table-column label="" align="center">
            <template #header>
              <!-- 自定义表头内容 -->
              人员
              <el-tooltip placement="top" raw-content content="点击行数据，查看明细">
                <icon icon="ep:question-filled" />
              </el-tooltip>
            </template>
            <template #default="{ row }">
              <user-tag text :value="row.userId" />
            </template>
          </el-table-column>
          <el-table-column label="缺陷总数" align="center" prop="bugTotal" />
          <el-table-column label="用例总数" align="center" prop="testcaseTotal" />
          <el-table-column align="center">
            <template #header>
              <!-- 自定义表头内容 -->
              缺陷率
              <el-tooltip placement="top" raw-content content="缺陷率：缺陷总数/用例总数">
                <icon icon="ep:question-filled" />
              </el-tooltip>
            </template>
            <template #default="{ row }">
              {{ (((row.bugTotal || 0) / (row.testcaseTotal | 1)) * 100).toFixed(2) + '%' }}
            </template>
          </el-table-column>
        </el-table>
      </div>
    </Card>
    <Dialog title="开发缺陷明细" v-model="visible">
      <el-table
        :data="list3"
        highlight-current-row
        stripe
        border
        :header-cell-style="{ background: '#f4f6f8' }"
        style="width: 100%"
      >
        <el-table-column label="项目名称" prop="projectName" align="center" />
        <el-table-column label="缺陷总数" align="center" prop="bugTotal" />
        <el-table-column label="用例总数" align="center" prop="testcaseTotal" />
        <el-table-column align="center">
          <template #header>
            <!-- 自定义表头内容 -->
            缺陷率
            <el-tooltip placement="top" raw-content content="缺陷率：缺陷总数/用例总数">
              <icon icon="ep:question-filled" />
            </el-tooltip>
          </template>
          <template #default="{ row }">
            {{ (((row.bugTotal || 0) / (row.testcaseTotal | 1)) * 100).toFixed(2) + '%' }}
          </template>
        </el-table-column>
      </el-table>
    </Dialog>
  </ContentWrap>
</template>

<script setup>
import * as Charts from '@/api/charts'

import moment from 'moment'
import { dayjs } from 'element-plus'
dayjs.en.weekStart = 1

const queryParams = ref({
  start: undefined,
  end: undefined
})

const month = ref('')
const list1 = ref([])
const list2 = ref([])
const list3 = ref([])
const visible = ref(false)

// 选择周时间的任意一天
const changeTime = async (val) => {
  const date = moment(new Date(val))
  queryParams.value = {
    start: date.startOf('month').format('YYYY-MM-DD'),
    end: date.endOf('month').format('YYYY-MM-DD')
  }
  month.value = date.format('YYYY年MM月')
}

const handleBugsMoney = async () => {
  list1.value = await Charts.getBugsMoney(queryParams.value)
}

const handleBugRate = async () => {
  list2.value = await Charts.getBugsRate(queryParams.value)
}

const handleBugRateDetail = async (row, event, column) => {
  list3.value = []
  visible.value = true
  const params = queryParams.value
  params.userId = row.userId
  list3.value = await Charts.getBugsRateDetail(params)
}

// 监听周期选项 刷新数据
watch(
  computed(() => queryParams.value.start),
  async () => {
    handleBugsMoney()
    handleBugRate()
  },
  { immediate: true, deep: true }
)

onMounted(() => {
  changeTime(moment(new Date().getTime()).subtract(1, 'months').toDate())
})
</script>
