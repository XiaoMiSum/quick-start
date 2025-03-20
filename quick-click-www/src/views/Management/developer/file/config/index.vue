<template>
  <!-- 搜索 -->
  <ContentWrap>
    <Search :schema="allSchemas.searchSchema" @search="setSearchParams" @reset="setSearchParams" />

    <el-row class="mt-10px" :gutter="10">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['developer:file:config:add']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
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
            link
            type="primary"
            @click="openForm('update', row.id)"
            v-hasPermi="['developer:file:config:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="primary"
            :disabled="row.master"
            @click="handleMaster(row.id)"
            v-hasPermi="['developer:file:config:update']"
          >
            主配置
          </el-button>
          <el-button link type="primary" @click="handleTest(row.id)"> 测试 </el-button>
          <el-button
            link
            type="danger"
            @click="handleRemove(row.id)"
            v-hasPermi="['developer:file:config:remove']"
          >
            删除
          </el-button>
        </div>
      </template>
    </Table>
  </ContentWrap>
  <!-- 表单弹窗：添加/修改 -->
  <FileConfigForm ref="formRef" @success="getList" />
</template>
<script lang="ts" setup>
import FileConfigForm from './FileConfigForm.vue'

import {
  allSchemas,
  getList,
  setSearchParams,
  tableObject,
  handleRemove,
  handleTest,
  handleMaster
} from './config.d'

defineOptions({ name: 'InfraFile' })

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
