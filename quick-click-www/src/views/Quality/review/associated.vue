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
                @click="handleBatchUnlinkCase(checked)"
              >
                <Icon class="mr-5px" icon="fa-solid:unlink" />
                取消关联
              </el-button>
              <el-button
                v-hasPermi="['review:case:execute']"
                plain
                type="success"
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
            <el-table-column label="用例名称" prop="title" show-overflow-tooltip width="200">
              <template #default="scope">
                <el-button link type="primary" @click="handleReviewCase(scope.row.id)">
                  {{ scope.row.title }}
                </el-button>
              </template>
            </el-table-column>
            <el-table-column label="所属模块" prop="path" show-overflow-tooltip width="200" />
            <el-table-column align="center" label="用例等级" prop="priority" width="80">
              <template #default="scope">
                <ones-tag
                  :type="DICT_TYPE.QUALITY_TESTCASE_PRIORITY"
                  :value="scope.row.priority"
                  effect="dark"
                />
              </template>
            </el-table-column>
            <el-table-column align="center" label="标签" prop="tags" show-overflow-tooltip>
              <template #default="scope">
                <el-tag v-for="(item, index) in scope.row.tags" :key="index" class="ml-2">
                  {{ item }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" label="评审结果" prop="result">
              <template #default="scope">
                <ones-tag :type="DICT_TYPE.QUALITY_TEST_STATUS" :value="scope.row.result" />
              </template>
            </el-table-column>
            <el-table-column align="center" label="评审人" prop="reviewer" show-overflow-tooltip>
              <template #default="scope">
                <user-tag :value="scope.row.reviewer" />
              </template>
            </el-table-column>
            <el-table-column align="center" label="评审时间" prop="reviewTime" width="170" />
            <el-table-column align="center" label="前端开发" width="80">
              <template #default="scope">
                <user-tag text :value="scope.row.frontendDeveloper" />
              </template>
            </el-table-column>

            <el-table-column align="center" label="后端开发" width="80">
              <template #default="scope">
                <user-tag text :value="scope.row.backendDeveloper" />
              </template>
            </el-table-column>
            <el-table-column :width="150" align="center" fixed="right" label="操作">
              <template #default="scope">
                <el-button
                  v-hasPermi="['review:case:execute']"
                  circle
                  plain
                  type="success"
                  @click="handleReviewCase(scope.row.id)"
                >
                  <Icon icon="ep:caret-right" />
                </el-button>

                <el-button
                  v-hasPermi="['review:case:remove']"
                  circle
                  plain
                  type="danger"
                  @click="handleBatchUnlinkCase([scope.row.id])"
                >
                  <Icon icon="fa-solid:unlink" />
                </el-button>
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
    :project-id="globalStore.getCurrentProject"
    :enums="getDictOptions(DICT_TYPE.QUALITY_TEST_STATUS)"
    source="review"
    @close="handleQuery"
  />

  <CaseViewer
    ref="caseViewer"
    :enums="getDictOptions(DICT_TYPE.QUALITY_TEST_STATUS)"
    @close="handleQuery"
  />
</template>

<script lang="ts" setup>
import { DefaultNodeTree } from '@/views/components/node'
import { CaseAssociated } from '../components'
import CaseViewer from './CaseViewer.vue'

import { DICT_TYPE, getDictOptions } from '@/utils/dictionary'

import * as HTTP from '@/api/quality/review'

import { useTagsViewStore } from '@/store/modules/tagsView'
import { useRoute } from 'vue-router' //1.先在需要跳转的页面引入useRouter
import { useGlobalStore } from '@/store/modules/global'

const globalStore = useGlobalStore()

const { params } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数
const { currentRoute, push } = useRouter()
const tagsViewStore = useTagsViewStore()

const message = useMessage() // 消息弹窗

defineOptions({ name: 'ReviewAssociated' })

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  title: '',
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
    toggleSelection()
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
    push('/quality/review')
    return
  }
  title.value = data.title
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

const caseAssociated = ref()
const handleAssociCase = async () => {
  caseAssociated.value.open()
}

const caseViewer = ref()
const handleReviewCase = async (id: number) => {
  caseViewer.value.open(id)
}

const handleReviewFirstCase = async () => {
  const id = await HTTP.getFirstReviewCase({ reviewId: currentReviewId.value })
  caseViewer.value.open(id)
}

const handleNodeClick = async (row: any) => {
  queryParams.value.nodeId = row.id === 0 ? null : row.id
  await handleQuery()
}

const handleSelectionChange = async (val: any[]) => {
  checked.value = val.map((item) => item.id)
}

const handleBatchUnlinkCase = async (ids: number[]) => {
  await message.delConfirm('是否确认取消关联所选的用例？')
  await HTTP.batchRemoveAssociCase(ids.join(','))
  await handleQuery()
}

const multipleTableRef = ref()
const toggleSelection = () => {
  multipleTableRef.value!.clearSelection()
  checked.value = []
}

const pageInit = ref(false)
/** 监听当前项目变化，返回测试评审列表 */
watch(
  computed(() => globalStore.getCurrentProject),
  async () => {
    if (pageInit.value) {
      tagsViewStore.delView(unref(currentRoute))
      push('/quality/review')
    }
    pageInit.value = true
  },
  { immediate: true, deep: true }
)

/** 初始化 **/
onMounted(async () => {
  if (params && params.reviewId) {
    currentReviewId.value = params.reviewId
    await handleGetData()
    await getList()
  }
})
</script>

<style scoped></style>
