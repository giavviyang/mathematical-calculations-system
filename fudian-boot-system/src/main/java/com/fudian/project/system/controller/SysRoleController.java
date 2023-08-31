package com.fudian.project.system.controller;

import com.alibaba.fastjson.JSON;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.cache.DataPermissionCache;
import com.fudian.project.system.pojo.SysRole;
import com.fudian.project.system.pojo.SysRolePermission;
import com.fudian.project.system.service.SysDataPermissionService;
import com.fudian.project.system.service.SysRoleDataService;
import com.fudian.project.system.service.SysRolePermissionService;
import com.fudian.project.system.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @title: SysRoleController
 * @projectName platform
 * @description:  角色
 * @date 2019/12/30 9:42
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("role")
@OperTitle("角色管理")
public class SysRoleController {

    private static final Logger log = LoggerFactory.getLogger(SysRoleController.class);


    /**
     * 角色服务层
     */
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 角色权限关联服务层
     */
    @Autowired
    private SysRolePermissionService sysRolePermissionService;



    @Autowired
    private SysDataPermissionService sysDataPermissionService;

    @Autowired
    private SysRoleDataService sysRoleDataService;

    @Autowired
    private DataPermissionCache dataPermissionCacheService;

    /**
     * 将当前的角色信息插入到当前的
     * 数据表中，当前需要处理用户权限问题
     * @param sysRole 角色对象
     * @return CommonResult
     */
    @PostMapping("insertSysRole")
    @OperInfo(info = "新增角色")
    public CommonResult<SysRole> insertSysRole(SysRole sysRole){
        return  sysRoleService.insertSysRole(sysRole);
    }

    /**
     * 更新角色信息
     * @param sysRole   角色对象
     * @return commonResult
     */
    @PutMapping("updateRoleById")
    @OperInfo(info = "修改角色")
    public CommonResult<SysRole> updateRoleById(SysRole sysRole){
        return   sysRoleService.updateRoleById(sysRole);
    }

    /**
     * 删除角色
     * @param param 角色对象
     * @return CommonResult
     */
    @DeleteMapping("deleteRoleById")
    @OperInfo(info = "删除角色")
    public CommonResult<SysRole> deleteRoleById(String param){
        List<SysRole>  sysRoles=JSON.parseArray(param, SysRole.class);
        return  sysRoleService.deleteRoleById(sysRoles.stream().map(e->e.getRoleId()).collect(Collectors.toList()));
    }

    /**
     * 分页查询所有的角色信息。
     * @param sysRole 角色对象
     * @return  CommonGridResult
     */
    @GetMapping("queryRolePageList")
    @OperInfo(info = "查询角色列表")
    public CommonGridResult queryRolePageList(SysRole sysRole){
        return sysRoleService.queryRolePageList(sysRole);
    }

    /**
     * 查询所有的角色信息。
     * @param sysRole 角色对象
     * @param  userId 用户id
     * @return CommonResult
     */
    @GetMapping("queryRoleList")
    @OperInfo(info = "查询角色列表")
    public CommonResult queryRoleList(SysRole sysRole, String userId){
        List<SysRole> list=  sysRoleService.queryRoleList(sysRole,userId);
        return  new CommonResult(200,"",list);
    }

    /**
     * 查询所有的角色信息。
     * @param sysRole  角色对象
     * @param permissionId 权限idinsertSysRole
     * @return CommonResult 公共方法
     */
    @GetMapping("queryRoleListByPermissionId")
    @OperInfo(info = "查询角色列表")
    public CommonResult queryRoleListByPermissionId(SysRole sysRole, String permissionId){
        List<SysRole> list=  sysRoleService.queryRoleListByPermissionId(sysRole,permissionId);
        return  new CommonResult(200,"",list);
    }

    /**
     * 查询所有的角色信息
     * 当前方法用于下拉框选项
     * map中的key值分别为 角色id 与角色名称
     * @return List<Map<String,Object>>
     */
    @GetMapping("queryRoleListMap")
    @OperInfo(info = "查询角色列表")
    public CommonResult queryRoleListMap(){
        List<Map<String,Object>> mapList=sysRoleService.queryRoleListMap();
        return  new CommonResult(200,"",mapList);
    }


    /**
     *
     * @param sysRolePermission 权限对象
     * @return  CommonResult 公共结果
     */
    @PostMapping("insertRolePermission")
    @OperInfo(info = "角色新增权限")
    public CommonResult insertRolePermission(SysRolePermission sysRolePermission){
        return  sysRolePermissionService. insertRolePermission(sysRolePermission);
    }

    /**
     * 根据roleId获取所有数据权限  不包括人员规则的权限。
     * @param roleId
     * @return
     */
    @GetMapping("getInitDataPermission")
    @OperInfo(info = "查询数据权限")
    public CommonResult getInitDataPermission(String roleId) {
        return sysRoleService.getInitDataPermission(roleId);
    }


    /**
     * 更新其他规则的数据权限
     * @param roleId
     * @param otherPermissions
     * @return
     */
    @PutMapping("updateOterDataPermission")
    @OperInfo(info = "修改干部档案权限")
    public CommonResult updateOterDataPermission(String roleId, String otherPermissions){
        try {
            sysRoleService.updateOterDataPermission(roleId, otherPermissions);
        }catch(Exception e){
            return CommonResult.error(500,"保存失败");
        }
        return CommonResult.success("保存成功");
    }

}
