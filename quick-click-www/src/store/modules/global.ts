import { store } from '@/store'
import { defineStore } from 'pinia'
import { getProject } from '@/api/login'

interface GlobalData {
  currentProject: string
  projects: any[]
}

export const useGlobalStore = defineStore('global-data', {
  state: (): GlobalData => ({
    currentProject: '',
    projects: []
  }),
  getters: {
    getCurrentProject(): string {
      return this.currentProject
    },
    getProjects(): any[] {
      return this.projects
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
    }
  }
})

export const useGlobalStoreWithOut = () => {
  return useGlobalStore(store)
}
