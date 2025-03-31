<template>
  <Dialog :title="'【确认】' + title" v-model="visible" @close="onClose">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-form-item label="修复人">
        <user-tag :value="formData.fixer" type="danger" />
        <el-text type="info" class="ml-10px"> 于</el-text>
        <el-text type="danger" class="ml-5px"> {{ formData.fixedTime }}</el-text>
        <el-text type="info" class="ml-5px"> 修复</el-text>
      </el-form-item>
      <el-form-item label="产生原因">
        <ones-tag :value="formData.cause" :type="DICT_TYPE.QUALITY_BUG_FIX_CAUSE" />
      </el-form-item>
      <el-form-item label="详细描述" prop="rootCause">
        <el-text type="info" class="ml-5px"> {{ formData.rootCause }}</el-text>
      </el-form-item>
      <el-form-item label="解决方案" prop="solution">
        <el-text type="info" class="ml-5px"> {{ formData.solution }}</el-text>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="danger" @click="visible = false">拒 绝</el-button>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 认</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { close, getData } from '@/api/quality/bug'

import { DICT_TYPE } from '@/utils/dictionary'

import { formatDate } from '@/utils/formatTime'

defineProps({
  users: {
    required: true,
    type: Array<any>
  }
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const title = ref('缺陷修复')
const visible = ref(false)
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref<any>({
  id: undefined,
  cause: undefined,
  rootCause: undefined,
  solution: undefined,
  handler: undefined,
  fixedTime: undefined
})

defineOptions({ name: 'BugCloser' })

const formRules = reactive({
  cause: [{ required: true, message: '产生原因不能为空', trigger: 'blur' }],
  rootCause: [{ required: true, message: '详细描述不能为空', trigger: 'blur' }],
  solution: [{ required: true, message: '解决方案不能为空', trigger: 'blur' }],
  handler: [{ required: true, message: '指派处理人不能为空', trigger: 'blur' }],
  fixedTime: [{ required: true, message: '修复时间不能为空', trigger: 'blur' }]
})

const formRef = ref() // 表单 Ref

const open = async (bug: any) => {
  visible.value = true
  title.value = bug.title
  formData.value = await getData(bug.id)
  formData.value.fixedTime = formatDate(new Date())
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    cause: undefined,
    rootCause: undefined,
    solution: undefined,
    handler: undefined,
    fixedTime: undefined
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
    await close(data.id)
    message.success(t('common.updateSuccess'))
    visible.value = false
  } finally {
    formLoading.value = false
  }
}
</script>
