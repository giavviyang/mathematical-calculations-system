package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.mapper.SysPermissionMenuMapper;
import com.fudian.project.system.pojo.SysPermissionMenu;
import com.fudian.project.system.service.SysPermissionMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysPermissionMenuServiceImpl extends ServiceImpl<SysPermissionMenuMapper, SysPermissionMenu> implements SysPermissionMenuService {

    @Autowired
    SysPermissionMenuMapper sysPermissionMenuMapper;

    @Override
    public int updateBatch(List<SysPermissionMenu> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<SysPermissionMenu> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SysPermissionMenu record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SysPermissionMenu record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public List<SysPermissionMenu> querySysPermissionMenu(List<String> permissionIdList) {
        QueryWrapper<SysPermissionMenu>  sysPermissionMenuQueryWrapper=new QueryWrapper<>();
        if(permissionIdList!=null&&permissionIdList.size()>0){
            sysPermissionMenuQueryWrapper.lambda().in(SysPermissionMenu::getPermissionId,permissionIdList);
            List<SysPermissionMenu>  sysPermissionMenuList=sysPermissionMenuMapper.selectList(sysPermissionMenuQueryWrapper);
            return sysPermissionMenuList;
        }else{
            return new ArrayList<>();
        }


    }

    @Override
    public CommonResult selectSysPermissionMenuByPermissionId(String permissionId) {
        List<String> list = sysPermissionMenuMapper.selectSysPermissionMenuByPermissionId(permissionId);
        CommonResult<Object> result = new CommonResult<>();
        result.setCode(200);
        result.setData(list);
        return result;
    }
}
