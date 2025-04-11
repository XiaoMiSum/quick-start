<template>
  <el-row :gutter="5">
    <!-- 左侧部门树 -->
    <el-col :span="5" :xs="24">
      <ContentWrap class="h-1/1">
        <DefaultNodeTree readonly @node-click="handleNodeClick" />
      </ContentWrap>
    </el-col>

    <el-col :span="19" :xs="24">
      <ContentWrap>
        <el-tabs v-model="activeName" @tab-change="tabChange">
          <el-tab-pane label="全部" name="All" />
          <el-tab-pane label="未关闭" name="UnClosed" />
          <el-tab-pane label="由我创建" name="IsCreator" />
          <el-tab-pane label="指派给我" name="ToMe" />
          <el-tab-pane label="由我解决" name="IsFixer" />
          <el-tab-pane label="待关闭" name="Fixed" />
          <el-tab-pane label="未解决" name="UnFixed" />
          <el-tab-pane label="未确认" name="New" />
          <el-tab-pane label="7天未处理" name="LongTime" />
        </el-tabs>
        <!-- 搜索工作栏 -->
        <el-form ref="queryFormRef" :inline="true" :model="queryParams">
          <el-form-item label="" prop="title">
            <el-input
              v-model="queryParams.title"
              class="!w-240px"
              clearable
              placeholder="标题"
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="责任人" prop="supervisor">
            <el-select
              v-model="queryParams.supervisor"
              clearable
              filterable
              placeholder="责任人"
              style="width: 240px"
            >
              <el-option
                v-for="item in userList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="修复人" prop="fixer">
            <el-select
              v-model="queryParams.fixer"
              filterable
              clearable
              placeholder="修复人"
              style="width: 240px"
            >
              <el-option
                v-for="item in userList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
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
            <el-button plain type="primary" v-hasPermi="['quality:bug:add']" @click="handleAddBug">
              <Icon class="mr-5px" icon="ep:plus" />
              新增
            </el-button>
          </el-col>
          <el-col :span="1.5" @click="handleDownload">
            <el-button plain>
              <Icon class="mr-5px" icon="ep:download" />
              导出
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
          <el-table-column
            label="标题"
            prop="title"
            show-overflow-tooltip
            max-width="500"
            min-width="300"
          >
            <template #default="scope">
              <el-button link type="primary" @click="handleViewBug(scope.row.id)">
                {{ scope.row.title }}
              </el-button>
            </template>
          </el-table-column>
          <el-table-column align="center" label="严重程度" prop="severity" width="80">
            <template #default="scope">
              <ones-tag :type="DICT_TYPE.QUALITY_BUG_SEVERITY" :value="scope.row.severity" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="优先级" prop="priority" width="80">
            <template #default="scope">
              <ones-tag :type="DICT_TYPE.QUALITY_TESTCASE_PRIORITY" :value="scope.row.priority" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="状态" prop="status">
            <template #default="scope">
              <ones-tag :type="DICT_TYPE.QUALITY_BUG_STATUS" :value="scope.row.status" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="责任人" prop="supervisor">
            <template #default="scope">
              <user-tag text :value="scope.row.supervisor" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="处理人" prop="handler">
            <template #default="scope">
              <user-tag text :value="scope.row.handler" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="修复人" prop="fixer">
            <template #default="scope">
              <user-tag text :value="scope.row.fixer" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="关闭人" prop="closer">
            <template #default="scope">
              <user-tag text :value="scope.row.closer" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="创建人" prop="closer">
            <template #default="scope">
              <user-tag text :value="scope.row.creatorId" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="创建时间" prop="createTime" width="165" />
          <el-table-column align="center" label="修复时间" prop="fixedTime" width="165" />
          <el-table-column align="center" label="关闭时间" prop="closedTime" width="165" />
          <el-table-column align="center" label="激活次数" prop="reopenedTimes" width="80">
            <template #default="{ row }">
              <el-text :type="row.reopenedTimes > 0 ? 'danger' : 'info'">
                {{ row.reopenedTimes }}</el-text
              >
            </template>
          </el-table-column>

          <el-table-column align="center" fixed="right" label="操作" width="320">
            <template #default="{ row }">
              <el-button
                v-hasPermi="['quality:bug:confirm']"
                circle
                text
                :type="row.status === 'New' ? 'primary' : ''"
                @click="openBugConfirmer(row)"
                :disabled="row.status !== 'New'"
              >
                确认
              </el-button>

              <el-button
                v-hasPermi="['quality:bug:confirm']"
                circle
                text
                :type="row.status === 'New' ? 'primary' : ''"
                @click="openBugRejecter(row)"
                :disabled="row.status !== 'New'"
              >
                拒绝
              </el-button>

              <el-button
                v-hasPermi="['quality:bug:fix']"
                text
                circle
                :type="['Opened', 'Reopened'].includes(row.status) ? 'primary' : ''"
                @click="openBugFixer(row)"
                :disabled="!['Opened', 'Reopened'].includes(row.status)"
              >
                修复
              </el-button>

              <el-button
                v-hasPermi="['quality:bug:close']"
                text
                circle
                :type="['Fixed', 'Rejected'].includes(row.status) ? 'primary' : ''"
                @click="openBugCloser(row)"
                :disabled="!['Fixed', 'Rejected'].includes(row.status)"
              >
                关闭
              </el-button>

              <el-button
                v-hasPermi="['quality:bug:reopen']"
                text
                circle
                :type="['Fixed', 'Rejected', 'Closed'].includes(row.status) ? 'primary' : ''"
                @click="openBugOpener(row)"
                :disabled="!['Fixed', 'Rejected', 'Closed'].includes(row.status)"
              >
                激活
              </el-button>

              <el-button
                v-if="row.status !== 'Closed'"
                v-hasPermi="['quality:bug:reopen']"
                text
                circle
                type="primary"
                @click="handleEditBug(row.id)"
              >
                编辑
              </el-button>

              <el-button
                text
                circle
                type="primary"
                v-if="row.status !== 'Closed'"
                v-hasPermi="['quality:bug:reopen']"
                @click="handleCopyBug(row.id)"
              >
                复制
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button
          v-hasPermi="['quality:bug:update']"
          class="mt-15px"
          plain
          type="primary"
          :disabled="checked.length < 1 || checked.find((item) => item.status === 'Closed')"
          @click="openBatchAssign"
        >
          <Icon class="mr-5px" icon="ep:pointer" />
          指派
        </el-button>
        <el-button
          v-hasPermi="['quality:bug:confirm']"
          class="mt-15px"
          plain
          type="primary"
          :disabled="checked.length < 1 || checked.find((item) => item.status !== 'New')"
          @click="handleBatchConfirm(checked.value.map((item) => item.id))"
        >
          <Icon class="mr-5px" icon="fa:bug" />
          确认
        </el-button>
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

  <BatchAssign ref="batchAssign" :users="userList" @success="getList" />
  <BugFixer ref="bugFixer" :users="userList" @success="getList" />
  <BugCloser ref="bugCloser" :users="userList" @success="getList" />
  <BugOpener ref="bugOpener" :users="userList" @success="getList" />
  <BugConfirmer ref="bugConfirmer" :users="userList" @success="getList" />
  <BugRejecter ref="bugRejecter" :users="userList" @success="getList" />
