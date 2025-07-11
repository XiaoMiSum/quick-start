<template>
  <ContentWrap>
    <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
      <el-form-item label="" prop="dictCode">
        <el-select v-model="queryParams.dictCode" class="!w-240px" @change="handleQuery" filterable>
          <el-option
            v-for="item in dictionaryList"
            :key="item.code"
            :label="item.name"
            :value="item.code"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="label">
        <el-input
          v-model="queryParams.label"
          placeholder="请输入字典标签"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery" type="primary"
          ><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button
        >
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
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="字典标签" align="center" prop="label" />
      <el-table-column label="字典键值" align="center" prop="value" />
      <el-table-column label="字典排序" align="center" prop="sort" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <ones-tag :type="DICT_TYPE.COMMON_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="颜色类型" align="center" prop="colorType" />
      <el-table-column class-name="small-padding fixed-width" label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row)"
            v-hasPermi="['developer:dictionary:update']"
          >
            修改
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['developer:dictionary:remove']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <DictDataForm ref="formRef" @success="getList" />
</template>
<script lang="ts" setup>
import { DICT_TYPE } from '@/utils/dictionary'
import * as VALUE_HTTP from '@/api/management/developer/dictionary/value'
import * as DICT_HTTP from '@/api/management/developer/dictionary'
import DictDataForm from './ValueForm.vue'

defineOptions({ name: 'SystemDictData' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const route = useRoute() // 路由

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  label: '',
  status: undefined,
  dictCode: route.query.dictCode
})
const queryFormRef = ref() // 搜索的表单

const dictionaryList = ref<DICT_HTTP.DictionaryVO[]>() // 字典类型的列表

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await VALUE_HTTP.getPage(queryParams)
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

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, data?: any) => {
  formRef.value.open(type, data, queryParams.dictCode)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await VALUE_HTTP.removeData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(async () => {
  await getList()
  // 查询字典（精简)列表
  dictionaryList.value = await DICT_HTTP.getSimple()
})
</script>
