<template>
  <Dialog
    v-model="visible"
    :tag="status"
    :title="data.name"
    :enums="enums"
    width="80%"
    @close="close"
  >
    <InfoViewer v-model="data" :show-actual="false" v-loading="loading" />

    <template #footer>
      <el-button link @click="handleLastClick">
        <Icon class="mr-5px" icon="ep:arrow-left" />
        上一条
      </el-button>
      <el-button link @click="handleNextClick">
        下一条
        <Icon class="ml-5px" icon="ep:arrow-right" />
      </el-button>
      <el-divider direction="vertical" />
      <el-button link type="warning" @click="handleSyncCase">
        <Icon class="mr-5px" icon="ep:refresh" />
        同步用例
      </el-button>
      <el-button link>
        <Icon class="mr-5px" icon="ep:chat-dot-square" />
        评论
      </el-button>
      <el-divider direction="vertical" />
      <Executer source="review" @click="handleReviewCase" />
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { InfoViewer, Executer } from '@/views/components/case'

import * as REVIEW from '@/api/track/review'

const message = useMessage() // 消息弹窗

const props = defineProps({
  enums: {
    required: true,
    type: Array
  }
})
const { enums } = toRefs(props)

const visible = ref(false)
const data = ref<any>({})
const loading = ref(false)
const status = ref('')

/** 打开弹窗 */
const open = async (obj: any) => {
  loading.value = true
  visible.value = true
  data.value = await REVIEW.getReviewCaseExecute(obj)
  status.value = data.value.reviewResult
  loading.value = false
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const handleSyncCase = async () => {
  loading.value = true
  const params = data.value
  data.value = await REVIEW.syncCase({
    id: params.id,
    reviewId: params.reviewId,
    caseId: params.caseId
  })
  loading.value = false
}

const handleLastClick = async () => {
  loading.value = true
  const params = data.value
  const resp = await REVIEW.getLastCase({ id: params.id, reviewId: params.reviewId })
  loading.value = false
  if (resp === null) {
    message.error('已经是第一条了')
  } else {
    data.value = resp
    status.value = data.value.reviewResult
  }
}

const handleNextClick = async () => {
  loading.value = true
  const params = data.value
  const resp = await REVIEW.getNextCase({ id: params.id, reviewId: params.reviewId })
  loading.value = false
  if (resp === null) {
    message.error('已经是最后一条了')
    return false
  } else {
    data.value = resp
    status.value = data.value.reviewResult
    return true
  }
}

const handleReviewCase = async (result: string) => {
  loading.value = true
  await REVIEW.reviewCase({
    id: data.value.id,
    caseId: data.value.caseId,
    reviewId: data.value.reviewId,
    result: result
  })
  status.value = result
  const b = await handleNextClick()
  if (b) {
    message.success('评审成功，获取下一条！')
  } else {
    visible.value = false
  }
}

const emit = defineEmits(['close']) // 定义 close 事件，用于操作成功后的回调
const close = () => {
  emit('close')
}
</script>

<style scoped></style>
