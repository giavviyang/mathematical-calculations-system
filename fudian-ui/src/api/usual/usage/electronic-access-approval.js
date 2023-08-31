import request from '@/utils/request'
//审批接口
export function e01zcApproval(jsonData) {
  return request({
    url: '/test/approval',
    method: 'put',
    data:jsonData
  })
}
//获取查阅记录
export function getApprovals(params) {
  return request({
    url: '/test/getApprovals',
    method: 'get',
    params: params
  })
}



