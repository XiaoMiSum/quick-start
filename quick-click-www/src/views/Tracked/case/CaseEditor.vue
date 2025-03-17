<template>
  <ContentWrap class="h-260px">
    <el-form ref="formRef" :model="caseData" :rules="formRules" label-width="100px">
      <el-form-item label="用例标题" prop="name">
        <el-input v-model="caseData.name" maxlength="64" show-word-limit />
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item label="所属模块" prop="nodeId">
            <el-tree-select
              v-model="caseData.nodeId"
              :data="modules"
              :props="defaultProps"
              check-strictly
              default-expand-all
              placeholder="请选择所属模块"
              style="width: 100%"
              value-key="nodeId"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用例等级" prop="level">
            <el-select v-model="caseData.level" placeholder="请选择用例等级" style="width: 100%">
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
              v-model="caseData.tags"
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
          <el-form-item label="责任人" prop="maintainer">
            <el-select v-model="caseData.maintainer" placeholder="请选择责任人" style="width: 100%">
              <el-option
                v-for="item in users"
                :key="item.id"
                :label="item.name"
                :value="item.name"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="前置条件" prop="prerequisite">
        <el-input
          v-model="caseData.prerequisite"
          :autosize="{ minRows: 2, maxRows: 6 }"
          maxlength="512"
          show-word-limit
          type="textarea"
        />
      </el-form-item>
    </el-form>
  </ContentWrap>
  <ContentWrap class="h-[calc(100%-260px)]">
    <el-form ref="formRef" :model="caseData">
      <el-table v-dragable="dragOptions" :data="caseData.steps" class="h-[calc(100%-260px)]">
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

        <el-table-column align="center" label="操作" width="110">
          <template #default="scope">
            <el-form-item>
              <el-button circle plain type="primary" @click="insertList(scope.$index + 1)">
                <Icon icon="ep:plus" />
              </el-button>
              <el-button circle plain type="danger" @click="handleDelete(scope.$index)">
                <Icon icon="ep:delete" />
              </el-button>
            </el-form-item>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
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
import * as HTTP from '@/api/track/testcase/index'
import * as MODULE from '@/api/track/node'
import * as USER from '@/api/system/user'

import { useTagsViewStore } from '@/store/modules/tagsView'
import { useAppStore } from '@/store/modules/app'
import { useUserStore } from '@/store/modules/user'
import { useRouter } from 'vue-router' //1.先在需要跳转的页面引入useRouter
import { defaultProps, handleTree } from '@/utils/tree'
import { CASE_LEVEL_ENUMS, TESTCASE_STATUS } from '@/utils/enums'

import { vDragable } from 'element-plus-table-dragable'
import { CaseStep, CaseVO } from '@/api/track/testcase.data'

const userStore = useUserStore()
const appStore = useAppStore()

const { params, query } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数

const { currentRoute, push } = useRouter()

const tagsViewStore = useTagsViewStore()

const message = useMessage()

const caseData = ref<CaseVO>({
  nodeId: undefined,
  name: '',
  level: '',
  steps: [{ exec: '', expected: '', actual: '' }]
})

const modules = ref<any>([])
const users = ref<any>([])
const tags = ref<any>([])

const loading = ref(false)

const formRules = reactive({
  name: [{ required: true, message: '用例标题不能为空', trigger: 'blur' }],
  nodeId: [{ required: true, message: '所属模块不能为空', trigger: 'blur' }],
  level: [{ required: true, message: '用例等级不能为空', trigger: 'blur' }],
  maintainer: [{ required: true, message: '责任人不能为空', trigger: 'blur' }]
})

/** 监听 标签变化 */
const tagsBlur = async (el: any) => {
  const val = el.target.value
  if (val) {
    caseData.value.tags?.push(val)
  }
}

/** 保存并关闭 */
const handleSubmitAndCloseView = async () => {
  loading.value = true
  try {
    if (!caseData.value.id) {
      caseData.value.id = await HTTP.addData(caseData.value)
      message.success('新增成功')
    } else {
      if (caseData.value.reviewed === TESTCASE_STATUS.Pass) {
        await message.confirm('当前测试用例已「评审通过」，继续操作将重置为「未评审」')
        caseData.value.reviewed = TESTCASE_STATUS.Prepare
      }
      await HTTP.updateData(caseData.value)
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
  try {
    if (!caseData.value.id) {
      await HTTP.addData(caseData.value)
      message.success('新增成功')
      resetData()
      if (query && query.from) {
        toCaseAdd()
      }
    } else {
      if (caseData.value.reviewed === TESTCASE_STATUS.Pass) {
        await message.confirm('当前测试用例已「评审通过」，继续操作将重置为「未评审」')
        caseData.value.reviewed = TESTCASE_STATUS.Prepare
      }
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
    nodeId: undefined,
    maintainer: userStore.getUser.name,
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
  push('/track/case')
}

const toCaseAdd = async () => {
  push('/track/case/add')
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
  ;(caseData.value.steps as CaseStep[]).splice(index, 0, { exec: '', expected: '', actual: '' })
}

const handleDelete = async (index: number) => {
  const steps = caseData.value.steps as CaseStep[]
  steps.splice(index, 1)
  if (!steps || steps.length < 1) {
    caseData.value.steps = [{ exec: '', expected: '', actual: '' }]
  }
}

onMounted(async () => {
  appStore.setProjectPick(false)
  getTree()
  getUsers()
  if (params && params.id) {
    caseData.value = await HTTP.getData(params.id)
  }
  if (query && query.from) {
    caseData.value = await HTTP.getData(query.from)
    delete caseData.value.id
    caseData.value.name = 'COPY_' + caseData.value.name
  }
  caseData.value.maintainer = userStore.getUser.name
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
