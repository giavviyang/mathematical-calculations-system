package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysPermission;

import java.util.List;
import java.util.Map;

public interface SysPermissionService extends IService<SysPermission> {


    int updateBatch(List<SysPermission> list);

    int batchInsert(List<SysPermission> list);

    int insertOrUpdate(SysPermission record);

    int insertOrUpdateSelective(SysPermission record);

    /**
     * 将权限信息存入数据库
     * @param sysPermission  权限对象
     * @return  CommonResult
     */
    CommonResult insertSysPermission(SysPermission sysPermission);

    /**
     * 更新权限数据
     * @param sysPermission
     * @return  CommonResult
     */
    CommonResult updateSysPermissionById(SysPermission sysPermission);

    /**
     * 删除当前的权限数据
     * @param sysPermission
     * @return  CommonResult
     */
    CommonResult deleteSysPermissionById(SysPermission sysPermission);

    /**
     * 分页查询当前权限数据条目
     * @param sysPermission 权限对象
     * @return CommonGridResult
     */
    CommonGridResult queryPermissionPageList(SysPermission sysPermission);

    /**
     * 查询当前权限数据条目
     * @param sysPermission 权限对象
     * @return List<SysPermission>
     */
    List<SysPermission> queryPermissionList(SysPermission sysPermission,String  userId);

    /**
     * 查询所有的下拉框数据
     * @return   List<Map<String,Object>>
     */
    List<Map<String,Object>> queryPermissionListMap();

    /**
     * 依据角色id查询数据权限
     * @param roleId 权限对象
     * @return CommonResult
     */
    List<SysPermission> queryPermissionListByRoleId(String roleId);


    /**
     * 依据map查询出
     * 当前得数据
     * @param map  参数
     * @return  CommonResult
     */
    List<SysPermission> queryPerminssionListByParamters(Map<String, String> map);


    /**
     * 依据roleId 查询当前得权限数据
     * @param roleId  参数
     * @return  CommonResult
     */
    List<SysPermission> queryRemainingUserListByRoleId(String roleId);
}
