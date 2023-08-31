import axios from 'axios'
import request from '@/utils/request'
import store from '../store'
import { getToken } from '@/utils/auth'

const baseUrl = process.env.VUE_APP_BASE_API
//请求excel
export function requestExcel(url,callBack){
  console.log("处理")
  var url =  url
  let requestx = request({
    method: 'get',
    url: url,
    params:{},
    headers: { 'Authorization': getToken() }
  })

  requestDownLoad(requestx,callBack);

};
// 下载
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

// 下载
/**
 *
 * @param requestF request请求对象
 * @param callBack 回调函数。参数：request返回的response；返回值类型：boolean，为true的时候才会执行下载
 */
export function requestDownLoad(request,callBack) {
  console.log(store);
  store._modules.root.state.mask.lodding = true;
  request.then(response=>{
    let b = callBack(response);
    let downLoadParams = {
      id:response.data,
      fileName:response.fileName
    };
    if(b){
      console.log("--------" + baseUrl);
      resolveBlob(response);
      // window.location.href = baseUrl + "/common/download?id=" + response.data + "&fileName=" + response.fileName
      // axios({
      //   method: 'get',
      //   url: baseUrl + "/common/download",
      //   params:downLoadParams,
      //   responseType: 'blob',
      //   headers: { 'Authorization': getToken() }
      // }).then(res => {
      //   resolveBlob(res)
      // })
      store._modules.root.state.mask.lodding = false;
    }else{
      store._modules.root.state.mask.lodding = false;
    }

  })

}

// 下载
export function downLoadPost(str,params) {
  store._modules.root.state.mask.lodding = true;
  var url = baseUrl + str
  axios({
    method: 'post',
    url: url,
    data:params,
    headers: { 'Authorization': getToken() }
  }).then(res => {
    store._modules.root.state.mask.lodding = false;
    resolveBlob(res.data)
  })
}

// 上传
export function upDataParams(str,params) {
  var url = baseUrl + str
  axios({
    method: 'post',
    url: url,
    params:params,
    headers: { 'Authorization': getToken() }
  })
}
// 打印
export function printParams(str) {
  var url = baseUrl + str
  axios({
    method: 'get',
    url: url,
    headers: { 'Authorization': getToken() }
  }).then(res => {
   window.open(res)
  })
}
/**
 * 解析blob响应内容并下载
 * @param {*} res blob响应内容
 */
export function resolveBlob(res) {
  console.log(res);
  window.location.href = encodeURI(baseUrl + "/common/download?id=" + res.data + "&fileName=" + res.fileName);
  // const aLink = document.createElement('a')
  // var blob = new Blob([res.data])
  // // //从response的headers中获取filename, 后端response.setHeader("Content-disposition", "attachment; filename=xxxx.docx") 设置的文件名;
  // var contentDisposition = decodeURI(res.headers['content-disposition'])
  //     // console.log(contentDisposition);
  // const fileName = decodeURI(res.headers['content-disposition'].split('fileName=')[1])
  // aLink.href = URL.createObjectURL(blob)
  // aLink.setAttribute('download', fileName) // 设置下载文件名称
  // document.body.appendChild(aLink)
  // aLink.click()
  // document.body.appendChild(aLink)
}
