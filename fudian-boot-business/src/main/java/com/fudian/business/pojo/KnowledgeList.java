package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuchenghong
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "knowledge_list")
public class KnowledgeList extends BaseEntity implements Serializable {

    @TableId(value = "knowledge_id")
    private String knowledgeId;
    /**
     * 运算方式
     */
    @TableField(value = "knowledge_operation")
    private String knowledgeOperation;
    /**
     * 数域
     */
    @TableField(value = "knowledge_num_field")
    private String knowledgeNumField;
    /**
     * 进位
     */
    @TableField(value = "knowledge_type")
    private String knowledgeType;
    /**
     * 分类信息
     */
    @TableField(value = "classification_info")
    private String classificationInfo;
    /**
     * 分类小项
     */
    @TableField(value = "classification_child_type")
    private String classificationChildType;
    /**
     * 知识点信息
     */
    @TableField(value = "classification_msg")
    private String classificationMsg;
    /**
     * 诊断id
     */
    @TableField(value = "diagnosis_id")
    private String diagnosisId;

}
