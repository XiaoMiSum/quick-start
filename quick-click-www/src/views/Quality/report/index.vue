<template>
  <div class="report-container">
    <el-card class="report-header">
      <el-form :model="queryForm" ref="queryFormRef" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="项目" prop="projectId">
              <el-select v-model="queryForm.projectId" placeholder="请选择项目" clearable>
                <el-option
                  v-for="project in projectList"
                  :key="project.id"
                  :label="project.name"
                  :value="project.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="报告类型" prop="reportType">
              <el-select v-model="queryForm.reportType" placeholder="请选择报告类型" clearable>
                <el-option label="项目周报" value="week" />
                <el-option label="项目月报" value="month" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item>
              <el-button type="primary" @click="handleQuery">查询</el-button>
              <el-button @click="handleReset">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <el-card class="report-content">
      <template #header>
        <div class="card-header">
          <span>测试报告列表</span>
          <el-button type="primary" @click="handleGenerate">生成报告</el-button>
        </div>
      </template>

      <el-table :data="reportList" style="width: 100%" v-loading="loading">
        <el-table-column prop="title" label="报告名称" />
        <el-table-column prop="projectName" label="项目名称" />
        <el-table-column prop="reportType" label="报告类型">
          <template #default="{ row }">
            <el-tag v-if="row.reportType === 'week'">项目周报</el-tag>
            <el-tag v-else-if="row.reportType === 'month'">项目月报</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.startTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="endTime" label="结束时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.endTime) }}
          </template>
        </el-table-column>
        <el-table-column prop="generateTime" label="生成时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.generateTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleView(row)">查看</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-if="total > 0"
        :total="total"
        v-model:page="queryForm.pageNo"
        v-model:limit="queryForm.pageSize"
        @pagination="getReportList"
      />
    </el-card>

    <!-- 生成报告对话框 -->
    <el-dialog v-model="generateDialogVisible" title="生成测试报告" width="500px">
      <el-form :model="generateForm" ref="generateFormRef" label-width="100px">
        <el-form-item label="项目" prop="projectId" :rules="[{ required: true, message: '请选择项目', trigger: 'change' }]">
          <el-select v-model="generateForm.projectId" placeholder="请选择项目" style="width: 100%">
            <el-option
              v-for="project in projectList"
              :key="project.id"
              :label="project.name"
              :value="project.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="报告类型" prop="reportType" :rules="[{ required: true, message: '请选择报告类型', trigger: 'change' }]">
          <el-select v-model="generateForm.reportType" placeholder="请选择报告类型" style="width: 100%">
            <el-option label="项目周报" value="week" />
            <el-option label="项目月报" value="month" />
          </el-select>
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker
            v-model="generateForm.startDate"
            type="date"
            placeholder="请选择开始日期"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker
            v-model="generateForm.endDate"
            type="date"
            placeholder="请选择结束日期"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="generateDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitGenerate">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 报告详情对话框 -->
    <el-dialog v-model="detailDialogVisible" title="测试报告详情" width="80%">
      <div v-if="currentReport" class="report-detail">
        <el-descriptions title="报告基本信息" :column="2" border>
          <el-descriptions-item label="报告名称">{{ currentReport.title }}</el-descriptions-item>
          <el-descriptions-item label="项目名称">{{ currentReport.projectName }}</el-descriptions-item>
          <el-descriptions-item label="报告类型">
            <el-tag v-if="currentReport.reportType === 'week'">项目周报</el-tag>
            <el-tag v-else-if="currentReport.reportType === 'month'">项目月报</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="生成时间">{{ formatDate(currentReport.generateTime) }}</el-descriptions-item>
          <el-descriptions-item label="时间范围">
            {{ formatDate(currentReport.startTime) }} 至 {{ formatDate(currentReport.endTime) }}
          </el-descriptions-item>
        </el-descriptions>

        <el-tabs v-model="activeTab">
          <el-tab-pane label="测试用例统计" name="testcase">
            <el-card v-if="currentReport.testCaseStatistics">
              <el-descriptions title="测试用例统计数据" :column="3" border>
                <el-descriptions-item label="总用例数">{{ currentReport.testCaseStatistics.totalCases }}</el-descriptions-item>
                <el-descriptions-item label="新增用例数">{{ currentReport.testCaseStatistics.newCases }}</el-descriptions-item>
                <el-descriptions-item label="执行用例数">{{ currentReport.testCaseStatistics.executedCases }}</el-descriptions-item>
                <el-descriptions-item label="通过用例数">{{ currentReport.testCaseStatistics.passedCases }}</el-descriptions-item>
                <el-descriptions-item label="失败用例数">{{ currentReport.testCaseStatistics.failedCases }}</el-descriptions-item>
                <el-descriptions-item label="阻塞用例数">{{ currentReport.testCaseStatistics.blockedCases }}</el-descriptions-item>
              </el-descriptions>
            </el-card>
          </el-tab-pane>

          <el-tab-pane label="缺陷统计" name="bug">
            <el-card v-if="currentReport.bugStatistics">
              <el-descriptions title="缺陷统计数据" :column="3" border>
                <el-descriptions-item label="总缺陷数">{{ currentReport.bugStatistics.totalBugs }}</el-descriptions-item>
                <el-descriptions-item label="新增缺陷数">{{ currentReport.bugStatistics.newBugs }}</el-descriptions-item>
                <el-descriptions-item label="已修复缺陷数">{{ currentReport.bugStatistics.fixedBugs }}</el-descriptions-item>
                <el-descriptions-item label="已关闭缺陷数">{{ currentReport.bugStatistics.closedBugs }}</el-descriptions-item>
                <el-descriptions-item label="缺陷修复率">{{ currentReport.bugStatistics.fixRate }}%</el-descriptions-item>
                <el-descriptions-item label="缺陷关闭率">{{ currentReport.bugStatistics.closeRate }}%</el-descriptions-item>
              </el-descriptions>

              <div class="statistics-charts">
                <el-row :gutter="20">
                  <el-col :span="12">
                    <div class="chart-container">
                      <h4>按严重等级统计</h4>
                      <!-- 这里可以集成图表组件 -->
                      <div v-for="(value, key) in currentReport.bugStatistics.severityStatistics" :key="key">
                        {{ key }}: {{ value }}
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="12">
                    <div class="chart-container">
                      <h4>按优先级统计</h4>
                      <!-- 这里可以集成图表组件 -->
                      <div v-for="(value, key) in currentReport.bugStatistics.priorityStatistics" :key="key">
                        {{ key }}: {{ value }}
                      </div>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </el-card>
          </el-tab-pane>

          <el-tab-pane label="测试计划执行情况" name="plan">
            <el-card v-if="currentReport.planExecutions && currentReport.planExecutions.length > 0">
              <el-table :data="currentReport.planExecutions" style="width: 100%">
                <el-table-column prop="planName" label="计划名称" />
                <el-table-column prop="planStartTime" label="计划开始时间" width="180">
                  <template #default="{ row }">
                    {{ formatDate(row.planStartTime) }}
                  </template>
                </el-table-column>
                <el-table-column prop="planEndTime" label="计划结束时间" width="180">
                  <template #default="{ row }">
                    {{ formatDate(row.planEndTime) }}
                  </template>
                </el-table-column>
                <el-table-column prop="actualStartTime" label="实际开始时间" width="180">
                  <template #default="{ row }">
                    {{ formatDate(row.actualStartTime) }}
                  </template>
                </el-table-column>
                <el-table-column prop="actualEndTime" label="实际结束时间" width="180">
                  <template #default="{ row }">
                    {{ formatDate(row.actualEndTime) }}
                  </template>
                </el-table-column>
                <el-table-column prop="status" label="状态" />
                <el-table-column label="执行统计">
                  <template #default="{ row }">
                    总数: {{ row.executionStatistics?.total || 0 }},
                    通过: {{ row.executionStatistics?.passed || 0 }},
                    准备: {{ row.executionStatistics?.preparing || 0 }},
                    跳过: {{ row.executionStatistics?.skipped || 0 }}
                  </template>
                </el-table-column>
              </el-table>
            </el-card>
          </el-tab-pane>

          <el-tab-pane label="测试评审情况" name="review">
            <el-card v-if="currentReport.reviewExecutions && currentReport.reviewExecutions.length > 0">
              <el-table :data="currentReport.reviewExecutions" style="width: 100%">
                <el-table-column prop="reviewName" label="评审名称" />
                <el-table-column prop="reviewStartTime" label="评审开始时间" width="180">
                  <template #default="{ row }">
                    {{ formatDate(row.reviewStartTime) }}
                  </template>
                </el-table-column>
                <el-table-column prop="reviewEndTime" label="评审结束时间" width="180">
                  <template #default="{ row }">
                    {{ formatDate(row.reviewEndTime) }}
                  </template>
                </el-table-column>
                <el-table-column prop="actualStartTime" label="实际开始时间" width="180">
                  <template #default="{ row }">
                    {{ formatDate(row.actualStartTime) }}
                  </template>
                </el-table-column>
                <el-table-column prop="actualEndTime" label="实际结束时间" width="180">
                  <template #default="{ row }">
                    {{ formatDate(row.actualEndTime) }}
                  </template>
                </el-table-column>
                <el-table-column prop="status" label="状态" />
                <el-table-column label="评审统计">
                  <template #default="{ row }">
                    总数: {{ row.reviewStatistics?.total || 0 }},
                    通过: {{ row.reviewStatistics?.passed || 0 }},
                    准备: {{ row.reviewStatistics?.preparing || 0 }},
                    跳过: {{ row.reviewStatistics?.skipped || 0 }}
                  </template>
                </el-table-column>
              </el-table>
            </el-card>
          </el-tab-pane>
        </el-tabs>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="detailDialogVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getReportList, getReportDetail, generateReport, deleteReport } from '@/api/quality/report'
