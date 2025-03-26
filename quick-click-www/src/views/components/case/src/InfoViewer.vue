<template>
  <el-form label-width="90px">
    <el-row>
      <el-col :span="8">
        <el-form-item label="所属模块：">
          <span>{{ modelValue.path || '未分组用例' }}</span>
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
      <el-col :span="24">
        <el-form-item label="前置条件：">
          <el-input
            v-model="modelValue.prerequisite"
            :autosize="{ minRows: 2, maxRows: 6 }"
            disabled
            maxlength="512"
            type="textarea"
          />
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-form-item label="执行步骤：">
          <el-table :data="modelValue.steps" :show-header="false" border>
            <el-table-column type="index" width="30" />
            <el-table-column>
              <template #default="scope">
                <el-form-item>
                  <el-input v-model="scope.row.step" disabled type="textarea" />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column>
              <template #default="scope">
                <el-form-item>
                  <el-input v-model="scope.row.expected" disabled type="textarea" />
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column v-if="showActual">
              <template #default="scope">
                <el-form-item>
                  <el-input v-model="scope.row.actual" type="textarea" />
                </el-form-item>
              </template>
            </el-table-column>
          </el-table>
        </el-form-item>
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
