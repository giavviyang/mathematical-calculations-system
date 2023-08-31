package com.fudian.business.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author xuchenghong
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfo implements Serializable {

    /**
     * 学生头像
     */
    private String studentPortrait;

    /**
     * 头像框
     */
    private String portraitFrame;

    /**
     * 学生昵称
     */
    private String studentNickname;

    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 学生班级
     */
    private String studentGrade;

    /**
     * 勤奋之星数量
     */
    private int diligence;

    /**
     * 进步之星数量
     */
    private int progress;

    /**
     * 累计登录次数
     */
    private int cumulativeLogin;

    /**
     * 已掌握知识点数量
     */
    private int knowledgeNumber;

    /**
     * 闯过关卡数量
     */
    private int levelNumber;

    /** 成长曲线正确率：(值1, 值2, 值3...)*/
    //private String correctRate;

    /**成长曲线习题数：(值1, 值2, 值3...)*/
    //private String exercisesNumber;

    /**
     * 成长曲线统计
     */
    private List<JSONObject> growthCurve;

    /**
     * 音效开关状态 0开 1关
     */
    private int acoustics;

    /**
     * 背景音状态 0开 1关
     */
    private int backgroundMusic;

    /**
     * 金币数
     */
    private int goldNumber;

}
