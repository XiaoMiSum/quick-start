<template>
  <ContentWrap>
    <!-- 操作工具栏 -->
    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-button type="primary" @click="handleQuery">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
        <el-button
          v-hasPermi="['system:project:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" highlight-current-row stripe>
      <el-table-column align="center" label="成员名称" prop="memberName" />
      <el-table-column
        :formatter="dateFormatter"
        align="center"
        label="添加时间"
        prop="createTime"
        width="180"
      />
      <el-table-column :width="300" align="center" label="操作">
        <template #default="scope">
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

  <MemberForm ref="formRef" @success="handleQuery" />
</template>

<script lang="ts" setup>
import { dateFormatter } from '@/utils/formatTime'
import { MemberForm } from '../components'

import * as HTTP from '@/api/project/member'

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

defineOptions({ name: 'ProjectMember' })

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10
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

const handleQuery = async () => {
  queryParams.pageNo = 1
  getList()
}

const resetQuery = async () => {
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
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
  await getList()
})
</script>

<style scoped></style>
