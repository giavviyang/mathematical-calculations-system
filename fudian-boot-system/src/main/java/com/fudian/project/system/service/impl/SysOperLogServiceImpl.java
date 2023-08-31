package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.SysOperLog;
import com.fudian.api.system.service.SysOperLogApi;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.system.mapper.SysOperLogMapper;
import com.fudian.project.system.service.SysOperLogService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService,SysOperLogApi{

    @Autowired
    SysOperLogMapper sysOperLogMapper;
    @Override
    public int updateBatch(List<SysOperLog> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<SysOperLog> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SysOperLog record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SysOperLog record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonGridResult queryOperLog(SysOperLog sysOperLog) {
        List<SysOperLog> logList=sysOperLogMapper.pagingQueryList(sysOperLog);
        int total = sysOperLogMapper.pagingQueryListCount(sysOperLog);

        CommonGridResult result=null;
        if (logList!=null&&logList.size()>0){
            result=new CommonGridResult(sysOperLog.getPageNum(),sysOperLog.getPageSize(),total,logList);
        }else {
            result=new CommonGridResult(sysOperLog.getPageNum(),sysOperLog.getPageSize(),0,logList);
        }
        return result;
    }
}
