import request from '@/utils/request'
export function queryE01z1ListByFlag(params) {
  return request({
    url: '/e01z1/queryE01z1ListByFlag',
    method: 'get',
    params: params
  })
}
export function queryE01z1ListByName(params) {
  return request({
    url: '/e01z1/queryE01z1ListByName',
    method: 'get',
    params: params
  })
}
export function deleteById(params) {
  return request({
    url: '/e01z1/deleteById',
    method: 'delete',
    params: params
  })
}
export function returnPlace(params) {
  return request({
    url: '/e01z1/returnPlace',
    method: 'put',
    params: params
  })
}
