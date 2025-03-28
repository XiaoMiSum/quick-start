<template>
  <ContentWrap class="h-1300px">
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
              <el-form-item label="所属模块" prop="nodeId">
                <el-tree-select
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

  <ContentWrap>
    <FloatingButton
      icon="Plus"
      tooltip="haha"
      :menu-items="menuItems"
      position="right-bottom"
      :offset="{ x: 700, y: 30 }"
      @item-click="handleMenuItemClick"
    />
  </ContentWrap>
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/quality/bug'
import * as Node from '@/api/project/node'
import * as User from '@/api/project/member'

import { useTagsViewStore } from '@/store/modules/tagsView'

import { useGlobalStore } from '@/store/modules/global'
import { useUserStore } from '@/store/modules/user'
import { useRouter } from 'vue-router' //1.先在需要跳转的页面引入useRouter

import { defaultProps2, handleTree } from '@/utils/tree'
import { getDictOptions, DICT_TYPE } from '@/utils/dictionary'

import { Editor } from '@/components/Editor'

import { FloatingButton, ActionButtonBar } from '@/components/XButton'

const userStore = useUserStore()
const globalStore = useGlobalStore()

const { params, query } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数

const { currentRoute, push } = useRouter()

const tagsViewStore = useTagsViewStore()

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const formData = ref<any>({
  nodeId: undefined,
  projectId: globalStore.getCurrentProject,
  supervisor: undefined,
  handler: undefined,
  severity: 'Medium',
  title: '',
  priority: 'P2',
  status: 'New'
})

const modules = ref<any>([])
const users = ref<any>([])

const loading = ref(false)

const formRules = reactive({
  title: [{ required: true, message: '用例标题不能为空', trigger: 'blur' }],
  nodeId: [{ required: true, message: '所属模块不能为空', trigger: 'blur' }],
  priority: [{ required: true, message: '优先级不能为空', trigger: 'blur' }],
  supervisor: [{ required: true, message: '责任人不能为空', trigger: 'blur' }],
  severity: [{ required: true, message: '严重程度不能为空', trigger: 'blur' }],
  handler: [{ required: true, message: '处理人不能为空', trigger: 'blur' }]
})

/** 保存并关闭 */
const handleSubmitAndCloseView = async () => {
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
    toCaseList()
  } finally {
    loading.value = false
  }
}

/** 保存并继续添加 */
const handleSubmitAndAdd = async () => {
  loading.value = true
  console.log(formData.value)
  try {
    if (!formData.value.id) {
      await HTTP.addData(formData.value)
      message.success('新增成功')
      resetData()
      if (query && query.from) {
        toCaseAdd()
      }
    } else {
      await HTTP.updateData(formData.value)
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
  formData.value = {
    nodeId: undefined,
    projectId: globalStore.getCurrentProject,
    supervisor: undefined,
    handler: undefined,
    severity: 'Medium',
    title: '',
    priority: 'P2',
    status: 'New'
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
  push('/quality/bug')
}

const toCaseAdd = async () => {
  push('/quality/bug/add')
}

/** 获得模块树 */
const getTree = async () => {
  modules.value = []
  const data = await Node.getList({ projectId: globalStore.getCurrentProject })
  modules.value = handleTree(data)
}

/**  获取用户列表 */
const getUsers = async () => {
  users.value = await User.getSimple(globalStore.getCurrentProject)
}

const handleHanderChange = async (val: number) => {
  if (!formData.value.supervisor) {
    formData.value.supervisor = val
  }
}

const handleDelete = async (id: string) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await HTTP.remove(id)
    message.success(t('common.delSuccess'))
    resetData()
    await handleCloseView()
    toCaseList()
  } catch {}
}

/**
 * 操作按钮
 */

const menuItems = ref([
  {
    key: 'add',
    icon: 'DocumentAdd',
    tooltip: '添加文档',
    type: 'success'
  },
  {
    key: 'edit',
    icon: 'Edit',
    tooltip: '编辑内容'
  },
  {
    key: 'delete',
    icon: 'Delete',
    tooltip: '删除项目',
    type: 'danger'
  },
  {
    key: 'share',
    icon: 'Share',
    tooltip: '分享',
    autoClose: false // 点击后不自动关闭菜单
  }
])

const handleMenuItemClick = (item) => {
  console.log('点击了菜单项:', item.key)
  // 根据item.key执行不同的操作
}

onMounted(async () => {
  getTree()
  getUsers()
  if (params && params.id) {
    formData.value = await HTTP.getData(params.id)
  }
  if (query && query.from) {
    const data = await HTTP.getData(query.from)
    Object.keys(formData.value).forEach((key) => (formData.value[key] = data[key]))
    formData.value.title = formData.value.title + '_copy'
    formData.value.status = 'New'
    console.log(formData.value)
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
