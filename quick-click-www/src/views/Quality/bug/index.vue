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
          <el-form-item label="修复人" prop="fixer" v-if="activeName !== '4'">
            <el-select
              v-model="queryParams.fixer"
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
              <el-button link type="primary" @click="handleEditBug(scope.row.id)">
                {{ scope.row.title }}
              </el-button>
            </template>
          </el-table-column>
          <el-table-column align="center" label="严重程度" prop="severity" width="80">
            <template #default="scope">
              <ones-tag
                :type="DICT_TYPE.QUALITY_BUG_SEVERITY"
                :value="scope.row.severity"
                effect="dark"
              />
            </template>
          </el-table-column>
          <el-table-column align="center" label="优先级" prop="priority" width="80">
            <template #default="scope">
              <ones-tag
                :type="DICT_TYPE.QUALITY_TESTCASE_PRIORITY"
                :value="scope.row.priority"
                effect="dark"
              />
            </template>
          </el-table-column>
          <el-table-column align="center" label="状态" prop="status">
            <template #default="scope">
              <ones-tag :type="DICT_TYPE.QUALITY_BUG_STATUS" :value="scope.row.status" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="责任人" prop="supervisor">
            <template #default="scope">
              <user-tag :value="scope.row.supervisor" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="处理人" prop="handler">
            <template #default="scope">
              <user-tag :value="scope.row.handler" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="修复人" prop="fixer">
            <template #default="scope">
              <user-tag :value="scope.row.fixer" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="关闭人" prop="closer">
            <template #default="scope">
              <user-tag :value="scope.row.closer" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="创建人" prop="creator" width="100" />
          <el-table-column align="center" label="创建时间" prop="createTime" width="165" />
          <el-table-column align="center" label="修复时间" prop="fixTime" width="165" />
          <el-table-column align="center" label="关闭时间" prop="closeTime" width="165" />
          <el-table-column align="center" label="激活次数" prop="reopenedTimes" width="80" />

          <el-table-column align="center" fixed="right" label="操作" width="300">
            <template #default="{ row }">
              <el-tooltip content="确认" placement="top">
                <el-button
                  v-hasPermi="['quality:bug:confirm']"
                  circle
                  plain
                  :type="row.status === 'New' ? 'primary' : ''"
                  @click="handleEditBug(row.id)"
                  :disabled="row.status !== 'New'"
                >
                  <Icon icon="fa:bug" />
                </el-button>
              </el-tooltip>

              <el-tooltip content="修复" placement="top">
                <el-button
                  v-hasPermi="['quality:bug:fix']"
                  circle
                  plain
                  :type="['Opened', 'Reopened'].includes(row.status) ? 'primary' : ''"
                  @click="handleEditBug(row.id)"
                  :disabled="!['Opened', 'Reopened'].includes(row.status)"
                >
                  <Icon icon="ep:circle-check" />
                </el-button>
              </el-tooltip>

              <el-tooltip content="关闭" placement="top">
                <el-button
                  v-hasPermi="['quality:bug:close']"
                  circle
                  plain
                  :type="row.status === 'Fixed' ? 'primary' : ''"
                  @click="handleEditBug(row.id)"
                  :disabled="row.status !== 'Fixed'"
                >
                  <Icon icon="ep:switch-button" />
                </el-button>
              </el-tooltip>

              <el-tooltip content="激活" placement="top">
                <el-button
                  v-hasPermi="['quality:bug:reopen']"
                  circle
                  plain
                  :type="['Fixed', 'Rejected'].includes(row.status) ? 'primary' : ''"
                  @click="handleEditBug(row.id)"
                  :disabled="!['Fixed', 'Rejected'].includes(row.status)"
                >
                  <Icon icon="ep:timer" />
                </el-button>
              </el-tooltip>

              <el-tooltip content="编辑" placement="top">
                <el-button
                  v-if="row.status !== 'Closed'"
                  v-hasPermi="['quality:bug:update']"
                  circle
                  plain
                  type="primary"
                  @click="handleEditBug(row.id)"
                >
                  <Icon icon="ep:edit" />
                </el-button>
              </el-tooltip>
              <el-tooltip content="复制" placement="top">
                <el-button
                  v-if="row.status !== 'Closed'"
                  v-hasPermi="['quality:bug:add']"
                  circle
                  plain
                  type="primary"
                  @click="handleCopyBug(row.id)"
                >
                  <Icon icon="ep:document-copy" />
                </el-button>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
        <el-button
          v-hasPermi="['quality:bug:update']"
          class="mt-15px"
          plain
          type="primary"
          :disabled="checked.length < 1"
          @click="openBatchAssign"
        >
          <Icon class="mr-5px" icon="ep:user" />
          指派
        </el-button>
        <el-button
          v-hasPermi="['quality:bug:confirm']"
          class="mt-15px"
          plain
          type="primary"
          :disabled="checked.length < 1 || checked.find((item) => item.status !== 'New')"
          @click="handleBatchConfirm"
        >
          <Icon class="mr-5px" icon="ep:circle-check" />
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
</template>

<script lang="ts" setup>
import { DefaultNodeTree } from '@/views/components/node'
import BatchAssign from './BatchAssign.vue'

import { handleTree } from '@/utils/tree'
import download from '@/utils/download'
import { DICT_TYPE } from '@/utils/dictionary'

import * as Node from '@/api/project/node'

import * as HTTP from '@/api/quality/bug'

import * as User from '@/api/project/member'

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

const activeName = ref('0')

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
const handleCopyBug = async (id: number) => {
  await push({ path: '/quality/bug/add', query: { from: id } })
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
  const data = await HTTP.download(queryParams.value)
  download.excel(data, '缺陷列表.xlsx')
}

/** 获得模块树 */
const getTree = async () => {
  modules.value = []
  const data = await Node.getList({ projectId: queryParams.value.projectId })
  modules.value = handleTree(data)
}
const tabChange = async () => {
  queryParams.value.tab = activeName
  checked.value = []
  list.value = []
  total.value = 0
  await handleQuery()
}

const getUserList = async () => {
  // 加载用户列表
  userList.value = await User.getSimple(queryParams.value.projectId)
}

const batchAssign = ref()
const openBatchAssign = async () => {
  batchAssign.value.open(checked.value.map((item) => item.id))
}

const handleBatchConfirm = async () => {
  await message.confirm('确定批量确认缺陷?')
  await HTTP.confirm(checked.value.map((item) => item.id))
  message.alertSuccess('操作成功')
  getList()
}

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => globalStore.getCurrentProject),
  () => {
    queryParams.value.projectId = globalStore.getCurrentProject
    getList()
    getTree()
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
