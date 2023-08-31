package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.StudentLevelTable;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;


/**
 * @author xuchenghong
 */

public interface StudentLevelTableService extends IService<StudentLevelTable> {
    /**
     * 分页查询学生关卡习题
     *
     * @param studentLevelTable
     * @return
     */
    CommonGridResult pagingSelectStudentLevelTable(StudentLevelTable studentLevelTable);

    /**
     * 查询学生关卡习题
     *
     * @param studentLevelTable
     * @return
     */
    List<StudentLevelTable> selectStudentLevelTable(StudentLevelTable studentLevelTable);

    /**
     * 添加学生关卡习题
     *
     * @param studentLevelTable
     * @return
     */
    int insertStudentLevelTable(StudentLevelTable studentLevelTable);

    /**
     * 修改学生关卡习题
     *
     * @param studentLevelTable
     * @return
     */
    int updateStudentLevelTableById(StudentLevelTable studentLevelTable);

    /**
     * 批量删除学生关卡习题
     *
     * @param ids
     * @return
     */
    int deleteStudentLevelTable(List<String> ids);
}
