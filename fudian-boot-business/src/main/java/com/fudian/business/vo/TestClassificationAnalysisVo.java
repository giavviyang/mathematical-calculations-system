package com.fudian.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 习题分析知识点错题详情
 *
 * @author xuchenghong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestClassificationAnalysisVo {
    /**
     * 题组id
     */
    private String testId;
    /**
     * 知识点id
     */
    private String classificationId;
    /**
     * 题知识点比例id
     */
    private Integer percentage;
    /**
     * 知识点名称id
     */
    private String classificationMsg;
    /**
     * 班级id
     */
    private String classId;
    /**
     * 平均用时
     */
    private Double avgTime;
    /**
     * 总正确率
     */
    private Double sumCorrectRate;
    /**
     * 总错误题数
     */
    private Integer sumErrorNum;
    /**
     * 错题诊断
     */
    private String diagnosisMsg;
    /**
     * 错题集合
     */
    private List<Map<String, Object>> errorDetails;
    /**
     * 错题集合
     */
    private List<String> errorList;
    /**
     * 页容量
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer pageNum;
}
