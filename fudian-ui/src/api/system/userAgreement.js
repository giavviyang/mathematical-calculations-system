import request from '@/utils/request'

// 查询协议
export function pagingQueryAgreement(query) {
  return request({
    url: '/userAgreement/pagingQueryAgreement',
    method: 'get',
    params: query
  })
}
// 添加协议
export function addAgreement(query) {
  return request({
    url: '/userAgreement/addAgreement',
    method: 'post',
    params: query
  })
}
// 修改协议
export function modifyAgreementById(query) {
  return request({
    url: '/userAgreement/modifyAgreementById',
    method: 'post',
    params: query
  })
}
// 根据id查询用户协议
export function queryAgreementById(query) {
  return request({
    url: '/userAgreement/queryAgreementById',
    method: 'post',
    params: query
  })
}
// 修改启用状态
export function modifyAgreementStartById(query) {
  return request({
    url: '/userAgreement/modifyAgreementStartById',
    method: 'post',
    params: query
  })
}
// 根据ids删除用户协议
export function removeAgreementById(query) {
  return request({
    url: '/userAgreement/removeAgreementById',
    method: 'post',
    data: query
  })
}
