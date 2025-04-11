<template>
  <ContentWrap>
    <el-text tag="b"> 操作记录 </el-text>
    <el-table :data="modelValue" style="margin-top: 10px" :show-header="false" empty-text="">
      <el-table-column type="expand">
        <template #default="{ row }">
          <FulltextDisplay
            :rich-text="row.content"
            :style="{ maxHeight: '200px', minHeight: '50px', width: '1000px', overflow: 'auto' }"
          />
        </template>
      </el-table-column>
      <el-table-column>
        <template #default="{ row }">
          <user-tag :value="row.userId" />
          <el-text type="info"> {{ ' 于 ' + row.createTime }} </el-text>
          <ones-tag v-if="row.operation" :value="row.operation" :type="dictCode" />
          <el-text v-else type="info"> 评论</el-text>
        </template>
      </el-table-column>
    </el-table>
  </ContentWrap>

  <Dialog title="【评论】" v-model="visible" @close="onClose" width="900px">
    <Editor ref="bugExecRecord" v-model="formData.content" height="300px" />

    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :disabled="formLoading || !formData.content" type="primary" @click="submitForm">
        确 定
      </el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { Editor, FulltextDisplay } from '@/components/Editor'

const props = defineProps({
  modelValue: {
    type: Array,
    requried: true
  },
  dictCode: {
    type: String,
    required: true
  },
  addRecord: {
    type: Function,
    required: false
  },
  filedObject: {
    type: Object,
    required: false,
    default: () => {}
  }
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const visible = ref(false)
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref<any>({})

defineOptions({ name: 'Record' })

const open = async (dataId: any, key: string) => {
  visible.value = true
  formData.value[key] = dataId
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {}
}

const onClose = () => {
  resetForm()
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调

const submitForm = async () => {
  if (!props.addRecord) {
    message.error('请设置Function: addRecord')
    return
  }
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    if (props.filedObject) {
      Object.keys(props.filedObject).forEach((key) => {
        data[key] = props.filedObject[key]
      })
    }
    await props.addRecord(data)
    message.success(t('common.optionSuccess'))
    emit('success')
    visible.value = false
  } finally {
    formLoading.value = false
  }
}
</script>
