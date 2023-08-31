package com.fudian.project.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.common.annotation.Log;
import com.fudian.common.enumns.BusinessType;
import com.fudian.common.enumns.ExceptionEnum;
import com.fudian.common.enumns.OperateEnum;
import com.fudian.common.enumns.UserStatus;
import com.fudian.common.exception.FuDianException;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.project.system.pojo.SysRole;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.project.system.pojo.SysUserRole;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.security.utils.SecurityUtils;
import com.fudian.project.system.service.SysDeptService;
import com.fudian.project.system.service.SysRoleService;
import com.fudian.project.system.service.SysUserRoleService;
import com.fudian.project.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

;

/**
 * @Description 系统用户处理Controller
 * @ClassName SysUserController
 * @Author mr.hou
 * @Date 2019/11/28 6:21 下午
 * @Version 1.0.0
 **/
@CrossOrigin(origins = "*")
@Api(value="/user", tags="测试接口模块")
@RequestMapping("user")
@RestController
@OperTitle("用户管理")
public class SysUserController {
    private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 获取用户信息
     * @param request
     * @return
     */
    @GetMapping("getLoginUserInfo")
    @OperInfo(info = "获取用户信息")
    public ResponseEntity getLoginUserIfon(HttpServletRequest request){
        SysUser sysUser = tokenService.getLoginUser(request).getUser();
        SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
        sysUser.setDeptId(sysDept == null?"":sysDept.getDeptName());
        return ResponseEntity.ok(CommonResult.success(sysUser));

    }

    /**
     * 修改用户信息
     * @param request
     * @param sysUser
     * @return
     */
    @PutMapping("updateLoginUserInfo")
    @OperInfo(info = "修改用户信息")
    public ResponseEntity updateLoginUserInfo(HttpServletRequest request,SysUser sysUser){

        sysUser.setUpdateTime(new Date());
        if(userService.updateById(sysUser)){
            LoginUser loginUser = tokenService.getLoginUser(request);
            loginUser.setUser(sysUser);
            tokenService.refreshToken(loginUser);
            return ResponseEntity.ok(CommonResult.success("操作成功"));
        }else{
            return ResponseEntity.ok(CommonResult.error("操作失败"));
        }

    }

    /**
     *
     * @param request
     * @param oldp 旧密码
     * @param newp 新密码
     * @return 用户修改自身密码
     */
    @PutMapping("updateUserPwd")
    @OperInfo(info = "用户修改密码")
    public ResponseEntity<CommonResult> updateUserPwd(HttpServletRequest request, String oldp, String newp){
        LoginUser loginUser = tokenService.getLoginUser(request);
//        oldp = SecurityUtils.encryptPassword(oldp);

        //判断用户输入的旧密码跟之前旧密码是否相同
        SysUser sysUser = loginUser.getUser();
        boolean b = SecurityUtils.matchesPassword(oldp,sysUser.getLoginPassword());
        if(!b){
            return ResponseEntity.ok(CommonResult.error("请输入正确的旧密码"));
        }else{
            if(userService.updateUserPwd(sysUser.getUserId(),newp)){
                return ResponseEntity.ok(CommonResult.success("操作成功"));
            }else{
                return ResponseEntity.ok(CommonResult.error("操作失败"));
            }
        }

    }

    /**
     * 用户验证
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value="验证用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "body ", defaultValue = ""),
        @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "body ", defaultValue = "")
    })

//   @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping
    @Log(title = "用户管理", operateType = OperateEnum.QUERY)
    public ResponseEntity<SysUser> queryUser(@RequestParam("username")String username, @RequestParam("password")String password){
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        SysUser user = this.userService.queryUser(username,password);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return ResponseEntity.ok(user);
    }
    @GetMapping("/{username}/{password}")
    @Log(title = "用户管理", operateType = OperateEnum.QUERY)
    public ResponseEntity<SysUser> queryUser2(@PathVariable("username") String username, @PathVariable("password")String password){
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        SysUser user = this.userService.queryUser(username,password);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<SysUser> queryUserByUsername(@RequestParam("username")String username){
        SysUser user = this.userService.queryUserByUsername(username);
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new FuDianException(ExceptionEnum.CURRENT_USER_NOT_EXIST);
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getUserStatus()))
        {
            log.info("登录用户：{} 已被禁用.", username);
            throw new FuDianException(ExceptionEnum.CURRENT_USER_DISABLE);
        }

        return ResponseEntity.ok(user);
    }

    /**
     * 查询用户
     * @param sysUser 系统用户对象
     * @return  CommonGridResult
     */
    @GetMapping("queryUserList")
    public CommonGridResult queryUserList(SysUser sysUser){
//        Map<String,Object> map= JSON.parseObject(sysUser.getJsonStr());
        CommonGridResult commonGridResult=userService.queryUserList(sysUser);
        return   commonGridResult;
    }

