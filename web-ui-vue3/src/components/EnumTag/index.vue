<template>
  <div>
    <!-- Tag 样式 -->
    <el-tag :disable-transitions="true" :type="tag" size="small">
      {{ getLabel() }}
    </el-tag>
  </div>
</template>

<script>
import { COMMON_STATUS_ENUMS } from '@/utils/enums'

export default {
  name: 'EnumTag',
  props: {
    enums: {
      type: Array,
      require: false,
      default: COMMON_STATUS_ENUMS
    },
    value: {
      type: null,
      require: true,
      default: 0 | '0'
    }
  },
  data() {
    return {
      tag: 'info'
    }
  },
  methods: {
    getLabel() {
      for (let index = 0; index < this.enums.length; index++) {
        const item = this.enums[index]
        if (item.key === this.value) {
          this.tag = item.tag
          return item.label
        }
      }
      return '未知'
    }
  }
}
</script>
<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
