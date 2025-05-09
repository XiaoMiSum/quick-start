<template>
  <Dialog v-model="_visible" :title="title" width="41%">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="110px"
    >
      <el-form-item label="计划名称" prop="title">
        <el-input
          v-model="formData.title"
          maxlength="64"
          placeholder="请输入计划名称"
          show-word-limit
        />
      </el-form-item>
      <el-form-item label="测试阶段" prop="stage">
        <el-select v-model="formData.stage" placeholder="请选择测试阶段" style="width: 100%">
          <el-option
            v-for="item in getDictOptions(DICT_TYPE.QUALITY_TEST_STAGE)"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="执行人" prop="executor">
        <el-select
          filterable
          v-model="formData.executor"
          placeholder="请选择执行人"
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
      <el-form-item label="预计开始时间" prop="expectedStartTime">
        <el-date-picker
          value-format="YYYY-MM-DD HH:mm:ss"
          v-model="formData.expectedStartTime"
          placeholder="请选择预计开始时间"
          type="datetime"
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="预计结束时间" prop="expectedEndTime">
        <el-date-picker
          value-format="YYYY-MM-DD HH:mm:ss"
          v-model="formData.expectedEndTime"
          placeholder="请选择预计结束时间"
          type="datetime"
          style="width: 100%"
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
      <el-button :disabled="formLoading" type="primary" @click="submitForm(false)"
        >保存并关闭
      </el-button>
      <el-button :disabled="formLoading" type="primary" @click="submitForm(true)"
        >保存并关联用例
      </el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/quality/plan'

import { useRouter } from 'vue-router' //1.先在需要跳转的页面引入useRouter

import { getDictOptions, DICT_TYPE } from '@/utils/dictionary'

import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()

import { useGlobalStore } from '@/store/modules/global'
const globalStore = useGlobalStore()

defineOptions({ name: 'PlanForm' })

const { t } = useI18n() // 国际化
const { push } = useRouter() // 路由

const _visible = ref(false) // 弹窗的是否展示
const title = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref<any>({
  id: undefined,
  title: '',
  stage: '',
  executor: userStore.getUser.id,
  expectedStartTime: '',
  expectedEndTime: '',
  memo: ''
})
const formRules = reactive({
  title: [{ required: true, message: '测试计划名称不能为空', trigger: 'blur' }],
  stage: [{ required: true, message: '测试阶段不能为空', trigger: 'blur' }],
  executor: [{ required: true, message: '执行人不能为空', trigger: 'blur' }],
  expectedStartTime: [{ required: true, message: '预计开始时间不能为空', trigger: 'blur' }],
  expectedEndTime: [{ required: true, message: '预计结束时间不能为空', trigger: 'blur' }]
})

const users = ref<any>([])

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
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
  getUsers()
}

/**  获取用户列表 */
const getUsers = async () => {
  users.value = await globalStore.getUsers
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    title: '',
    stage: '',
    projectId: globalStore.getCurrentProject,
    executor: userStore.getUser.id,
    expectedStartTime: '',
    expectedEndTime: '',
    memo: ''
  }
  formRef.value?.resetFields()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调

const submitForm = async (to?: Boolean) => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    if (formType.value === 'create') {
      data.id = await HTTP.addData(data)
    } else {
      await HTTP.updateData(data)
    }
    if (to) {
      push('/quality/test-plan/' + data.id + '/associated-use-cases')
    } else {
      // 发送操作成功的事件
      emit('success')
    }
  } finally {
    _visible.value = false
    formLoading.value = false
  }
}
</script>
