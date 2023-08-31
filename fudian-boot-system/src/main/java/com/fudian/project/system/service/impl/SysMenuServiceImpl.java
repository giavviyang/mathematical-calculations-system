package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.vo.MetaVo;
import com.fudian.common.vo.RouterVo;
import com.fudian.project.system.mapper.SysMenuMapper;
import com.fudian.project.system.mapper.SysPermissionMenuMapper;
import com.fudian.project.system.pojo.SysMenu;
import com.fudian.project.system.pojo.SysPermissionMenu;
import com.fudian.project.system.pojo.SysUserRole;
import com.fudian.project.system.security.service.PermissionService;
import com.fudian.project.system.security.utils.SecurityUtils;
import com.fudian.project.system.service.SysMenuService;
import com.fudian.project.system.service.SysPermissionMenuService;
import com.fudian.project.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysPermissionMenuMapper sysPermissionMenuMapper;


    @Override
    public int updateBatch(List<SysMenu> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public CommonResult queryMenuById(String id) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getParentId, id);
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> sysMenus = baseMapper.selectList(queryWrapper);
        CommonResult commonResult = new CommonResult();
        commonResult.setCode(200);
        if (null == sysMenus || 0 == sysMenus.size()) {
            commonResult.setData(new ArrayList<>());
        }else{
            commonResult.setData(sysMenus);
        }
        return commonResult;
    }

    @Override
    public int batchInsert(List<SysMenu> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SysMenu record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SysMenu record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    //@Cacheable(value = "sysMenu",key = "#permissionId", unless="#result == null")
    public List<SysMenu> querySysMenusByPermissionId(String permissionId) {
        //查询出所有的菜单数据。
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        List<SysMenu> list = sysMenuMapper.selectList(queryWrapper);
        QueryWrapper<SysPermissionMenu> sysPermissionMenuQueryWrapper = new QueryWrapper<>();
        sysPermissionMenuQueryWrapper.lambda().eq(SysPermissionMenu::getPermissionId, permissionId);
        List<SysPermissionMenu> sysPermissionMenus = sysPermissionMenuMapper.selectList(sysPermissionMenuQueryWrapper);
        //获取关联表中的菜单Id
        List<String> reduceMenuIdList = sysPermissionMenus.stream().map(e -> e.getMenuId()).collect(Collectors.toList());
        //依据id过滤当前的
        List<SysMenu> finalList = list.stream().filter(x -> reduceMenuIdList.contains(x.getMenuId())).collect(Collectors.toList());
        return finalList;
    }

    @Override
    //@Cacheable(value = "sysMenu", unless="#result == null", condition = "#sysMenu.menuId == null")
    public List<SysMenu> querySysMenusList(SysMenu sysMenu) {
        //查询出所有的菜单数据。
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        //菜单名称
        String menuName = sysMenu.getMenuName();
        if (menuName != null && !"".equals(menuName)) {
            queryWrapper.like(SysMenu::getMenuName, menuName);
        }
        //菜单可用状态
        String visible = sysMenu.getVisible();
        if (visible != null && !"".equals(visible)) {
            queryWrapper.eq(SysMenu::getVisible, visible);
        }
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> list = sysMenuMapper.selectList(queryWrapper);
        //将当前的数据进行进行分行处理。
        return list;
    }

    @Override
    //@Caching(evict = {
    //        @CacheEvict(value = "sysMenu"),
    //        @CacheEvict(value = "sysMenuList")
    //})
    public CommonResult insertSysMenu(SysMenu sysMenu) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        String menuType = sysMenu.getMenuType();
        if (sysMenu.getParentId() == null || sysMenu.getParentId().equals("")) {
            sysMenu.setParentId("0");
        }
        if (menuType.equals("M")) {
            //当前为主目录
            queryWrapper.lambda().eq(SysMenu::getMenuType, menuType);
            List<SysMenu> menus = sysMenuMapper.selectList(queryWrapper);
            if (menus != null && menus.size() > 0) {
                List<String> menuIdList = menus.stream().filter(e -> e.getMenuId().length() == 1).map(e -> e.getMenuId()).collect(Collectors.toList());
                List<Integer> transList = new ArrayList<>();
                for (String str : menuIdList) {
                    transList.add(Integer.parseInt(str));
                }
                //得到最大值
                Integer max = transList.stream().reduce(Integer::max).get();
                int maxMenuId = Integer.valueOf(max);
                sysMenu.setMenuId(++maxMenuId + "");
                //获取到最大的值
                int maxOrderNum = menus.stream().mapToInt(SysMenu::getOrderNum).max().getAsInt();
                sysMenu.setOrderNum(++maxOrderNum);
                sysMenu.setIsFrame(1);
                sysMenu.setCreateBy("admin");
                sysMenu.setCreateTime(new Date());
            } else {
                //当前无此内容下的id
                sysMenu.setMenuId("1");
                sysMenu.setParentId("0");
                sysMenu.setOrderNum(0);
                sysMenu.setCreateBy("admin");
                sysMenu.setCreateTime(new Date());
                if (sysMenu.getIcon() == null || sysMenu.getIcon().equals("")) {
                    sysMenu.setIcon("#");
                }
            }
        } else if (menuType.equals("C")) {
            //当前为菜单
            queryWrapper.lambda().eq(SysMenu::getMenuType, menuType).eq(SysMenu::getParentId, sysMenu.getParentId());
            List<SysMenu> menus = sysMenuMapper.selectList(queryWrapper);
            if (menus != null && menus.size() > 0) {
                List<String> menuIdList = menus.stream().map(e -> e.getMenuId()).collect(Collectors.toList());
                List<Integer> transList = new ArrayList<>();
                for (String str : menuIdList) {
                    transList.add(Integer.parseInt(str));
                }
                //得到最大值
                Integer max = transList.stream().reduce(Integer::max).get();
                int maxMenuId = Integer.valueOf(max);
                sysMenu.setMenuId(++maxMenuId + "");
                //获取到最大的值
                int maxOrderNum = menus.stream().mapToInt(SysMenu::getOrderNum).max().getAsInt();
                sysMenu.setOrderNum(++maxOrderNum);
                sysMenu.setIsFrame(1);
                sysMenu.setCreateBy("admin");
                sysMenu.setCreateTime(new Date());
            } else {
                //当前无此内容下的id
                sysMenu.setMenuId(sysMenu.getParentId() + "00");
                sysMenu.setOrderNum(0);
                sysMenu.setCreateBy("admin");
                sysMenu.setCreateTime(new Date());
            }
        } else {
            //当前为菜单
            queryWrapper.lambda().eq(SysMenu::getMenuType, menuType).eq(SysMenu::getParentId, sysMenu.getParentId());
            List<SysMenu> menus = sysMenuMapper.selectList(queryWrapper);
            if (menus != null && menus.size() > 0) {
                List<String> menuIdList = menus.stream().map(e -> e.getMenuId()).collect(Collectors.toList());
                List<Integer> transList = new ArrayList<>();
                for (String str : menuIdList) {
                    transList.add(Integer.parseInt(str));
                }
                //得到最大值
                Integer max = transList.stream().reduce(Integer::max).get();
                int maxMenuId = Integer.valueOf(max);
                sysMenu.setMenuId(++maxMenuId + "");
                //获取到最大的值
                int maxOrderNum = menus.stream().mapToInt(SysMenu::getOrderNum).max().getAsInt();
                sysMenu.setOrderNum(++maxOrderNum);
                sysMenu.setIsFrame(1);
                sysMenu.setCreateBy("admin");
                sysMenu.setCreateTime(new Date());
            } else {
                //当前无此内容下的id
                sysMenu.setMenuId(sysMenu.getParentId() + "0");
                sysMenu.setOrderNum(0);
                sysMenu.setCreateBy("admin");
                sysMenu.setCreateTime(new Date());
            }
        }
        int i = sysMenuMapper.insert(sysMenu);
        CommonResult commonResult = new CommonResult();
        if (i > 0) {
            commonResult.setMsg("新增按钮权限成功");
            commonResult.setCode(200);
        } else {
            commonResult.setMsg("新增按钮权限失败");
            commonResult.setCode(500);
        }
        return commonResult;
    }

    @Override
    //@Caching(evict = {
    //        @CacheEvict(value = "sysMenu"),
    //        @CacheEvict(value = "sysMenuList")
    //})
    public CommonResult updateSysMenuById(SysMenu sysMenu) {
        int i = sysMenuMapper.updateById(sysMenu);
        CommonResult commonResult = new CommonResult();
        if (i > 0) {
            commonResult.setMsg("更新菜单信息成功");
            commonResult.setCode(200);
        } else {
            commonResult.setMsg("更新菜单信息失败");
            commonResult.setCode(500);
        }
        return commonResult;
    }

    @Override
    //@Caching(evict = {
    //        @CacheEvict(value = "sysMenu"),
    //        @CacheEvict(value = "sysMenuList")
    //})
    public CommonResult deleteSysMenuById(String menuId) {

        int i = sysMenuMapper.deleteById(menuId);
        int j = sysPermissionMenuMapper.deleteById(menuId);
        CommonResult commonResult = new CommonResult();
        if (i > 0) {
            commonResult.setMsg("删除菜单成功");
            commonResult.setCode(200);
        } else {
            commonResult.setMsg("删除菜单失败，请联系管理员");
            commonResult.setCode(500);
        }
        return commonResult;
    }

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SysPermissionMenuService sysPermissionMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public List<SysMenu> selectMenuTreeByUserId(String userId) {
        List<SysMenu> menus = null;
        //todo  菜单查询 数据库交互

        if (SecurityUtils.isAdmin(userId)) {

            //超级管理员 拥有所有路由目录权限
            menus = sysMenuMapper.selectMenuTreeAll();
        } else {

            List<SysUserRole> sysUserRoles = sysUserRoleService.querySysUserRoleList(userId);

            // 权限集合
            menus = permissionService.getMenuPermission(userId, sysUserRoles);
            /*List<String> permissionList = new ArrayList<>();
            permissionList.addAll(permissions);

            //依据当前的权限id查询当前的所有功能权限
            List<SysPermissionMenu> sysPermissionMenuList =  sysPermissionMenuService.querySysPermissionMenu(permissionList);
            //过滤出当前的menuId,存入到当前的redis中
            List<String> menuIdList=sysPermissionMenuList.stream().map(e->e.getMenuId()).collect(Collectors.toList());
            //查询当前id下的所有按钮信息并组成集合存入redis中
            menus =sysMenuService.querySysMenusList(menuIdList);*/

            //非超级管理员,根据用户Id查询相应的路由目录
            //menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, "0");
    }

    @Override
    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setName(StringUtils.capitalize(menu.getMenuName()));
            router.setPath(getRouterPath(menu));
            router.setHidden(menu.getVisible().equals("0") ? "false" : "true");
            router.setComponent(StringUtils.isEmpty(menu.getComponent()) ? "Layout" : menu.getComponent());
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon()));
            List<SysMenu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && "M".equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            routers.add(router);
        }
        return routers;
    }

    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menusList) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = menusList.iterator(); iterator.hasNext(); ) {
            SysMenu t = (SysMenu) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if ("0".equals(t.getParentId())) {
                recursionFn(menusList, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menusList;
        }
        return returnList;
    }

    @Override
    //@Cacheable(value="sysMenuList" , unless = "#result == null")
    public List<SysMenu> querySysMenusList(List<String> menuIdList) {
        QueryWrapper<SysMenu> sysMenuQueryWrapper = new QueryWrapper<>();
        if(menuIdList==null||menuIdList.size()==0){
            return new ArrayList<>();
        }
        sysMenuQueryWrapper.lambda().in(SysMenu::getMenuId, menuIdList).eq(SysMenu::getStatus, "0");
        sysMenuQueryWrapper.lambda().orderByAsc(SysMenu::getParentId).orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> sysMenuList = sysMenuMapper.selectList(sysMenuQueryWrapper);
        return sysMenuList;
    }

    @Override
    public CommonResult getAllTreeMenus() {
        LambdaQueryWrapper<SysMenu> sysMenuLam = new LambdaQueryWrapper<>();
        sysMenuLam.eq(SysMenu::getVisible, "0").eq(SysMenu::getStatus, "0");
        sysMenuLam.ne(SysMenu::getMenuId, "206");
        sysMenuLam.or(query -> query.eq(SysMenu::getMenuType, "B"));
        List<SysMenu> sysMenus = this.list(sysMenuLam);
        List<SysMenu> returnList = new ArrayList<>();
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getParentId().equals("0")) {
                sysMenu.setChildren(getChildrenTreeData(sysMenu, sysMenus));
                returnList.add(sysMenu);
            }
        }
        return CommonResult.success(returnList);
    }


    /**
     * 获取tree型数据
     */
    private List<SysMenu> getChildrenTreeData(SysMenu sysMenu, List<SysMenu> list) {
        List<SysMenu> childrenList = list.stream().filter(e -> e.getParentId().equals(sysMenu.getMenuId())).collect(Collectors.toList());
        if (childrenList != null && childrenList.size() > 0) {
            for (SysMenu childrenSysMenu : childrenList) {
                childrenSysMenu.setChildren(getChildrenTreeData(childrenSysMenu, list));
            }
        }
        return childrenList;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录
        if ("0".equals(menu.getParentId()) && 1 == (menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        return routerPath;
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    private List<SysMenu> getChildPerms(List<SysMenu> list, String parentId) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext(); ) {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId().equals(parentId)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext()) {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (t.getMenuId().equals(n.getParentId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
