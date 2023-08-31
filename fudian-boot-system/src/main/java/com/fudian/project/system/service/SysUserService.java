package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysUserRole;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {


    SysUser queryUser(String username, String password);

    SysUser queryUserByUsername(String username);

    int updateBatch(List<SysUser> list);

    int batchInsert(List<SysUser> list);

    int insertOrUpdate(SysUser record);

    int insertOrUpdateSelective(SysUser record);

    /**
     * 当前方法为查询分页用户
     *
     * @param sysUser 系统用户对象
     * @return CommonGridResult
     */
    CommonGridResult queryUserList(SysUser sysUser);

    /**
     * 新
     *
     * @param sysUser
     * @return
     */
    CommonGridResult queryUserListNew(SysUser sysUser);

    /**
     * 新
     *
     * @param sysUser
     * @return
     */
    CommonResult updateUserByIdNew(SysUser sysUser);

    /**
     * 当前数据为向用户中添加数据
     *
     * @param sysUser 系统用户对象
     * @return CommonResult<SysUser>
     */
    CommonResult<SysUser> insertUser(SysUser sysUser);

    /**
     * 依据id更新当前的系统用户
     *
     * @param sysUser 系统用户对象
     * @return CommonResult<SysUser>
     */
    CommonResult<SysUser> updateUserById(SysUser sysUser);

    /**
     * 依据id更新当前的系统用户
     *
     * @param userIdList 系统用户的id拼接的字符串
     * @return CommonResult<SysUser>
     */
    CommonResult<SysUser> deleteUserById(List<String> userIdList);

    /**
     * 依据当前用户中
     *
     * @param permissionId 权限id
     * @return List<SysUser>
     */
    List<SysUser> queryUserByPermissionId(String permissionId);

    /**
     * 依据角色查询当前角色下绑定的用户
     *
     * @param sysUserRole 角色id
     * @return List<SysUser>
     */
    List<SysUser> queryUserByRoleId(SysUserRole sysUserRole);

    /**
     * 依据角色id查询数据权限
     *
     * @param permissionId 权限对象
     * @return CommonResult
     */
    List<SysUser> queryUserListByPermissionId(String permissionId, SysUser sysUser);

    /**
     * 依据角色id查询未绑定的用户数据
     *
     * @param permissionId 权限对象
     * @return CommonResult
     */
    List<SysUser> queryRemainingUserListByPermissionId(String permissionId);

    /**
     * 查询部门用户树
     *
     * @return CommonResult
     */
    CommonResult queryDeptUserTree();


    /**
     * 依据id查询剩余未被绑定当前角色得用户
     *
     * @param roleId 角色id
     * @return List<SysUser>
     */
    List<SysUser> queryRemainingUserListByRoleId(String roleId);

    /**
     * 依据map查询出
     * 当前得数据
     *
     * @param map 参数
     * @return CommonResult
     */
    List<SysUser> queryUserListByParamters(Map<String, String> map);

    CommonGridResult queryAddUserList(SysUser sysUser);

    void addUserAndPermission(String userId, String permissionId);

    /**
     * 重置密码
     *
     * @param sysUser
     * @return
     */
    CommonResult resetPassword(SysUser sysUser);

    /**
     * 禁用账户
     *
     * @param sysUser
     * @return
     */
    CommonResult prohibitUser(SysUser sysUser);

//    /**
//     * 查询用户
//     *
//     * @param deptIds
//     * @param roleId
//     * @return
//     */
//    CommonGridResult getRoleBindUserByDeptIds(String deptIds, String roleId, A01 a01);

//    CommonGridResult getRoleBindUserByDeptIdsRemain(String deptIds, String roleId, A01 a01);

    int updateBatchSelective(List<SysUser> list);

    boolean updateUserPwd(String userId, String password);


    List<SysUser> querySysUserByClassId(String classId);

}






