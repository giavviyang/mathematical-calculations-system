package com.fudian.common.system.api;

import com.fudian.common.system.pojo.SysUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @Description 底层共通业务API，提供其他独立模块调用
 * @ClassName SystemApi
 * @Author mr.hou
 * @Date 2020/5/13 6:27 下午
 * @Version 1.0.0
 **/
public interface SystemApi {

    /**
     * 根据用户账号查询用户信息
     * @param username 用户名
     * @return
     */
    SysUser getUserByUserName(String username);

    /**
     * 根据用户id查询用户信息
     * @param id 用户Id
     * @return
     */
    SysUser getUserById(String id);

    /**
     * 获取当前登录的用户信息
     * @param request
     * @return
     */
    SysUser getLoginUser(HttpServletRequest request);

    /**
     * 根据用户名获取用户菜单权限
     * @param userName
     * @return
     */
    Set<String> getMenuPermission(String userName);


}
