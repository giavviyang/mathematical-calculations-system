package com.fudian.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudian.common.utils.AppletUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 榜单
 *
 * @author xch
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SysRankingList implements Serializable {

    /**
     * 排名
     */
    private String number;
    /**
     * 班级id
     */
    private String deptId;
    /**
     * 班级名称
     */
    private String deptName;
    /**
     * 学生id
     */
    private String studentId;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 总题数
     */
    private int totalNum;
    /**
     * 正确率
     */
    private double correcNum;
    /**
     * 平均正确率
     */
    private double averageCorrecRate;
    /**
     * 平均每题用时
     */
    private double averageTime;
    /**
     * 对比上周进步率
     */
    private double progressRate;
    /**
     * 榜单类型 0周勤奋榜 1学期勤奋榜
     */
    private int type;
    /**
     * 勤奋之星
     */
    private int diligentStar;
    /**
     * 进步之星
     */
    private int progressStar;

    /**
     * 页容量
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 起始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public void setNumber(String number) {
        int i = number.indexOf(".");
        if (i != -1){
            number = number.substring(0,i);
        }
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }

    public void setCorrecNum(double correcNum) {
        this.correcNum = AppletUtils.retainTwoDecimal(correcNum)*100;
    }

    public double getCorrecNum() {
        return this.correcNum;
    }

    public void setAverageCorrecRate(double averageCorrecRate) {
        this.averageCorrecRate = AppletUtils.retainTwoDecimal(averageCorrecRate)*100;
    }

    public double getAverageCorrecRate() {
        return this.averageCorrecRate;
    }

    public void setAverageTime(double averageTime) {
        this.averageTime = AppletUtils.retainTwoDecimal(averageTime);
    }

    public double getAverageTime() {
        return this.averageTime;
    }

    public void setProgressRate(double progressRate) {
        this.progressRate = AppletUtils.retainTwoDecimal(progressRate)*100;
    }

    public double getProgressRate() {
        return this.progressRate;
    }
}