    @GetMapping("queryUserListNew")
    public CommonGridResult queryUserListNew(SysUser sysUser){
        return userService.queryUserListNew(sysUser);
    }

    @PutMapping("updateUserByIdNew")
    public CommonResult updateUserByIdNew(SysUser sysUser){
        return userService.updateUserByIdNew(sysUser);
    }

    /**
     * 依据map查询出
     * 当前得数据
     * @param userName  参数
   \ * @param roleId  参数
     * @return  CommonResult
     */
    @GetMapping("queryUserListByMap")
    public CommonResult queryUserListByMap(String userName, String roleId){
        Map<String,String>  map=new HashMap<>();
        map.put("userName",userName);
        map.put("roleId",roleId);
        List<SysUser> list=userService.queryUserListByParamters(map);
        return new CommonResult(200,"",list);
    }

    /**
     * 查询用户
     * @param sysUser
     * @return
     */
    @GetMapping("queryAddUserList")
    public CommonGridResult queryAddUserList(SysUser sysUser) {
        CommonGridResult commonGridResult = userService.queryAddUserList(sysUser);
        return commonGridResult;
    }

    /**
     * 新增用户对象
     * @param sysUser 用户对象
     * @return   CommonResult<SysUser>
     */
    @PostMapping("insertSysUser")
    public CommonResult<SysUser> insertUser(SysUser   sysUser){
        CommonResult<SysUser> commonResult=userService.insertUser(sysUser);
        return  commonResult;
    }

    /**
     * 更显当前的系统用户
     * @param sysUser 系统用户对象
     * @return CommonResult<SysUser>
     */
    @PutMapping("updateUserById")
    public CommonResult<SysUser> updateUserById(SysUser  sysUser){
        CommonResult<SysUser> commonResult=userService.updateUserById(sysUser);
        return    commonResult;
    }

    /**
     * 添加测试数据
     */
//    @GetMapping("insertDataUser")
//    public void insert(){
//        for (int i = 0; i < 999; i++) {
//            SysUser sysUser = new SysUser();
//            sysUser.setUserName("测试"+i);
//            sysUser.setLoginCode("ceshi"+i);
//            sysUser.setUserStatus(0);
//            sysUser.setDeptId("16a9a85ffe0744d1b7273a307bb86bea");
//            userService.insertUser(sysUser);
//        }
//    }

    /**
     * 删除当前系统用户，当前为假删除
     * @param sysUser 用户Id拼接的字符串
     * @return CommonResult<SysUser>
     */
    @DeleteMapping("deleteUserById")
    public CommonResult<SysUser> deleteUserById(SysUser  sysUser){
         String  jsonStr=sysUser.getJsonStr();
        List<String> userIdList=null;
        if (jsonStr != null) {
            String[]  userIds=jsonStr.split(",");
            userIdList= Arrays.asList(userIds);
        }else {
            userIdList=new ArrayList<>();
            userIdList.add(sysUser.getUserId());
        }
        if (userIdList != null) {
            CommonResult<SysUser> commonResult=userService.deleteUserById(userIdList);
            return   commonResult;
        }else {
            return   new CommonResult<>(200,"系统异常请联系管理员",null);
        }
    }

    /**
     *  依据权限id通过关联表查询当前用户下的用户
     * @param permissionId  权限id
     * @return  List<SysUser>
     */
    @GetMapping("queryUserByPermissionId")
    public  List<SysUser>   queryUserByPermissionId(String  permissionId){
        List<SysUser>   commonResult=userService.queryUserByPermissionId(permissionId);
        return  commonResult;
    }

    /**
     *  查询部门用户树
     * @return  List<SysUser>
     */
    @GetMapping("queryDeptUserTree")
    public CommonResult queryDeptUserTree(){
        CommonResult commonResult=userService.queryDeptUserTree();
        return  commonResult;
    }

