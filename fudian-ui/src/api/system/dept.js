import request from '@/utils/request'

// 查询部门列表
export function listDept(query) {
  return request({
    url: '/system/dept/queryDeptListByParentId',
    method: 'get',
    params: query
  })
}
// 根据当前登录用户获取部门信息
export function getDeptListByLoginUser(query) {
  return request({
    url: '/system/dept/getDeptListByLoginUser',
    method: 'get',
    params: query
  })
}

//获取部门树
export function getDeptTree(params) {
  return request({
    url: '/system/dept',
    method: 'get',
    params:params
  })
}
//获取部门树
export function getDeptTreeByUserSchool(params) {
  return request({
    url: '/system/dept/getDeptTreeByUserSchool',
    method: 'get',
    params:params
  })
}
// 查询部门详细
export function getDept(deptId) {
  return request({
    url: '/system/dept/' + deptId,
    method: 'get'
  })
}

// 查询部门下拉树结构
export function treeselect() {
  return request({
    url: '/system/dept/treeselect',
    method: 'get'
  })
}

// 根据角色ID查询部门树结构
export function roleDeptTreeselect(roleId) {
  return request({
    url: '/system/dept/roleDeptTreeselect/' + roleId,
    method: 'get'
  })
}

// 新增部门
export function addDept(data) {
  return request({
    url: '/system/dept/insertDept',
    method: 'POST',
    params: data
  })
}

// 修改部门
export function updateDept(param) {
  return request({
    url: '/system/dept/updateDeptById',
    method: 'PUT',
    params: param
  })
}

// 删除部门
export function delDept(param) {
  return request({
    url: '/system/dept/deleteDeptById',
    method: 'DELETE',
    params:param
  })
}

// 删除部门
export function deleteDeptByIds(param) {
  return request({
    url: '/system/dept/deleteDeptByIds',
    method: 'DELETE',
    params:param
  })
}


export function querySysDeptByLike(param) {
  return request({
    url: '/system/dept/querySysDeptByLike',
    method: 'GET',
    params:param
  })
}

export function getListDept(param) {
  return request({
    url: '/system/dept/getListDept',
    method: 'GET',
    params:param
  })
}

export function orderSave(param) {
  return request({
    url: '/system/dept/orderSave',
    method: 'PUT',
    params:param
  })
}
