import { store } from '@/store'
import { defineStore } from 'pinia'
import { getProject } from '@/api/login'
import { getSimple } from '@/api/project/member'

interface GlobalData {
  currentProject: string
  projects: any[]
  users: any[]
}

export const useGlobalStore = defineStore('global-data', {
  state: (): GlobalData => ({
    currentProject: '',
    projects: [],
    users: []
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
    }
  },
  actions: {
    setCurrentProject(projectId: string) {
      this.currentProject = projectId
    },
    async setProjects() {
      this.projects = await getProject()
      if (this.projects && !this.currentProject) {
        this.setCurrentProject(this.projects[0].value)
      }
    },
    async setGlobalUsers() {
      this.users = await getSimple(this.currentProject)
    }
  }
})

export const useGlobalStoreWithOut = () => {
  return useGlobalStore(store)
}
