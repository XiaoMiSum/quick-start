<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" @submit.prevent>
      <el-form-item label="智能体名称" prop="name">
        <el-input
          v-model="queryParams.name"
          class="!w-240px"
          clearable
          placeholder="智能体名称"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="状态" class="!w-240px">
          <el-option
            v-for="dict in getDictOptions(DICT_TYPE.AI_AGENT_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
        <el-button plain type="primary" v-hasPermi="['ai:agent:add']" @click="handleAdd">
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button plain type="danger" v-hasPermi="['ai:agent:remove']" :disabled="checked.length === 0" @click="handleDeleteBatch">
          <Icon class="mr-5px" icon="ep:delete" />
          删除
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
      <el-table-column label="智能体名称" prop="name" show-overflow-tooltip min-width="200">
        <template #default="scope">
          <el-button link type="primary" @click="handleEdit(scope.row.id)">
            {{ scope.row.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="描述" prop="description" show-overflow-tooltip min-width="250" />
      <el-table-column label="模型类型" prop="modelType" width="150" />
      <el-table-column label="状态" prop="status" width="100" align="center">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" width="165" />
      <el-table-column label="创建者" prop="creator" width="120" align="center">
        <template #default="scope">
          <user-tag text :value="scope.row.creator" />
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="right" label="操作" width="200">
        <template #default="{ row }">
          <el-button
            v-hasPermi="['ai:agent:update']"
            link
            type="primary"
            @click="handleEdit(row.id)"
          >
            编辑
          </el-button>
          <el-button
            v-hasPermi="['ai:agent:remove']"
            link
            type="danger"
            @click="handleDelete(row.id)"
          >
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

  <!-- 添加/编辑对话框 -->
  <el-dialog
    v-model="dialogVisible"
    :title="dialogTitle"
    width="600px"
    @close="handleCloseDialog"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      @submit.prevent
    >
      <el-form-item label="智能体名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入智能体名称" />
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input
          v-model="formData.description"
          placeholder="请输入描述"
          type="textarea"
          :rows="3"
        />
      </el-form-item>
      <el-form-item label="模型类型" prop="modelType">
        <el-input v-model="formData.modelType" placeholder="请输入模型类型" />
      </el-form-item>
      <el-form-item label="配置参数" prop="config">
        <el-input
          v-model="formData.config"
          placeholder="请输入配置参数"
          type="textarea"
          :rows="4"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="formData.status" placeholder="请选择状态" class="w-full">
          <el-option
            v-for="dict in getDictOptions(DICT_TYPE.AI_AGENT_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="handleSubmit">确 定</el-button>
      <el-button @click="handleCloseDialog">取 消</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import * as AiAgentApi from '@/api/ai'
import { DICT_TYPE } from '@/utils/dictionary'
import { getDictOptions } from '@/utils/dict'

defineOptions({ name: 'AiAgentManagement' })

const message = useMessage() // 消息弹窗
const { push } = useRouter() // 路由

const queryFormRef = ref() // 搜索的表单
const formRef = ref() // 添加/编辑表单

// 查询参数
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  name: '',
  status: undefined
})

// 表单数据
const formData = ref({
  id: undefined as number | undefined,
  name: '',
  description: '',
  modelType: '',
  config: '',
  status: undefined as number | undefined
})

// 表单校验规则
const formRules = {
  name: [{ required: true, message: '智能体名称不能为空', trigger: 'blur' }],
  modelType: [{ required: true, message: '模型类型不能为空', trigger: 'blur' }],
  status: [{ required: true, message: '状态不能为空', trigger: 'change' }]
}

// 状态
const loading = ref(false)
const list = ref<any[]>([])
const total = ref(0)
const checked = ref<any[]>([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)

// 获取列表数据
const getList = async () => {
  loading.value = true
  try {
    const data = await AiAgentApi.getPage(queryParams.value)
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

// 新增按钮
const handleAdd = () => {
  dialogTitle.value = '添加AI智能体'
  isEdit.value = false
  dialogVisible.value = true
  formData.value = {
    id: undefined,
    name: '',
    description: '',
    modelType: '',
    config: '',
    status: undefined
  }
}

// 编辑按钮
const handleEdit = async (id: number) => {
  try {
    const data = await AiAgentApi.getData(id)
    dialogTitle.value = '编辑AI智能体'
    isEdit.value = true
    dialogVisible.value = true
    formData.value = {
      id: data.id,
      name: data.name,
      description: data.description,
      modelType: data.modelType,
      config: data.config,
      status: data.status
    }
  } catch (error) {
    message.error('获取数据失败')
  }
}

// 删除按钮
const handleDelete = async (id: number) => {
  try {
    await message.confirm('确定要删除该AI智能体吗?')
    await AiAgentApi.removeData(id)
    message.success('删除成功')
    getList()
  } catch (error) {
    message.error('删除失败')
  }
}

// 批量删除按钮
const handleDeleteBatch = async () => {
  try {
    await message.confirm('确定要删除选中的AI智能体吗?')
    await Promise.all(checked.value.map(item => AiAgentApi.removeData(item.id)))
    message.success('删除成功')
    getList()
  } catch (error) {
    message.error('删除失败')
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    if (isEdit.value) {
      await AiAgentApi.updateData(formData.value)
      message.success('修改成功')
    } else {
      await AiAgentApi.addData(formData.value)
      message.success('新增成功')
    }
    dialogVisible.value = false
    getList()
  } catch (error) {
    message.error(isEdit.value ? '修改失败' : '新增失败')
  }
}

// 关闭对话框
const handleCloseDialog = () => {
  dialogVisible.value = false
  formRef.value.resetFields()
}

// 状态切换
const handleStatusChange = async (row: any) => {
  try {
    await AiAgentApi.updateData(row)
    message.success('状态更新成功')
  } catch (error) {
    message.error('状态更新失败')
    // 恢复原来的状态
    row.status = row.status === 1 ? 0 : 1
  }
}

// 初始化
onMounted(() => {
  getList()
})
</script>