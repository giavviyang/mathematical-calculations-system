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

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "student_answer")
public class StudentAnswer extends BaseEntity implements Serializable {
    /**
     * id
     */
    @TableId(value = "id")
    private String id;
    /**
     * 学生id
     */
    @TableField(value = "student_id")
    private String studentId;
    /**
     * 知识点id
     */
    @TableField(value = "classification_id")
    private String classificationId;
    /**
     * 班级id
     */
    @TableField(value = "class_id")
    private String classId;

    /**
     * 答题时间
     */
    @TableField(value = "answer_time")
    private Integer answerTime;
    /**
     * 正确题数
     */
    @TableField(value = "correc_num")
    private int correcNum;
    /**
     * 错误题数
     */
    @TableField(value = "error_num")
    private int errorNum;
    /**
     * 原题  习题id+答案
     */
    @TableField(value = "original_title")
    private String originalTitle;
    /**
     * 类型0练习 1闯关 ，2错题本
     */
    @TableField(value = "type")
    private int type;
    /**
     * 总题数
     */
    @TableField(value = "total_num")
    private int totalNum;
    /**
     * 正确率
     */
    @TableField(value = "correc_rate")
    private Double correcRate;
    /**
     * 用时比例
     */
    @TableField(value = "time_proportion")
    private double timeProportion;
    /**
     * 判断是否是锁定的判断条件（0不是，1是）
     */
    @TableField(value = "is_statistics")
    private double isStatistics;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 批次id
     */
    @TableField(value = "batch_id")
    private String batchId;

    /**
     * 是否是随机出题 0随机，1统一
     */
    @TableField(value = "is_random")
    private Integer isRandom;

}
