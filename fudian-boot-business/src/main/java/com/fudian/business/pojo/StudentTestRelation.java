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
 * 记录学生课堂练习题的最高分等数据
 * @author xuchenghong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "student_test_relation")
public class StudentTestRelation extends BaseEntity implements Serializable {
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
     * 课堂测试id
     */
    @TableField(value = "test_id")
    private String testId;
    /**
     * 最高分
     */
    @TableField(value = "highest_score")
    private Integer highestScore;
    /**
     * 正确次数
     */
    @TableField(value = "correct_num")
    private Integer correctNum;
    /**
     * 错误次数
     */
    @TableField(value = "error_num")
    private Integer errorNum;
    /**
     * 班级id
     */
    @TableField(value = "dept_id")
    private String deptId;
    /**
     * 历史发放最高金币数量
     */
    @TableField(value = "highest_coins_num")
    private Integer highestCoinsNum;
    /**
     * 历史最少用时
     */
    @TableField(value = "second")
    private Integer second;
    /**
     * 锁定类型 0未锁定 1错误锁 2正确锁 3前一关未锁定
     */
    @TableField(value = "is_lock")
    private Integer isLock;
    /**
     * 锁定时间
     */
    @TableField(value = "lock_time")
    private Date lockTime;
    /**
     * 是否掌握 0未掌握 1已掌握
     */
    @TableField(value = "is_master")
    private Integer isMaster;

}