    /**
     * 依据角色查询当前角色下绑定的用户
     * @param sysUserRole  角色id
     * @return  List<SysUser>
     */
    @GetMapping("queryUserByRoleId")
    public CommonResult queryUserByRoleId(SysUserRole sysUserRole){
        List<SysUser>   list=userService.queryUserByRoleId(sysUserRole);
        return  new CommonResult(200,"",list);
    }

    /**
     * 依据角色id查询未绑定的用户数据
     * @param roleId 权限对象
     * @return CommonResult
     */
    @GetMapping("queryRemainingUserListByRoleId")
    public CommonResult queryRemainingUserListByRoleId(String roleId){
        List<SysUser>   list =userService.queryRemainingUserListByRoleId(roleId);
        return  new CommonResult(200,"",list);

    }

    /**
     * 依据角色id查询数据权限
     * @param permissionId 权限对象
     * @return CommonResult
     */
    @GetMapping("queryUserListByPermissionId")
    public CommonResult queryUserListByPermissionId(String permissionId, SysUser sysUser){
        List<SysUser>   list =userService.queryUserListByPermissionId(permissionId,sysUser);
        return  new CommonResult(200,"",list);

    }

    /**
     +     * 给用户添加权限
     +     * @param permissionId 权限对象
     +     * @return CommonResult
     +     */
    @PostMapping("addUserAndPermission")
    public CommonResult addUserAndPermission(String userIds, String permissionId){
        try {
            userService.addUserAndPermission(userIds, permissionId);
        } catch (Exception e) {
            e.printStackTrace();
            CommonResult commonResult=new CommonResult();
            commonResult.setCode(500);
            commonResult.setMsg("保存失败");
        }
        CommonResult commonResult=new CommonResult();
        commonResult.setCode(200);
        return  commonResult;

    }

    /**
     * 依据权限id查询未绑定的用户数据
     * @param permissionId 权限对象
     * @return CommonResult
     */
    @GetMapping("queryRemainingUserListByPermissionId")
    public CommonResult queryRemainingUserListByPermissionId(String permissionId){
        List<SysUser>   list =userService.queryRemainingUserListByPermissionId(permissionId);
        CommonResult commonResult=new CommonResult();
        commonResult.setCode(200);
        commonResult.setData(list);
        return  commonResult;

    }

    /**
     * 重置账户密码
     * @param sysUser
     * @return CommonResult
     */
    @PutMapping("resetPassword")
    public CommonResult resetPassword(SysUser sysUser){
        return  userService.resetPassword(sysUser);

    }

    /**
     * 禁用账户
     * @param sysUser
     * @return CommonResult
     */
    @PutMapping("prohibitUser")
    public CommonResult prohibitUser(SysUser sysUser){

        return  userService.prohibitUser(sysUser);

    }

    /**
     * 获取所有角色列表
     * @return
     */
    @GetMapping("getAllRoleList")
    public CommonResult getAllRoleList(SysRole sysRole){
        LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.ne(SysRole::getRoleStatus,1);
        if (sysRole != null && StringUtils.isNotEmpty(sysRole.getRoleSummary())) {
            lambdaQueryWrapper.like(SysRole::getRoleSummary,sysRole.getRoleSummary());
        }
        if (sysRole != null && StringUtils.isNotEmpty(sysRole.getSchoolId())) {
            lambdaQueryWrapper.eq(SysRole::getSchoolId,sysRole.getSchoolId());
        }
        List<SysRole> list =  roleService.list(lambdaQueryWrapper);
        return CommonResult.success(list);

    }

    /**
     * 获取所有角色列表
     * @return
     */
    @GetMapping("getRoleListByUserId")
    public CommonResult getRoleListByUserId(SysUser sysUser){
        LambdaQueryWrapper<SysUserRole> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(SysUserRole::getUserId,sysUser.getUserId());
        List<SysUserRole> roleList=userRoleService.list(roleLambdaQueryWrapper);
        if(roleList!=null&&roleList.size()>0) {
            List<String> ids = new ArrayList<>();
            for(SysUserRole sysUserRole : roleList){
                ids.add(sysUserRole.getRoleId());
            }
            LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(SysRole::getRoleId,ids);
            lambdaQueryWrapper.ne(SysRole::getRoleStatus, 1);
            List<SysRole> list = roleService.list(lambdaQueryWrapper);
            return CommonResult.success(list);
        }else{
            return CommonResult.success(new ArrayList<>());
        }
    }



}
