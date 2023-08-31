package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.ExercisesTrainSet;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;

import java.util.Map;

public interface AcademicSituationAnalysisService extends IService<ExercisesTrainSet> {

    /**
     * 根据数域和学生id获取每个算法的总练习题数
     * @author hong 2022/1/2
     * @param studentId
        * @param numberType
     * @return com.fudian.common.pojo.CommonResult
     */
    CommonResult getAlgorithmAmount(String studentId, String numberType);

    /**
     * 根据数域和学生id获取每个知识点的总练习题数
     * @author hong 2022/1/2
        * @param studentId
        * @param numberType
     * @return com.fudian.common.pojo.CommonResult
     */
    CommonResult getKnowledgeAmount(String studentId, String numberType,String algorithm);

    /**
     * 根据学生id，获取知识点掌握情况
     * @author hong 2022/1/2
        * @param studentId
     * @return com.fudian.common.pojo.CommonResult
     */
    CommonResult getMasterSituation(String studentId);

    /**
     * 根据班级id，分析班级知识点掌握情况
     * @author hong 2022/1/2
        * @param map
     * @return com.fudian.common.pojo.CommonResult
     */
    CommonGridResult getKnowledgeAnalysis(Map<String,Object> map);

    /**
     * 根据学生id，获取闯关答题情况
     * @author hong 2022/1/4
        * @param studentId
     * @return com.fudian.common.pojo.CommonResult
     */
    CommonResult getBreakThroughAnalysis(String studentId);

}
