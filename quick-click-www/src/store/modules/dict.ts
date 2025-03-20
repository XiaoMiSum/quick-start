import { defineStore } from 'pinia'
import { store } from '../index'
// @ts-ignore
import { CACHE_KEY, useCache } from '@/hooks/web/useCache'
import { getSimple } from '@/api/management/developer/dictionary/value'

const { wsCache } = useCache('sessionStorage')

export interface DictValueType {
  value: any
  label: string
  colorType?: string
}

export interface DictType {
  code: string
  dictValue: DictValueType[]
}

export interface DictState {
  dictMap: Map<string, any>
  isSetDict: boolean
}

export const useDictStore = defineStore('dict', {
  state: (): DictState => ({
    dictMap: new Map<string, any>(),
    isSetDict: false
  }),
  getters: {
    getDictMap(): Recordable {
      const dictMap = wsCache.get(CACHE_KEY.DICTIONARY)
      if (dictMap) {
        this.dictMap = dictMap
      }
      return this.dictMap
    },
    getIsSetDict(): boolean {
      return this.isSetDict
    }
  },
  actions: {
    async setDictMap() {
      const dictMap = wsCache.get(CACHE_KEY.DICTIONARY)
      if (dictMap) {
        this.dictMap = dictMap
        this.isSetDict = true
      } else {
        const res = await getSimple()
        // 设置数据
        const dictDataMap = new Map<string, any>()
        res.forEach((item: any) => {
          // 获得 dictCode 层级
          const enumValueObj = dictDataMap[item.dictCode]
          if (!enumValueObj) {
            dictDataMap[item.dictCode] = []
          }
          // 处理 dictValue 层级
          dictDataMap[item.dictCode].push({
            value: item.value,
            label: item.label,
            colorType: item.colorType,
            disable: item.disable
          })
        })
        this.dictMap = dictDataMap
        this.isSetDict = true
        wsCache.set(CACHE_KEY.DICTIONARY, dictDataMap, { exp: 60 }) // 60 秒 过期
      }
    },
    getDictByCode(code: string) {
      if (!this.isSetDict) {
        this.setDictMap()
      }
      return this.dictMap[code]
    },
    async resetDict() {
      wsCache.delete(CACHE_KEY.DICTIONARY)
      const res = await getSimple()
      // 设置数据
      const dictDataMap = new Map<string, any>()
      res.forEach((dictData: any) => {
        // 获得 dictCode 层级
        const enumValueObj = dictDataMap[dictData.dictCode]
        if (!enumValueObj) {
          dictDataMap[dictData.dictCode] = []
        }
        // 处理 dictValue 层级
        dictDataMap[dictData.dictCode].push({
          value: dictData.value,
          label: dictData.label,
          colorType: dictData.colorType,
          disable: dictData.disable
        })
      })
      this.dictMap = dictDataMap
      this.isSetDict = true
      wsCache.set(CACHE_KEY.DICTIONARY, dictDataMap, { exp: 60 }) // 60 秒 过期
    }
  }
})

export const useDictStoreWithOut = () => {
  return useDictStore(store)
}
