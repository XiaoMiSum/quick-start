<template>
  <ContentWrap title="执行步骤">
    <el-form ref="formRef" :model="modelValue">
      <el-table v-dragable="dragOptions" :data="modelValue.steps">
        <el-table-column type="index" width="30">
          <Icon icon="ep:rank" />
        </el-table-column>
        <el-table-column label="步骤描述">
          <template #default="scope">
            <el-form-item :prop="'formData.steps.' + scope.$index + '.exec'" clearable>
              <el-input v-model="scope.row.exec" type="textarea" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="期望结果">
          <template #default="scope">
            <el-form-item :prop="'formData.steps.' + scope.$index + '.expected'" clearable>
              <el-input v-model="scope.row.expected" type="textarea" />
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column v-if="false" label="实际结果">
          <template #default="scope">
            <el-form-item :prop="'formData.steps.' + scope.$index + '.actual'" clearable>
              <el-input v-model="scope.row.actual" type="textarea" />
            </el-form-item>
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作" width="110">
          <template #default="scope">
            <el-form-item>
              <el-button circle plain type="primary" @click="insertList(scope.$index + 1)">
                <Icon icon="ep:plus" />
              </el-button>
              <el-button circle plain type="danger" @click="handleDelete(scope.row.id)">
                <Icon icon="ep:delete" />
              </el-button>
            </el-form-item>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
  </ContentWrap>
</template>

<script lang="ts" setup>
import { vDragable } from 'element-plus-table-dragable'
import { CaseStep, CaseVO } from '@/api/tracked/testcase.data'

const props = defineProps({
  modelValue: {
    required: true,
    type: Object as () => CaseVO
  }
})

const { modelValue } = toRefs(props)

const dragOptions = [
  {
    selector: 'thead tr', // add drag support for column
    option: {
      // sortablejs's option
      animation: 150,
      onEnd: (evt) => {
        /* you can define a 'columns' ref 
        and use v-for render it in table's slot. 
        then you can change index of the item in 'column' here 
        to implement drag column to sort */
        console.log(evt.oldIndex, evt.newIndex)
      }
    }
  },
  {
    selector: 'tbody', // add drag support for row
    option: {
      // sortablejs's option
      animation: 150,
      onEnd: (evt) => {
        console.log(evt.oldIndex, evt.newIndex)
      }
    }
  }
]

const insertList = async (index: number) => {
  ;(modelValue.value.steps as CaseStep[]).splice(index, 0, { exec: '', expected: '', actual: '' })
}

const handleDelete = async (index: number) => {
  const steps = modelValue.value.steps as CaseStep[]
  steps.splice(index, 1)
  if (!steps || steps.length < 1) {
    modelValue.value.steps = [{ exec: '', expected: '', actual: '' }]
  }
}
</script>

<style scoped></style>
