<template>
  <Dialog v-model="_visible" :title="title">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-form-item label="成员" prop="userId">
        <el-select v-model="formData.userId" placeholder="请选择" @change="handleUserChange">
          <el-option
            v-for="item in userList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="岗位" prop="postId">
        <el-select v-model="formData.postId" placeholder="请选择">
          <el-option
            v-for="item in postList"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="_visible = false">取 消</el-button>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/project/member'
import * as UserApi from '@/api/management/system/user'
import * as PostApi from '@/api/management/system/post'

defineOptions({ name: 'MemberForm' })

import { defineProps } from 'vue'

const props = defineProps({
  projectId: String
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const _visible = ref(false) // 弹窗的是否展示
const title = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref<any>({
  id: undefined,
  userId: undefined,
  postId: undefined,
  projectId: props.projectId
})
const formRules = reactive({
  userId: [{ required: true, message: '请选择成员', trigger: 'blur' }],
  postId: [{ required: true, message: '请选择岗位', trigger: 'blur' }]
})

const userList = ref<any>([]) // 用户列表
const postList = ref<any>([]) // 岗位列表

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, data?: any) => {
  _visible.value = true
  title.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (data) {
    formLoading.value = true
    try {
      formData.value = Object.assign({}, data)
    } finally {
      formLoading.value = false
    }
  }
  // 加载用户列表
  userList.value = await UserApi.listSimple()
  // 加载岗位列表
  postList.value = await PostApi.listSimple()
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    userId: undefined,
    postId: undefined,
    projectId: props.projectId
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

const handleUserChange = (value) => {
  formData.value.postId = userList.value.filter((user) => user.value === value)[0].extend?.post
}
</script>
