package com.fudian.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysRolePermission;

import java.util.List;

public interface SysRolePermissionService extends IService<SysRolePermission> {


    int updateBatch(List<SysRolePermission> list);

    int updateBatchSelective(List<SysRolePermission> list);

    int batchInsert(List<SysRolePermission> list);

    int insertOrUpdate(SysRolePermission record);

    int insertOrUpdateSelective(SysRolePermission record);

    /**
     * 向角色数据关联表中添加数据
     * @param sysRolePermission
     * @return CommonResult
     */
    CommonResult insertRolePermission(SysRolePermission sysRolePermission);

    /**
     * 依据角色id查询当前
     * 用户的权限id
     * @param sysRoleIdList
     * @return
     */
    List<SysRolePermission>  querySysRolePermission(List<String>  sysRoleIdList);

    /**
     * 查询当角色绑定的权限数据
     * @param sysRolePermission
     * @return
     */
    List<SysRolePermission> querySysRolePermissionListById(SysRolePermission sysRolePermission);

    /**
     *
     * @param roleId
     * @param jsonStr
     * @return
     */
    CommonResult insertSysRolePermissionInRole(String roleId, String jsonStr);

    CommonResult<SysRolePermission> deleteSysRolePermissionInRole(SysRolePermission sysRolePermission);

}

