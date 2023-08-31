package com.fudian.project.system.controller;

import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.pojo.SysRoleMenu;
import com.fudian.project.system.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xyh
 * @title: SysRoleMenuController
 * @projectName standard
 * @description:  当前类为
 * @date 2020/3/12 18:17
 */
@RestController
@RequestMapping("sysRoleMenu")
@OperTitle("角色菜单")
public class SysRoleMenuController {


    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    @PostMapping("insertRoleMenu")
    @OperInfo(info = "新增角色菜单")
    public CommonResult insertRoleMenu(SysRoleMenu sysRoleMenu){


        return    sysRoleMenuService.insertRoleMenu(sysRoleMenu);
    }


    @DeleteMapping("removeRoleMenu")
    @OperInfo(info = "删除角色菜单")
    public CommonResult removeRoleMenu(SysRoleMenu sysRoleMenu){

        return    sysRoleMenuService.removeRoleMenu(sysRoleMenu);

    }

    @PostMapping("batchInsertRoleMenu")
    @OperInfo(info = "新增角色菜单")
    public CommonResult batchInsertRoleMenu(String roleId, String menuIds){
        try {
            sysRoleMenuService.batchInsertRoleMenu(roleId, menuIds);
        }catch (Exception e){
            e.printStackTrace();
            return CommonResult.error(500,"保存失败");
        }
        return CommonResult.success("保存成功");
    }

    @GetMapping("getAllMenuIds")
    @OperInfo(info = "查询角色菜单")
    public CommonResult getAllMenuIds(String roleId){

        return sysRoleMenuService.getAllMenuIds(roleId);
    }

}
