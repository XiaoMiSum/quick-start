<template>
  <ProjectInfo ref="projectInfo" v-model="data" @save-success="getInfo" />
  <ContentWrap>
    <div class="relative">
      <el-tabs v-model="active" :tab-change="tabChange">
        <el-tab-pane label="主页 & 归档">
          <div class="flex">
            <div class="mr-8 w-49%">
              <ProjectLink ref="link" @save="handleSaveLink" v-model="data.links" />
            </div>
            <div class="w-49%">
              <ProjectArchive ref="archive" />
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane>
          <template #label>
            <Icon icon="ep:user" class="mr-2px" />
            <span>项目成员</span>
          </template>
          暂未实现
        </el-tab-pane>
        <el-tab-pane>
          <template #label>
            <Icon icon="fa:file-archive-o" class="mr-2px" />
            <span>环境定义</span>
          </template>
          暂未实现
        </el-tab-pane>
      </el-tabs>
      <el-tooltip content="新增" placement="top" v-if="active !== '0'">
        <el-button
          v-if="active !== '0'"
          circle
          plain
          type="primary"
          style="position: absolute; top: 0; right: 0"
          @click="handleAddClick"
        >
          <Icon icon="ep:plus" />
        </el-button>
      </el-tooltip>
    </div>
  </ContentWrap>
</template>

<script lang="ts" setup>
import ProjectInfo from './ProjectInfo.vue'
import ProjectLink from './ProjectLink.vue'
import ProjectArchive from './ProjectArchive.vue'

import * as HTTP from '@/api/project'

import { useAppStore } from '@/store/modules/app'
const appStore = useAppStore()

import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()

const data = ref<any>({ productManagers: [], developers: [], testers: [], links: [] })
const active = ref('0')

const handleSaveLink = async () => {
  await HTTP.updateData({ id: data.value.id, links: data.value.links })
  await getInfo()
}

const getInfo = async () => {
  const id = userStore.getProject
  data.value = await HTTP.getData(id)
}

const tabChange = (name: string) => {
  active.value = name
}

const link = ref()
const handleAddClick = async () => {
  switch (active.value) {
    case '0':
      link.value.handleAddClick()
      break
    case '1':
      alert('暂未实现')
      break
    case '2':
      alert('暂未实现')
      break
    case '3':
      alert('暂未实现')
      break
  }
}

/** 监听当前项目变化，刷新列表数据 */
const archive = ref()
watch(
  computed(() => userStore.getProject),
  () => {
    getInfo()
    if (archive.value) {
      archive.value.initialization()
    }
  },
  { immediate: true, deep: true }
)

onMounted(async () => {
  appStore.setProjectPick(true)
  await getInfo()
})
</script>
