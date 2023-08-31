import request from '@/utils/request'
import axios from "axios/index";
import { getToken } from '@/utils/auth'

const baseUrl = process.env.VUE_APP_BASE_API
export function queryAllLoginLog(params) {
  return request({
    url: '/test/queryAllLoginLog',
    method: 'get',
    params: params
  })
}

export function queryAllLoginLogExcel(params,callback) {
  let url = baseUrl + '/test/queryAllLoginLog'
  axios({
    method: 'get',
    url: url,
    params:params,
    headers: { 'Authorization': getToken() }
  }).then(response => callback(response.data))
}
