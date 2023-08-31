package com.fudian.project.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.system.mapper.*;
import com.fudian.project.system.pojo.SysPermission;
import com.fudian.project.system.pojo.SysPermissionMenu;
import com.fudian.project.system.pojo.SysRolePermission;
import com.fudian.project.system.pojo.SysUserPermission;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.SysPermissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Autowired
    private  SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysPermissionDataMapper sysPermissionDataMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysPermissionMenuMapper sysPermissionMenuMapper;

    @Autowired
    private SysUserPermissionMapper  sysUserPermissionMapper;

    @Autowired
    private TokenService tokenService;

    @Override
    public int updateBatch(List<SysPermission> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<SysPermission> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SysPermission record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SysPermission record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    @Transactional
    public CommonResult insertSysPermission(SysPermission sysPermission) {
        //当前查具体的全宗信息，预留
        sysPermission.setOrgId("略略略");
        String uuid= StringUtils.uuid();
        sysPermission.setPermissionId(uuid);
        sysPermission.setCreateBy("admin");
        sysPermission.setCreateTime(new Date());
        //获取菜单权限
        String jsonStr = sysPermission.getJsonStr();
        List<String> menuIds = new ArrayList<>();
        if (jsonStr != null && !"".equals(jsonStr)) {
            if (jsonStr.contains(",")) {
                String[] ids = jsonStr.split(",");
                for (String id : ids) {
                    menuIds.add(id);
                }
            } else {
                menuIds.add(jsonStr);
            }
        }
        //判断当前的系统内置权限是否为空，与归属角色是否为null
        /*Map<String,Object>   map=sysPermission.getParams();
        List<String> dataPermissionIds = null;
        dataPermissionIds = (List<String>) map.get("dataPermissionIds");
        int  i=0;
        if (dataPermissionIds != null&&dataPermissionIds.size()>0) {
            List<SysPermissionData> sysPermissionDatas=new ArrayList<>();
            for (String str:dataPermissionIds) {
                sysPermissionDatas.add(new SysPermissionData(uuid,str));
            }
            i= sysPermissionDataMapper.batchInsert(sysPermissionDatas);
        }
        List<String> roleIds= (List<String>) map.get("roleIds");
        int j=0;
        if (roleIds != null&& roleIds.size()>0 ) {
            List<SysRolePermission> sysRolePermissions=new ArrayList<>();
            for (String str:roleIds) {
                sysRolePermissions.add(new SysRolePermission(uuid,str));
            }
            j =  sysRolePermissionMapper.batchInsert(sysRolePermissions);
        }*/

        int x=0;
        if ( menuIds.size()>0) {
            List<SysPermissionMenu>  sysPermissionMenus=new ArrayList<>();
            for (String  str:menuIds) {
                sysPermissionMenus.add(new SysPermissionMenu(uuid,str));
            }
          x= sysPermissionMenuMapper.batchInsert(sysPermissionMenus);
        }
        int z=sysPermissionMapper.insert(sysPermission);
        CommonResult commonResult=null;
        if (z >0 && x > 0) {
            commonResult=new CommonResult(200,"新增权限数据成功",null);
        }else {
            commonResult=new CommonResult(500,"新增权限数据失败",null);
        }
        return commonResult;
    }

    @Override
    @Transactional
    public CommonResult updateSysPermissionById(SysPermission sysPermission) {
        //获取当前登录的用户名
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //判断当前的权限数据中的数据内置权限与角色信息
        /*Map<String,Object>   map=sysPermission.getParams();
        List<String> dataPermissionIds= (List<String>) map.get("dataPermissionIds");
        int  i=0;
        if (dataPermissionIds != null&&dataPermissionIds.size()>0) {
            //删除掉以后重新插入
            sysPermissionDataMapper.deleteById(sysPermission.getPermissionId());
            List<SysPermissionData> sysPermissionDatas=new ArrayList<>();
            for (String str:dataPermissionIds) {
                sysPermissionDatas.add(new SysPermissionData(sysPermission.getPermissionId(),str));
            }
            i= sysPermissionDataMapper.batchInsert(sysPermissionDatas);
        }
        List<String> roleIds= (List<String>) map.get("roleIds");
        int j=0;
        if (roleIds != null&& roleIds.size()>0 ) {
            sysRolePermissionMapper.deleteById(sysPermission.getPermissionId());
            List<SysRolePermission> sysRolePermissions=new ArrayList<>();
            for (String str:roleIds) {
                sysRolePermissions.add(new SysRolePermission(sysPermission.getPermissionId(),str));
            }
            j =  sysRolePermissionMapper.batchInsert(sysRolePermissions);
        }
        List<String>  menuIds= (List<String>) map.get("menuIds");
        int x=0;
        if (menuIds != null && menuIds.size()>0) {
            sysPermissionMenuMapper.deleteById(sysPermission.getPermissionId());
            List<SysPermissionMenu>  sysPermissionMenus=new ArrayList<>();
            for (String  str:menuIds) {
                sysPermissionMenus.add(new SysPermissionMenu(sysPermission.getPermissionId(),str));
            }
            x= sysPermissionMenuMapper.batchInsert(sysPermissionMenus);
        }*/
        //获取菜单权限
        String jsonStr = sysPermission.getJsonStr();
        List<String> menuIds = new ArrayList<>();
        if (jsonStr != null && !"".equals(jsonStr)) {
            if (jsonStr.contains(",")) {
                String[] ids = jsonStr.split(",");
                for (String id : ids) {
                    menuIds.add(id);
                }
            } else {
                menuIds.add(jsonStr);
            }
        }
        sysPermission.setUpdateTime(new Date());
        sysPermission.setUpdateBy(loginUser.getUser().getLoginCode());
        int z=sysPermissionMapper.updateById(sysPermission);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("permission_id",sysPermission.getPermissionId());
        int y = sysPermissionMenuMapper.delete(wrapper);


        int x=0;
        if ( menuIds.size()>0) {
            List<SysPermissionMenu>  sysPermissionMenus=new ArrayList<>();
            for (String  str:menuIds) {
                sysPermissionMenus.add(new SysPermissionMenu(sysPermission.getPermissionId(),str));
            }
            x+= sysPermissionMenuMapper.batchInsert(sysPermissionMenus);
        }

        CommonResult commonResult=null;
        if (z >0&&x>0) {
            commonResult=new CommonResult(200,"更新权限数据成功",null);
        }else {
            commonResult=new CommonResult(500,"更新权限数据失败",null);
        }
        return commonResult;
    }

    @Override
    public CommonResult deleteSysPermissionById(SysPermission sysPermission) {
        List<String>   sysPermissionIds=null;
        if(sysPermission.getJsonStr()==null){
            sysPermissionIds=new ArrayList<>();
            sysPermissionIds.add(sysPermission.getPermissionId());
        }else{
            List<SysPermission>  sysPermissions= JSON.parseArray(sysPermission.getJsonStr(), SysPermission.class);
            sysPermissionIds=sysPermissions.stream().map(e->e.getPermissionId()).collect(Collectors.toList());
        }
        int  z=sysPermissionMapper.deleteBatchIds(sysPermissionIds);
        int  i=sysPermissionMenuMapper.deleteBatchIds(sysPermissionIds);
        int  j=sysRolePermissionMapper.deleteBatchIds(sysPermissionIds);
        int  x=sysPermissionDataMapper.deleteBatchIds(sysPermissionIds);
        CommonResult commonResult=null;
        if (z >0&&i>=0&&j>=0&&x>=0) {
            commonResult=new CommonResult(200,"更新权限数据成功",null);
        }else {
            commonResult=new CommonResult(500,"更新权限数据失败",null);
        }
        return commonResult;
    }

    @Override
    public CommonGridResult queryPermissionPageList(SysPermission sysPermission) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        try {
            /*Class clazz = sysPermission.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                String fieldName=f.getName();
                Method m = clazz.getDeclaredMethod("get"+ StringUtils.firstChar2UpperCase(fieldName), null);
                Object b = m.invoke(sysPermission, null);
                if (b != null && b != "") {
                    TableId tableId=  f.getAnnotation(TableId.class);
                    queryWrapper.eq(tableId.value(), b);
                }
            }*/
            if (sysPermission.getPermissionName() != null && !"".equals(sysPermission.getPermissionName())) {
                queryWrapper.like("permission_name",sysPermission.getPermissionName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        queryWrapper.orderByDesc("create_time");

        PageHelper.startPage(sysPermission.getPageNum(), sysPermission.getPageSize());
        List<SysPermission> list = sysPermissionMapper.selectList(queryWrapper);
        Page page = (Page) list;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(sysPermission.getPageNum(), sysPermission.getPageSize(), total, list);
        return commonGridResult;
    }

    @Override
    public List<SysPermission> queryPermissionList(SysPermission sysPermission, String  userId) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        try {
            Class clazz = sysPermission.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                String fieldName=f.getName();
                Method m = clazz.getDeclaredMethod("get"+ StringUtils.firstChar2UpperCase(fieldName), null);
                Object b = m.invoke(sysPermission, null);
                if (b != null && b != "") {
                    TableId tableId=  f.getAnnotation(TableId.class);
                    queryWrapper.eq(tableId.value(), b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<SysPermission> list = sysPermissionMapper.selectList(queryWrapper);
        QueryWrapper<SysUserPermission> sysUserPermissionQueryWrapper = new QueryWrapper<>();
        sysUserPermissionQueryWrapper.lambda().eq(SysUserPermission::getUserId,userId);
        List<SysUserPermission> sysUserPermissions= sysUserPermissionMapper.selectList(sysUserPermissionQueryWrapper);
        List<String>  permissionIds=sysUserPermissions.stream().map(e->e.getPermissionId()).collect(Collectors.toList());
        //过滤当前的权限数据
        List<SysPermission> reduceList=list.stream().filter(e->!permissionIds.contains(e.getPermissionId())).collect(Collectors.toList());
        return   reduceList;
    }

    @Override
    public List<Map<String, Object>> queryPermissionListMap() {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        List<SysPermission>  list= sysPermissionMapper.selectList(queryWrapper);
        List<Map<String, Object>>  mapList=new ArrayList<>();
        for (SysPermission sysPermission :list) {
            Map<String,Object> map=new HashMap<>();
            map.put("permissionName",sysPermission.getPermissionName());
            map.put("permissionId",sysPermission.getPermissionId());
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public List<SysPermission> queryPermissionListByRoleId(String roleId) {
        QueryWrapper<SysRolePermission> sysRolePermissionQueryWrapper= new QueryWrapper<>();
        sysRolePermissionQueryWrapper.lambda().eq(SysRolePermission::getRoleId,roleId);
        List<SysRolePermission> list=sysRolePermissionMapper.selectList(sysRolePermissionQueryWrapper);
        List<String>  premissionIdList=list.stream().map(e->e.getPermissionId()).collect(Collectors.toList());
        //依据目前的角色id查询到的关联 查询权限信息
        List<SysPermission>  sysPermissions=null;
        if (premissionIdList != null&&premissionIdList.size()>0) {
            QueryWrapper<SysPermission> sysPermissionQueryWrapper= new QueryWrapper<>();
            sysPermissionQueryWrapper.lambda().in(SysPermission::getPermissionId,premissionIdList);
            sysPermissions= sysPermissionMapper.selectList(sysPermissionQueryWrapper);
        }
        return sysPermissions;
    }


    @Override
    public List<SysPermission> queryPerminssionListByParamters(Map<String, String> map) {
        List<SysPermission>  list= sysPermissionMapper.queryUserListByParamters(map);
        return  list;
    }


    @Override
    public List<SysPermission> queryRemainingUserListByRoleId(String roleId) {
        //查询所有的权限数据
        QueryWrapper<SysPermission> sysUserQueryWrapper= new QueryWrapper<>();
        List<SysPermission>  sysPermissionList= sysPermissionMapper.selectList(sysUserQueryWrapper);
        //查询当前绑定的权限数据
        QueryWrapper<SysRolePermission> sysRolePermissionQueryWrapper= new QueryWrapper<>();
        sysRolePermissionQueryWrapper.lambda().eq(SysRolePermission::getRoleId,roleId);
        List<SysRolePermission> list=sysRolePermissionMapper.selectList(sysRolePermissionQueryWrapper);
        List<String>  permissionIdList=list.stream().map(e->e.getPermissionId()).collect(Collectors.toList());
        //过滤当前的用户信息
        List<SysPermission>  reduceSysPermission=sysPermissionList.stream().filter(e->!permissionIdList.contains(e.getPermissionId())).collect(Collectors.toList());
        return reduceSysPermission;
    }
}
