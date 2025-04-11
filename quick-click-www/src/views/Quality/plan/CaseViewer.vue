<template>
  <Dialog
    v-model="visible"
    :dict-value="status"
    :dict-code="DICT_TYPE.QUALITY_TEST_STATUS"
    :title="data.title"
    width="80%"
    @close="close"
  >
    <InfoViewer v-model="data" :show-actual="true" v-loading="loading" />

    <div class="mt-10px"></div>
    <Record
      ref="caseRecord"
      v-model="comments"
      :dict-code="DICT_TYPE.QUALITY_TEST_STATUS"
      :add-record="PLAN.addRecord"
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
      <Executer source="plan" @click="handleExecuteCase" />
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { InfoViewer, Executer } from '@/views/components/case'

import * as PLAN from '@/api/quality/plan'
import { DICT_TYPE } from '@/utils/dictionary'

const message = useMessage() // 消息弹窗

const visible = ref(false)
const data = ref<any>({})
const loading = ref(false)
const status = ref('')
const comments = ref([])

/** 打开弹窗 */
const open = async (id: any) => {
  loading.value = true
  visible.value = true
  data.value = await PLAN.getPlanCaseExecute(id)
  status.value = data.value.result
  loading.value = false
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const handleSyncCase = async () => {
  loading.value = true
  const params = data.value
  data.value = await PLAN.syncCase({
    id: params.id,
    planId: params.planId,
    originalId: params.originalId
  })
  loading.value = false
}

const handleLastClick = async () => {
  loading.value = true
  const params = data.value
  const resp = await PLAN.getLastCase({ id: params.id, planId: params.planId })
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
  const resp = await PLAN.getNextCase({ id: params.id, planId: params.planId })
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

const handleExecuteCase = async (result: string) => {
  loading.value = true
  await PLAN.executeCase({
    id: data.value.id,
    originalId: data.value.originalId,
    planId: data.value.planId,
    projectId: data.value.projectId,
    steps: data.value.steps,
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
  comments.value = await PLAN.getRecords(data.value.id)
}

const emit = defineEmits(['close']) // 定义 close 事件，用于操作成功后的回调
const close = () => {
  emit('close')
}
</script>

<style scoped></style>
