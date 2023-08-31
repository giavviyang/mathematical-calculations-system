import request from '@/utils/request'
// 查询待审核
export function getSysFile(params) {
  return request({
    url: '/dataPermissions/getSysFile',
    method: 'get',
    params: params
  })
}