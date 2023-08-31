package com.fudian.project.system.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.system.pojo.SysDataPermission;
import com.fudian.api.system.pojo.SysFile;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysRoleData;
import com.fudian.project.system.pojo.SysUserRole;

import java.util.List;
import java.util.Map;

public interface SysDataPermissionService extends IService<SysDataPermission> {


    int updateBatch(List<SysDataPermission> list);

    int batchInsert(List<SysDataPermission> list);

    int insertOrUpdate(SysDataPermission record);

    int insertOrUpdateSelective(SysDataPermission record);

    /**
     * 向数据权限表中添加
     * @param dataPermissions 数据权限表对象
     * @return  CommonResult<SysDataPermission>
     */
    CommonResult<SysDataPermission> insertDataPermissions(List<SysDataPermission> dataPermissions);


    /**
     * 依据id删除当前的数据权限
     * @param dataPermissionsIds
     * @return CommonResult
     */
    CommonResult deleteDataPermissionsById(List<String> dataPermissionsIds);

    /**
     * 查询出当前数据权限id下所有数据
     * @param sysDataPermissionIdList
     * @return   List<SysDataPermission>
     */
    List<SysDataPermission>   querySysDataPermissionList(List<String>  sysDataPermissionIdList);

    CommonResult insertDataPermissionsByPermissionId(String permissionId, String dataPermissions);

    CommonResult selectDataPermissionsByPermissionId(String permissionId);

    List<SysFile> getSysFile(String e01z1id);

    JSONArray getDept();


    JSONArray getType(String a01Id);

    List<E01z1> getE01z1(String a01Id);


    /**
     * 获取角色的数据权限
     * @param userId
     * @return
     */
    Map<String,List<String>> getDataPermission(String userId);


    /**
     * 获取干部数据权限
     * @param userId
     * @return
     */
    List<Map<String,List<String>>> getA01DataPermission(String userId);

    /*
     * 新增部门数据权限
     * @param roleId
     * @param deptIds
     * @return
     */
//    CommonResult insertDeptDatas(String roleId, String deptIds);

    Map<String,Object> setSerachMap(Map<String,Object> map);
}
