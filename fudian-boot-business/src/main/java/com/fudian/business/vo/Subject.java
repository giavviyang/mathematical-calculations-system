package com.fudian.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject implements Serializable {
    /**
     * 知识点id
     */
    private String id;
    /**
     * 知识点标题
     */
    private String title;
    /**
     * 数量
     */
    private int number;
    /**
     * 题目列表
     */
    private List<SubjectList> list;


}
