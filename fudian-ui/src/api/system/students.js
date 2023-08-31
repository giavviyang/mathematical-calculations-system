import request from '@/utils/request'

//多条件查询学生信息
export function queryStudentAndClass(params) {
  return request({
    url: '/students/queryStudentAndClass',
    method: 'get',
    params: params
  })
}
//新增学生信息
export function addStudents(params) {
  return request({
    url: '/students/addStudents',
    method: 'post',
    params: params
  })
}
//修改学生信息
export function modifyStudentsById(params) {
  return request({
    url: '/students/modifyStudentsById',
    method: 'post',
    params: params
  })
}
//删除学生信息
export function removeStudentsById(params) {
  return request({
    url: '/students/removeStudentsById',
    method: 'post',
    params: params
  })
}
//上传excel
export function importExcelData(data) {
  return request({
    url: '/students/importExcelData',
    method: 'post',
    data: data,
    timeout: 100000
  })
}
//调整班级
export function changeClass(params) {
  return request({
    url: '/students/changeClass',
    method: 'post',
    params: params
  })
}
//查询学号
export function queryStudentsNumber(params) {
  return request({
    url: '/students/queryStudentsNumber',
    method: 'get',
    params: params
  })
}
//学生毕业
export function studentGraduation(params) {
  return request({
    url: '/students/studentGraduation',
    method: 'post',
    params: params
  })
}
//查询已毕业学生
export function pagingQueryStudentGraduation(params) {
  return request({
    url: '/students/pagingQueryStudentGraduation',
    method: 'post',
    params: params
  })
}
