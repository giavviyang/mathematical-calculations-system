package com.fudian.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fudian.common.pojo.BaseEntity;
import com.fudian.common.utils.AppletUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearnPracticeVo  implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 学生id
     */
    private String studentId;
    /**
     * 学生name
     */
    private String studentName;
    /**
     * 知识点id
     */
    private String classificationId;
    /**
     * 知识点name
     */
    private String classificationName;
    /**
     * 班级id
     */
    private String classId;
    /**
     * 班级name
     */
    private String className;
    /**
     * 是否随机
     */
    private Integer isRandom;
    /**
     * 正确题数
     */
    private int correcNum;
    /**
     * 错误题数
     */
    private int errorNum;
    /**
     * 总题数
     */
    private int totalNum;
    /**
     * 正确率
     */
    private double correcRate;
    /**
     * 用时比例
     */
    private double timeProportion;
    /**
     * 批次id
     */
    private String batchId;
    /**
     * 错题拼接字段
     */
    private String wrongQuestion;
    /**
     * 错题集合
     */
    private List<String> wrongQuestionList;
    /**
     * 诊断信息
     */
    private String diagnosisMsg;

    /**
     * 页容量
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 总页数
     */
    private Integer total;

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


    public void setCorrecRate(double correcRate) {
        this.correcRate = AppletUtils.retainTwoDecimal(correcRate) * 100;
    }

    public double getCorrecRate() {
        return this.correcRate;
    }

    public void setTimeProportion(double timeProportion) {
        this.timeProportion = AppletUtils.retainTwoDecimal(timeProportion);
    }

    public double getTimeProportion() {
        return this.timeProportion;
    }


}
