package com.fudian.project.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.enums.DeptEnum;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.SysDeptApi;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.pojo.StudentAnswer;
import com.fudian.business.service.StudentAnswerService;
import com.fudian.business.service.StudentsService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.PinYin;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.system.mapper.SysDeptMapper;
import com.fudian.project.system.pojo.SysRoleData;
import com.fudian.project.system.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService, SysDeptApi {


    @Autowired
    SysUserService sysUserService;

    @Autowired
    TokenApi tokenService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysDataPermissionService sysDataPermissionService;

    @Autowired
    private StudentsService studentsService;


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
    public int updateBatch(List<SysDept> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<SysDept> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(SysDept record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(SysDept record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    /**
     * 获取所有部门列表
     *
     * @return
     */
    @Override
    public JSONArray getDeptList() {

        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(SysDept::getOrderNum);
        List<SysDept> list = this.list(lambdaQueryWrapper);
        JSONArray jsonArray = getDeptTreeGrid(list, "000");
        return jsonArray;
    }

    /**
     * @param sysDept
     * @return
     */
    @Override
    public JSONArray getDeptTreeList(SysDept sysDept) {
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(SysDept::getOrderNum);
        lambdaQueryWrapper.eq(SysDept::getDeptType, sysDept.getDeptType());
        lambdaQueryWrapper.eq(SysDept::getStatus, 0);
        List<SysDept> list = new ArrayList<>();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        //数据权限处理
        if (sysDept.getDeptType() != null && "1".equals(sysDept.getDeptType().toString()) && !loginUser.getUser().getUserId().equals("admin")) {
            if (loginUser != null && loginUser.getUser() != null && loginUser.getUser().getUserId() != null && (sysDept.getOtherParam() == null || !"isSystem".equals(sysDept.getOtherParam()))) {
                Map<String, List<String>> map = sysDataPermissionService.getDataPermission(loginUser.getUser().getUserId());
                List<String> deptIds = map.get("deptIds");
                if (deptIds != null && deptIds.size() > 0) {
                    if (!deptIds.contains("root")) {
                        lambdaQueryWrapper.and(query -> query.in(SysDept::getId, deptIds));
                    }
                    list = this.list(lambdaQueryWrapper);
                }
            } else {
                String schoolId = baseMapper.selectSchoolId(loginUser.getUser().getDeptId());
                lambdaQueryWrapper.like(SysDept::getAncestors, schoolId);
                list = this.list(lambdaQueryWrapper);
            }
        } else {
            list = this.list(lambdaQueryWrapper);
        }
        JSONArray jsonArray = getDeptTreeGrid(list, "000");
        return jsonArray;
    }

    @Autowired
    private SysRoleDataService sysroleDataService;
    @Autowired
    private SysDeptService sysDeptService;
    /**
     * @param userId
     * @return
     */
    @Override
    public JSONObject getUserDeptTreeList(String  userId) {
        SysUser user = sysUserService.getById(userId);
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(SysDept::getOrderNum);
        lambdaQueryWrapper.eq(SysDept::getDeptType,1);
        lambdaQueryWrapper.eq(SysDept::getStatus,0);
        lambdaQueryWrapper.like(SysDept::getAncestors,user.getDeptId());
        List<SysDept> list = this.list(lambdaQueryWrapper);
        //数据权限处理
        List<SysRoleData> sysRoleData = sysroleDataService.getUserRoleData(userId);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = getUserDeptTreeGrid(list, "000", sysRoleData);
        jsonObject.put("list",jsonArray);

        List<String> listIds = sysRoleData.stream().map(SysRoleData::getDataPermissionId).collect(Collectors.toList());

        if (listIds != null && listIds.size() > 0) {
            LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(SysDept::getId,listIds);
            queryWrapper.in(SysDept::getNodeType,2);
            List<SysDept> sysDepts = sysDeptService.list(queryWrapper);

            List<String> listId = sysDepts.stream().map(SysDept::getId).collect(Collectors.toList());

            jsonObject.put("listIds",listId);
        }
        return jsonObject;
    }

    @Override
    public CommonResult orderSave(SysDept sysDept) {
        String jsonStr = sysDept.getJsonStr();
        String[] ids = jsonStr.split(",");
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SysDept::getId, ids);
        List<SysDept> list = this.list(lambdaQueryWrapper);
        List<SysDept> updateList = new ArrayList<>();
        sysDept.getPageNum();
        sysDept.getPageSize();

        int num = 1 + sysDept.getPageSize() * (sysDept.getPageNum() - 1);
        for (String id : ids) {
            for (SysDept sysDept1 : list) {
                if (sysDept1.getId().equals(id)) {
                    sysDept1.setOrderNum(num++);
                    updateList.add(sysDept1);
                }
            }
        }
        int i = this.updateBatch(updateList);
        if (i > 0) {
            return CommonResult.success("排序成功");
        } else {
            return CommonResult.error(500, "排序失败");
        }
    }

    /**
     * 检查部门是否可删除
     *
     * @param id
     * @return
     */
    @Override
    public DeptEnum.CheckDept check(String id) {
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDept::getParentId, id);
        List<SysDept> list = this.list(lambdaQueryWrapper);
        //存在下级部门
        if (list != null && list.size() > 0) {
            return DeptEnum.CheckDept.EXIST_DEPT;
        }

        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getDeptId, id);
        List<SysUser> users = sysUserService.list(sysUserLambdaQueryWrapper);
        //存在用户
        if (users != null && users.size() > 0) {
            return DeptEnum.CheckDept.EXIST_USER;
        }

        return DeptEnum.CheckDept.SUCCESS;
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @Override
    public DeptEnum.CheckDept deleteDept(String id) {
        DeptEnum.CheckDept checkDept = check(id);
        //检查通过
        if (checkDept.getCode() == 200) {
            //执行删除
            if (this.removeById(id)) {
                return DeptEnum.CheckDept.SUCCESS;
            } else {
                return DeptEnum.CheckDept.ERROR;
            }
        }
        return checkDept;
    }

    /**
     * 更改子级状态
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateChildrenStatus(String id, String status) {
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDept::getParentId, id);
        //查询当前部门的子级部门
        List<SysDept> list = this.list(lambdaQueryWrapper);
        if (list == null || list.size() == 0) {
            return true;
        } else {
            //循环修改
            for (SysDept sysDept : list) {
                sysDept.setStatus(status);
                this.updateById(sysDept);
                //执行递归 修改子级下子级
                boolean b = updateChildrenStatus(sysDept.getId(), status);
                return b;
            }
        }
        return true;
    }

    /**
     * 更新部门
     *
     * @param sysDept
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDept(SysDept sysDept) {
        if (this.updateById(sysDept)) {
            boolean b = updateChildrenStatus(sysDept.getParentId(), sysDept.getStatus());
            return b;
        }
        return false;
    }

    /**
     * 修改部门的上级部门
     *
     * @param id
     * @param parentId
     * @return
     */
    @Override
    public boolean updateParent(String id, String parentId) {
        //查询出父级部门的子级的最大序号
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(SysDept::getParentId, parentId);
        lambdaQueryWrapper.orderByDesc(SysDept::getOrderNum);
        List<SysDept> list = this.list(lambdaQueryWrapper);
        //得出序号
        int index = list == null || list.size() == 0 ? 1 : list.get(0).getOrderNum() + 1;
        SysDept sysDept = this.getById(id);
        if (sysDept != null) {
            sysDept.setOrderNum(index);
            sysDept.setParentId(parentId);
            return this.updateById(sysDept);
        }
        return false;
    }

    /**
     * 调整序号
     *
     * @param json 部门数据 序号+id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrder(String json) {
        JSONArray jsonArray = JSONArray.parseArray(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            LambdaUpdateWrapper<SysDept> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.set(SysDept::getOrderNum, jsonArray.getJSONObject(i).getInteger("orderNum"));
            lambdaUpdateWrapper.eq(SysDept::getId, jsonArray.getJSONObject(i).getString("id"));
            this.update(lambdaUpdateWrapper);

        }

        return true;
    }

    @Override
    public CommonResult insertSysDept(SysDept sysDept) {
        // 获取锁
        LOCK_POOL.get("insert").lock();
        try {
            LambdaQueryWrapper<SysDept> sysDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysDeptLambdaQueryWrapper.eq(SysDept::getDeptName, sysDept.getDeptName());
            sysDeptLambdaQueryWrapper.eq(SysDept::getParentId, sysDept.getParentId());
            SysDept sysDept1 = baseMapper.selectOne(sysDeptLambdaQueryWrapper);
            if (null == sysDept1) {
                LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
                sysDept.setId(StringUtils.uuid());
                sysDept.setAncestors(sysDept.getAncestors() + sysDept.getId() + ",");
                //设置创建时间
                sysDept.setCreateTime(new Date());
                sysDept.setCreateBy(loginUser.getUser().getUserName());
                //获取当前序列得最大值
                if (sysDept.getParentId() != null && !sysDept.getParentId().equals("")) {
                    QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
                    List<SysDept> sysDeptList = baseMapper.selectList(queryWrapper.lambda().eq(SysDept::getParentId, sysDept.getParentId()));
                    if (sysDeptList != null && sysDeptList.size() > 0) {
                        Optional<SysDept> max = sysDeptList.stream().max(Comparator.comparingInt(SysDept::getOrderNum));
                        sysDept.setOrderNum(max.get().getOrderNum() + 1);
                    } else {
                        sysDept.setOrderNum(1);
                    }
                } else {
                    sysDept.setParentId("000");
                    sysDept.setOrderNum(1);
                }
                //生成拼音
                sysDept.setDeptPym(PinYin.getFirstSpell(sysDept.getDeptName()));
                //存储当前得数据
                sysDept.setStatus("0");
                int i = baseMapper.insert(sysDept);
                CommonResult commonResult = null;
                if (i > 0) {
                    commonResult = new CommonResult(200, "新增成功", null);
                } else {
                    commonResult = new CommonResult(500, "新增部门失败", null);
                }
                return commonResult;
            } else {
                return new CommonResult(500, "部门名称已存在", null);
            }
        } finally {
            LOCK_POOL.get("insert").unlock();
        }
    }

    @Override
    public CommonResult querySysDeptByLike(SysDept sysDept) {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(SysDept::getDeptName, sysDept.getDeptName());
        List<SysDept> sysDeptList = baseMapper.selectList(queryWrapper);
        CommonResult commonResult = new CommonResult(200, "新增成功", sysDeptList);
        return commonResult;
    }

    @Override
    public CommonResult updateDeptById(SysDept sysDept) {
        // 获取锁
        LOCK_POOL.get("modify").lock();
        try {
            // 查询是否存在
            LambdaQueryWrapper<SysDept> sysDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysDeptLambdaQueryWrapper.eq(SysDept::getDeptName, sysDept.getDeptName());
            sysDeptLambdaQueryWrapper.eq(SysDept::getParentId, sysDept.getParentId());
            SysDept sysDept1 = baseMapper.selectOne(sysDeptLambdaQueryWrapper);
            if (sysDept1 == null || sysDept.getId().equals(sysDept1.getId())) {
                // 不存在就修改
                LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
                sysDept.setUpdateTime(new Date());
                sysDept.setUpdateBy(loginUser.getUser().getUserName());
                sysDept.setDeptPym(PinYin.getFirstSpell(sysDept.getDeptName()));
                int i = baseMapper.updateById(sysDept);
                boolean b = updateChildrenStatus(sysDept.getId(), sysDept.getStatus());
                CommonResult commonResult = null;
                if (i > 0 && b) {
                    commonResult = new CommonResult(200, "修改信息成功", null);
                } else {
                    commonResult = new CommonResult(500, "修改部门信息失败", null);
                }
                return commonResult;
            } else {
                return CommonResult.error(500, "单位名称已存在");
            }
        } finally {
            LOCK_POOL.get("modify").unlock();
        }

    }


    /**
     * 递归生成部门树
     *
     * @param list
     * @param parentId
     * @return
     */
    @Override
    public JSONArray getDeptTreeGrid(List<SysDept> list, String parentId) {
        JSONArray jsonArray = new JSONArray();
        list.stream().forEach(x -> {
            if (x.getParentId().equals(parentId)) {
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(x);
                JSONArray children = getDeptTreeGrid(list, x.getId());
                if (children.size() > 0) {
                    jsonObject.put("children", children);
//                    jsonObject.put("expan",true);
                }
                jsonArray.add(jsonObject);
            }
        });
        return jsonArray;
    }

    /**
     * 递归生成部门树2
     *
     * @param list
     * @param parentId
     * @param sysRoleData
     * @return
     */
    public JSONArray getUserDeptTreeGrid(List<SysDept> list, String parentId, List<SysRoleData> sysRoleData) {
        JSONArray jsonArray = new JSONArray();
        list.stream().forEach(x -> {
            if (x.getParentId().equals(parentId)) {
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(x);
                jsonObject.put("isTeacher", 0);
                for (SysRoleData sysRoleDatum : sysRoleData) {
                    if (sysRoleDatum.getDataPermissionId().equals(x.getId())) {
                        jsonObject.put("isTeacher", sysRoleDatum.getIsTeacher());
                    }
                }
                //jsonObject.put("isTeacher", 1);
                JSONArray children = getUserDeptTreeGrid(list, x.getId(),sysRoleData);
                if (children.size() > 0) {
                    jsonObject.put("children", children);
                }
                jsonArray.add(jsonObject);
            }
        });
        return jsonArray;
    }

    @Override
    public CommonGridResult getListDeptByPId(SysDept sysDept) {
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (sysDept.getParentId().equals("000")) {
            LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
            SysUser user = loginUser.getUser();
            if (!user.getLoginCode().equals("admin")) {
                lambdaQueryWrapper.eq(SysDept::getId, user.getDeptId());
            }
        }
        lambdaQueryWrapper.eq(SysDept::getParentId, sysDept.getParentId());
        lambdaQueryWrapper.eq(SysDept::getDeptType, sysDept.getDeptType());
        lambdaQueryWrapper.eq(SysDept::getStatus, 0);
        lambdaQueryWrapper.orderByAsc(SysDept::getOrderNum);
        PageHelper.startPage(sysDept.getPageNum(), sysDept.getPageSize());
        List<SysDept> returnList = this.list(lambdaQueryWrapper);
        Page<SysDept> page = (Page) returnList;
        CommonGridResult commonGridResult = new CommonGridResult();
        commonGridResult.setPageSize(page.getPageSize());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setTotal(page.getTotal());
        commonGridResult.setRows(page.getResult());
        return commonGridResult;
    }

    @Override
    public CommonResult getDeptListByUser() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        LambdaQueryWrapper<SysDept> sysDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDeptLambdaQueryWrapper.eq(SysDept::getParentId, "000");
        if (!loginUser.getUser().getLoginCode().equals("admin")) {
            sysDeptLambdaQueryWrapper.eq(SysDept::getId, loginUser.getUser().getDeptId());
        }
        return CommonResult.success(this.list(sysDeptLambdaQueryWrapper));
    }

    @Transactional
    @Override
    public CommonResult delSysDeptByIds(SysDept sysDept) {
        // 获取 变量值 判断是学生节点还是用户节点
        Boolean isDept = Boolean.valueOf(sysDept.getJsonStr());
        // 获取ID集合
        String[] ids = sysDept.getOtherParam().split(",");
        // 条件申明
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = null;
        LambdaQueryWrapper<SysDept> sysDeptLambdaQueryWrapper = null;
        LambdaQueryWrapper<Students> studentAnswerLambdaQueryWrapper = null;
        // 消息申明
        String message = "";
        boolean flag = true;
        if (isDept) {
            // 如果是用户机构 查询机构下是否存在人员
            for (String id : ids) {
                sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
                sysUserLambdaQueryWrapper.eq(SysUser::getDeptId, id);
                List<SysUser> sysUsers = sysUserService.getBaseMapper().selectList(sysUserLambdaQueryWrapper);
                if (null != sysUsers && 0 < sysUsers.size()) {
                    flag = false;
                    sysDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    sysDeptLambdaQueryWrapper.eq(SysDept::getId, id);
                    SysDept sysDept1 = baseMapper.selectOne(sysDeptLambdaQueryWrapper);
                    message += sysDept1.getDeptName() + "、";
                }
            }
        } else {
            // 判断节点下是否存在学生
            for (String id : ids) {
                studentAnswerLambdaQueryWrapper = new LambdaQueryWrapper<>();
                studentAnswerLambdaQueryWrapper.eq(Students::getClassRoomId, id);
                List<Students> students = studentsService.list(studentAnswerLambdaQueryWrapper);
                if (null != students && 0 < students.size()) {
                    flag = false;
                    sysDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    sysDeptLambdaQueryWrapper.eq(SysDept::getId, id);
                    SysDept sysDept1 = baseMapper.selectOne(sysDeptLambdaQueryWrapper);
                    message += sysDept1.getDeptName() + "、";
                }
            }
        }
        // 如果不存在 删除机构
        if (flag) {
            LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(SysDept::getId, ids);
            boolean b = this.remove(lambdaQueryWrapper);
            if (b) {
                return CommonResult.success("删除成功");
            } else {
                return CommonResult.error(500, "删除失败");
            }
        } else {
            // 如果存在 根据机构返回不同的消息
            if (isDept) {
                return CommonResult.error(500, "删除失败," + message + "节点下存在用户");
            } else {
                return CommonResult.error(500, "删除失败," + message + "节点下存在学生");
            }
        }

    }


    /**
     * 查询学生班级拼接年级
     *
     * @param deptId
     * @return
     */
    @Override
    public String selectRoomClass(String deptId) {
        String name = baseMapper.selectRoomClass(deptId);
        return name;
    }

    /**
     * 查询当前班级同年级的班级
     *
     * @param deptId
     * @return
     */
    @Override
    public List<String> queryGradeClassId(String deptId) {
        List<String> list = baseMapper.selectGradeClassId(deptId);
        return list;
    }

    @Override
    public SysDept getIdByDeptName(Map<String, Object> map) {
        return baseMapper.getIdByDeptName(map);
    }

    /**
     * 查询当前学校有多少个年级
     *
     * @param deptId
     * @return
     */
    @Override
    public List<SysDept> queryGradeClass(String deptId) {
        List<SysDept> list = baseMapper.selectGradeClass(deptId);
        return list;
    }

    /**
     * 查询班级所在学校
     *
     * @param deptId
     * @return
     */
    @Override
    public SysDept selectRoomClassSchool(String deptId) {
        SysDept sysDept = baseMapper.selectRoomClassSchool(deptId);
        return sysDept;
    }

    /**
     * 查询班级所在年级
     *
     * @param deptId
     * @return
     */
    @Override
    public SysDept selectRoomClassGrade(String deptId) {

        return baseMapper.selectRoomClassGrade(deptId);
    }

}
