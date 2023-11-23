<template>
  <div
    :class="prefixCls"
    class="<xl:bg-v-dark relative h-[100%] <md:px-10px <sm:px-10px <xl:px-10px"
  >
    <div class="relative mx-auto h-full flex">
      <div
        :class="`${prefixCls}__left flex-1 bg-gray-500 bg-opacity-20 relative p-30px <xl:hidden`"
      >
        <!-- 左上角的 logo + 系统标题 -->
        <div class="relative flex items-center text-white">
          <img :src="'/' + appStore.getIcon + '.ico'" alt="" class="mr-10px h-48px w-48px" />
          <span class="text-20px font-bold">{{ underlineToHump(appStore.getTitle) }}</span>
        </div>
        <!-- 左边的背景图 + 欢迎语 -->
        <div class="h-[calc(100%-60px)] flex items-center justify-center">
          <TransitionGroup
            appear
            enter-active-class="animate__animated animate__bounceInLeft"
            tag="div"
          >
            <img key="1" alt="" class="w-350px" src="@/assets/svgs/login-box-bg.svg" />
            <div key="2" class="text-3xl text-white">{{ t('login.welcome') }}</div>
            <div key="3" class="mt-5 text-14px font-normal text-white">
              {{ t('login.message') }}
            </div>
          </TransitionGroup>
        </div>
      </div>
      <div class="dark:bg-v-dark relative flex-1 p-30px <sm:p-10px">
        <!-- 右上角的主题、语言选择 -->
        <div class="flex items-center justify-between text-white @2xl:justify-end @xl:justify-end">
          <div class="flex items-center @2xl:hidden @xl:hidden"></div>
          <div class="flex items-center justify-end space-x-10px">
            <ThemeSwitch />
            <LocaleDropdown class="<xl:text-white dark:text-white" />
          </div>
        </div>
        <!-- 右边的登录界面 -->
        <div
          class="m-auto h-full w-[100%] flex items-center @2xl:max-w-500px @lg:max-w-500px @md:max-w-500px @xl:max-w-500px"
        >
          <!-- 账号登录 -->
          <LoginForm class="m-auto h-auto p-20px <xl:(rounded-3xl light:bg-white)" />
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { underlineToHump } from '@/utils'

import { useDesign } from '@/hooks/web/useDesign'
import { useAppStore } from '@/store/modules/app'
import { ThemeSwitch } from '@/layout/components/ThemeSwitch'
import { LocaleDropdown } from '@/layout/components/LocaleDropdown'
import { LoginForm } from './components'

const { t } = useI18n()
const appStore = useAppStore()
const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('login')
</script>

<style lang="scss" scoped>
$prefix-cls: #{$namespace}-login;

.#{$prefix-cls} {
  &__left {
    &::before {
      position: absolute;
      top: 0;
      left: 0;
      z-index: -1;
      width: 100%;
      height: 100%;
      background-image: url('@/assets/svgs/login-bg.svg');
      background-position: center;
      background-repeat: no-repeat;
      content: '';
    }
  }
}
</style>
