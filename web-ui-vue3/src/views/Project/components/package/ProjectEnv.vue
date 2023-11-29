<template>
  <Card>
    <template #header>
      <span class="text-16px font-700">环境信息</span>
      <el-tooltip content="新增" placement="top">
        <el-button circle plain type="primary" @click="handleShowFrom()">
          <Icon icon="ep:plus" />
        </el-button>
      </el-tooltip>
    </template>
    <el-table v-if="env.list && env.list.length > 0" :data="env.list">
      <el-table-column label="环境类型" width="200">
        <template #default="scope">
          <EnumTag :enums="ENV_ENUMS" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column label="协议" width="150">
        <template #default="scope">
          <EnumTag :enums="PROTOCOL_ENUMS" :value="scope.row.protocol" />
        </template>
      </el-table-column>
      <el-table-column label="主机地址" prop="host" />
      <el-table-column label="端口" width="100" prop="port" align="right" />
      <el-table-column label="备注" prop="memo" />
      <el-table-column align="center" label="" width="110">
        <template #default="scope">
          <el-tooltip content="编辑" placement="top">
            <el-button circle plain type="primary" @click="handleShowFrom(scope.row)">
              <Icon icon="ep:edit" />
            </el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button circle plain type="danger" @click="handleDeleteEnv(scope.row.id)">
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
      :total="env.total"
      @pagination="getEnv"
    />
  </Card>
  <Dialog v-model="visible" title="环境信息">
    <el-form :model="form" label-width="70">
      <el-form-item label="环境类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择环境类型" style="width: 100%">
          <el-option
            v-for="(item, index) in ENV_ENUMS"
            :key="index"
            :label="item.label"
            :value="item.key"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="协议" prop="protocol">
        <el-select v-model="form.protocol" placeholder="请选择请求协议" style="width: 100%">
          <el-option
            v-for="(item, index) in PROTOCOL_ENUMS"
            :key="index"
            :label="item.label"
            :value="item.key"
          />
        </el-select>
      </el-form-item>
      <el-form-item prop="host" label="主机地址">
        <el-input v-model="form.host" placeholder="请输入主机地址，如：localhost" />
      </el-form-item>
      <el-form-item prop="port" label="端口">
        <el-input v-model="form.port" placeholder="请输入主机端口，如：80" />
      </el-form-item>
      <el-form-item prop="memo" label="备注">
        <el-input v-model="form.memo" placeholder="请输入备注信息" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="loading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { Card } from '@/components/Card'

import * as ENV from '@/api/project/env'

import { ENV_ENUMS, PROTOCOL_ENUMS } from '@/utils/enums'

import { useUserStore } from '@/store/modules/user'

const userStore = useUserStore()

const env = ref<any>({
  list: [],
  total: 0
})

const visible = ref(false)
const loading = ref(false)
const form = ref<any>()

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 5
})

const getEnv = async () => {
  env.value = await ENV.getPage(queryParams.value)
}

const handleQueryEnv = async () => {
  queryParams.value.pageNo = 1
  getEnv()
}

const handleShowFrom = async (data?: any) => {
  console.log(data)
  if (data) {
    form.value = Object.assign({}, data)
  } else {
    form.value = { type: 'test', protocol: 'http', host: '', port: '', memo: '' }
  }
  visible.value = true
}

const submitForm = async () => {
  const func = form.value.id ? ENV.updateData : ENV.addData
  await func(form.value)
  visible.value = false
  handleQueryEnv()
}

const handleDeleteEnv = (id: any) => {
  ENV.removeData(id)
  handleQueryEnv()
}

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => userStore.getProject),
  () => {
    getEnv()
  },
  { immediate: true, deep: true }
)

onMounted(() => {
  getEnv()
})
</script>

<style scoped></style>
