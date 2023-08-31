package com.fudian.project.system.security.service;

import com.fudian.api.system.pojo.SysUser;
import com.fudian.project.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 */
@Component
public class SysPermissionService {
    @Autowired
    private SysRoleService roleService;

  /*  @Autowired
    private ISysMenuService menuService;*/

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user) {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        //if (user.isAdmin()) {
        //    roles.add("admin");
        //} else {
            //todo 数据库好后需要放开
//            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
//        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> roles = new HashSet<String>();
        roles.add("*:*:*");
        // 管理员拥有所有权限
        //if (user.isAdmin()) {
        //    roles.add("*:*:*");
        //} else {
            //todo 数据库好后需要放开
//            roles.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
//        }
        return roles;
    }
}
