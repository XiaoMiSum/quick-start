<template>
  <ContentWrap>
    <div class="relative">
      <el-tabs v-model="active" @tab-change="tabChange">
        <el-tab-pane name="0" label="基础信息">
          <ProjectInfo ref="projectInfo" v-model="data" @save-success="getInfo" />
          <ContentWrap> 待评审列表 </ContentWrap>
          <ContentWrap> 待执行列表 </ContentWrap>
        </el-tab-pane>
        <el-tab-pane name="1" v-if="checkPermi(['project:member:query'])">
          <template #label>
            <Icon icon="ep:user" class="mr-2px" />
            <span>项目成员</span>
          </template>
          <ProjectMember ref="projectMember" :currentProject="currentProject" />
        </el-tab-pane>
        <el-tab-pane name="2" v-if="checkPermi(['project:node:query'])">
          <template #label>
            <Icon icon="fa:file-archive-o" class="mr-2px" />
            <span>模块管理</span>
          </template>
          <ProjectNode ref="projectNode" :currentProject="currentProject" />
        </el-tab-pane>
        <el-tab-pane name="3" v-if="checkPermi(['project:node:query'])">
          <template #label>
            <Icon icon="fa:file-archive-o" class="mr-2px" />
            <span>每日统计-开发</span>
          </template>
          <DevDaysData ref="devDaysData" :users="users" />
        </el-tab-pane>
        <el-tab-pane name="4" v-if="checkPermi(['project:node:query'])">
          <template #label>
            <Icon icon="fa:file-archive-o" class="mr-2px" />
            <span>每日统计-测试</span>
          </template>
          <TesterDaysData ref="testerDaysData" :users="users" />
        </el-tab-pane>
      </el-tabs>
    </div>
  </ContentWrap>
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/project'

import ProjectInfo from './ProjectInfo.vue'
import ProjectMember from '../member/index.vue'
import ProjectNode from '../node/index.vue'
import DevDaysData from '../days/DevDaysData.vue'
import TesterDaysData from '../days/TesterDaysData.vue'

import { checkPermi } from '@/utils/permission'

import { useGlobalStore } from '@/store/modules/global'
const globalStore = useGlobalStore()

const data = ref<any>({})
const active = ref('0')
const flag = ref(false)

const currentProject = ref('')

const getInfo = async () => {
  const id = globalStore.getCurrentProject
  data.value = await HTTP.getData(id)
}

const users = ref<any>([])

/**  获取用户列表 */
const getUsers = async () => {
  users.value = await globalStore.getUsers
}

const projectNode = ref()
const projectMember = ref()

const devDaysData = ref()
const testerDaysData = ref()

const handleChangeTabData = async () => {
  switch (active.value) {
    case '0':
      await getInfo()
      break
    case '1':
      await projectMember.value.getList()
      break
    case '2':
      await projectNode.value.getList()
      break
    case '3':
      await devDaysData.value.getList()
      break
    case '4':
      await testerDaysData.value.getList()
      break
  }
}

const tabChange = (name) => {
  active.value = name
  flag.value = true
}

/** 监听当前项目变化，刷新列表数据 */
watch(
  computed(() => globalStore.getCurrentProject),
  async () => {
    currentProject.value = globalStore.getCurrentProject
    await getUsers()
    await getInfo()
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

onMounted(async () => {
  currentProject.value = globalStore.getCurrentProject
  await getInfo()
})
</script>
