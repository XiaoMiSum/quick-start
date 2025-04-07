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
          <el-tab-pane label="回收站" name="Trash" />
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
              :style="{ width: '240px' }"
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
              filterable
              placeholder="责任人"
              :style="{ width: '240px' }"
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
            <el-button
              v-hasPermi="['quality:case:add']"
              plain
              type="primary"
              @click="handleAddCase"
            >
              <Icon class="mr-5px" icon="ep:plus" />
              新增
            </el-button>
          </el-col>
          <el-col :span="1.5" @click="openImports">
            <el-button plain v-hasPermi="['quality:case:add']">
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
              v-hasPermi="['quality:case:update']"
              @click="handleBatchRecoverTestcase"
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
              v-hasPermi="['quality:case:remove']"
              @click="handleBatchRemoveTestcase"
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
          <el-table-column
            label="用例名称"
            prop="title"
            show-overflow-tooltip
            min-width="350"
            sortable
          >
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
          <el-table-column align="center" label="用例等级" prop="priority" width="120" sortable>
            <template #default="scope">
              <ones-tag
                :type="DICT_TYPE.QUALITY_TESTCASE_PRIORITY"
                :value="scope.row.priority"
                effect="dark"
              />
            </template>
          </el-table-column>
          <el-table-column
            v-if="activeName === 'Testcase'"
            align="center"
            label="最后评审"
            prop="lastReviewResult"
            sortable
            width="120"
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
            width="120"
            sortable
          >
            <template #default="scope">
              <user-tag text :value="scope.row.supervisor" />
            </template>
          </el-table-column>

          <el-table-column align="center" label="前端开发" width="120" sortable>
            <template #default="scope">
              <user-tag text :value="scope.row.frontendDeveloper" />
            </template>
          </el-table-column>

          <el-table-column align="center" label="后端开发" width="120" sortable>
            <template #default="scope">
              <user-tag text :value="scope.row.backendDeveloper" />
            </template>
          </el-table-column>

          <el-table-column
            v-if="activeName === 'Trash'"
            align="center"
            label="删除人"
            prop="updater"
            show-overflow-tooltip
            width="100"
          />

          <el-table-column
            align="center"
            :label="(activeName === 'Testcase' ? '更新' : '删除') + '时间'"
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
              <el-button
                v-hasPermi="['quality:case:update']"
                circle
                text
                type="primary"
                @click="handleEditCase(scope.row.id)"
              >
                编辑
              </el-button>

              <el-button
                v-hasPermi="['quality:case:add']"
                circle
                text
                type="primary"
                @click="handleCopyCase(scope.row.id)"
              >
                复制
              </el-button>

              <el-button
                v-hasPermi="['quality:case:remove']"
                circle
                text
                type="danger"
                @click="handleDelete([scope.row.id])"
              >
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-button
          v-if="activeName === 'Testcase'"
          class="mt-15px"
          plain
          type="primary"
          :disabled="checked.length < 1"
          @click="openBatchUpdate"
        >
          <Icon class="mr-5px" icon="ep:edit" />
          批量编辑
        </el-button>
        <el-button
          v-if="activeName === 'Testcase'"
          class="mt-15px"
          :disabled="!checked || checked.length < 1"
          plain
          type="danger"
          @click="handleDelete(checked)"
        >
          <Icon class="mr-5px" icon="ep:delete" />
          批量删除
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
import download from '@/utils/download'
import { DICT_TYPE, getDictOptions } from '@/utils/dictionary'

import * as HTTP from '@/api/quality/testcase'

import { useGlobalStore } from '@/store/modules/global'

const { push } = useRouter() // 路由

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const globalStore = useGlobalStore()

defineOptions({ name: 'CaseList' })

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
    queryParams.value.projectId = globalStore.getCurrentProject
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

const handleDelete = async (ids: string[]) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    const params = {
      projectId: globalStore.getCurrentProject,
      idListString: ids.join(',')
    }
    await HTTP.batchRemove(params)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

const handleAddCase = async () => {
  await push('/quality/test-case/add')
}

const handleEditCase = async (id: string) => {
  await push('/quality/test-case/edit/' + id)
}
const handleCopyCase = async (id: string) => {
  await push({ path: '/quality/test-case/add', query: { from: id } })
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

const handleBatchRemoveTestcase = async () => {
  // 删除的二次确认
  await message.delConfirm()
  await HTTP.batchRemoveTestcase({
    projectId: globalStore.getCurrentProject,
    idListString: checked.value.join(',')
  })
  await handleQuery()
}

const handleBatchRecoverTestcase = async () => {
  // 删除的二次确认
  await message.confirm('确认还原所选用例？')
  await HTTP.recoverTestcase({ projectId: globalStore.getCurrentProject, ids: checked.value })
  await handleQuery()
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
    userList.value = globalStore.getUsers
  },
  { immediate: true, deep: true }
)

/** 初始化 **/
onMounted(async () => {
  await getList()
  await getTree()
})
</script>
