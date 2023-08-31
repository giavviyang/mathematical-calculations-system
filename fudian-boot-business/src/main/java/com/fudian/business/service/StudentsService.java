package com.fudian.business.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.Students;
import com.fudian.business.vo.StudentInfo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 学生业务
 *
 * @author xuchenghong
 */

public interface StudentsService extends IService<Students> {

    /**
     * 根据学号查询学生
     *
     * @param studentNumber
     * @return
     */
    Students selectStudentsByStudentNumber(String studentNumber);

    /**
     * 根据id查询学生
     *
     * @param id
     * @return
     */
    Students selectStudentsByStudentId(String id);

    /**
     * 根据学号修改
     *
     * @param student
     * @return int 修改数量
     */
    int updateStudentByStudentNumber(Students student);

    /**
     * \
     * 分页查询学生
     *
     * @return
     */
    CommonGridResult pagingSelectStudents(Students students);

    /**
     * 查询学生
     *
     * @param students
     * @return
     */
    List<Students> selectStudents(Students students);

    /**
     * 根据id修改
     *
     * @param student
     * @return
     */
    int updateStudentByStudentId(Students student);

    /**
     * 根据id删除学生
     *
     * @param studentId
     * @return
     */
    int deleteStudentByStudentId(String studentId);

    /**
     * 根据ids删除学生
     *
     * @param ids
     * @return
     */
    int deleteStudentByStudentId(List<String> ids);

    CommonGridResult queryStudentAndClass(Map<String, Object> map);

    /**
     * 新增学生
     *
     * @return
     */
    CommonResult insertStudent(Students students);

    List<Students> queryGradeStudent(String classRoomId);


    /**
     * 根据学生相关信息，查询学生
     *
     * @param student
     * @return java.util.List<com.fudian.api.system.pojo.Students>
     * @author hong 2022/1/2
     */
    CommonGridResult queryStudentInfoByStudent(Students student);

    CommonResult importStudentsByExcel(MultipartFile part);

    CommonResult changeClass(String ids, String classId);

    /**
     * 查询学生个人信息
     *
     * @param studentId
     * @return
     */
    StudentInfo queryStudentInfoByStudentId(String studentId);


    /**
     * @param studentIds
     * @param classId
     * @return
     */
    CommonResult<?> studentGraduation(List<String> studentIds, String classId);

    /**
     * @param students
     * @return
     */
    CommonGridResult pagingQueryStudentGraduation(Students students);

    /**
     * 查询学生（学生头像和头像框为替换后的）根据学生id集合
     *
     * @param studentIds 学生id集合
     * @return 学生信息
     */
    List<Students> batchSelectStudentsById(List<String> studentIds);

}
