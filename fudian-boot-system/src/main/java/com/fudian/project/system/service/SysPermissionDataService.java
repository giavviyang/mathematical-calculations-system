package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.pojo.SysPermissionData;

import java.util.List;

public interface SysPermissionDataService extends IService<SysPermissionData> {


    int updateBatch(List<SysPermissionData> list);

    int batchInsert(List<SysPermissionData> list);

    int insertOrUpdate(SysPermissionData record);

    int insertOrUpdateSelective(SysPermissionData record);

    /**
     * 向权限数据关联表中插入数据
     * @param sysPermissionDatas 权限数据关联表
     * @return CommonResult
     */
    CommonResult insertSysPermissionData(List<SysPermissionData> sysPermissionDatas);

    /**
     * 依据权限id查询当前权限下
     * 的所有数据权限。
     * @param permissionId  权限id
     * @return   List<String>
     */
    List<String>  queryDataPermissionIdsByPermissionId(String permissionId);

    /**
     * 依据权限id删除
     * @param permissionId  权限id
     * @return CommonResult
     */
    CommonResult deletePermissionDataByPermissionId(String permissionId);

    /**
     * 删除指定的数据
     * @param sysPermissionData  权限数据表对象
     * @return CommonResult
     */
    CommonResult deletePermissionDataByMap(SysPermissionData sysPermissionData);

    /**
     * 查询当前权限id下的所有数据
     * @param premissionIdList 权限id集合
     * @return List<SysPermissionData>
     */
    List<SysPermissionData>  querySysPermissionDataList(List<String>  premissionIdList);

}
