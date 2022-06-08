import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import installElementPlus from './plugins/element'

import locale from 'element-plus/lib/locale/lang/zh-cn'
import { getReq, postReq } from '@/utils/HttpApi'
// 导入弹出框组件
import { ElMessage } from 'element-plus'

const app = createApp(App)
installElementPlus(app)
app.use(router).mount('#app')
app.config.globalProperties.$http = 'axios'

app.config.globalProperties.$getReq = getReq
app.config.globalProperties.$postReq = postReq

app.config.globalProperties.$err = (message) => {
  ElMessage.error({ message: message })
}

createApp(App).use(installElementPlus, { locale })
