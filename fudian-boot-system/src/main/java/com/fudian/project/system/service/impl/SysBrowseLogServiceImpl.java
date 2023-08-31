package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.SysBrowseLog;
import com.fudian.api.system.service.SysBrowseApi;
import com.fudian.project.system.mapper.SysBrowseLogMapper;
import com.fudian.project.system.service.SysBrowseLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  *@author lutao
  *@create 2020/3/9
*/

@Service
public class SysBrowseLogServiceImpl extends ServiceImpl<SysBrowseLogMapper, SysBrowseLog> implements SysBrowseLogService, SysBrowseApi {
    @Autowired
    private SysBrowseLogMapper sysBrowseLogMapper;

    @Override
    public int updateByPrimaryKeySelective(SysBrowseLog record) {
        return sysBrowseLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysBrowseLog record) {
        return sysBrowseLogMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<SysBrowseLog> list) {
        return sysBrowseLogMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SysBrowseLog> list) {
        return sysBrowseLogMapper.batchInsert(list);
    }

    @Override
    public List<SysBrowseLog> queryAllLog(SysBrowseLog sysBrowseLog) {
        LambdaQueryWrapper<SysBrowseLog> wrapper=new LambdaQueryWrapper<>();
        if (sysBrowseLog.getUserName()!=null &&!"".equals(sysBrowseLog.getUserName())){
            wrapper.like(SysBrowseLog::getUserName,sysBrowseLog.getUserName());
        }
        if (sysBrowseLog.getE01z111a()!=null&&!"".equals(sysBrowseLog.getE01z111a())){
            wrapper.like(SysBrowseLog::getE01z111a,sysBrowseLog.getE01z111a());
        }
        wrapper.orderByDesc(SysBrowseLog::getOperTime);
        PageHelper.startPage(sysBrowseLog.getPageNum(),sysBrowseLog.getPageSize());
        List<SysBrowseLog> list=sysBrowseLogMapper.selectList(wrapper);
        return list;
    }

}
