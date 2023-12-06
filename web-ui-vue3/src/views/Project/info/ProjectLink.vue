<template>
  <Card>
    <template #header>
      <span class="text-16px font-700">项目主页</span>
      <el-tooltip content="新增" placement="top">
        <el-button circle plain type="primary" @click="handleShowFrom()">
          <Icon icon="ep:plus" />
        </el-button>
      </el-tooltip>
    </template>
    <el-table v-if="modelValue && modelValue.length > 0" :data="modelValue">
      <el-table-column label="链接类型" width="200">
        <template #default="scope">
          <EnumTag :enums="LINK_ENUMS" :value="scope.row.type" />
        </template>
      </el-table-column>
      <el-table-column label="链接地址" prop="link">
        <template #default="scope">
          <el-link :href="scope.row.link" :underline="false" target="_blank" type="primary">
            {{ scope.row.link }}
          </el-link>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo" />
      <el-table-column align="center" label="" width="110">
        <template #default="scope">
          <el-tooltip content="编辑" placement="top">
            <el-button circle plain type="primary" @click="handleShowFrom(scope.row)">
              <Icon icon="ep:edit" />
            </el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button circle plain type="danger" @click="handleDeleteLink(scope.row)">
              <Icon icon="ep:delete" />
            </el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
  </Card>

  <Dialog v-model="visible" title="项目主页">
    <el-form :model="form" label-width="70">
      <el-form-item label="链接类型" prop="type">
        <el-select v-model="form.type" placeholder="请选择链接类型" style="width: 100%">
          <el-option
            v-for="(item, index) in LINK_ENUMS"
            :key="index"
            :label="item.label"
            :value="item.key"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="链接地址" prop="link">
        <el-input v-model="form.link" placeholder="请输入链接地址，如：https://www.baidu.com/" />
      </el-form-item>
      <el-form-item label="备注" prop="memo">
        <el-input v-model="form.memo" placeholder="请输入备注信息" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取 消</el-button>
      <el-button :loading="loading" type="primary" @click="handleSaveLink">确 定</el-button>
    </template>
  </Dialog>
</template>

<script lang="ts" setup>
import { Card } from '@/components/Card'

import { LINK_ENUMS } from '@/utils/enums'

const visible = ref(false)
const loading = ref(false)
const form = ref<any>()

const props = defineProps({
  modelValue: {
    required: true,
    type: Array
  },
  readonly: {
    required: false,
    type: Boolean,
    default: false
  }
})

const { modelValue } = toRefs(props)

const handleShowFrom = async (data?: any) => {
  if (data) {
    modelValue.value.splice(modelValue.value.indexOf(data), 1)
    form.value = data
  } else {
    form.value = { type: 'doc', link: '', memo: '' }
  }
  visible.value = true
}

const handleSaveLink = async () => {
  if (!modelValue.value) {
    modelValue.value = []
  }
  if (!form.value.id) {
    modelValue.value.push(form.value)
  }
  visible.value = false
  await emits('save')
  visible.value = false
}
const emits = defineEmits(['save'])

const handleDeleteLink = async (data: any) => {
  modelValue.value.splice(modelValue.value.indexOf(data), 1)
  await emits('save')
}
</script>

<style scoped></style>
