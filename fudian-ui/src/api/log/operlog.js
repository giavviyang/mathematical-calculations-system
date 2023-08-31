import request from '@/utils/request'
import axios from "axios/index";
import { getToken } from '@/utils/auth'

const baseUrl = process.env.VUE_APP_BASE_API

export function queryAllOperLog(params) {
  return request({
    url: '/test/queryAllOperLog',
    method: 'get',
    params: params
  })
}

export function queryAllOperLogExcel(params,callback) {
  let url = baseUrl + '/test/queryAllOperLog'
  axios({
    method: 'get',
    url: url,
    params:params,
    headers: { 'Authorization': getToken() }
  }).then(response => callback(response.data))
}
