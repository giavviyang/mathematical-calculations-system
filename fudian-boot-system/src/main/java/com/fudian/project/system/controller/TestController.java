package com.fudian.project.system.controller;

import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.common.utils.ServletUtils;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 测试Controller
 * @ClassName TestController
 * @Author mr.hou
 * @Date 2019/12/27 5:11 下午
 * @Version 1.0.0
 **/
@RestController
@RequestMapping("system")
@Api(tags = "用户相关接口", description = "提供用户相关的 Rest API")
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private TokenService tokenService;



    //@PreAuthorize("@ss.hasPermi('system:user:query')")   //权限验证前置
    @GetMapping("user")
    //@Log(title = "用户管理", businessType = BusinessType.QUERY) //日志记录
    @ApiOperation("新增用户")
    public ResponseEntity queryUser(String loginCode,String password){
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        SysUser user = this.sysUserService.queryUser(loginCode,password);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return ResponseEntity.ok(user);
    }

    @GetMapping("userTest")
    @ApiOperation("获取用户")
    //@ApiImplicitParam(name = "user", value = "获取用户参数对象", dataType = "SysUser")
    public SysUser queryUser(SysUser user){
       String password = DigestUtils.md5DigestAsHex(user.getLoginPassword().getBytes());
        return sysUserService.queryUser(user.getLoginCode(),password);
    }

}
