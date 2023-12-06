<template>
  <ProjectInfo ref="projectInfo" />
  <ProjectLink @save="handleSaveLink" v-model="data.links" />
</template>

<script lang="ts" setup>
import ProjectInfo from './ProjectInfo.vue'
import ProjectLink from './ProjectLink.vue'

import * as HTTP from '@/api/project'

import { useAppStore } from '@/store/modules/app'
const appStore = useAppStore()

import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()

const data = ref<any>({ links: [] })

const handleSaveLink = async () => {
  await HTTP.updateData({ id: data.value.id, links: data.value.links })
  await getInfo()
}

const getInfo = async () => {
  const id = userStore.getProject
  data.value = await HTTP.getData(id)
}

/** 监听当前项目变化，刷新列表数据 */

watch(
  computed(() => userStore.getProject),
  () => {
    getInfo()
  },
  { immediate: true, deep: true }
)

onMounted(async () => {
  appStore.setProjectPick(true)
  await getInfo()
})
</script>

<style scoped></style>
