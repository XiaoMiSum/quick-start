<script lang="ts" setup>
import * as HTTP from '@/api/management/developer/sms/channel'

const message = useMessage()
const { t } = useI18n()

const visible = ref(false)
const formLoading = ref(false)
const title = ref('')
const formType = ref('')
const form = ref<any>({})
const rules = ref<any>({
  signature: [{ required: true, message: '短信签名不能为空', trigger: 'blur' }],
  code: [{ required: true, message: '渠道编码不能为空', trigger: 'blur' }],
  apiKey: [{ required: true, message: '短信 API 的账号不能为空', trigger: 'blur' }]
})

const open = async (type: string, id?: number) => {
  visible.value = true
  title.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (id) {
    formLoading.value = true
    try {
      form.value = await HTTP.getData(id)
    } finally {
      formLoading.value = false
    }
  }
}

/** 重置表单 */
const formRef = ref()
const resetForm = () => {
  form.value = {}
  formRef.value?.resetFields()
}

/** 提交按钮 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = form.value
    if (formType.value === 'create') {
      await HTTP.addData(data)
      message.success(t('common.createSuccess'))
    } else {
      await HTTP.updateData(data)
      message.success(t('common.updateSuccess'))
    }
    visible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

defineExpose({ open })
</script>

<template>
  <!-- 对话框(添加 / 修改) -->
  <Dialog v-model="visible" :title="title">
    <el-form ref="formRef" :model="form" :rules="rules" label-width="130px">
      <el-form-item label="短信签名" prop="signature">
        <el-input v-model="form.signature" placeholder="请输入短信签名" />
      </el-form-item>
      <el-form-item label="渠道编码" prop="code">
        <el-input v-model="form.code" placeholder="请输入短信编码" />
      </el-form-item>
      <el-form-item label="短信 API 的账号" prop="apiKey">
        <el-input v-model="form.apiKey" placeholder="请输入短信 API 的账号" />
      </el-form-item>
      <el-form-item label="短信 API 的密钥" prop="apiSecret">
        <el-input v-model="form.apiSecret" placeholder="请输入短信 API 的密钥" />
      </el-form-item>
      <el-form-item label="短信发送回调 URL" prop="callbackUrl">
        <el-input v-model="form.callbackUrl" placeholder="请输入短信发送回调 URL" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<style lang="scss" scoped></style>
