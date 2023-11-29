<script lang="ts" setup>
import { onMounted } from 'vue'
import { getSimple } from '@/api/project/index.ts'
import { useUserStore } from '@/store/modules/user'

const userStore = useUserStore()

defineOptions({ name: 'ProjectSelector' })

const currentProject = userStore.getDefaultProject || 1
const projects = ref<any>([])
const title = ref('')

const getList = async () => {
  projects.value = await getSimple()
  projects.value.length
  for (let i = 0; i < projects.value.length; i++) {
    const item = projects.value[i]
    if (item.code === currentProject) {
      title.value = item.name
    }
  }
}

const handleChange = async (value: number, name: string) => {
  await userStore.setDefaultProject(value)
  title.value = name
  // location.href = '/index'
}

onMounted(() => {
  getList()
})
</script>

<template>
  <el-dropdown>
    <span class="el-dropdown-link"> {{ '项目：' + title }}</span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item
          v-for="item in projects"
          :key="item.code"
          :disiable="item.disiable"
          @click="handleChange(item.code, item.name)"
        >
          {{ item.name }}
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<style scoped>
.el-dropdown-link {
  display: flex;
  cursor: pointer;
  align-items: center;
}
</style>
