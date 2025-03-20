<script lang="ts" setup>
import * as HTTP from '@/api/management/developer/sms/log'
import { parseTime } from '@/utils'
import { DICT_TYPE } from '@/utils/dictionary'

const visible = ref(false)
const formData = ref<any>({})
const open = async (id: number) => {
  formData.value = await HTTP.getData(id)
  visible.value = true
}

defineExpose({ open })
</script>

<template>
  <!-- 短信日志详细 -->
  <Dialog v-model="visible" title="短信日志详细" width="700px">
    <el-form ref="form" :model="formData" label-width="140px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="日志主键：">{{ formData.id }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="短信渠道：">{{ formData.channelCode }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="短信模板：">{{ formData.templateCode }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="API 的模板编号：">{{ formData.apiTemplateId }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="用户信息：">{{ formData.mobile }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="短信内容：">{{ formData.templateContent }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="短信参数：">{{ formData.templateParams }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="发送状态：">
            <ones-tag :type="DICT_TYPE.INFRA_SMS_SEND_STATUS" :value="formData.sendStatus" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="发送时间：">{{ parseTime(formData.sendTime) }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="发送结果："
            >{{ formData.sendCode }} | {{ formData.sendMsg }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="API 发送结果："
            >{{ formData.apiSendCode }} | {{ formData.apiSendMsg }}
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="API 短信编号：">{{ formData.apiSerialNo }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="API 请求编号：">{{ formData.apiRequestId }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="接收状态：">
            <ones-tag :type="DICT_TYPE.INFRA_SMS_RECEIVE_STATUS" :value="formData.receiveStatus" />
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="接收时间：">{{ parseTime(formData.receiveTime) }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="API 接收结果："
            >{{ formData.apiReceiveCode }} | {{ formData.apiReceiveMsg }}
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">关 闭</el-button>
    </template>
  </Dialog>
</template>

<style lang="scss" scoped></style>
