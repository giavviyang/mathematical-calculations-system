package com.fudian.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectList implements Serializable {
    /**
     * 题目id
     */
    private String id;
    /**
     * 算式类型 整数，小数，分数
     */
    private Integer type;
    /**
     * 结果类型 整数，小数，分数
     */
    private Integer resultType;
    /**
     * 算式
     */
    private String formula;


}
