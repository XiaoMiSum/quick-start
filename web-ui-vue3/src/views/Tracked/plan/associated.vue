<template>
  <ContentWrap :title="'测试计划：' + title">
    <el-row :gutter="20">
      <!-- 左侧模块树 -->
      <el-col :span="5" :xs="24">
        <ContentWrap class="h-1/1">
          <ModuleTree @node-click="handleNodeClick" />
        </ContentWrap>
      </el-col>
      <el-col :span="19" :xs="24">
        <ContentWrap>
          <!-- 搜索工作栏 -->
          <el-form ref="queryFormRef" :inline="true" :model="queryParams">
            <el-form-item label="" prop="caseName">
              <el-input
                v-model="queryParams.caseName"
                class="!w-240px"
                clearable
                placeholder="请输入用例名称"
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
                v-hasPermi="['plan:case:add']"
                plain
                type="primary"
                @click="handleAssociCase()"
              >
                <Icon class="mr-5px" icon="ep:link" />
                关联用例
              </el-button>
              <el-button
                v-hasPermi="['plan:case:remove']"
                :disabled="!checked || checked.length < 1"
                plain
                type="danger"
                @click="handleBatchUnlinkCase"
              >
                <Icon class="mr-5px" icon="fa-solid:unlink" />
                取消关联
              </el-button>
              <el-button plain type="primary" @click="handleAssociCase()">
                <Icon class="mr-5px" icon="ep:caret-right" />
                开始执行
              </el-button>
            </el-col>
          </el-row>
        </ContentWrap>

        <!-- 列表 -->
        <ContentWrap>
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
            <el-table-column align="center" label="编号" prop="id" width="60" />
            <el-table-column label="用例名称" prop="name" show-overflow-tooltip width="200" />
            <el-table-column label="所属模块" prop="path" show-overflow-tooltip width="200" />
            <el-table-column align="center" label="用例等级" prop="level">
              <template #default="scope">
                <EnumTag :enums="CASE_LEVEL_ENUMS" :value="scope.row.level" />
              </template>
            </el-table-column>
            <el-table-column align="center" label="标签" prop="tags" show-overflow-tooltip>
              <template #default="scope">
                <el-tag v-for="item in scope.row.tags" :key="item"> {{ item }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" label="执行结果" prop="executeResult">
              <template #default="scope">
                <EnumTag :enums="RESULT_ENUMS" :value="scope.row.executeResult" />
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              label="执行人"
              prop="executorUser"
              show-overflow-tooltip
            />

            <el-table-column
              :formatter="dateFormatter"
              align="center"
              label="执行时间"
              prop="executeTime"
              width="170"
            />
            <el-table-column :width="150" align="center" fixed="right" label="操作">
              <template #default="scope">
                <el-tooltip content="执行" placement="top">
                  <el-button
                    v-hasPermi="['plan:case:execute']"
                    circle
                    plain
                    type="primary"
                    @click="handleExecuteCase(scope.row)"
                  >
                    <Icon icon="ep:checked" />
                  </el-button>
                </el-tooltip>
                <el-tooltip content="移除" placement="top">
                  <el-button
                    v-hasPermi="['plan:case:remove']"
                    circle
                    plain
                    type="danger"
                    @click="handleDelete(scope.row.id)"
                  >
                    <Icon icon="ep:unlock" />
                  </el-button>
                </el-tooltip>
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
      </el-col>
    </el-row>
  </ContentWrap>

  <CaseAssociated
    ref="caseAssociated"
    :data-id="currentPlanId"
    source="plan"
    @close="handleQuery"
  />
  <CaseViewer ref="caseViewer" source="plan" @close="handleQuery" />
</template>

<script lang="ts" setup>
import { ModuleTree } from '@/views/Project/components/index'
import { CaseAssociated, CaseViewer } from '../components'

import { dateFormatter } from '@/utils/formatTime'

import { CASE_LEVEL_ENUMS, RESULT_ENUMS } from '@/utils/enums'

import * as HTTP from '@/api/tracked/plan'

import { useRoute } from 'vue-router' //1.先在需要跳转的页面引入useRouter
import { useAppStore } from '@/store/modules/app'

const { params } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数

const message = useMessage() // 消息弹窗

const appStore = useAppStore()

defineOptions({ name: 'PlanAssociated' })

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  caseName: '',
  moduleId: null
})

const checked = ref<any>([])
const currentPlanId = ref<any>(-1)
const title = ref('')
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)
const queryFormRef = ref() // 搜索的表单

const getList = async () => {
  loading.value = true
  try {
    queryParams.value.planId = currentPlanId.value
    const data = await HTTP.getPlanCase(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = async () => {
  queryParams.value.pageNo = 1
  getList()
}

const resetQuery = async () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm('是否解除关联所选用例？')
    // 发起删除
    await HTTP.removeAssociCase(id)
    // 刷新列表
    await getList()
  } catch {}
}

const caseAssociated = ref()
const handleAssociCase = async () => {
  caseAssociated.value.open()
}

const caseViewer = ref()
const handleExecuteCase = async (data?: any) => {
  caseViewer.value.open({ id: data.id, planId: data.planId })
}

const handleNodeClick = async (row: any) => {
  queryParams.value.moduleId = row.id === 0 ? null : row.id
  handleQuery()
}

const handleSelectionChange = async (val: any[]) => {
  checked.value = val.map((item) => item.id)
}

const handleBatchUnlinkCase = async () => {
  await message.delConfirm('是否确认取消关联选中的用例？')
  await HTTP.batchRemoveAssociCase(checked.value)
  toggleSelection()
  await handleQuery()
}

const multipleTableRef = ref()
const toggleSelection = () => {
  multipleTableRef.value!.clearSelection()
  checked.value = []
}

/** 初始化 **/
onMounted(async () => {
  appStore.setProjectPick(false)
  if (params && params.planId) {
    currentPlanId.value = params.planId
    const data = await HTTP.getData(params.planId)
    title.value = data.name
  }
  await getList()
})
</script>

<style scoped></style>
