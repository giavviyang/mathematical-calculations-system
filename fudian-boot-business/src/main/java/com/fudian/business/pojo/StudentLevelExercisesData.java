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
@TableName(value = "student_level_exercises_data")
public class StudentLevelExercisesData extends BaseEntity implements Serializable {

    @TableField(value = "student_id")
    private String studentId;


    @TableField(value = "level_id")
    private String levelId;


    @TableField(value = "grade")
    private Integer grade;


    @TableField(value = "create_mun")
    private String createMun;


    @TableField(value = "appoint_rule")
    private String appointRule;

}
