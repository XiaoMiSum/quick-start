<template>
  <el-form
    v-show="getShow"
    ref="formLogin"
    :model="loginData.loginForm"
    :rules="LoginRules"
    label-position="top"
    label-width="120px"
    size="large"
  >
    <el-row style="margin-right: -10px; margin-left: -10px">
      <el-col :span="24" style="padding-right: 10px; padding-left: 10px">
        <el-form-item>
          <LoginFormTitle style="width: 100%" />
        </el-form-item>
      </el-col>
      <el-col :span="24" style="padding-right: 10px; padding-left: 10px">
        <el-form-item prop="username">
          <el-input
            v-model="loginData.loginForm.username"
            :placeholder="t('login.usernamePlaceholder')"
            :prefix-icon="iconAvatar"
          />
        </el-form-item>
      </el-col>
      <el-col :span="24" style="padding-right: 10px; padding-left: 10px">
        <el-form-item prop="password">
          <el-input
            v-model="loginData.loginForm.password"
            :placeholder="t('login.passwordPlaceholder')"
            :prefix-icon="iconLock"
            show-password
            type="password"
            @keyup.enter="handleLogin"
          />
        </el-form-item>
      </el-col>
      <el-col
        :span="24"
        style="padding-right: 10px; padding-left: 10px; margin-top: -20px; margin-bottom: -20px"
      >
        <el-form-item>
          <el-row justify="space-between" style="width: 100%">
            <el-col :span="6" />
            <el-col :offset="6" :span="12">
              <el-link style="float: right" type="primary" :underline="false" href="/register">{{
                t('login.hasNotUser')
              }}</el-link>
            </el-col>
          </el-row>
        </el-form-item>
      </el-col>
      <el-col
        :span="24"
        style="padding-right: 10px; padding-left: 10px; margin-top: -20px; margin-bottom: -20px"
      >
        <el-form-item>
          <el-row justify="space-between" style="width: 100%">
            <el-col :span="6" />
            <el-col :offset="6" :span="12" />
          </el-row>
        </el-form-item>
      </el-col>
      <el-col :span="24" style="padding-right: 10px; padding-left: 10px">
        <el-form-item>
          <XButton
            :loading="loginLoading"
            :title="t('login.login')"
            class="w-[100%]"
            type="primary"
            @click="handleLogin"
          />
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script lang="ts" setup>
import { useRouter } from 'vue-router'
import { ElLoading } from 'element-plus'
import LoginFormTitle from './LoginFormTitle.vue'

import { useIcon } from '@/hooks/web/useIcon'

import { encrypt_aes } from '@/utils/jsencrypt'
import * as authUtil from '@/utils/auth'
import * as LoginApi from '@/api/login'
import { LoginStateEnum, useFormValid, useLoginState } from './useLogin'

const { t } = useI18n()
const iconAvatar = useIcon({ icon: 'ep:avatar' })
const iconLock = useIcon({ icon: 'ep:lock' })
const formLogin = ref()
const { validForm } = useFormValid(formLogin)
const { getLoginState } = useLoginState()
const { push } = useRouter()
const loginLoading = ref(false)

const getShow = computed(() => unref(getLoginState) === LoginStateEnum.LOGIN)

const LoginRules = {
  username: [required],
  password: [required]
}
const loginData = reactive({
  isShowPassword: false,
  loginForm: {
    username: '',
    password: ''
  }
})

let token = ref('')

// 登录
const handleLogin = async () => {
  loginLoading.value = true
  try {
    const valid = await validForm()
    if (!valid) {
      return
    }
    const form = { ...loginData.loginForm }
    form.password = encrypt_aes(loginData.loginForm.password)
    const data = await LoginApi.login(form)
    token.value = data.token
    if (data.requiredBindAuthenticator) {
      loginLoading.value = false
    } else {
      ElLoading.service({
        lock: true,
        text: '正在加载系统中...',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      loginLoading.value = false
      authUtil.setToken(token.value)
      setTimeout(() => {
        const loadingInstance = ElLoading.service()
        loadingInstance.close()
        push({ path: '/' })
      }, 400)
    }
  } catch {
    loginLoading.value = false
  } finally {
  }
}
onMounted(() => {})
</script>

<style lang="scss" scoped>
:deep(.anticon) {
  &:hover {
    color: var(--el-color-primary) !important;
  }
}

.login-code {
  float: right;
  width: 100%;
  height: 38px;

  img {
    width: 100%;
    height: auto;
    max-width: 100px;
    vertical-align: middle;
    cursor: pointer;
  }
}
</style>
