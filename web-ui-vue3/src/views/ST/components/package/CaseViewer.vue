<template>
  <Dialog v-model="visible" :title="data.name" width="80%" @close="close">
    <ContentWrap>
      <!-- 搜索工作栏 -->
    </ContentWrap>

    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="loading" type="primary">确 定</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
const props = defineProps({
  source: {
    required: true,
    type: String
  }
})
const { source } = toRefs(props)

const visible = ref(false)
const data = ref<any>({})
const loading = ref(false)

/** 打开弹窗 */
const open = async (obj: any) => {
  visible.value = true
  data.value = { ...obj }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const emit = defineEmits(['close']) // 定义 close 事件，用于操作成功后的回调
const close = () => {
  emit('close')
}
</script>

<style scoped></style>
