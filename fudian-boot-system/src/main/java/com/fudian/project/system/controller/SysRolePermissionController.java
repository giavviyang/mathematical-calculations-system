package com.fudian.project.system.controller;

import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.pojo.SysRolePermission;
import com.fudian.project.system.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @title: SysRolePermissionController
 * @projectName standard
 * @description: TODO
 * @date 2020/1/3 16:49
 */

@RestController
@RequestMapping("rolePermission")
@OperTitle("角色权限管理")
public class SysRolePermissionController {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     *
     * @param sysRolePermission
     * @return  CommonResult
     */
    @PostMapping("insertRolePermission")
    @OperInfo(info = "角色新增权限")
    public CommonResult insertRolePermission(SysRolePermission sysRolePermission){
        CommonResult commonResult=sysRolePermissionService. insertRolePermission(sysRolePermission);
        return  commonResult;
    }

    /**
     *
     * @param sysRolePermission
     * @return  CommonResult
     */
    @PostMapping("insertSysRolePermissionInRole")
    @OperInfo(info = "角色新增权限")
    public CommonResult insertSysRolePermissionInRole(String roleId, String jsonStr){
        CommonResult commonResult=sysRolePermissionService. insertSysRolePermissionInRole(roleId,jsonStr);
        return  commonResult;
    }

    /**
     * 查询当角色绑定的权限数据
     * @param sysRolePermission
     * @return
     */
    @GetMapping("querySysRolePermissionListById")
    @OperInfo(info = "查询角色的权限")
    public CommonResult querySysRolePermissionListById(SysRolePermission sysRolePermission){
        List<SysRolePermission> sysUserPermissionList=sysRolePermissionService.querySysRolePermissionListById(sysRolePermission);
        List<String> sysUserPermissionIdList=sysUserPermissionList.stream().map(e->e.getPermissionId()).collect(Collectors.toList());
        return new CommonResult(200,"",sysUserPermissionIdList);

    }


    /**
     * 依据角色id删除关联数据
     * @param sysRolePermission
     * @return
     */
    @DeleteMapping("deleteSysRolePermissionInRole")
    @OperInfo(info = "删除角色的权限")
    public CommonResult<SysRolePermission> deleteSysRolePermissionInRole(SysRolePermission sysRolePermission){
        CommonResult<SysRolePermission> commonResult  = sysRolePermissionService.deleteSysRolePermissionInRole(sysRolePermission);
        return  commonResult;
    }
}
