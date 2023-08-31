package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.SysLogininfo;
import com.fudian.api.system.service.SysLogininfoApi;
import com.fudian.project.system.mapper.SysLogininfoMapper;
import com.fudian.project.system.service.SysLogininfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class SysLogininfoServiceImpl extends ServiceImpl<SysLogininfoMapper, SysLogininfo> implements SysLogininfoService, SysLogininfoApi {
    @Autowired
    private SysLogininfoMapper sysLogininfoMapper;

    @Override
    public int updateBatch(List<SysLogininfo> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<SysLogininfo> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(SysLogininfo record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(SysLogininfo record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public List<SysLogininfo> queryAllLog(SysLogininfo sysLogininfo) {
        LambdaQueryWrapper<SysLogininfo> wrapper=new LambdaQueryWrapper<>();
        if (sysLogininfo.getUserName()!=null &&!"".equals(sysLogininfo.getUserName())){
            wrapper.like(SysLogininfo::getUserName,sysLogininfo.getUserName());
        }
        if (sysLogininfo.getLoginTime()!=null&&!"".equals(sysLogininfo.getLoginTime())){
            wrapper.ge(SysLogininfo::getLoginTime,sysLogininfo.getLoginTime());
        }
        if (sysLogininfo.getEndTime()!=null&&!"".equals(sysLogininfo.getEndTime())){
            Date endTime = sysLogininfo.getEndTime();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(endTime);
            calendar.add(Calendar.DATE,1);
            wrapper.le(SysLogininfo::getLoginTime,calendar.getTime());
        }
        wrapper.orderByDesc(SysLogininfo::getLoginTime);
        PageHelper.startPage(sysLogininfo.getPageNum(),sysLogininfo.getPageSize());
        List<SysLogininfo> list=sysLogininfoMapper.selectList(wrapper);
        return list;
    }
}
