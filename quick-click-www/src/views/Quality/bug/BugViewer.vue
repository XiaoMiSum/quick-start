<template>
  <ContentWrap>
    <el-form ref="formRef" :model="formData" label-width="100px">
      <el-row>
        <el-col :span="16">
          <el-form-item label="标题">
            <el-text type="info"> {{ formData.title }}</el-text>
          </el-form-item>
          <el-form-item label="详细描述">
            <FulltextDisplay :rich-text="formData.content" />
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
                <el-text type="info"> {{ formData.causeDetailed }}</el-text>
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
    :offset="{ x: 450, y: 50 }"
    @item-click="handleMenuItemClick"
  />

  <BugConfirmer ref="bugConfirmer" :users="users" @success="getData" />
  <BugRejecter ref="bugRejecter" :users="users" @success="getData" />
  <BugFixer ref="bugFixer" :users="users" @success="getData" />
  <BugCloser ref="bugCloser" :users="users" @success="getData" />
  <BugOpener ref="bugOpener" :users="users" @success="getData" />

  <Record
    ref="bugRecord"
    v-model="comments"
    :add-record="HTTP.addRecord"
    :dict-code="DICT_TYPE.QUALITY_BUG_STATUS"
    @success="handlegetRecords"
  />
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/quality/bug'

import { checkPermi } from '@/utils/permission'

import { useTagsViewStore } from '@/store/modules/tagsView'

import { useGlobalStore } from '@/store/modules/global'
import { useRouter } from 'vue-router' //1.先在需要跳转的页面引入useRouter

import { defaultProps2 } from '@/utils/tree'
import { DICT_TYPE } from '@/utils/dictionary'

import { FulltextDisplay } from '@/components/Editor'
import { FloatingButton } from '@/components/XButton'
import { Record } from '@/components/Record'

import BugConfirmer from './BugConfirmer.vue'
import BugRejecter from './BugRejecter.vue'
import BugFixer from './BugFixer.vue'
import BugCloser from './BugCloser.vue'
import BugOpener from './BugOpener.vue'

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

const comments = ref<any>([])

const formRef = ref()
const handleCloseView = async () => {
  tagsViewStore.delView(unref(currentRoute))
}

const handleGoBugList = async () => {
  try {
    await message.confirm('确认返回缺陷跟踪列表？')
    await handleCloseView()
    toBugList()
  } finally {
    showOptionButton.value = true
  }
}

const toBugList = async () => {
  push('/quality/bug/list')
}

/** 获得模块树 */
const getTree = async () => {
  modules.value = []
  modules.value = await globalStore.getNodes
}

/**  获取用户列表 */
const getUsers = async () => {
  users.value = await globalStore.getUsers
}

const handleDelete = async (id: string) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    await HTTP.remove(id)
    message.success(t('common.delSuccess'))
    await handleCloseView()
    toBugList()
  } finally {
    showOptionButton.value = true
  }
}

const handlegetRecords = async () => {
  try {
    const data = await HTTP.getRecords(params.id)
    comments.value = data
  } finally {
    showOptionButton.value = true
  }
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
        key: 'comment',
        label: '评论',
        type: '',
        icon: 'ep:chat-line-square'
      },
      {
        key: 'delete',
        label: '删除',
        type: 'danger',
        disabled: !!checkPermi(['quality:bug:remove']),
        icon: 'ep:delete'
      },
      {
        key: 'edit',
        label: '编辑',
        type: 'primary',
        disabled: !!checkPermi(['quality:bug:update']),
        icon: 'ep:edit'
      },
      {
        key: 'reopen',
        label: '激活',
        disabled:
          !['Fixed', 'Rejected', 'Closed'].includes(formData.value.status) &&
          checkPermi(['quality:bug:reopen']),
        type: ['Fixed', 'Rejected', 'Closed'].includes(formData.value.status) ? 'primary' : '',
        icon: 'ep:timer'
      },
      {
        key: 'close',
        label: '关闭',
        disabled: formData.value.status !== 'Fixed' && !!checkPermi(['quality:bug:close']),
        type: formData.value.status === 'Fixed' ? 'primary' : '',
        icon: 'ep:switch-button'
      },
      {
        key: 'fix',
        label: '修复',
        disabled:
          !['Opened', 'Reopened'].includes(formData.value.status) &&
          !!checkPermi(['quality:bug:fix']),
        type: ['Opened', 'Reopened'].includes(formData.value.status) ? 'primary' : '',
        icon: 'ep:circle-check'
      },
      {
        key: 'reject',
        label: '拒绝',
        disabled: formData.value.status !== 'New' && !!checkPermi(['quality:bug:reject']),
        type: formData.value.status === 'New' ? 'primary' : '',
        icon: 'ep:circle-close'
      },
      {
        key: 'confirm',
        label: '确认',
        disabled: formData.value.status !== 'New' && checkPermi(['quality:bug:confirm']),
        type: formData.value.status === 'New' ? 'primary' : '',
        icon: 'fa:bug'
      },
      {
        key: 'cancel',
        label: '返回',
        type: '',
        icon: 'ep:back'
      }
    ]
  },
  { immediate: true, deep: true }
)

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

const handleMenuItemClick = async (item) => {
  console.log('点击按钮：' + item.key)
  showOptionButton.value = false
  // 根据item.key执行不同的操作
  switch (item.key) {
    case 'comment':
      await handleComment()
      break
    case 'delete':
      await handleDelete(formData.value.id)
      break
    case 'confirm':
      await handleConfirm()
      break
    case 'reject':
      await handleReject()
      break
    case 'fix':
      await handleFix()
      break
    case 'reopen':
      await handleReopen()
      break
    case 'close':
      await handleClose()
      break
    case 'cancel':
      await handleGoBugList()
      break
    case 'edit':
      handleEditBug()
  }
}

const handleEditBug = async () => {
  await push('/quality/bug/edit/' + formData.value.id)
}

const bugConfirmer = ref()
const handleConfirm = async () => {
  bugConfirmer.value.open(formData.value)
}

const bugRejecter = ref()
const handleReject = async () => {
  bugRejecter.value.open(formData.value)
}

const bugFixer = ref()
const handleFix = async () => {
  bugFixer.value.open(formData.value)
}

const bugCloser = ref()
const handleClose = async () => {
  bugCloser.value.open(formData.value)
}

const bugOpener = ref()
const handleReopen = async () => {
  bugOpener.value.open(formData.value)
}

const bugRecord = ref()
const handleComment = async () => {
  bugRecord.value.open(formData.value.id, 'bugId')
}

const getData = async () => {
  showOptionButton.value = true
  formData.value = await HTTP.getData(params.id)
}

onMounted(async () => {
  getTree()
  getUsers()
  getData()
  handlegetRecords()
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
