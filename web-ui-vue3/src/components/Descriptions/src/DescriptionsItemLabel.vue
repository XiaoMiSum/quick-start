<script lang="ts" setup>
const { label } = defineProps({
  label: {
    type: String,
    required: true
  },
  icon: {
    type: String,
    required: false
  }
})
</script>

<template>
  <div class="cell-item">
    <Icon v-if="icon" :icon="icon" :size="18" style="vertical-align: middle" />
    {{ label }}
  </div>
</template>

<style lang="scss" scoped>
.cell-item {
  display: inline;
}

.cell-item::after {
  content: ':';
}
</style>
