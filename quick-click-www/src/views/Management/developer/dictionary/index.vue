<template>
  <!-- 搜索工作栏 -->
  <ContentWrap>
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="68px">
      <el-form-item label="" prop="name">
        <el-input
          v-model="queryParams.name"
          class="!w-240px"
          clearable
          placeholder="请输入字典名称"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="code">
        <el-input
          v-model="queryParams.code"
          class="!w-240px"
          clearable
          placeholder="请输入字典编码"
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
    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['developer:dictionary:add']"
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
      <el-table-column align="center" label="字典名称" prop="name" />
      <el-table-column align="center" label="字典编码" prop="code" />
      <el-table-column align="center" label="状态" prop="status">
        <template #default="scope">
          <ones-tag :type="DICT_TYPE.COMMON_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column align="center" label="来源" prop="source" />
      <el-table-column
        class-name="small-padding fixed-width"
        width="300"
        align="center"
        label="操作"
      >
        <template #default="scope">
          <el-button
            v-hasPermi="['developer:dictionary:update']"
            link
            type="primary"
            @click="openForm('update', scope.row)"
          >
            修改
          </el-button>
          <el-button link type="primary" @click="handleLinkClick(scope.row.code)"> 数据 </el-button>
          <el-button
            v-hasPermi="['developer:dictionary:remove']"
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
  <DictionaryForm ref="formRef" @success="getList" />
</template>

<script lang="ts" setup>
import { DICT_TYPE } from '@/utils/dictionary'
import * as HTTP from '@/api/management/developer/dictionary'
import DictionaryForm from './DictionaryForm.vue'

const { push } = useRouter()
defineOptions({ name: 'Dictionary' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 字典表格数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: '',
  code: ''
})
const queryFormRef = ref() // 搜索的表单

/** 查询字典类型列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await HTTP.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, data?: any) => {
  formRef.value.open(type, data)
}

/** 数据按钮操作 */
const handleLinkClick = (code: string) => {
  push({ path: '/developer/dictionary/values', query: { dictCode: code } })
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await HTTP.removeData(id)
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
