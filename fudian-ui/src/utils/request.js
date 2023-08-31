import axios from 'axios'
import { Notification, MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken, setToken, removeToken } from '@/utils/auth'

axios.defaults.withCredentials = true;

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: process.env.VUE_APP_BASE_API,
  // 超时
  timeout: 10000
})
// request拦截器
service.interceptors.request.use(
  config => {
    if (getToken()) {
      config.headers['Authorization'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
  },
  error => {
    console.log(error)
    Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(res => {
    const code = res.data.code
    if (code === 412) {
      MessageBox.confirm(
        '登录状态已过期，您可以继续留在该页面，或者重新登录',
        '系统提示',
        {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).then(() => {
        removeToken();
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    } else if (code === 401) {
      Message({
        message: "无访问权限",
        type: 'error'
      })
      return Promise.reject('error')
    } else if (code === 402) {
      Message({
        message: res.data.msg,
        type: 'error'
      })
      return Promise.reject('error')
    } else if (code !== 200) {
      Message({
        message: res.data.msg,
        type: 'error'
      })
      return Promise.reject('error')
    } else {
      // //缓存字典数据
      // if(res.config.url.toString().lastIndexOf("sysDictionary/queryDictionaryByTypes")>-1){
      //   //将参数值放入本地存储
      //   if(window.localStorage){
      //     let storage=window.localStorage;
      //     storage.setItem(JSON.stringify(res.config.params),res.data);
      //   }
      // }else if(res.config.url.toString().lastIndexOf("sysDictionary/queryDictionaryByType")>-1){
      //   if(window.localStorage){
      //     let storage=window.localStorage;
      //     storage.setItem(JSON.stringify(res.config.params),res.data);
      //   }
      // }
      return res.data
    }
  },
  error => {
    console.log('err' + error)
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
