<template>
  <Card>
    <template #header>
      <span class="text-16px font-700">环境信息</span>
      <el-tooltip content="新增" placement="top">
        <el-button circle plain type="primary" @click="insertEnvList">
          <Icon icon="ep:plus" />
        </el-button>
      </el-tooltip>
    </template>
    <el-form ref="envFormRef" :model="env">
      <el-table v-if="env.list && env.list.length > 0" :data="env.list">
        <el-table-column label="环境类型" width="200">
          <template #default="scope">
            <el-form-item :prop="'env.list.' + scope.$index + '.type'" clearable>
              <el-select v-model="scope.row.type" placeholder="请选择环境类型" style="width: 100%">
                <el-option
                  v-for="(item, index) in ENV_ENUMS"
                  :key="index"
                  :label="item.label"
                  :value="item.key"
                />
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="协议" width="150">
          <template #default="scope">
            <el-form-item :prop="'env.list.' + scope.$index + '.protocol'" clearable>
              <el-select
                v-model="scope.row.protocol"
                placeholder="请选择请求协议"
                style="width: 100%"
              >
                <el-option
                  v-for="(item, index) in PROTOCOL_ENUMS"
                  :key="index"
                  :label="item.label"
                  :value="item.key"
                />
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="主机地址">
          <template #default="scope">
            <el-form-item :prop="'env.list.' + scope.$index + '.host'" clearable>
              <el-input v-model="scope.row.host" placeholder="请输入主机地址" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="端口" width="100">
          <template #default="scope">
            <el-form-item :prop="'env.list.' + scope.$index + '.port'" clearable>
              <el-input v-model="scope.row.port" placeholder="80" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="备注">
          <template #default="scope">
            <el-form-item :prop="'env.list.' + scope.$index + '.memo'" clearable>
              <el-input v-model="scope.row.memo" placeholder="请输入备注信息" />
            </el-form-item>
          </template>
        </el-table-column>

        <el-table-column align="center" label="" width="110">
          <template #default="scope">
            <el-form-item>
              <el-tooltip content="保存" placement="top">
                <el-button circle plain type="primary" @click="handleSaveEnv(scope.row)">
                  <Icon icon="ep:finished" />
                </el-button>
              </el-tooltip>
              <el-tooltip content="删除" placement="top">
                <el-button circle plain type="danger" @click="handleDeleteEnv(scope.row.id)">
                  <Icon icon="ep:delete" />
                </el-button>
              </el-tooltip>
            </el-form-item>
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
    </el-form>
  </Card>
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

const insertEnvList = () => {
  env.value.list = env.value.list || []
  if (env.value.list.length > 1) {
    env.value.list.splice(0, 0, { type: 'test', protocol: 'http', host: '', port: '', memo: '' })
  } else {
    env.value.list.push({ type: 'test', protocol: 'http', host: '', port: '', memo: '' })
  }
}

const handleSaveEnv = async (data: any) => {
  const func = data.id ? ENV.updateData : ENV.addData
  await func(data)
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
