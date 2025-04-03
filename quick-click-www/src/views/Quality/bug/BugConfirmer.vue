<template>
  <Dialog :title="'【确认】' + title" v-model="visible" @close="onClose" width="1200px">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-form-item label="优先级" prop="priority">
        <el-select v-model="formData.priority" style="width: 100%">
          <el-option
            v-for="item in getDictOptions(DICT_TYPE.QUALITY_TESTCASE_PRIORITY)"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="责任人" prop="supervisor">
        <el-select
          filterable
          v-model="formData.supervisor"
          placeholder="请选择责任人"
          style="width: 100%"
        >
          <el-option
            v-for="item in users"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处理人" prop="handler">
        <el-select
          filterable
          v-model="formData.handler"
          placeholder="请选择处理人"
          style="width: 100%"
          @change="changeHandler"
        >
          <el-option
            v-for="item in users"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
    </el-form>

    <Editor ref="bugComment" v-model="formData.comment" height="300px" />
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :disabled="formLoading" type="primary" @click="submitForm"> 确 定 </el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { confirm } from '@/api/quality/bug'

import { DICT_TYPE, getDictOptions } from '@/utils/dictionary'

import { formatDate } from '@/utils/formatTime'

defineProps({
  users: {
    required: true,
    type: Array<any>
  }
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗
const title = ref('确认')
const visible = ref(false)
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref<any>({})

defineOptions({ name: 'BugConfirmer' })

const formRules = reactive({
  priority: [{ required: true, message: '优先级不能为空', trigger: 'blur' }],
  supervisor: [{ required: true, message: '责任人不能为空', trigger: 'blur' }],
  handler: [{ required: true, message: '指派处理人不能为空', trigger: 'blur' }],
  fixedTime: [{ required: true, message: '修复时间不能为空', trigger: 'blur' }]
})

const formRef = ref() // 表单 Ref

const open = async (bug: any) => {
  visible.value = true
  title.value = bug.title
  formData.value = Object.assign({}, bug)
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {}
  formRef.value?.resetFields()
}

const changeHandler = () => {
  formData.value.assignedTime = formatDate(new Date())
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
    await confirm({
      id: data.id,
      assignedTime: data.assignedTime,
      priority: data.priority,
      supervisor: data.supervisor,
      handler: data.handler,
      comment: data.comment
    })
    message.success(t('common.optionSuccess'))
    visible.value = false
  } finally {
    formLoading.value = false
  }
}
</script>
