<template>
  <Dialog
    v-model="visible"
    :dict-value="status"
    :dict-code="DICT_TYPE.QUALITY_TEST_STATUS"
    :title="data.title"
    width="80%"
    @close="close"
  >
    <InfoViewer v-model="data" :show-actual="false" v-loading="loading" />

    <div class="mt-10px"></div>
    <Record
      ref="caseRecord"
      v-model="comments"
      :dict-code="DICT_TYPE.QUALITY_TEST_STATUS"
      :add-record="REVIEW.addRecord"
      :filed-object="{ projectId: data.projectId, reviewId: data.reviewId }"
      @success="handleGetRecords"
    />

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
      <el-button link @click="handleComment">
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

import * as REVIEW from '@/api/quality/review'
import { DICT_TYPE } from '@/utils/dictionary'

const message = useMessage() // 消息弹窗
const visible = ref(false)
const data = ref<any>({})
const loading = ref(false)
const status = ref('')
const comments = ref([])

/** 打开弹窗 */
const open = async (id: number) => {
  loading.value = true
  visible.value = true
  data.value = await REVIEW.getReviewCaseExecute(id)
  comments.value = await REVIEW.getRecords(id)
  status.value = data.value.result
  loading.value = false
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const handleSyncCase = async () => {
  loading.value = true
  const params = data.value
  data.value = await REVIEW.syncCase({
    id: params.id,
    reviewId: params.reviewId,
    originalId: params.originalId
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
    status.value = data.value.result
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
    status.value = data.value.result
    return true
  }
}

const handleReviewCase = async (result: string) => {
  loading.value = true
  await REVIEW.reviewCase({
    id: data.value.id,
    originalId: data.value.originalId,
    reviewId: data.value.reviewId,
    projectId: data.value.projectId,
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

const caseRecord = ref()
const handleComment = async () => {
  caseRecord.value.open(data.value.id, 'dataId')
}

const handleGetRecords = async () => {
  comments.value = await REVIEW.getRecords(data.value.id)
}

const emit = defineEmits(['close']) // 定义 close 事件，用于操作成功后的回调
const close = () => {
  emit('close')
}
</script>

<style scoped></style>
