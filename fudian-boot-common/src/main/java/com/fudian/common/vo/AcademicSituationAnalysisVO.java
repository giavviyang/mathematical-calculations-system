package com.fudian.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class AcademicSituationAnalysisVO {

    //算法ID（加法、减法、乘法、除法）
    private String algorithmId;
    //知识点ID
    private String classificationId;
    //知识点信息
    private String classificationMsg;
    //行数
    private int count;

}
