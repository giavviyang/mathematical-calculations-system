package com.fudian.project.business.service;


import com.alibaba.fastjson.JSONArray;
import com.fudian.business.vo.TrainResult;

public interface BreakThroughService {
    /**
     * 关卡列表
     *
     * @return
     */
    JSONArray queryLevelList();

    /**
     * 知识点练习错题重做答案提交
     *
     * @return
     */
    TrainResult submitErrorAnswerResult(String id, String json, int second);
}
