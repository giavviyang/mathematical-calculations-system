package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xuchenghong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "test_answer")
public class TestAnswer extends BaseEntity implements Serializable {
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
     * 习题数值 (1+1)
     */
    @TableField(value = "exercises_num")
    private String exercisesNum;
    /**
     * 习题类型 1-整数，2-小数，3-分数
     */
    @TableField(value = "exercises_type")
    private Integer exercisesType;
    /**
     * 习题答案
     */
    @TableField(value = "exercises_answers")
    private String exercisesAnswers;
    /**
     * 学生答案
     */
    @TableField(value = "student_answers")
    private String studentAnswers;
    /**
     * 答题时间
     */
    @TableField(value = "answer_time")
    private int answerTime;
    /**
     * 平均每题答题时间
     */
    @TableField(value = "avg_answer_time")
    private double avgAnswerTime;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;
    /**
     * 生成批次id 试卷id
     */
    @TableField(value = "batch_id")
    private String batchId;
    /**
     * 是否是错题  0不是 1是
     */
    @TableField(value = "is_error")
    private Integer isError;
}
