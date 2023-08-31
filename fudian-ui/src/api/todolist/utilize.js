import request from '@/utils/request'
// 查询待审核
export function getNoApproval(params) {
  return request({
    url: '/test/getNoApproval',
    method: 'get',
    params: params
  })
}
// 个人待审核 全部申请信息
export function personalToAudit(params) {
  return request({
    url: '/test/getDetailData',
    method: 'get',
    params: params
  })
}
// 左侧树节点点击 获取申请权限
export function accessPermissions(params) {
  return request({
    url: '/test/getPersonTree',
    method: 'get',
    params: params
  })
}
// 管理员权限 提交 同意
export function approveDate(params) {
  return request({
    url: '/test/approveDate',
    method: 'put',
    params: params
  })
}
// 管理员权限 提交  不同意
export function noPassApprove(params) {
  return request({
    url: '/test/noPassApprove',
    method: 'put',
    params: params
  })
}
