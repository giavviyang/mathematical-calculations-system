import request from '@/utils/request'

//加载右侧用户的grid
export function selectAll(params) {
  return request({
    url: '/watermark/selectAll',
    method: 'get',
    params: params
  })
}

//新增配置
export function insert(params) {
  return request({
    url: '/watermark/insert',
    method: 'post',
    params: params
  })
}

//删除配置
export function deleteStorage(params) {
  return request({
    url: '/watermark/delete',
    traditional:true,
    method: 'delete',
    params: params
  })
}

//修改配置
export function updateById(params) {
  return request({
    url: '/watermark/updateById',
    method: 'post',
    params: params
  })
}

//启用配置
export function active(params) {
  return request({
    url: '/watermark/active',
    method: 'put',
    params: params
  })
}

//停用配置
export function disable(params) {
  return request({
    url: '/watermark/disable',
    method: 'put',
    params: params
  })
}
