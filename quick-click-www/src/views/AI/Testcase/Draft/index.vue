<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" @submit.prevent>
      <el-form-item label="需求ID" prop="requirementId">
        <el-input
          v-model="queryParams.requirementId"
          class="!w-240px"
          clearable
          placeholder="需求ID"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb-10px">
      <el-col :span="1.5">
        <el-button plain type="danger" v-hasPermi="['ai:testcase:remove']" :disabled="checked.length === 0" @click="handleDeleteBatch">
          <Icon class="mr-5px" icon="ep:delete" />
          批量删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain type="primary" v-hasPermi="['ai:testcase:confirm']" :disabled="checked.length === 0" @click="handleConfirmBatch">
          <Icon class="mr-5px" icon="ep:check" />
          批量确认
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain type="warning" v-hasPermi="['ai:testcase:reject']" :disabled="checked.length === 0" @click="handleRejectBatch">
          <Icon class="mr-5px" icon="ep:close" />
          批量拒绝
        </el-button>
      </el-col>
    </el-row>

    <!-- 列表 -->
    <el-table
      ref="multipleTableRef"
      v-loading="loading"
      :data="list"
      :row-key="(row) => row.id"
      highlight-current-row
      width="100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column :reserve-selection="true" type="selection" width="35" />
      <el-table-column label="测试用例标题" prop="title" show-overflow-tooltip min-width="200">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row.id)">
            {{ scope.row.title }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="需求ID" prop="requirementId" width="150" />
      <el-table-column label="项目ID" prop="projectId" width="150" />
      <el-table-column label="模块ID" prop="moduleId" width="150" />
      <el-table-column label="优先级" prop="priority" width="100" />
      <el-table-column label="创建时间" prop="createTime" width="165" />
      <el-table-column align="center" fixed="right" label="操作" width="250">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleDetail(row.id)">
            详情
          </el-button>
          <el-button link type="primary" v-hasPermi="['ai:testcase:confirm']" @click="handleConfirm(row.id)">
            确认
          </el-button>
          <el-button link type="danger" v-hasPermi="['ai:testcase:reject']" @click="handleReject(row.id)">
            拒绝
          </el-button>
          <el-button link type="primary" v-hasPermi="['ai:testcase:update']" @click="handleEdit(row.id)">
            编辑
          </el-button>
          <el-button link type="danger" v-hasPermi="['ai:testcase:remove']" @click="handleDelete(row.id)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <Pagination
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 查看对话框 -->
  <el-dialog
    v-model="dialogVisible"
    title="AI生成测试用例详情"
    width="800px"
    @close="handleCloseDialog"
  >
    <el-form
      ref="formRef"
      :model="formData"
      label-width="120px"
      @submit.prevent
    >
      <el-row>
        <el-col :span="12">
          <el-form-item label="测试用例标题:">
            <span>{{ formData.title }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态:">
            <ones-tag :type="DICT_TYPE.AI_GENERATED_TESTCASE_STATUS" :value="formData.status" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="需求ID:">
            <span>{{ formData.requirementId }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="项目ID:">
            <span>{{ formData.projectId }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="模块ID:">
            <span>{{ formData.moduleId }}</span>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="优先级:">
            <span>{{ formData.priority }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="标签:">
        <span>{{ formData.tags }}</span>
      </el-form-item>
      <el-form-item label="前置条件:">
        <div v-html="formData.prerequisite"></div>
      </el-form-item>
      <el-form-item label="测试步骤:">
        <div v-html="formData.steps"></div>
      </el-form-item>
      <el-form-item label="描述:">
        <div v-html="formData.description"></div>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="handleCloseDialog">关 闭</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import * as AiGeneratedTestcaseDraftApi from '@/api/ai/generatedTestcaseDraft'
import { DICT_TYPE } from '@/utils/dictionary'
import { getDictOptions } from '@/utils/dict'
import { useRouter } from 'vue-router'

defineOptions({ name: 'AiGeneratedTestcaseDraft' })

const message = useMessage() // 消息弹窗
const router = useRouter() // 路由

const queryFormRef = ref() // 搜索的表单
const formRef = ref() // 表单

// 查询参数（默认只查询待确认状态的测试用例）
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  requirementId: ''
})

// 表单数据
const formData = ref({
  id: undefined as number | undefined,
  requirementId: '',
  projectId: '',
  moduleId: '',
  agentId: undefined as number | undefined,
  title: '',
  description: '',
  prerequisite: '',
  steps: '',
  priority: '',
  tags: '',
  status: undefined as number | undefined,
  createTime: ''
})

// 状态
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const checked = ref<any[]>([])
const dialogVisible = ref(false)

// 获取列表数据
const getList = async () => {
  loading.value = true
  try {
    const data = await AiGeneratedTestcaseDraftApi.getDraftPage(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

// 搜索
const handleQuery = () => {
  queryParams.value.pageNo = 1
  getList()
}

// 重置查询
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

// 处理选择变化
const handleSelectionChange = (val: any[]) => {
  checked.value = val
}

// 查看按钮
const handleView = async (id: number) => {
  try {
    const data = await AiGeneratedTestcaseDraftApi.getData(id)
    dialogVisible.value = true
    formData.value = {
      ...data
    }
  } catch (error) {
    message.error('获取数据失败')
  }
}

// 详情按钮
const handleDetail = (id: number) => {
  router.push(`/ai/testcase/${id}`)
}

// 编辑按钮
const handleEdit = (id: number) => {
  router.push(`/ai/testcase/edit/${id}`)
}

// 确认按钮
const handleConfirm = async (id: number) => {
  try {
    await message.confirm('确定要确认该AI生成的测试用例吗?')
    await AiGeneratedTestcaseDraftApi.confirmData(id)
    message.success('确认成功')
    getList()
  } catch (error) {
    message.error('确认失败')
  }
}

// 拒绝按钮
const handleReject = async (id: number) => {
  try {
    await message.confirm('确定要拒绝该AI生成的测试用例吗?')
    await AiGeneratedTestcaseDraftApi.rejectData(id)
    message.success('拒绝成功')
    getList()
  } catch (error) {
    message.error('拒绝失败')
  }
}

// 删除按钮
const handleDelete = async (id: number) => {
  try {
    await message.confirm('确定要删除该AI生成的测试用例吗?')
    await AiGeneratedTestcaseDraftApi.removeData(id)
    message.success('删除成功')
    getList()
  } catch (error) {
    message.error('删除失败')
  }
}

// 批量删除按钮
const handleDeleteBatch = async () => {
  try {
    await message.confirm('确定要删除选中的AI生成的测试用例吗?')
    await Promise.all(checked.value.map(item => AiGeneratedTestcaseDraftApi.removeData(item.id)))
    message.success('删除成功')
    getList()
  } catch (error) {
    message.error('删除失败')
  }
}

// 批量确认按钮
const handleConfirmBatch = async () => {
  try {
    await message.confirm('确定要确认选中的AI生成的测试用例吗?')
    await Promise.all(checked.value.map(item => AiGeneratedTestcaseDraftApi.confirmData(item.id)))
    message.success('确认成功')
    getList()
  } catch (error) {
    message.error('确认失败')
  }
}

// 批量拒绝按钮
const handleRejectBatch = async () => {
  try {
    await message.confirm('确定要拒绝选中的AI生成的测试用例吗?')
    await Promise.all(checked.value.map(item => AiGeneratedTestcaseDraftApi.rejectData(item.id)))
    message.success('拒绝成功')
    getList()
  } catch (error) {
    message.error('拒绝失败')
  }
}

// 关闭对话框
const handleCloseDialog = () => {
  dialogVisible.value = false
  formRef.value.resetFields()
}

// 初始化
onMounted(() => {
  getList()
})
</script>