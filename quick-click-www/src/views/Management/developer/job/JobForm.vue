<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="120px"
    >
      <el-form-item label="任务名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入任务名称" />
      </el-form-item>
      <el-form-item label="处理器的名字" prop="handlerName">
        <el-input
          v-model="formData.handlerName"
          :readonly="formData.id !== undefined"
          placeholder="请输入处理器的名字"
        />
      </el-form-item>
      <el-form-item label="处理器的参数" prop="handlerParam">
        <el-input v-model="formData.handlerParam" placeholder="请输入处理器的参数" />
      </el-form-item>
      <el-form-item label="CRON 表达式" prop="cronExpression">
        <crontab
          :modelValue="formData.cronExpression"
          @update:model-value="formData.cronExpression = $event"
        />
      </el-form-item>
      <el-form-item label="重试次数" prop="retryCount">
        <el-input
          v-model="formData.retryCount"
          placeholder="请输入重试次数。设置为 0 时，不进行重试"
        />
      </el-form-item>
      <el-form-item label="重试间隔" prop="retryInterval">
        <el-input
          v-model="formData.retryInterval"
          placeholder="请输入重试间隔，单位：毫秒。设置为 0 时，无需间隔"
        />
      </el-form-item>
      <el-form-item label="监控超时时间" prop="monitorTimeout">
        <el-input v-model="formData.monitorTimeout" placeholder="请输入监控超时时间，单位：毫秒" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button :loading="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import * as JobApi from '@/api/management/developer/job'

defineOptions({ name: 'JobForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  name: '',
  handlerName: '',
  handlerParam: '',
  cronExpression: '',
  retryCount: '',
  retryInterval: '',
  monitorTimeout: ''
})
const formRules = reactive({
  name: [{ required: true, message: '任务名称不能为空', trigger: 'blur' }],
  handlerName: [{ required: true, message: '处理器的名字不能为空', trigger: 'blur' }],
  cronExpression: [{ required: true, message: 'CRON 表达式不能为空', trigger: 'blur' }],
  retryCount: [{ required: true, message: '重试次数不能为空', trigger: 'blur' }],
  retryInterval: [{ required: true, message: '重试间隔不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await JobApi.getJob(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

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
    console.log(formData.value)
    console.log(data)
    if (formType.value === 'create') {
      await JobApi.addJob(data)
      message.success(t('common.createSuccess'))
    } else {
      await JobApi.updateJob(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: '',
    handlerName: '',
    handlerParam: '',
    cronExpression: '',
    retryCount: '',
    retryInterval: '',
    monitorTimeout: ''
  }
  formRef.value?.resetFields()
}
</script>
