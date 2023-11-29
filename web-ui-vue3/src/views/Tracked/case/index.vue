<template>
  <el-row :gutter="5">
    <!-- 左侧部门树 -->
    <el-col :span="5" :xs="24">
      <ContentWrap class="h-1/1">
        <ModuleTree @node-click="handleNodeClick" />
      </ContentWrap>
    </el-col>
    <el-col :span="19" :xs="24">
      <ContentWrap>
        <!-- 搜索工作栏 -->
        <el-form ref="queryFormRef" :inline="true" :model="queryParams">
          <el-form-item label="" prop="name">
            <el-input
              v-model="queryParams.name"
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
          <el-table-column align="center" label="编号" prop="id" width="55" />
          <el-table-column label="用例名称" prop="name" show-overflow-tooltip width="200">
            <template #default="scope">
              <el-button link type="primary" @click="handleEditCase(scope.row.id)">
                {{ scope.row.name }}
              </el-button>
            </template>
          </el-table-column>
          <el-table-column label="所属模块" prop="path" show-overflow-tooltip width="200" />
          <el-table-column align="center" label="用例等级" prop="level">
            <template #default="scope">
              <EnumTag :enums="CASE_LEVEL_ENUMS" :value="scope.row.level" />
            </template>
          </el-table-column>
          <el-table-column align="center" label="标签" prop="tags" show-overflow-tooltip>
            <template #default="scope">
              <el-tag v-for="(item, index) in scope.row.tags" :key="index" class="ml-2">
                {{ item }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column align="center" label="评审结果" prop="reviewed">
            <template #default="scope">
              <EnumTag :enums="RESULT_ENUMS" :value="scope.row.reviewed" />
            </template>
          </el-table-column>
          <el-table-column label="负责人" prop="chargeUser" show-overflow-tooltip />
          <el-table-column
            :formatter="dateFormatter"
            align="center"
            label="更新时间"
            prop="updateTime"
            width="170"
          />
          <el-table-column align="center" fixed="right" label="操作" width="150">
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
</template>

<script lang="ts" setup>
import { ModuleTree } from '@/views/Project/components/index'

import { dateFormatter } from '@/utils/formatTime'
import { handleTree } from '@/utils/tree'

import { CASE_LEVEL_ENUMS, RESULT_ENUMS } from '@/utils/enums'

import * as MHTTP from '@/api/project/module'
import * as HTTP from '@/api/tracked/testcase'
import { useAppStore } from '@/store/modules/app'
import { useUserStore } from '@/store/modules/user'

const { push } = useRouter() // 路由
const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const appStore = useAppStore()

const userStore = useUserStore()

defineOptions({ name: 'ProjectManager' })

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  name: '',
  moduleId: null
})

const loading = ref(false)
const list = ref<any>([])
const total = ref(0)
const modules = ref<any>([])
const checked = ref<any>([])

const queryFormRef = ref() // 搜索的表单
const getList = async () => {
  loading.value = true
  try {
    const data = await HTTP.getPage(queryParams.value)
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
    await message.delConfirm()
    // 发起删除
    await HTTP.removeData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

const handleAddCase = async () => {
  await push('/tracked/case/add')
}

const handleEditCase = async (id: number) => {
  await push('/tracked/case/edit/' + id)
}
const handleCopyCase = async (id: number) => {
  await push({ path: '/tracked/case/add', query: { from: id } })
}

const handleNodeClick = async (row: any) => {
  queryParams.value.moduleId = row.id === 0 ? null : row.id
  await handleQuery()
}

const handleSelectionChange = async (val: any[]) => {
  checked.value = val.map((item) => item.id)
}

const handleBatchDeleteCase = async () => {
  await HTTP.batchRemove(checked.value)
  toggleSelection()
  await handleQuery()
}

const multipleTableRef = ref()
const toggleSelection = () => {
  multipleTableRef.value!.clearSelection()
  checked.value = []
}

/** 获得模块树 */
const getTree = async () => {
  modules.value = []
  const data = await MHTTP.getSimple()
  modules.value = handleTree(data)
}

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => userStore.getProject),
  () => {
    getList()
  },
  { immediate: true, deep: true }
)

/** 初始化 **/
onMounted(async () => {
  appStore.setProjectPick(true)
  await getList()
  await getTree()
})
</script>

<style scoped></style>
