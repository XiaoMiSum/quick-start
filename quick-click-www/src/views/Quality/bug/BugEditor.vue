<template>
  <ContentWrap class="h-1000px">
    <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px">
      <el-row>
        <el-col :span="16">
          <el-form-item label="标题" prop="title">
            <el-input v-model="formData.title" maxlength="64" show-word-limit />
          </el-form-item>
          <el-form-item label="详细描述" prop="content">
            <Editor ref="bugStep" v-model="formData.content" height="600px" />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-row>
            <el-col>
              <el-form-item label="关联计划" prop="planId">
                <el-select
                  v-model="formData.planId"
                  style="width: 100%"
                  @change="handleTestPlanChange"
                >
                  <el-option
                    v-for="item in testPlans"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="关联用例" prop="testcaseId">
                <el-select v-model="formData.testcaseId" style="width: 100%">
                  <el-option
                    v-for="item in testcases"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="所属模块" prop="nodeId">
                <el-tree-select
                  filterable
                  v-model="formData.nodeId"
                  :data="modules"
                  :props="defaultProps2"
                  check-strictly
                  default-expand-all
                  placeholder="请选择所属模块"
                  style="width: 100%"
                  value-key="nodeId"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="严重程度" prop="severity">
                <el-select v-model="formData.severity" style="width: 100%">
                  <el-option
                    v-for="item in getDictOptions(DICT_TYPE.QUALITY_BUG_SEVERITY)"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="优先级" prop="priority">
                <el-select v-model="formData.priority" style="width: 100%">
                  <el-option
                    v-for="item in getDictOptions(DICT_TYPE.QUALITY_TESTCASE_PRIORITY)"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="处理人" prop="handler">
                <el-select
                  v-model="formData.handler"
                  filterable
                  placeholder="请选择处理人"
                  style="width: 100%"
                  @change="handleHanderChange"
                >
                  <el-option
                    v-for="item in users"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-form-item label="责任人" prop="supervisor">
                <el-select
                  v-model="formData.supervisor"
                  filterable
                  placeholder="请选择责任人"
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in users"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-form>
  </ContentWrap>
  <FloatingButton
    size="large"
    buttonType=""
    :menu-items="menuItems"
    position="right-bottom"
    :offset="{ x: 600, y: 30 }"
    @item-click="handleMenuItemClick"
  />
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/quality/bug'
import * as Plan from '@/api/quality/plan'

import { useTagsViewStore } from '@/store/modules/tagsView'

import { useGlobalStore } from '@/store/modules/global'
import { useUserStore } from '@/store/modules/user'
import { useRouter } from 'vue-router' //1.先在需要跳转的页面引入useRouter

import { defaultProps2 } from '@/utils/tree'
import { getDictOptions, DICT_TYPE } from '@/utils/dictionary'

import { Editor } from '@/components/Editor'
import { FloatingButton } from '@/components/XButton'
import { handleRemove } from '@/views/Management/developer/errorlog/ErrorLog'

const userStore = useUserStore()
const globalStore = useGlobalStore()

const { params, query } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数

const { currentRoute, push } = useRouter()

const tagsViewStore = useTagsViewStore()

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const formData = ref<any>({
  nodeId: undefined,
  planId: undefined,
  testcaseId: undefined,
  projectId: globalStore.getCurrentProject,
  supervisor: undefined,
  handler: undefined,
  severity: 'Medium',
  title: '',
  priority: 'P2',
  status: 'New',
  content:
    '<p><strong>【步骤】</strong></p><p><br></p><p><strong>【结果】</strong></p><p><br></p><p><strong>【期望】</strong></p><p><br></p>'
})

const modules = ref<any>([])
const users = ref<any>([])
const testPlans = ref<any>([])
const testcases = ref<any>([])

const loading = ref(false)

const formRules = reactive({
  title: [{ required: true, message: '用例标题不能为空', trigger: 'blur' }],
  nodeId: [{ required: true, message: '所属模块不能为空', trigger: 'blur' }],
  priority: [{ required: true, message: '优先级不能为空', trigger: 'blur' }],
  supervisor: [{ required: true, message: '责任人不能为空', trigger: 'blur' }],
  severity: [{ required: true, message: '严重程度不能为空', trigger: 'blur' }],
  handler: [{ required: true, message: '处理人不能为空', trigger: 'blur' }]
})

const formRef = ref()

