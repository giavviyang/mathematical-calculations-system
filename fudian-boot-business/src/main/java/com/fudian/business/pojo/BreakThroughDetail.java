package com.fudian.business.pojo;

import lombok.Data;

@Data
public class BreakThroughDetail {

    /**
     * 知识点id
     */
    private String classificationId;
    /**
     * 知识点名称
     */
    private String classificationMsg;
    /**
     * 是否正确（0-错误，1-正确）
     */
    private int isCorrect;
    /**
     * 正确率
     */
    private int correctRate;
    /**
     * 正确、错误行数
     */
    private int count;
    /**
     * 知识点总行数占比(正确+错误)/关卡
     */
    private int proportion;
    /**
     * 题目
     */
    private String wrongQuestion;

}
