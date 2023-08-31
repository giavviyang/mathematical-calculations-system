package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.SysDataPermission;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.system.mapper.SysPermissionDataMapper;
import com.fudian.project.system.pojo.SysPermissionData;
import com.fudian.project.system.service.SysPermissionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysPermissionDataServiceImpl extends ServiceImpl<SysPermissionDataMapper, SysPermissionData> implements SysPermissionDataService {

    @Autowired
    private   SysPermissionDataMapper   sysPermissionDataMapper;

    @Override
    public int updateBatch(List<SysPermissionData> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<SysPermissionData> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SysPermissionData record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SysPermissionData record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonResult insertSysPermissionData(List<SysPermissionData> sysPermissionDatas) {
        int i=baseMapper.batchInsert(sysPermissionDatas);
        CommonResult<SysDataPermission> commonResult=null;
        if(i>0){
             commonResult=new CommonResult(200,"新增权限数据关联成功",i);
        }else {
              commonResult=new CommonResult(500,"新增权限数据关联失败，请联系管理员",null);
        }
        return  commonResult;
    }

    @Override
    public List<String> queryDataPermissionIdsByPermissionId(String permissionId) {
        QueryWrapper<SysPermissionData>  queryWrapper=new QueryWrapper();
        queryWrapper.lambda().eq(SysPermissionData::getPermissionId,permissionId);
        List<SysPermissionData>  list= sysPermissionDataMapper.selectList(queryWrapper);
        List<String>  dataPermissionIds=list.stream().map(e->e.getDataPermissionId()).collect(Collectors.toList());
        return dataPermissionIds;
    }

    @Override
    public CommonResult deletePermissionDataByPermissionId(String permissionId) {
        int  i=sysPermissionDataMapper.deleteById(permissionId);
        CommonResult<SysDataPermission> commonResult=null;
        if(i>0){
            commonResult=new CommonResult(200,"删除权限数据关联成功",i);
        }else {
            commonResult=new CommonResult(500,"删除权限数据关联失败，请联系管理员",null);
        }
        return  commonResult;
    }

    @Override
    public CommonResult deletePermissionDataByMap(SysPermissionData sysPermissionData) {
        Map<String,Object>  map=sysPermissionData.getParams();
        int i= sysPermissionDataMapper.deleteByMap(map);
        CommonResult<SysDataPermission> commonResult=null;
        if(i>0){
            commonResult=new CommonResult(200,"删除权限数据关联成功",i);
        }else {
            commonResult=new CommonResult(500,"删除权限数据关联失败，请联系管理员",null);
        }
        return  commonResult;
    }

    @Override
    public List<SysPermissionData> querySysPermissionDataList(List<String> premissionIdList) {
        QueryWrapper<SysPermissionData>  sysPermissionDataQueryWrapper=new QueryWrapper<>();
        sysPermissionDataQueryWrapper.lambda().in(SysPermissionData::getPermissionId,premissionIdList);
        List<SysPermissionData>  sysPermissionDataList=sysPermissionDataMapper.selectList(sysPermissionDataQueryWrapper);
        return sysPermissionDataList;

    }
}
