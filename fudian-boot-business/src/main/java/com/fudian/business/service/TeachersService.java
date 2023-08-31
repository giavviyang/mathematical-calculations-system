package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.Teacher;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;


/**
 * @author xuchenghong
 */

public interface TeachersService extends IService<Teacher> {

    /**
     * 分页查询教师信息
     * @param teacher
     * @return
     */
    CommonGridResult pagingSelectTeacher(Teacher teacher);

    /**
     * 查询教师信息
     * @param teacher
     * @return
     */
    List<Teacher> selectTeacher(Teacher teacher);

    /**
     * 添加教师信息
     * @param teacher
     * @return
     */
    int insertTeacher(Teacher teacher);

    /**
     * 修改教师信息
     * @param teacher
     * @return
     */
    int updateTeacherById(Teacher teacher);

    /**
     * 批量删除教师信息
     * @param ids
     * @return
     */
    int deleteTeacherById(List<String> ids);
    /**
     * 删除教师信息
     * @param id
     * @return
     */
    int deleteTeacherById(String id);
}
