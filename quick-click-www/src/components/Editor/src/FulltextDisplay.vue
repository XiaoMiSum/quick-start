<template>
  <div :style="style">
    <div v-html="props.richText" style="border: none !important" @click="showPreviewImg"></div>
    <div v-if="imgBiggerState.isPreview">
      <ImgPreview
        :src="imgBiggerState.imgSrc"
        :onClick="closeImgPreview"
        :onKeydown="keydownCloseImgPreview"
      />
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import ImgPreview from './ImgPreview.vue'

const props = defineProps({
  richText: {
    type: String,
    default: ''
  },
  overflow: {
    type: Boolean,
    default: true
  },
  style: {
    type: Object,
    required: false,
    default: () => ({
      maxHeight: '800px',
      minWidth: '1000px',
      minHeight: '200px',
      overflow: 'auto'
    })
  }
})

const imgBiggerState = reactive({
  isPreview: false,
  ifShowBigger: false,
  imgSite: {
    height: 0,
    width: 0
  }, //图片属性
  imgSrc: '' //图片地址
})
const showPreviewImg = (e) => {
  if (e.target.nodeName === 'IMG') {
    imgBiggerState.isPreview = true //打开图片放大器开关
    let userAgent = navigator.userAgent //获取浏览器属性
    if (userAgent.indexOf('Chrome') > -1) {
      //Google
      imgBiggerState.imgSrc = e.target.currentSrc
    } else {
      //其他
      imgBiggerState.imgSrc = e.target.href
    }
  }
}

const keydownCloseImgPreview = (e) => {
  console.log(e)
  if (e && e.keyCode === 27) {
    imgBiggerState.isPreview = false
  }
}
const closeImgPreview = () => {
  imgBiggerState.isPreview = false
}
</script>

<style lang="scss" scoped>
.rich-text-container div {
  color: #909399;

  /* 这里可以添加其他你需要的样式 */
}

/* 滚动条整体样式 */
::-webkit-scrollbar {
  width: 2;
}

/* 滚动条滑块 */
::-webkit-scrollbar-thumb {
  width: 1px;
  height: 90px;
  background: var(--dark-mono-a100, #909399);
  border-radius: 2px 0 0;
}

/* 滚动条滑块悬停 */
::-webkit-scrollbar-thumb:hover {
  width: 1px;
  background: var(--dark-mono-a100, #909399);
  border-radius: 1px;
}

/* 滚动条轨道 */
::-webkit-scrollbar-track {
  width: 2px;
  background-color: #909399;
  border-radius: 1px;
}
</style>
