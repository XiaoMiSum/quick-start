import Editor from './src/Editor.vue'
import FulltextDisplay from './src/FulltextDisplay.vue'
import { IDomEditor } from '@wangeditor/editor'

export interface EditorExpose {
  getEditorRef: () => Promise<IDomEditor>
}

export { Editor, FulltextDisplay }
