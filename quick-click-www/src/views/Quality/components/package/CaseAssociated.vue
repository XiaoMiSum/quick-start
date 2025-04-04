<template>
  <Dialog v-model="visible" title="关联用例" width="80%" @close="close">
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
        </ContentWrap>

        <!-- 列表 -->
        <ContentWrap>
          <el-table
            ref="multipleTableRef"
            v-loading="loading"
            :data="list"
            :row-key="(row) => row.originalId"
            highlight-current-row
            width="100%"
            @selection-change="handleSelectionChange"
          >
            <el-table-column :reserve-selection="true" type="selection" width="35" />
            <el-table-column label="用例名称" prop="title" show-overflow-tooltip max-width="500" />
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
                <ones-tag
                  :type="DICT_TYPE.QUALITY_TESTCASE_PRIORITY"
                  :value="scope.row.priority"
                  effect="dark"
                />
              </template>
            </el-table-column>

            <el-table-column align="center" label="责任人" prop="supervisor">
              <template #default="scope">
                <user-tag :value="scope.row.supervisor" />
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
    <template #footer>
      <span class="mr-10px"> 已选中 {{ checked.length }} 条数据 </span>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="btnLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { DefaultNodeTree } from '@/views/components/node'

import { DICT_TYPE } from '@/utils/dictionary'

import * as REVIEW from '@/api/quality/review'
import * as PLAN from '@/api/quality/plan'

const props = defineProps({
  source: {
    required: true,
    type: String
  },
  dataId: {
    required: true,
    type: null,
    default: -1
  },
  projectId: {
    required: true,
    type: null,
    default: -1
  },
  enums: {
    required: true,
    type: Array
  }
})
const { source, dataId, projectId } = toRefs(props)

const visible = ref(false)
const btnLoading = ref(false)
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)
const checked = ref<any>([])

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  title: '',
  nodeId: null
})
const queryFormRef = ref() // 搜索的表单

/** 打开弹窗 */
const open = async () => {
  checked.value = []
  visible.value = true
  loading.value = true
  try {
    await getList()
  } finally {
    loading.value = false
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const getList = async () => {
  loading.value = true
  try {
    const func = source.value === 'review' ? REVIEW.getUnAssociCase : PLAN.getUnAssociCase
    if (source.value === 'review') {
      queryParams.value.reviewId = dataId.value
    } else {
      queryParams.value.planId = dataId.value
    }
    queryParams.value.projectId = projectId.value
    const data = await func(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    toggleSelection()
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

const submitForm = async () => {
  btnLoading.value = true
  try {
    if (source.value === 'review') {
      await REVIEW.addAssociCase({
        projectId: projectId.value,
        reviewId: dataId.value,
        testcases: checked.value
      })
    } else if (source.value === 'plan') {
      await PLAN.addAssociCase({
        projectId: projectId.value,
        planId: dataId.value,
        testcases: checked.value
      })
    }
    await getList()
  } finally {
    btnLoading.value = false
  }
}

const handleSelectionChange = async (val: any[]) => {
  checked.value = val.map((item) => item.originalId)
}

const multipleTableRef = ref()
const toggleSelection = () => {
  multipleTableRef.value!.clearSelection()
  checked.value = []
}

const emit = defineEmits(['close']) // 定义 close 事件，用于操作成功后的回调
const close = () => {
  emit('close')
}
</script>

<style scoped></style>
