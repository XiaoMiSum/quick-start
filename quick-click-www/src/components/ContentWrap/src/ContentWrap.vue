<script lang="ts" setup>
import { propTypes } from '@/utils/propTypes'
import { useDesign } from '@/hooks/web/useDesign'

defineOptions({ name: 'ContentWrap' })

const { getPrefixCls } = useDesign()

const prefixCls = getPrefixCls('content-wrap')

defineProps({
  title: propTypes.string.def(''),
  message: propTypes.string.def(''),
  statistics: propTypes.object.def()
})
</script>

<template>
  <ElCard :class="[prefixCls, 'mb-5px']" shadow="never">
    <template v-if="title" #header>
      <div class="flex items-center">
        <span class="text-16px font-700">{{ title }}</span>
        <ElTooltip v-if="message" effect="dark" placement="right">
          <template #content>
            <div class="max-w-200px">{{ message }}</div>
          </template>
          <Icon :size="14" class="ml-5px" icon="ep:question-filled" />
        </ElTooltip>
        <div v-if="statistics" class="ml-50px">
          <el-button link type="info">
            {{ statistics.name }}
          </el-button>
          <el-tooltip content="通过数" placement="top">
            <el-button link type="success">
              {{ statistics.passed }}
            </el-button>
          </el-tooltip>
          <el-tooltip content="不通过数" placement="top">
            <el-button link type="danger">
              {{
                statistics.total - statistics.passed - statistics.notstarted - statistics.skipped
              }}
            </el-button>
          </el-tooltip>
          <el-tooltip content="跳过数" placement="top">
            <el-button link type="info">
              {{ statistics.skipped }}
            </el-button>
          </el-tooltip>
          <el-tooltip content="未开始数" placement="top">
            <el-button link type="warning">
              {{ statistics.notstarted }}
            </el-button>
          </el-tooltip>
        </div>
      </div>
    </template>
    <div>
      <slot></slot>
    </div>
  </ElCard>
</template>
