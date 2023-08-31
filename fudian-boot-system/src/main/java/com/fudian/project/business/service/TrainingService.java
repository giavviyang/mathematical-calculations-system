package com.fudian.project.business.service;


import com.fudian.business.vo.Classification;
import com.fudian.business.vo.KnowledgePopup;
import com.fudian.business.vo.TrainResult;
import com.fudian.common.pojo.CommonResult;

import java.util.List;

public interface TrainingService {


    /**
     * 查询类型 整数，小数，分数
     *
     * @return
     */
    List<Classification> selectTrainData();

    /**
     * 查询例题弹窗
     *
     * @return
     */
    KnowledgePopup selectKnowledgePopup(String knowledgeId);

    /**
     * 查询题目列表
     *
     * @return
     */
    CommonResult selectSubjectListById(String id, int type);

    /**
     * 答案提交
     *
     * @return
     */
    TrainResult submitAnswerResult(String id, String json, int second);

    /**
     * 知识点练习错题重做答案提交
     *
     * @return
     */
    TrainResult submitErrorAnswerResult(String id, String json, int second);


}
