import request from '@/utils/request'

//加载右侧用户的grid
export function userGrid(params) {
  return request({
    // url: '/user/queryUserList',
    url: '/user/queryUserListNew',
    method: 'get',
    params: params
  })
}
//获取部门的名称
export function userTree(params) {
  return request({
    url: '/system/dept',
    method: 'get',
    params:params
  })
}
//删除用户
export function deleteUser(params) {
  return request({
    url: '/user/deleteUserById/',
    method: 'delete',
    params: params
  })
}
//新增用户
export function addUser(params) {
  return request({
    url: '/user/insertSysUser',
    method: 'post',
    params: params
  })
}
//依据用户id查询过滤出未绑定的权限
export function addPermissions(params) {
  return request({
    url: '/permission/queryPermissionList',
    method: 'get',
    params: params
  })
}
//给当前用户绑定权限
export function savePermissions(params) {
  return request({
    url: '/userPermission/insertUserPermissions',
    method: 'post',
    params: params
  })
}
//依据用户id过滤出未绑定的角色集合
export function queryRoleList(params) {
  return request({
    url: '/role/queryRoleList',
    method: 'get',
    params: params
  })
}
//给当前用户绑定角色
export function insertSysUserRole(params) {
  return request({
    url: '/userRole/insertSysUserRole/',
    method: 'post',
    params: params
  })
}
//给当前用户绑定多个角色
export function insertSysUserRoles(params) {
  return request({
    url: '/userRole/insertSysUserRoles/',
    method: 'post',
    params: params
  })
}

//给当前用户绑定多个角色
export function getRoleListByUserId(params) {
  return request({
    url: '/user/getRoleListByUserId/',
    method: 'GET',
    params: params
  })
}

//依据用户id进行更新updateUserByIdNew
export function updateUserById(params) {
  return request({
    url: '/user/updateUserById',
    method: 'put',
    params: params
  })
}

export function updateUserByIdNew(params) {
  return request({
    url: '/user/updateUserByIdNew',
    method: 'put',
    params: params
  })
}


//依据用户id对用户权限关联表进行查询 获取用户的绑定的权限信息
export function querySysUserPermissionListById(params) {
  return request({
    url: '/userPermission/querySysUserPermissionListById',
    method: 'GET',
    params: params
  })
}


//依据用户id对用户角色关联表进行查询 获取用户的绑定的角色信息
export function querySysUserRoleListById(params) {
  return request({
    url: '/userRole/querySysUserRoleListById',
    method: 'GET',
    params: params
  })
}


/**
 * xyh添加
 */
export function queryUserByRoleId(params) {
  return request({
    url: '/user/queryUserByRoleId',
    method: 'GET',
    params:params
  })
}

/**
 * xyh添加 通过map参数查询数据
 */
export function queryUserListByMap(params) {
  return request({
    url: '/user/queryUserListByMap',
    method: 'GET',
    params:params
  })
}

/**
 * xyh添加 通过map参数查询数据
 */
export function queryRemainingUserListByRoleId(params) {
  return request({
    url: '/user/queryRemainingUserListByRoleId',
    method: 'GET',
    params:params
  })
}

/**
 * xyh添加 在角色页面添加用户角色关联
 */
export function insertSysUserRoleInRole(params) {
  return request({
    url: '/userRole/insertSysUserRoleInRole',
    method: 'POST',
    params:params
  })
}

/**
 * xyh添加 解除角色页面添加用户角色关联
 */
export function deleteSysUserRoleInRole(params) {
  return request({
    url: '/userRole/deleteSysUserRoleInRole',
    method: 'DELETE',
    params:params
  })
}

/**
 * 重置密码
 */
export function resetPassword(params) {
  return request({
    url: '/user/resetPassword',
    method: 'PUT',
    params:params
  })
}

/**
 * 获取所有启用角色列表
 */
export function getAllRoleList(params) {
  return request({
    url: '/user/getAllRoleList',
    method: 'GET',
    params: params,
  })
}

/**
 * 查询教师数据权限回显数据
 */
export function queryUserDeptTree(params) {
  return request({
    url: '/system/dept/queryUserDeptTree',
    method: 'GET',
    params:params
  })
}
/**
* 在教师管理页新增设置数据权限
*/
export function saveUserDataPermissions(params) {
  return request({
    url: '/dataPermissions/saveUserDataPermissions',
    method: 'POST',
    params:params
  })
}
