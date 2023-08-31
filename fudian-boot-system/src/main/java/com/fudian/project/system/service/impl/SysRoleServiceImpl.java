package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.ams.service.A01Api;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysDataPermission;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.SysRoleApi;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.project.system.cache.DataPermissionCache;
import com.fudian.project.system.mapper.SysRoleMapper;
import com.fudian.project.system.mapper.SysRoleMenuMapper;
import com.fudian.project.system.mapper.SysRolePermissionMapper;
import com.fudian.project.system.mapper.SysUserRoleMapper;
import com.fudian.project.system.pojo.*;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.SysDataPermissionService;
import com.fudian.project.system.service.SysRoleDataService;
import com.fudian.project.system.service.SysRoleService;
import com.fudian.project.system.service.SysUserRoleService;
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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService, SysRoleApi {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysDataPermissionService sysDataPermissionService;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysRoleDataService sysRoleDataService;


    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 缓存
     */
    @Autowired
    private DataStoreWrapper redisCache;

    @Autowired
    private DataPermissionCache dataPermissionCacheService;


    @Override
    public int updateBatch(List<SysRole> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SysRole> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SysRole record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SysRole record) {
        return baseMapper.insertOrUpdateSelective(record);
    }


    @Transactional
    @Override
    public CommonResult<SysRole> insertSysRole(SysRole sysRole) {
        String uuid = StringUtils.uuid();
        //判断当前的permissionIds是否存为null;
//        String[] permissionIds=sysRole.getJsonStr().split(",");
//        int j=0;
//        if (permissionIds != null &&permissionIds.length>0) {
//            List<SysRolePermission>  sysRolePermissions=new ArrayList<>();
//            for (String str:permissionIds) {
//                sysRolePermissions.add(new SysRolePermission(uuid,str));
//            }
//            j=sysRolePermissionMapper.batchInsert(sysRolePermissions);
//        }
        //获取当前操作用户的信息
        SysUser user = tokenService.getLoginUser(ServletUtils.getRequest()).getUser();
        //以下为将当前的用户整理成符合要求的
        sysRole.setRoleId(uuid);
        //获取当前角色的最大编号，目前未做查询处理
        sysRole.setRoleNum("1000");
        //将获取到的全宗数据填入当前的对象中
        sysRole.setOrgId(user.getOrgId());
        sysRole.setOrgId("001");
        sysRole.setCreateBy(user.getUserName());
        sysRole.setCreateTime(new Date());
        int i = sysRoleMapper.insert(sysRole);
        CommonResult<SysRole> commonResult = null;
        if (i > 0) {

            commonResult = new CommonResult<SysRole>(200, "新增角色成功", null);

        } else {
            commonResult = new CommonResult<SysRole>(500, "新增角色失败，请联系管理员", null);

        }

        return commonResult;
    }

    @Override
    public CommonResult<SysRole> updateRoleById(SysRole sysRole) {


        CommonResult<SysRole> commonResult = null;

        int i = sysRoleMapper.updateById(sysRole);
        if (i > 0) {
            commonResult = new CommonResult<SysRole>(200, "修改角色成功", null);
        } else {
            commonResult = new CommonResult<SysRole>(500, "修改角色失败，请联系管理员", null);
        }
        return commonResult;
    }

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public CommonResult<SysRole> deleteRoleById(List<String> sysRoleIdList) {
        int i = sysRoleMapper.deleteBatchIds(sysRoleIdList);

        LambdaQueryWrapper<SysUserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.in(SysUserRole::getRoleId,sysRoleIdList);
        int z = sysUserRoleMapper.delete(userRoleWrapper);

        LambdaQueryWrapper<SysRoleMenu> menuWrapper = new LambdaQueryWrapper<>();
        menuWrapper.in(SysRoleMenu::getRoleId,sysRoleIdList);
        int m = sysRoleMenuMapper.delete(menuWrapper);

        CommonResult<SysRole> commonResult = null;
        if (i > 0) {
            commonResult = new CommonResult<SysRole>(200, "删除角色成功", null);
        } else {
            commonResult = new CommonResult<SysRole>(500, "删除角色失败，请联系管理员", null);
        }
        return commonResult;
    }

    @Override
    public CommonGridResult queryRolePageList(SysRole sysRole) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        try {
            Class clazz = sysRole.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                String fieldName = f.getName();
                Method m = clazz.getDeclaredMethod("get" + StringUtils.firstChar2UpperCase(fieldName), null);
                Object b = m.invoke(sysRole, null);
                if (b != null && b != "") {
                    TableId tableId = f.getAnnotation(TableId.class);
                    TableField tableField = f.getAnnotation(TableField.class);
                    if (tableId != null) {
                        queryWrapper.eq(tableId.value(), b);
                    }
                    if (tableField != null) {
                        queryWrapper.like(tableField.value(), b);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        queryWrapper.lambda().orderByDesc(SysRole::getCreateTime);
        PageHelper.startPage(sysRole.getPageNum(), sysRole.getPageSize());
        List<SysRole> list = sysRoleMapper.selectList(queryWrapper);
        Page page = (Page) list;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(sysRole.getPageNum(), sysRole.getPageSize(), total, list);
        return commonGridResult;
    }

    @Override
    public List<Map<String, Object>> queryRoleListMap() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        List<SysRole> list = sysRoleMapper.selectList(queryWrapper);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysRole sysRole : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("roleName", sysRole.getRoleName());
            map.put("roleId", sysRole.getRoleId());
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public List<SysRole> queryRoleList(SysRole sysRole, String userId) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        try {
            Class clazz = sysRole.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                String fieldName = f.getName();
                Method m = clazz.getDeclaredMethod("get" + StringUtils.firstChar2UpperCase(fieldName), null);
                Object b = m.invoke(sysRole, null);
                if (b != null && b != "") {
                    TableId tableId = f.getAnnotation(TableId.class);
                    queryWrapper.eq(tableId.value(), b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<SysRole> list = sysRoleMapper.selectList(queryWrapper);

        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        sysUserRoleQueryWrapper.lambda().eq(SysUserRole::getUserId, userId);
        List<SysUserRole> sysUserPermissions = sysUserRoleMapper.selectList(sysUserRoleQueryWrapper);
        List<String> roleIds = sysUserPermissions.stream().map(e -> e.getRoleId()).collect(Collectors.toList());
        //过滤当前的权限数据
        List<SysRole> reduceList = list.stream().filter(e -> !roleIds.contains(e.getRoleId())).collect(Collectors.toList());
        return reduceList;
    }

    @Override
    public List<SysRole> queryRoleListByPermissionId(SysRole sysRole, String permissionId) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        try {
            /*Class clazz = sysRole.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field f = fields[i];
                String fieldName=f.getName();
                Method m = clazz.getDeclaredMethod("get"+ StringUtils.firstChar2UpperCase(fieldName), null);
                Object b = m.invoke(sysRole, null);
                if (b != null && b != "") {
                    TableId tableId=  f.getAnnotation(TableId.class);
                    queryWrapper.eq(tableId.value(), b);
                }
            }*/
            if (sysRole.getRoleName() != null || "".equals(sysRole.getRoleName())) {
                queryWrapper.like("role_name", sysRole.getRoleName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<SysRole> list = sysRoleMapper.selectList(queryWrapper);

        QueryWrapper<SysRolePermission> sysRolePermissionQueryWrapper = new QueryWrapper<>();
        sysRolePermissionQueryWrapper.lambda().eq(SysRolePermission::getPermissionId, permissionId);
        List<SysRolePermission> sysRolePermissions = sysRolePermissionMapper.selectList(sysRolePermissionQueryWrapper);
        List<String> roleIds = sysRolePermissions.stream().map(e -> e.getRoleId()).collect(Collectors.toList());
        //过滤当前的权限数据
        List<SysRole> reduceList = list.stream().filter(e -> !roleIds.contains(e.getRoleId())).collect(Collectors.toList());
        return reduceList;
    }

    @Override
    public CommonResult getInitDataPermission(String roleId) {
        LambdaQueryWrapper<SysRoleData> dataLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dataLambdaQueryWrapper.eq(SysRoleData::getRoleId, roleId);
        List<SysRoleData> sysRoleDatas = sysRoleDataService.list(dataLambdaQueryWrapper);
        Map<String, Object> returnMap = new HashMap<>();
        if (sysRoleDatas != null && sysRoleDatas.size() > 0) {
            LambdaQueryWrapper<SysDataPermission> dpLam = new LambdaQueryWrapper<>();
            List<String> list = sysRoleDatas.stream().map(e -> e.getDataPermissionId()).collect(Collectors.toList());
            dpLam.in(SysDataPermission::getDataPermissionsId, list);
            List<SysDataPermission> dataPermissions = sysDataPermissionService.list(dpLam);
            //部门权限集合
            List<String> deptIds = dataPermissions.stream()
                    .filter(e -> e.getDataPermissionsType().equals("1"))
                    .map(e -> e.getDataId())
                    .collect(Collectors.toList());
            returnMap.put("1", deptIds);

            //分类权限集合
            List<String> classify = dataPermissions.stream()
                    .filter(e -> e.getDataPermissionsType().equals("3"))
                    .map(e -> e.getDataId())
                    .collect(Collectors.toList());
            returnMap.put("3", classify);

            //其他权限集合
            List<String> otherPer = dataPermissions.stream()
                    .filter(e -> e.getDataPermissionsType().equals("6"))
                    .map(e -> e.getDataId())
                    .collect(Collectors.toList());
            returnMap.put("6", otherPer);

        }
        return CommonResult.success("查询成功", returnMap);
    }


    @Transactional
    @Override
    public CommonResult updateOterDataPermission(String roleId, String dataPermissmions) {
        LambdaQueryWrapper<SysRoleData> srdLam = new LambdaQueryWrapper<>();
        srdLam.eq(SysRoleData::getRoleId, roleId);
        List<SysRoleData> sysRoleDatas = sysRoleDataService.list(srdLam);
        List<String> dataPIds = sysRoleDatas.stream().map(e -> e.getDataPermissionId()).collect(Collectors.toList());
        LambdaQueryWrapper<SysDataPermission> sdpLam = new LambdaQueryWrapper<>();
        sdpLam.in(SysDataPermission::getDataPermissionsId, dataPIds).eq(SysDataPermission::getDataPermissionsType, "6");
        List<SysDataPermission> delDataPers = sysDataPermissionService.list(sdpLam);
        if (delDataPers != null && delDataPers.size() > 0) {
            List<String> deldataPids = delDataPers.stream().map(e -> e.getDataPermissionsId()).collect(Collectors.toList());
            LambdaQueryWrapper<SysRoleData> delsLam = new LambdaQueryWrapper<>();
            delsLam.in(SysRoleData::getDataPermissionId, deldataPids);
            sysRoleDataService.remove(delsLam);
            sysDataPermissionService.remove(sdpLam);
        }
        if (dataPermissmions != null && !"".equals(dataPermissmions)) {
            String[] dataIds = dataPermissmions.split(",");
            List<SysRoleData> roleDataList = new ArrayList<>();
            List<SysDataPermission> dataPermissionList = new ArrayList<>();
            for (String dataId : dataIds) {
                SysDataPermission sysDataPermission = new SysDataPermission();
                String dataPid = StringUtils.uuid();
                sysDataPermission.setDataPermissionsType("6");
                sysDataPermission.setDataPermissionsId(dataPid);
                sysDataPermission.setDataId(dataId);
                sysDataPermission.setDataPermissionsIdentity("1");
                SysRoleData sysRoleData = new SysRoleData();
                sysRoleData.setRoleId(roleId);
                sysRoleData.setDataPermissionId(dataPid);
                roleDataList.add(sysRoleData);
                dataPermissionList.add(sysDataPermission);
            }
            sysRoleDataService.batchInsert(roleDataList);
            sysDataPermissionService.batchInsert(dataPermissionList);
        }
        dataPermissionCacheService.updatePermissions();
        return CommonResult.success("保存成功");
    }


}
