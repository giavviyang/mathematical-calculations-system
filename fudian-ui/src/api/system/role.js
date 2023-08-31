import request from '@/utils/request'
export function queryRolePageList(params) {
  return request({
    url: '/role/queryRolePageList',
    method: 'get',
    params: params
  })
}
export function insertSysRole(params) {
  return request({
    url: '/role/insertSysRole',
    method: 'post',
    params: params
  })
}
export function deleteRoleById(params) {
  return request({
    url: '/role/deleteRoleById',
    method: 'delete',
    params: params
  })
}
export function updateRoleById(params) {
  return request({
    url: '/role/updateRoleById',
    method: 'put',
    params: params
  })
}

/**
 * xyh添加
 */
export function queryRoleListMap() {
  return request({
    url: '/role/queryRoleListMap',
    method: 'GET',
  })
}


/**
 * xyh添加
 */
export function querySysRolePermissionListById(params) {
  return request({
    url: '/rolePermission/querySysRolePermissionListById',
    method: 'GET',
    params:params
  })
}


/**
 * 保存部门
 */
export function saveDataPermissions(params) {
  return request({
    url: '/dataPermissions/saveDataPermissions',
    method: 'POST',
    params:params
  })
}


/**
 * 已分配人员tableData获取
 */
export function getRoleBindUserByDeptIds(params) {
  return request({
    url: '/user/getRoleBindUserByDeptIds',
    method: 'GET',
    params:params
  })
}


/**
 * 未分配人员tableData获取
 */
export function getRoleBindUserByDeptIdsRemain(params) {
  return request({
    url: '/user/getRoleBindUserByDeptIdsRemain',
    method: 'GET',
    params:params
  })
}





/**
 * 根据角色id获取权限以用于数据回显
 */
export function getInitDataPermission(params) {
  return request({
    url: '/role/getInitDataPermission',
    method: 'GET',
    params:params
  })
}


/**
 * 新增或移除干部权限
 */
export function addOrRemoveA01Permission(params) {
  return request({
    url: '/role/addOrRemoveA01Permission',
    method: 'PUT',
    params:params
  })
}

/**
 * 其他规则
 */
export function updateOterDataPermission(params) {
  return request({
    url: '/role/updateOterDataPermission',
    method: 'PUT',
    params:params
  })
}

/**
 * 获取所有功能树
 */
export function getAllMenuTree() {
  return request({
    url: '/menu/getAllMenuTree',
    method: 'GET',
  })
}


/**
 * 保存功能树
 */
export function batchInsertRoleMenu(params) {
  return request({
    url: '/sysRoleMenu/batchInsertRoleMenu',
    method: 'POST',
    params:params
  })
}


/**
 * 查询回显数据
 */
export function getAllMenuIds(params) {
  return request({
    url: '/sysRoleMenu/getAllMenuIds',
    method: 'GET',
    params:params
  })
}



