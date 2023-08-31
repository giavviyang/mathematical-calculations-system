package com.fudian.api.system.pojo;

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
@TableName(value = "students")
public class Students extends BaseEntity implements Serializable {
    /**
     * 学生ID
     */
    @TableId(value = "student_id")
    private String studentId;

    /**
     * 正在使用中的头像框
     */
    @TableField(value = "goods_id")
    private String goodsId;

    /**
     * 学生姓名
     */
    @TableField(value = "student_name")
    private String studentName;
    /**
     * 学生昵称
     */
    @TableField(value = "student_nickname")
    private String studentNickname;

    /**
     * 班级信息id
     */
    @TableField(value = "class_room_id")
    private String classRoomId;

    /**
     * 班主任Id
     */
    @TableField(value = "class_headmaster_id")
    private String classHeadmasterId;

    /**
     * 学生学号
     */
    @TableField(value = "student_number")
    private String studentNumber;

    /**
     * 学生班级
     */
    @TableField(value = "student_grade")
    private String studentGrade;

    /**
     * 学生密码
     */
    @TableField(value = "student_code")
    private String studentCode;

    /**
     * 学生状态
     */
    @TableField(value = "student_status")
    private Integer studentStatus;

    /**
     * 微信的openID
     */
    @TableField(value = "student_openid")
    private String studentOpenid;

    /**
     * 练习状态(学生练习状态 0为锁定太，1为正常)
     */
    @TableField(value = "student_practice_status")
    private Integer studentPracticeStatus;

    /**
     * 学生头像
     */
    @TableField(value = "student_portrait")
    private String studentPortrait;
    /**
     * 金币数量
     */
    @TableField(value = "coins_num")
    private int coinsNum;
    /**
     * 进步之星数量
     */
    @TableField(value = "progress_star")
    private int progressStar;
    /**
     * 勤奋之星数量
     */
    @TableField(value = "diligent_star")
    private int diligentStar;
    /**
     * 累计登录次数
     */
    @TableField(value = "cumulative_login")
    private int cumulativeLogin;

    /**音效开关状态 0开 1关*/
    @TableField(value = "acoustics")
    private int acoustics;

    /**背景音状态 0开 1关*/
    @TableField(value = "background_music")
    private int backgroundMusic;

    /**备注*/
    @TableField(value = "remark")
    private String remark;

}
