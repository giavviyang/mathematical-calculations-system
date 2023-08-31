import request from '@/utils/request'
// 分页查询锁定数据
export function pagingQueryUnlock(params) {
  return request({
    url: '/unlock/pagingQueryUnlock',
    method: 'post',
    params: params
  })
}
// 修改锁定数据
export function modifyUnlockById(params) {
  return request({
    url: '/unlock/modifyUnlockById',
    method: 'post',
    data: params
  })
}
