<template>
  <ContentWrap>
    <div class="flex items-center justify-between">
      <div class="text-xl font-bold">缺陷统计分析</div>
      <div class="flex items-center gap-2">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="YYYY-MM-DD"
          @change="handleDateChange"
        />
        <el-button type="primary" @click="loadStatistics">查询</el-button>
      </div>
    </div>
  </ContentWrap>

  <!-- 统计概览 -->
  <ContentWrap title="统计概览">
    <el-row :gutter="20">
      <el-col :span="6">
        <SummaryCard
          title="总缺陷数"
          :value="statistics.totalBugs"
          icon="ep:warning"
          icon-color="text-red-500"
          icon-bg-color="bg-red-100"
        />
      </el-col>
      <el-col :span="6">
        <SummaryCard
          title="已修复"
          :value="statistics.fixedBugs"
          icon="ep:check"
          icon-color="text-green-500"
          icon-bg-color="bg-green-100"
        />
      </el-col>
      <el-col :span="6">
        <SummaryCard
          title="已关闭"
          :value="statistics.closedBugs"
          icon="ep:circle-close"
          icon-color="text-gray-500"
          icon-bg-color="bg-gray-100"
        />
      </el-col>
      <el-col :span="6">
        <SummaryCard
          title="修复率"
          :value="statistics.fixRate"
          :decimals="2"
          suffix="%"
          icon="ep:data-analysis"
          icon-color="text-blue-500"
          icon-bg-color="bg-blue-100"
        />
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 图表分析 -->
  <ContentWrap title="图表分析">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="趋势分析" name="trend">
        <Echart :options="trendChartOptions" :height="400" />
      </el-tab-pane>
      <el-tab-pane label="状态分布" name="status">
        <Echart :options="statusChartOptions" :height="400" />
      </el-tab-pane>
      <el-tab-pane label="严重等级分布" name="severity">
        <Echart :options="severityChartOptions" :height="400" />
      </el-tab-pane>
      <el-tab-pane label="优先级分布" name="priority">
        <Echart :options="priorityChartOptions" :height="400" />
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>

  <!-- 详细统计 -->
  <ContentWrap title="详细统计">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="never">
          <template #header>
            <div class="flex items-center">
              <Icon icon="ep:user" class="mr-2" />
              <span>责任人分布</span>
            </div>
          </template>
          <Echart :options="supervisorChartOptions" :height="300" />
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="never">
          <template #header>
            <div class="flex items-center">
              <Icon icon="ep:management" class="mr-2" />
              <span>处理人分布</span>
            </div>
          </template>
          <Echart :options="handlerChartOptions" :height="300" />
        </el-card>
      </el-col>
    </el-row>
  </ContentWrap>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import * as BugApi from '@/api/quality/bug'
import { useMessage } from '@/hooks/web/useMessage'
import { Echart } from '@/components/Echart'
import SummaryCard from '@/components/SummaryCard/index.vue'
import { formatDate } from '@/utils/formatTime'
import { useAppStore } from '@/store/modules/app'

defineOptions({ name: 'BugStatistics' })

const route = useRoute()
const message = useMessage()
const appStore = useAppStore()

const dateRange = ref<[string, string]>([
  formatDate(new Date(Date.now() - 30 * 24 * 60 * 60 * 1000), 'YYYY-MM-DD'),
  formatDate(new Date(), 'YYYY-MM-DD')
])

const activeTab = ref('trend')
const statistics = ref({
  totalBugs: 0,
  fixedBugs: 0,
  closedBugs: 0,
  fixRate: 0.0
})

const trendChartOptions = ref({})
const statusChartOptions = ref({})
const severityChartOptions = ref({})
const priorityChartOptions = ref({})
const supervisorChartOptions = ref({})
const handlerChartOptions = ref({})

// 处理日期变化
const handleDateChange = (val: [string, string] | null) => {
  if (val && val[0] && val[1]) {
    dateRange.value = val
    loadStatistics()
  }
}

// 加载统计分析数据
const loadStatistics = async () => {
  try {
    const projectId = appStore.getCurrentProject
    if (!projectId) {
      message.error('请先选择项目')
      return
    }

    const response = await BugApi.getStatistics({
      projectId,
      startDate: dateRange.value[0],
      endDate: dateRange.value[1]
    })

    if (response.code === 0) {
      processStatisticsData(response.data)
    } else {
      message.error(response.msg || '获取统计分析数据失败')
    }
  } catch (error) {
    message.error('获取统计分析数据失败')
    console.error(error)
  }
}

