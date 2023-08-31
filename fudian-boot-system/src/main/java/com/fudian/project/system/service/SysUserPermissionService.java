package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysUserPermission;

import java.util.List;

public interface SysUserPermissionService extends IService<SysUserPermission> {


    int updateBatch(List<SysUserPermission> list);

    int batchInsert(List<SysUserPermission> list);

    int insertOrUpdate(SysUserPermission record);

    int insertOrUpdateSelective(SysUserPermission record);

    /**
     * 新增用户与权限关联数据
     * @param userId
     * @param data
     * @return
     */
    CommonResult<SysUserPermission> insertUserPermissions(String userId, String data);

    /**
     * 移除当前的帮定
     * @param sysUserPermission
     * @return
     */
    boolean deleteUserPermissionsByPermissionId(SysUserPermission sysUserPermission);

    /**
     * 通过用户id
     * 查询当前用户关联的权限信息
     * @param userId
     * @return List<SysUserPermission>
     */
    List<SysUserPermission>  querySysUserPermissionList(String userId);


    /**
     * 依据用户id 查询当前用户关联的所有的权限id
     * @param sysUserPermission 用户权限数据关联对象
     * @return CommonResult
     */
    List<SysUserPermission> querySysUserPermissionListById(SysUserPermission sysUserPermission);
}
