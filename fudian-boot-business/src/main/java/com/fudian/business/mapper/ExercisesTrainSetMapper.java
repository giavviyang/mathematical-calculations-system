package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.BreakThroughDetail;
import com.fudian.business.pojo.ExercisesTrainSet;

import com.fudian.business.pojo.RelatedKnowledgePoints;
import com.fudian.common.vo.AcademicSituationAnalysisVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author xuchenghong
 */

@Mapper
public interface ExercisesTrainSetMapper extends BaseMapper<ExercisesTrainSet> {

    List<Map<String,Object>>  getAlgorithmAmount(String studentId, int numberType);

    List<Map<String,Object>> getKnowledgeAmount(String studentId, int numberType,String algorithm);

    /**
     * 统计每个关卡知识点信息
     * 返回数据：1-知识点id、2-知识点名称、3-题目是否做对（0-错误，1-正确）、4-当前知识点的总（错误/正确）条数、5-当前知识点所有（错误/正确）题目
     * @author hong 2022/2/10
        * @param studentId
        * @param batchIds
     * @return java.util.List<com.fudian.business.pojo.BreakThroughDetail>
     */
    List<BreakThroughDetail> getBreakThroughTopic(@Param("studentId") String studentId, @Param("batchIds") List<String> batchIds);

    /**
     * 获取知识点练习的错题
     * @param studentId
     * @param classificationIds
     * @return
     */
    @Deprecated
    List<RelatedKnowledgePoints> getPracticeWrongQuestions(@Param("studentId") String studentId,
                                                           @Param("classificationIds") Set<String> classificationIds);

    /**
     * 根据批次id获取错题
     * @author xch
     * @param ids
     * @return
     */
    List<ExercisesTrainSet> getErrorExercises(@Param("ids") List<String> ids);

    /**
     * 删除没有作答的习题
     * @param studentId 学生id
     * @param type 习题类型 知识点0，闯关1，错题本2
     * @param relationId 关联id，(知识点id，闯关id)
     * @return
     */
    int batchDeleteExercisesSet(@Param("studentId") String studentId,@Param("type") int type,@Param("relationId") String relationId);
}
