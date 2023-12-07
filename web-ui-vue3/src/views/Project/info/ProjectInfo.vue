<template>
  <Card>
    <template #header>
      <span class="text-16px font-700">{{ modelValue.name }}</span>
      <el-tooltip content="编辑" placement="top">
        <el-button circle plain type="primary" @click="openForm('update', modelValue.id)">
          <Icon icon="ep:edit" />
        </el-button>
      </el-tooltip>
    </template>
    <el-descriptions :column="2" border>
      <el-descriptions-item label="产品经理">
        <el-tag
          v-for="(item, index) in modelValue.productManagers"
          :key="index"
          class="mr-2"
          type="info"
          >{{ item }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="开发人员">
        <el-tag v-for="(item, index) in modelValue.developers" :key="index" class="mr-2" type="info"
          >{{ item }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="测试人员">
        <el-tag v-for="(item, index) in modelValue.testers" :key="index" class="mr-2" type="info"
          >{{ item }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="备注信息"> {{ modelValue.memo }}</el-descriptions-item>
    </el-descriptions>
  </Card>
  <ProjectForm ref="infoFormRef" @success="saveSuccess" />
</template>

<script lang="ts" setup>
import ProjectForm from '@/views/Project/table/ProjectForm.vue'
import { Card } from '@/components/Card'

defineOptions({ name: 'ProjectInfo' })

const props = defineProps({
  modelValue: {
    required: true,
    type: Object
  }
})

const { modelValue } = toRefs(props)

/** 添加/修改操作 */
const infoFormRef = ref()
const openForm = (type: string, id?: number) => {
  infoFormRef.value.open(type, id)
}

const emits = defineEmits(['save-success'])

const saveSuccess = async () => {
  await emits('save-success')
}
</script>

<style scoped></style>
