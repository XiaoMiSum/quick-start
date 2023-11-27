<template>
  <Dialog v-model="visible" :tag="status" :title="data.name" width="80%" @close="close">
    <ContentWrap>
      <!-- 搜索工作栏 -->
      <el-form label-width="90px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="所属模块：">
              <span>{{ data.path || '未分组用例' }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="用例等级：">
              <EnumTag :enums="CASE_LEVEL_ENUMS" :value="data.level" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="负责人：">
              <span>{{ data.chargeUser }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="标签：">
              <div v-if="data.tags && data.tags.length > 0">
                <el-tag v-for="item in data.tags" :key="item"> {{ item }}</el-tag>
              </div>
              <span v-else> 无</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="前置步骤：">
              <el-input
                v-model="data.precondition"
                :autosize="{ minRows: 2, maxRows: 6 }"
                disabled
                maxlength="512"
                type="textarea"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="执行步骤：">
              <el-table :data="data.steps" :show-header="false" border>
                <el-table-column type="index" width="30" />
                <el-table-column>
                  <template #default="scope">
                    <el-form-item>
                      <el-input v-model="scope.row.exec" disabled type="textarea" />
                    </el-form-item>
                  </template>
                </el-table-column>
                <el-table-column>
                  <template #default="scope">
                    <el-form-item>
                      <el-input v-model="scope.row.expected" disabled type="textarea" />
                    </el-form-item>
                  </template>
                </el-table-column>
              </el-table>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </ContentWrap>

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
      <el-button link>
        <Icon class="mr-5px" icon="ep:chat-dot-square" />
        评论
      </el-button>
      <el-button :loading="loading" plain type="danger" @click="handleReviewCase('NOPASSED')">
        不通过
      </el-button>
      <el-button :loading="loading" type="primary" @click="handleReviewCase('PASSED')">
        通过
      </el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { CASE_LEVEL_ENUMS } from '@/utils/enums'

import * as REVIEW from '@/api/st/review'

const message = useMessage() // 消息弹窗

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
const status = ref('')

/** 打开弹窗 */
const open = async (obj: any) => {
  visible.value = true
  data.value = await REVIEW.getReviewCaseExecute(obj)
  status.value = data.value.reviewResult
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const handleLastClick = async () => {
  const params = data.value
  const rx = await REVIEW.getLastCase({ id: params.id, reviewId: params.reviewId })
  if (rx === null) {
    message.error('已经是最后一条了')
    return
  }
  data.value = rx
  status.value = data.value.reviewResult
}
const handleNextClick = async () => {
  const params = data.value
  const rx = await REVIEW.getNextCase({ id: params.id, reviewId: params.reviewId })
  if (rx === null) {
    message.error('已经是最后一条了')
    return 1
  }
  data.value = rx
  status.value = data.value.reviewResult
}

const handleReviewCase = async (result: string) => {
  const rx = await REVIEW.reviewCase({ id: data.value.id, result: result })
  status.value = rx.reviewResult
  const b = await handleNextClick()
  if (!b) {
    message.success('评审成功，进入下一条！')
  }
}

const emit = defineEmits(['close']) // 定义 close 事件，用于操作成功后的回调
const close = () => {
  emit('close')
}
</script>

<style scoped></style>
