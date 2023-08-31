import axios from 'axios'
import { getToken } from '@/utils/auth'

const baseUrl = process.env.VUE_APP_BASE_API

/**
 * 通用文件下载
 * @param str 文件下载接口地址
 * @params params 额外参数
 */
export function downLoad(str,params) {
  var url = baseUrl + str
  axios({
    method: 'get',
    url: url,
    params:params,
    responseType: 'blob',
    headers: { 'Authorization': getToken() }
  }).then(res => {
    resolveBlob(res)
  })
}

/**
 * 解析blob响应内容并下载
 * @param {*} res blob响应内容
 */
export function resolveBlob(res) {
  const aLink = document.createElement('a')
  var blob = new Blob([res.data])
  // //从response的headers中获取filename, 后端response.setHeader("Content-disposition", "attachment; filename=xxxx.docx") 设置的文件名;
  var contentDisposition = decodeURI(res.headers['content-disposition'])
      // console.log(contentDisposition);
  const fileName = decodeURI(res.headers['content-disposition'].split('fileName=')[1])
  aLink.href = URL.createObjectURL(blob)
  aLink.setAttribute('download', fileName) // 设置下载文件名称
  document.body.appendChild(aLink)
  aLink.click()
  document.body.appendChild(aLink)
}
