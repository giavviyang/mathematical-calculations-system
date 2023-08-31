//库房结构管理接口
import request from '@/utils/request'

export function queryRoomStruct(params){
  return request({
    url: '/store/struct',
    method: 'get',
    params: params
  })
}

export function insertRoomStruct(params){
  return request({
    url: '/store/struct',
    method: 'post',
    params: params
  })
}

export  function updateRoomStruct(params){
  return request({
    url: '/store/struct/layer',
    method: 'put',
    params: params
  })
}

export  function deleteRoomStruct(params){
  return request({
    url: '/store/struct/batch',
    method: 'delete',
    params: params
  })
}

export  function checkRoomStruct(params){
  return request({
    url: '/store/struct/checks',
    method: 'get',
    params: params
  })
}


export  function updateStructName(params){
  return request({
    url: '/store/struct',
    method: 'put',
    params: params
  })
}


