<template>
  <Card>
    <template #header>
      <span class="text-16px font-700">项目主页</span>
      <el-tooltip content="新增" placement="top">
        <el-button circle plain type="primary" @click="insertLinkList">
          <Icon icon="ep:plus" />
        </el-button>
      </el-tooltip>
    </template>
    <el-form ref="linkFormRef" :model="link">
      <el-table v-if="link.list && link.list.length > 0" :data="link.list">
        <el-table-column label="链接类型" width="200">
          <template #default="scope">
            <el-form-item :prop="'link.list.' + scope.$index + '.type'" clearable>
              <el-select v-model="scope.row.type" placeholder="请选择链接类型" style="width: 100%">
                <el-option
                  v-for="(item, index) in LINK_ENUMS"
                  :key="index"
                  :label="item.label"
                  :value="item.key"
                />
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="链接地址">
          <template #default="scope">
            <el-form-item :prop="'link.list.' + scope.$index + '.link'" clearable>
              <el-input v-model="scope.row.link" placeholder="请输入链接地址" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="备注">
          <template #default="scope">
            <el-form-item :prop="'link.list.' + scope.$index + '.memo'" clearable>
              <el-input v-model="scope.row.memo" placeholder="请输入备注信息" />
            </el-form-item>
          </template>
        </el-table-column>

        <el-table-column align="center" label="" width="110">
          <template #default="scope">
            <el-form-item>
              <el-tooltip content="保存" placement="top">
                <el-button circle plain type="primary" @click="handleSaveLink(scope.row)">
                  <Icon icon="ep:finished" />
                </el-button>
              </el-tooltip>
              <el-tooltip content="删除" placement="top">
                <el-button circle plain type="danger" @click="handleDeleteLink(scope.row.id)">
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
        :total="link.total"
        @pagination="getLink"
      />
    </el-form>
  </Card>
</template>

<script lang="ts" setup>
import { Card } from '@/components/Card'

import * as LINK from '@/api/project/link'

import { LINK_ENUMS } from '@/utils/enums'

const link = ref<any>({
  list: [],
  total: 0
})

const queryParams = ref<any>({
  pageNo: 1,
  pageSize: 5
})

const getLink = async () => {
  link.value = await LINK.getPage(queryParams.value)
}

const handleQueryLink = async () => {
  queryParams.value.pageNo = 1
  getLink()
}

const insertLinkList = () => {
  link.value.list = link.value.list || []
  if (link.value.list.length > 1) {
    link.value.list.splice(0, 0, { type: '', link: '', memo: '' })
  } else {
    link.value.list.push({ type: '', link: '', memo: '' })
  }
}

const handleSaveLink = async (data: any) => {
  const func = data.id ? LINK.updateData : LINK.addData
  await func(data)
  handleQueryLink()
}

const handleDeleteLink = (id: any) => {
  LINK.removeData(id)
  handleQueryLink()
}

onMounted(() => {
  getLink()
})
</script>

<style scoped></style>
