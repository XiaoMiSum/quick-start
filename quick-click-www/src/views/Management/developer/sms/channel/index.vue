<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryForm" :inline="true" :model="queryParams">
      <el-form-item prop="signature">
        <el-input
          v-model="queryParams.signature"
          clearable
          placeholder="请输入短信签名"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="status">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择状态"
          style="width: 200px"
        >
          <el-option
            v-for="item in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
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
        <el-button
          v-hasPermi="['developer:sms:channel:add']"
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

  <ContentWrap>
    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column align="center" label="编号" prop="id" />
      <el-table-column align="center" label="短信签名" prop="signature" />
      <el-table-column align="center" label="渠道编码" prop="code" />
      <el-table-column align="center" label="状态" prop="status" width="80">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleChangeStatus(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column align="center" label="短信 API 账号" prop="apiKey" />
      <el-table-column align="center" label="短信 API 密钥" prop="apiSecret" />
      <el-table-column align="center" label="回调 URL" prop="callbackUrl" />
      <el-table-column align="center" class-name="small-padding fixed-width" label="操作">
        <template #default="scope">
          <el-button
            v-hasPermi="['developer:sms:channel:update']"
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            >修改
          </el-button>
          <el-button
            v-hasPermi="['developer:sms:channel:remove']"
            link
            type="danger"
            @click="handleDelete(scope.row)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>

  <ChannelFrom ref="channelForm" @success="getList" />
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/management/developer/sms/channel'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dictionary'
import { CommonStatus } from '@/utils/constants'
import ChannelFrom from './ChannelForm.vue'

const message = useMessage()
const { t } = useI18n()

const loading = ref(false)
const total = ref(0)
const list = ref<any>([])
const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  signature: undefined,
  status: undefined
})

const getList = async () => {
  loading.value = true
  // 执行查询
  HTTP.getPage(queryParams.value).then((data) => {
    list.value = data.list
    total.value = data.total
  })
  loading.value = false
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNo = 1
  getList()
}

/** 重置按钮操作 */

const queryForm = ref() // 搜索的表单
const resetQuery = () => {
  queryForm.value.resetFields()
  handleQuery()
}

/** 修改状态操作 */
const handleChangeStatus = async (row: any) => {
  try {
    // 修改状态的二次确认
    const text = row.status === CommonStatus.ENABLE ? '禁用' : '启用'
    await message.confirm('确认要' + text + '短信渠道 "' + row.name + '"?', t('common.reminder'))
    const status = row.status === CommonStatus.ENABLE ? CommonStatus.DISABLE : CommonStatus.ENABLE
    await HTTP.updateData({ id: row.id, status: status })
    message.success(text + '成功')
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status = row.status === CommonStatus.ENABLE ? CommonStatus.DISABLE : CommonStatus.ENABLE
  }
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await HTTP.delData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 添加/修改操作 */
const channelForm = ref()
const openForm = (type: string, id?: number) => {
  channelForm.value.open(type, id)
}

onMounted(() => {
  getList()
})
</script>
