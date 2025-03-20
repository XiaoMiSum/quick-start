<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item prop="status">
        <el-select v-model="queryParams.status" clearable placeholder="请选择状态">
          <el-option
            v-for="item in getIntDictOptions(DICT_TYPE.COMMON_STATUS)"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item prop="code">
        <el-input
          v-model="queryParams.code"
          clearable
          placeholder="请输入模板编码"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item prop="apiTemplateId">
        <el-input
          v-model="queryParams.apiTemplateId"
          clearable
          placeholder="请输入短信 API 的模板编号"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="channelCode">
        <el-select
          v-model="queryParams.channelCode"
          clearable
          filterable
          placeholder="请选择短信渠道"
          @change="handleQuery"
        >
          <el-option
            v-for="(item, index) in channels"
            :key="index"
            :label="item.code"
            :value="item.code"
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
          v-hasPermi="['developer:sms:template:add']"
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
      <el-table-column align="center" label="模板名称" prop="name" />
      <el-table-column align="center" label="模板内容" prop="content" width="500" />
      <el-table-column align="center" label="开启状态" prop="status" width="80">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleChangeStatus(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column align="center" label="短信渠道模板编号" prop="apiTemplateId" width="180" />
      <el-table-column align="center" label="短信渠道" prop="channelCode" width="120" />
      <el-table-column
        align="center"
        class-name="small-padding fixed-width"
        label="操作"
        width="200"
      >
        <template #default="scope">
          <el-button
            v-hasPermi="['developer:sms:template:send:sms']"
            link
            type="primary"
            @click="handleSendSms(scope.row.id)"
            >测试
          </el-button>
          <el-button
            v-hasPermi="['developer:sms:template:update']"
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            >修改
          </el-button>
          <el-button
            v-hasPermi="['developer:sms:template:remove']"
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

  <TemplateForm ref="templateForm" :options="channels" @success="getList" />
  <SendSms ref="sendSms" />
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/management/developer/sms/template'
import { DICT_TYPE, getIntDictOptions } from '@/utils/dictionary'
import { CommonStatus } from '@/utils/constants'
import * as C from '@/api/management/developer/sms/channel'
import SendSms from './SendSms.vue'
import TemplateForm from './TemplateForm.vue'

const message = useMessage()
const { t } = useI18n()

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref<any>([]) // 列表的数据
const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 10,
  code: undefined,
  status: undefined,
  apiTemplateId: undefined,
  channelCode: undefined
})

const channels = ref<any>([])

const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
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

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const templateForm = ref()
const openForm = (type: string, id?: number) => {
  templateForm.value.open(type, id)
}

const handleGetChannels = () => {
  C.getSimple().then((data) => {
    channels.value = data
  })
}

/** 修改状态操作 */
const handleChangeStatus = async (row: any) => {
  try {
    // 修改状态的二次确认
    const text = row.status === CommonStatus.ENABLE ? '禁用' : '启用'
    await message.confirm('确认要' + text + '短信模板 "' + row.name + '"?', t('common.reminder'))
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

const sendSms = ref()
const handleSendSms = (id: number) => {
  sendSms.value.open(id)
}

onMounted(() => {
  getList()
  handleGetChannels()
})
</script>
