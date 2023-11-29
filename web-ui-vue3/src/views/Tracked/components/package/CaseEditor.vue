<template>
  <CaseInfo v-model="caseData" :modules="modules" :tags="tags" :users="users" />
  <CaseStep v-model="caseData" :showActual="false" />
  <ContentWrap>
    <div class="float-right">
      <el-button @click="handleGoCaseList">取消</el-button>
      <el-button :loading="loading" type="primary" @click="handleSubmitAndCloseView"
        >保存并关闭
      </el-button>
      <el-button :loading="loading" @click="handleSubmitAndAdd"> 保存并继续添加</el-button>
    </div>
  </ContentWrap>
</template>

<script lang="ts" setup>
import CaseInfo from './CaseInfo.vue'
import CaseStep from './CaseStep.vue'
import { CaseVO } from '@/api/tracked/testcase.data'
import { handleTree } from '@/utils/tree'

import * as HTTP from '@/api/tracked/testcase/index'
import * as MODULE from '@/api/project/module'
import * as TAG from '@/api/project/tag'
import * as USER from '@/api/system/user'

import { useTagsViewStore } from '@/store/modules/tagsView'
import { useUserStoreWithOut } from '@/store/modules/user'
import { useRouter } from 'vue-router' //1.先在需要跳转的页面引入useRouter

const userStore = useUserStoreWithOut()

const { params, query } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数

const { currentRoute, push } = useRouter()

const tagsViewStore = useTagsViewStore()

const message = useMessage()

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

const handleSubmitAndCloseView = async () => {
  loading.value = true
  try {
    if (!caseData.value.id) {
      caseData.value.id = await HTTP.addData(caseData.value)
      message.success('新增成功')
      if (query && query.from) {
        toCaseAdd()
      }
    } else {
      await HTTP.updateData(caseData.value)
      message.success('更新成功')
    }
    await handleCloseView()
    toCaseList()
  } finally {
    loading.value = false
  }
}

const handleSubmitAndAdd = async () => {
  loading.value = true
  try {
    if (!caseData.value.id) {
      await HTTP.addData(caseData.value)
      message.success('新增成功')
      resetData()
    } else {
      await HTTP.updateData(caseData.value)
      message.success('更新成功')
      await handleCloseView()
      toCaseAdd()
    }
  } finally {
    loading.value = false
  }
}

const handleCloseView = async () => {
  tagsViewStore.delView(unref(currentRoute))
}

const resetData = () => {
  caseData.value = {
    moduleId: undefined,
    chargeUserId: userStore.getUser.id,
    name: '',
    level: '',
    steps: [{ exec: '', expected: '', actual: '' }]
  }
}

const handleGoCaseList = async () => {
  try {
    await message.confirm('确认返回测试用例列表？')
    resetData()
    await handleCloseView()
    toCaseList()
  } finally {
  }
}

const toCaseList = async () => {
  push('/tracked/case')
}

const toCaseAdd = async () => {
  push('/tracked/case/add')
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
    delete caseData.value.id
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
