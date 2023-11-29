<template>
  <Card>
    <template #header>
      <span class="text-16px font-700">项目主页</span>
      <el-tooltip content="新增" placement="top">
        <el-button circle plain type="primary" @click="handleShowFrom()">
          <Icon icon="ep:plus" />
        </el-button>
      </el-tooltip>
    </template>
    <el-table v-if="link.list && link.list.length > 0" :data="link.list">
      <el-table-column label="链接类型" width="200">
        <template #default="scope">
          <EnumTag :enums="LINK_ENUMS" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column label="链接地址" prop="link">
        <template #default="scope">
          <el-link :href="scope.row.link" target="_blank" :underline="false" type="primary">
            {{ scope.row.link }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo" />
      <el-table-column align="center" label="" width="110">
        <template #default="scope">
          <el-tooltip content="编辑" placement="top">
            <el-button circle plain type="primary" @click="handleShowFrom(scope.row)">
              <Icon icon="ep:edit" />
            </el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button circle plain type="danger" @click="handleDeleteLink(scope.row.id)">
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
      :total="link.total"
      @pagination="getLink"
    />
  </Card>

  <Dialog v-model="visible" title="项目主页">
    <el-form :model="form" label-width="70">
      <el-form-item label="链接类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择链接类型" style="width: 100%">
          <el-option
            v-for="(item, index) in LINK_ENUMS"
            :key="index"
            :label="item.label"
            :value="item.key"
          />
        </el-select>
      </el-form-item>
      <el-form-item prop="link" label="链接地址">
        <el-input v-model="form.link" placeholder="请输入链接地址，如：https://www.baidu.com/" />
      </el-form-item>
      <el-form-item prop="memo" label="备注">
        <el-input v-model="form.memo" placeholder="请输入备注信息" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="loading" type="primary" @click="handleSaveLink">确 定</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { Card } from '@/components/Card'

import * as LINK from '@/api/project/link'

import { LINK_ENUMS } from '@/utils/enums'

import { useUserStore } from '@/store/modules/user'

const userStore = useUserStore()

const visible = ref(false)
const loading = ref(false)
const form = ref<any>()

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

const handleShowFrom = async (data?: any) => {
  if (data) {
    form.value = Object.assign({}, data)
  } else {
    form.value = { type: 'doc', link: '', memo: '' }
  }
  visible.value = true
}

const handleSaveLink = async () => {
  const func = form.value.id ? LINK.updateData : LINK.addData
  visible.value = false
  await func(form.value)
  handleQueryLink()
}

const handleDeleteLink = (id: any) => {
  LINK.removeData(id)
  handleQueryLink()
}

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => userStore.getProject),
  () => {
    getLink()
  },
  { immediate: true, deep: true }
)

onMounted(() => {
  getLink()
})
</script>

<style scoped></style>
