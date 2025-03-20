<template>
  <!-- 搜索工作栏 -->
  <ContentWrap>
    <Search :schema="allSchemas.searchSchema" @search="setSearchParams" @reset="setSearchParams" />
    <el-row class="mt-10px" :gutter="10">
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
    <Table
      :columns="allSchemas.tableColumns"
      :loading="tableObject.loading"
      :data="tableObject.tableList"
      :pagination="{
        background: true,
        total: tableObject.total
      }"
      v-model:pageSize="tableObject.pageSize"
      v-model:currentPage="tableObject.currentPage"
    >
      <template #action="{ row }">
        <div class="flex items-center justify-center">
          <el-button
            v-hasPermi="['developer:dictionary:update']"
            link
            type="primary"
            @click="openForm('update', row)"
          >
            修改
          </el-button>
          <el-button link type="primary" @click="handleLinkClick(row.code)"> 数据 </el-button>
          <el-button
            v-hasPermi="['developer:dictionary:remove']"
            link
            type="danger"
            @click="handleDelete(row.id)"
          >
            删除
          </el-button>
        </div>
      </template>
    </Table>
    <!-- 分页 -->
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <DictionaryForm ref="formRef" @success="getList" />
</template>

<script lang="ts" setup>
import DictionaryForm from './DictionaryForm.vue'
import { allSchemas, getList, setSearchParams, tableObject, handleDelete } from './Dictionary.d'

const { push } = useRouter()

defineOptions({ name: 'Dictionary' })

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, data?: any) => {
  formRef.value.open(type, data)
}

/** 数据按钮操作 */
const handleLinkClick = (code: string) => {
  push({ path: '/developer/dictionary/values', query: { dictCode: code } })
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
