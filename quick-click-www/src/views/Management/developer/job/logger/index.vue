<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <Search
      :schema="allSchemas.searchSchema"
      :model="queryParams"
      @search="setSearchParams"
      @reset="setSearchParams"
    />
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
            v-hasPermi="['developer:job:query']"
            link
            type="primary"
            @click="openDetail(row.id)"
          >
            详细
          </el-button>
        </div>
      </template>
    </Table>
  </ContentWrap>
  <!-- 表单弹窗：查看 -->
  <JobLogDetail ref="detailRef" />
</template>
<script lang="ts" setup>
import JobLogDetail from './JobLogDetail.vue'

import { allSchemas, getList, tableObject, setSearchParams } from './job.logger.d'

defineOptions({ name: 'InfraJobLog' })

const { query } = useRoute() // 查询参数
const queryParams = ref({
  jobId: query ? query.id : undefined,
  handlerName: undefined,
  beginTime: undefined,
  endTime: undefined,
  status: undefined
})

/** 查看操作 */
const detailRef = ref()
const openDetail = (rowId?: number) => {
  detailRef.value.open(rowId)
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
