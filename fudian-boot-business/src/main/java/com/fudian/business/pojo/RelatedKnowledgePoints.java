package com.fudian.business.pojo;

import lombok.Data;

@Data
public class RelatedKnowledgePoints {

    /**
     * 学生答题统计id
     */
    private String answerId;
    /**
     * 知识点id
     */
    private String classificationId;
    /**
     * 知识点名称
     */
    private String classificationMsg;
    /**
     * 正确率
     */
    private int correctRate;
    /**
     * 错题
     */
    private String wrongQuestion;

}
