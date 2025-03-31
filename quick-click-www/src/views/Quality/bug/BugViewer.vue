<template>
  <ContentWrap class="h-1000px">
    <el-form ref="formRef" :model="formData" label-width="100px">
      <el-row>
        <el-col :span="16">
          <el-form-item label="标题">
            <el-text type="info"> {{ formData.title }}</el-text>
          </el-form-item>
          <el-form-item label="详细描述">
            <Editor ref="bugStep" v-model="formData.content" height="600px" readonly />
          </el-form-item>
        </el-col>

        <el-col :span="8">
          <el-row>
            <el-col>
              <el-form-item label="所属模块">
                <el-tree-select
                  disabled
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
              <el-form-item label="严重程度">
                <ones-tag :value="formData.severity" :type="DICT_TYPE.QUALITY_BUG_SEVERITY" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="优先级">
                <ones-tag :value="formData.priority" :type="DICT_TYPE.QUALITY_TESTCASE_PRIORITY" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="当前状态">
                <ones-tag :value="formData.status" :type="DICT_TYPE.QUALITY_BUG_STATUS" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="激活次数">
                <el-text type="danger"> {{ formData.reopenedTimes }}</el-text>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="当前指派">
                <user-tag :value="formData.handler" />
                <el-text type="info"> {{ ' 于 ' + formData.assignedTime }}</el-text>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="责任人">
                <user-tag :value="formData.supervisor" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="由谁创建">
                <user-tag :value="formData.creatorId" />
                <el-text type="info"> {{ ' 于 ' + formData.createTime }} </el-text>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="由谁解决">
                <user-tag :value="formData.fixer" v-if="formData.fixer" />
                <el-text v-if="formData.fixer" type="info">
                  {{ ' 于 ' + formData.fixedTime }}</el-text
                >
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="产生原因">
                <ones-tag
                  :value="formData.cause"
                  :type="DICT_TYPE.QUALITY_BUG_FIX_CAUSE"
                  v-if="formData.cause"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="详情">
                <el-text type="info"> {{ formData.rootCause }}</el-text>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="解决方案">
                <el-text type="info"> {{ formData.solution }}</el-text>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="由谁关闭">
                <user-tag :value="formData.closer" v-if="formData.closer" />
                <el-text v-if="formData.closer" type="info">
                  {{ ' 于 ' + formData.fixedTime }}</el-text
                >
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="由谁解决">
                <user-tag :value="formData.fixer" v-if="formData.fixer" />
                <el-text v-if="formData.fixer" type="info">
                  {{ ' 于 ' + formData.fixedTime }}</el-text
                >
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col>
              <el-form-item label="最后修改">
                <el-text type="info">
                  {{ formData.updater + ' 于 ' + formData.updateTime }}</el-text
                >
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-form>
  </ContentWrap>
  <FloatingButton
    v-if="menuItems && showOptionButton"
    size="default"
    buttonType=""
    :menu-items="menuItems"
    position="right-bottom"
    :offset="{ x: 550, y: 100 }"
    @item-click="handleMenuItemClick"
  />

  <BugConfirmer ref="bugConfirmer" :users="users" @success="getData" />
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/quality/bug'
import * as Node from '@/api/project/node'
import * as User from '@/api/project/member'

import { checkPermi } from '@/utils/permission'

import { useTagsViewStore } from '@/store/modules/tagsView'

import { useGlobalStore } from '@/store/modules/global'
import { useRouter } from 'vue-router' //1.先在需要跳转的页面引入useRouter

import { defaultProps2, handleTree } from '@/utils/tree'
import { DICT_TYPE } from '@/utils/dictionary'

import { Editor } from '@/components/Editor'
import { FloatingButton } from '@/components/XButton'

import BugConfirmer from './BugConfirmer.vue'

const globalStore = useGlobalStore()

const { params } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数

const { currentRoute, push } = useRouter()

const tagsViewStore = useTagsViewStore()

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const showOptionButton = ref<boolean>(true)

const formData = ref<any>({
  severity: '',
  priority: '',
  status: '',
  cause: ''
})

const modules = ref<any>([])
const users = ref<any>([])

const formRef = ref()
const handleCloseView = async () => {
  tagsViewStore.delView(unref(currentRoute))
}

const handleGoBugList = async () => {
  try {
    await message.confirm('确认返回测试用例列表？')
    await handleCloseView()
    toBugList()
  } finally {
  }
}

const toBugList = async () => {
  push('/quality/bug')
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

const handleDelete = async (id: string) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await HTTP.remove(id)
    message.success(t('common.delSuccess'))
    await handleCloseView()
    toBugList()
  } catch {}
}

/**
 * 操作按钮
 */

const menuItems = ref<any[]>([])

watch(
  computed(() => formData.value),
  () => {
    menuItems.value = [
      {
        key: 'delete',
        label: '删除',
        type: 'danger',
        permi: checkPermi(['quality:bug:remove'])
      },
      {
        key: 'edit',
        label: '编辑',
        type: 'primary',
        permi: checkPermi(['quality:bug:update'])
      },
      {
        key: 'reopen',
        label: '激活',
        disabled: !['Fixed', 'Rejected', 'Closed'].includes(formData.value.status),
        type: ['Fixed', 'Rejected', 'Closed'].includes(formData.value.status) ? 'primary' : '',
        permi: checkPermi(['quality:bug:reopen'])
      },
      {
        key: 'close',
        label: '关闭',
        disabled: formData.value.status !== 'Fixed',
        type: formData.value.status === 'Fixed' ? 'primary' : '',
        permi: checkPermi(['quality:bug:close'])
      },
      {
        key: 'fix',
        label: '修复',
        disabled: !['Opened', 'Reopened'].includes(formData.value.status),
        type: ['Opened', 'Reopened'].includes(formData.value.status) ? 'primary' : '',
        permi: checkPermi(['quality:bug:fix'])
      },
      {
        key: 'confirm',
        label: '确认',
        disabled: formData.value.status !== 'New',
        type: formData.value.status === 'New' ? 'primary' : '',
        permi: checkPermi(['quality:bug:confirm'])
      },
      {
        key: 'cancel',
        label: '取消',
        type: ''
      }
    ]
  },
  { immediate: true, deep: true }
)

const handleMenuItemClick = async (item) => {
  console.log('点击按钮：' + item.key)
  // 根据item.key执行不同的操作
  switch (item.key) {
    case 'delete':
      await handleDelete(formData.value.id)
      break
    case 'confirm':
      showOptionButton.value = false
      await handleConfirm()
      break
    case 'cancel':
      await handleGoBugList()
      break
  }
}

const bugConfirmer = ref()
const handleConfirm = async () => {
  bugConfirmer.value.open(formData.value)
}

const getData = async () => {
  showOptionButton.value = true
  formData.value = await HTTP.getData(params.id)
}

onMounted(async () => {
  getTree()
  getUsers()
  getData()
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
