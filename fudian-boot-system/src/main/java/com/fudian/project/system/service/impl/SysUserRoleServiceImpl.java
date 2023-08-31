package com.fudian.project.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.mapper.SysUserRoleMapper;
import com.fudian.project.system.pojo.SysRole;
import com.fudian.project.system.pojo.SysUserRole;
import com.fudian.project.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public int updateBatch(List<SysUserRole> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SysUserRole> list) {
        this.saveBatch(list);
        return list.size();
//        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SysUserRole record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SysUserRole record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonResult<SysUserRole> insertSysUserRole(SysUserRole sysUserRole) {
        List<SysRole>  sysRoles= JSON.parseArray(sysUserRole.getJsonStr(), SysRole.class);
        List<String>  roleIdList=sysRoles.stream().map(e->e.getRoleId()).collect(Collectors.toList());
        List<SysUserRole> sysUserRoles = new ArrayList<>(16);
        for (String str : roleIdList) {
            sysUserRoles.add(new SysUserRole(sysUserRole.getUserId(), str));
        }
        int i = sysUserRoleMapper.batchInsert(sysUserRoles);
        if (i > 0) {
            CommonResult<SysUserRole> commonResult = new CommonResult<>(200, "分配角色用户数据成功", null);
            return commonResult;
        } else {
            CommonResult<SysUserRole> commonResult = new CommonResult<>(500, "分配角色用户数据失败，请联系管理员", null);
            return commonResult;
        }
    }

    @Override
    public CommonResult<SysUserRole> insertSysUserRoles(SysUserRole sysUserRole) {
        LambdaQueryWrapper<SysUserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUserRole::getUserId,sysUserRole.getUserId());
        boolean flag = this.remove(lambdaQueryWrapper);
        if(sysUserRole.getOtherParam()!=null&&!"".equals(sysUserRole.getOtherParam())) {
            String[] roleIds = sysUserRole.getOtherParam().split(",");
            List<SysUserRole> list = new ArrayList<>();
            for (String roleId : roleIds) {
                SysUserRole userRole = new SysUserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(sysUserRole.getUserId());
                list.add(userRole);
            }
            int i = this.batchInsert(list);
            if(i>0){
                return CommonResult.success("指定成功");
            }else{
                return CommonResult.success("指定失败");
            }
        }
        if(flag){
            return CommonResult.success("清空成功");
        }else{
            return CommonResult.success("清空失败");
        }
    }

    @Override
    public CommonResult<SysUserRole> deleteSysUserRoleByRoleId(String roleId) {
        int i = sysUserRoleMapper.deleteById(roleId);
        if (i > 0) {
            CommonResult<SysUserRole> commonResult = new CommonResult<>(200, "删除角色用户数据成功", null);
            return commonResult;
        } else {
            CommonResult<SysUserRole> commonResult = new CommonResult<>(200, "删除角色用户数据失败，请联系管理员", null);
            return commonResult;
        }
    }

    @Override
    public List<String> querySysUserIdByRoleId(SysUserRole sysUserRole) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(SysUserRole::getRoleId, sysUserRole.getRoleId());
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(queryWrapper);
        List<String> userIds = sysUserRoles.stream().map(e -> e.getUserId()).collect(Collectors.toList());
        return userIds;
    }

    @Override
    public int updateBatchSelective(List<SysUserRole> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public List<SysUserRole> querySysUserRoleList(String userId) {
        QueryWrapper<SysUserRole>  sysUserRoleQueryWrapper=new QueryWrapper<>();
        sysUserRoleQueryWrapper.lambda().eq(SysUserRole::getUserId,userId);
        List<SysUserRole>  list=sysUserRoleMapper.selectList(sysUserRoleQueryWrapper);
        return list;

    }

    @Override
    public List<SysUserRole> querySysUserPermissionListById(SysUserRole sysUserRole) {
        QueryWrapper<SysUserRole>  sysUserRoleQueryWrapper=new QueryWrapper<>();
        sysUserRoleQueryWrapper.lambda().eq(SysUserRole::getUserId,sysUserRole.getUserId());
        List<SysUserRole>  list=sysUserRoleMapper.selectList(sysUserRoleQueryWrapper);
        return list;
    }

    @Override
    public CommonResult<SysUserRole> insertSysUserRoleInRole(String roleId, String jsonStr) {
        String[]  userIdArray=jsonStr.split(",");
        List<SysUserRole> sysUserRoles = new ArrayList<>(16);
        for (String str : userIdArray) {
            sysUserRoles.add(new SysUserRole(str, roleId));
        }
        int i = sysUserRoleMapper.batchInsert(sysUserRoles);
        if (i > 0) {
            CommonResult<SysUserRole> commonResult = new CommonResult<>(200, "分配角色用户数据成功", null);
            return commonResult;
        } else {
            CommonResult<SysUserRole> commonResult = new CommonResult<>(500, "分配角色用户数据失败，请联系管理员", null);
            return commonResult;
        }

    }

    @Override
    public CommonResult<SysUserRole> deleteSysUserRoleInRole(SysUserRole sysUserRole) {
        String[]  userIdArray=sysUserRole.getJsonStr().split(",");
        List<SysUserRole> sysUserRoles = new ArrayList<>(16);
        for (String str : userIdArray) {
            sysUserRoles.add(new SysUserRole(str, sysUserRole.getRoleId()));
        }
        QueryWrapper<SysUserRole>  queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserRole::getRoleId,sysUserRole.getRoleId()).in(SysUserRole::getUserId,userIdArray);
        int i= sysUserRoleMapper.delete(queryWrapper);
        if (i > 0) {
            CommonResult<SysUserRole> commonResult = new CommonResult<>(200, "解除角色绑定成功", null);
            return commonResult;
        } else {
            CommonResult<SysUserRole> commonResult = new CommonResult<>(500, "解除角色绑定失败，请联系管理员", null);
            return commonResult;
        }
    }
}

