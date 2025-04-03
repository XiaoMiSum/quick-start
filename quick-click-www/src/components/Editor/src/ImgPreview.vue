<template>
  <div class="preview" @click="onClick" @keydown="onKeydown($event)">
    <div class="preview-img">
      <img :src="src" alt />
    </div>
  </div>
</template>
<script>
export default {
  props: {
    src: {
      type: String,
      default: '',
      required: true
    },
    onClick: { type: Function, default: () => {}, required: true },
    onKeydown: { type: Function, default: () => {}, required: true }
  },
  data() {
    return {}
  }
}
</script>
<style lang="scss" scoped>
.preview {
  position: fixed;
  inset: 0;
  z-index: 9999;
  display: flex;
  width: 100%;
  height: 100%;
  overflow: scroll;
  cursor: pointer;
  background: rgb(0 0 0 / 50%);
  align-items: center;
}

.preview-img {
  display: inline-block;
  padding: 20px;
  margin: auto;

  img {
    max-width: 100%;
    max-height: 100%;
  }
}
</style>
