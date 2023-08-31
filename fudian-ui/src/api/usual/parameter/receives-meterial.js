import request from '@/utils/request'

// 获取年份
export function queryYear() {
  return request({
    url: '/e01z1/queryYear',
    method: 'get',
  })
}

// 接收记录
export function queryE01z2s(params) {
  return request({
    url: '/e01z1/queryE01z2List',
    method: 'get',
    params: params
  })
}

// 删除记录
export function deleteE01z2ByIds(params) {
  return request({
    url: '/e01z1/deleteE01z2s',
    method: 'delete',
    params: params
  })
}

  //编辑记录
export function updateE01z2ById(params) {
  return request({
    url: '/e01z1/updateE01z2',
    method: 'put',
    params: params
  })
}
