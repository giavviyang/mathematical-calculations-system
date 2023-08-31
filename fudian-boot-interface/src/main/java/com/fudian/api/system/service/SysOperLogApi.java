package com.fudian.api.system.service;

import com.fudian.api.system.pojo.SysOperLog;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 15:21
 * @Desc:
 */
public interface SysOperLogApi {

    public boolean save(SysOperLog entity);

    public CommonGridResult queryOperLog(SysOperLog sysOperLog);

}
