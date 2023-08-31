import request from '@/utils/request'
import axios from "axios/index";
import { getToken } from '@/utils/auth'

const baseUrl = process.env.VUE_APP_BASE_API

export function queryAllBrowseLog(params) {
  return request({
    url: '/test/queryAllBrowseLog',
    method: 'get',
    params: params
  })
}

export function queryAllBrowseLogExcel(params,callback) {
  let url = baseUrl + '/test/queryAllBrowseLog'
  axios({
    method: 'get',
    url: url,
    params:params,
    headers: { 'Authorization': getToken() }
  }).then(response => callback(response.data))
}

export function addLookLog(params) {
  return request({
    url: '/test/addLookLog',
    method: 'post',
    params: params
  })
}


