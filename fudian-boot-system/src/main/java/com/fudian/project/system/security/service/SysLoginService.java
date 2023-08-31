package com.fudian.project.system.security.service;

import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.common.enumns.ExceptionEnum;
import com.fudian.common.exception.FuDianException;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.project.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataStoreWrapper redisCache;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String login(String username, String password) {
        // 用户验证
        Authentication authentication = null;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            }
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername

        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new FuDianException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
            } else if (e instanceof NullPointerException) {
                throw new FuDianException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
            } else if (e instanceof InternalAuthenticationServiceException) {
                if ("账户已停用".equals(e.getMessage())) {
                    throw new FuDianException(ExceptionEnum.CURRENT_USER_DISABLE);
                } else {
                    throw new FuDianException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
                }
            } else {
                throw new FuDianException(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 登录验证
     *
     * @param userCode 用户凭证
     * @return 结果
     */
    public String loginByUserCode(String userCode) {
        // 用户验证
        Authentication authentication = null;

        SysUser sysUser = sysUserService.getById(userCode);

        if(sysUser == null){
            throw new FuDianException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }else{
            userDetailsService.createLoginUser(sysUser);
        }

        LoginUser loginUser = (LoginUser) userDetailsService.createLoginUser(sysUser);
        loginUser.setUser(sysUser);
        loginUser.setLoginTime(System.currentTimeMillis());
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
