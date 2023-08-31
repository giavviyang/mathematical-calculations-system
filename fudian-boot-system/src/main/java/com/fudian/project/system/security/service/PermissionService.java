package com.fudian.project.system.security.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.system.pojo.*;
import com.fudian.project.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义权限实现，ss取自SpringSecurity首字母
 */
@Service("ss")
public class PermissionService {
    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    private static final String ROLE_DELIMETER = ",";

    private static final String PERMISSION_DELIMETER = ",";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysUserPermissionService sysUserPermissionService;

    @Autowired
    private SysPermissionMenuService sysPermissionMenuService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        return hasPermissions(loginUser.getPermissions(), permission);
    }

    /**
     * 验证用户是否不具备某权限，与 hasPermi逻辑相反
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    public boolean lacksPermi(String permission) {
        return hasPermi(permission) != true;
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 以 PERMISSION_NAMES_DELIMETER 为分隔符的权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermi(String permissions) {
        if (StringUtils.isEmpty(permissions)) {
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        Set<String> authorities = loginUser.getPermissions();
        for (String permission : permissions.split(PERMISSION_DELIMETER)) {
            if (permission != null && hasPermissions(authorities, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色字符串
     * @return 用户是否具备某角色
     */
    public boolean hasRole(String role) {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles())) {
//            return false;
//        }


//        for (SysRole sysRole : loginUser.getUser().getRoles()) {
//            String roleKey = "admin";
//
//            if (SUPER_ADMIN.contains(roleKey) || roleKey.contains(StringUtils.trim(role))) {
//                return true;
//            }
//        }
        return false;
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。
     *
     * @param role 角色名称
     * @return 用户是否不具备某角色
     */
    public boolean lacksRole(String role) {
        return hasRole(role) != true;
    }

    /**
     * 验证用户是否具有以下任意一个角色
     *
     * @param roles 以 ROLE_NAMES_DELIMETER 为分隔符的角色列表
     * @return 用户是否具有以下任意一个角色
     */
    public boolean hasAnyRoles(String roles) {
        if (StringUtils.isEmpty(roles)) {
            return false;
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
//        if (StringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles())) {
//            return false;
//        }
//        for (String role : roles.split(ROLE_DELIMETER)) {
//            if (hasRole(role)) {
//                return true;
//            }
//        }
        return false;
    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission) {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
    }

    public List<SysUserRole> getRolePermission(SysUser user) {
        //查询当前用户的所有角色信息。
        return sysUserRoleService.querySysUserRoleList(user.getUserId());
    }

    public Set<String> getMenuPermission(SysUser user,List<SysUserRole> roles) {

        //过滤出角色id
        List<String>  sysRoleIdList=roles.stream().map(e->e.getRoleId()).collect(Collectors.toList());
        if(sysRoleIdList==null||sysRoleIdList.size()==0){
            return new HashSet<>();
        }
        //依据角色id 查询当前的权限id
//        List<SysRolePermission> sysRolePermissionList = sysRolePermissionService.querySysRolePermission(sysRoleIdList);
//        List<String> collect = sysRolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        permissionIdList.addAll(collect);
//        permissionIdList = permissionIdList.stream().distinct().collect(Collectors.toList());
//        List<SysPermissionMenu> sysPermissionMenuList =  sysPermissionMenuService.querySysPermissionMenu(permissionIdList);
        LambdaQueryWrapper<SysRoleMenu> roleMenuLam = new LambdaQueryWrapper<>();
        roleMenuLam.in(SysRoleMenu::getRoleId,sysRoleIdList);
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.list(roleMenuLam);
        if(sysRoleMenus==null||sysRoleMenus.size()==0){
            return new HashSet<>();
        }

        //过滤出当前的menuId,存入到当前的redis中
        List<String> menuIdList=sysRoleMenus.stream().map(e->e.getMenuId()).collect(Collectors.toList());

        //查询当前id下的所有按钮信息并组成集合存入redis中
        List<SysMenu> sysMenuList=sysMenuService.querySysMenusList(menuIdList);

        return sysMenuList.stream().map(e -> e.getPerms()).collect(Collectors.toSet());
    }

    public List<SysMenu> getMenuPermission(String userId,List<SysUserRole> roles) {


        /**
         * 原有用户角色权限为互相关联   现改为用户-角色-权限
         */
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("user_id",userId);
//        List<SysUserPermission> sysUserPermissionList = sysUserPermissionService.list(queryWrapper);
//        List<String> permissionIdList = sysUserPermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//
//        //过滤出角色id
//        List<String>  sysRoleIdList=roles.stream().map(e->e.getRoleId()).collect(Collectors.toList());
//        //依据角色id 查询当前的权限id
//        List<SysRolePermission> sysRolePermissionList = sysRolePermissionService.querySysRolePermission(sysRoleIdList);
//        List<String> collect = sysRolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        permissionIdList.addAll(collect);
//        permissionIdList = permissionIdList.stream().distinct().collect(Collectors.toList());
//
//        List<SysPermissionMenu> sysPermissionMenuList =  sysPermissionMenuService.querySysPermissionMenu(permissionIdList);
//
//        //过滤出当前的menuId,存入到当前的redis中
//        List<String> menuIdList=sysPermissionMenuList.stream().map(e->e.getMenuId()).collect(Collectors.toList());


        if(roles!=null&&roles.size()>0){
            LambdaQueryWrapper<SysRoleMenu> srmLam = new LambdaQueryWrapper<>();
            srmLam.in(SysRoleMenu::getRoleId,roles.stream().map(e->e.getRoleId()).collect(Collectors.toList()));
            List<SysRoleMenu> list=sysRoleMenuService.list(srmLam);

            //查询当前id下的所有按钮信息并组成集合存入redis中
            return sysMenuService.querySysMenusList(list.stream().map(e->e.getMenuId()).collect(Collectors.toList()));
        }else{
            return new ArrayList<>();
        }





    }

    /**
     * 查询当前用户的所有权限
     * @param userId
     * @return
     */
    public List<String> getAllPermission(String userId) {

        List<SysUserRole> sysUserRoles = sysUserRoleService.querySysUserRoleList(userId);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        List<SysUserPermission> sysUserPermissionList = sysUserPermissionService.list(queryWrapper);
        List<String> permissionIdList = sysUserPermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());

        //过滤出角色id
        List<String>  sysRoleIdList=sysUserRoles.stream().map(e->e.getRoleId()).collect(Collectors.toList());
        //依据角色id 查询当前的权限id
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionService.querySysRolePermission(sysRoleIdList);
        List<String> collect = sysRolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
        permissionIdList.addAll(collect);
        return permissionIdList.stream().distinct().collect(Collectors.toList());

    }
}
