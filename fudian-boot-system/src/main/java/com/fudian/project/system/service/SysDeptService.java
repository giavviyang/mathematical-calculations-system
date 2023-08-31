package com.fudian.project.system.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.enums.DeptEnum;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {


    int updateBatch(List<SysDept> list);

    int batchInsert(List<SysDept> list);

    int insertOrUpdate(SysDept record);

    int insertOrUpdateSelective(SysDept record);

    JSONArray getDeptList();

    DeptEnum.CheckDept check(String id);

    DeptEnum.CheckDept deleteDept(String id);

    boolean updateChildrenStatus(String id,String status);

    boolean updateDept(SysDept sysDept);

    boolean updateParent(String id,String parentId);

    boolean saveOrder(String json);

    /**
     * 插入部门信息
     * @param sysDept 部门对象
     * @return  CommonResult
     */
    CommonResult insertSysDept(SysDept sysDept);

    /**
     * Like 查询当前 部门信息
     * @param sysDept 部门对象
     * @return CommonResult
     */
    CommonResult querySysDeptByLike(SysDept sysDept);

    /**
     * 更新数据
     * @param sysDept
     * @return CommonResult
     */
    CommonResult updateDeptById(SysDept sysDept);

    JSONArray getDeptTreeGrid(List<SysDept> list,String parentId);

    /**
     * 根据父级id获取部门列表
     * @param sysDept
     * @return
     */
    CommonGridResult getListDeptByPId(SysDept sysDept);


    /**
     * 获取部门列表
     * @param sysDept
     * @return
     */
    JSONArray getDeptTreeList(SysDept sysDept);
    /**
     * 获取部门列表
     * @param userId
     * @return
     */
    JSONObject getUserDeptTreeList(String userId);

    /**
     * 保存排序
     * @param sysDept
     * @return
     */
    CommonResult orderSave(SysDept sysDept);


    /*
     * 根据当前登录用户获取学校信息
     * */
    CommonResult getDeptListByUser();

    /**
     * 根据多个id删除部门
     * @param sysDept
     * @return
     */
    CommonResult delSysDeptByIds(SysDept sysDept);
    /**
     * 查询学生班级拼接年级
     * @param deptId
     * @return
     */
    String selectRoomClass(String deptId);
    /**
     * 查询当前班级同年级的班级
     * @param deptId
     * @return
     */
    List<String> queryGradeClassId(String deptId);
    /**
     * 查询当前学校有多少个年级
     * @param deptId
     * @return
     */
    List<SysDept> queryGradeClass(String deptId);

    /**
     * 查询班级所在学校
     * @param deptId
     * @return
     */
    SysDept selectRoomClassSchool(String deptId);
    /**
     * 查询班级所在年级
     * @param deptId
     * @return
     */
    SysDept selectRoomClassGrade(String deptId);
}
