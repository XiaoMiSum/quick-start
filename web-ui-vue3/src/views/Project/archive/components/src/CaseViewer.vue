<template>
  <Dialog v-model="visible" :title="data.name" width="80%">
    <InfoViewer v-model="data" v-loading="loading" :show-actual="false" />
  </Dialog>
</template>

<script setup lang="ts">
import { InfoViewer } from '@/views/components/case'

import * as HTTP from '@/api/project/archive'

const visible = ref(false)
const loading = ref(false)

const data = ref<any>({})
/** 打开弹窗 */
const open = async (id: Number) => {
  loading.value = true
  visible.value = true
  try {
    data.value = await HTTP.getTestcase(id)
  } finally {
    loading.value = false
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>

<style scoped></style>
