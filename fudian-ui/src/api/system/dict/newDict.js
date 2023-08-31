import request from '@/utils/request'

// 查询字典类型列表
export function query(query) {
  return request({
    url: '/sysDictionary/queryTreeById',
    method: 'get',
    params: query
  })
}

export function modify(query) {
  return request({
    url: '/sysDictionary/updateSysDictionary',
    method: 'put',
    params: query
  })
}

export function insert(query) {
  return request({
    url: '/sysDictionary/insertSysDictionary',
    method: 'post',
    data: query
  })
}

export function deleteByCondition(query) {
  return request({
    url: '/sysDictionary/deleteSysDictionary',
    method: 'delete',
    params: query
  })
}
// 查询字典类型列表
export function getDataByEparentCode(query) {
  return request({
    url: '/sysDictionary/getDataByEparentCode',
    method: 'get',
    params: query
  })
}
