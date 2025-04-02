<template>
  <Dialog title="批量指派" v-model="visible" @close="onClose">
    <el-form ref="formRef" v-loading="formLoading" :model="formData" label-width="100px">
      <el-form-item label="处理人" prop="handler">
        <el-select
          filterable
          v-model="formData.handler"
          placeholder="请选择处理人"
          style="width: 100%"
        >
          <el-option
            v-for="item in users"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { assign } from '@/api/quality/bug'

defineProps({
  users: {
    required: true,
    type: Array<any>
  }
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const visible = ref(false)
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref<any>({
  ids: undefined,
  handler: undefined
})

defineOptions({ name: 'BatchAssign' })

const formRef = ref() // 表单 Ref

const open = (ids: string[]) => {
  visible.value = true
  formData.value.ids = ids
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    ids: undefined,
    handler: undefined
  }
  formRef.value?.resetFields()
}

const onClose = () => {
  resetForm()
  emit('success')
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    await assign(data)
    message.success(t('common.updateSuccess'))
    visible.value = false
  } finally {
    formLoading.value = false
  }
}
</script>
