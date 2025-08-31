<template>
  <Dialog v-model="_visible" :title="title">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="100px"
    >
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="formData.title"
          maxlength="64"
          placeholder="请输入标题"
          show-word-limit
        />
      </el-form-item>

      <el-form-item label="详细描述" prop="description">
        <el-input
          v-model="formData.description"
          :autosize="{ minRows: 3, maxRows: 6 }"
          maxlength="255"
          placeholder="请输入详细描述"
          show-word-limit
          type="textarea"
        />
      </el-form-item>

      <el-form-item label="所属项目" prop="projectId">
        <el-select
          v-model="formData.projectId"
          class="!w-full"
          clearable
          placeholder="请选择所属项目"
          @change="handleProjectChange"
        >
          <el-option
            v-for="project in projectList"
            :key="project.id"
            :label="project.title"
            :value="project.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="所属模块" prop="moduleId">
        <el-select
          v-model="formData.moduleId"
          class="!w-full"
          clearable
          placeholder="请选择所属模块"
        >
          <el-option
            v-for="module in moduleList"
            :key="module.id"
            :label="module.title"
            :value="module.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="原型地址" prop="prototypeUrl">
        <el-input
          v-model="formData.prototypeUrl"
          maxlength="255"
          placeholder="请输入原型地址"
          show-word-limit
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
import { ref, reactive, defineExpose, defineEmits, onMounted } from 'vue'
import * as RequirementApi from '@/api/project/requirement'
import * as ProjectApi from '@/api/project'
import * as NodeApi from '@/api/project/node'
import { useI18n } from '@/hooks/web/useI18n'
import { useMessage } from '@/hooks/web/useMessage'

defineOptions({ name: 'RequirementForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const _visible = ref(false) // 弹窗的是否展示
const title = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref<any>({
  id: undefined,
  title: '',
  description: '',
  projectId: '',
  moduleId: '',
  prototypeUrl: ''
})
const formRules = reactive({
  title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
  projectId: [{ required: true, message: '所属项目不能为空', trigger: 'blur' }]
})

// 项目和模块列表
const projectList = ref<any>([])
const moduleList = ref<any>([])

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: string) => {
  _visible.value = true
  title.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 获取项目列表
  await getProjectList()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      const res = await RequirementApi.getRequirement(id)
      formData.value = res
      // 获取模块列表
      if (res.projectId) {
        await getModuleList(res.projectId)
      }
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
    description: '',
    projectId: '',
    moduleId: '',
    prototypeUrl: ''
  }
  formRef.value?.resetFields()
  // 清空模块列表
  moduleList.value = []
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
      await RequirementApi.createRequirement(data)
      message.success(t('common.createSuccess'))
    } else {
      await RequirementApi.updateRequirement(data)
      message.success(t('common.updateSuccess'))
    }
    _visible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

// 获取项目列表
const getProjectList = async () => {
  try {
    const data = await ProjectApi.getSimpleProjects()
    projectList.value = data
  } catch (err) {
    console.error('获取项目列表失败', err)
  }
}

// 获取模块列表
const getModuleList = async (projectId: string) => {
  try {
    const data = await NodeApi.getProjectNodeList(projectId)
    moduleList.value = data
  } catch (err) {
    console.error('获取模块列表失败', err)
  }
}

// 项目变更时，更新模块列表
const handleProjectChange = async (projectId: string) => {
  if (projectId) {
    await getModuleList(projectId)
  } else {
    moduleList.value = []
  }
  // 清空已选择的模块
  formData.value.moduleId = ''
}

/** 初始化 **/
onMounted(async () => {
  await getProjectList()
})
</script>

<style scoped></style>