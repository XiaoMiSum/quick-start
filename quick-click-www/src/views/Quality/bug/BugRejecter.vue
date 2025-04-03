<template>
  <Dialog :title="'【拒绝】' + title" v-model="visible" @close="onClose">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-form-item label="拒绝原因" prop="rejectedCause">
        <el-input
          v-model="formData.rejectedCause"
          :autosize="{ minRows: 2, maxRows: 4 }"
          type="textarea"
          maxlength="255"
          show-word-limit
          placeholder="请输入拒绝原因"
        />
      </el-form-item>

      <el-form-item label="指派给" prop="handler">
        <el-select filterable v-model="formData.handler" style="width: 100%">
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
import { reject, getData } from '@/api/quality/bug'

defineProps({
  users: {
    required: true,
    type: Array<any>
  }
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const title = ref('')
const visible = ref(false)
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref<any>({
  id: undefined,
  rejectedCause: undefined,
  handler: undefined
})

defineOptions({ name: 'BugRejecter' })

const formRules = reactive({
  rejectedCause: [{ required: true, message: '拒绝原因不能为空', trigger: 'blur' }],
  handler: [{ required: true, message: '指派处理人不能为空', trigger: 'blur' }]
})

const formRef = ref() // 表单 Ref

const open = async (bug: any) => {
  visible.value = true
  title.value = bug.title
  const data = await getData(bug.id)
  formData.value.id = data.id
  formData.value.handler = data.creatorId
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    rejectedCause: undefined,
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
    await reject(data)
    message.success(t('common.optionSuccess'))
    visible.value = false
  } finally {
    formLoading.value = false
  }
}
</script>
