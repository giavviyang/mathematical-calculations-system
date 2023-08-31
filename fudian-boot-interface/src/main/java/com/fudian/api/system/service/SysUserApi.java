package com.fudian.api.system.service;

import com.fudian.api.system.pojo.SysUser;

import java.io.Serializable;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 17:02
 * @Desc:
 */
public interface SysUserApi {

    public SysUser getById(Serializable id);
}
