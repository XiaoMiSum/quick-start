<template>
  <div class="head-container">
    <el-input v-model="name" class="mb-20px" clearable placeholder="请输入模块名称">
      <template #prefix>
        <Icon icon="ep:search" />
      </template>
    </el-input>
  </div>
  <div class="head-container">
    <el-tree
      ref="treeRef"
      :data="dataTree"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      :props="defaultProps"
      default-expand-all
      highlight-current
      node-key="id"
      @node-click="handleNodeClick"
    />
  </div>
</template>

<script lang="ts" setup>
import { ElTree } from 'element-plus'
import * as HTTP from '@/api/project/module'
import { defaultProps, handleTree } from '@/utils/tree'

defineOptions({ name: 'ModuleTree' })

const name = ref('')
const dataTree = ref<Tree[]>([]) // 树形结构
const treeRef = ref<InstanceType<typeof ElTree>>()

/** 获得模块树 */
const getTree = async () => {
  dataTree.value = []
  const data = await HTTP.getSimple()
  let module: Tree = { id: 0, name: '根模块', children: [] }
  module.children = handleTree(data)
  module.children.splice(0, 0, { id: -1, name: '未分组用例', children: [] })
  dataTree.value.push(module)
}

/** 基于名字过滤 */
const filterNode = (name: string, data: Tree) => {
  if (!name) return true
  return data.name.includes(name)
}

/** 处理部门被点击 */
const handleNodeClick = async (row: { [key: string]: any }) => {
  emits('node-click', row)
}
const emits = defineEmits(['node-click'])

/** 监听name */
watch(name, (val) => {
  treeRef.value!.filter(val)
})

/** 初始化 */
onMounted(async () => {
  await getTree()
})
</script>
