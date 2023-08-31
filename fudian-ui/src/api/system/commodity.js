import request from '@/utils/request'

//获取商品列表
export function getGoodsList(query) {
  return request({
    url: 'applet/goods/getGoodsList',
    method: 'get',
    params: query
  })
}

// 新增商品
export function insertGoodsList(data) {
  return request({
    url: 'applet/goods/insertGoodsList',
    method: 'post',
    params: data
  })
}

// 修改商品
export function updateGoodsListTwo(data) {
  return request({
    url: 'applet/goods/updateGoodsListTwo',
    method: 'post',
    data: data
  })
}

// 删除商品
export function deleteGoodsListByIds(params) {
  return request({
    url: 'applet/goods/deleteGoodsListByIds',
    method: 'delete',
    params:params
  })
}
