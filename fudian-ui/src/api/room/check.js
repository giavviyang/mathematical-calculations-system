import request from '@/utils/request'

export function queryCheclList(params){
  return request({
    url: '/store/check',
    method: 'get',
    params: params
  })
}


export function addCheck(params){
  return request({
    url: '/store/check',
    method: 'post',
    params: params
  })
}

export function updateCheck(params){
  return request({
    url: '/store/check',
    method: 'put',
    params: params
  })
}

export function deleteCheck(id){
  return request({
    url: '/store/check/' + id,
    method: 'delete',
  })
}

export function deleteChecks(id){
  return request({
    url: '/store/check/deletes',
    params:{ids:id},
    method: 'delete',
  })
}
