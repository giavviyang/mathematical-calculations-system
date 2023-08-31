import Vue from 'vue'
import Cookies from 'js-cookie'

import 'normalize.css/normalize.css' // a modern alternative to CSS resets

import Element from 'element-ui'
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css
import App from './App'
import store from './store'
import router from './router'
import permission from './directive/permission'
import Print from '@/plugs/print'  // 打印  // 路径
import '@/assets/icons' // icon
import '@/assets/iconAL/iconfont.css' // icon阿里图标
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import "./api/flexpaper/flexpaper_flash";
import "./api/flexpaper/flexpaper_flash_debug";
import "./api/flexpaper/jquery.min";
import moment from 'moment'
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel, download } from "@/utils/ruoyi";
import Pagination from "@/components/Pagination";
import '@/assets/styles/components.scss'
import '@/assets/styles/iconfont/iconfont.css'
import echarts from 'echarts'
// 富文本组件
import Editor from "@/components/Editor"
// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.download = download
Vue.prototype.moment = moment
Vue.prototype.$echarts = echarts

Vue.prototype.msgSuccess = function (msg) {
  this.$message({ showClose: true, message: msg, type: "success" });
}

Vue.prototype.msgError = function (msg) {
  this.$message({ showClose: true, message: msg, type: "error" });
}

Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg);
}

// 全局组件挂载
Vue.component('Pagination', Pagination)

Vue.component('Editor', Editor)
Vue.use(permission)
Vue.use(Print) // 注册 打印
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})

Vue.config.productionTip = false

import 'xe-utils'
import VXETable from 'vxe-table'
import 'vxe-table/lib/index.css'

Vue.use(VXETable)
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
