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
        <el-form ref="queryFormRef" :inline="true" :model="queryParams">
          <el-form-item label="" prop="username">
            <el-input
              v-model="queryParams.username"
              class="!w-240px"
              clearable
              placeholder="请输入用户名称"
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleQuery">
              <Icon icon="ep:search" />
              搜索
            </el-button>
            <el-button @click="resetQuery">
              <Icon icon="ep:refresh" />
              重置
            </el-button>
          </el-form-item>
        </el-form>

        <!-- 操作工具栏 -->
        <el-row :gutter="10">
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
        <el-table v-loading="loading" :data="list">
          <el-table-column
            :show-overflow-tooltip="true"
            align="center"
            label="登录名称"
            prop="username"
          />
          <el-table-column
            :show-overflow-tooltip="true"
            align="center"
            label="用户姓名"
            prop="name"
          />
          <el-table-column
            key="deptName"
            :show-overflow-tooltip="true"
            align="center"
            label="部门"
            prop="dept.name"
          />
          <el-table-column key="status" label="状态">
            <template #default="scope">
              <el-switch
                v-model="scope.row.status"
                :active-value="1"
                :inactive-value="0"
                @change="handleStatusChange(scope.row)"
              />
            </template>
          </el-table-column>
          <el-table-column
            :formatter="dateFormatter"
            align="center"
            label="创建时间"
            prop="createTime"
            width="180"
          />
          <el-table-column align="center" label="操作" width="160">
            <template #default="scope">
              <div class="flex items-center justify-center">
                <el-button
                  v-hasPermi="['system:user:update']"
                  link
                  type="primary"
                  @click="openForm('update', scope.row.id)"
                >
                  修改
                </el-button>
                <el-button
                  v-hasPermi="['system:permission:assign-user-role']"
                  link
                  type="primary"
                  @click="handleRole(scope.row)"
                >
                  分配角色
                </el-button>
                <el-dropdown
                  v-hasPermi="[
                    'system:user:remove',
                    'system:user:reset-password',
                    'system:permission:assign-user-role'
                  ]"
                  @command="(command) => handleCommand(command, scope.row)"
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
          </el-table-column>
        </el-table>
        <Pagination
          v-model:limit="queryParams.pageSize"
          v-model:page="queryParams.pageNo"
          :total="total"
          @pagination="getList"
        />
      </ContentWrap>
    </el-col>
  </el-row>

  <!-- 添加或修改用户对话框 -->
  <UserForm ref="formRef" @success="handleQuery" />
  <!-- 分配角色 -->
  <UserAssignRoleForm ref="assignRoleFormRef" @success="getList" />
</template>

<script lang="ts" setup>
import { checkPermi } from '@/utils/permission'
import { dateFormatter } from '@/utils/formatTime'
import * as HTTP from '@/api/system/user'
import UserForm from './UserForm.vue'
import UserAssignRoleForm from './UserAssignRoleForm.vue'
import DeptTree from './DeptTree.vue'
import { COMMON_STATUS_ENUM } from '@/utils/enums'

defineOptions({ name: 'SystemUser' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const total = ref(0) // 列表的总页数
const list = ref([]) // 列表的数
const queryParams = ref({
  pageNo: 1,
  pageSize: 10,
  username: undefined,
  deptId: undefined
})
const queryFormRef = ref() // 搜索的表单

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await HTTP.listUser(queryParams.value)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.value.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value?.resetFields()
  handleQuery()
}

/** 处理部门被点击 */
const handleDeptNodeClick = async (row) => {
  queryParams.value.deptId = row.id
  await getList()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 修改用户状态 */
const handleStatusChange = async (row: HTTP.UserVO) => {
  try {
    // 修改状态的二次确认
    const text = row.status === COMMON_STATUS_ENUM.ENABLE ? '启用' : '停用'
    await message.confirm('确认要"' + text + '""' + row.name + '"用户吗?')
    // 发起修改状态
    await HTTP.updateUser({ id: row.id, status: row.status })
    // 刷新列表
    await getList()
  } catch {
    // 取消后，进行恢复按钮
    row.status =
      row.status === COMMON_STATUS_ENUM.ENABLE
        ? COMMON_STATUS_ENUM.DISABLE
        : COMMON_STATUS_ENUM.ENABLE
  }
}

/** 操作分发 */
const handleCommand = (command: string, row: HTTP.UserVO) => {
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
const handleResetPwd = async (row: HTTP.UserVO) => {
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
const handleRole = (row: HTTP.UserVO) => {
  assignRoleFormRef.value.open(row)
}

/** 初始化 */
onMounted(() => {
  getList()
})
</script>
