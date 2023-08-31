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
@TableName(value = "teacher")
public class Teacher extends BaseEntity implements Serializable {

    @TableId(value = "teacher_id")
    private String teacherId;


    @TableField(value = "teacher_name")
    private String teacherName;


    @TableField(value = "teacher_telphone")
    private String teacherTelphone;


    @TableField(value = "teacher_portrait")
    private byte[] teacherPortrait;

}
