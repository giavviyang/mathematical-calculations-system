import request from '@/utils/request'

//分页查询学生信息
export function pagingQuery(params) {
  return request({
    url: '/classroomTest/pagingQuery',
    method: 'post',
    data: params,
  })
}
//新增数据
export function addTestTable(params) {
  return request({
    url: '/classroomTest/addTestTable',
    method: 'post',
    data: params,
  })
}
//删除数据
export function removeTestTable(params) {
  return request({
    url: '/classroomTest/removeTestTable',
    method: 'post',
    params: params,
  })
}
//批量删除数据
export function batchRemoveTestTable(params) {
  return request({
    url: '/classroomTest/batchRemoveTestTable',
    method: 'post',
    params: params,
  })
}
//修改发布状态
export function modifyReleaseState(params) {
  return request({
    url: '/classroomTest/modifyReleaseState',
    method: 'get',
    params: params,
  })
}
//查询老师的授课班级
export function getUserClassRoom() {
  return request({
    url: '/system/dept/getUserClassRoom',
    method: 'get',
  })
}
