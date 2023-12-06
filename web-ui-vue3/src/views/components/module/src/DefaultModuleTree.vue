<template>
  <ModuleTree
    v-model="dataTree"
    :readonly="readonly"
    @node-click="handleNodeClick"
    @add-node="handleAddNode"
    @edit-node="handleEditNode"
    @remove-node="handleRemoveNode"
  />
  <NodeForm ref="nodeForm" @success="getTree" />
</template>

<script lang="ts" setup>
import ModuleTree from './ModuleTree.vue'
import NodeForm from './NodeForm.vue'
import * as HTTP from '@/api/track/node'
import { handleTree } from '@/utils/tree'

const message = useMessage() // 消息弹窗

defineOptions({ name: 'ModuleTree' })

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
  dataTree.value = []
  const data = await HTTP.getSimple()
  let module: Tree = { id: '0', name: '根节点', children: [] }
  module.children = handleTree(data)
  module.children.splice(0, 0, { id: '-1', name: '未分组用例', children: [] })
  dataTree.value.push(module)
}

/** 新增节点 */
const nodeForm = ref()
const handleAddNode = async () => {
  nodeForm.value.open('create')
}

/** 编辑节点 */
const handleEditNode = async (data: any) => {
  nodeForm.value.open('edit', data.id)
}

/** 删除节点 */
const handleRemoveNode = async (data: any) => {
  await message.delConfirm('确认删除节点「' + data.name + '」? 节点下的用例将移到「未分组用例」')
  await HTTP.removeData(data.id)
  await getTree()
}

/** 处理节点被点击 */
const handleNodeClick = async (row: { [key: string]: any }) => {
  emits('node-click', row)
}
const emits = defineEmits(['node-click'])

/** 初始化 */
onMounted(async () => {
  await getTree()
})
</script>
