<template>
  <ContentWrap :statistics="statistics" :title="'测试计划：' + title">
    <el-row :gutter="20">
      <!-- 左侧模块树 -->
      <el-col :span="5" :xs="24">
        <ContentWrap class="h-1/1">
          <DefaultNodeTree @node-click="handleNodeClick" />
        </ContentWrap>
      </el-col>
      <el-col :span="19" :xs="24">
        <ContentWrap>
          <!-- 搜索工作栏 -->
          <el-form ref="queryFormRef" :inline="true" :model="queryParams">
            <el-form-item label="" prop="title">
              <el-input
                v-model="queryParams.title"
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
                @click="handleBatchUnlinkCase(checked)"
              >
                <Icon class="mr-5px" icon="fa-solid:unlink" />
                取消关联
              </el-button>
              <el-button plain type="primary" @click="handleOpenImportCase">
                <Icon class="mr-5px" icon="ep:upload" />
                从评审导入
              </el-button>
              <el-button plain type="success" @click="handleExecuteFirstCase">
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
            <el-table-column label="用例名称" prop="title" show-overflow-tooltip width="200">
              <template #default="scope">
                <el-button link type="primary" @click="handleExecuteCase(scope.row.id)">
                  {{ scope.row.title }}
                </el-button>
              </template>
            </el-table-column>
            <el-table-column label="所属模块" prop="path" show-overflow-tooltip width="200" />
            <el-table-column align="center" label="用例等级" prop="priority">
              <template #default="scope">
                <ones-tag :type="DICT_TYPE.QUALITY_TESTCASE_PRIORITY" :value="scope.row.priority" />
              </template>
            </el-table-column>
            <el-table-column align="center" label="标签" prop="tags" show-overflow-tooltip>
              <template #default="scope">
                <el-tag v-for="(item, index) in scope.row.tags" :key="index" class="ml-2">
                  {{ item }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" label="执行结果" prop="result">
              <template #default="scope">
                <ones-tag :type="DICT_TYPE.QUALITY_TEST_STATUS" :value="scope.row.result" />
              </template>
            </el-table-column>
            <el-table-column align="center" label="执行人" prop="executor" show-overflow-tooltip>
              <template #default="scope">
                <user-tag :value="scope.row.executor" />
              </template>
            </el-table-column>
            <el-table-column align="center" label="执行时间" prop="executeTime" width="170" />
            <el-table-column :width="150" align="center" fixed="right" label="操作">
              <template #default="scope">
                <el-tooltip content="执行" placement="top">
                  <el-button
                    v-hasPermi="['plan:case:execute']"
                    circle
                    plain
                    type="success"
                    @click="handleExecuteCase(scope.row.id)"
                  >
                    <Icon icon="ep:caret-right" />
                  </el-button>
                </el-tooltip>
                <el-tooltip content="移除" placement="top">
                  <el-button
                    v-hasPermi="['plan:case:remove']"
                    circle
                    plain
                    type="danger"
                    @click="handleBatchUnlinkCase([scope.row.id])"
                  >
                    <Icon icon="fa-solid:unlink" />
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
    :project-id="globalStore.getCurrentProject"
    :enums="getDictOptions(DICT_TYPE.QUALITY_TEST_STATUS)"
    source="plan"
    @close="handleQuery"
  />
  <CaseViewer
    ref="caseViewer"
    :enums="getDictOptions(DICT_TYPE.QUALITY_TEST_STATUS)"
    @close="handleQuery"
  />
  <CaseImports ref="caseImports" @close="getList" />
</template>

<script lang="ts" setup>
import { DefaultNodeTree } from '@/views/components/node'
import { CaseAssociated } from '../components'
import CaseImports from './CaseImports.vue'
import CaseViewer from './CaseViewer.vue'

import * as HTTP from '@/api/quality/plan'

import { useTagsViewStore } from '@/store/modules/tagsView'
import { useRoute } from 'vue-router' //1.先在需要跳转的页面引入useRouter

import { useGlobalStore } from '@/store/modules/global'

import { DICT_TYPE, getDictOptions } from '@/utils/dictionary'

const { params } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数

const message = useMessage() // 消息弹窗

const globalStore = useGlobalStore()

const { currentRoute, push } = useRouter()

const tagsViewStore = useTagsViewStore()

defineOptions({ name: 'PlanAssociated' })

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  title: '',
  nodeId: null
})

const checked = ref<any>([])
const currentPlanId = ref<any>(null)
const title = ref('')
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)
const queryFormRef = ref() // 搜索的表单
const statistics = ref<any>(null)

const handleGetData = async () => {
  if (!currentPlanId.value) {
    return
  }
  const data = await HTTP.getData(currentPlanId.value)
  if (!data) {
    tagsViewStore.delView(unref(currentRoute))
    push('/quality/plan')
    return
  }
  title.value = data.title
  statistics.value = data.statistics
  statistics.value.name = '执行进度'
}

const getList = async () => {
  loading.value = true
  try {
    queryParams.value.planId = currentPlanId.value
    const data = await HTTP.getPlanCase(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    toggleSelection()
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

const caseAssociated = ref()
const handleAssociCase = async () => {
  caseAssociated.value.open()
}

const caseViewer = ref()
const handleExecuteCase = async (id: number) => {
  caseViewer.value.open(id)
}
const handleExecuteFirstCase = async () => {
  const id = await HTTP.getFirstReviewCase({ planId: currentPlanId.value })
  caseViewer.value.open(id)
}

const handleNodeClick = async (row: any) => {
  queryParams.value.nodeId = row.id === 0 ? null : row.id
  handleQuery()
}

const handleSelectionChange = async (val: any[]) => {
  checked.value = val.map((item) => item.id)
}

const handleBatchUnlinkCase = async (ids: number[]) => {
  await message.delConfirm('是否确认取消关联选中的用例？')
  await HTTP.batchRemoveAssociCase(ids.join(','))
  await handleQuery()
}

const multipleTableRef = ref()
const toggleSelection = () => {
  multipleTableRef.value!.clearSelection()
  checked.value = []
}

const caseImports = ref()
const handleOpenImportCase = () => {
  caseImports.value.open(currentPlanId.value)
}

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => globalStore.getCurrentProject),
  () => {
    handleGetData()
  },
  { immediate: true, deep: true }
)

/** 初始化 **/
onMounted(async () => {
  if (params && params.planId) {
    currentPlanId.value = params.planId
    await handleGetData()
    await getList()
  }
})
</script>

<style scoped></style>
