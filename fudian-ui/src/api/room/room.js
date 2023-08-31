//库房管理接口
import request from '@/utils/request'

//查询库房
export function queryRoomList(query) {
  return request({
    url: '/store/room',
    method: 'get',
    params: query
  })
}

//新增库房
export function insertRoom(params) {
  return request({
    url: '/store/room',
    method: 'post',
    params: params
  })
}

//修改库房
export function updateRoom(params) {
  return request({
    url: '/store/room',
    method: 'put',
    params: params
  })
}

//检查库房内是否存在档案
export function checkRoom(id) {
  return request({
    url: '/store/room/check/' + id,
    method: 'get',
    // params: params
  })
}

//删除库房
export function deleteRoom(id) {
  return request({
    url: '/store/room/' + id,
    method: 'delete',
    // params: params
  })
}
