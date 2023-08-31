package com.fudian.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 关卡VO
 * @author xuchenghong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LevelVo implements Serializable {
    /** id */
    private String levelId;
    /** 关卡 */
    private Integer levelName;
    /** 班级id */
    private String gradeNum;
    /** 关卡得分 */
    private int highestScore;
    /** 年级 */
    private Integer orderNum;
    /** 年级名称 */
    private String deptName;
}
