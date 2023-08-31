package com.fudian.project.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.mapper.SysUserPermissionMapper;
import com.fudian.project.system.pojo.SysPermission;
import com.fudian.project.system.pojo.SysUserPermission;
import com.fudian.project.system.service.SysUserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserPermissionServiceImpl extends ServiceImpl<SysUserPermissionMapper, SysUserPermission> implements SysUserPermissionService {
    @Autowired
    private  SysUserPermissionMapper sysUserPermissionMapper;

    @Override
    public int updateBatch(List<SysUserPermission> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<SysUserPermission> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SysUserPermission record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SysUserPermission record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonResult<SysUserPermission> insertUserPermissions(String userId, String data) {
        List<SysPermission>  sysPermissions= JSON.parseArray(data, SysPermission.class);
        List<String>  sysPermissionIds=sysPermissions.stream().map(e->e.getPermissionId()).collect(Collectors.toList());
        List<SysUserPermission> sysUserPermissions=new ArrayList<>();
        for (String  str:sysPermissionIds) {
            sysUserPermissions.add(new SysUserPermission(userId,str));
        }
         int i=   sysUserPermissionMapper.batchInsert(sysUserPermissions);
        CommonResult<SysUserPermission> commonResult=new CommonResult<>();
        if (i>0) {
            commonResult.setMsg("新增用户数据权限关联成功");
            commonResult.setCode(200);
        }else{
            commonResult.setMsg("新增用户数据权限关联失败，请联系关联员");
            commonResult.setCode(500);
        }
        return  commonResult;
    }

    @Override
    public boolean deleteUserPermissionsByPermissionId(SysUserPermission sysUserPermission) {
        //获取用户对象集合
        List<SysUser>  sysUsers=JSON.parseArray(sysUserPermission.getJsonStr(), SysUser.class);
        //获取用户的Id
        List<String>  userIdList=sysUsers.stream().map(e->e.getUserId()).collect(Collectors.toList());
        String permissionId=sysUserPermission.getPermissionId();
        List<SysUserPermission>  sysUserPermissions=new ArrayList<>();
        for (int i = 0; i <userIdList.size(); i++) {
            sysUserPermissions.add(new SysUserPermission(userIdList.get(i),permissionId));
        }
        QueryWrapper<SysUserPermission> sysUserPermissionQueryWrapper=new  QueryWrapper<>();
        sysUserPermissionQueryWrapper.lambda().eq(SysUserPermission::getPermissionId,permissionId)
                                                .in(SysUserPermission::getUserId,userIdList);
        int i=sysUserPermissionMapper.delete(sysUserPermissionQueryWrapper);
        boolean b=false;
        if (i>0) {
            b=true;
        }
        return b;
    }

    @Override
    public List<SysUserPermission> querySysUserPermissionList(String userId) {
        //获取当前的用户id的权限 查询参数
        QueryWrapper<SysUserPermission>  sysUserPermissionQueryWrapper=new QueryWrapper<>();
        sysUserPermissionQueryWrapper.lambda().eq(SysUserPermission::getUserId,userId);
        List<SysUserPermission> list= sysUserPermissionMapper.selectList(sysUserPermissionQueryWrapper);
        return  list;
    }

    @Override
    public List<SysUserPermission> querySysUserPermissionListById(SysUserPermission sysUserPermission) {
        QueryWrapper<SysUserPermission>  sysUserPermissionQueryWrapper=new QueryWrapper<>();
        sysUserPermissionQueryWrapper.lambda().eq(SysUserPermission::getUserId,sysUserPermission.getUserId());
        List<SysUserPermission> list= sysUserPermissionMapper.selectList(sysUserPermissionQueryWrapper);
        return  list;
    }
}
