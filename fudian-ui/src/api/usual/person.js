import request from '@/utils/request'
// 查询部门树
export function queryDeptListById(params) {
  return request({
    url: '/person/queryDeptListById/'+params,
    method: 'get',
    // params: params
  })
}
// 查询人员
export function queryA01(params) {
  return request({
    url: '/person/queryA01',
    method: 'get',
    params: params
  })
}
