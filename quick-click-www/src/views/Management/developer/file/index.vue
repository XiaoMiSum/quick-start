<template>
  <!-- 搜索 -->
  <ContentWrap>
    <Search :schema="allSchemas.searchSchema" @search="setSearchParams" @reset="setSearchParams" />

    <el-row class="mt-10px" :gutter="10">
      <el-col :span="1.5">
        <el-button type="primary" plain @click="openForm">
          <Icon icon="ep:upload" class="mr-5px" /> 上传文件
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <ContentWrap>
    <!-- 列表 -->
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
            v-hasPermi="['developer:file:remove']"
            link
            type="danger"
            @click="handleRemove(row.id)"
            >删除
          </el-button>
        </div>
      </template>
    </Table>
  </ContentWrap>
  <!-- 表单弹窗：添加/修改 -->
  <FileForm ref="formRef" @success="getList" />
</template>
<script lang="ts" setup>
import FileForm from './FileForm.vue'

import { allSchemas, getList, setSearchParams, tableObject, handleRemove } from './file.d'

defineOptions({ name: 'InfraFile' })

/** 添加/修改操作 */
const formRef = ref()
const openForm = () => {
  formRef.value.open()
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
