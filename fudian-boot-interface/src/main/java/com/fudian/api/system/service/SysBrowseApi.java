package com.fudian.api.system.service;

import com.fudian.api.system.pojo.SysBrowseLog;
import com.fudian.api.system.pojo.SysOperLog;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/29 9:42
 * @Desc:
 */
public interface SysBrowseApi {

    public List<SysBrowseLog> queryAllLog(SysBrowseLog sysBrowseLog);

    public boolean save(SysBrowseLog entity);


}
