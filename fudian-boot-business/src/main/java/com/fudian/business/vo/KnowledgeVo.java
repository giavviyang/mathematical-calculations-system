package com.fudian.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 知识点
 * @author xuchenghong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeVo implements Serializable {
    /** id */
    private String classificationId;
    /** pid */
    private String pid;
    /** 分类id */
    private String knowledgeId;
    /** 知识点名称 */
    private String classificationMsg;
    /** 是否有子类 0没有，1有 */
    private Integer isSubclass;
    /** 数域 */
    private String knowledgeNumField;
    /** 运算符 */
    private String knowledgeOperation;
    /** 进位 */
    private String knowledgeType;

    /** 图标 */
    private String icon;
    /** 是否有弹窗 1有 0没有 */
    private int dialog;
    /** 锁定类型（未锁定，错5次锁，对5次锁） */
    private int lockType;
    /** 分数 */
    private Double grade;

}
