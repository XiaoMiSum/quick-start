<template>
  <ContentWrap :statistics="statistics" :title="'测试评审：' + title">
    <el-row :gutter="5">
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
                v-hasPermi="['review:case:add']"
                plain
                type="primary"
                @click="handleAssociCase()"
              >
                <Icon class="mr-5px" icon="ep:link" />
                关联用例
              </el-button>
              <el-button
                v-hasPermi="['review:case:remove']"
                :disabled="!checked || checked.length < 1"
                plain
                type="danger"
                @click="handleBatchUnlinkCase"
              >
                <Icon class="mr-5px" icon="fa-solid:unlink" />
                取消关联
              </el-button>
              <el-button
                v-hasPermi="['review:case:execute']"
                plain
                type="primary"
                @click="handleReviewFirstCase()"
              >
                <Icon class="mr-5px" icon="ep:caret-right" />
                开始评审
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
            <el-table-column label="用例名称" prop="name" show-overflow-tooltip width="200" />
            <el-table-column label="所属模块" prop="path" show-overflow-tooltip width="200" />
            <el-table-column align="center" label="用例等级" prop="level">
              <template #default="scope"> </template>
            </el-table-column>
            <el-table-column align="center" label="标签" prop="tags" show-overflow-tooltip>
              <template #default="scope">
                <el-tag v-for="item in scope.row.tags" :key="item"> {{ item }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              label="负责人"
              prop="maintainer"
              show-overflow-tooltip
            />
            <el-table-column align="center" label="评审结果" prop="reviewResult">
              <template #default="scope"> </template>
            </el-table-column>
            <el-table-column align="center" label="评审人" prop="reviewer" show-overflow-tooltip />
            <el-table-column
              :formatter="dateFormatter"
              align="center"
              label="评审时间"
              prop="reviewTime"
              width="170"
            />
            <el-table-column :width="150" align="center" fixed="right" label="操作">
              <template #default="scope">
                <el-tooltip content="评审" placement="top">
                  <el-button
                    v-hasPermi="['review:case:execute']"
                    circle
                    plain
                    type="primary"
                    @click="handleReviewCase(scope.row)"
                  >
                    <Icon icon="ep:checked" />
                  </el-button>
                </el-tooltip>
                <el-tooltip content="移除" placement="top">
                  <el-button
                    v-hasPermi="['review:case:remove']"
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
    :data-id="currentReviewId"
    :enums="REVIEW_TESTCASE_STATUS"
    source="review"
    @close="handleQuery"
  />

  <CaseViewer ref="caseViewer" :enums="REVIEW_TESTCASE_STATUS" @close="handleQuery" />
</template>

<script lang="ts" setup>
/** 初始化 **/
onMounted(async () => {
  appStore.setProjectPick(true)
  if (params && params.reviewId) {
    currentReviewId.value = params.reviewId
    await handleGetData()
    await getList()
  }
})

import { DefaultNodeTree } from '@/views/components/node'
import { CaseAssociated } from '../components'
import CaseViewer from './CaseViewer.vue'

import { dateFormatter } from '@/utils/formatTime'

import { CASE_LEVEL_ENUMS, REVIEW_TESTCASE_STATUS } from '@/utils/enums'

import * as HTTP from '@/api/track/review'

import { useTagsViewStore } from '@/store/modules/tagsView'
import { useRoute } from 'vue-router' //1.先在需要跳转的页面引入useRouter
import { useAppStore } from '@/store/modules/app'
import { useUserStore } from '@/store/modules/user'

const userStore = useUserStore()
const appStore = useAppStore()

const { params } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数
const { currentRoute, push } = useRouter()
const tagsViewStore = useTagsViewStore()

const message = useMessage() // 消息弹窗

defineOptions({ name: 'ReviewAssociated' })

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  caseName: '',
  nodeId: null
})

const checked = ref<any>([])

const currentReviewId = ref<any>(null)
const title = ref('')
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)
const statistics = ref<any>(null)
const queryFormRef = ref() // 搜索的表单

const getList = async () => {
  loading.value = true
  try {
    queryParams.value.reviewId = currentReviewId.value
    const data = await HTTP.getReviewCase(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleGetData = async () => {
  if (!currentReviewId.value) {
    return
  }
  const data = await HTTP.getData(currentReviewId.value)
  if (!data) {
    tagsViewStore.delView(unref(currentRoute))
    push('/track/review')
    return
  }
  title.value = data.name
  statistics.value = data.statistics
  statistics.value.name = '评审进度'
}

const handleQuery = async () => {
  queryParams.value.pageNo = 1
  await getList()
}

const resetQuery = async () => {
  queryFormRef.value.resetFields()
  await handleQuery()
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
const handleReviewCase = async (data?: any) => {
  caseViewer.value.open({ id: data.id, reviewId: data.reviewId })
}

const handleReviewFirstCase = async () => {
  const id = await HTTP.getFirstReviewCase({ reviewId: currentReviewId.value })
  caseViewer.value.open({ id, reviewId: currentReviewId.value })
}

const handleNodeClick = async (row: any) => {
  queryParams.value.nodeId = row.id === 0 ? null : row.id
  await handleQuery()
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

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => userStore.getProject),
  () => {
    handleGetData()
  },
  { immediate: true, deep: true }
)
</script>

<style scoped></style>
