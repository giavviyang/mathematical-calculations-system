package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuchenghong
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "knowledge_list_classification")
public class KnowledgeListClassification extends BaseEntity implements Serializable {
    /**分类id*/
    @TableId(value = "classification_id")
    private String classificationId;

    /**排序字段*/
    @TableId(value = "order_num")
    private String orderNum;

    /**父类id*/
    @TableId(value = "pid")
    private String pid;

    /**类型id*/
    @TableField(value = "knowledge_id")
    private String knowledgeId;

    /**字典*/
    @TableField(value = "classification_code")
    private String classificationCode;

    /**数据范围*/
    @TableField(value = "classification_datarange")
    private int classificationDatarange;

    /**出题数*/
    @TableField(value = "questions_number")
    private int questionsNumber;

    /**习题数据规则*/
    @TableField(value = "classification_rule")
    private String classificationRule;

    /**进位规则*/
    @TableField(value = "classification_carry_rule")
    private String classificationCarryRule;

    /**倒计时时间*/
    @TableField(value = "classification_time")
    private double classificationTime;

    /**分类描述信息*/
    @TableField(value = "classification_msg")
    private String classificationMsg;

    /**例题*/
    @TableField(value = "example_title")
    private String exampleTitle;
    /**是否有子类（0没有，1有）*/
    @TableField(value = "is_subclass")
    private int isSubclass;
    /**1整数，2小数，3分数*/
    @TableField(value = "type")
    private int type;
    /**别名*/
    @TableField(value = "classification_nickname")
    private String classificationNickname;

    /**是否有随机出题（0随机，1统一）*/
    @TableField(value = "is_random")
    private int isRandom;

    //例题
    @TableField(exist = false)
    private List<Popup> popupList;

    //诊断消息
    @TableField(exist = false)
    private  String  diagnosisMsg;

    @TableField(exist = false)
    private  List<KnowledgeListClassification>  children;

}
