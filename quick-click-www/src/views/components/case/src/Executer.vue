<template>
  <el-button
    v-if="source === 'plan'"
    :loading="loading"
    plain
    type="danger"
    @click="handleClick(QualityTestStatus.Blocking)"
  >
    阻塞
  </el-button>
  <el-button :loading="loading" plain type="info" @click="handleClick(QualityTestStatus.Skipped)">
    跳过
  </el-button>
  <el-button :loading="loading" plain type="warning" @click="handleClick(QualityTestStatus.Failed)">
    不通过
  </el-button>
  <el-button :loading="loading" plain type="success" @click="handleClick(QualityTestStatus.Passed)">
    通过
  </el-button>
</template>

<script lang="ts" setup>
import { QualityTestStatus } from '@/utils/constants'

const props = defineProps({
  source: {
    required: true,
    type: String
  }
})
const { source } = toRefs(props)

const loading = ref(false)

const emit = defineEmits(['click']) // 定义 click 事件，用于操作成功后的回调

const handleClick = async (result: string) => {
  loading.value = true
  try {
    emit('click', result)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped></style>
