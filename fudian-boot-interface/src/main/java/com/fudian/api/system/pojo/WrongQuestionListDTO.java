package com.fudian.api.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrongQuestionListDTO {

    //主键
    private String id;
    //学生id
    private String studentId;
    //学生名
    private String studentName;
    //错题列表
    private String errorContent;
    //数域id
    private String numfieldId;
    //算法id
    private String algorithmId;
    //算法名（加、减、乘、除）
    private String fullName;
    //创建时间
    private Date createTime;
    //知识点id
    private String classificationId;
    //知识点名称
    private String classificationMsg;
    //知识点数量
    private String knowledgeCount;
    //知识点别名
    private String classificationNickname;
    //是否锁定
    private int isLock;
    //算法标识
    private String algorithmMsg;

}
