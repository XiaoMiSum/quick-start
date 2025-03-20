<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <Search :schema="allSchemas.searchSchema" @search="setSearchParams" @reset="setSearchParams" />
    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mt-10px">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:dept:add']"
          plain
          type="primary"
          @click="openForm('create')"
        >
          <Icon class="mr-5px" icon="ep:plus" />
          新增
        </el-button>
        <el-button plain type="danger" @click="toggleExpandAll">
          <Icon class="mr-5px" icon="ep:sort" />
          展开/折叠
        </el-button>
      </el-col>
    </el-row>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <Table
      :columns="allSchemas.tableColumns"
      :loading="tableObject.loading"
      :data="list"
      :default-expand-all="isExpandAll"
      row-key="id"
    >
      <template #action="{ row }">
        <div class="flex items-center justify-center">
          <el-button
            v-hasPermi="['system:dept:update']"
            link
            type="primary"
            @click="openForm('update', row.id)"
          >
            修改
          </el-button>
          <el-button
            v-hasPermi="['system:dept:remove']"
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
  <DeptForm ref="formRef" @success="getList2" />
</template>

<script lang="ts" setup>
import { handleTree } from '@/utils/tree'

import DeptForm from './DeptForm.vue'

import { allSchemas, HTTP, tableObject, getList, setSearchParams } from './index.d'

defineOptions({ name: 'SystemDept' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化
const list = ref() // 列表的数据

const isExpandAll = ref(true) // 是否展开，默认全部展开
const refreshTable = ref(true) // 重新渲染表格状态

/** 查询部门列表 */
const getList2 = async () => {
  await getList()
  list.value = handleTree(tableObject.tableList)
}

/** 展开/折叠操作 */
const toggleExpandAll = () => {
  refreshTable.value = false
  isExpandAll.value = !isExpandAll.value
  nextTick(() => {
    refreshTable.value = true
  })
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await HTTP.delData(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList2()
  } catch {}
}

/** 初始化 **/
onMounted(async () => {
  await getList2()
  // 获取用户列表
})
</script>
