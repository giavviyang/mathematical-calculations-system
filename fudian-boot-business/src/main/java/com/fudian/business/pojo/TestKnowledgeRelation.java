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
@TableName(value = "test_knowledge_relation")
public class TestKnowledgeRelation extends BaseEntity implements Serializable {
    /**
     * 测试表主键
     */
    @TableField(value = "test_id")
    private String testId;
    /**
     * 知识点id
     */
    @TableField(value = "classification_id")
    private String classificationId;
    /**
     * 知识点名称
     */
    @TableField(exist = false)
    private String classificationMsg;
    /**
     * 比例
     */
    @TableField(value = "percentage")
    private Integer percentage;
}
