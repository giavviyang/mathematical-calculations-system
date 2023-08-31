import request from '@/utils/request'
//获取申请通过的
export function queryE01zcRelaData(e01zcId) {
  return request({
    url: '/test/querye01zcreladata',
    method: 'get',
    params: {
      e01zcId:e01zcId
    }
  })
}

//添加一个申请
export function addE01zcRela(params) {
  return request({
    url: '/test/addE01zcRela',
    method: 'post',
    data: params
  })
}

//获取通过后的材料树
export function getE01z1TreeByRelaId(id) {
  return request({
    url: '/test/getE01z1TreeByRelaId/' + id,
    method: 'get',
  })
}

//添加一个申请
export function check(e01zcId) {
  return request({
    url: '/test/check',
    method: 'get',
    params: {
      e01zcId:e01zcId
    }
  })
}

//到期续查
export function renew(params) {
  return request({
    url: '/test/renew',
    method: 'get',
    params: params
  })
}

//查阅材料
export function getE01zcRela(id) {
  return request({
    url: '/test/getE01zcRela/' + id,
    method: 'get',
  })
}


