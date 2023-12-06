<template>
  <Card>
    <template #header>
      <span class="text-16px font-700">{{ info.name }}</span>
      <el-tooltip content="编辑" placement="top">
        <el-button circle plain type="primary" @click="openForm('update', info.id)">
          <Icon icon="ep:edit" />
        </el-button>
      </el-tooltip>
    </template>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="产品经理">
        <el-tag v-for="(item, index) in info.productManagers" :key="index" class="mr-2" type="info"
          >{{ item }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="开发人员">
        <el-tag v-for="(item, index) in info.developers" :key="index" class="mr-2" type="info"
          >{{ item }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="测试人员">
        <el-tag v-for="(item, index) in info.testers" :key="index" class="mr-2" type="info"
          >{{ item }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="备注信息"> {{ info.memo }}</el-descriptions-item>
    </el-descriptions>
  </Card>
  <ProjectForm ref="infoFormRef" @success="getInfo" />
</template>

<script lang="ts" setup>
import ProjectForm from '@/views/Project/table/ProjectForm.vue'
import { Card } from '@/components/Card'

import * as PROJECT from '@/api/project'

import { useUserStore } from '@/store/modules/user'

const userStore = useUserStore()

defineOptions({ name: 'ProjectInfo' })

const info = ref<any>({})

/** 添加/修改操作 */
const infoFormRef = ref()
const openForm = (type: string, id?: number) => {
  infoFormRef.value.open(type, id)
}

const getInfo = async () => {
  const id = userStore.getProject
  info.value = await PROJECT.getData(id)
}

// 监听当前项目变化，刷新列表数据
watch(
  computed(() => userStore.getProject),
  () => {
    getInfo()
  },
  { immediate: true, deep: true }
)

onMounted(() => {
  getInfo()
})
</script>

<style scoped></style>
