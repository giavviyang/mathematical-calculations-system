package com.fudian.project.system.controller;

import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.pojo.SysUserPermission;
import com.fudian.project.system.service.SysUserPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @title: SysUserPermissionController
 * @projectName platform
 * @description: TODO 用户权限关联
 * @date 2019/12/28 15:11
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("userPermission")
@OperTitle("用户权限")
public class SysUserPermissionController {

    private static final Logger log = LoggerFactory.getLogger(SysUserPermissionController.class);

    @Autowired
    private SysUserPermissionService sysUserPermissionService;

    /**
     * 新增用户与权限关联数据
     * @param sysUserPermissions 用户与权限关联
     * @return CommonResult<SysUserPermission>
     */
    @PostMapping("insertUserPermissions")
    @OperInfo(info = "用户关联权限")
    public CommonResult<SysUserPermission> insertUserPermissions(String  userId , String data){

        CommonResult<SysUserPermission> commonResult=  sysUserPermissionService.insertUserPermissions(userId,data);

        return   commonResult;
    }


    /**
     * 依据权限id 删除用户与权限关联
     * @param permissionsId   权限id
     * @return  CommonResult<SysUserPermission>
     */
    @DeleteMapping("deleteUserPermissionsByPermissionId")
    @OperInfo(info = "解除用户权限的关联关系")
    public CommonResult<SysUserPermission> deleteUserPermissionsByPermissionId(SysUserPermission sysUserPermission){
        CommonResult<SysUserPermission> commonResult=new CommonResult<>();
        boolean boo=  sysUserPermissionService.deleteUserPermissionsByPermissionId(sysUserPermission);
        if (boo) {
            commonResult.setMsg("删除用户数据权限关联成功");
            commonResult.setCode(200);
        }else{
            commonResult.setMsg("删除用户数据权限关联失败，请联系关联员");
            commonResult.setCode(500);
        }
        return  commonResult;
    }

    /**
     * 依据用户Id删除 用户与权限关联表
     * @param userId 用户id
     * @return CommonResult<SysUserPermission>
     */
    @DeleteMapping("deleteUserPermissionsByUserId")
    @OperInfo(info = "解除用户权限的关联关系")
    public CommonResult<SysUserPermission> deleteUserPermissionsByUserId(String userId){
        boolean boo=  sysUserPermissionService.removeById(userId);
        if (boo) {
            CommonResult<SysUserPermission> commonResult=new CommonResult<>(200,"删除用户数据权限关联成功",null);
            return  commonResult;
        }else{
            CommonResult<SysUserPermission> commonResult=new CommonResult<>(500,"删除用户数据权限关联失败，请联系关联员",null);
            return  commonResult;
        }
    }

    /**
     * 依据用户id 查询当前用户关联的所有的权限id
     * @param sysUserPermission 用户权限数据关联对象
     * @return CommonResult
     */
    @GetMapping("querySysUserPermissionListById")
    @OperInfo(info = "查询用户的所有权限")
    public CommonResult querySysUserPermissionListById(SysUserPermission sysUserPermission){
        List<SysUserPermission>   sysUserPermissionList=sysUserPermissionService.querySysUserPermissionListById(sysUserPermission);
        List<String> sysUserPermissionIdList=sysUserPermissionList.stream().map(e->e.getPermissionId()).collect(Collectors.toList());
        return new CommonResult(200,"",sysUserPermissionIdList);
    }
}
