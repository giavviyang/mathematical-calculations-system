package com.fudian.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 知识点出题方式（统一，随机）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSettingVo {
    /**
     * 班级id
     */
    private String classId;
    /**
     * 知识点id
     */
    private String classificationId;
    /**
     * 知识点名称
     */
    private String classificationName;
    /**
     * 知识点别名
     */
    private String classificationNickname;
    /**
     * 数域
     */
    private String knowledgeNumField;
    /**
     * 运算方式
     */
    private String knowledgeOperation;
    /**
     * 进位
     */
    private String knowledgeType;
    /**
     * 是否是随机出题
     */
    private String isRandom;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 第几页
     */
    private Integer pageNum;
}
