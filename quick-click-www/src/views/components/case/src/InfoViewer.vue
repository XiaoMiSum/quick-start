<template>
  <el-form label-width="90px">
    <el-row>
      <el-col :span="8">
        <el-form-item label="所属模块：">
          <el-text type="info">{{ modelValue.path || '未分组用例' }}</el-text>
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="用例等级：">
          <ones-tag
            :type="DICT_TYPE.QUALITY_TESTCASE_PRIORITY"
            :value="modelValue.priority"
            effect="dark"
          />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <el-form-item label="标签：">
          <div v-if="modelValue.tags && modelValue.tags.length > 0">
            <el-tag v-for="item in modelValue.tags" :key="item" class="mr-5px"> {{ item }}</el-tag>
          </div>
          <span v-else> 无</span>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="8">
        <el-form-item label="前端开发：" prop="frontendDeveloper">
          <user-tag :value="modelValue.frontendDeveloper" />
        </el-form-item>
      </el-col>
      <el-col :span="8">
        <el-form-item label="后端开发：" prop="backendDeveloper">
          <user-tag :value="modelValue.backendDeveloper" />
        </el-form-item>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="24">
        <el-form-item label="前置条件：">
          <el-text type="info">{{ modelValue.prerequisite }}</el-text>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-table :data="modelValue.steps" :header-cell-style="{ background: '#f4f6f8' }" border>
          <el-table-column label="执行步骤" prop="step" />
          <el-table-column label="期望结果" prop="expected" />
          <el-table-column v-if="showActual" label="实际结果">
            <template #default="{ row }">
              <el-input v-model="row.actual" />
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </el-form>
</template>

<script lang="ts" setup>
import { DICT_TYPE } from '@/utils/dictionary'
const props = defineProps({
  modelValue: {
    required: true,
    type: Object
  },
  showActual: {
    required: false,
    type: Boolean,
    default: false
  }
})

const { modelValue, showActual } = toRefs(props)
</script>

<style scoped></style>
