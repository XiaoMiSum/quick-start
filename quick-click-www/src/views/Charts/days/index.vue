<template>
  <ContentWrap>
    <el-tabs v-model="active" :tab-change="tabChange">
      <el-tab-pane label="开发人员" name="0" v-if="checkPermi(['charts:days:query'])">
        <DevDaysData ref="devDaysData" :users="users" />
      </el-tab-pane>
    </el-tabs>
  </ContentWrap>
</template>

<script setup>
import * as Days from '@/api/charts/days'
import { checkPermi } from '@/utils/permission'

import DevDaysData from './DevDaysData.vue'

const active = ref('0')
const flag = ref(false)

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

import { useGlobalStore } from '@/store/modules/global'
const globalStore = useGlobalStore()

defineOptions({ name: 'ChartsDays' })
const users = ref([])

/**  获取用户列表 */
const getUsers = async () => {
  users.value = await globalStore.getUsers
}

const tabChange = (name) => {
  active.value = name
  flag.value = true
}

const devDaysData = ref(null)
const handleChangeTabData = () => {
  switch (active.value) {
    case '0':
      devDaysData.value.getList()
      break
  }
}

/** 监听当前项目变化，刷新用户数据数据 */
watch(
  computed(() => globalStore.getCurrentProject),
  async () => {
    await getUsers()
    handleChangeTabData()
  },
  { immediate: true, deep: true }
)

// 监听活动面板变化 刷新数据
watch(
  computed(() => active.value),
  async () => {
    if (flag.value) {
      handleChangeTabData()
    }
  },
  { immediate: true, deep: true }
)
</script>
