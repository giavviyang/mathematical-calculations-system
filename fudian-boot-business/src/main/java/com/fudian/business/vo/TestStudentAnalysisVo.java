package com.fudian.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 习题分析学生错题详情
 *
 * @author xuchenghong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestStudentAnalysisVo {
    /**
     * 题组id
     */
    private String testId;
    /**
     * 教师id
     */
    private String userId;
    /**
     * 班级id
     */
    private String classId;
    /**
     * 学生id
     */
    private String studentId;
    /**
     * 学生名
     */
    private String studentName;
    /**
     * 平均用时
     */
    private Double avgAnswerTime;
    /**
     * 总正确率
     */
    private Double sumCorrectRate;
    /**
     * 答题时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 知识点集合
     */
    private List<Map<String, Object>> classifyDetails;
    /**
     * 页容量
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer pageNum;
}
