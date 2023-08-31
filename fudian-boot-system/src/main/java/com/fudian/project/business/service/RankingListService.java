package com.fudian.project.business.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fudian.business.vo.Classification;
import com.fudian.business.vo.KnowledgePopup;
import com.fudian.business.vo.Subject;
import com.fudian.business.vo.TrainResult;

import java.util.List;

public interface RankingListService {

    /**
     * 勤奋榜
     *
     * @param type
     * @return
     */
    JSONObject queryDiligenceRanking(int type);
    /**
     * 进步榜
     *
     * @return
     */
    JSONObject queryProgressRanking();

    /**
     * 关卡榜
     *
     * @return
     */
    JSONObject queryLevelRanking();
}
