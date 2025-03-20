<template>
  <el-table :data="modelValue" highlight-current-row stripe :show-header="false">
    <el-table-column label="版本号" prop="version" show-overflow-tooltip />
    <el-table-column label="迭代名称" prop="name" show-overflow-tooltip />
    <el-table-column label="状态" prop="status">
      <template #default="scope">
        <el-tag type="info" size="small" effect="dark">
          {{ scope.row.status === 0 ? '已归档' : '未归档' }}
        </el-tag>
      </template>
    </el-table-column>
    <el-table-column label="需求明细">
      <template #default="scope">
        <el-button link type="primary" @click="showRequirements(scope.row.requirements)">
          需求明细
        </el-button>
      </template>
    </el-table-column>

    <el-table-column align="center" width="200">
      <template #default="scope">
        <el-tooltip content="编辑" placement="top">
          <el-button link type="primary" @click="openForm('update', scope.row)">
            <Icon icon="ep:edit" />
          </el-button>
        </el-tooltip>
        <el-tooltip content="归档" placement="top">
          <el-button
            :disabled="scope.row.status === 0"
            link
            type="primary"
            @click="handleArchive(scope.row.id)"
          >
            <Icon icon="fa:file-archive-o" />
          </el-button>
        </el-tooltip>
        <el-tooltip content="明细" placement="top">
          <el-button
            :disabled="scope.row.status === 1"
            link
            type="primary"
            @click="openDetail(scope.row.id)"
          >
            <Icon icon="ep:view" />
          </el-button>
        </el-tooltip>
        <el-tooltip content="导出" placement="top">
          <el-button
            :disabled="scope.row.status === 1"
            link
            type="primary"
            @click="handleDownload(scope.row)"
          >
            <Icon icon="ep:download" />
          </el-button>
        </el-tooltip>
        <el-tooltip content="删除" placement="top">
          <el-button
            :disabled="scope.row.status === 0"
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
          >
            <Icon icon="ep:delete" />
          </el-button>
        </el-tooltip>
      </template>
    </el-table-column>
  </el-table>

  <Dialog v-model="_visible" title="需求明细">
    <div v-for="(item, index) in _requirements" :key="index">
      <el-text class="mx-1" type="info">
        {{ item }}
      </el-text>
      <el-divider />
    </div>
  </Dialog>
</template>

<script lang="ts" setup>
defineOptions({ name: 'ProjectArchive' })

defineProps({
  modelValue: {
    required: true,
    type: Array
  }
})

const _requirements = ref<string[]>([])

const _visible = ref(false)

const showRequirements = (requirements: string[]) => {
  _requirements.value = requirements
  _visible.value = true
}

const openForm = (type: String, data: any) => {
  emit('open-form', type, data)
}

const openDetail = (id: String) => {
  emit('open-detail', id)
}

const handleDownload = (data: any) => {
  emit('download', data)
}

const handleDelete = (id: String) => {
  emit('delete', id)
}

const handleArchive = (id: String) => {
  emit('archive', id)
}

const emit = defineEmits(['open-form', 'open-detail', 'download', 'delete', 'archive']) // 定义 click 事件
</script>

<style scoped></style>
