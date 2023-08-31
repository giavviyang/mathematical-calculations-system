import request from '@/utils/request'
// 查询当前任务批次下所有材料
export function queryMaterialList(params) {
  return request({
    url: '/daks/queryE01z1sByTaskId',
    method: 'get',
    params: params
  })
}
// 删除
export function deleteMaterialList(params) {
  return request({
    url: '/dataPermissions/getSysFile',
    method: 'delete',
    params: params
  })
}
