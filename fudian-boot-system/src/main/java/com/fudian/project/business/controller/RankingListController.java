package com.fudian.project.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.business.service.RankingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 训练习题
 *
 * @author xuchenghong
 */
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*",origins = "*")
@RestController
@RequestMapping(value = "applet/rankingList")
public class RankingListController {

    @Autowired
    private RankingListService rankingListService;


    /**
     * 周勤奋榜
     *
     * @param
     * @return
     */
    @PostMapping("/weekDiligenceRanking")
    public CommonResult<?> weekDiligenceList() {
        JSONObject jsonObject = null;
        try {
            jsonObject = rankingListService.queryDiligenceRanking(0);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.success(jsonObject);
    }

    /**
     * 勤奋榜
     *
     * @param
     * @return
     */
    @PostMapping("/totalDiligenceRanking")
    public CommonResult<?> totalDiligenceList() {
        try {
            JSONObject jsonObject = rankingListService.queryDiligenceRanking(1);
            return CommonResult.success(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    /**
     * 进步榜
     *
     * @param
     * @return
     */
    @PostMapping("/progressRanking")
    public CommonResult<?> progressList() {
        JSONObject jsonObject = null;
        try {
            jsonObject = rankingListService.queryProgressRanking();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.success(jsonObject);
    }

    /**
     * 查询关卡榜
     *
     * @return
     */
    @PostMapping("levelRanking")
    public CommonResult<?> levelRanking() {
        JSONObject jsonObject = null;
        try {
            jsonObject = rankingListService.queryLevelRanking();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
        return CommonResult.success(jsonObject);
    }


}
