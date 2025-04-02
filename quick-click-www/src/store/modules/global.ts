import { store } from '@/store'
import { defineStore } from 'pinia'
import { getProject } from '@/api/login'
import * as User from '@/api/project/member'
import * as Node from '@/api/project/node'
import { handleTree } from '@/utils/tree'

interface GlobalData {
  currentProject: string
  projects: any[]
  users: any[]
  nodes: any[]
}

export const useGlobalStore = defineStore('global-data', {
  state: (): GlobalData => ({
    currentProject: '',
    projects: [],
    users: [],
    nodes: []
  }),
  getters: {
    getCurrentProject(): string {
      return this.currentProject
    },
    getProjects(): any[] {
      return this.projects
    },
    getUsers(): any[] {
      return this.users
    },
    getNodes(): any[] {
      return this.nodes
    }
  },
  actions: {
    setCurrentProject(projectId: string) {
      this.currentProject = projectId
      this.setGlobalUsers()
    },
    async setProjects() {
      this.projects = await getProject()
      // todo 这里要获取用户最后一次操作的项目数据
      if (this.projects && !this.currentProject) {
        this.setCurrentProject(this.projects[0].value)
      }
    },
    async setGlobalUsers() {
      this.users = await User.getSimple(this.currentProject)
    },
    async setNodes() {
      const data = await Node.getList({ projectId: this.getCurrentProject })
      this.nodes = handleTree(data)
    }
  }
})

export const useGlobalStoreWithOut = () => {
  return useGlobalStore(store)
}
