package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysRole;

import java.util.List;
import java.util.Map;


public interface SysRoleService extends IService<SysRole> {


    int updateBatch(List<SysRole> list);

    int batchInsert(List<SysRole> list);

    int insertOrUpdate(SysRole record);

    int insertOrUpdateSelective(SysRole record);

    /**
     * 将当前的角色信息插入到当前的
     * 数据表中，当前需要处理用户权限问题
     * @param sysRole 角色对象
     * @return CommonResult
     */
    CommonResult<SysRole> insertSysRole(SysRole sysRole);

    /**
     * 更新角色信息，
     * @param sysRole   角色对象
     * @return commonResult
     */
    CommonResult<SysRole> updateRoleById(SysRole sysRole);


    /**
     * 删除角色
     * @param sysRoleIdList 角色对象
     * @return CommonResult
     */
    CommonResult<SysRole> deleteRoleById(List<String> sysRoleIdList);


    /**
     * 分页查询角色信息
     * @param sysRole 角色对象
     * @return CommonGridResult
     */
    CommonGridResult queryRolePageList(SysRole sysRole);

    /**
     * 查询角色map
     * @return  List<Map<String, Object>>
     */
    List<Map<String, Object>> queryRoleListMap();

    /**
     * 查询所有的角色信息。
     * @param sysRole
     * @return
     */
    List<SysRole> queryRoleList(SysRole sysRole,String userId);

    /**
     * 依据权限id 查询角色下的数据
     * @param sysRole
     * @param permissionId
     * @return
     */
    List<SysRole> queryRoleListByPermissionId(SysRole sysRole, String permissionId);


    /**
     * 根据角色Id获取数据权限
     * @param roleId
     * @return
     */
    CommonResult getInitDataPermission(String roleId);


    /**
     * 更新其他规则的数据权限
     * @param roleId
     * @param dataPermissmions
     * @return
     */
    CommonResult updateOterDataPermission(String roleId, String dataPermissmions);

}
