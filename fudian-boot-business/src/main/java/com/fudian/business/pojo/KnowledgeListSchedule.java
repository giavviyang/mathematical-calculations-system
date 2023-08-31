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
@TableName(value = "knowledge_list_schedule")
public class KnowledgeListSchedule extends BaseEntity implements Serializable {

    /**诊断id*/
    @TableId(value = "diagnosis_id")
    private String diagnosisId;

    /**知识点id*/
    @TableField(value = "classification_id")
    private String classificationId;

    /**诊断信息*/
    @TableField(value = "diagnosis_msg")
    private String diagnosisMsg;

}
