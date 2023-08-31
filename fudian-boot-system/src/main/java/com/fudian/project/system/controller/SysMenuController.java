package com.fudian.project.system.controller;

import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.pojo.SysMenu;
import com.fudian.project.system.service.SysMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xyh
 * @title: SysMenuController
 * @projectName platform
 * @description: TODO 菜单控制类
 * @date 2019/12/30 10:37
 */
@RestController
@RequestMapping("menu")
@OperTitle("菜单控制")
public class SysMenuController {

    private static final Logger log = LoggerFactory.getLogger(SysMenuController.class);

    @Autowired
    private SysMenuService sysMenuService;


    /**
     * 依据权限id查询所具有的功能权限
     * 1、查询出所有的menu信息，通过关联表进行过滤
     * @param permissionId  权限id
     * @return CommonResult
     */
    @GetMapping("querySysMenusByPermissionId")
    @OperInfo(info = "查询拥有权限的菜单")
    public CommonResult querySysMenusByPermissionId(String permissionId){
        List<SysMenu> sysMenuList= sysMenuService.querySysMenusByPermissionId(permissionId);
        return  CommonResult.success(sysMenuService.buildMenus(sysMenuList));
    }

    /**
     * 分页查询菜单数据
     * @param sysMenu 菜单对象
     * @return CommonResult
     */
    @GetMapping("querySysMenusList")
    @OperInfo(info = "查询菜单")
    public CommonResult querySysMenusList(SysMenu sysMenu){
        List<SysMenu> menusList = sysMenuService.querySysMenusList(sysMenu);
        return  CommonResult.success(sysMenuService.buildMenuTree(menusList));
    }
    @GetMapping("queryMenuById")
    @OperInfo(info = "查询菜单")
    public CommonResult queryMenuById(String id){
        return sysMenuService.queryMenuById(id);
    }
    /**
     * 向菜单表中添加数据
     * @param sysMenu 菜单对象
     * @return CommonResult
     */
    @PostMapping("insertSysMenu")
    @OperInfo(info = "新增菜单")
    public CommonResult insertSysMenu(SysMenu sysMenu, String parentId){
        sysMenu.setParentId(parentId);
        CommonResult commonResult=sysMenuService.insertSysMenu(sysMenu);
        return  commonResult;
    }

    /**
     * 更新菜单信息
     * @param sysMenu  菜单对象
     * @return CommonResult
     */
    @PutMapping("updateSysMenuById")
    @OperInfo(info = "修改菜单")
    public CommonResult updateSysMenuById(SysMenu sysMenu){
        CommonResult commonResult=sysMenuService.updateSysMenuById(sysMenu);
        return  commonResult;
    }

    /**
     * 依据menuId 删除数据
     * 从关联表中数据当前menuId的所有权限关联
     * @param menuId 菜单id
     * @return CommonResult
     */
    @DeleteMapping("deleteSysMenuById")
    @OperInfo(info = "删除菜单")
    public CommonResult deleteSysMenuById(String  menuId){
        CommonResult commonResult=sysMenuService.deleteSysMenuById(menuId);
        return  commonResult;
    }

    /**
     * 获取所有功能权限树
     * @return
     */
    @GetMapping("getAllMenuTree")
    @OperInfo(info = "查询菜单")
    public CommonResult getAllMenuTree(){
        return sysMenuService.getAllTreeMenus();
    }

}
