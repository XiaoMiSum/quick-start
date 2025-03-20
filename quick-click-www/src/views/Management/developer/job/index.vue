<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <Search :schema="allSchemas.searchSchema" @search="setSearchParams" @reset="setSearchParams" />
    <el-row class="mt-10px" :gutter="10">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['developer:job:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
        <el-button v-hasPermi="['developer:job:query']" plain type="info" @click="handleJobLog(0)">
          <Icon class="mr-5px" icon="ep:zoom-in" />
          执行日志
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
            v-hasPermi="['developer:job:update']"
            link
            type="primary"
            @click="openForm('update', row.id)"
          >
            修改
          </el-button>
          <el-button
            v-hasPermi="['developer:job:update']"
            link
            type="primary"
            @click="handleChangeStatus(row)"
          >
            {{ row.status === InfraJobStatus.STOP ? '开启' : '暂停' }}
          </el-button>
          <el-button
            v-hasPermi="['developer:job:remove']"
            link
            type="danger"
            @click="handleRemove(row.id)"
          >
            删除
          </el-button>
          <el-dropdown
            v-hasPermi="['developer:job:trigger', 'developer:job:query']"
            @command="(command) => handleCommand(command, row)"
          >
            <el-button link type="primary">
              <Icon icon="ep:d-arrow-right" />
              更多
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item v-if="checkPermi(['developer:job:trigger'])" command="handleRun">
                  执行一次
                </el-dropdown-item>
                <el-dropdown-item v-if="checkPermi(['developer:job:query'])" command="openDetail">
                  任务详细
                </el-dropdown-item>
                <el-dropdown-item v-if="checkPermi(['developer:job:query'])" command="handleJobLog">
                  调度日志
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </template>
    </Table>
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <JobForm ref="formRef" @success="getList" />
  <!-- 表单弹窗：查看 -->
  <JobDetail ref="detailRef" />
</template>
<script lang="ts" setup>
import { checkPermi } from '@/utils/permission'
import JobForm from './JobForm.vue'
import JobDetail from './JobDetail.vue'

import { InfraJobStatus } from '@/utils/constants'

import {
  allSchemas,
  getList,
  setSearchParams,
  tableObject,
  handleRemove,
  handleChangeStatus,
  handleRun
} from './job.d'

defineOptions({ name: 'InfraJob' })

const { push } = useRouter() // 路由

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** '更多'操作按钮 */
const handleCommand = (command, row) => {
  switch (command) {
    case 'handleRun':
      handleRun(row)
      break
    case 'openDetail':
      openDetail(row.id)
      break
    case 'handleJobLog':
      handleJobLog(row?.id)
      break
    default:
      break
  }
}

/** 查看操作 */
const detailRef = ref()
const openDetail = (id: number) => {
  detailRef.value.open(id)
}

/** 跳转执行日志 */
const handleJobLog = (id: number) => {
  if (id > 0) {
    push({
      path: '/developer/job/log',
      query: {
        jobId: id
      }
    })
  } else {
    push('/developer/job/log')
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
