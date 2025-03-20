<script lang="ts" setup>
import { propTypes } from '@/utils/propTypes'
import { useDesign } from '@/hooks/web/useDesign'

defineOptions({ name: 'ContentCard' })

const slots = useSlots()

const { getPrefixCls } = useDesign()

const prefixCls = getPrefixCls('content-wrap')
defineProps({
  shado: propTypes.string.def('never')
})
</script>

<template>
  <ElCard :class="[prefixCls, 'mb-10px']" :shadow="shado">
    <template v-if="slots.header" #header>
      <div class="card-header">
        <slot name="header"></slot>
      </div>
    </template>
    <div>
      <slot></slot>
    </div>
  </ElCard>
</template>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
