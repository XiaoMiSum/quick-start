<template>
  <Dialog v-model="_visible" title="缺陷关联管理" width="90%">
    <ContentWrap>
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="mb-4">
            <el-text class="mx-1" type="primary">关联的缺陷</el-text>
          </div>
          <el-table :data="associatedBugs" stripe>
            <el-table-column label="缺陷标题" prop="title" show-overflow-tooltip>
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
            <el-table-column label="责任人" prop="supervisor" width="100">
              <template #default="{ row }">
                <user-tag text :value="row.supervisor" />
              </template>
            </el-table-column>
            <el-table-column align="center" label="操作" width="120">
              <template #default="{ row }">
                <el-button link type="primary" @click="handleViewBug(row.id)">
                  查看
                </el-button>
                <el-button link type="danger" @click="handleRemoveAssociation(row)">
                  解除关联
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
        <el-col :span="12">
          <div class="mb-4">
            <el-text class="mx-1" type="primary">可关联的缺陷</el-text>
          </div>
          <el-form :inline="true" :model="searchForm" class="mb-4">
            <el-form-item label="缺陷标题">
              <el-input v-model="searchForm.title" placeholder="请输入缺陷标题" />
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="searchForm.status" clearable placeholder="请选择状态">
                <el-option
                  v-for="item in bugStatusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="searchUnassociatedBugs">
                <Icon class="mr-5px" icon="ep:search" />
                搜索
              </el-button>
            </el-form-item>
          </el-form>
          <el-table :data="unassociatedBugs" stripe>
            <el-table-column label="缺陷标题" prop="title" show-overflow-tooltip>
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
            <el-table-column label="责任人" prop="supervisor" width="100">
              <template #default="{ row }">
                <user-tag text :value="row.supervisor" />
              </template>
            </el-table-column>
            <el-table-column align="center" label="操作" width="80">
              <template #default="{ row }">
                <el-button link type="primary" @click="handleAddAssociation(row)">
                  关联
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </ContentWrap>
    
    <template #footer>
      <el-button @click="_visible = false">关闭</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import * as BugApi from '@/api/quality/bug'
import * as TestcaseApi from '@/api/quality/testcase'
import { DICT_TYPE } from '@/utils/dictionary'
import { useMessage } from '@/hooks/web/useMessage'
import { useRouter } from 'vue-router'

defineOptions({ name: 'BugAssociation' })

const { push, resolve } = useRouter()
const message = useMessage()

const _visible = ref(false)
const testcaseId = ref('')
const testcaseInfo = ref<any>(null)

const associatedBugs = ref<any[]>([])
const unassociatedBugs = ref<any[]>([])

// 缺陷状态选项
const bugStatusOptions = ref([
  { label: '新建', value: 'New' },
  { label: '已指派', value: 'Opened' },
  { label: '已确认', value: 'Confirmed' },
  { label: '已修复', value: 'Fixed' },
  { label: '已关闭', value: 'Closed' },
  { label: '已驳回', value: 'Rejected' },
  { label: '重新打开', value: 'Reopened' }
])

const searchForm = ref({
  title: '',
  status: ''
})

// 打开关联管理对话框
const open = async (id: string) => {
  _visible.value = true
  testcaseId.value = id
  
  // 获取测试用例信息
  try {
    testcaseInfo.value = await TestcaseApi.getData(id)
  } catch (error) {
    console.error('获取测试用例信息失败:', error)
  }
  
  // 加载关联的缺陷
  await loadAssociatedBugs()
  
  // 加载未关联的缺陷
  await loadUnassociatedBugs()
}

// 加载已关联的缺陷
const loadAssociatedBugs = async () => {
  try {
    const response = await BugApi.getPage({ testcaseId: testcaseId.value })
    associatedBugs.value = response.list || []
  } catch (error) {
    message.error('获取关联缺陷列表失败')
    console.error(error)
  }
}

// 加载未关联的缺陷
const loadUnassociatedBugs = async () => {
  try {
    // 这里可以根据需要添加筛选条件，比如同一项目的缺陷
    const response = await BugApi.getPage({ 
      projectId: testcaseInfo.value?.projectId,
      testcaseId: '' // 空字符串表示未关联
    })
    unassociatedBugs.value = response.list || []
  } catch (error) {
    message.error('获取未关联缺陷列表失败')
    console.error(error)
  }
}

// 搜索未关联的缺陷
const searchUnassociatedBugs = async () => {
  try {
    const response = await BugApi.getPage({ 
      projectId: testcaseInfo.value?.projectId,
      title: searchForm.value.title,
      status: searchForm.value.status
    })
    // 过滤掉已关联的缺陷
    const associatedBugIds = associatedBugs.value.map(bug => bug.id)
    unassociatedBugs.value = (response.list || []).filter(bug => 
      !associatedBugIds.includes(bug.id) && !bug.testcaseId
    )
  } catch (error) {
    message.error('搜索缺陷失败')
    console.error(error)
  }
}

// 查看缺陷详情
const handleViewBug = (bugId: string) => {
  const routeData = resolve({ path: '/quality/bug/view/' + bugId });
  window.open(routeData.href, '_blank');
}

// 添加关联
const handleAddAssociation = async (bug: any) => {
  try {
    // 更新缺陷的testcaseId字段
    await BugApi.updateData({
      ...bug,
      testcaseId: testcaseId.value
    })
    
    message.success('关联成功')
    // 重新加载数据
    await loadAssociatedBugs()
    await loadUnassociatedBugs()
  } catch (error) {
    message.error('关联失败')
    console.error(error)
  }
}

// 解除关联
const handleRemoveAssociation = async (bug: any) => {
  try {
    // 清除缺陷的testcaseId字段
    await BugApi.updateData({
      ...bug,
      testcaseId: null
    })
    
    message.success('解除关联成功')
    // 重新加载数据
    await loadAssociatedBugs()
    await loadUnassociatedBugs()
  } catch (error) {
    message.error('解除关联失败')
    console.error(error)
  }
}

defineExpose({ open })
</script>