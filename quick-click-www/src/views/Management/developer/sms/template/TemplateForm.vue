<script lang="ts" setup>
import * as HTTP from '@/api/management/developer/sms/template'
import { toRefs } from 'vue'

const message = useMessage()
const { t } = useI18n()

const props = defineProps({
  options: {
    type: Array<any>,
    default: ref<any>([])
  }
})

const { options } = toRefs(props)

const visible = ref(false)
const formLoading = ref(false)
const title = ref('')
const formType = ref('')
const formData = ref<any>({})
const rules = ref<any>({
  status: [{ required: true, message: '开启状态不能为空', trigger: 'blur' }],
  code: [{ required: true, message: '模板编码不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '模板名称不能为空', trigger: 'blur' }],
  content: [{ required: true, message: '模板内容不能为空', trigger: 'blur' }],
  apiTemplateId: [{ required: true, message: '短信 API 的模板编号不能为空', trigger: 'blur' }],
  channelId: [{ required: true, message: '短信渠道编号不能为空', trigger: 'change' }]
})

const open = async (type: string, id?: number) => {
  visible.value = true
  title.value = t('action.' + type)
  formType.value = type
  resetForm()
  if (id) {
    formLoading.value = true
    try {
      formData.value = await HTTP.getData(id)
    } finally {
      formLoading.value = false
    }
  }
}

/** 重置表单 */
const formRef = ref()
const resetForm = () => {
  formData.value = {}
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
    const data = formData.value
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
    <el-form ref="formRef" :model="formData" :rules="rules" label-width="100px">
      <el-form-item label="短信渠道" prop="channelId">
        <el-select v-model="formData.channelId" placeholder="请选择短信渠道" style="width: 100%">
          <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.signature"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="模板编号" prop="code">
        <el-input v-model="formData.code" placeholder="请输入模板编号" />
      </el-form-item>
      <el-form-item label="模板名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入模板名称" />
      </el-form-item>
      <el-form-item label="模板内容" prop="content">
        <el-input v-model="formData.content" placeholder="请输入模板内容" type="textarea" />
      </el-form-item>
      <el-form-item label="apiTemplateId" prop="apiTemplateId">
        <el-input v-model="formData.apiTemplateId" placeholder="请输入短信 API 的模板编号" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<style lang="scss" scoped></style>
