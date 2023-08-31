package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * @author xuchenghong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "exercises_train_set")
public class ExercisesTrainSet extends BaseEntity implements Serializable {

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
     * 学生id
     */
    @TableField(value = "student_id")
    private String studentId;
    /**
     * 班级id
     */
    @TableField(value = "class_id")
    private String classId;
    /**
     * 关卡id
     */
    @TableField(value = "level_id")
    private String levelId;

    /**
     * 习题数值 (1+1)
     */
    @TableField(value = "exercises_num")
    private String exercisesNum;

    /**
     * 习题运算规则
     */
    @TableField(value = "exercises_operation_rule")
    private String exercisesOperationRule;

    /**
     * 习题答案
     */
    @TableField(value = "exercises_answers")
    private String exercisesAnswers;

    /**
     * 习题类型
     */
    @TableField(value = "exercises_type")
    private Integer exercisesType;

    /**
     * 习题生成次数
     */
    @TableField(value = "exercises_create_num")
    private Integer exercisesCreateNum;
    /**
     * 错误答案
     */
    @TableField(value = "error_answers")
    private String errorAnswers;
    /**
     * 答题时间
     */
    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 类型  0练习 1闯关 ，2错题本
     */
    @TableField(value = "type")
    private Integer type;
    /**
     * 是否是错题  0不是 1是
     */
    @TableField(value = "is_error")
    private int isError;
    /**
     * 生成批次id 试卷id
     */
    @TableField(value = "batch_id")
    private String batchId;
    /**
     * 是否是随机出题 0随机，1统一
     */
    @TableField(value = "is_random")
    private Integer isRandom;


}
