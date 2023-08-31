import request from '@/utils/request'
import store from "../../../store";
import {resolveBlob} from "../../../downLoad/filedownload";

import axios from 'axios'

const baseUrl = process.env.VUE_APP_BASE_API

// 查询字典类型列表
export function getLeftTreeNode() {
  return request({
    url: '/system/dict/type/getLeftTreeNode',
    method: 'get'
  })
}

// 查询字典类型列表
export function listType(query) {
  return request({
    url: '/system/dict/type/list',
    method: 'get',
    params: query
  })
}

// 查询字典类型详细
export function getType(dictId) {
  return request({
    url: '/system/dict/type/getType/' + dictId,
    method: 'get'
  })
}


// 查询字典类型详细
export function getData(dictId) {
  return request({
    url: '/system/dict/type/getData/' + dictId,
    method: 'get'
  })
}

// 新增字典类型
export function addType(data) {
  return request({
    url: '/system/dict/type/addType',
    method: 'post',
    data: data
  })
}

// 新增字典数据
export function addDictData(data) {
  return request({
    url: '/system/dict/type/addDictData',
    method: 'post',
    data: data
  })
}

// 修改字典类型
export function updateType(data) {
  return request({
    url: '/system/dict/type/updateType',
    method: 'put',
    data: data
  })
}

// 修改字典数据
export function updateData(data) {
  return request({
    url: '/system/dict/type/updateData',
    method: 'put',
    data: data
  })
}

// 删除字典类型
export function delType(dictId) {
  return request({
    url: '/system/dict/type/delType/' + dictId,
    method: 'delete'
  })
}

// 删除字典数据
export function delData(dictId) {
  return request({
    url: '/system/dict/type/delData/' + dictId,
    method: 'delete'
  })
}

// 导出字典类型
export function exportType(query) {
  return request({
    url: '/system/dict/type/export',
    method: 'get',
    params: query
  })
}

// 获取字典选择框列表
export function optionselect() {
  return request({
    url: '/system/dict/type/optionselect',
    method: 'get'
  })
}

//点击字节点查询后台方法
export function clickTreeNode(param) {
  return request({
    url: '/system/dict/type/clickTreeNode',
    method: 'get',
    params: param
  })
}

export function queryDictTree(param) {
  return request({
    url: '/system/dict/type/queryDictTree',
    method: 'get',
    params: param
  })
}
export function addNode(param) {
  return request({
    url: '/system/dict/type/addNode',
    method: 'post',
    params: param
  })
}
export function addOrUpdateNodeType(data) {
  return request({
    url: '/system/dict/type/addOrUpdateNodeType',
    method: 'post',
    data: data
  })
}

// export function addOrUpdateNodeType(params) {
//   var url = baseUrl + '/system/dict/type/addOrUpdateNodeType'
//   console.log(params)
//   return axios({
//     method: 'post',
//     url: url,
//     data:params,
//   })
// }

export function removeNodeById(param) {
  return request({
    url: '/system/dict/type/removeNodeById',
    method: 'delete',
    params: param
  })
}
export function addOrUpdateNodeData(param) {
  return request({
    url: '/system/dict/type/addOrUpdateNodeData',
    method: 'post',
    data: param
  })
}
export function queryByLabel(param) {
  return request({
    url: '/system/dict/type/queryByLabel',
    method: 'get',
    params: param
  })
}

export function getTreeByDictType(param) {
  return request({
    url: '/system/dict/type/getTreeByDictType',
    method: 'get',
    params: param
  })
}