/** 保存并关闭 */
const handleSubmitAndCloseView = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  loading.value = true
  try {
    if (!formData.value.id) {
      formData.value.id = await HTTP.addData(formData.value)
      message.success('新增成功')
    } else {
      await HTTP.updateData(formData.value)
      message.success('更新成功')
    }
    await handleCloseView()
    toBugList()
  } finally {
    loading.value = false
  }
}

/** 保存并继续添加 */
const handleSubmitAndAdd = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  loading.value = true
  try {
    if (!formData.value.id) {
      await HTTP.addData(formData.value)
      message.success('新增成功')
      resetData()
      if (query && query.from) {
        toBugAdd()
      }
    } else {
      await HTTP.updateData(formData.value)
      message.success('更新成功')
      await handleCloseView()
      toBugAdd()
    }
  } finally {
    loading.value = false
  }
}

const handleCloseView = async () => {
  tagsViewStore.delView(unref(currentRoute))
}

const resetData = () => {
  formData.value = {
    nodeId: undefined,
    planId: undefined,
    testcaseId: undefined,
    projectId: globalStore.getCurrentProject,
    supervisor: undefined,
    handler: undefined,
    severity: 'Medium',
    title: '',
    priority: 'P2',
    status: 'New',
    content:
      '<p><strong>【步骤】</strong></p><p><br></p><p><strong>【结果】</strong></p><p><br></p><p><strong>【期望】</strong></p><p><br></p>'
  }
}

const handleGoBugList = async () => {
  try {
    await message.confirm('确认返回缺陷跟踪列表？')
    resetData()
    await handleCloseView()
    toBugList()
  } finally {
  }
}

const toBugList = async () => {
  push('/quality/bug/list')
}

const toBugAdd = async () => {
  push('/quality/bug/add')
}

/** 获得模块树 */
const getTree = async () => {
  modules.value = []
  modules.value = globalStore.getNodes
}

/**  获取用户列表 */
const getUsers = async () => {
  users.value = await globalStore.getUsers
}

/**  获取测试计划 */
const getTestPlans = async () => {
  testPlans.value = await Plan.getSimple(globalStore.getCurrentProject)
}

const handleTestPlanChange = async (val: any) => {
  testcases.value = await Plan.getFailedPlanCaseSimple(val)
}

const handleHanderChange = async (val: number) => {
  if (!formData.value.supervisor) {
    formData.value.supervisor = val
  }
}

/**
 * 操作按钮
 */

const menuItems = ref([
  {
    key: 'save-close',
    label: '保存并关闭',
    type: 'success',
    permi: true,
    disabled: false
  },
  {
    key: 'save-continue',
    label: '保存并继续',
    type: 'primary',
    permi: true,
    disabled: false
  },
  {
    key: 'cancel',
    label: '取消',
    type: '',
    permi: true,
    disabled: false
  }
])

const handleMenuItemClick = async (item) => {
  console.log('点击了菜单项:', item.key)
  // 根据item.key执行不同的操作
  switch (item.key) {
    case 'save-close':
      await handleSubmitAndCloseView()
      break
    case 'save-continue':
      await handleSubmitAndAdd()
      break
    case 'cancel':
      await handleGoBugList()
      break
  }
}

/** 监听当前项目变化，返回缺陷跟踪列表 */
const pageInit = ref(false)

watch(
  computed(() => globalStore.getCurrentProject),
  async () => {
    if (pageInit.value) {
      await handleCloseView()
      await toBugList()
    }
    pageInit.value = true
  },
  { immediate: true, deep: true }
)

onMounted(async () => {
  getTree()
  getUsers()
  getTestPlans()
  if (params && params.id) {
    formData.value = await HTTP.getData(params.id)
  }
  if (query && query.from && query.originalId) {
    if (query.from === 'bug') {
      const data = await HTTP.getData(query.originalId)
      Object.keys(formData.value)
        .filter((key) => !['status', 'handler', 'supervisor'].includes(key))
        .forEach((key) => (formData.value[key] = data[key]))
      formData.value.title = formData.value.title + '_copy'
      formData.value.status = 'New'
      formData.value.handler = undefined
      formData.value.supervisor = undefined
    } else if (query.from === 'testplan') {
      const data = Plan.getData(query.originalId)
      formData.value.title = data.title
      formData.value.planId = data.planId
      formData.value.testcaseId = data.originalId
      formData.value.status = 'New'
    }
  }
  if (!formData.value.id && !formData.value.supervisor) {
    formData.value.supervisor = userStore.getUser.id
  }
})
</script>

<style scoped>
.float-right {
  margin-top: 20px;
  margin-bottom: 10px;
  text-align: right;
  box-sizing: border-box;
}
</style>
