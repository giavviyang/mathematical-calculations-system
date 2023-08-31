package com.fudian.api.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrongQuestionListVO {

    //算法id
    private String algorithmId;
    //算法名（加、减、乘、除）
    private String fullName;
    //图片地址
    private String imgAddr;
    //知识点
    private List<KnowledgeDTO> knowledgeDTOs;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KnowledgeDTO {

        //知识点id
        private String knowledgeId;
        //知识点名称
        private String classificationMsg;
        //知识点名称
        private String classificationNickname;
        //知识点数量
        private String knowledgeCount;
        //知识是否锁定
        private int isLock;
        //错题
        private List<WrongQuestion> errorList;

    }

}
