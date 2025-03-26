<script lang="tsx">
import { defineComponent, PropType, ref } from 'vue'
import { ElTag } from 'element-plus'

import { useGlobalStore } from '@/store/modules/global'

const globalStore = useGlobalStore()

export default defineComponent({
  name: 'UserTag',
  props: {
    type: {
      type: String as PropType<string>,
      required: false,
      default: 'info'
    },
    value: {
      type: [String, Number, Boolean] as PropType<string | number | boolean>,
      required: false
    }
  },
  setup(props) {
    const dictData = ref<any>()
    const getDictObj = (value: any) => {
      const options: any[] = globalStore.getUsers
      options.forEach((item: any) => {
        if (item.value === value) {
          dictData.value = item
        }
      })
    }
    const rederDictTag = () => {
      if (!props.type) {
        return null
      }
      // 解决自定义字典标签值为零时标签不渲染的问题
      if (props.value === undefined || props.value === null) {
        return null
      }
      getDictObj(props.value)
      return (
        <ElTag type={props.type} disableTransitions={true}>
          {dictData.value?.label}
        </ElTag>
      )
    }
    return () => rederDictTag()
  }
})
</script>
