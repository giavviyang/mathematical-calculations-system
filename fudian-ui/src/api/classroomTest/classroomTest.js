import request from '@/utils/request'

export function queryClassifyErrorTopic(params) {
  return request({
    url: '/classroomTest/queryClassifyErrorTopic',
    method: 'post',
    params: params
  })
}
export function queryStudentErrorTopic(params) {
  return request({
    url: '/classroomTest/queryStudentErrorTopic',
    method: 'post',
    params: params
  })
}
