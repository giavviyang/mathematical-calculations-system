import request from '@/utils/request'
export function queryPermissionPageList(params) {
  return request({
    url: '/permission/queryPermissionPageList',
    method: 'get',
    params: params
  })
}
export function queryAddUserList(params) {
  return request({
    url: '/user/queryAddUserList',
    method: 'get',
    params: params
  })
}
export function insertSysPermission(params) {
  return request({
    url: '/permission/insertSysPermission',
    method: 'post',
    async:false,
    params: params
  })
}
export function deleteSysPermissionById(params) {
  return request({
    url: '/permission/deleteSysPermissionById',
    method: 'delete',
    params: params
  })
}
export function insertRolePermission(params) {
  return request({
    url: '/role/insertRolePermission',
    method: 'post',
    params: params
  })
}
export function addUserAndPermission(params) {
  return request({
    url: '/user/addUserAndPermission',
    method: 'post',
    params: params
  })
}
export function queryRoleListByPermissionIdEnabled(params) {
  return request({
    url: '/role/queryRoleListByPermissionIdEnabled',
    method: 'get',
    params: params
  })
}

export function queryUserListByPermissionId(params) {
  return request({
    url: '/user/queryUserListByPermissionId',
    method: 'get',
    params: params
  })
}
export function deleteUserPermissionsByPermissionId(params) {
  return request({
    url: '/userPermission/deleteUserPermissionsByPermissionId',
    method: 'delete',
    params: params
  })
}

/* 机构树 */
export function institutionsTree() {
  return request({
    url: '/system/dept/queryDeptListByParentId',
    method: 'get'
  })
}

/* 用户列表 树*/
export function listUsersTree(params) {
  return request({
    url: '/user/queryUserByOrgIds',
    method: 'get',
    params: params
  })
}
/* 材料类型 树*/
export function materialTypeTree(params) {
  return request({
    url: '/sysDictionary/queryMaterialsTree',
    method: 'get',
    params: params
  })
}

/* 材料名称 树*/
export function materialNameTree(params) {
  return request({
    url: '/e01z1/queryE01z1List',
    method: 'get',
    params:params
  })
}
/* 电子材料 树*/
export function sysFileTree(params) {
  return request({
    url: '/sysFile/queryE01z1Grid',
    method: 'get',
    params:params
  })
}

/* 功能权限树 */
export function permissionsTree(params) {
  return request({
    url: '',
    method: 'get',
    params: params
  })
}

/* 部门树 */
export function queryDeptListByParentId() {
  return request({
    url: '/system/dept/queryDeptListByParentId',
    method: 'get'
  })
}
//根据权限id查询的对应的菜单列表
export function selectSysPermissionMenuByPermissionId(params) {
  return request({
    url: '/permissionMenu/selectSysPermissionMenuByPermissionId',
    method: 'GET',
    params:params
  })
}

export function updateSysPermissionById(params) {
  return request({
    url: '/permission/updateSysPermissionById',
    method: 'PUT',
    params:params
  })
}

export function queryPermissionListMap() {
  return request({
    url: '/permission/queryPermissionListMap',
    method: 'GET',
  })
}

export function queryPermissionListByRoleId(params) {
  return request({
    url: '/permission/queryPermissionListByRoleId',
    method: 'GET',
    params:params
  })
}


export function queryPermissionListByMap(params) {
  return request({
    url: '/permission/queryPermissionListByMap',
    method: 'GET',
    params:params
  })
}


export function queryRemainingPermissionListByRoleId(params) {
  return request({
    url: '/permission/queryRemainingPermissionListByRoleId',
    method: 'GET',
    params:params
  })
}

export function insertDataPermissionsByPermissionId(params) {
  return request({
    url: '/dataPermissions/insertDataPermissionsByPermissionId',
    method: 'post',
    params:params
  })
}

export function selectDataPermissionsByPermissionId(params) {
  return request({
    url: '/dataPermissions/selectDataPermissionsByPermissionId',
    method: 'get',
    params:params
  })
}


export function insertSysRolePermissionInRole(params) {
  return request({
    url: '/rolePermission/insertSysRolePermissionInRole',
    method: 'POST',
    params:params
  })
}

export function deleteSysRolePermissionInRole(params) {
  return request({
    url: '/rolePermission/deleteSysRolePermissionInRole',
    method: 'DELETE',
    params:params
  })
}
