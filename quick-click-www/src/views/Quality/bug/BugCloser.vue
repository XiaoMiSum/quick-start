<template>
  <Dialog :title="'【关闭】' + title" v-model="visible" @close="onClose" width="1200px">
    <el-form ref="formRef" :rules="formRules" :model="formData" label-width="100px">
      <div v-if="formData.status === 'Rejected'">
        <el-form-item label="拒绝时间">
          <user-tag :value="formData.rejectedUser" type="danger" />
          <el-text type="danger"> {{ ' 于 ' + formData.rejectedTime }}</el-text>
        </el-form-item>
        <el-form-item label="拒绝原因">
          <el-text type="info"> {{ formData.rejectedCause }}</el-text>
        </el-form-item>
      </div>

      <div v-else>
        <el-form-item label="修复时间">
          <user-tag :value="formData.fixer" type="danger" />
          <el-text type="danger"> {{ '于' + formData.fixedTime }}</el-text>
        </el-form-item>
        <el-form-item label="产生原因">
          <ones-tag :value="formData.cause" :type="DICT_TYPE.QUALITY_BUG_FIX_CAUSE" />
        </el-form-item>
        <el-form-item label="详细描述" prop="causeDetailed">
          <el-text type="info"> {{ formData.causeDetailed }}</el-text>
        </el-form-item>
        <el-form-item label="解决方案" prop="solution">
          <el-text type="info"> {{ formData.solution }}</el-text>
        </el-form-item>
      </div>

      <el-form-item label="验证时长" prop="duration">
        <el-input-number
          v-model="formData.duration"
          :precision="0"
          placeholder="请输入验证时长（分钟）"
          :step="1"
          style="width: 100%"
        />
      </el-form-item>
    </el-form>

    <Editor ref="bugExecRecord" v-model="comment" height="200px" />
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
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

const title = ref('')
const visible = ref(false)
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref<any>({
  id: undefined,
  cause: '',
  rootCause: '',
  solution: '',
  handler: undefined,
  fixedTime: undefined,
  duration: undefined
})

const comment = ref('')

const formRules = reactive({
  duration: [{ required: true, message: '验证时长不能为空', trigger: 'blur' }]
})

defineOptions({ name: 'BugCloser' })

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
    cause: '',
    rootCause: '',
    solution: '',
    handler: undefined,
    fixedTime: undefined,
    duration: undefined
  }
  comment.value = ''
  formRef.value?.resetFields()
}

const onClose = () => {
  resetForm()
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
    await close({
      comment: comment.value,
      id: formData.value.id,
      duration: formData.value.duration
    })
    message.success(t('common.optionSuccess'))
    emit('success')
    visible.value = false
  } finally {
    formLoading.value = false
  }
}
</script>
