package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 知识点练习统一出题习题存储表
 *
 * @author xuchenghong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "knowledge_exercises_train")
public class KnowledgeExercisesTrain implements Serializable {
    /**
     * 习题id
     */
    @TableId(value = "exercises_id")
    private String exercisesId;
    /**
     * 知识id
     */
    @TableField(value = "classification_id")
    private String classificationId;

    /**
     * 第一个值
     */
    @TableField(value = "first")
    private String first;
    /**
     * 第二个值
     */
    @TableField(value = "second")
    private String second;
    /**
     * 运算符
     */
    @TableField(value = "operate")
    private String operate;
    /**
     * 第三个值
     */
    @TableField(value = "three")
    private String three;
    /**
     * 习题答案
     */
    @TableField(value = "result")
    private String result;
}
