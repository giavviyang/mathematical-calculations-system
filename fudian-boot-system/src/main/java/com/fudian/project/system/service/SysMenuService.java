package com.fudian.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.vo.RouterVo;
import com.fudian.project.system.pojo.SysMenu;

import java.util.List;


public interface SysMenuService extends IService<SysMenu> {


    CommonResult queryMenuById(String id);

    int updateBatch(List<SysMenu> list);

    int batchInsert(List<SysMenu> list);

    int insertOrUpdate(SysMenu record);

    int insertOrUpdateSelective(SysMenu record);

    /**
     * 依据权限id查询所具有的功能权限
     * 1、查询出所有的menu信息，通过关联表进行过滤
     * @param permissionId  权限id
     * @return CommonResult
     */
    List<SysMenu> querySysMenusByPermissionId(String permissionId);

    /**
     * 分页查询菜单数据
     * @param sysMenu 菜单对象
     * @return CommonResult
     */
    List<SysMenu> querySysMenusList(SysMenu sysMenu);

    /**
     * 向菜单表中添加数据
     * @param sysMenu 菜单对象
     * @return CommonResult
     */
    CommonResult insertSysMenu(SysMenu sysMenu);

    /**
     * 更新菜单信息
     * @param sysMenu  菜单对象
     * @return CommonResult
     */
    CommonResult updateSysMenuById(SysMenu sysMenu);


    /**
     * 依据menuId 删除数据
     * 从关联表中数据当前menuId的所有权限关联
     * @param menuId 菜单id
     * @return CommonResult
     */
    CommonResult deleteSysMenuById(String menuId);

    /**
     * 根据用户查询主菜单
     * @param userId
     * @return
     */
    List<SysMenu> selectMenuTreeByUserId(String userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVo> buildMenus(List<SysMenu> menus);

    List<SysMenu> buildMenuTree(List<SysMenu> menusList);

    /**
     * 查询结构树
     * @param menuIdList
     * @return List<SysMenu>
     */
    List<SysMenu> querySysMenusList(List<String> menuIdList);

    /**
     * 获取所有功能树
     * @return
     */
    CommonResult getAllTreeMenus();

}
