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
          <el-tab-pane label="测试用例" name="Testcase" />
          <el-tab-pane label="回收站" name="Recycle" />
        </el-tabs>
        <!-- 搜索工作栏 -->
        <el-form ref="queryFormRef" :inline="true" :model="queryParams">
          <el-form-item label="" prop="title">
            <el-input
              v-model="queryParams.title"
              class="!w-240px"
              clearable
              placeholder="用例名称"
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item label="" prop="priority">
            <el-select
              v-model="queryParams.priority"
              clearable
              placeholder="优先级"
              style="width: 100%"
            >
              <el-option
                v-for="item in getDictOptions(DICT_TYPE.QUALITY_TESTCASE_PRIORITY)"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="" prop="supervisor">
            <el-select
              v-model="queryParams.supervisor"
              clearable
              placeholder="责任人"
              style="width: 100%"
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
        <el-row v-if="activeName === 'Testcase'" :gutter="10">
          <el-col :span="1.5">
            <el-button plain type="primary" @click="handleAddCase">
              <Icon class="mr-5px" icon="ep:plus" />
              新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              :disabled="!checked || checked.length < 1"
              plain
              type="danger"
              @click="handleBatchDeleteCase"
            >
              <Icon class="mr-5px" icon="ep:delete" />
              批量删除
            </el-button>
          </el-col>
          <el-col :span="1.5" @click="openImports">
            <el-button plain>
              <Icon class="mr-5px" icon="ep:upload" />
              导入
            </el-button>
          </el-col>
          <el-col :span="1.5" @click="handleDownload">
            <el-button plain>
              <Icon class="mr-5px" icon="ep:download" />
              导出
            </el-button>
          </el-col>
        </el-row>
        <el-row v-else :gutter="10">
          <el-col :span="1.5">
            <el-button
              :disabled="!checked || checked.length < 1"
              plain
              type="success"
              @click="handleBatchRecoverRecycleTestcase"
            >
              <Icon class="mr-5px" icon="ep:document-add" />
              批量恢复
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              :disabled="!checked || checked.length < 1"
              plain
              type="danger"
              @click="handleBatchRemoveRecycleTestcase"
            >
              <Icon class="mr-5px" icon="ep:delete" />
              彻底删除
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
          <el-table-column label="用例名称" prop="title" show-overflow-tooltip max-width="500">
            <template #default="scope">
              <el-button link type="primary" @click="handleEditCase(scope.row.id)">
                {{ scope.row.title }}
              </el-button>
            </template>
          </el-table-column>
          <el-table-column label="所属模块" prop="path" show-overflow-tooltip width="200" />
          <el-table-column align="center" label="标签" prop="tags" show-overflow-tooltip>
            <template #default="scope">
              <el-tag v-for="(item, index) in scope.row.tags" :key="index" class="ml-2">
                {{ item }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" label="用例等级" prop="priority" width="80">
            <template #default="scope">
              <ones-tag :type="DICT_TYPE.QUALITY_TESTCASE_PRIORITY" :value="scope.row.priority" />
            </template>
          </el-table-column>
          <el-table-column
            v-if="activeName === 'Testcase'"
            align="center"
            label="最后一次评审"
            prop="lastReviewResult"
          >
            <template #default="scope">
              <ones-tag :type="DICT_TYPE.QUALITY_TEST_STATUS" :value="scope.row.lastReviewResult" />
            </template>
          </el-table-column>
          <el-table-column
            v-if="activeName === 'Testcase'"
            align="center"
            label="责任人"
            prop="supervisor"
          >
            <template #default="scope">
              <el-select
                :disabled="!supervisorEnable"
                v-model="scope.row.supervisor"
                style="width: 100%"
              >
                <el-option
                  v-for="item in userList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column
            v-if="activeName === 'Testcase'"
            :formatter="dateFormatter"
            align="center"
            label="更新时间"
            prop="updateTime"
            width="165"
          />
          <el-table-column
            v-if="activeName === 'Testcase'"
            align="center"
            fixed="right"
            label="操作"
            width="150"
          >
            <template #default="scope">
              <el-tooltip content="编辑" placement="top">
                <el-button circle plain type="primary" @click="handleEditCase(scope.row.id)">
                  <Icon icon="ep:edit" />
                </el-button>
              </el-tooltip>
              <el-tooltip content="复制" placement="top">
                <el-button circle plain type="primary" @click="handleCopyCase(scope.row.id)">
                  <Icon icon="ep:document-copy" />
                </el-button>
              </el-tooltip>
              <el-tooltip content="删除" placement="top">
                <el-button circle plain type="danger" @click="handleDelete(scope.row.id)">
                  <Icon icon="ep:delete" />
                </el-button>
              </el-tooltip>
            </template>
          </el-table-column>

          <el-table-column
            v-if="activeName === 'Recycle'"
            align="center"
            label="删除人"
            prop="creator"
            show-overflow-tooltip
            width="100"
          />
          <el-table-column
            v-if="activeName === 'Recycle'"
            :formatter="dateFormatter"
            align="center"
            label="删除时间"
            prop="createTime"
            width="165"
          />
        </el-table>
        <el-button
          class="mt-15px"
          plain
          type="primary"
          :disabled="checked.length < 1"
          @click="openBatchUpdate"
        >
          <Icon class="mr-5px" icon="ep:edit" />
          批量编辑
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

  <CaseImports
    ref="caseImports"
    @close="getList"
    @download="handleDownload"
    @upload="handleBatchImports"
  />

  <CaseBatchEditor ref="caseBatchEditor" :nodes="modules" :users="userList" @close="getList" />
</template>

<script lang="ts" setup>
import { DefaultNodeTree } from '@/views/components/node'
import CaseImports from './CaseImports.vue'
import CaseBatchEditor from './CaseBatchEditor.vue'

import { dateFormatter } from '@/utils/formatTime'
import { handleTree } from '@/utils/tree'
import download from '@/utils/download'
import { DICT_TYPE, getDictOptions } from '@/utils/dictionary'

import * as Node from '@/api/project/node'
import * as HTTP from '@/api/quality/testcase'
import * as User from '@/api/project/member'

import { useGlobalStore } from '@/store/modules/global'

const supervisorEnable = ref(false)

const { push } = useRouter() // 路由
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const globalStore = useGlobalStore()

defineOptions({ name: 'ProjectManager' })

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  title: '',
  nodeId: null
})

