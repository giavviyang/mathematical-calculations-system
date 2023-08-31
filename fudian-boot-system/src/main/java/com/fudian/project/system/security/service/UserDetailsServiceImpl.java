package com.fudian.project.system.security.service;

import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.project.system.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户验证处理
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = null;
        if(!username.equals("apply")){
            user = userService.queryUserByUsername(username);
        }else{//临时借阅
            user = new SysUser();
            user.setLoginPassword("$2a$10$v0kRUDZLOHNLvIZUEyhPpOgj3n.zkYsidw2vq/eX6NFXlfsPzJG0y");
            user.setUserId("apply");

        }
//        if(user == null){
//            return null;
//        }


        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user, permissionService.getMenuPermission(user));
    }
}
