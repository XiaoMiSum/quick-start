<template>
  <div class="floating-button-container" :style="containerStyle">
    <el-tooltip :content="isMenuOpen ? ' 收起' : '展开'" placement="top">
      <!-- 主按钮 -->
      <el-button
        class="main-button"
        :type="buttonType"
        :size="size"
        :circle="circle"
        @click="toggleMenu"
      >
        <Icon :icon="isMenuOpen ? 'ep:arrow-right' : 'ep:arrow-left'" />
      </el-button>
    </el-tooltip>
    <!-- 展开的菜单项 -->
    <transition-group name="floating-button-item">
      <div
        v-for="(item, index) in menuItems"
        :key="item.key"
        v-show="isMenuOpen"
        class="menu-item"
        :style="getMenuItemStyle(index)"
      >
        <el-button
          :type="item.type || ''"
          :size="size"
          :circle="circle"
          @click="handleItemClick(item)"
        >
          {{ item.label }}
        </el-button>
      </div>
    </transition-group>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const props = defineProps({
  // 主按钮类型
  buttonType: {
    type: String,
    default: 'primary'
  },
  // 按钮大小
  size: {
    type: String,
    default: 'large'
  },
  // 是否为圆形按钮
  circle: {
    type: Boolean,
    default: false
  },
  // 主按钮提示文字
  tooltip: {
    type: String,
    default: ''
  },
  // 菜单项配置
  menuItems: {
    type: Array,
    default: () => []
  },
  // 按钮位置
  position: {
    type: String,
    default: 'right-bottom',
    validator: (value) => ['right-bottom', 'left-bottom', 'right-top', 'left-top'].includes(value)
  },
  // 按钮距离边缘的距离
  offset: {
    type: Object,
    default: () => ({ x: 20, y: 20 })
  }
})

const emit = defineEmits(['item-click'])

const isMenuOpen = ref(true)

// 计算容器样式
const containerStyle = computed(() => {
  const positionMap = {
    'right-bottom': { right: `${props.offset.x}px`, bottom: `${props.offset.y}px` },
    'left-bottom': { left: `${props.offset.x}px`, bottom: `${props.offset.y}px` },
    'right-top': { right: `${props.offset.x}px`, top: `${props.offset.y}px` },
    'left-top': { left: `${props.offset.x}px`, top: `${props.offset.y}px` }
  }
  return positionMap[props.position]
})

// 计算菜单项样式
const getMenuItemStyle = (index) => {
  const baseOffset = props.size === 'large' ? 300 : props.size === 'default' ? 100 : 100
  return {
    'margin-right': `${(index + 1) * baseOffset}px`
  }
}

// 切换菜单状态
const toggleMenu = () => {
  isMenuOpen.value = !isMenuOpen.value
}

// 处理菜单项点击
const handleItemClick = (item) => {
  emit('item-click', item)
}
</script>

<style scoped>
.floating-button-container {
  position: fixed;
  z-index: 9999;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.main-button {
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 20%);
}

.menu-item {
  position: absolute;
  margin-right: 20px;
  transition: all 0.3s ease;
}

.floating-button-item-enter-active,
.floating-button-item-leave-active {
  transition: all 0.3s ease;
}

.floating-button-item-enter-from,
.floating-button-item-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
</style>
