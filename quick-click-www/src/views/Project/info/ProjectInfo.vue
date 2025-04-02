<template>
  <Card>
    <template #header>
      <span class="text-16px font-700"> {{ modelValue.title }}</span>
      <el-button
        v-hasPermi="['project:list:update']"
        circle
        text
        type="primary"
        @click="openForm('update', modelValue.id)"
      >
        编辑
      </el-button>
    </template>
    <div>
      <span> {{ modelValue.memo || '暂无描述' }} </span>
    </div>
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
