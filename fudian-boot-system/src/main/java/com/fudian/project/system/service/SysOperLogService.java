package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.SysOperLog;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;

public interface SysOperLogService extends IService<SysOperLog> {


    int updateBatch(List<SysOperLog> list);

    int batchInsert(List<SysOperLog> list);

    int insertOrUpdate(SysOperLog record);

    int insertOrUpdateSelective(SysOperLog record);

    /**
     * 功能描述：获取当前条件下所有的操作记录
     * @author lutao
     * @param
     * @return :
     * @date    2020/3/5
    */
    CommonGridResult queryOperLog(SysOperLog sysOperLog);
}
