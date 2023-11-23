<template>
  <CaseInfo v-model="caseData" :modules="modules" :tags="tags" :users="users" />
  <CaseStep v-model="caseData" :showActual="false" />
  <ContentWrap v-if="!viewModel">
    <div class="float-right">
      <el-button @click="handleGoCaseList">取消</el-button>
      <el-button :loading="loading" type="primary" @click="handleSubmit">确定</el-button>
    </div>
  </ContentWrap>
</template>

<script lang="ts" setup>
import CaseInfo from './CaseInfo.vue'
import CaseStep from './CaseStep.vue'
import { CaseVO } from '@/api/st/testcase.data'
import { handleTree } from '@/utils/tree'

import * as HTTP from '@/api/st/testcase/index'
import * as MODULE from '@/api/project/module'
import * as TAG from '@/api/project/tag'
import * as USER from '@/api/system/user'

import { useUserStoreWithOut } from '@/store/modules/user'

const userStore = useUserStoreWithOut()

import { useRoute } from 'vue-router' //1.先在需要跳转的页面引入useRouter
const { params, query } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数

const { push } = useRouter()

const message = useMessage()

const props = defineProps({
  viewModel: {
    required: false,
    type: Boolean,
    default: false
  },
  action: {
    required: false,
    type: String,
    default: 'add'
  }
})
const { viewModel, action } = toRefs(props)

const caseData = ref<CaseVO>({
  moduleId: undefined,
  name: '',
  level: '',
  steps: [{ exec: '', expected: '', actual: '' }]
})

const modules = ref<any>([])
const users = ref<any>([])
const tags = ref<any>([])

const loading = ref(false)
const handleSubmit = async () => {
  loading.value = true
  try {
    if (action.value === 'add') {
      await HTTP.addData(caseData.value)
      message.success('新增成功')
      resetData()
    } else {
      await HTTP.updateData(caseData.value)
      message.success('更新成功')
    }
  } finally {
    loading.value = false
  }
}

const resetData = () => {
  caseData.value = {
    moduleId: undefined,
    name: '',
    level: '',
    steps: [{ exec: '', expected: '', actual: '' }]
  }
}

const handleGoCaseList = async () => {
  try {
    await message.confirm('确认返回测试用例列表？')
    resetData()
    push('/st/case')
  } finally {
  }
}

/** 获得模块树 */
const getTree = async () => {
  modules.value = []
  const data = await MODULE.getSimple()
  modules.value = handleTree(data)
}

/**  获取用户列表 */
const getUsers = async () => {
  users.value = await USER.listSimple()
}

const getTags = async () => {
  tags.value = await TAG.getSimple()
}

onMounted(async () => {
  getTree()
  getUsers()
  getTags()
  if (params && params.id) {
    caseData.value = await HTTP.getData(params.id)
  }
  if (query && query.from) {
    caseData.value = await HTTP.getData(query.from)
    caseData.value.name = 'COPY_' + caseData.value.name
  }
  caseData.value.chargeUserId = userStore.getUser.id
})
</script>

<style scoped>
.float-right {
  padding: var(--el-dialog-padding-primary);
  padding-bottom: 20px;
  text-align: right;
  box-sizing: border-box;
}
</style>
