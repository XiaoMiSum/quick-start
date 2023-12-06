<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item label="" prop="name">
        <el-input
          v-model="queryParams.name"
          class="!w-240px"
          clearable
          placeholder="请输入归档名称"
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
        <el-button plain type="primary" @click="openForm('create')">
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" highlight-current-row stripe>
      <el-table-column align="center" label="归档名称" prop="name" />
      <el-table-column align="center" label="创建人" prop="creator" />
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="归档时间"
        prop="createTime"
        show-overflow-tooltip
        width="170"
      />
      <el-table-column :width="300" align="center" label="操作">
        <template #default="scope">
          <el-tooltip content="编辑" placement="top">
            <el-button circle plain type="primary" @click="openForm('update', scope.row)">
              <Icon icon="ep:edit" />
            </el-button>
          </el-tooltip>
          <el-tooltip content="明细" placement="top">
            <el-button circle plain type="primary" @click="openDetail(scope.row.id)">
              <Icon icon="ep:view" />
            </el-button>
          </el-tooltip>
          <el-tooltip content="导出" placement="top">
            <el-button circle plain type="primary" @click="handleDownload(scope.row)">
              <Icon icon="ep:download" />
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

  <ArchiveForm ref="formRef" @success="handleQuery" />
</template>

<script lang="ts" setup>
import { ArchiveForm } from './components'

import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'

import * as HTTP from '@/api/project/archive'
import { useAppStore } from '@/store/modules/app'

const appStore = useAppStore()
const { push } = useRouter() // 路由

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

defineOptions({ name: 'ProjectTag' })

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  name: '',
  status: undefined
})
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)

const queryFormRef = ref() // 搜索的表单

const getList = async () => {
  loading.value = true
  try {
    const data = await HTTP.getPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const handleQuery = async () => {
  queryParams.pageNo = 1
  getList()
}

const resetQuery = async () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 明细 */
const openDetail = (id?: number) => {
  push('/project/archive/' + id)
}

/** 导出 */
const handleDownload = async (data: any) => {
  const excel = await HTTP.download(data.id)
  download.excel(excel, data.name + '.xlsx')
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

/** 初始化 **/
onMounted(async () => {
  appStore.setProjectPick(true)
  await getList()
})
</script>

<style scoped></style>
