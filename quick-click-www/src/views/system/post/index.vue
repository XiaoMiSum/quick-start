<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="岗位名称" prop="name">
        <el-input
          v-model="queryParams.name"
          clearable
          placeholder="请输入岗位名称"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位编码" prop="code">
        <el-input
          v-model="queryParams.code"
          clearable
          placeholder="请输入岗位编码"
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
    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:post:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list">
      <el-table-column align="center" label="岗位编号" prop="id" />
      <el-table-column align="center" label="岗位名称" prop="name" />
      <el-table-column align="center" label="岗位编码" prop="code" />
      <el-table-column align="center" label="岗位顺序" prop="sort" />
      <el-table-column align="center" label="岗位备注" prop="remark" />
      <el-table-column align="center" label="状态" prop="status">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="创建时间"
        prop="createTime"
        width="180"
      />
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button
            v-hasPermi="['system:post:update']"
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
          >
            编辑
          </el-button>
          <el-button
            v-hasPermi="['system:post:remove']"
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
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

  <!-- 表单弹窗：添加/修改 -->
  <PostForm ref="formRef" @success="handleQuery" />
</template>

<script lang="ts" setup>
import { dateFormatter } from '@/utils/formatTime'
import * as HTTP from '@/api/system/post'
import PostForm from './PostForm.vue'
import { COMMON_STATUS_ENUM } from '@/utils/enums'

defineOptions({ name: 'SystemPost' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  code: '',
  name: '',
  status: undefined
})
const queryFormRef = ref() // 搜索的表单

/** 查询岗位列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await HTTP.listData(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 修改岗位状态 */
const handleStatusChange = async (row: any) => {
  try {
    // 修改状态的二次确认
    const text = row.status === COMMON_STATUS_ENUM.ENABLE ? '启用' : '停用'
    await message.confirm('确认要"' + text + '""' + row.name + '"吗?')
    // 发起修改状态
    await HTTP.updateData({ id: row.id, status: row.status })
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status =
      row.status === COMMON_STATUS_ENUM.ENABLE
        ? COMMON_STATUS_ENUM.DISABLE
        : COMMON_STATUS_ENUM.ENABLE
  }
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await HTTP.delData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
