/**
 * 数据字典工具类
 */
import { useDictStoreWithOut } from '@/store/modules/dict'
import { ElementPlusInfoType } from '@/types/elementPlus'

const dictStore = useDictStoreWithOut()

/**
 * 获取 dictCode 对应的数据字典数组
 *
 * @param dictCode 数据类型
 * @returns {*|Array} 数据字典数组
 */
export interface DictDataType {
  dictCode: string
  label: string
  value: string | number | boolean
  colorType: ElementPlusInfoType | ''
  cssClass: string
}

export interface NumberDictDataType extends DictDataType {
  value: number
}

export const getDictOptions = (dictCode: string) => {
  return dictStore.getDictByCode(dictCode) || []
}

export const getIntDictOptions = (dictCode: string): NumberDictDataType[] => {
  // 获得通用的 DictDataType 列表
  const dictOptions: DictDataType[] = getDictOptions(dictCode)
  // 转换成 number 类型的 NumberDictDataType 类型
  // why 需要特殊转换：避免 IDEA 在 v-for="dict in getIntDictOptions(...)" 时，el-option 的 key 会告警
  const dictOption: NumberDictDataType[] = []
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: parseInt(dict.value + '')
    })
  })
  return dictOption
}

export const getStrDictOptions = (dictCode: string) => {
  const dictOption: DictDataType[] = []
  const dictOptions: DictDataType[] = getDictOptions(dictCode)
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: dict.value + ''
    })
  })
  return dictOption
}

export const getBoolDictOptions = (dictCode: string) => {
  const dictOption: DictDataType[] = []
  const dictOptions: DictDataType[] = getDictOptions(dictCode)
  dictOptions.forEach((dict: DictDataType) => {
    dictOption.push({
      ...dict,
      value: dict.value + '' === 'true'
    })
  })
  return dictOption
}

/**
 * 获取指定字典类型的指定值对应的字典对象
 * @param dictCode 字典类型
 * @param value 字典值
 * @return DictDataType 字典对象
 */
export const getDictObj = (dictCode: string, value: any): DictDataType | undefined => {
  const dictOptions: DictDataType[] = getDictOptions(dictCode)
  for (const dict of dictOptions) {
    if (dict.value === value + '') {
      return dict
    }
  }
}

/**
 * 获得字典数据的文本展示
 *
 * @param dictCode 字典类型
 * @param value 字典数据的值
 * @return 字典名称
 */
export const getDictLabel = (dictCode: string, value: any): string => {
  const dictOptions: DictDataType[] = getDictOptions(dictCode)
  const dictLabel = ref('')
  dictOptions.forEach((dict: DictDataType) => {
    if (dict.value === value + '') {
      dictLabel.value = dict.label
    }
  })
  return dictLabel.value
}

export enum DICT_TYPE {
  USER_TYPE = 'user_type',
  COMMON_STATUS = 'common_status',
  TERMINAL = 'terminal', // 终端
  DATE_INTERVAL = 'date_interval', // 数据间隔
  APPLICATIONS = 'applications',

  // ========== SYSTEM 模块 ==========
  SYSTEM_USER_GENDER = 'system_user_gender',
  SYSTEM_MENU_TYPE = 'system_menu_type',
  SYSTEM_ROLE_TYPE = 'system_role_type',
  SYSTEM_LOGIN_TYPE = 'system_login_type',
  SYSTEM_LOGIN_RESULT = 'system_login_result',
  SYSTEM_ERROR_CODE_TYPE = 'system_error_code_type',

  // ========== INFRA 模块 ==========
  INFRA_BOOLEAN_STRING = 'infra_boolean_string',
  INFRA_JOB_STATUS = 'infra_job_status',
  INFRA_JOB_LOG_STATUS = 'infra_job_log_status',
  INFRA_API_ERROR_LOG_PROCESS_STATUS = 'infra_api_error_log_process_status',
  INFRA_CONFIG_TYPE = 'infra_config_type',
  INFRA_FILE_STORAGE = 'infra_file_storage',
  INFRA_OPERATE_TYPE = 'infra_operate_type',
  INFRA_SMS_SEND_STATUS = 'infra_sms_send_status',
  INFRA_SMS_RECEIVE_STATUS = 'infra_sms_receive_status',

  // ========== 质量 模块 ==========
  QUALITY_TEST_STATUS = 'test_status',
  QUALITY_TESTCASE_PRIORITY = 'testcase_priority'
}
