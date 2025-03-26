<template>
  <ContentWrap class="h-260px">
    <el-form ref="formRef" :model="caseData" :rules="formRules" label-width="100px">
      <el-form-item label="用例标题" prop="title">
        <el-input v-model="caseData.title" maxlength="64" show-word-limit />
      </el-form-item>
      <el-row>
        <el-col :span="12">
          <el-form-item label="所属模块" prop="nodeId">
            <el-tree-select
              v-model="caseData.nodeId"
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
        <el-col :span="12">
          <el-form-item label="优先级" prop="priority">
            <el-select v-model="caseData.priority" placeholder="请选择优先级" style="width: 100%">
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
        <el-col :span="12">
          <el-form-item label="标签" prop="tags">
            <el-input-tag v-model="caseData.tags" placeholder="请输入标签" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="责任人" prop="supervisor">
            <el-select v-model="caseData.supervisor" placeholder="请选择责任人" style="width: 100%">
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
    <VueDraggable target="tbody" v-model="caseData.steps" :animation="150" @end="onEnd">
      <el-table id="dragTable" :data="caseData.steps" class="h-[calc(100%-260px)]">
        <el-table-column type="index" width="30">
          <Icon icon="ep:rank" />
        </el-table-column>
        <el-table-column label="步骤描述">
          <template #default="{ row }">
            <el-input v-model="row.step" type="textarea" />
          </template>
        </el-table-column>
        <el-table-column label="期望结果">
          <template #default="{ $index }">
            <el-input v-model="caseData.steps[$index].expected" type="textarea" />
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
    </VueDraggable>
    <div class="float-right">
      <el-button @click="handleGoCaseList">取消</el-button>
      <el-button :loading="loading" type="primary" @click="handleSubmitAndCloseView">
        保存并关闭
      </el-button>
      <el-button :loading="loading" type="success" @click="handleSubmitAndAdd">
        保存并继续添加
      </el-button>
    </div>
  </ContentWrap>
</template>

<script lang="ts" setup>
import * as HTTP from '@/api/quality/testcase'
import * as Node from '@/api/project/node'
import * as User from '@/api/project/member'

import { useTagsViewStore } from '@/store/modules/tagsView'

import { useGlobalStore } from '@/store/modules/global'
import { useUserStore } from '@/store/modules/user'
import { useRouter } from 'vue-router' //1.先在需要跳转的页面引入useRouter

import { defaultProps2, handleTree } from '@/utils/tree'

import { VueDraggable } from 'vue-draggable-plus'

import { CaseStep, CaseVO } from '@/api/quality/testcase.data'
import { getDictOptions, DICT_TYPE } from '@/utils/dictionary'

const userStore = useUserStore()
const globalStore = useGlobalStore()

const { params, query } = useRoute() //2.在跳转页面定义router变量，解构得到指定的query和params传参的参数

const { currentRoute, push } = useRouter()

const tagsViewStore = useTagsViewStore()

const message = useMessage()

const caseData = ref<CaseVO>({
  nodeId: undefined,
  projectId: globalStore.getCurrentProject,
  supervisor: undefined,
  title: '',
  priority: '',
  steps: [{ step: '', expected: '', actual: '' }]
})

const modules = ref<any>([])
const users = ref<any>([])
const tags = ref<any>([])

const loading = ref(false)

const formRules = reactive({
  title: [{ required: true, message: '用例标题不能为空', trigger: 'blur' }],
  nodeId: [{ required: true, message: '所属模块不能为空', trigger: 'blur' }],
  priority: [{ required: true, message: '优先级不能为空', trigger: 'blur' }],
  supervisor: [{ required: true, message: '责任人不能为空', trigger: 'blur' }],
  steps: [{ required: true, message: '责任人不能为空', trigger: 'blur' }]
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
  console.log(caseData.value)
  try {
    if (!caseData.value.id) {
      await HTTP.addData(caseData.value)
      message.success('新增成功')
      resetData()
      if (query && query.from) {
        toCaseAdd()
      }
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
    nodeId: undefined,
    projectId: globalStore.getCurrentProject,
    supervisor: undefined,
    title: '',
    priority: '',
    steps: [{ step: '', expected: '', actual: '' }]
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
  push('/quality/case')
}

const toCaseAdd = async () => {
  push('/quality/case/add')
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

const onEnd = (evt) => {
  console.log(evt)
  console.log(caseData.value.steps)
}

const insertList = async (index: number) => {
  ;(caseData.value.steps as CaseStep[]).splice(index, 0, { step: '', expected: '', actual: '' })
}

const handleDelete = async (index: number) => {
  const steps = caseData.value.steps as CaseStep[]
  steps.splice(index, 1)
  if (!steps || steps.length < 1) {
    caseData.value.steps = [{ step: '', expected: '', actual: '' }]
  }
}

onMounted(async () => {
  getTree()
  getUsers()
  if (params && params.id) {
    caseData.value = await HTTP.getData(params.id)
  }
  if (query && query.from) {
    caseData.value = await HTTP.getData(query.from)
    delete caseData.value.id
    caseData.value.title = caseData.value.title + '_copy'
  }
  if (!caseData.value.id && !caseData.value.supervisor) {
    caseData.value.supervisor = userStore.getUser.id
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
