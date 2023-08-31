package com.fudian.project.system.controller;

import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.pojo.SysPermission;
import com.fudian.project.system.service.SysPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xyh
 * @title: SysPermissionController
 * @projectName platform
 * @description: TODO 权限控制类
 * @date 2019/12/30 18:06
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("permission")
@OperTitle("权限管理")
public class SysPermissionController {

    private static final Logger log = LoggerFactory.getLogger(SysPermissionController.class);

    @Autowired
    private SysPermissionService sysPermissionService;


    /**
     * 将权限信息存入数据库
     * @param sysPermission  权限对象
     * @return  CommonResult
     */
    @PostMapping("insertSysPermission")
    @OperInfo(info = "新增权限")
    public CommonResult insertSysPermission(SysPermission sysPermission){
        CommonResult commonResult=sysPermissionService.insertSysPermission(sysPermission);
        return  commonResult;
    }

    /**
     * 更新权限数据
     * @param sysPermission
     * @return  CommonResult
     */
    @PutMapping("updateSysPermissionById")
    public CommonResult updateSysPermissionById(SysPermission sysPermission){
        CommonResult commonResult= sysPermissionService.updateSysPermissionById(sysPermission);
        return  commonResult;
    }

    /**
     * 删除当前的权限数据
     * @param sysPermission
     * @return  CommonResult
     */
    @DeleteMapping("deleteSysPermissionById")
    @OperInfo(info = "删除权限")
    public CommonResult deleteSysPermissionById(SysPermission sysPermission){
        CommonResult commonResult =sysPermissionService.deleteSysPermissionById(sysPermission);
        return commonResult;
    }

    /**
     * 分页查询当前权限数据条目
     * @param sysPermission 权限对象
     * @return CommonGridResult
     */
    @GetMapping("queryPermissionPageList")
    @OperInfo(info = "查询权限")
    public CommonGridResult queryPermissionPageList(SysPermission sysPermission){
        CommonGridResult commonGridResult =sysPermissionService.queryPermissionPageList(sysPermission);
        return  commonGridResult;
    }

    /**
     * 查询权限数据
     * @param sysPermission 权限对象
     * @return List<SysPermission>
     */
    @GetMapping("queryPermissionList")
    @OperInfo(info = "查询权限")
    public CommonResult queryPermissionList(SysPermission sysPermission, String  userId){
        CommonResult commonResult=new CommonResult();
        List<SysPermission> list =sysPermissionService.queryPermissionList(sysPermission,userId);
        commonResult.setCode(200);
        commonResult.setData(list);
        return  commonResult;
    }


    /**
     * 默认查询当前的所有权限信息
     * 以list<map>的格式返回
     * @return List<Map<String,Object>>
     */
    @GetMapping("queryPermissionListMap")
    @OperInfo(info = "查询权限")
    public CommonResult queryPermissionListMap(){
        List<Map<String,Object>> mapList =sysPermissionService.queryPermissionListMap();
        CommonResult commonResult=new CommonResult();
        commonResult.setCode(200);
        commonResult.setData(mapList);
        return  commonResult;
    }

    /**
     * 依据角色id查询数据权限
     * @param roleId 权限对象
     * @return CommonResult
     */
    @GetMapping("queryPermissionListByRoleId")
    @OperInfo(info = "查询权限")
    public CommonResult queryPermissionListByRoleId(String roleId){
        List<SysPermission>   list =sysPermissionService.queryPermissionListByRoleId(roleId);
        CommonResult commonResult=new CommonResult();
        commonResult.setCode(200);
        commonResult.setData(list);
        return  commonResult;
    }



    /**
     * 依据map查询出
     * 当前得数据
     * @param permissionName  参数
     * @param roleId  参数
     * @return  CommonResult
     */
    @GetMapping("queryPermissionListByMap")
    @OperInfo(info = "查询权限")
    public CommonResult queryPermissionListByMap(String permissionName, String roleId){
        Map<String,String>  map=new HashMap<>(2);
        map.put("permissionName",permissionName);
        map.put("roleId",roleId);
        List<SysPermission> list=sysPermissionService.queryPerminssionListByParamters(map);
        return new CommonResult(200,"",list);
    }


    /**
     * 依据roleId 查询当前得权限数据
     * @param roleId  参数
     * @return  CommonResult
     */
    @GetMapping("queryRemainingPermissionListByRoleId")
    @OperInfo(info = "查询权限")
    public CommonResult queryRemainingPermissionListByRoleId(String roleId){
        List<SysPermission>   list =sysPermissionService.queryRemainingUserListByRoleId(roleId);
        return  new CommonResult(200,"",list);
    }
}
