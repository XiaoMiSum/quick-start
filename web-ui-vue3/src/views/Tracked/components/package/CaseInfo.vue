<template>
  <ContentWrap title="基本信息">
    <el-form ref="formRef" :model="modelValue" :rules="formRules" label-width="100px">
      <el-form-item label="用例标题" prop="name">
        <el-input v-model="modelValue.name" maxlength="64" show-word-limit />
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item label="所属模块" prop="moduleId">
            <el-tree-select
              v-model="modelValue.moduleId"
              :data="modules"
              :props="defaultProps"
              check-strictly
              default-expand-all
              placeholder="请选择所属模块"
              style="width: 100%"
              value-key="moduleId"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用例等级" prop="level">
            <el-select v-model="modelValue.level" placeholder="请选择用例等级" style="width: 100%">
              <el-option
                v-for="item in CASE_LEVEL_ENUMS"
                :key="item.key"
                :label="item.label"
                :value="item.key"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="标签" prop="tags">
            <el-select
              v-model="modelValue.tags"
              allow-create
              clearable
              filterable
              multiple
              placeholder="请选择用例标签，无可用标签可输入新标签"
              style="width: 100%"
              @blur="tagsBlur"
            >
              <el-option
                v-for="item in tags"
                :key="item.code"
                :disabled="item.disabled"
                :label="item.name"
                :value="item.name"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="责任人" prop="chargeUserId">
            <el-select
              v-model="modelValue.chargeUserId"
              placeholder="请选择责任人"
              style="width: 100%"
            >
              <el-option v-for="item in users" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="前置步骤" prop="precondition">
        <el-input
          v-model="modelValue.precondition"
          :autosize="{ minRows: 2, maxRows: 6 }"
          maxlength="512"
          show-word-limit
          type="textarea"
        />
      </el-form-item>
    </el-form>
  </ContentWrap>
</template>

<script lang="ts" setup>
import { CaseVO } from '@/api/tracked/testcase.data'
import { CASE_LEVEL_ENUMS } from '@/utils/enums'
import { defaultProps } from '@/utils/tree'

const formRules = reactive({
  name: [{ required: true, message: '用例标题不能为空', trigger: 'blur' }],
  moduleId: [{ required: true, message: '所属模块不能为空', trigger: 'blur' }],
  level: [{ required: true, message: '用例等级不能为空', trigger: 'blur' }],
  chargeUserId: [{ required: true, message: '责任人不能为空', trigger: 'blur' }]
})

const props = defineProps({
  modelValue: {
    required: true,
    type: Object as () => CaseVO
  },
  modules: {
    required: true,
    type: Array
  },
  users: {
    required: true,
    type: Array as () => any[]
  },
  tags: {
    required: true,
    type: Array as () => any[]
  }
})

const { modelValue, modules, users } = toRefs(props)

const tagsBlur = async (el: any) => {
  const val = el.target.value
  if (val) {
    modelValue.value.tags?.push(val)
  }
}
</script>

<style scoped></style>
