<template>
  <Dialog :title="title" v-model="visible" @close="onClose">
    <el-upload
      class="upload-demo"
      :drag="true"
      :accept="accept"
      :http-request="httpRequest"
      :on-change="onChange"
      :auto-upload="true"
    >
      <Icon icon="ep:upload-filled" />

      <div class="el-upload__text"> 移动文件到此处 或 <em> 点击上传</em> </div>
    </el-upload>

    <div class="el-upload__text">
      批量导入需按指定模板 <el-button link type="primary" @click="download">点击下载模板</el-button>
    </div>
  </Dialog>
</template>

<script setup lang="ts">
const props = defineProps({
  title: {
    required: false,
    type: String,
    default: '上传'
  },
  accept: {
    required: false,
    type: String,
    default: 'xlsx,xls,txt'
  }
})

const { title, accept } = toRefs(props)

const file = ref() // 待上传的文件

const visible = ref(false)

const open = () => {
  visible.value = true
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const onChange = (uploadFile) => {
  file.value = uploadFile
}

const emit = defineEmits(['close', 'upload', 'download']) // 定义事件，用于回调父组件

const onClose = () => {
  visible.value = false
  emit('close')
}

const httpRequest = () => {
  const formData = new FormData()
  formData.append('file', file.value.raw)
  emit('upload', formData)
}

const download = () => {
  emit('download', true)
}
</script>
