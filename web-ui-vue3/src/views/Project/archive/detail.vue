<template>
  <ContentWrap :title="'归档：' + title">
    <el-row :gutter="5">
      <!-- 左侧模块树 -->
      <el-col :span="5" :xs="24">
        <ContentWrap class="h-1/1">
          <NodeTree
            ref="nodeTree"
            v-model="dataTree"
            @node-click="handleNodeClick"
            :readonly="true"
          />
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
        </ContentWrap>

        <!-- 列表 -->
        <ContentWrap>
          <el-table v-loading="loading" :data="list" highlight-current-row width="100%">
            <el-table-column label="用例名称" prop="name" show-overflow-tooltip width="200" />
            <el-table-column label="所属模块" prop="path" show-overflow-tooltip width="200" />
            <el-table-column align="center" label="用例等级" prop="level">
              <template #default="scope">
                <EnumTag :enums="CASE_LEVEL_ENUMS" :value="scope.row.level" />
              </template>
            </el-table-column>
            <el-table-column align="center" label="标签" prop="tags" show-overflow-tooltip>
              <template #default="scope">
                <el-tag v-for="item in scope.row.tags" :key="item" class="mr-5px">
                  {{ item }}</el-tag
                >
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              label="负责人"
              prop="chargeUser"
              show-overflow-tooltip
            />
            <el-table-column align="center" label="" width="100">
              <template #default="scope">
                <el-tooltip content="明细" placement="top">
                  <el-button link plain type="primary" @click="openCaseViewer(scope.row.id)">
                    <Icon class="mr-5px" icon="ep:document" />
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

  <CaseViewer ref="caseViewer" />
</template>

<script lang="ts" setup>
import { useTagsViewStore } from '@/store/modules/tagsView'
import { useAppStore } from '@/store/modules/app'
import { useUserStore } from '@/store/modules/user'
import { useRoute } from 'vue-router' //1.先在需要跳转的页面引入useRouter

import { CaseViewer } from './components'
import { NodeTree } from '@/views/components/node'

import { handleTree } from '@/utils/tree'
import { CASE_LEVEL_ENUMS } from '@/utils/enums'

import { getNode, getTestcases, getData } from '@/api/project/archive'

defineOptions({ name: 'ArchiveDetail' })

const appStore = useAppStore()
const userStore = useUserStore()
const tagsViewStore = useTagsViewStore()
const { params } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数
const { currentRoute, push } = useRouter()

const archiveId = ref<any>(null)

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  archiveId: null,
  caseName: '',
  nodeId: null
})

const dataTree = ref<Tree[]>([]) // 树形结构
const title = ref('')
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)
const queryFormRef = ref() // 搜索的表单

const handleGetData = async () => {
  if (!archiveId.value) {
    return
  }
  const data = await getData(archiveId.value)
  if (!data) {
    tagsViewStore.delView(unref(currentRoute))
    push('/project/profile')
    return
  }
  title.value = data.name
}

/** 获取用例 */
const getList = async () => {
  loading.value = true
  try {
    const data = await getTestcases(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = async () => {
  queryParams.value.pageNo = 1
  await getList()
}

const resetQuery = async () => {
  queryFormRef.value.resetFields()
  await handleQuery()
}

const handleNodeClick = async (row: any) => {
  queryParams.value.nodeId = row.id === 0 ? null : row.id
  await handleQuery()
}

/** 获得模块树 */
const getTree = async (id: any) => {
  dataTree.value = []
  const data = await getNode(id)
  let node: Tree = { id: 0, name: '根模块', children: [] }
  node.children = handleTree(data)
  node.children.splice(0, 0, { id: -1, name: '未分组用例', children: [] })
  dataTree.value.push(node)
}

/** 查看用例 */
const caseViewer = ref()
const openCaseViewer = (id: Number) => {
  caseViewer.value.open(id)
}

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => userStore.getProject),
  () => {
    handleGetData()
  },
  { immediate: true, deep: true }
)

/** 初始化 **/
onMounted(async () => {
  appStore.setProjectPick(true)
  if (params && params.id) {
    archiveId.value = params.id
    queryParams.value.archiveId = params.id
    await handleGetData()
    await getTree(params.id)
    await getList()
  }
})
</script>

<style scoped></style>
