package com.fudian.business.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fudian.common.pojo.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "exercises_rules")
public class ExercisesRules extends BaseEntity {

    /**
     * uuid
     */
    @TableId(value = "excercise_rule_id", type = IdType.INPUT)
    private String excerciseRuleId;

    /**
     * uuid
     */
    @TableId(value = "pid")
    private String pid;

    /**
     * 元素个数
     */
    @TableField(value = "excercise_element_num")
    private String excerciseElementNum;

    /**
     * 运算类型
     */
    @TableField(value = "operate_type")
    private int operateType;

    /**
     * 数位
     */
    @TableField(value = "digit_postion")
    private int digitPostion;

    /**
     * 题目数位要求（小数*小数 整数+整数）
     */
    @TableField(value = "element_rulers")
    private String elementRulers;

    /**
     * 进位要求
     */
    @TableField(value = "carry_rulers")
    private int carryRulers;

    /**
     * 第一位数
     */
    @TableField(value = "num_first")
    private int numFirst;

    /**
     * 第二位数
     */
    @TableField(value = "num_second")
    private int numSecond;

    /**
     * 第三位数
     */
    @TableField(value = "num_three")
    private int numThree;

    /**
     * 第一个参数倍率
     */
    @TableField(value = "first_magnify")
    private String firstMagnify;

    /**
     * 第二个参数倍率
     */
    @TableField(value = "second_magnify")
    private String secondMagnify;

    /**
     * 第三个参数倍率
     */
    @TableField(value = "three_magnify")
    private String threeMagnify;

    /**
     * 分母
     */
    @TableField(value = "denominator")
    private int denominator;

    /**
     * 分子
     */
    @TableField(value = "molecule")
    private int molecule;

    /**
     * 第一位最小值
     */
    @TableField(value = "first_min")
    private int firstMin;

    /**
     * 第二位最小值
     */
    @TableField(value = "second_min")
    private int secondMin;

    /**
     * 第三位最小值
     */
    @TableField(value = "three_min")
    private int threeMin;

    /**
     * 第一位长度
     */
    @TableField(value = "num_first_len")
    private int numFirstLen;

    /**
     * 第二位长度
     */
    @TableField(value = "num_second_len")
    private int numSecondLen;

    /**
     * 第三位长度
     */
    @TableField(value = "num_three_len")
    private int numThreeLen;

    /**
     * 第一个数类型
     */
    @TableField(value = "first_num_type")
    private int firstNumType;

    /**
     * 第二个数类型
     */
    @TableField(value = "second_num_type")
    private int secondNumType;

    /**
     * 第三个数类型
     */
    @TableField(value = "three_num_type")
    private int threeNumType;

}
