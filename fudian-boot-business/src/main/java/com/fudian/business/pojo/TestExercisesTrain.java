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
@TableName(value = "test_exercises_train")
public class TestExercisesTrain extends BaseEntity implements Serializable {
    /**
     * 习题id
     */
    @TableId(value = "exercises_id")
    private String exercisesId;
    /**
     * 课堂测试id
     */
    @TableField(value = "test_id")
    private String testId;
    /**
     * 知识id
     */
    @TableField(value = "classification_id")
    private String classificationId;

    /**
     * 习题数值 (1+1)
     */
    @TableField(value = "exercises_num")
    private String exercisesNum;
    /**
     * 习题答案
     */
    @TableField(value = "exercises_answers")
    private String exercisesAnswers;
    /**
     * 习题类型 1-整数，2-小数，3-分数
     */
    @TableField(value = "exercises_type")
    private Integer exercisesType;
}
