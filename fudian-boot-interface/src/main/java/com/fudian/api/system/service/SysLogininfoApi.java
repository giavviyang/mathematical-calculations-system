package com.fudian.api.system.service;

import com.fudian.api.system.pojo.SysLogininfo;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 15:29
 * @Desc:
 */
public interface SysLogininfoApi {

    public boolean save(SysLogininfo entity);

    public List<SysLogininfo> queryAllLog(SysLogininfo sysLogininfo);
}
