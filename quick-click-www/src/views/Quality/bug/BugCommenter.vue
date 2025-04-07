<template>
  <ContentWrap>
    <el-text tag="b"> 评论记录 </el-text>
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
          <el-text type="info"> {{ ' 于 ' + row.createTime + '评论' }} </el-text>
        </template>
      </el-table-column>
    </el-table>
  </ContentWrap>

  <Dialog title="【评论】" v-model="visible" @close="onClose" width="900px">
    <Editor ref="bugComment" v-model="formData.content" height="300px" />

    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :disabled="formLoading || !formData.content" type="primary" @click="submitForm">
        确 定
      </el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { addComment } from '@/api/quality/bug'

import { Editor, FulltextDisplay } from '@/components/Editor'

defineProps({
  modelValue: {
    type: Array,
    requried: true
  },
  readonly: {
    type: Boolean,
    default: false
  }
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const visible = ref(false)
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formData = ref<any>({})

defineOptions({ name: 'BugCommenter' })

const open = async (bugId: string) => {
  visible.value = true
  formData.value.bugId = bugId
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 重置表单 */
const resetForm = () => {
  formData.value = {}
}

const onClose = () => {
  resetForm()
  emit('success')
}

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调

const submitForm = async () => {
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value
    await addComment(data)
    message.success(t('common.optionSuccess'))
    visible.value = false
  } finally {
    formLoading.value = false
  }
}
</script>
