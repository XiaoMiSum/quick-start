<template>
  <ProjectInfo ref="projectInfo" v-model="data" @save-success="getInfo" />
  <ContentWrap>
    <div class="relative">
      <el-tabs v-model="active" :tab-change="tabChange">
        <el-tab-pane label="主页 & 迭代" />
        <el-tab-pane v-if="checkPermi(['project:member:query'])">
          <template #label>
            <Icon icon="ep:user" class="mr-2px" />
            <span>项目成员</span>
          </template>
          <ProjectMember ref="projectMember" :currentProject="globalStore.getCurrentProject" />
        </el-tab-pane>
        <el-tab-pane v-if="checkPermi(['project:node:query'])">
          <template #label>
            <Icon icon="fa:file-archive-o" class="mr-2px" />
            <span>模块管理</span>
          </template>
          <ProjectNode ref="projectNode" :currentProject="globalStore.getCurrentProject" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </ContentWrap>
</template>

<script lang="ts" setup>
import ProjectInfo from './ProjectInfo.vue'
import ProjectMember from '../member/index.vue'
import ProjectNode from '../node/index.vue'

import { checkPermi } from '@/utils/permission'

import * as HTTP from '@/api/project'

import { useGlobalStore } from '@/store/modules/global'
const globalStore = useGlobalStore()

const data = ref<any>({})
const active = ref('0')

const getInfo = async () => {
  const id = globalStore.getCurrentProject
  data.value = await HTTP.getData(id)
}

const tabChange = (name: string) => {
  active.value = name
}

/** 监听当前项目变化，刷新列表数据 */
const archive = ref()
watch(
  computed(() => globalStore.getCurrentProject),
  async () => {
    await getInfo()
    if (archive.value) {
      archive.value.initialization()
    }
  },
  { immediate: true, deep: true }
)

onMounted(async () => {
  await getInfo()
})
</script>
