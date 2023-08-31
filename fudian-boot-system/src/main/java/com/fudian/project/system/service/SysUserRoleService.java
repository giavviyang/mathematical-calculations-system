package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysUserRole;

import java.util.List;

public interface SysUserRoleService extends IService<SysUserRole> {


    int updateBatch(List<SysUserRole> list);

    int batchInsert(List<SysUserRole> list);

    int insertOrUpdate(SysUserRole record);

    int insertOrUpdateSelective(SysUserRole record);

    /**
     * 向用户角色关联表中插入数据
     *
     * @param sysUserRole
     * @return CommonResult<SysUserRole>
     */
    CommonResult<SysUserRole> insertSysUserRole(SysUserRole sysUserRole);

    /**
     * 向用户角色关联表中插入多条数据
     *
     * @param sysUserRole
     * @return CommonResult<SysUserRole>
     */
    CommonResult<SysUserRole> insertSysUserRoles(SysUserRole sysUserRole);

    /**
     * 依据角色id 删除数据
     *
     * @param roleId 角色Id
     * @return CommonResult<SysUserRole>
     */
    CommonResult<SysUserRole> deleteSysUserRoleByRoleId(String roleId);

    /**
     * 依据角色id获取当前
     *
     * @param sysUserRole 角色对象
     * @return List<String>
     */
    List<String> querySysUserIdByRoleId(SysUserRole sysUserRole);

    /**
     * 批量更细
     * @param list 用户角色关联对象
     * @return int
     */
    int updateBatchSelective(List<SysUserRole> list);

    /**
     * 查询用户关联数据
     * @param userId 用户id
     * @return  List<SysUserRole>
     */
    List<SysUserRole>  querySysUserRoleList(String userId);

    /**
     * 依据用户id 查询当前用户关联的所有的角色id
     * @param sysUserRole 用户权限数据关联对象
     * @return CommonResult
     */
    List<SysUserRole> querySysUserPermissionListById(SysUserRole sysUserRole);

    /**
     * 向用户角色关联表中插入数据
     * @param  roleId 用户与角色关联表对象
     * @param  jsonStr  用户id得字符串集合
     * @return  CommonResult<SysUserRole>
     */
    CommonResult<SysUserRole> insertSysUserRoleInRole(String roleId, String jsonStr);

    /**
     *依据角色id 删除数据
     * @param sysUserRole  用户与角色关联表对象
     * @return CommonResult<SysUserRole>
     */
    CommonResult<SysUserRole> deleteSysUserRoleInRole(SysUserRole sysUserRole);
}

