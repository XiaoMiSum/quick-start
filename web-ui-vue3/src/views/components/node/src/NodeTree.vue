<template>
  <div class="head-container">
    <el-input v-model="name" class="mb-20px" clearable placeholder="请输入节点名称">
      <template #prefix>
        <Icon icon="ep:search" />
      </template>
      <template #append v-if="!readonly">
        <el-button @click="handleAddNode">
          <Icon icon="ep:plus" />
        </el-button>
      </template>
    </el-input>
  </div>
  <div class="head-container">
    <el-tree
      ref="treeRef"
      :data="modelValue"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      :props="defaultProps"
      highlight-current
      default-expand-all
      node-key="id"
      @node-click="handleNodeClick"
    >
      <template #default="{ node, data }">
        <span class="custom-tree-node">
          <span>{{ node.label }}</span>
          <span>
            <a v-if="!readonly && verify(data.id)" @click="handleEditNode(data)">
              <Icon icon="ep:edit" />
            </a>
            <a v-if="!readonly && verify(data.id)" class="ml-8px" @click="handleRemoveNode(data)">
              <Icon icon="ep:delete" />
            </a>
          </span>
        </span>
      </template>
    </el-tree>
  </div>
</template>

<script lang="ts" setup>
import { ElTree } from 'element-plus'
import { defaultProps } from '@/utils/tree'

defineOptions({ name: 'NodeTree' })

const props = defineProps({
  modelValue: {
    required: true,
    type: Array
  },
  readonly: {
    required: false,
    type: Boolean,
    default: false
  }
})

const { modelValue } = toRefs(props)

const name = ref('')
const treeRef = ref<InstanceType<typeof ElTree>>()

/** 基于名字过滤 */
const filterNode = (name: string, data: Tree) => {
  if (!name) return true
  return data.name.includes(name)
}

const verify = (id: string) => {
  return id && id.length > 10
}

/** 处理节点被点击 */
const handleNodeClick = async (row: { [key: string]: any }) => {
  emits('node-click', row)
}

/** 新增节点 */
const handleAddNode = async () => {
  emits('add-node')
}

/** 编辑节点 */
const handleEditNode = async (data: any) => {
  emits('edit-node', data)
}

/** 删除节点 */
const handleRemoveNode = async (data: any) => {
  emits('remove-node', data)
}

const emits = defineEmits(['node-click', 'add-node', 'remove-node', 'edit-node'])

/** 监听name */
watch(name, (val) => {
  treeRef.value!.filter(val)
})
</script>

<style scoped>
.custom-tree-node {
  display: flex;
  padding-right: 8px;
  font-size: 14px;
  flex: 1;
  align-items: center;
  justify-content: space-between;
}
</style>
