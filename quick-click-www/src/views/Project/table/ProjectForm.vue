<template>
  <Dialog v-model="_visible" :title="title">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-form-item label="项目名称" prop="title">
        <el-input
          v-model="formData.title"
          maxlength="64"
          placeholder="请输入项目名称"
          show-word-limit
        />
      </el-form-item>
      <el-form-item label="备注" prop="memo">
        <el-input
          v-model="formData.memo"
          maxlength="255"
          placeholder="请输入备注信息"
          show-word-limit
          type="textarea"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="_visible = false">取 消</el-button>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/project'

defineOptions({ name: 'ProjectForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const _visible = ref(false) // 弹窗的是否展示
const title = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref<any>({
  id: undefined,
  title: '',
  memo: ''
})
const formRules = reactive({
  name: [{ required: true, message: '项目名称不能为空', trigger: 'blur' }]
})

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: string) => {
  _visible.value = true
  title.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await HTTP.getData(id)
    } finally {
      formLoading.value = false
    }
  }
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    title: '',
    memo: ''
  }
  formRef.value?.resetFields()
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
    if (formType.value === 'create') {
      await HTTP.addData(data)
      message.success(t('common.createSuccess'))
    } else {
      await HTTP.updateData(data)
      message.success(t('common.updateSuccess'))
    }
    _visible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}
</script>
