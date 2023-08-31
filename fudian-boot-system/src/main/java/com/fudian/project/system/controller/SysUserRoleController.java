package com.fudian.project.system.controller;

import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.pojo.SysUserRole;
import com.fudian.project.system.service.SysUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @title: SysUserRoleController
 * @projectName platform
 * @description: TODO 用户角色关联
 * @date 2019/12/28 15:11
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("userRole")
@OperTitle("用户角色")
public class SysUserRoleController {

    private static final Logger log = LoggerFactory.getLogger(SysUserRoleController.class);

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 依据角色id获取当前
     * @param sysUserRole 角色
     * @return List<String>
     */
    @GetMapping("querySysUserIdByRoleId")
    @OperInfo(info = "查询用户")
    public List<String>   querySysUserIdByRoleId(SysUserRole sysUserRole){
        List<String>  list=sysUserRoleService.querySysUserIdByRoleId(sysUserRole);
        return  list;
    }

    /**
     * 向用户角色关联表中插入数据
     * @param sysUserRole 用户与角色关联表对象
     * @return  CommonResult<SysUserRole>
     */
    @PostMapping("insertSysUserRole")
    @OperInfo(info = "角色关联用户")
    public CommonResult<SysUserRole> insertSysUserRole(SysUserRole sysUserRole){
        CommonResult<SysUserRole> commonResult= sysUserRoleService.insertSysUserRole(sysUserRole);
        return  commonResult;
    }

    /**
     * 向用户角色关联表中插入多条数据
     * @param sysUserRole 用户与角色关联表对象
     * @return  CommonResult<SysUserRole>
     */
    @PostMapping("insertSysUserRoles")
    @OperInfo(info = "角色关联用户")
    public CommonResult<SysUserRole> insertSysUserRoles(SysUserRole sysUserRole){
        CommonResult<SysUserRole> commonResult= sysUserRoleService.insertSysUserRoles(sysUserRole);
        return  commonResult;
    }

    /**
     * 向用户角色关联表中插入数据
     * @param  roleId 用户与角色关联表对象
     * @param  jsonStr  用户id得字符串集合
     * @return  CommonResult<SysUserRole>
     */
    @PostMapping("insertSysUserRoleInRole")
    @OperInfo(info = "角色关联用户")
    public CommonResult<SysUserRole> insertSysUserRoleInRole(String roleId, String jsonStr){
        CommonResult<SysUserRole> commonResult= sysUserRoleService.insertSysUserRoleInRole(roleId,jsonStr);
        return  commonResult;
    }


    /**
     *依据角色id 删除数据
     * @param sysUserRole  用户与角色关联表对象
     * @return CommonResult<SysUserRole>
     */
    @DeleteMapping("deleteSysUserRoleInRole")
    @OperInfo(info = "解除角色用户关联")
    public CommonResult<SysUserRole> deleteSysUserRoleInRole(SysUserRole sysUserRole){
        CommonResult<SysUserRole> commonResult  = sysUserRoleService.deleteSysUserRoleInRole(sysUserRole);
        return  commonResult;
    }


    /**
     *依据角色id 删除数据
     * @param sysUserRole  用户与角色关联表对象
     * @return CommonResult<SysUserRole>
     */
    @DeleteMapping("deleteSysUserRoleByRoleId")
    @OperInfo(info = "解除角色用户关联")
    public CommonResult<SysUserRole> deleteSysUserRoleByRoleId(SysUserRole sysUserRole){
        CommonResult<SysUserRole> commonResult  = sysUserRoleService.deleteSysUserRoleByRoleId(sysUserRole.getRoleId());
        return  commonResult;
    }

    /**
     * 依据用户id 查询当前用户关联的所有的角色id
     * @param sysUserRole 用户权限数据关联对象
     * @return CommonResult
     */
    @GetMapping("querySysUserRoleListById")
    @OperInfo(info = "查询用户关联的角色")
    public CommonResult querySysUserRoleListById(SysUserRole sysUserRole){
        List<SysUserRole>   sysUserRoleList=sysUserRoleService.querySysUserPermissionListById(sysUserRole);
        List<String> sysUserRoleIdList=sysUserRoleList.stream().map(e->e.getRoleId()).collect(Collectors.toList());
        return new CommonResult(200,"",sysUserRoleIdList);
    }
}
