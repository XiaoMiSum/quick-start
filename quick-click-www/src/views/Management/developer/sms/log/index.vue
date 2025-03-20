<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams">
      <el-form-item prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          clearable
          placeholder="请输入手机号"
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
            :label="item.signature"
            :value="item.code"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="templateCode">
        <el-select
          v-model="queryParams.templateCode"
          clearable
          filterable
          placeholder="请选择短信模版"
          @change="handleQuery"
        >
          <el-option
            v-for="(item, index) in templates"
            :key="index"
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
  </ContentWrap>

  <ContentWrap>
    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column align="center" label="手机号" prop="mobile" width="130" />
      <el-table-column align="center" label="短信内容" prop="templateContent" width="400" />
      <el-table-column align="center" label="发送状态">
        <template #default="scope">
          <ones-tag :type="DICT_TYPE.INFRA_SMS_SEND_STATUS" :value="scope.row.sendStatus" />
        </template>
      </el-table-column>
      <el-table-column align="center" label="接收状态">
        <template #default="scope">
          <ones-tag :type="DICT_TYPE.INFRA_SMS_RECEIVE_STATUS" :value="scope.row.receiveStatus" />
        </template>
      </el-table-column>
      <el-table-column align="center" label="短信渠道" prop="channelCode" />
      <el-table-column align="center" label="短信类型" prop="templateType" />
      <el-table-column align="center" class-name="small-padding fixed-width" label="" width="50">
        <template #default="scope">
          <el-button
            v-hasPermi="['developer:sms:log:query']"
            link
            type="primary"
            @click="handleView(scope.row.id)"
            >详细
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination
      v-show="total > 0"
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>

  <LogDetail ref="logDetail" />
</template>

<script lang="ts" setup>
import { getPage } from '@/api/management/developer/sms/log'
import * as C from '@/api/management/developer/sms/channel'
import * as T from '@/api/management/developer/sms/template'
import LogDetail from './LogDetail.vue'
import { DICT_TYPE } from '@/utils/dictionary'

const loading = ref(false)
const total = ref(0)
const list = ref<any>([])

const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  channelCode: undefined,
  templateCode: undefined,
  mobile: undefined
})

const channels = ref<any>([])
const templates = ref<any>([])

const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await getPage(queryParams.value)
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
const logDetail = ref()
const handleView = (id: number) => {
  logDetail.value.open(id)
}

const handleGetChannels = () => {
  C.getSimple().then((data) => {
    channels.value = data
  })
}

const handleGetTemplates = () => {
  T.getSimple().then((data) => {
    templates.value = data
  })
}

onMounted(() => {
  getList()
  handleGetChannels()
  handleGetTemplates()
})
</script>
