package com.fudian.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterSituationStatisticsDTO {

    /**
     * 知识点id
     */
    private String classificationId;
    /**
     * 总题数
     */
    private int count;
    /**
     * 正确率
     */
    private int correctRate;
    /**
     * 答题平均用时
     */
    private String averageTime;
    /**
     * 错题
     */
    private String error;
    /**
     * 错误原因
     */
    private String reason;

}
