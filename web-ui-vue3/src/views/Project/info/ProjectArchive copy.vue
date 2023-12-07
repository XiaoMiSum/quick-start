<template>
  <Card>
    <template #header>
      <span class="text-16px font-700">归档历史</span>
      <el-tooltip content="编辑" placement="top">
        <el-button circle plain type="primary" @click="openForm('create')">
          <Icon icon="ep:plus" />
        </el-button>
      </el-tooltip>
    </template>

    <el-table v-loading="loading" :data="list" highlight-current-row stripe :show-header="false">
      <el-table-column>
        <template #default="scope">
          <span>
            {{
              formatDate(scope.row.createTime) +
              ' ' +
              scope.row.creator +
              ' 创建归档：' +
              scope.row.name
            }}
          </span>
        </template>
      </el-table-column>

      <el-table-column align="center" width="200">
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
  </Card>

  <ArchiveForm ref="formRef" @success="getList" />
</template>

<script lang="ts" setup>
import { ArchiveForm } from '@/views/Project/archive/components'

import { formatDate } from '@/utils/formatTime'
import download from '@/utils/download'

import * as HTTP from '@/api/project/archive'
import { useAppStore } from '@/store/modules/app'

const appStore = useAppStore()
const { push } = useRouter() // 路由

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

defineOptions({ name: 'ProjectArchive' })

const props = defineProps({
  modelValue: {
    required: true,
    type: Object
  }
})

const queryParams = reactive({
  pageNo: 1,
  pageSize: 5
})
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)

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
