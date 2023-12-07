<template>
  <el-table :data="modelValue" highlight-current-row stripe :show-header="false">
    <el-table-column>
      <template #default="scope">
        <span>
          {{
            formatDate(scope.row.createTime) +
            ' ' +
            scope.row.creator +
            ' 创建归档：' +
            scope.row.name
          }}
        </span>
      </template>
    </el-table-column>

    <el-table-column align="center" width="200">
      <template #default="scope">
        <el-tooltip content="编辑" placement="top">
          <el-button circle plain type="primary" @click="openForm('update', scope.row)">
            <Icon icon="ep:edit" />
          </el-button>
        </el-tooltip>
        <el-tooltip content="明细" placement="top">
          <el-button circle plain type="primary" @click="openDetail(scope.row.id)">
            <Icon icon="ep:view" />
          </el-button>
        </el-tooltip>
        <el-tooltip content="导出" placement="top">
          <el-button circle plain type="primary" @click="handleDownload(scope.row)">
            <Icon icon="ep:download" />
          </el-button>
        </el-tooltip>
        <el-tooltip content="删除" placement="top">
          <el-button circle plain type="danger" @click="handleDelete(scope.row.id)">
            <Icon icon="ep:delete" />
          </el-button>
        </el-tooltip>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
import { formatDate } from '@/utils/formatTime'

defineOptions({ name: 'ProjectArchive' })

defineProps({
  modelValue: {
    required: true,
    type: Array
  }
})

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

const emit = defineEmits(['open-form', 'open-detail', 'download', 'delete']) // 定义 click 事件
</script>

<style scoped></style>