const activeName = ref('Testcase')

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
    queryParams.value.trash = activeName.value === 'Testcase' ? 0 : 1
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

const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await HTTP.removeData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

const handleAddCase = async () => {
  await push('/quality/case/add')
}

const handleEditCase = async (id: number) => {
  await push('/quality/case/edit/' + id)
}
const handleCopyCase = async (id: number) => {
  await push({ path: '/quality/case/add', query: { from: id } })
}

const handleNodeClick = async (row: any) => {
  queryParams.value.nodeId = row.id === '0' ? null : row.id
  await handleQuery()
}

const caseBatchEditor = ref()
const openBatchUpdate = async () => {
  caseBatchEditor.value.open(checked)
}

const handleSelectionChange = async (val: any[]) => {
  checked.value = val.map((item) => item.id)
}

const handleBatchDeleteCase = async () => {
  await HTTP.batchRemove(checked.value)
  await handleQuery()
}

const multipleTableRef = ref()
const toggleSelection = () => {
  multipleTableRef.value!.clearSelection()
  checked.value = []
}

const handleDownload = async (template: boolean = false) => {
  const data = await HTTP.download(template)
  download.excel(data, '测试用例.xlsx')
}

const caseImports = ref()
const handleBatchImports = async (data: any) => {
  await HTTP.batchImports(data)
}

const openImports = () => {
  caseImports.value.open()
}

/** 获得模块树 */
const getTree = async () => {
  modules.value = []
  const data = await Node.getList({ projectId: queryParams.value.projectId })
  modules.value = handleTree(data)
}

const getTags = async () => {}

const tabChange = async () => {
  checked.value = []
  list.value = []
  total.value = 0
  queryParams.value.nodeId = null
  await handleQuery()
}

const handleBatchRemoveRecycleTestcase = async () => {
  await HTTP.batchRemoveRecycleTestcase(checked.value)
  await handleQuery()
}

const handleBatchRecoverRecycleTestcase = async () => {
  await HTTP.recoverTestcase(checked.value)
  await handleQuery()
}

const getUserList = async () => {
  // 加载用户列表
  userList.value = await User.getSimple(queryParams.value.projectId)
}

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => globalStore.getCurrentProject),
  () => {
    queryParams.value.projectId = globalStore.getCurrentProject
    getList()
    getTree()
    getTags()
  },
  { immediate: true, deep: true }
)

/** 初始化 **/
onMounted(async () => {
  await getList()
  await getTree()
  await getTags()
  await getUserList()
})
</script>
