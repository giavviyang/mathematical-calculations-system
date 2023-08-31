package com.fudian.api.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.SysDept;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 15:39
 * @Desc:
 */
public interface SysDeptApi extends IService<SysDept> {
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

    SysDept getIdByDeptName(Map<String,Object> map);
    /**
     * 查询班级所在学校
     * @param deptId
     * @return
     */
    SysDept selectRoomClassSchool(String deptId);
}
