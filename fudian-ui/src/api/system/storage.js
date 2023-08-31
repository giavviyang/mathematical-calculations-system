import request from '@/utils/request'

//加载右侧用户的grid
export function selectAll(params) {
  return request({
    url: '/storageConfig/selectAll',
    method: 'get',
    params: params
  })
}

//新增配置
export function insert(params) {
  return request({
    url: '/storageConfig/insert',
    method: 'post',
    params: params
  })
}

//删除配置
export function deleteStorage(params) {
  return request({
    url: '/storageConfig/delete',
    traditional:true,
    method: 'delete',
    params: params
  })
}

//修改配置
export function updateById(params) {
  return request({
    url: '/storageConfig/updateById',
    method: 'put',
    params: params
  })
}

//启用配置
export function active(params) {
  return request({
    url: '/storageConfig/active',
    method: 'put',
    params: params
  })
}

//测试配置
export function test(params) {
  return request({
    url: '/storageConfig/test',
    method: 'post',
    params: params
  })
}
