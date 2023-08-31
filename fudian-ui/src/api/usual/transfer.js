import request from '@/utils/request'
// 查询岗位列表
export function queryA01List(params) {
  return request({
    url: '/transfer/queryA01List',
    method: 'get',
    params: params
  })
}
export function queryIdAndDate() {
  return request({
    url: '/transfer/queryIdAndDate',
    method: 'get'
  })
}
// 监听
export function queryIdIsUsable(params) {
  return request({
    url: '/transfer/queryIdIsUsable',
    method: 'get',
    params: params
  })
}
// 提交后台
export function insertTransfer(params) {
  return request({
    url: '/transfer/insertTransfer',
    method: 'post',
    data: params
  })
}
// 数据包
export function exportPacket(params) {
  return request({
    url: '/transfer/exportPacket',
    method: 'get',
    params: params,
  })
}
