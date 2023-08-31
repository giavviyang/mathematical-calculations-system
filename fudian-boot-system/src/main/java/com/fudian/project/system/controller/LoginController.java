package com.fudian.project.system.controller;

import com.fudian.api.system.pojo.LoginUser;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.project.system.pojo.SysMenu;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.project.system.pojo.SysUserRole;
import com.fudian.project.system.security.service.PermissionService;
import com.fudian.project.system.security.service.SysLoginService;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.SysMenuService;
import com.fudian.project.system.service.SysUserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description 登录管理Controller
 * @ClassName LoginController
 * @Author mr.hou
 * @Date 2019/12/26 2:14 下午
 * @Version 1.0.0
 **/
@RestController
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysMenuService menuService;


    @GetMapping("login")
    public CommonResult<Map<String,Object>> login(
            @ApiParam(required = true)
                    String username,
            @ApiParam(required = true)
                    String password,SysUser user
    ) {

        String encode = new BCryptPasswordEncoder().encode(password);
        System.out.println(encode);

        // 生成令牌
        String token = loginService.login(username, password);
        CommonResult result = CommonResult.success();
        result.put("token",token);
        return result;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public CommonResult<Map<String,Object>> getInfo() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        Set<String> roleSet;
        Set<String> permissions;
        CommonResult result = CommonResult.success();

        if(user.getUserId().equals("apply")){//临时借阅
            permissions =new HashSet<>();
            permissions.add("*:*:*");
            roleSet = new HashSet<>();
            roleSet.add("apply");
            result.put("user", user);
           return result;
        }

        if (user.getUserId() != null && "admin".equals(user.getUserId())) {
            roleSet = new HashSet<>();
            roleSet.add("admin");
            permissions =new HashSet<>();
            permissions.add("*:*:*");
        } else {
            // 角色集合
            List<SysUserRole> roles = permissionService.getRolePermission(user);
            roleSet = roles.stream().map(e -> e.getRoleId()).collect(Collectors.toSet());
            //Set<String> roleList = new HashSet<>();
            //roleList.add("admin");

            // 权限集合
            permissions = permissionService.getMenuPermission(user,roles);
            //Set<String> permissions =new HashSet<>();
            //permissions.add("*:*:*");
        }




        result.put("user", user);
        result.put("roles", roleSet);
        result.put("permissions", permissions);
       return result;
    }


    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public CommonResult getRouters() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return CommonResult.success(menuService.buildMenus(menus));
    }


}
