package com.fudian.business.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.KnowledgeListClassification;

import com.fudian.business.vo.KnowledgeVo;
import com.fudian.business.vo.QuestionSettingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * @author xuchenghong
 */

@Mapper
public interface KnowledgeListClassificationMapper extends BaseMapper<KnowledgeListClassification> {

    /**
     * 查询小程序一级知识点列表
     * @param studentId 学生id
     * @return 一级知识点列表
     */
    List<KnowledgeVo> selectKnowledgeListClassification(String studentId);
    /**
     * 查询小程序二级知识点列表
     * @param studentId 学生id
     * @return 二级知识点列表
     */
    List<KnowledgeVo> selectKnowledgeListClassificationTow(String studentId);

    List<Map<String,Object>> queryListByLevelId(@Param("levelId") String levelId);

    /**
     * 学生未练习的知识点
     * @author hong 2022/1/3
        * @param studentId
     * @return java.util.List<java.lang.String>
     */
    List<Map<String,Object>> getNoKnowledgeOfPractice(String studentId);

    /**
     * 班级所有学生未练习的知识点
     * @author hong 2022/1/3
        * @param map
     * @return java.util.List<java.lang.String>
     */
    List<String> getUnknownKnowledgeByClassId(Map<String,Object> map);

    /**
     * 获取所有知识点
     * @author hong 2022/2/17
     */
    List<Map<String,String>> getAllKnowledgePoints();

    /**
     * 知识点出题设置分页查询功能
     * @param questionSettingVo
     * @return
     */
    List<QuestionSettingVo> pageingSelectQuestionSetting(@Param("questionSettingVo") QuestionSettingVo questionSettingVo);

    List<JSONObject> getKnowledgeAndSchedule(@Param("classificationIds")List<String> classificationIds);
}
