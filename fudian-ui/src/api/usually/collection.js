import request from '@/utils/request'


export function pagingQueryNewsNotice(params) {
  return request({
    url: '/newsNotice/pagingQueryNewsNotice',
    method: 'post',
    params: params
  })
}
export function removeNewsNotice(params) {
  return request({
    url: '/newsNotice/removeNewsNotice',
    method: 'post',
    data: params
  })
}
export function readNewsNotice(params) {
  return request({
    url: '/newsNotice/readNewsNotice',
    method: 'post',
    data: params
  })
}
