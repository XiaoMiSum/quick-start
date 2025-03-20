<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <Search :schema="allSchemas.searchSchema" @search="setSearchParams" @reset="setSearchParams" />
    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mt-10px">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <Table
      :columns="allSchemas.tableColumns"
      :loading="tableObject.loading"
      :data="tableObject.tableList"
      :pagination="{
        background: true,
        total: tableObject.total
      }"
      v-model:pageSize="tableObject.pageSize"
      v-model:currentPage="tableObject.currentPage"
    >
      <template #action="{ row }">
        <div class="flex items-center justify-center">
          <el-button
            v-hasPermi="['system:role:update']"
            link
            type="primary"
            @click="openForm('update', row.id)"
          >
            编辑
          </el-button>
          <el-button
            v-hasPermi="['system:permission:assign-role-menu']"
            link
            preIcon="ep:basketball"
            title="菜单权限"
            type="primary"
            @click="openAssignMenuForm(row)"
          >
            菜单权限
          </el-button>
          <el-button
            v-hasPermi="['system:role:remove']"
            link
            type="danger"
            @click="handleDelete(row.id)"
          >
            删除
          </el-button>
        </div>
      </template>
    </Table>
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <RoleForm ref="formRef" @success="getList" />
  <!-- 表单弹窗：菜单权限 -->
  <RoleAssignMenuForm ref="assignMenuFormRef" @success="getList" />
</template>

<script lang="ts" setup>
import RoleForm from './RoleForm.vue'
import RoleAssignMenuForm from './RoleAssignMenuForm.vue'

import { allSchemas, tableObject, getList, setSearchParams, handleDelete } from './index.d'

defineOptions({ name: 'SystemRole' })
/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}
/** 菜单权限操作 */
const assignMenuFormRef = ref()
const openAssignMenuForm = async (row: any) => {
  assignMenuFormRef.value.open(row)
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
