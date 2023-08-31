import request from '@/utils/request'

// 新增常用模板
export function insertTemplate(params) {
    return request({
      url: '/wfTemplate/insertTemplate',
      method: 'post',
      contentType:false,
      processData:false,
      headers: {'Content-Type': 'multipart/form-data'},
      data:params
    })
  }


// 编辑常用模板
export function updateTemplate(params) {
  return request({
    url: '/wfTemplate/updateTemplate',
    method: 'post',
    contentType:false,
    processData:false,
    headers: {'Content-Type': 'multipart/form-data'},
    data:params
  })
}


// 删除多条常用模板
export function delTemplatesByIds(params) {
  return request({
    url: '/wfTemplate/delTemplatesByIds',
    method: 'delete',
    params:params
  })
}


// 获取常用模板列表
export function getTemplateList(params) {
  return request({
    url: '/wfTemplate/getTemplateList',
    method: 'get',
    params:params
  })
}


// 获取pdf文件路径
export function getPdfFileByTemplateId(params) {
  return request({
    url: '/wfTemplate/getPdfFileByTemplateId',
    method: 'get',
    params:params
  })
}


// 获取多个word文件路径
export function getWordFileByTemplateIds(params) {
    return request({
      url: '/wfTemplate/getWordFileByTemplateIds',
      method: 'get',
      params:params
    })
  }


// 获取多个word文件路径
export function getFileName(params) {
  return request({
    url: '/wfTemplate/getFileName',
    method: 'get',
    params:params
  })
}