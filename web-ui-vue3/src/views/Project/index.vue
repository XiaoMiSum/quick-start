<template>
  <ProjectInfo ref="projectInfo" />
  <ProjectLink ref="projectLink" />
  <ProjectEnv ref="projectEnv" />
</template>

<script lang="ts" setup>
import { ProjectEnv, ProjectInfo, ProjectLink } from './components'

import { useAppStore } from '@/store/modules/app'

const appStore = useAppStore()

onMounted(async () => {
  appStore.setProjectPick(true)
})
</script>

<style scoped></style>
