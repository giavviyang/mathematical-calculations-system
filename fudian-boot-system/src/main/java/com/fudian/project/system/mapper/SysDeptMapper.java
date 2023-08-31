package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.SysDept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SysDeptMapper extends BaseMapper<SysDept> {
    int updateBatch(List<SysDept> list);

    int batchInsert(@Param("list") List<SysDept> list);

    int insertOrUpdate(SysDept record);

    int insertOrUpdateSelective(SysDept record);

    /**
     * 根据班级id查询年级+班级名称
     * @param deptId
     * @return
     */
    @Select("select concat(p.dept_name,t.dept_name) from sys_dept t left join sys_dept p on t.parent_id = p.id where t.id = #{deptId}")
    String selectRoomClass(String deptId);

    /**
     * 查询当前班级同年级的班级
     * @param deptId
     * @return
     */
    @Select("select sdo.id from sys_dept sdo inner join sys_dept sdt on sdo.parent_id = sdt.parent_id where sdt.id = #{deptId}")
    List<String> selectGradeClassId(String deptId);
    /**
     * 查询当前学校有多少年级
     * @param deptId
     * @return
     */
    @Select("select * from sys_dept sd1 left join sys_dept sd2 on sd1.parent_id = sd2.parent_id left join sys_dept sd3 on sd2.id = sd3.parent_id where sd1.status = 0 and sd3.id = #{deptId} order by sd1.order_num asc")
    List<SysDept> selectGradeClass(String deptId);

    SysDept getIdByDeptName(@Param("map") Map<String,Object> map);

    /**
     * 查询班级所在学校
     * @param deptId
     * @return
     */
    @Select("select * from sys_dept where id = (select parent_id from sys_dept where id = (select parent_id from sys_dept where id = #{deptId}))")
    SysDept selectRoomClassSchool(String deptId);
    /**
     * 查询班级所在年级
     * @param deptId
     * @return
     */
    @Select("select * from sys_dept where id = (select parent_id from sys_dept where id = #{deptId})")
    SysDept selectRoomClassGrade(String deptId);

    /**
     * 查询同学校下的所有班级
     * @param classId
     * @return
     */
    String selectSchoolId(@Param("classId")String classId);
}
