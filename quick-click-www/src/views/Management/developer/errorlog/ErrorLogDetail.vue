<script lang="ts" setup>
// 代码高亮插件
import hljs from 'highlight.js' // 导入代码高亮文件
import 'highlight.js/styles/github-dark.css' // 导入代码高亮样式
import java from 'highlight.js/lib/languages/java'
import * as HTTP from '@/api/management/developer/error-log'

const form = ref<any>({})
const visible = ref(false)

/** 打开窗口 */
const open = (id: number) => {
  HTTP.getData(id).then((data) => {
    form.value = data
    visible.value = true
  })
}

/** 数据提交操作 */
const handleSubmit = (status: number) => {
  const data = { id: form.value.id, status: status }
  HTTP.updateData(data).then(() => {
    visible.value = false
  })
}

/** 高亮显示 */
const highlightedCode = (item: string) => {
  if (!item) {
    return '&nbsp;'
  }
  const language = item.startsWith('{') ? 'json' : item.startsWith('<') ? 'html' : 'java'
  if (language === 'json') {
    item = JSON.stringify(JSON.parse(item), null, 2)
  }
  if (language === 'html') {
    item = item.replaceAll('\\n', '\n')
  }
  return hljs.highlight(language, item, true).value
}

/** 提交表单 */
const emit = defineEmits(['close']) // 定义 success 事件，用于关闭窗口的回调

const handleClose = () => {
  visible.value = false
  emit('close')
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

onMounted(() => {
  hljs.registerLanguage('java', java)
})
</script>

<template>
  <Dialog v-model="visible" title="异常详情信息" width="700px" @close="handleClose">
    <el-descriptions :colon="false" :column="1" :title="form.applicationName">
      <el-descriptions-item label="请求来源">{{ form.userIp }}</el-descriptions-item>
      <el-descriptions-item label="请求方法">{{ form.requestMethod }}</el-descriptions-item>
      <el-descriptions-item label="请求地址">{{ form.requestUrl }}</el-descriptions-item>
      <el-descriptions-item label="请求参数">{{ form.requestParams }}</el-descriptions-item>
    </el-descriptions>
    <br />
    <el-descriptions :colon="false" :column="2" :title="form.exceptionName">
      <template #extra>
        <el-button
          v-hasPermi="['developer:error-log:update']"
          :disabled="form.status !== 0"
          type="text"
          @click="handleSubmit(2)"
          >忽略
        </el-button>
        <el-button
          v-hasPermi="['developer:error-log:update']"
          :disabled="form.status !== 0"
          type="text"
          @click="handleSubmit(1)"
          >已处理
        </el-button>
      </template>
      <el-descriptions-item label="异常类型">{{ form.exceptionClassName }}</el-descriptions-item>
      <el-descriptions-item label="异常文件">{{ form.exceptionFileName }}</el-descriptions-item>
      <el-descriptions-item label="异常方法">{{ form.exceptionMethodName }}</el-descriptions-item>
      <el-descriptions-item label="异常行数">{{ form.exceptionLineNumber }}</el-descriptions-item>
      <el-descriptions-item label="异常消息"
        >{{ form.exceptionRootCauseMessage }}
      </el-descriptions-item>
    </el-descriptions>
    <br />
    <el-descriptions :colon="false" :column="1" title="异常详情">
      <el-descriptions-item label="">
        <pre style="width: 670px">
              <code class="hljs" v-html="highlightedCode(form.exceptionStackTrace)"></code>
          </pre>
      </el-descriptions-item>
    </el-descriptions>
  </Dialog>
</template>

<style lang="scss" scoped></style>
