<template>
  <div style="position: relative">
    <el-row v-if="figure === 1" :gutter="20" justify="center" type="flex">
      <el-col :span="8">
        <el-image :src="data.quickMark" fit="cover" style="width: 160px; height: 160px" />
      </el-col>
      <el-col :span="14">
        <el-divider>Two Factor Authentication</el-divider>
        <span id="securityCode">安全码：{{ data.securityCode }}</span>
        <el-button link type="primary" @click="copy(data.securityCode)">复制</el-button>
        <br />
        <br />
        <span>安全码用于找回身份验证器，请妥善保管</span>
        <br />
        <span>为了您的账号安全，请勿将二维码及安全码透露给他人</span>
      </el-col>
    </el-row>
    <el-row :gutter="15" justify="center" type="flex">
      <el-col :span="15">
        <el-input
          v-model="content"
          maxlength="6"
          placeholder="请输入身份验证码"
          @input="handleVerify"
        />
      </el-col>
    </el-row>
  </div>
</template>
<script lang="ts" setup>
import { useClipboard } from '@vueuse/core'
import { getCurrentInstance, ref, toRefs } from 'vue'
import { bindUserAuthenticator, getUserAuthenticator } from '@/api/login/profile'

/**
 * VerifyIdentity
 * @description 身份验证
 * */
const props = defineProps({
  figure: {
    type: Number,
    default: 5
  },
  explain: {
    type: String,
    default: ''
  }
})

const { t } = useI18n() // 国际化
const message = useMessage() // 消息
/** 复制 **/
const copy = async (text: string) => {
  const { copy, copied, isSupported } = useClipboard({ source: text })
  if (!isSupported) {
    message.error(t('common.copyError'))
  } else {
    await copy(text)
    if (unref(copied)) {
      message.success(t('common.copySuccess'))
    }
  }
}
const { proxy } = getCurrentInstance()
let content = ref('')
const data = ref({ securityCode: '', quickMark: '' })
const { figure } = toRefs(props)

const handleVerify = async () => {
  if (content.value.length === 6) {
    if (figure?.value === 1) {
      await bindUserAuthenticator(content.value)
    }
    proxy.$parent.closeBox()
    proxy.$parent.$emit('success', content.value)
  }
}

// 请求获取身份验证器图片和安全码
const getAuthenticator = async () => {
  if (figure?.value === 1) {
    data.value = await getUserAuthenticator()
  }
}

const get = async function () {
  content.value = ''
  await getAuthenticator()
}
defineExpose({ get })
</script>
