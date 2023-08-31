package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
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
@TableName(value = "class_rome")
public class ClassRome extends BaseEntity implements Serializable {

    @TableId(value = "class_id")
    private String classId;

    @TableField(value = "class_name")
    private String className;

    @TableField(value = "class_grade")
    private Integer classGrade;

    @TableField(value = "class_headmaster_id")
    private String classHeadmasterId;

    @TableField(value = "class_address")
    private String classAddress;

    @TableField(value = "class_student_num")
    private Integer classStudentNum;

}
