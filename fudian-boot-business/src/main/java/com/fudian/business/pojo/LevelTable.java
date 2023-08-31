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
@TableName(value = "level_table")
public class LevelTable extends BaseEntity implements Serializable {

    /**
     * 关卡id
     */
    @TableId(value = "level_id")
    private String levelId;
    /**
     * 关卡名称
     */
    @TableField(value = "level_name")
    private String levelName;
    /**
     * 限制用时
     */
    @TableField(value = "level_time")
    private String levelTime;
    /**
     * 题目数量
     */
    @TableField(value = "level_num_topic")
    private String levelNumTopic;
    /**
     * 年级id
     */
    @TableField(value = "grade_num")
    private String gradeNum;
}