import { getList as getProjectList } from '@/api/project/project'
import { formatDate } from '@/utils/date'

// 查询表单
const queryForm = reactive({
  projectId: '',
  reportType: '',
  pageNo: 1,
  pageSize: 10
})

// 生成报告表单
const generateForm = reactive({
  projectId: '',
  reportType: 'week',
  startDate: '',
  endDate: ''
})

// 数据相关
const reportList = ref([])
const projectList = ref([])
const total = ref(0)
const loading = ref(false)
const activeTab = ref('testcase')

// 对话框相关
const generateDialogVisible = ref(false)
const detailDialogVisible = ref(false)
const currentReport = ref(null)

// 表单引用
const queryFormRef = ref()
const generateFormRef = ref()

// 获取报告列表
const loadReportList = async () => {
  loading.value = true
  try {
    const res = await getReportList(queryForm)
    reportList.value = res.data.list || []
    total.value = res.data.total || 0
  } catch (error) {
    console.error('获取报告列表失败:', error)
    ElMessage.error('获取报告列表失败')
  } finally {
    loading.value = false
  }
}

// 获取项目列表
const loadProjectList = async () => {
  try {
    const res = await getProjectList({ pageNo: 1, pageSize: 1000 })
    projectList.value = res.data.list || []
  } catch (error) {
    console.error('获取项目列表失败:', error)
    ElMessage.error('获取项目列表失败')
  }
}

