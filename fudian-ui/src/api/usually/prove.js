import request from '@/utils/request'
export function getList(params) {
  return request({
    url: '/issue/getList',
    method: 'get',
    params: params
  })
}
export function deleteById(params) {
  return request({
    url: '/issue/deleteById',
    method: 'delete',
    params: params
  })
}
export function quertRelativeList(params) {
  return request({
    url: '/issue/quertRelativeList',
    method: 'get',
    params: params
  })
}

export function savePorve(params) {
  return request({
    url: '/issue/savePorve',
    method: 'post',
    params: params
  })
}
export function saveEdit(params) {
  return request({
    url: '/issue/saveEdit',
    method: 'post',
    params: params
  })
}

export function createPdf(params) {
  return request({
    url: '/issue/createPdf',
    method: 'get',
    params: params
  })
}
export function exportWord(params) {
  return request({
    url: '/issue/exportWord',
    method: 'get',
    params: params
  })
}export function downloadHai(params) {
  return request({
    url: '/issue/downloadHai',
    method: 'get',
    params: params
  })
}
