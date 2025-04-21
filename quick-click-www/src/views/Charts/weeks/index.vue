<template>
  <ContentWrap>
    <Card>
      <template #header>
        <span class="text-30px font-700">
          <el-text tag="b" size="large" style="font-size: 30px">质量赛马</el-text>
          <el-text tag="b" size="large">
            {{ '（' + weekQueryParams.start + '~' + weekQueryParams.end + '）' }}
          </el-text>
        </span>
        <el-date-picker
          v-model="weekQueryParams.start"
          :format="weekQueryParams.start + ' 至 ' + weekQueryParams.end"
          type="week"
          placeholder="选择周"
          :clearable="false"
          @change="changeTime"
        />
      </template>
      <div>
        <div class="mb-10px">
          <el-text tag="b" size="large" style="font-size: 20px">项目开发质量合格率：</el-text>
          <el-text tag="b" size="large" type="success">{{ firstText }}</el-text>
        </div>
        <div class="mb-10px">
          <el-text tag="b" size="large" style="font-size: 20px">BUG责任排名前三名：</el-text>
          <el-text tag="b" size="large" type="danger">{{ developerText }}</el-text>
        </div>
        <el-row>
          <el-col :span="8">
            <Echart :options="monthRank" :height="300" :width="600" />
          </el-col>
          <el-col :span="8">
            <Echart :options="weekRank" :height="300" :width="600" />
          </el-col>
          <el-col :span="8">
            <Echart :options="weekTrend" :height="300" :width="600" />
          </el-col>
        </el-row>
        <el-row class="mt-20px">
          <el-col :span="16">
            <Echart :options="developerMonthRank" :height="300" :width="1200" />
          </el-col>
          <el-col :span="8">
            <Echart :options="developerWeekRank" :height="300" :width="600" />
          </el-col>
        </el-row>
      </div>
    </Card>
    <Card>
      <template #header>
        <span class="text-30px font-700">
          <el-text tag="b" size="large" style="font-size: 30px">缺陷跟踪汇总</el-text>
          <el-text tag="b" size="large">
            {{ '（' + weekQueryParams.start + '~' + weekQueryParams.end + '）' }}
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
          :data="list2"
          :header-cell-style="{ background: '#f4f6f8' }"
          highlight-current-row
          stripe
          border
          style="width: 100%"
        >
          <el-table-column label="开发人员" align="center">
            <template #default="{ row }">
              <user-tag text :value="row.userId" />
            </template>
          </el-table-column>
          <el-table-column label="重复激活数" prop="reopenedBugTotal" align="center" />
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

const weekQueryParams = ref({
  start: undefined,
  end: undefined
})

const monthQueryParams = ref({
  start: undefined,
  end: undefined
})

// 选择周时间的任意一天
const changeTime = async (val) => {
  const date = moment(new Date(val))
  weekQueryParams.value = {
    start: date.clone().startOf('isoWeek').format('YYYY-MM-DD'),
    end: date.clone().endOf('isoWeek').format('YYYY-MM-DD')
  }
  monthQueryParams.value = {
    start: moment(weekQueryParams.value.start).startOf('month').format('YYYY-MM-DD'),
    end: moment(weekQueryParams.value.start).endOf('month').format('YYYY-MM-DD')
  }
}

const monthRank = ref({})
const weekRank = ref({})
const weekTrend = ref({})
const firstText = ref('')
const developerMonthRank = ref({})
const developerWeekRank = ref({})
const developerText = ref('')

const list = ref([])
const list2 = ref([])
const reopenedBugHandler = ref([])
const reopenedTotal = ref(0)
const closedRate = ref(0)

const handleProjectRankWeek = async () => {
  weekRank.value = await handleProjectRank(weekQueryParams.value, 'week')
}

const handleProjectRankMonth = async () => {
  monthRank.value = await handleProjectRank(monthQueryParams.value, 'month')
}

const handleProjectRank = async (params, type) => {
  const { title, first, items } = await Charts.getProjectRank(params, type)
  if (type === 'week') {
    const temp = first.map((item) => item.projectName)
    firstText.value =
      temp && temp.length > 0
        ? temp.join('、') + first[0].qualificationRate + '%（第一名）'
        : '无数据'
  }
  return bar(
    title,
    items.map((item) => item.projectName + '\n\n' + item.managerName),
    items.map((item) => item.qualificationRate)
  )
}

const handleProjectTrendWeek = async () => {
  const { title, items } = await Charts.getProjectTrend('week')
  weekTrend.value = await line(
    title,
    items.map((item) => item.start + ' - ' + item.end),
    items.map((item) => item.qualificationRate)
  )
}

const handleDeveloperRankWeek = async () => {
  developerWeekRank.value = await handleDeveloperRank(weekQueryParams.value, 'week')
}

const handleDeveloperRankMonth = async () => {
  developerMonthRank.value = await handleDeveloperRank(monthQueryParams.value, 'month')
}

const handleDeveloperRank = async (params, type) => {
  const { title, first, items } = await Charts.getDeveloperRank(params, type)
  if (type === 'week') {
    const temp = first.map((item) => item.name)
    developerText.value = temp && temp.length > 0 ? temp.join('、') : '无数据'
  }
  return bar(
    title,
    items.map((item) => item.name),
    items.map((item) => item.total),
    '{c}个'
  )
}

const handleProjectBug = async () => {
  list.value = await Charts.getProjectBug(weekQueryParams.value, 'week')
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
  list2.value = await Charts.getReopenedBugs(weekQueryParams.value)
  reopenedTotal.value = list2.value.reduce((sum, item) => sum + item.reopenedBugTotal, 0)
  reopenedBugHandler.value = list2.value.map((item) => item.userId)
}

// 监听周期选项 刷新数据
watch(
  computed(() => weekQueryParams.value.start),
  async () => {
    handleProjectRankWeek()
    handleProjectRankMonth()
    handleProjectTrendWeek()
    handleDeveloperRankWeek()
    handleDeveloperRankMonth()
    handleProjectBug()
    handleReopenedBugs()
  },
  { immediate: true, deep: true }
)

onMounted(() => {
  changeTime(moment(new Date().getTime()).subtract(1, 'weeks').toDate())
})
</script>
