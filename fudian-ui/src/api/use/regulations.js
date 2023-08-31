import request from '@/utils/request'
import axios from "axios/index";
import { getToken } from '@/utils/auth'

const baseUrl = process.env.VUE_APP_BASE_API

// 新增规章制度
export function insertWfRule(params,success,_this) {
    axios({
      url: baseUrl + '/wfRule/insertWfRule',
      method: 'post',
      contentType:false,
      processData:false,
      headers: {'Content-Type': 'multipart/form-data','Authorization': getToken() },
      data:params
    }).then(function (res) {
        success(res.data,_this)
      })
  }


// 编辑规章制度
export function updateWfRule(params,success,_this) {
  axios({
    url: baseUrl + '/wfRule/updateWfRule',
    method: 'post',
    contentType:false,
    processData:false,
    headers: {'Content-Type': 'multipart/form-data','Authorization': getToken()},
    data:params
  }).then(function (res) {
    success(res.data,_this)
  })
}


// 删除多条规章制度记录
export function delWfRuleByIds(params) {
  return request({
    url: '/wfRule/delWfRuleByIds',
    method: 'delete',
    params:params
  })
}


// 获取规章制度列表
export function getRuleList(params) {
  return request({
    url: '/wfRule/getRuleList',
    method: 'get',
    params:params
  })
}


// 获取pdf文件路径
export function getPdfFileByRuleId(params) {
  return request({
    url: '/wfRule/getPdfFileByRuleId',
    method: 'get',
    params:params
  })
}


// 获取pdf文件路径
export function getFileName(params) {
  return request({
    url: '/wfRule/getFileName',
    method: 'get',
    params:params
  })
}