// 查询
const handleQuery = () => {
  queryForm.pageNo = 1
  loadReportList()
}

// 重置
const handleReset = () => {
  queryForm.projectId = ''
  queryForm.reportType = ''
  queryForm.pageNo = 1
  loadReportList()
}

// 生成报告
const handleGenerate = () => {
  generateForm.projectId = queryForm.projectId
  generateDialogVisible.value = true
}

// 提交生成报告
const submitGenerate = async () => {
  if (!generateFormRef.value) return
  await generateFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await generateReport({
          projectId: generateForm.projectId,
          reportType: generateForm.reportType,
          startDate: generateForm.startDate,
          endDate: generateForm.endDate
        })
        ElMessage.success('报告生成成功')
        generateDialogVisible.value = false
        loadReportList()
      } catch (error) {
        console.error('生成报告失败:', error)
        ElMessage.error('生成报告失败')
      }
    }
  })
}

// 查看报告详情
const handleView = async (row) => {
  try {
    const res = await getReportDetail(row.id)
    currentReport.value = res.data
    detailDialogVisible.value = true
  } catch (error) {
    console.error('获取报告详情失败:', error)
    ElMessage.error('获取报告详情失败')
  }
}

// 删除报告
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该报告吗？', '提示', {
      type: 'warning'
    })
    await deleteReport(row.id)
    ElMessage.success('删除成功')
    loadReportList()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除报告失败:', error)
      ElMessage.error('删除报告失败')
    }
  }
}

// 初始化
onMounted(() => {
  loadReportList()
  loadProjectList()
})
</script>

<style scoped>
.report-container {
  padding: 20px;
}

.report-header {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.report-detail {
  max-height: 60vh;
  overflow-y: auto;
}

.statistics-charts {
  margin-top: 20px;
}

.chart-container {
  margin-bottom: 20px;
}

.chart-container h4 {
  margin-bottom: 10px;
}
</style>