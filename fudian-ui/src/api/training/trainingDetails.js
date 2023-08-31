import request from '@/utils/request'

export function pageingQueryQuestionSetting(params) {
  return request({
    url: '/klc/pageingQueryQuestionSetting',
    method: 'post',
    params: params
  })
}
export function modifyIsRandom(params) {
  return request({
    url: '/klc/modifyIsRandom',
    method: 'post',
    params: params
  })
}
