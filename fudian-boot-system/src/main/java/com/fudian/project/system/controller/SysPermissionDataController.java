package com.fudian.project.system.controller;

import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.pojo.SysPermissionData;
import com.fudian.project.system.service.SysPermissionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xyh
 * @title: SysPermissionData
 * @projectName platform
 * @description: TODO 权限与数据权限关联
 * @date 2019/12/28 15:13
 */
@RestController
@RequestMapping("permissionData")
@OperTitle("权限数据管理")
public class  SysPermissionDataController {


    private static final Logger log = LoggerFactory.getLogger(SysPermissionDataController.class);

    @Autowired
    private SysPermissionDataService sysPermissionDataService;

    /**
     * 向权限数据关联表中插入数据
     * @param sysPermissionDatas 权限数据关联表
     * @return CommonResult
     */
    @PostMapping("insertSysPermissionData")
    @OperInfo(info = "新增权限数据")
    public CommonResult insertSysPermissionData(List<SysPermissionData> sysPermissionDatas){
        CommonResult commonResult=sysPermissionDataService.insertSysPermissionData(sysPermissionDatas);
        return commonResult ;
    }

    /**
     * 依据权限id查询当前权限下
     * 的所有数据权限。
     * @param permissionId  权限id
     * @return   List<String>
     */
    @GetMapping("queryDataPermissionIdsByPermissionId")
    @OperInfo(info = "查询权限数据")
    public  List<String>  queryDataPermissionIdsByPermissionId(String  permissionId){
        List<String>   dataPermissionIds=    sysPermissionDataService.queryDataPermissionIdsByPermissionId(permissionId);
        return  dataPermissionIds;
    }

    /**
     * 依据权限id删除
     * @param permissionId  权限id
     * @return CommonResult
     */
    @DeleteMapping("deletePermissionDataByPermissionId")
    @OperInfo(info = "删除权限数据")
    public CommonResult deletePermissionDataByPermissionId(String permissionId){
        CommonResult commonResult=sysPermissionDataService.deletePermissionDataByPermissionId(permissionId);
        return   commonResult;
    }

    /**
     * 删除指定的数据
     * @param sysPermissionData  权限数据表对象
     * @return CommonResult
     */
    @DeleteMapping("deletePermissionDataByMap")
    @OperInfo(info = "删除权限数据")
    public CommonResult deletePermissionDataByMap(SysPermissionData sysPermissionData){
        CommonResult commonResult=sysPermissionDataService.deletePermissionDataByMap(sysPermissionData);
        return   commonResult;
    }

}
