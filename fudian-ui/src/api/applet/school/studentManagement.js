import request from '@/utils/request'


//分页查询学生信息
export function pagingQueryStudents(params) {
  return request({
    url: '/students/pagingQueryStudents',
    method: 'post',
    params: params,
  })
}
//查询教师tree
export function queryTeacher(params) {
  return request({
    url: '/teacher/queryTeacher',
    method: 'post',
    params: params,
  })
}
