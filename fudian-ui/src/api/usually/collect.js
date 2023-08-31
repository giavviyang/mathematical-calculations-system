import request from '@/utils/request'
export function getList(params) {
  return request({
    url: '/collcet/getList',
    method: 'get',
    params: params
  })
}
export function addArchiveColl(params) {
  return request({
    url: '/collcet/addArchiveColl',
    method: 'put',
    params: params
  })
}
export function getA01List(params) {
  return request({
    url: '/collcet/getA01List',
    method: 'get',
    params: params
  })
}
export function removeRecord(params) {
  return request({
    url: '/collcet/removeRecord',
    method: 'delete',
    params: params
  })
}
export function adjustDept(params) {
  return request({
    url: '/collcet/adjustDept',
    method: 'put',
    params: params
  })
}
export function adjustState(params) {
  return request({
    url: '/collcet/adjustState',
    method: 'put',
    params: params
  })
}
export function getHighSearch(params) {
  return request({
    url: '/collcet/getHighSearch',
    method: 'get',
    params: params
  })
}
export function saveArchiveLock(params) {
  return request({
    url: '/collcet/saveArchiveLock',
    method: 'put',
    params: params
  })
}

export function updateA01(params) {
  return request({
    url: '/collcet/updateA01',
    method: 'put',
    params: params
  })
}