// 处理统计分析数据
const processStatisticsData = (data: any) => {
  // 更新概览数据
  const statusStats = data.statusStatistics || {}
  statistics.value.totalBugs = Object.values(statusStats).reduce((sum: number, count: any) => sum + count, 0)
  statistics.value.fixedBugs = statusStats['Fixed'] || 0
  statistics.value.closedBugs = statusStats['Closed'] || 0
  statistics.value.fixRate = data.fixRate || 0.0

  // 生成趋势图数据
  generateTrendChartData(data.dailyStatistics || [])
  
  // 生成状态分布图数据
  generateStatusChartData(data.statusStatistics || {})
  
  // 生成严重等级分布图数据
  generateSeverityChartData(data.severityStatistics || {})
  
  // 生成优先级分布图数据
  generatePriorityChartData(data.priorityStatistics || {})
  
  // 生成责任人分布图数据
  generateSupervisorChartData(data.supervisorStatistics || {})
  
  // 生成处理人分布图数据
  generateHandlerChartData(data.handlerStatistics || {})
}

// 生成趋势图数据
const generateTrendChartData = (dailyStats: any[]) => {
  const dates = dailyStats.map(item => item.date)
  const createdData = dailyStats.map(item => item.createdCount || 0)
  const fixedData = dailyStats.map(item => item.fixedCount || 0)
  const closedData = dailyStats.map(item => item.closedCount || 0)

  trendChartOptions.value = {
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['新增缺陷', '修复缺陷', '关闭缺陷']
    },
    xAxis: {
      type: 'category',
      data: dates
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '新增缺陷',
        type: 'line',
        data: createdData,
        smooth: true
      },
      {
        name: '修复缺陷',
        type: 'line',
        data: fixedData,
        smooth: true
      },
      {
        name: '关闭缺陷',
        type: 'line',
        data: closedData,
        smooth: true
      }
    ]
  }
}

// 生成状态分布图数据
const generateStatusChartData = (statusStats: Record<string, number>) => {
  const statuses = Object.keys(statusStats)
  const counts = Object.values(statusStats)

  statusChartOptions.value = {
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%'
    },
    series: [
      {
        name: '缺陷状态分布',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '18',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: statuses.map((status, index) => ({
          name: status,
          value: counts[index]
        }))
      }
    ]
  }
}

// 生成严重等级分布图数据
const generateSeverityChartData = (severityStats: Record<string, number>) => {
  const severities = Object.keys(severityStats)
  const counts = Object.values(severityStats)

  severityChartOptions.value = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: severities
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '严重等级',
        type: 'bar',
        barWidth: '60%',
        data: counts,
        itemStyle: {
          color: (params: any) => {
            const colors = ['#FF0000', '#FF6600', '#FFCC00', '#00CC00', '#0066CC']
            return colors[params.dataIndex % colors.length]
          }
        }
      }
    ]
  }
}

// 生成优先级分布图数据
const generatePriorityChartData = (priorityStats: Record<string, number>) => {
  const priorities = Object.keys(priorityStats)
  const counts = Object.values(priorityStats)

  priorityChartOptions.value = {
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%'
    },
    series: [
      {
        name: '优先级分布',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '18',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: priorities.map((priority, index) => ({
          name: priority,
          value: counts[index]
        }))
      }
    ]
  }
}

// 生成责任人分布图数据
const generateSupervisorChartData = (supervisorStats: Record<string, number>) => {
  const supervisors = Object.keys(supervisorStats)
  const counts = Object.values(supervisorStats)

  supervisorChartOptions.value = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: supervisors.map(id => `用户${id}`) // 这里应该替换为实际的用户名
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '责任人',
        type: 'bar',
        barWidth: '60%',
        data: counts
      }
    ]
  }
}

// 生成处理人分布图数据
const generateHandlerChartData = (handlerStats: Record<string, number>) => {
  const handlers = Object.keys(handlerStats)
  const counts = Object.values(handlerStats)

  handlerChartOptions.value = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: handlers.map(id => `用户${id}`) // 这里应该替换为实际的用户名
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '处理人',
        type: 'bar',
        barWidth: '60%',
        data: counts
      }
    ]
  }
}

onMounted(() => {
  loadStatistics()
})
</script>

<style lang="scss" scoped>
:deep(.el-card__header) {
  padding: 10px 20px;
  font-weight: bold;
}
</style>