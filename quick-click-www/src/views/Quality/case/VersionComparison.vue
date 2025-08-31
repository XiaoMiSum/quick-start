<template>
  <Dialog v-model="_visible" title="版本对比" width="90%">
    <ContentWrap>
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="version-header">
            <h3>版本 {{ version1.version }}: {{ version1.title }}</h3>
            <p>创建时间: {{ version1.createTime }}</p>
            <p>创建人: {{ version1.creator }}</p>
          </div>
          <el-card class="version-content">
            <div class="content-section">
              <h4>基本信息</h4>
              <el-descriptions :column="1" border>
                <el-descriptions-item label="标题">
                  <span :class="{ 'diff-field': isFieldDifferent('title') }">{{ version1.title }}</span>
                </el-descriptions-item>
                <el-descriptions-item label="优先级">
                  <span :class="{ 'diff-field': isFieldDifferent('priority') }">{{ version1.priority }}</span>
                </el-descriptions-item>
                <el-descriptions-item label="前置条件">
                  <span :class="{ 'diff-field': isFieldDifferent('prerequisite') }">{{ version1.prerequisite }}</span>
                </el-descriptions-item>
                <el-descriptions-item label="责任人">
                  <span :class="{ 'diff-field': isFieldDifferent('supervisor') }">{{ version1.supervisor }}</span>
                </el-descriptions-item>
                <el-descriptions-item label="前端开发">
                  <span :class="{ 'diff-field': isFieldDifferent('frontendDeveloper') }">{{ version1.frontendDeveloper }}</span>
                </el-descriptions-item>
                <el-descriptions-item label="后端开发">
                  <span :class="{ 'diff-field': isFieldDifferent('backendDeveloper') }">{{ version1.backendDeveloper }}</span>
                </el-descriptions-item>
              </el-descriptions>
            </div>
            
            <div class="content-section">
              <h4>执行步骤</h4>
              <el-table :data="version1.steps" border stripe>
                <el-table-column label="步骤" prop="step" />
                <el-table-column label="期望结果" prop="expected" />
                <el-table-column label="实际结果" prop="actual" />
              </el-table>
            </div>
          </el-card>
        </el-col>
        
        <el-col :span="12">
          <div class="version-header">
            <h3>版本 {{ version2.version }}: {{ version2.title }}</h3>
            <p>创建时间: {{ version2.createTime }}</p>
            <p>创建人: {{ version2.creator }}</p>
          </div>
          <el-card class="version-content">
            <div class="content-section">
              <h4>基本信息</h4>
              <el-descriptions :column="1" border>
                <el-descriptions-item label="标题">
                  <span :class="{ 'diff-field': isFieldDifferent('title') }">{{ version2.title }}</span>
                </el-descriptions-item>
                <el-descriptions-item label="优先级">
                  <span :class="{ 'diff-field': isFieldDifferent('priority') }">{{ version2.priority }}</span>
                </el-descriptions-item>
                <el-descriptions-item label="前置条件">
                  <span :class="{ 'diff-field': isFieldDifferent('prerequisite') }">{{ version2.prerequisite }}</span>
                </el-descriptions-item>
                <el-descriptions-item label="责任人">
                  <span :class="{ 'diff-field': isFieldDifferent('supervisor') }">{{ version2.supervisor }}</span>
                </el-descriptions-item>
                <el-descriptions-item label="前端开发">
                  <span :class="{ 'diff-field': isFieldDifferent('frontendDeveloper') }">{{ version2.frontendDeveloper }}</span>
                </el-descriptions-item>
                <el-descriptions-item label="后端开发">
                  <span :class="{ 'diff-field': isFieldDifferent('backendDeveloper') }">{{ version2.backendDeveloper }}</span>
                </el-descriptions-item>
              </el-descriptions>
            </div>
            
            <div class="content-section">
              <h4>执行步骤</h4>
              <el-table :data="version2.steps" border stripe>
                <el-table-column label="步骤" prop="step" />
                <el-table-column label="期望结果" prop="expected" />
                <el-table-column label="实际结果" prop="actual" />
              </el-table>
            </div>
          </el-card>
        </el-col>
      </el-row>
      
      <div v-if="diffResult" class="diff-result mt-4">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>差异分析</span>
            </div>
          </template>
          <div class="diff-content">
            <pre>{{ diffResult }}</pre>
          </div>
        </el-card>
      </div>
    </ContentWrap>
    
    <template #footer>
      <el-button @click="_visible = false">关闭</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import * as TestcaseApi from '@/api/quality/testcase'
import { useMessage } from '@/hooks/web/useMessage'

defineOptions({ name: 'VersionComparison' })

const message = useMessage()

const _visible = ref(false)
const version1 = ref<any>({})
const version2 = ref<any>({})
const diffResult = ref<string>('')

// 检查字段是否有差异
const isFieldDifferent = (field: string) => {
  if (!version1.value || !version2.value) return false
  
  switch (field) {
    case 'title':
      return version1.value.title !== version2.value.title
    case 'priority':
      return version1.value.priority !== version2.value.priority
    case 'prerequisite':
      return version1.value.prerequisite !== version2.value.prerequisite
    case 'supervisor':
      return version1.value.supervisor !== version2.value.supervisor
    case 'frontendDeveloper':
      return version1.value.frontendDeveloper !== version2.value.frontendDeveloper
    case 'backendDeveloper':
      return version1.value.backendDeveloper !== version2.value.backendDeveloper
    default:
      return false
  }
}

// 打开对比对话框
const open = async (ver1: any, ver2: any) => {
  _visible.value = true
  version1.value = ver1
  version2.value = ver2
  
  // 获取版本详细信息
  try {
    const [detail1, detail2] = await Promise.all([
      TestcaseApi.getData(ver1.id),
      TestcaseApi.getData(ver2.id)
    ])
    
    version1.value = { ...ver1, ...detail1 }
    version2.value = { ...ver2, ...detail2 }
    
    // 获取差异分析结果
    const diffResponse = await TestcaseApi.compareVersions(ver1.id, ver2.id)
    diffResult.value = diffResponse
  } catch (error) {
    message.error('获取版本详情失败')
    console.error(error)
  }
}

defineExpose({ open })
</script>

<style scoped>
.version-header {
  margin-bottom: 20px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.version-header h3 {
  margin: 0 0 10px 0;
  color: #303133;
}

.version-header p {
  margin: 5px 0;
  color: #606266;
}

.content-section {
  margin-bottom: 20px;
}

.content-section h4 {
  margin: 0 0 10px 0;
  color: #303133;
  border-bottom: 1px solid #ebeef5;
  padding-bottom: 5px;
}

.diff-result {
  margin-top: 20px;
}

.diff-content pre {
  white-space: pre-wrap;
  word-wrap: break-word;
  background-color: #f5f7fa;
  padding: 10px;
  border-radius: 4px;
  font-family: monospace;
}

.diff-field {
  background-color: #fff0f0;
  padding: 2px 4px;
  border-radius: 2px;
  font-weight: bold;
}
</style>