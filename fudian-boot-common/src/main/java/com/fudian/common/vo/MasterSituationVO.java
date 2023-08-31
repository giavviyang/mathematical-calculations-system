package com.fudian.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterSituationVO {

    /**
     * 知识点id
     */
    private String classificationId;
    /**
     * 知识点描述
     */
    private String classificationMsg;
    /**
     * 知识点昵称
     */
    private String classificationNickname;
    /**
     * 知识点总练习题数
     */
    private int count;
    /**
     * 正确率
     */
    private int correctRate;
    /**
     * 平均用时（秒）
     */
    private String averageTime;
    /**
     * 答题学生数量
     */
    private int studentNum;
    /**
     * 0-掌握，1-未掌握，2-未练习
     */
    private int state;
    /**
     * 错题详情
     */
    private List<WrongQuestionDetails> details;
    /**
     * 批次IDs
     */
    private String batchIds;

    @Data
    public static class WrongQuestionDetails {
        /**
         * 详情
         */
        private String detail;
        /**
         * 错题
         */
        private String content;
        /**
         * 分类数量
         */
        private Integer classCount;

        public WrongQuestionDetails() {}

        public WrongQuestionDetails(String detail, String content) {
            this.detail = detail;
            this.content = content;
        }

        public WrongQuestionDetails(String detail, Integer classCount) {
            this.detail = detail;
            this.classCount = classCount;
        }
    }
}
