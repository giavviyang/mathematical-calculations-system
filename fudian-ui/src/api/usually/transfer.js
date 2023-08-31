import request from '@/utils/request'


/*勤奋榜*/
export function diligenceRanking(params) {
  return request({
    url: '/sysRanking/diligenceRanking',
    method: 'post',
    params: params
  })
}
/*勤奋榜*/
export function progressRanking(params) {
  return request({
    url: '/sysRanking/progressRanking',
    method: 'post',
    params: params
  })
}
/*勤奋榜*/
export function addStars(params) {
  return request({
    url: '/sysRanking/addStars',
    method: 'post',
    params: params
  })
}
/*勤奋榜*/
export function levelRanking(params) {
  return request({
    url: '/sysRanking/levelRanking',
    method: 'post',
    params: params
  })
}
