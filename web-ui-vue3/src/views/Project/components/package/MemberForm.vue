<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="80px"
    >
      <el-form-item label="项目成员" prop="userId">
        <el-select
          v-model="formData.userIds"
          clearable
          multiple
          placeholder="请选择项目成员"
          style="width: 100%"
        >
          <el-option v-for="item in userList" :key="item.id" :label="item.name" :value="item.id" />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import * as HTTP from '@/api/project/member'
import * as UserApi from '@/api/system/user'

defineOptions({ name: 'SystemDeptForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  userIds: undefined
})
const formRules = reactive({
  userIds: [{ required: true, message: '项目成员不能为空', trigger: 'blur' }]
})
const formRef = ref() // 表单 Ref
const userList = ref<UserApi.UserVO[]>([]) // 用户列表

/** 打开弹窗 */
const open = async (type: string) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 获得用户列表
  userList.value = await UserApi.listSimple()
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
    await HTTP.addData(data)
    message.success(t('common.createSuccess'))
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
    userIds: undefined
  }
  formRef.value?.resetFields()
}
</script>
