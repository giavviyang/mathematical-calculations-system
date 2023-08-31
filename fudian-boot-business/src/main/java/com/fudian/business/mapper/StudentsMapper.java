package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.Students;
import com.fudian.business.vo.StudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author xuchenghong
 */

@Mapper
public interface StudentsMapper extends BaseMapper<Students> {
    /**
     * 查询学校或年级或班级下的学生
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> queryStudentAndClass(@Param("map") Map<String, Object> map);


    /**
     * 查询当前学生同年级学生
     *
     * @param classRoomId
     * @return
     */
    List<Students> selectGradeStudent(@Param("classRoomId") String classRoomId);

    /**
     * 查询学生个人信息
     *
     * @param studentId
     * @return
     */
    StudentInfo queryStudentInfoByStudentId(@Param("studentId") String studentId);

    List<Map<String, Object>> queryStudentInfoByStudent(Students students);
    /**
     * 查询学生（学生头像和头像框为替换后的）根据学生id集合
     *
     * @param studentIds 学生id集合
     * @return 学生信息
     */
    List<Students> batchSelectStudentsById(@Param("studentIds") List<String> studentIds);

}
