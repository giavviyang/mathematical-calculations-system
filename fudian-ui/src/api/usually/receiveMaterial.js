import request from  '@/utils/request'

export function pagingQueryAnswer(params) {
  return request({
    url: '/learnPractice/pagingQueryAnswer',
    method: 'post',
    data: params
  })
}

export function pagingQueryClassify(params) {
  return request({
    url: '/learnPractice/pagingQueryClassify',
    method: 'post',
    data: params
  })
}
