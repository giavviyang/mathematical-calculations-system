package com.fudian.project.system.controller;

import com.fudian.api.system.pojo.LoginUser;
import com.fudian.common.constant.HttpStatus;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.project.system.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/7/2 17:09
 * @Desc:
 */
@Slf4j
@RestController
@RequestMapping("logout")
public class LogOutController {
//
//    @Autowired
//    DataStoreWrapper dataStoreWrapper;

    @Autowired
    TokenService tokenService;

    @PostMapping("logout")
    public CommonResult logout(HttpServletRequest request){
        LoginUser loginUser = tokenService.getLoginUser(request);
        try{
            if (StringUtils.isNotNull(loginUser))
            {
                String userName = loginUser.getUser().getUserName();
                // 删除用户缓存记录
                tokenService.delLoginUser(loginUser.getToken());
            }
        }catch(Exception e){
            log.error("用户未在登录状态");
        }

        return CommonResult.error(HttpStatus.SUCCESS, "退出成功");
    }
}
