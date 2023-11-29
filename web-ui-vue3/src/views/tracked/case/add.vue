<template>
  <CaseEditor />
</template>

<script lang="ts" setup>
import { CaseEditor } from '../components/index'
import { useAppStore } from '@/store/modules/app'
const appStore = useAppStore()
onMounted(() => {
  appStore.setProjectPick(false)
})
</script>

<style scoped></style>
