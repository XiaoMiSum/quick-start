<template>
  <el-row :gutter="20">
    <!-- 左侧部门树 -->
    <el-col :span="4" :xs="24">
      <ContentWrap class="h-1/1">
        <DeptTree @node-click="handleDeptNodeClick" />
      </ContentWrap>
    </el-col>
    <el-col :span="20" :xs="24">
      <!-- 搜索 -->
      <ContentWrap>
        <Search
          :schema="allSchemas.searchSchema"
          @search="setSearchParams"
          @reset="setSearchParams"
        />
        <!-- 操作工具栏 -->
        <el-row :gutter="10" class="mt-10px">
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['system:user:add']"
              plain
              type="primary"
              @click="openForm('create')"
            >
              <Icon icon="ep:plus" />
              新增
            </el-button>
          </el-col>
        </el-row>
      </ContentWrap>

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
                v-hasPermi="['system:user:update']"
                link
                type="primary"
                @click="openForm('edit', row.id)"
              >
                修改
              </el-button>
              <el-button
                v-hasPermi="['system:permission:assign-user-role']"
                link
                type="primary"
                @click="handleRole(row)"
              >
                分配角色
              </el-button>
              <el-dropdown
                v-hasPermi="[
                  'system:user:remove',
                  'system:user:reset-password',
                  'system:permission:assign-user-role'
                ]"
                @command="(command) => handleCommand(command, row)"
              >
                <el-button link type="primary"> 更多</el-button>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item
                      v-if="checkPermi(['system:user:reset-password'])"
                      command="handleResetPwd"
                    >
                      <Icon icon="ep:key" />
                      重置密码
                    </el-dropdown-item>
                    <el-dropdown-item
                      v-if="checkPermi(['system:user:remove'])"
                      command="handleDelete"
                    >
                      <Icon icon="ep:delete" />
                      删除
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </template>
        </Table>
      </ContentWrap>
    </el-col>
  </el-row>

  <!-- 添加或修改用户对话框 -->
  <UserForm ref="formRef" @success="getList" />
  <!-- 分配角色 -->
  <UserAssignRoleForm ref="assignRoleFormRef" @success="getList" />
</template>

<script lang="ts" setup>
import UserForm from './UserForm.vue'
import UserAssignRoleForm from './UserAssignRoleForm.vue'
import DeptTree from './DeptTree.vue'

import { checkPermi } from '@/utils/permission'

import { User } from '@/api/management/system/user/index.d'

import { allSchemas, HTTP, message } from './User.d'

defineOptions({ name: 'SystemUser' })

const { t } = useI18n() // 国际化

const { tableMethods, tableObject } = useTable({
  getListApi: HTTP.listUser
})

const { getList, setSearchParams } = tableMethods

/** 处理部门被点击 */
const handleDeptNodeClick = async (row) => {
  tableObject.params.deptId = row.id
  await getList()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 操作分发 */
const handleCommand = (command: string, row: User) => {
  switch (command) {
    case 'handleDelete':
      handleDelete(row.id)
      break
    case 'handleResetPwd':
      handleResetPwd(row)
      break
    case 'handleRole':
      handleRole(row)
      break
    default:
      break
  }
}

/** 删除按钮操作 */
const handleDelete = async (id: number | undefined) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await HTTP.delUser(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 重置密码 */
const handleResetPwd = async (row: User) => {
  try {
    // 重置的二次确认
    const result = await message.prompt('请输入"' + row.name + '"的新密码', t('common.reminder'))
    const password = result.value
    // 发起重置
    await HTTP.resetUserPwd(row.id, password)
    message.success('修改成功，新密码是：' + password)
  } catch {}
}

/** 分配角色 */
const assignRoleFormRef = ref()
const handleRole = (row: User) => {
  assignRoleFormRef.value.open(row)
}

/** 初始化 */
onMounted(() => {
  getList()
})
</script>
