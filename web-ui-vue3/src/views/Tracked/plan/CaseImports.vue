<template>
  <Dialog title="导入用例" v-model="visible" @close="close">
    <el-form ref="formRef" label-width="80px">
      <el-form-item label="测试评审" prop="name">
        <el-select
          v-model="params.reviewId"
          placeholder="请选择测试评审"
          style="width: 100%"
          @change="handleChange"
        >
          <el-option v-for="item in lists" :key="item.code" :label="item.name" :value="item.code" />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :disabled="loading" type="primary" @click="handleImports()">导入 </el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import * as REVIEW from '@/api/track/review'
import * as HTTP from '@/api/track/plan'

const message = useMessage()

const loading = ref(false)
const visible = ref(false)
const lists = ref<any>([])
const params = ref({
  planId: -1,
  reviewId: null
})

const open = async (planId: number) => {
  params.value = { planId: planId, reviewId: null }
  visible.value = true
  loading.value = true
  lists.value = await REVIEW.getSimple()
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

const handleImports = async () => {
  loading.value = true
  try {
    const data = await HTTP.importCase(params.value)
    message.success('导入' + data + '条用例')
    visible.value = false
  } finally {
    loading.value = false
  }
}

const handleChange = () => {
  loading.value = false
}

const emit = defineEmits(['close']) // 定义 close 事件，用于操作成功后的回调
const close = () => {
  emit('close')
}
</script>
