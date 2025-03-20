<script lang="ts" setup>
import * as HTTP from '@/api/management/developer/sms/template'

const message = useMessage()

const visible = ref(false)
const formLoading = ref(false)
const formData = ref<any>({
  content: '',
  params: [],
  mobile: '',
  templateCode: '',
  templateParams: new Map()
})
const rules = ref<any>({
  mobile: [{ required: true, message: '手机不能为空', trigger: 'blur' }],
  templateCode: [{ required: true, message: '模版编码不能为空', trigger: 'blur' }],
  templateParams: {}
})

const open = async (id: number) => {
  visible.value = true
  resetForm()
  // 设置数据
  formLoading.value = true
  try {
    const data = await HTTP.getData(id)
    // 设置动态表单
    formData.value.content = data.content
    formData.value.params = data.params
    formData.value.templateCode = data.code
    formData.value.templateParams = data.params.reduce((obj, item) => {
      obj[item] = '' // 给每个动态属性赋值，避免无法读取
      return obj
    }, {})
    rules.templateParams = data.params.reduce((obj, item) => {
      obj[item] = { required: true, message: '参数 ' + item + ' 不能为空', trigger: 'blur' }
      return obj
    }, {})
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const formRef = ref()
const resetForm = () => {
  formData.value = {
    content: '',
    params: [],
    mobile: '',
    templateCode: '',
    templateParams: new Map()
  }
  formRef.value?.resetFields()
}

/** 提交按钮 */
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    await HTTP.sendData(data)
    message.success('发送成功')
    visible.value = false
  } finally {
    formLoading.value = false
  }
}

defineExpose({ open })
</script>

<template>
  <!-- 对话框(发送短信) -->
  <Dialog v-model="visible" title="测试发送短信">
    <el-form ref="formRef" :model="formData" :rules="rules" label-width="140px">
      <el-form-item label="模板内容" prop="content">
        <el-input
          v-model="formData.content"
          placeholder="请输入模板内容"
          readonly
          type="textarea"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="formData.mobile" placeholder="请输入手机号" />
      </el-form-item>
      <el-form-item
        v-for="param in formData.params"
        :key="param"
        :label="'参数 {' + param + '}'"
        :prop="'templateParams.' + param"
      >
        <el-input v-model="formData.params[param]" :placeholder="'请输入 ' + param + ' 参数'" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<style lang="scss" scoped></style>
