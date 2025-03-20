<template>
  <ContentWrap>
    <el-form v-if="form" ref="queryForm" label-width="100px">
      <el-form-item v-for="(key, index) in keys" :key="index" :label="form[key]['label']">
        <el-select
          v-if="form[key]['options']"
          v-model="form[key]['value']"
          filterable
          :placeholder="'请选择' + form[key]['label']"
          @change="handleChangeOption"
          @focus="handleChangeCurrentKey(key)"
        >
          <el-option
            v-for="item in form[key]['options']"
            :key="item.label"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-input
          v-else
          v-model="form[key]['value']"
          :disabled="disabled"
          maxlength="32"
          :placeholder="'请输入' + form[key]['label']"
          style="width: 220px"
        >
          <template #append>
            <el-button v-if="disabled" @click="disabled = false">编辑</el-button>
            <el-button v-else @click="handleSave(key, form[key]['value'])">保存</el-button>
          </template>
        </el-input>
      </el-form-item>
    </el-form>
  </ContentWrap>
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/management/system/configurer'

const disabled = ref(true) // 禁止修改状态

const keys = ref([] as string[])
const form = ref({})
const currentKey = ref('')

const message = useMessage()

const init = () => {
  HTTP.getPage().then((data) => {
    console.log(Object.keys(data))
    keys.value = Object.keys(data)
    form.value = data
  })
}

const handleChangeCurrentKey = async (name: string) => {
  currentKey.value = name
}

const handleSave = (name: string, value: string) => {
  if (name === 'title' && !value) {
    message.error('系统名称不能为空')
    return
  }
  save(name, value)
  disabled.value = true
}

const handleChangeOption = (value: string) => {
  save(currentKey.value, value)
}

const save = (name: string, value: any) => {
  HTTP.save({ name, value }).then(() => {
    init()
  })
}

onMounted(() => {
  init()
})
</script>
