<template>
  <Dialog v-model="_visible" :title="title">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="80px"
    >
      <el-form-item label="上级模块" prop="parentId">
        <el-tree-select
          v-model="formData.parentId"
          :data="dataTree"
          :props="defaultProps"
          check-strictly
          default-expand-all
          placeholder="请选择上级模块"
          style="width: 100%"
          value-key="deptId"
        />
      </el-form-item>
      <el-form-item label="模块名称" prop="name">
        <el-input v-model="formData.name" placeholder="请输入模块名称" />
      </el-form-item>
      <el-form-item label="显示排序" prop="sort">
        <el-input-number v-model="formData.sort" :min="0" controls-position="right" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="_visible = false">取 消</el-button>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { defaultProps } from '@/utils/tree'
import * as HTTP from '@/api/track/node'

import { handleTree } from '@/utils/tree'

defineOptions({ name: 'ProjectForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const _visible = ref(false) // 弹窗的是否展示
const title = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref<any>({
  id: undefined,
  name: '',
  parentId: 0,
  sort: 0,
  memo: ''
})
const formRules = reactive({
  parentId: [{ required: true, message: '上级模块不能为空', trigger: 'blur' }],
  name: [{ required: true, message: '项目名称不能为空', trigger: 'blur' }]
})

const dataTree = ref() // 树形结构

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
  await getTree()
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    name: '',
    parentId: '0',
    sort: 0,
    memo: ''
  }
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

/** 获得模块树 */
const getTree = async () => {
  dataTree.value = []
  const data = await HTTP.getSimple()
  let module: Tree = { id: '0', name: '根节点', children: [] }
  module.children = handleTree(data)
  dataTree.value.push(module)
}
</script>
