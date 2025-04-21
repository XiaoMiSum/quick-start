<template>
  <ContentWrap>
    <Card>
      <template #header>
        <span class="text-30px font-700">
          <el-text tag="b" size="large" style="font-size: 30px">质量赛马</el-text>
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
        <div class="mb-10px">
          <el-text tag="b" size="large" style="font-size: 20px">项目开发质量合格率：</el-text>
          <el-text tag="b" size="large" type="danger">{{
            '本期 ' +
            thisMonthRate.toFixed(2) +
            '%，环比' +
            (thisMonthRate - lastMonthRate >= 0 ? '上升' : '下降') +
            Math.abs((thisMonthRate - lastMonthRate).toFixed(2)) +
            '%'
          }}</el-text>
          <el-text tag="b" size="large" style="font-size: 20px">，本期合格率最低项目：</el-text>
          <el-text tag="b" size="large" type="danger">{{ lowest }}</el-text>
        </div>
        <div class="mb-10px">
          <el-text tag="b" size="large" style="font-size: 20px">缺陷数：</el-text>
          <el-text tag="b" size="large" type="danger">{{
            '本期 ' +
            thisMonthBugTotal +
            '个，环比' +
            (thisMonthBugTotal - lastMonthBugTotal >= 0 ? '上升' : '下降') +
            Math.abs(thisMonthBugTotal - lastMonthBugTotal) +
            '个'
          }}</el-text>
          <el-text tag="b" size="large" style="font-size: 20px">，责任人前三名：</el-text>
          <el-text tag="b" size="large" type="danger">{{ developerText }}</el-text>
        </div>
        <el-row>
          <el-col :span="16">
            <Echart :options="monthRank" :height="300" :width="1200" />
          </el-col>
          <el-col :span="8">
            <Echart :options="monthTrend" :height="300" :width="600" />
          </el-col>
        </el-row>
        <el-row class="mt-20px">
          <el-col :span="16">
            <Echart :options="developerMonthRank" :height="300" :width="1200" />
          </el-col>
          <el-col :span="8">
            <Echart :options="monthBugTrend" :height="300" :width="600" />
          </el-col>
        </el-row>
      </div>
    </Card>
    <Card>
      <template #header>
        <span class="text-30px font-700">
          <el-text tag="b" size="large" style="font-size: 30px">缺陷跟踪汇总</el-text>
          <el-text tag="b" size="large">
            {{ '（' + month + '）' }}
          </el-text>
        </span>
        <span>
          <el-text tag="b" size="large" style="font-size: 25px">处理时限：</el-text>
          <el-text tag="b" type="danger"> 高：1天 、</el-text>
          <el-text tag="b" type="warning"> 中：3天、 </el-text>
          <el-text tag="b" type="success"> 低：5天 </el-text>
        </span>
      </template>
      <div>
        <div class="mb-20px">
          <el-text tag="b" size="large" style="font-size: 20px">BUG改善关闭率：</el-text>
          <el-text tag="b" size="large" type="success">{{ closedRate.toFixed(2) + '%' }}</el-text>
        </div>
        <div class="mb-20px">
          <el-table
            :data="list"
            highlight-current-row
            stripe
            show-summary
            border
            :header-cell-style="{ background: '#f4f6f8' }"
            style="width: 100%"
          >
            <el-table-column label="项目名称" align="center" prop="projectName" />
            <el-table-column label="Bug总数" align="center" prop="bugTotal" />
            <el-table-column label="Bug关闭数" align="center" prop="thisRangeClosedBugTotal" />
            <el-table-column label="Bug关闭率" align="center">
              <template #default="{ row }">
                {{ ((row.thisRangeClosedBugTotal || 0) / (row.bugTotal || 1)).toFixed(2) + ' %' }}
              </template>
            </el-table-column>
            <el-table-column label="Bug未关闭数" align="center" prop="thisRangeUnclosedBugTotal" />
            <el-table-column label="解决人" align="center">
              <template #default="{ row }">
                <user-tag v-for="item in row.unfinishedBugHandler" :key="item" :value="item" />
              </template>
            </el-table-column>
            <el-table-column label="项目经理" prop="managerName" align="center" />
            <el-table-column label="计划完成时间" align="center">
              <template #default="{ row }">
                {{ row.completionDate || '已完成' }}
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div class="mb-20px">
          <el-text tag="b" size="large" style="font-size: 20px">BUG重复激活数：</el-text>
          <el-text tag="b" size="large" type="danger">{{ reopenedTotal }}</el-text>
          <el-text tag="b" size="large" style="font-size: 20px">{{ '，责任人：' }}</el-text>
          <user-tag v-for="item in reopenedBugHandler" :key="item" :value="item" />
        </div>
        <el-table
          :data="list"
          :header-cell-style="{ background: '#f4f6f8' }"
          highlight-current-row
          stripe
          show-summary
          border
          style="width: 100%"
        >
          <el-table-column label="开发人员" align="center">
            <template #default="{ row }">
              <user-tag v-for="item in row.reopenedBugHandler" :key="item" :value="item" />
            </template>
          </el-table-column>
          <el-table-column label="重复激活数" prop="thisRangeReopenedBugTotal" align="center" />
        </el-table>
      </div>
    </Card>
  </ContentWrap>
