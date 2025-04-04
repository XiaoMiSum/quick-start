<template>
  <Dialog v-model="_visible" :title="'批量' + title" @close="close">
    <el-form ref="formRef" v-loading="formLoading" :model="formData" label-width="100px">
      <el-form-item label="">
        <el-text type="danger"> * 只需设置待修改的属性 </el-text>
      </el-form-item>
      <el-form-item label="所属模块" prop="nodeId">
        <el-tree-select
          filterable
          v-model="formData.nodeId"
          :data="nodes"
          :props="defaultProps2"
          check-strictly
          default-expand-all
          placeholder="请选择所属模块"
          style="width: 100%"
          value-key="nodeId"
        />
      </el-form-item>

      <el-form-item label="优先级" prop="priority">
        <el-select v-model="formData.priority" placeholder="请选择优先级" style="width: 100%">
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
      <el-form-item label="前端开发" prop="frontendDeveloper">
        <el-select
          filterable
          v-model="formData.frontendDeveloper"
          placeholder="请选择前端开发"
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

      <el-form-item label="后端开发" prop="backendDeveloper">
        <el-select
          filterable
          v-model="formData.backendDeveloper"
          placeholder="请选择后端开发"
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
    </el-form>
    <template #footer>
      <el-button @click="_visible = false">取 消</el-button>
      <el-button :disabled="formLoading || !button" type="primary" @click="submitForm">
        确 定
      </el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/quality/testcase'

import { defaultProps2 } from '@/utils/tree'
import { DICT_TYPE, getDictOptions } from '@/utils/dictionary'

defineOptions({ name: 'CaseBatchEditor' })

defineProps({
  nodes: {
    required: true,
    type: Array<any>
  },
  users: {
    required: true,
    type: Array<any>
  }
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const _visible = ref(false) // 弹窗的是否展示
const title = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref<any>({
  ids: undefined,
  nodeId: undefined,
  priority: undefined,
  supervisor: undefined,
  frontendDeveloper: undefined,
  backendDeveloper: undefined
})

const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (ids?: string[]) => {
  _visible.value = true
  title.value = t('action.update')
  formData.value.ids = ids
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    ids: undefined,
    nodeId: undefined,
    priority: undefined,
    supervisor: undefined,
    frontendDeveloper: undefined,
    backendDeveloper: undefined
  }
  formRef.value?.resetFields()
}
const button = computed(
  () =>
    formData.value.nodeId ||
    formData.value.priority ||
    formData.value.supervisor ||
    formData.value.frontendDeveloper ||
    formData.value.backendDeveloper
)

const close = () => {
  resetForm()
  // 发送操作成功的事件
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
    await HTTP.batchUpdate(data)
    message.success(t('common.updateSuccess'))
    _visible.value = false
  } finally {
    formLoading.value = false
  }
}
</script>
