import request from '@/utils/request'
export function querySysMenusList(params) {
  return request({
    url: '/menu/querySysMenusList',
    method: 'GET',
    params: params
  })
}

export function insertSysMenu(params) {
  return request({
    url: '/menu/insertSysMenu',
    method: 'POST',
    params: params
  })
}
export function deleteSysMenuById(params) {
  return request({
    url: '/menu/deleteSysMenuById',
    method: 'delete',
    params: params
  })
}


export function updateSysMenuById(params) {
  return request({
    url: '/menu/updateSysMenuById',
    method: 'put',
    params: params
  })
}

export function queryMenuById(params) {
  return request({
    url: '/menu/queryMenuById',
    method: 'GET',
    params: params
  })
}
