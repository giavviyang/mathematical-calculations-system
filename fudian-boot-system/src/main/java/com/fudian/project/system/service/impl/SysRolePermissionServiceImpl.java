package com.fudian.project.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.mapper.SysRolePermissionMapper;
import com.fudian.project.system.pojo.SysRole;
import com.fudian.project.system.pojo.SysRolePermission;
import com.fudian.project.system.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    @Autowired
    private  SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public int updateBatch(List<SysRolePermission> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<SysRolePermission> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<SysRolePermission> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SysRolePermission record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SysRolePermission record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonResult insertRolePermission(SysRolePermission sysRolePermission) {
        String  permissionId=sysRolePermission.getPermissionId();
        List<SysRole>  sysRoles= JSON.parseArray(sysRolePermission.getJsonStr(), SysRole.class);
        List<String>  sysRoleIdList=sysRoles.stream().map(e->e.getRoleId()).collect(Collectors.toList());
        List<SysRolePermission> sysRolePermissions=new ArrayList<>();
        for (String str:sysRoleIdList) {
            sysRolePermissions.add(new SysRolePermission(str,permissionId));
        }
       int i= sysRolePermissionMapper.batchInsert(sysRolePermissions);
        CommonResult<SysRolePermission> commonResult=new CommonResult<>();
        if (i>0) {
            commonResult.setMsg("新增权限角色关联成功");
            commonResult.setCode(200);
        }else{
            commonResult.setMsg("新增权限角色关联失败，请联系关联员");
            commonResult.setCode(500);
        }
        return commonResult;
    }

    @Override
    public List<SysRolePermission> querySysRolePermission(List<String> sysRoleIdList) {
        QueryWrapper<SysRolePermission> sysRolePermissionQueryWrapper=new QueryWrapper<>();
        sysRolePermissionQueryWrapper.lambda().in(SysRolePermission::getRoleId,sysRoleIdList);
        List<SysRolePermission>  sysRolePermissionList= sysRolePermissionMapper.selectList(sysRolePermissionQueryWrapper);
        return sysRolePermissionList;
    }

    @Override
    public List<SysRolePermission> querySysRolePermissionListById(SysRolePermission sysRolePermission) {
        QueryWrapper<SysRolePermission>  sysUserPermissionQueryWrapper=new QueryWrapper<>();
        sysUserPermissionQueryWrapper.lambda().eq(SysRolePermission::getRoleId,sysRolePermission.getRoleId());
        List<SysRolePermission> list= sysRolePermissionMapper.selectList(sysUserPermissionQueryWrapper);
        return  list;
    }

    @Override
    public CommonResult insertSysRolePermissionInRole(String roleId, String jsonStr) {

        String[]  permissionIdArray=jsonStr.split(",");
        List<SysRolePermission> sysRolePermissions = new ArrayList<>(16);
        for (String str : permissionIdArray) {
            sysRolePermissions.add(new SysRolePermission(roleId, str));
        }
        int i = sysRolePermissionMapper.batchInsert(sysRolePermissions);
        if (i > 0) {
            CommonResult<SysRolePermission> commonResult = new CommonResult<>(200, "分配权限数据成功", null);
            return commonResult;
        } else {
            CommonResult<SysRolePermission> commonResult = new CommonResult<>(500, "分配权限数据失败，请联系管理员", null);
            return commonResult;
        }


    }

    @Override
    public CommonResult<SysRolePermission> deleteSysRolePermissionInRole(SysRolePermission sysRolePermission) {
        String[]  permissionIdArray=sysRolePermission.getJsonStr().split(",");
//        List<SysRolePermission> sysRolePermissions = new ArrayList<>(16);
//        for (String str : permissionIdArray) {
//            sysRolePermissions.add(new SysRolePermission(sysRolePermission.getRoleId(),str));
//        }
        QueryWrapper<SysRolePermission>  queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SysRolePermission::getRoleId,sysRolePermission.getRoleId()).in(SysRolePermission::getPermissionId,permissionIdArray);
        int i= sysRolePermissionMapper.delete(queryWrapper);
        if (i > 0) {
            CommonResult<SysRolePermission> commonResult = new CommonResult<>(200, "解除角色绑定成功", null);
            return commonResult;
        } else {
            CommonResult<SysRolePermission> commonResult = new CommonResult<>(500, "解除角色绑定失败，请联系管理员", null);
            return commonResult;
        }
    }
}

