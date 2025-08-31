<template>
  <ContentWrap v-if="bugs && bugs.length > 0">
    <template #header>
      <span>关联的缺陷</span>
    </template>
    <el-table :data="bugs" stripe>
      <el-table-column label="缺陷标题" prop="title">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleViewBug(row.id)">
            {{ row.title }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="status" width="100">
        <template #default="{ row }">
          <ones-tag :type="DICT_TYPE.QUALITY_BUG_STATUS" :value="row.status" />
        </template>
      </el-table-column>
      <el-table-column label="严重程度" prop="severity" width="100">
        <template #default="{ row }">
          <ones-tag :type="DICT_TYPE.QUALITY_BUG_SEVERITY" :value="row.severity" />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="160" />
    </el-table>
  </ContentWrap>
</template>

<script lang="ts" setup>
import * as BugApi from '@/api/quality/bug'
import { DICT_TYPE } from '@/utils/dictionary'
import { useRouter } from 'vue-router'

defineOptions({ name: 'RelatedBugs' })

const props = defineProps({
  testcaseId: {
    type: String,
    required: true
  }
})

const { push, resolve } = useRouter()

const bugs = ref<any[]>([])

const handleViewBug = (bugId: string) => {
  const routeData = resolve({ path: '/quality/bug/view/' + bugId });
  window.open(routeData.href, '_blank');
}

const loadRelatedBugs = async () => {
  try {
    const response = await BugApi.getPage({ testcaseId: props.testcaseId })
    bugs.value = response.list || []
  } catch (error) {
    console.error('获取关联缺陷信息失败:', error)
  }
}

// 组件挂载时加载数据
onMounted(() => {
  loadRelatedBugs()
})

// 监听测试用例ID变化
watch(() => props.testcaseId, () => {
  loadRelatedBugs()
})

defineExpose({ loadRelatedBugs })
</script>