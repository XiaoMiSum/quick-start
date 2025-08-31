<template>
  <Dialog v-model="_visible" title="用例版本管理" width="80%">
    <ContentWrap>
      <el-table :data="versionList" highlight-current-row stripe>
        <el-table-column label="版本号" prop="version" width="80" />
        <el-table-column label="标题" prop="title" show-overflow-tooltip />
        <el-table-column label="创建时间" prop="createTime" width="180" />
        <el-table-column label="更新时间" prop="updateTime" width="180" />
        <el-table-column label="创建人" prop="creator" width="120" />
        <el-table-column align="center" label="是否基线" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.isBaseline === 1" type="success">是</el-tag>
            <el-tag v-else type="info">否</el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="200">
          <template #default="scope">
            <el-button link type="primary" @click="handleViewVersion(scope.row)">
              查看
            </el-button>
            <el-button link type="primary" @click="handleCompareVersion(scope.row)">
              对比
            </el-button>
            <el-button v-if="scope.row.isBaseline !== 1" link type="primary" @click="handleSetBaseline(scope.row)">
              设为基线
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </ContentWrap>
    
    <template #footer>
      <el-button @click="_visible = false">关闭</el-button>
    </template>
  </Dialog>
  
  <!-- 版本对比对话框 -->
  <VersionComparison ref="versionComparisonRef" />
</template>

<script lang="ts" setup>
import * as TestcaseApi from '@/api/quality/testcase'
import { useMessage } from '@/hooks/web/useMessage'
import { useI18n } from '@/hooks/web/useI18n'
import { useRouter } from 'vue-router'
import VersionComparison from './VersionComparison.vue'

defineOptions({ name: 'CaseVersionList' })

const { t } = useI18n()
const message = useMessage()
const { push } = useRouter()

const _visible = ref(false)
const versionList = ref<any[]>([])
const currentCaseId = ref('')
const versionComparisonRef = ref()

// 打开版本列表
const open = async (caseId: string) => {
  _visible.value = true
  currentCaseId.value = caseId
  await getVersionList(caseId)
}

// 获取版本列表
const getVersionList = async (caseId: string) => {
  try {
    versionList.value = await TestcaseApi.getVersionList(caseId)
  } catch (error) {
    message.error('获取版本列表失败')
  }
}

// 查看版本
const handleViewVersion = async (row: any) => {
  // 跳转到版本详情页面
  push(`/quality/test-case/edit/${row.id}`)
}

// 对比版本
const handleCompareVersion = async (row: any) => {
  // 选择两个版本进行对比
  const selectedVersions = versionList.value.filter(version => version.id !== row.id)
  if (selectedVersions.length === 0) {
    message.warning('没有其他版本可以对比')
    return
  }
  
  // 默认选择第一个版本进行对比
  const versionToCompare = selectedVersions[0]
  versionComparisonRef.value.open(row, versionToCompare)
}

// 设为基线
const handleSetBaseline = async (row: any) => {
  try {
    // 更新用例，设为基线版本
    await TestcaseApi.updateData({
      ...row,
      isBaseline: 1
    })
    
    message.success('设为基线成功')
    // 重新加载版本列表
    await getVersionList(currentCaseId.value)
  } catch (error) {
    message.error('设为基线失败')
    console.error(error)
  }
}

defineExpose({ open })
</script>

<style scoped></style>