import request from '@/utils/request'

// 获取年份
export function queryYear() {
  return request({
    url: '/a01/queryYear',
    method: 'get',
  })
}

// 接收记录
export function queryE01z5s(params) {
  return request({
    url: '/a01/queryE01z5s',
    method: 'get',
    params: params
  })
}
// 编辑
export function updateE01z5(params) {
  return request({
    url: '/a01/updateE01z5',
    method: 'put',
    params: params
  })
}
// 删除
export function deleteE01z5s(params) {
  return request({
    url: '/a01/deleteE01z5s',
    method: 'delete',
    params: params
  })
}
//获取库房位置Map
export function queryRoomArch() {
  return request({
    url: '/a01/queryRoomArch',
    method: 'get',
  })
}
//获取部门Map
export function queryDeptMap() {
  return request({
    url: '/a01/queryDeptMap',
    method: 'get',
  })
}
//获取全宗Map
export function queryOrgMap() {
  return request({
    url: '/a01/queryOrgMap',
    method: 'get',
  })
}
