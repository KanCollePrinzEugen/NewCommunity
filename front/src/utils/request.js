import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: 'http://localhost:8090',
  timeout: 900000,
  params: {}
})

service.interceptors.request.use(
  (config) => {
    config.headers.Authorization = window.sessionStorage.getItem('token') || ''
    return config
  },
  (error) => {
    // Do something with request error
    console.log(error) // for debug
  })

service.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res instanceof Blob) {
      return response.data
    }
    if (response.data.state === 504 || response.data.state === 404) {
      ElMessage.error({ message: '未找到请求的资源' })
    } else if (response.data.state === 403) {
      ElMessage.error({ message: '无访问权限' })
    } else if (response.data.state === 401) {
      ElMessage.error({ message: '请先登录' })
    }
    if (response.data.state !== 200) {
      ElMessage.error({ message: '未知错误' })
      return null
    } else {
      return response.data
    }
  }
)
export default service
