<template>
  <div class="flex">
    <div class="w-79.5%">
      <ContentWrap :title="userStore.getUser.name + '，' + hello()">
        <el-row>
          <el-col :sm="12" :lg="6">
            <div class="statistic-card">
              <div style="display: inline-flex; align-items: center; font-size: 18px"> 现在是 </div>
              <div class="statistic-footer">
                <div class="footer-item">
                  <span style="font-size: 18px">{{ timer }}</span>
                </div>
              </div>
            </div>
          </el-col>
          <el-col :sm="12" :lg="6">
            <div class="statistic-card">
              <el-statistic :value="data.bugs">
                <template #title>
                  <div style="display: inline-flex; align-items: center; font-size: 18px">
                    我的 Bug
                  </div>
                </template>
              </el-statistic>
              <div class="statistic-footer">
                <div class="footer-item">
                  <span>请及时处理</span>
                </div>
              </div>
            </div>
          </el-col>
          <el-col :sm="12" :lg="6">
            <div class="statistic-card">
              <el-statistic :value="data.reviews">
                <template #title>
                  <div style="display: inline-flex; align-items: center; font-size: 18px">
                    待参与的评审
                  </div>
                </template>
              </el-statistic>
              <div class="statistic-footer">
                <div class="footer-item">
                  <span>请及时处理</span>
                </div>
              </div>
            </div>
          </el-col>
          <el-col :sm="12" :lg="6">
            <div class="statistic-card">
              <el-statistic :value="data.plans">
                <template #title>
                  <div style="display: inline-flex; align-items: center; font-size: 18px">
                    待执行的计划
                  </div>
                </template>
              </el-statistic>
              <div class="statistic-footer">
                <div class="footer-item">
                  <span>请及时处理</span>
                </div>
              </div>
            </div>
          </el-col>
        </el-row>
      </ContentWrap>
    </div>
    <div class="ml-5px w-20%">
      <ContentWrap title="快捷入口">
        <el-table :data="links" style="width: 100%" :show-header="false">
          <el-table-column>
            <template #default="{ row }">
              <el-button link type="primary" @click="push(row.path)"> {{ row.name }} </el-button>
            </template>
          </el-table-column>
          <el-table-column align="right" width="50px">
            <template #default="{ row }">
              <el-button link type="primary" @click="push(row.path)"> Go </el-button>
            </template>
          </el-table-column>
        </el-table>
      </ContentWrap>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/modules/user' // 用户数据
import { formatDate } from '@/utils/formatTime'
import { getTodoData } from '@/api/login'

const userStore = useUserStore()
const { push } = useRouter() // 路由

const links = ref<any>([
  { name: '测试用例', path: '/quality/test-case/list' },
  { name: '测试评审', path: '/quality/test-review/list' },
  { name: '测试计划', path: '/quality/test-plan/list' },
  { name: '缺陷跟踪', path: '/quality/bug/list' }
])

const hello = () => {
  const hours = new Date().getHours()
  if (hours >= 6 && hours < 12) {
    return '早上好！'
  } else if (hours >= 12 && hours < 13) {
    return '中午好！'
  } else if (hours >= 13 && hours < 18) {
    return '下午好！'
  } else if (hours >= 18 && hours < 23) {
    return '晚上好！'
  } else {
    return '夜深了，请注意休息！'
  }
}
const timer = ref(formatDate(new Date()))

const data = ref<any>({})
onMounted(async () => {
  setInterval(() => (timer.value = formatDate(new Date())), 1000)
  data.value = await getTodoData()
  setInterval(async () => (data.value = await getTodoData()), 1000 * 60 * 10)
})
</script>

<style lang="scss" scoped>
.statistic-card {
  height: 100%;
  padding: 20px;
  background-color: var(--el-bg-color-overlay);
  border-radius: 4px;
}

.statistic-footer {
  display: flex;
  margin-top: 16px;
  font-size: 12px;
  color: var(--el-text-color-regular);
  justify-content: space-between;
  flex-wrap: wrap;
}

.statistic-footer .footer-item {
  display: flex;
  justify-content: space-between;
}

.statistic-footer .footer-item span:last-child {
  display: inline-flex;
}
</style>
