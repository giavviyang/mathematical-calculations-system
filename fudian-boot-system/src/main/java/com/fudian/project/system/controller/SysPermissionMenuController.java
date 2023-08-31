package com.fudian.project.system.controller;


import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.project.system.service.SysPermissionMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xyh
 * @title: SysPermissionMenu
 * @projectName platform
 * @description: TODO 权限按钮数据
 * @date 2019/12/30 17:54
 */
@RestController
@RequestMapping("permissionMenu")
@OperTitle("权限菜单管理")
public class SysPermissionMenuController {

    private static final Logger log = LoggerFactory.getLogger(SysPermissionMenuController.class);

    @Autowired
    private SysPermissionMenuService sysPermissionMenuService;


//    /**
//     * 批量向权限菜单表中添加中关联数据
//     * @param sysPermissionMenus
//     * @return
//     */
//    @PostMapping("insertSysPermissionMenus")
//    public CommonResult<SysPermissionMenu>  insertSysPermissionMenus(List<SysPermissionMenu> sysPermissionMenus){
//        CommonResult<SysPermissionMenu>  commonResult =   sysPermissionMenuService.insertSysPermissionMenus(sysPermissionMenus);
//        return  commonResult;
//    }
//
//
//    @DeleteMapping("deleteSysPermissionMenus")
//    public  CommonResult<SysPermissionMenu> deleteSysPermissionMenus(){
//
//
//    }

    /**
     * 根据权限id查询对应的菜单
     * @param permissionId
     * @return
     */
    @GetMapping("selectSysPermissionMenuByPermissionId")
    @OperInfo(info = "查询权限菜单")
    public CommonResult selectSysPermissionMenuByPermissionId(String permissionId){
        CommonResult commonResult =   sysPermissionMenuService.selectSysPermissionMenuByPermissionId(permissionId);
        return  commonResult;
    }
}
