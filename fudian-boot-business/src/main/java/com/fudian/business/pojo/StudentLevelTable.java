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


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "student_level_table")
public class StudentLevelTable extends BaseEntity implements Serializable {

    @TableField(value = "student_id")
    private String studentId;


    @TableField(value = "exercises_id")
    private String exercisesId;


    @TableField(value = "exercises_num")
    private String exercisesNum;


    @TableField(value = "exercises_operation_rule")
    private String exercisesOperationRule;


    @TableField(value = "exercises_answers")
    private String exercisesAnswers;

}
