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
@TableName(value = "teacher_class_ref")
public class TeacherClassRef extends BaseEntity implements Serializable {

    @TableField(value = "teacher_id")
    private String teacherId;


    @TableField(value = "class_room_id")
    private String classRoomId;

}
