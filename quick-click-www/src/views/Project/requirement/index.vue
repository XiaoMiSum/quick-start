<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form ref="queryFormRef" :inline="true" :model="queryParams" label-width="100px">
      <el-form-item label="需求标题" prop="title">
        <el-input
          v-model="queryParams.title"
          class="!w-240px"
          clearable
          placeholder="请输入需求标题"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属项目" prop="projectId">
        <el-select
          v-model="queryParams.projectId"
          class="!w-240px"
          clearable
          placeholder="请选择所属项目"
        >
          <el-option
            v-for="project in projectList"
            :key="project.id"
            :label="project.title"
            :value="project.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属模块" prop="moduleId">
        <el-select
          v-model="queryParams.moduleId"
          class="!w-240px"
          clearable
          placeholder="请选择所属模块"
        >
          <el-option
            v-for="module in moduleList"
            :key="module.id"
            :label="module.title"
            :value="module.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleQuery">
          <Icon class="mr-5px" icon="ep:search" />
          搜索
        </el-button>
        <el-button @click="resetQuery">
          <Icon class="mr-5px" icon="ep:refresh" />
          重置
        </el-button>
      </el-form-item>
    </el-form>
    <!-- 操作工具栏 -->
    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-button plain type="primary" @click="openForm('create')">
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" highlight-current-row stripe>
      <el-table-column label="标题" prop="title" show-overflow-tooltip />
      <el-table-column label="所属项目" prop="projectName" show-overflow-tooltip />
      <el-table-column label="所属模块" prop="moduleName" show-overflow-tooltip />
      <el-table-column label="原型地址" prop="prototypeUrl" show-overflow-tooltip />
      <el-table-column label="创建时间" prop="createTime" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updateTime" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.updateTime) }}
        </template>
      </el-table-column>
      <el-table-column :width="150" align="center" label="操作">
        <template #default="scope">
          <el-button
            circle
            text
            type="primary"
            @click="openForm('update', scope.row.id)"
          >
            编辑
          </el-button>

          <el-button
            circle
            text
            type="danger"
            @click="handleDelete(scope.row.id)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 分页 -->
    <Pagination
      v-model:limit="queryParams.pageSize"
      v-model:page="queryParams.pageNo"
      :total="total"
      @pagination="getList"
    />
  </ContentWrap>

  <RequirementForm ref="formRef" @success="handleQuery" />
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import RequirementForm from './RequirementForm.vue'
import { formatDate } from '@/utils/date'

import * as RequirementApi from '@/api/project/requirement'
import * as ProjectApi from '@/api/project'
import * as NodeApi from '@/api/project/node'
import { useMessage } from '@/hooks/web/useMessage'
import { useI18n } from '@/hooks/web/useI18n'

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

defineOptions({ name: 'Requirement' })

const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  title: undefined,
  projectId: undefined,
  moduleId: undefined
})
const loading = ref(false)
const list = ref<any>([])
const total = ref(0)

// 项目和模块列表
const projectList = ref<any>([])
const moduleList = ref<any>([])

const queryFormRef = ref() // 搜索的表单

const getList = async () => {
  loading.value = true
  try {
    const data = await RequirementApi.getRequirementPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

const getProjectList = async () => {
  try {
    const data = await ProjectApi.getSimpleProjects()
    projectList.value = data
  } catch (err) {
    console.error('获取项目列表失败', err)
  }
}

const getModuleList = async () => {
  try {
    // 如果已选择项目，则获取该项目的模块列表
    if (queryParams.projectId) {
      const data = await NodeApi.getProjectNodeList(queryParams.projectId)
      moduleList.value = data
    } else {
      // 否则清空模块列表
      moduleList.value = []
    }
  } catch (err) {
    console.error('获取模块列表失败', err)
  }
}

const handleQuery = async () => {
  queryParams.pageNo = 1
  getList()
}

const resetQuery = async () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: string) => {
  formRef.value.open(type, id)
}

const handleDelete = async (id: string) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await RequirementApi.deleteRequirement(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 初始化 **/
onMounted(async () => {
  await getList()
  await getProjectList()
})
</script>
</script>
</template>
</file>
```
