<template>
  <NodeTree
    v-model="dataTree"
    :readonly="readonly"
    :props="{
      children: 'children',
      label: 'title',
      value: 'id'
    }"
    @node-click="handleNodeClick"
  />
</template>

<script lang="ts" setup>
import NodeTree from './NodeTree.vue'

import { useGlobalStore } from '@/store/modules/global'

const globalStore = useGlobalStore()

defineOptions({ name: '' })

defineProps({
  readonly: {
    required: false,
    type: Boolean,
    default: true
  }
})

const dataTree = ref<Tree[]>([]) // 树形结构

/** 获得模块树 */
const getTree = async () => {
  await globalStore.setNodes()
  dataTree.value = []
  const data = globalStore.getNodes
  if (data) {
    let node: Tree = { id: '', title: '/', children: [] }
    node.children = data
    dataTree.value.push(node)
  }
}

/** 处理节点被点击 */
const handleNodeClick = async (row: { [key: string]: any }) => {
  emits('node-click', row)
}
const emits = defineEmits(['node-click'])

/** 监听当前项目变化，刷新列表数据 */
watch(
  computed(() => globalStore.getCurrentProject),
  async () => {
    await getTree()
  },
  { immediate: true, deep: true }
)

/** 初始化 */
</script>
