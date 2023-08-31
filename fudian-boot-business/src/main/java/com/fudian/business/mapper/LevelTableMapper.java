package com.fudian.business.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.LevelTable;
import com.fudian.business.vo.LevelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * @author xuchenghong
 */

@Mapper
public interface LevelTableMapper extends BaseMapper<LevelTable> {
    List<Map<String,Object>>  queryList(Map<String,Object> map);

    @Select("SELECT MAX(level_name) maxNum FROM level_table WHERE grade_num = #{gradeId} AND level_id IN (SELECT relation_id FROM student_knowledge_relation WHERE student_id = #{studentId} and highest_score >= 80)")
    Integer selectMaxPasses(String gradeId,String studentId);

    @Select("SELECT MAX(level_name) maxNum FROM level_table WHERE grade_num = #{gradeId}")
    Integer selectMaxCheckpoints(String gradeId);

    int getMaxLevelNum(String gradeId);
    /**
     * 获取学生-关卡信息
     * @param studentId 学生id
     * @return 关卡信息
     */
    List<LevelVo> getStudentLevelList(String studentId);
    /**
     * 获取关卡关联知识点进位
     *
     * @param levelId 关卡第
     * @return 关联知识点进位
     */
    List<JSONObject> getKnowledgeType(String levelId);
}
