import request from '@/utils/request'
export function queryElec(params) {
  return request({
    url: '/test/queryElec',
    method: 'get',
    params: params
  })
}
export function queryBrowse(params) {
  return request({
    url: '/test/queryBrowse',
    method: 'get',
    params: params
  })
}
export function queryBorrow(params) {
  return request({
    url: '/test/queryBorrow',
    method: 'get',
    params: params
  })
}
export function queryAskApplic(params) {
  return request({
    url: '/test/queryAskApplic',
    method: 'get',
    params: params
  })
}
export function queryTemp(params) {
  return request({
    url: '/test/queryTemp',
    method: 'get',
    params: params
  })
}
