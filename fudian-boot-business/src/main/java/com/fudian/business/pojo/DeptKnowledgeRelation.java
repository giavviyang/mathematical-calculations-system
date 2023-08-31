package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xuchenghong
 */

/**
 * 班级知识点关联表
 * 作用：判断班级知识点训练出题方式是否随机
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "dept_knowledge_relation")
public class DeptKnowledgeRelation extends BaseEntity implements Serializable {
    /**
     * id
     */
    @TableField(value = "id")
    private String id;
    /**
     * 班级id
     */
    @TableField(value = "class_id")
    private String classId;

    /**
     * 知识点id
     */
    @TableField(value = "classification_id")
    private String classificationId;
    /**
     * 是否随机
     */
    @TableField(value = "is_random")
    private Integer isRandom;
}