</template>

<script setup>
import * as Charts from '@/api/charts'

import moment from 'moment'

import { bar, line } from '@/utils/echars'
import { formatDate } from '@/utils/formatTime'
import { dayjs } from 'element-plus'
dayjs.en.weekStart = 1

const queryParams = ref({
  start: undefined,
  end: undefined
})

const month = ref('')

const lastMonth = ref()

// 选择周时间的任意一天
const changeTime = async (val) => {
  const date = moment(new Date(val))
  queryParams.value = {
    start: date.startOf('month').format('YYYY-MM-DD'),
    end: date.endOf('month').format('YYYY-MM-DD')
  }
  month.value = date.format('YYYY年MM月')
  lastMonth.value = date.subtract(1, 'months')
}

// 合格率排名
const monthRank = ref({})
// 合格率推移
const monthTrend = ref({})
// 本期合格率
const thisMonthRate = ref(0)
// 上期合格率
const lastMonthRate = ref(0)
// 合格率最低描述
const lowest = ref('')
// 开发人员缺陷数排名
const developerMonthRank = ref({})
// 缺陷总数推移
const monthBugTrend = ref({})
// 本期缺陷总数
const thisMonthBugTotal = ref(0)
// 上期缺陷总数
const lastMonthBugTotal = ref(0)
const developerText = ref('')

const list = ref([])
const list2 = ref([])
const reopenedBugHandler = ref([])
const reopenedTotal = ref(0)
const closedRate = ref(0)

const handleProjectRankMonth = async () => {
  const { title, items } = await Charts.getProjectRank(queryParams.value, 'month')

  thisMonthRate.value =
    items.reduce((sum, item) => sum + item.qualificationRate, 0) / (items.length | 1)

  lowest.value =
    items.length > 1
      ? items[items.length - 1].projectName + items[items.length - 1].qualificationRate + '%'
      : '无数据'

  monthRank.value = bar(
    title,
    items.map((item) => item.projectName + '\n\n' + item.managerName),
    items.map((item) => item.qualificationRate)
  )
}
const handleProjectRankMonth2 = async () => {
  const date = lastMonth.value
  const { items } = await Charts.getProjectRank(
    {
      start: date.startOf('month').format('YYYY-MM-DD'),
      end: date.endOf('month').format('YYYY-MM-DD')
    },
    'month'
  )
  lastMonthRate.value =
    items.reduce((sum, item) => sum + item.qualificationRate, 0) / (items.length | 1)
}

const handleProjectTrendMonth = async () => {
  const { title, items } = await Charts.getProjectTrend('month')
  monthTrend.value = await line(
    title,
    items.map((item) => formatDate(moment(item.start, 'YYYY-MM-DD').toDate(), 'YYYY-MM')),
    items.map((item) => item.qualificationRate)
  )
}

const handleDeveloperRankMonth = async () => {
  developerMonthRank.value = await handleDeveloperRank(queryParams.value, 'month')
}

const handleDeveloperRank = async (params, type) => {
  const { title, items } = await Charts.getDeveloperRank(params, type)
  const temp = items.map((item) => item.name + item.total + '个')
  developerText.value = temp && temp.length > 0 ? temp.join('、') : '无数据'
  return bar(
    title,
    items.map((item) => item.name),
    items.map((item) => item.total),
    '{c}个'
  )
}

const handleBugTrendMonth = async () => {
  const { title, items } = await Charts.getBugTrend(queryParams.value, 'month')
  thisMonthBugTotal.value = items.length < 1 ? 0 : items[0].total
  lastMonthBugTotal.value = items.length < 2 ? 0 : items[items.length - 1].total
  monthBugTrend.value = bar(
    title,
    items.map((item) => item.month),
    items.map((item) => item.total),
    '{c}个',
    false
  )
}

const handleProjectBug = async () => {
  list.value = await Charts.getProjectBug(queryParams.value, 'month')
  closedRate.value = 0
  let bugTotal = 0
  let unclosedTotal = 0
  list.value.forEach((item) => {
    bugTotal = bugTotal + item.bugTotal
    unclosedTotal = unclosedTotal + item.thisRangeClosedBugTotal
  })
  closedRate.value = unclosedTotal / (bugTotal | 1)
}

const handleReopenedBugs = async () => {
  list2.value = await Charts.getReopenedBugs(queryParams.value)
  reopenedTotal.value = list2.value.reduce((sum, item) => sum + item.reopenedBugTotal, 0)
  reopenedBugHandler.value = list2.value.map((item) => item.userId)
}

// 监听周期选项 刷新数据
watch(
  computed(() => queryParams.value.start),
  async () => {
    handleProjectRankMonth()
    handleProjectTrendMonth()
    handleDeveloperRankMonth()
    handleBugTrendMonth()
    handleProjectBug()
    handleReopenedBugs()
  },
  { immediate: true, deep: true }
)

onMounted(() => {
  changeTime(moment(new Date().getTime()).subtract(1, 'months').toDate())
})
</script>