</template>

<script lang="ts" setup>
import { DefaultNodeTree } from '@/views/components/node'

import BatchAssign from './BatchAssign.vue'
import BugFixer from './BugFixer.vue'
import BugCloser from './BugCloser.vue'
import BugOpener from './BugOpener.vue'
import BugConfirmer from './BugConfirmer.vue'
import BugRejecter from './BugRejecter.vue'

import download from '@/utils/download'
import { DICT_TYPE } from '@/utils/dictionary'

import * as HTTP from '@/api/quality/bug'

import { useGlobalStore } from '@/store/modules/global'

const message = useMessage() // 消息弹窗
const { push } = useRouter() // 路由

const globalStore = useGlobalStore()

defineOptions({ name: 'BugManagement' })

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  title: '',
  nodeId: null
})

const activeName = ref('ToMe')

const loading = ref(false)
const list = ref<any>([])
const total = ref(0)
const modules = ref<any>([])
const checked = ref<any>([])
const userList = ref<any>([])

const queryFormRef = ref() // 搜索的表单

const getList = async () => {
  loading.value = true
  try {
    queryParams.value.projectId = globalStore.getCurrentProject
    queryParams.value.tab = activeName
    const data = await HTTP.getPage(queryParams.value)
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

const handleAddBug = async () => {
  await push('/quality/bug/add')
}

const handleEditBug = async (id: number) => {
  await push('/quality/bug/edit/' + id)
}

const handleViewBug = async (id: number) => {
  await push('/quality/bug/view/' + id)
}

const handleCopyBug = async (id: number) => {
  await push({ path: '/quality/bug/add', query: { from: 'bug', originalId: id } })
}

const handleNodeClick = async (row: any) => {
  queryParams.value.nodeId = row.id === '0' ? null : row.id
  await handleQuery()
}

const handleSelectionChange = async (val: any[]) => {
  checked.value = val
}

const multipleTableRef = ref()
const toggleSelection = () => {
  multipleTableRef.value!.clearSelection()
  checked.value = []
}

const handleDownload = async () => {
  message.warning('暂未实现')
  /*  
  const data = await HTTP.download(queryParams.value)
  download.excel(data, '缺陷列表.xlsx')
  */
}

/** 获得模块树 */
const getTree = async () => {
  await globalStore.setNodes()
  modules.value = []
  modules.value = await globalStore.getNodes
}
const tabChange = async () => {
  checked.value = []
  list.value = []
  total.value = 0
  await handleQuery()
}

const getUserList = async () => {
  // 加载用户列表
  userList.value = await globalStore.getUsers
}

const batchAssign = ref()
const openBatchAssign = async () => {
  batchAssign.value.open(checked.value.map((item) => item.id))
}

const bugFixer = ref()
const openBugFixer = async (data: any) => {
  bugFixer.value.open(data)
}

const bugCloser = ref()
const openBugCloser = async (data: any) => {
  bugCloser.value.open(data)
}

const bugOpener = ref()
const openBugOpener = async (data: any) => {
  bugOpener.value.open(data)
}

const bugConfirmer = ref()
const openBugConfirmer = async (data: any) => {
  bugConfirmer.value.open(data)
}

const bugRejecter = ref()
const openBugRejecter = async (data: any) => {
  bugRejecter.value.open(data)
}

const handleBatchConfirm = async (ids: any[]) => {
  await message.confirm('确定要确认所选缺陷?')
  await HTTP.batchConfirm(ids)
  message.success('操作成功')
  getList()
}

const resetQuery2 = async () => {
  queryParams.value = { pageNo: 1, pageSize: 10 }
  getList()
}

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => globalStore.getCurrentProject),
  async () => {
    await resetQuery2()
    await getTree()
    await getUserList()
  },
  { immediate: true, deep: true }
)

/** 初始化 **/
onMounted(async () => {
  await getList()
  await getTree()
  await getUserList()
})
</script>
