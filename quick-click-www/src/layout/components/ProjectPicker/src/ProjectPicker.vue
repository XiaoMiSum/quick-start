<script lang="ts" setup>
import { useDesign } from '@/hooks/web/useDesign'

import { useGlobalStore } from '@/store/modules/global'

import { onMounted } from 'vue'

const message = useMessage() // 消息弹窗

const globalStore = useGlobalStore()

defineOptions({ name: 'ProjectPicker' })

const { getPrefixCls } = useDesign()

const prefixCls = getPrefixCls('user-info')

const projects = ref<any>([])
const title = ref('')

const handleChange = async (value: string, label: string) => {
  globalStore.setCurrentProject(value)
  title.value = label
}

const init = () => {
  projects.value = globalStore.getProjects
  if (!projects.value) {
    message.alertWarning('没有可用项目，请联系管理员加入项目或添加项目！')
  } else {
    for (let i = 0; i < projects.value.length; i++) {
      const item = projects.value[i]
      if (item.value === globalStore.getCurrentProject) {
        title.value = item.label
      }
    }
  }
}

// 监听项目下拉列表变化
watch(
  computed(() => globalStore.getProjects),
  () => {
    init()
  },
  { immediate: true, deep: true }
)

onMounted(() => {
  init()
})
</script>

<template>
  <ElDropdown :class="prefixCls" class="custom-hover" trigger="click">
    <div class="flex items-center">
      <Icon class="right-5px" icon="ep:promotion" />
      <span class="pl-[5px] text-14px text-[var(--top-header-text-color)] <lg:hidden">
        {{ '项目：' + title }}
      </span>
      <Icon class="left-10px" icon="ep:arrow-down" />
    </div>
    <template #dropdown>
      <ElDropdownMenu>
        <ElDropdownItem
          v-for="item in projects"
          :key="item.value"
          divided
          @click="handleChange(item.value, item.label)"
        >
          <div>{{ item.label }}</div>
        </ElDropdownItem>
      </ElDropdownMenu>
    </template>
  </ElDropdown>
</template>
