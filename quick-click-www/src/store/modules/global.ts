import { store } from '@/store'
import { defineStore } from 'pinia'
import { getProject } from '@/api/login'
import * as User from '@/api/project/member'
import * as Profile from '@/api/login/profile'
import * as Node from '@/api/project/node'
import { handleTree } from '@/utils/tree'
import { profile } from 'console'

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
    async setUserProject() {
      const data = await Profile.getProfile()
      if (data?.projectId) {
        this.setCurrentProject(data.projectId)
      }
    },
    setCurrentProject(projectId: string) {
      if (this.currentProject !== projectId) {
        this.currentProject = projectId
        this.setGlobalUsers()
        Profile.saveProfile({ projectId })
      }
    },
    async setProjects() {
      this.projects = await getProject()
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
