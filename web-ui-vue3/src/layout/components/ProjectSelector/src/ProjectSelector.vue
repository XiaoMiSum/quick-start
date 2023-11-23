<script lang="ts" setup>
import { onMounted } from 'vue'
import { getSimple } from '@/api/project/index.ts'
import { useUserStore } from '@/store/modules/user'

const userStore = useUserStore()

defineOptions({ name: 'ProjectSelector' })

const currentProject = userStore.getDefaultProject || 1
const projects = ref<any>([])

const getList = async () => {
  projects.value = await getSimple()
}

const handleChange = async (value: number) => {
  await userStore.setDefaultProject(value)
  location.href = '/index'
}

onMounted(() => {
  getList()
})
</script>

<template>
  <el-select
    v-model="currentProject"
    class="m-2"
    placeholder="Select"
    size="large"
    @change="handleChange"
  >
    <el-option
      v-for="item in projects"
      :key="item.code"
      :disiable="item.disiable"
      :label="item.name"
      :value="item.code"
    />
  </el-select>
</template>
