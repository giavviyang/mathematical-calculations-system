//库房档案管理接口
import request from '@/utils/request'

export function queryA01(params) {
  return request({
    url: '/store/arch',
    method: 'get',
    params: params
  })
}


export function insertA01(params) {
  return request({
    url: '/store/arch',
    method: 'post',
    params: params
  })
}

export function downRoom(a01Id) {
  return request({
    url: '/store/arch/' + a01Id,
    method: 'delete',
  })
}

export function updateA01Position(params) {
  return request({
    url: '/store/arch/updateA01Position',
    method: 'put',
    params: params
  })
}

export function selectA01ByDeptIdAndPosiIsNull(params) {
  return request({
    url: '/store/arch/a01',
    method: 'get',
    params:params
  })
}

export function queryRoomA01Summary(roomId) {
  let params = {
    roomId:roomId
  }
  return request({
    url: '/store/arch/summary',
    method: 'get',
    params:params
  })
}

export function queryA01History(a01Id) {

  return request({
    url: '/store/arch/history/' + a01Id ,
    method: 'get',
  })
}

export function queryNodeNum(params) {

  return request({
    url: '/store/struct/querynodenum',
    method: 'get',
    params:params
  })
}


