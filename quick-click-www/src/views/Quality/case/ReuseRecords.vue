<template>
  <el-dialog
    v-model="visible"
    :title="'测试用例复用记录 - ' + testcaseTitle"
    width="800px"
    append-to-body
    @close="handleClose"
  >
    <el-table :data="reuseRecords" v-loading="loading" style="width: 100%">
      <el-table-column prop="targetType" label="目标类型" width="120">
        <template #default="{ row }">
          <el-tag v-if="row.targetType === 'PLAN'" type="primary">测试计划</el-tag>
          <el-tag v-else-if="row.targetType === 'REVIEW'" type="success">测试评审</el-tag>
          <el-tag v-else>{{ row.targetType }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="targetId" label="目标ID" width="150" show-overflow-tooltip />
      <el-table-column prop="operatorId" label="操作人" width="120">
        <template #default="{ row }">
          <user-tag text :value="row.operatorId" />
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="复用时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述" show-overflow-tooltip />
    </el-table>
    
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleClose">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { getRecords } from '@/api/quality/reuse'
import { formatDate } from '@/utils/date'
import { ElMessage } from 'element-plus'

const visible = ref(false)
const loading = ref(false)
const testcaseTitle = ref('')
const testcaseId = ref('')
const reuseRecords = ref<any[]>([])

// 打开复用记录对话框
const open = async (id: string, title: string) => {
  visible.value = true
  testcaseId.value = id
  testcaseTitle.value = title
  await loadReuseRecords()
}

// 加载复用记录
const loadReuseRecords = async () => {
  loading.value = true
  try {
    const response = await getRecords(testcaseId.value)
    reuseRecords.value = response.data || []
  } catch (error) {
    console.error('获取复用记录失败:', error)
    ElMessage.error('获取复用记录失败')
    reuseRecords.value = []
  } finally {
    loading.value = false
  }
}

// 关闭对话框
const handleClose = () => {
  visible.value = false
  testcaseId.value = ''
  testcaseTitle.value = ''
  reuseRecords.value = []
}

// 暴露方法给父组件
defineExpose({ open })
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>