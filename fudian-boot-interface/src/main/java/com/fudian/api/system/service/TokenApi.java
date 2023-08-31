package com.fudian.api.system.service;

import com.fudian.api.system.pojo.LoginUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 15:14
 * @Desc:
 */
public interface TokenApi {

    public LoginUser getLoginUser(HttpServletRequest request);
}
