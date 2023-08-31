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
@TableName(value = "wrong_question_list")
public class WrongQuestionList extends BaseEntity implements Serializable {
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
     * 错题内容（每题之间用，分割，正确与错误、诊断文本之间用|分割）
     */
    @TableField(value = "error_content")
    private String errorContent;
    /**
     * 数域id
     */
    @TableField(value = "numfield_id")
    private String numfieldId;
    /**
     * 运算规则id
     */
    @TableField(value = "algorithm_id")
    private String algorithmId;
    /**
     * 习题id
     */
    @TableField(value = "exercises_id")
    private String exercisesId;
    /**
     * 批次id
     */
    @TableField(value = "batch_id")
    private String batchId;
    /**
     * 关联类型 0练习 1闯关 2错题
     */
    @TableField(value = "type")
    private int type;
    /**
     * 删除标识（0-存在，1-删除）
     */
    @TableField(value = "deleted")
    private int deleted;
    /**
     * 错题创建时间
     */
    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
