package com.fudian.project.system.security.filter;

import com.alibaba.fastjson.JSON;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.common.constant.HttpStatus;
import com.fudian.common.enumns.ExceptionEnum;
import com.fudian.common.exception.FuDianException;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        LoginUser loginUser = null;
        try {
            loginUser = tokenService.getLoginUser(request);
        } catch (FuDianException e) {
            if (e.getExceptionEnum() == ExceptionEnum.LOGIN_TIMEOUT) {
                int code = HttpStatus.LOGIN_TIMEOUT;
                String msg = StringUtils.format(e.getMessage(), request.getRequestURI());
                CommonResult error = CommonResult.error(code, msg);
                String erroMsg = JSON.toJSONString(error);
                ServletUtils.renderString(response,erroMsg);
                return;
            }
        }
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
}
