<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <Search :schema="allSchemas.searchSchema" @search="setSearchParams" @reset="setSearchParams" />
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
            v-hasPermi="['developer:error-log:update']"
            link
            type="primary"
            @click="handleOpenDetail(row.id)"
            >处理
          </el-button>
          <el-button
            v-hasPermi="['developer:error-log:remove']"
            link
            type="danger"
            @click="handleRemove(row.id)"
            >删除
          </el-button>
        </div>
      </template>
    </Table>
  </ContentWrap>

  <ErrorLogDetail ref="detailRef" @close="getList" />
</template>

<script lang="ts" setup>
import ErrorLogDetail from './ErrorLogDetail.vue' // 代码高亮插件

import { allSchemas, getList, setSearchParams, tableObject, handleRemove } from './ErrorLog.d'

const detailRef = ref()
/** 查看详情 */
const handleOpenDetail = (id: number) => {
  detailRef.value.open(id)
}

onMounted(() => {
  getList()
})
</script>
