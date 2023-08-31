package com.fudian.business.pojo;

import lombok.Data;

import java.util.List;

@Data
public class BreakthroughStatistics {

    /**
     * 年级
     */
    private String grade;
    /**
     * 关卡id
     */
    private String levelId;
    /**
     * 关卡名称
     */
    private String levelName;
    /**
     * 正确率
     */
    private int correctRate;
    /**
     * 平均时间
     */
    private double averageTime;
    /**
     * 总题数
     */
    private int totalNum;
    /**
     * 批次id
     */
    private String batchId;
    /**
     * 闯关详情
     */
    private List<BreakThroughDetail> details;
    /**
     * 历史闯关详情
     */
    private List<BreakThroughDetail> historyDetails;
}
