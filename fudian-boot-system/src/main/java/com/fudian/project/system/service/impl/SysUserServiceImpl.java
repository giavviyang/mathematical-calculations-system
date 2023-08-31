package com.fudian.project.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.SysUserApi;
import com.fudian.api.system.service.TokenApi;
import com.fudian.common.constant.HttpStatus;
import com.fudian.common.enumns.ExceptionEnum;
import com.fudian.common.exception.FuDianException;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.system.mapper.*;
import com.fudian.project.system.pojo.SysRoleData;
import com.fudian.project.system.pojo.SysUserPermission;
import com.fudian.project.system.pojo.SysUserRole;
import com.fudian.project.system.security.utils.SecurityUtils;
import com.fudian.project.system.service.SysDeptService;
import com.fudian.project.system.service.SysDictionaryService;
import com.fudian.project.system.service.SysUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author mr.hou
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService, SysUserApi {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysUserPermissionMapper sysUserPermissionMapper;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private TokenApi tokenService;

    /**
     * 公平锁池 为了保证性能 对锁的粒度进行规划
     */
    private static final ConcurrentHashMap<String, Lock> LOCK_POOL = new ConcurrentHashMap<>();

    // 初始化
    static {
        // 统一采用公平锁
        // 新增锁
        LOCK_POOL.putIfAbsent("insert", new ReentrantLock(true));
        // 修改锁
        LOCK_POOL.putIfAbsent("modify", new ReentrantLock(true));
    }

    @Override
    public SysUser queryUser(String username, String password) {
        //拼凑查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(SysUser::getLoginCode, username)
                .eq(SysUser::getLoginPassword, password)
        ;
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public SysUser queryUserByUsername(String username) {
        //拼凑查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(SysUser::getLoginCode, username);

        // 设置只有启用的用户才可以登录
//        wrapper.eq(SysUser::getUserStatus, "0");
        SysUser sysUser = baseMapper.selectOne(wrapper);
        if (sysUser.getUserStatus() == 1) {
            throw new RuntimeException("账户已停用");
        }
        if (sysUser.getUserType() != null && sysUser.getUserType() == 2 && sysUser.getLoginPasswordDate().getTime() < System.currentTimeMillis()) {
            throw new FuDianException(ExceptionEnum.PASSWORD_TIMEOUT);
        }
        return sysUser;

    }

    @Override
    public int updateBatch(List<SysUser> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SysUser> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SysUser record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SysUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public CommonGridResult queryUserList(SysUser sysUser) {
        String deptId = sysUser.getDeptId();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (deptId != null && !deptId.equals("000")) {
            try {
                Class clazz = sysUser.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field f = fields[i];
                    String fieldName = f.getName();
                    Method m = clazz.getDeclaredMethod("get" + StringUtils.firstChar2UpperCase(fieldName), null);
                    Object b = m.invoke(sysUser, null);
                    if (b != null && b != "") {
                        TableId tableId = f.getAnnotation(TableId.class);

                        queryWrapper.eq(tableId.value(), b);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (deptId != null && deptId.equals("000")) {
            try {
                Class clazz = sysUser.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field f = fields[i];
                    String fieldName = f.getName();
                    if (!fieldName.equals("deptId")) {
                        Method m = clazz.getDeclaredMethod("get" + StringUtils.firstChar2UpperCase(fieldName), null);
                        Object b = m.invoke(sysUser, null);
                        if (b != null && b != "") {
                            TableId tableId = f.getAnnotation(TableId.class);
                            queryWrapper.eq(tableId.value(), b);
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Class clazz = sysUser.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field f = fields[i];
                    String fieldName = f.getName();
                    Method m = clazz.getDeclaredMethod("get" + StringUtils.firstChar2UpperCase(fieldName), null);
                    Object b = m.invoke(sysUser, null);
                    if (b != null && b != "") {
                        TableId tableId = f.getAnnotation(TableId.class);
                        queryWrapper.eq(tableId.value(), b);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        PageHelper.startPage(sysUser.getPageNum(), sysUser.getPageSize());
        List<SysUser> list = sysUserMapper.selectList(queryWrapper.lambda().ne(SysUser::getUserId, "admin"));
        Page page = (Page) list;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(sysUser.getPageNum(), sysUser.getPageSize(), total, list);
        return commonGridResult;
    }

    @Override
    public CommonGridResult queryUserListNew(SysUser sysUser) {

        if (StringUtils.isEmpty(sysUser.getDeptId())) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            if (!loginUser.getUser().getLoginCode().equals("admin")) {
                sysUser.setDeptId(loginUser.getUser().getDeptId());
            }
        }
        PageHelper.startPage(sysUser.getPageNum(), sysUser.getPageSize());
        List<Map> sysUsers = baseMapper.querySysUserByNameAndDeptId(sysUser);
        Page page = (Page) sysUsers;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(sysUser.getPageNum(), sysUser.getPageSize(), total, sysUsers);
        return commonGridResult;
    }

    /**
     * 查询所有指定ID的子ID集合
     *
     * @param deptIds
     * @param id
     */
    private void getDeptId(List<String> deptIds, String id) {
        if (!StringUtils.isEmpty(id)) {
            LambdaQueryWrapper<SysDept> sysDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysDeptLambdaQueryWrapper.eq(SysDept::getParentId, id);
            List<SysDept> sysDepts = sysDeptService.getBaseMapper().selectList(sysDeptLambdaQueryWrapper);
            for (SysDept sysDept : sysDepts) {
                deptIds.add(sysDept.getId());
                getDeptId(deptIds, sysDept.getId());
            }
        }
    }

    @Override
    public CommonResult updateUserByIdNew(SysUser sysUser) {
        LOCK_POOL.get("modify").lock();
        try {
            String loginCode = sysUser.getLoginCode();
            // 校验登录账户是否存在
            LambdaQueryWrapper<SysUser> checkWapper = new LambdaQueryWrapper<>();
            checkWapper.eq(SysUser::getLoginCode, loginCode);
            SysUser checkUser = baseMapper.selectOne(checkWapper);
            if (null == checkUser) {
                sysUser.setUpdateBy("admin");
                sysUser.setUpdateTime(new Date());
                int i = sysUserMapper.updateById(sysUser);
                CommonResult commonResult = new CommonResult();
                if (i > 0) {
                    commonResult.setCode(HttpStatus.SUCCESS);
                    commonResult.setMsg("修改成功");
                } else {
                    commonResult.setCode(HttpStatus.ERROR);
                    commonResult.setMsg("修改失败");
                }
                return commonResult;
            } else {
                if (checkUser.getUserId().equals(sysUser.getUserId())) {
                    sysUser.setUpdateBy("admin");
                    sysUser.setUpdateTime(new Date());
                    int i = sysUserMapper.updateById(sysUser);
                    CommonResult commonResult = new CommonResult();
                    if (i > 0) {
                        commonResult.setCode(HttpStatus.SUCCESS);
                        commonResult.setMsg("修改成功");
                    } else {
                        commonResult.setCode(HttpStatus.ERROR);
                        commonResult.setMsg("修改失败");
                    }
                    return commonResult;
                } else {
                    CommonResult commonResult = new CommonResult();
                    commonResult.setMsg("登录账号已存在");
                    commonResult.setCode(HttpStatus.ERROR);
                    return commonResult;
                }
            }
        } finally {
            LOCK_POOL.get("modify").unlock();
        }
    }

    /**
     * 添加用户
     * 为了保证系统中用户的唯一,通过锁控制请求
     *
     * @param sysUser 系统用户对象
     * @return
     */
    @Transactional
    @Override
    public CommonResult<SysUser> insertUser(SysUser sysUser) {
        // 获取锁
        LOCK_POOL.get("insert").lock();
        try {
            String loginCode = sysUser.getLoginCode();
            // 校验登录账户是否存在
            LambdaQueryWrapper<SysUser> checkWapper = new LambdaQueryWrapper<>();
            checkWapper.eq(SysUser::getLoginCode, loginCode);
            SysUser checkUser = baseMapper.selectOne(checkWapper);
            if (null != checkUser) {
                CommonResult commonResult = new CommonResult();
                commonResult.setMsg("登录账号已存在");
                commonResult.setCode(HttpStatus.ERROR);
                return commonResult;
            } else {
                // 获取登录用户
                LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
                //处理新增的用户对象  生成UUID
                String uuid = StringUtils.uuid();
                sysUser.setUserId(uuid);
                sysUser.setOrgId("001");
                sysUser.setLoginPassword(new BCryptPasswordEncoder().encode("das000"));
                sysUser.setCreateBy(loginUser.getUser().getUserName());
                sysUser.setCreateTime(new Date());
                int i = sysUserMapper.insert(sysUser);
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(uuid);
                sysUserRole.setRoleId("defaultBindingRole");
                int insert = sysUserRoleMapper.insert(sysUserRole);
                CommonResult<SysUser> commonResult = new CommonResult<>();
                if (i > 0 && insert > 0) {
                    commonResult.setCode(200);
                    commonResult.setMsg("新增用户成功");
                } else {
                    commonResult.setCode(500);
                    commonResult.setMsg("新增用户失败");
                }
                return commonResult;
            }
        } finally {
            // 释放锁
            LOCK_POOL.get("insert").unlock();
        }
    }

    @Override
    public CommonResult<SysUser> updateUserById(SysUser sysUser) {
        sysUser.setUpdateBy("admin");
        sysUser.setUpdateTime(new Date());
        //判断权限与角色
        String[] arrayPermissionId = sysUser.getJsonStr().split(",");
        String[] arrayRoleId = sysUser.getOtherParam().split(",");
        List<String> permissionsIdList = Arrays.asList(arrayPermissionId);
        List<String> roleIdList = Arrays.asList(arrayRoleId);
        List<SysUserPermission> userPermissions = null;
        if (permissionsIdList.size() > 0) {
            userPermissions = new ArrayList<>(16);
            for (String str : permissionsIdList) {
                userPermissions.add(new SysUserPermission(sysUser.getUserId(), str));
            }
        }
        List<SysUserRole> userRoles = null;
        if (roleIdList.size() > 0) {
            userRoles = new ArrayList<>(16);
            for (String str : roleIdList) {
                userRoles.add(new SysUserRole(sysUser.getUserId(), str));
            }
        }
        int i = sysUserMapper.updateById(sysUser);
        int j = 0;
        if (userPermissions != null) {
            j = sysUserPermissionMapper.batchInsert(userPermissions);
        }
        int z = 0;
        if (userRoles != null) {
            z = sysUserRoleMapper.batchInsert(userRoles);
        }
        CommonResult<SysUser> commonResult = new CommonResult<>();
        if (i > 0 && j >= 0 && z >= 0) {
            commonResult.setCode(200);
            commonResult.setMsg("新增用户成功");
        } else {
            commonResult.setCode(500);
            commonResult.setMsg("新增用户失败，请联系管理员");
        }
        return commonResult;

    }

@Autowired
private SysRoleDataMapper sysRoleDataMapper;
    @Override
    public CommonResult<SysUser> deleteUserById(List<String> userIdList) {
        //删除当前数据中的关联信息
        int j = sysUserMapper.deleteBatchIds(userIdList);

        LambdaQueryWrapper<SysUserRole> userRoleWrapper = new LambdaQueryWrapper<>();
        userRoleWrapper.in(SysUserRole::getUserId,userIdList);
        int z = sysUserRoleMapper.delete(userRoleWrapper);

        LambdaQueryWrapper<SysRoleData> roleDataWrapper = new LambdaQueryWrapper<>();
        roleDataWrapper.in(SysRoleData::getRoleId,userIdList);
        sysRoleDataMapper.delete(roleDataWrapper);

        CommonResult<SysUser> commonResult = new CommonResult<>();
        if (j >= 0) {
            commonResult.setCode(200);
            commonResult.setMsg("删除用户成功");
        } else {
            commonResult.setCode(500);
            commonResult.setMsg("删除用户失败，请联系管理员");
        }
        return commonResult;
    }

    @Override
    public List<SysUser> queryUserByPermissionId(String permissionId) {

        List<SysUser> list = sysUserMapper.queryUserByPermissionId(permissionId);

        return list;
    }

    @Override
    public List<SysUser> queryUserByRoleId(SysUserRole sysUserRole) {
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        sysUserRoleQueryWrapper.lambda().eq(SysUserRole::getRoleId, sysUserRole.getRoleId());
        List<SysUserRole> list = sysUserRoleMapper.selectList(sysUserRoleQueryWrapper);
        if (list == null || list.size() <= 0) {
            return new ArrayList<>();
        }
        List<String> userIdList = list.stream().map(e -> e.getUserId()).collect(Collectors.toList());
        //依据目前的角色id查询到的关联 查询权限信息
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.lambda().in(SysUser::getUserId, userIdList);
        List<SysUser> sysUsers = sysUserMapper.selectList(sysUserQueryWrapper);
        return sysUsers;
    }

    @Override
    public List<SysUser> queryUserListByPermissionId(String permissionId, SysUser sysUser) {
        QueryWrapper<SysUserPermission> sysUserPermissionQueryWrapper = new QueryWrapper<>();
        sysUserPermissionQueryWrapper.lambda().eq(SysUserPermission::getPermissionId, permissionId);
        List<SysUserPermission> list = sysUserPermissionMapper.selectList(sysUserPermissionQueryWrapper);
        if (list == null || list.size() <= 0) {
            return new ArrayList<>();
        }

        List<String> userIdList = list.stream().map(e -> e.getUserId()).collect(Collectors.toList());
        //依据目前的角色id查询到的关联 查询权限信息
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.lambda().in(SysUser::getUserId, userIdList);
        if (sysUser.getUserName() != null && !"".equals(sysUser.getUserName())) {
            sysUserQueryWrapper.lambda().like(SysUser::getUserName, sysUser.getUserName());
        }
        List<SysUser> sysUsers = sysUserMapper.selectList(sysUserQueryWrapper);
        return sysUsers;
    }

    @Override
    public List<SysUser> queryRemainingUserListByPermissionId(String permissionId) {
        //查询所有的用户数据
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        List<SysUser> sysUsers = sysUserMapper.selectList(sysUserQueryWrapper);
        //查询当前绑定的用户数据
        QueryWrapper<SysUserPermission> sysUserPermissionQueryWrapper = new QueryWrapper<>();
        sysUserPermissionQueryWrapper.lambda().eq(SysUserPermission::getPermissionId, permissionId);
        List<SysUserPermission> list = sysUserPermissionMapper.selectList(sysUserPermissionQueryWrapper);
        List<String> userIdList = list.stream().map(e -> e.getUserId()).collect(Collectors.toList());
        //过滤当前的用户信息
        List<SysUser> reduceSysusers = sysUsers.stream().filter(e -> !userIdList.contains(e.getUserId())).collect(Collectors.toList());
        return reduceSysusers;
    }

    @Override
    public List<SysUser> queryRemainingUserListByRoleId(String roleId) {
        //查询所有的用户数据
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        List<SysUser> sysUsers = sysUserMapper.selectList(sysUserQueryWrapper);
        //查询当前绑定的用户数据
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        sysUserRoleQueryWrapper.lambda().eq(SysUserRole::getRoleId, roleId);
        List<SysUserRole> list = sysUserRoleMapper.selectList(sysUserRoleQueryWrapper);
        List<String> userIdList = list.stream().map(e -> e.getUserId()).collect(Collectors.toList());
        //过滤当前的用户信息
        List<SysUser> reduceSysusers = sysUsers.stream().filter(e -> !userIdList.contains(e.getUserId())).collect(Collectors.toList());
        return reduceSysusers;
    }

    @Override
    public List<SysUser> queryUserListByParamters(Map<String, String> map) {

        List<SysUser> list = sysUserMapper.queryUserListByParamters(map);

        return list;
    }

    @Override
    public CommonResult queryDeptUserTree() {

        //查询所有的部门
        JSONArray deptList = sysDeptService.getDeptList();
        //查询所有的用户数据
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        List<SysUser> sysUsers = sysUserMapper.selectList(sysUserQueryWrapper);

        //将用户转换为集合
        Map<String, List<SysUser>> map = new HashMap<>();
        for (SysUser sysUser : sysUsers) {
            List<SysUser> list = map.get(sysUser.getDeptId());
            if (list == null) {
                list = new ArrayList<>();
                list.add(sysUser);
                map.put(sysUser.getDeptId(), list);
            } else {
                list.add(sysUser);
            }
        }

        //遍历所有部门将用户放入
        addUser2Dept(deptList, map);
        CommonResult commonResult = new CommonResult<>();
        commonResult.setData(deptList);
        commonResult.setCode(200);
        return commonResult;
    }


    @Override
    public CommonGridResult queryAddUserList(SysUser sysUser) {
        List<SysUser> user = this.queryUserListByPermissionId(sysUser.getJsonStr(), sysUser);
        List<String> ids = new ArrayList<>();
        for (SysUser s : user) {
            ids.add(s.getUserId());
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        if (ids.size() > 0) {
            queryWrapper.notIn("user_id", ids.toArray());
        }

        PageHelper.startPage(sysUser.getPageNum(), sysUser.getPageSize());
        List<SysUser> list = sysUserMapper.selectList(queryWrapper);
        Page page = (Page) list;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(sysUser.getPageNum(), sysUser.getPageSize(), total, list);

        return commonGridResult;
    }

    @Override
    @Transactional
    public void addUserAndPermission(String userIds, String permissionId) {

        String[] ids = null;

        if (userIds != null) {
            ids = userIds.split(",");
        }
        List<SysUserPermission> list = new ArrayList<>();
        if (ids != null) {
            for (String id : ids) {
                SysUserPermission sysUserPermission = new SysUserPermission();
                sysUserPermission.setUserId(id);
                sysUserPermission.setPermissionId(permissionId);
                list.add(sysUserPermission);
            }
            sysUserPermissionMapper.batchInsert(list);
        }

    }

    @Override
    public CommonResult resetPassword(SysUser sysUser) {
        String[] ids = sysUser.getOtherParam().split(",");
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SysUser::getUserId, ids);
        List<SysUser> list = this.list(lambdaQueryWrapper);
        for (SysUser sysUser1 : list) {//需修改  加密
            // String encode = new BCryptPasswordEncoder().encode("das000");
            String resetPassWord = new BCryptPasswordEncoder().encode("das000");
            sysUser1.setLoginPassword(resetPassWord);
        }
        int i = this.updateBatch(list);
        if (i > 0) {
            return CommonResult.success("重置成功");
        } else {
            return CommonResult.error(500, "重置失败");
        }

    }

    @Override
    public CommonResult prohibitUser(SysUser sysUser) {
        String[] ids = sysUser.getOtherParam().split(",");
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SysUser::getUserId, ids);
        List<SysUser> list = this.list(lambdaQueryWrapper);
        for (SysUser sysUser1 : list) {
            sysUser1.setUserStatus(1);
        }
        int i = this.updateBatch(list);
        if (i > 0) {
            return CommonResult.success("禁用成功");
        } else {
            return CommonResult.error(500, "禁用失败");
        }
    }



    @Override
    public boolean updateUserPwd(String userId, String password) {

        LambdaUpdateWrapper<SysUser> lambdaUpdateWrapper = new LambdaUpdateWrapper();

        lambdaUpdateWrapper.set(SysUser::getLoginPassword, SecurityUtils.encryptPassword(password));
        lambdaUpdateWrapper.eq(SysUser::getUserId, userId);
        return this.update(lambdaUpdateWrapper);
    }

    private void addUser2Dept(JSONArray deptList, Map<String, List<SysUser>> map) {
        for (int i = 0; i < deptList.size(); i++) {
            JSONObject jsonObject = deptList.getJSONObject(i);
            List<SysUser> list = map.get(jsonObject.getString("id"));
            if (list != null && list.size() > 0) {
                JSONArray children = jsonObject.getJSONArray("children");
                if (children != null) {
                    addUser2Dept(children, map);
                    for (SysUser sysUser : list) {
                        JSONObject j = JSON.parseObject(JSON.toJSONString(sysUser));
                        j.put("deptName", sysUser.getUserName());
                        children.add(j);
                    }
                } else {
                    children = new JSONArray();
                    for (SysUser sysUser : list) {
                        JSONObject j = JSON.parseObject(JSON.toJSONString(sysUser));
                        j.put("deptName", sysUser.getUserName());
                        children.add(j);
                    }
                    jsonObject.put("children", children);
                }
            } else {
                JSONArray children = jsonObject.getJSONArray("children");
                if (children != null) {
                    addUser2Dept(children, map);
                }
            }
        }
    }

    @Override
    public int updateBatchSelective(List<SysUser> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public List<SysUser> querySysUserByClassId(String classId) {
        return baseMapper.querySysUserByClassId(classId);
    }
}







