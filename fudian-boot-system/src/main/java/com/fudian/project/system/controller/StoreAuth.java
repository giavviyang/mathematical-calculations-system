package com.fudian.project.system.controller;

import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysDataPermission;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.common.vo.RouterVo;
import com.fudian.project.system.pojo.*;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xyh
 * @title: StoreAuth
 * @projectName standard
 * @description: 存储当前的认证到redis
 * @date 2020/1/7 21:40
 */
@Component
public class StoreAuth {
    @Autowired
    DataStoreWrapper redisCache;

    @Autowired
    TokenService tokenService;

    @Autowired
    SysDeptService sysDeptService;

    @Autowired
    SysUserPermissionService sysUserPermissionService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    SysPermissionDataService sysPermissionDataService;

    @Autowired
    SysDataPermissionService sysDataPermissionService;

    @Autowired
    SysRolePermissionService sysRolePermissionService;

    @Autowired
    SysPermissionMenuService sysPermissionMenuService;

    @Autowired
    SysMenuService sysMenuService;


    /**
     * 向内存中添加数据
     * @return
     */
    public boolean  addDept(){
        //判断当前redis是否可用。可用则将数据添加到当前的redis中，
        // 不可用则将当前的数据存入到指定的地址或者直接从数据库读取
        List<SysDept> depts=sysDeptService.list();
        redisCache.setCacheList("SysDept",depts);
        return true;
//        long l=listOperations.size("SysDept");
//        boolean b=false;
//        if (l>0) {
//            b=true;
//        }
//        return b;
    }

    /**
     * 查询权限菜单
     * @return
     */
    @Cacheable(value = "permission_menu",unless="#result == null")
    public List<SysPermissionMenu>  getPermissionMenu(){
        System.out.println("从数据库查询去权限菜单并缓存");
        return sysPermissionMenuService.list();
    }

    @CacheEvict(value = "permission_menu")
    public void deletePermissionMenu() {
        System.out.println("权限菜单缓存已被清空");
    }

    /**
     * 获取权限的map集合
     * map中分别存储了当前不同类型的权限
     * @return Map<String,List<SysDataPermission>>
     */
    public   Map<String,List<SysDataPermission>>  getSysDataPermissionMap(){
        List<String> permissionIdList=  queryCurrUserPermissionId();
        //依据当前用户所有的角色/权限集合查询数据权限
        List<SysPermissionData>  sysPermissionDataList=sysPermissionDataService.querySysPermissionDataList(permissionIdList);
        //过滤出当前的所有数据权限
        List<String>  sysDataPermissionIdList=sysPermissionDataList.stream().map(e->e.getDataPermissionId()).collect(Collectors.toList());
        //依据以上的数据权限id查询当前的所有数据权限，并对当前的数据进行分类
        List<SysDataPermission>   sysDataPermissionList=sysDataPermissionService.querySysDataPermissionList(sysDataPermissionIdList);
        //按照当前的数据进行分组
        Map<String,List<SysDataPermission>>  map= sysDataPermissionList.stream().collect(Collectors.groupingBy(SysDataPermission::getDataPermissionsType));
        return  map;

    }

    /**
     * 向当前redis中存储权限信息
     *添加数据权限
     * @return
     */
    public  boolean  addDataPermissions(Map<String,List<SysDataPermission>>  map){
        boolean b  = false;
        //将数据存入到redis中 获取到操作对象
        redisCache.setCacheMap("dataPermission", map);
        //判断当前操作对象得长度。
//        long  l=redisDataPermission.size("dataPermission");
//        if (l > 0) {
//            b=true;
//        }
//        return b;
        return true;
    }

    /**
     * 获取左侧导航栏信息
     * 以及页面各种按钮显示与否的权限控制机制
     * @return
     */
    public  List<RouterVo>  getMenuPermissions(){
        List<String> permissionIdList=  queryCurrUserPermissionId();
        //依据当前的权限id查询当前的所有功能权限
        List<SysPermissionMenu> sysPermissionMenuList =  sysPermissionMenuService.querySysPermissionMenu(permissionIdList);
        //过滤出当前的menuId,存入到当前的redis中
        List<String> menuIdList=sysPermissionMenuList.stream().map(e->e.getMenuId()).collect(Collectors.toList());
        //查询当前id下的所有按钮信息并组成集合存入redis中
        List<SysMenu> sysMenuList=sysMenuService.querySysMenusList(menuIdList);
        List<RouterVo> routerVos = sysMenuService.buildMenus(sysMenuList);
        return  routerVos;
    }

    /**
     * 向当前redis中存储权限信息
     * 添加功能权限
     * @return  boolean
     */
    public  boolean  addMenPermissions(List<RouterVo> routerVos){
        boolean b  = false;
        redisCache.setCacheList("sysmenu", routerVos);
//        long l=sysMenu.size("sysmenu");
//        if (l>0) {
//            b=true;
//        }
//        return b;
        return true;
    }

    /**
     * 查询当前用户的所有的权限Id
     * @return
     */
    public  List<String>   queryCurrUserPermissionId(){
        //通过用户用户查询当前用户的部门权限
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String  userId=loginUser.getUserId();
        //查询当前用户的所有角色信息。
        List<SysUserRole> sysUserRoleList=sysUserRoleService.querySysUserRoleList(userId);
        //过滤出角色id
        List<String>  sysRoleIdList=sysUserRoleList.stream().map(e->e.getRoleId()).collect(Collectors.toList());
        //依据角色id 查询当前的权限id
        List<SysRolePermission> sysRolePermissionList = sysRolePermissionService.querySysRolePermission(sysRoleIdList);
        //获取当前的权限id
        List<String>  rolePermissionIdList=sysRolePermissionList.stream().map(e->e.getPermissionId()).collect(Collectors.toList());
        //获取当前的用户id的权限 查询参数
        List<SysUserPermission>  sysUserPermissionList= sysUserPermissionService.querySysUserPermissionList(userId);
        //获取所有权限的Id
        List<String>  premissionIdList=sysUserPermissionList.stream().map(e->e.getPermissionId()).collect(Collectors.toList());
        premissionIdList.addAll(rolePermissionIdList);
        //去重当前的id
        List<String>  reducePermissionIdList=premissionIdList.stream().distinct().collect(Collectors.toList());
        return  reducePermissionIdList;
    }

}
