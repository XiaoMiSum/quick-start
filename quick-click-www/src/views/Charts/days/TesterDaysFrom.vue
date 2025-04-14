<template>
  <Dialog v-model="_visible" title="修改数据">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="130px"
    >
      <el-form-item label="新增用例数" prop="testcaseTotal">
        <el-input-number
          v-model="formData.testcaseTotal"
          placeholder="请输入新增用例数"
          :step="1"
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="执行用例数" prop="executeTestcaseTotal">
        <el-input-number
          v-model="formData.executeTestcaseTotal"
          placeholder="请输入执行用例数"
          :step="1"
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="新增缺陷数" prop="newBugTotal">
        <el-input-number
          v-model="formData.newBugTotal"
          placeholder="请输入新增缺陷数"
          :step="1"
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="验证缺陷数" prop="validatedBugTotal">
        <el-input-number
          v-model="formData.validatedBugTotal"
          placeholder="请输入验证缺陷数"
          :step="1"
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="关闭缺陷数" prop="closedBugTotal">
        <el-input-number
          v-model="formData.closedBugTotal"
          placeholder="请输入关闭缺陷数"
          :step="1"
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="激活缺陷数" prop="reopenedBugTotal">
        <el-input-number
          v-model="formData.reopenedBugTotal"
          placeholder="请输入激活缺陷数"
          :step="1"
          style="width: 100%"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="_visible = false">取 消</el-button>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">保存 </el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { updateTester } from '@/api/charts/days'

defineOptions({ name: 'TesterDaysForm' })

const _visible = ref(false) // 弹窗的是否展示

const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref<any>({})
const formRules = reactive({
  testcaseTotal: [{ required: true, message: '新增用例数不能为空', trigger: 'blur' }],
  executeTestcaseTotal: [{ required: true, message: '执行用例数不能为空', trigger: 'blur' }],
  newBugTotal: [{ required: true, message: '新增缺陷数不能为空', trigger: 'blur' }],
  validatedBugTotal: [{ required: true, message: '验证缺陷数不能为空', trigger: 'blur' }],
  closedBugTotal: [{ required: true, message: '关闭缺陷数不能为空', trigger: 'blur' }],
  reopenedBugTotal: [{ required: true, message: '激活缺陷数不能为空', trigger: 'blur' }]
})

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (data: any) => {
  _visible.value = true
  resetForm()
  // 修改时，设置数据

  formLoading.value = true
  try {
    formData.value = Object.assign({}, data)
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {}
  formRef.value?.resetFields()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

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
    await updateTester(data)
    // 发送操作成功的事件
    emit('success')
  } finally {
    _visible.value = false
    formLoading.value = false
  }
}
</script>
