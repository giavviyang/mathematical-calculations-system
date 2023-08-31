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
@TableName(value = "level_table_exercises")
public class LevelTableExercises extends BaseEntity implements Serializable {

    @TableField(value = "exercises_id")
    private String exercisesId;


    @TableField(value = "level_id")
    private String levelId;


    @TableField(value = "exercises_num")
    private String exercisesNum;


    @TableField(value = "exercises_operation_rule")
    private String exercisesOperationRule;


    @TableField(value = "exercises_answers")
    private String exercisesAnswers;


    @TableField(value = "exercises_type")
    private Integer exercisesType;


    @TableField(value = "exercises_create_num")
    private Integer exercisesCreateNum;


    @TableField(value = "classification_id")
    private String classificationId;

}
