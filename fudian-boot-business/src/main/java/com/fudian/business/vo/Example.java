package com.fudian.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 例题
 * @author xuchenghong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Example {
    /** 描述内容 */
    private String content;
    /** 例题类型(0单题，1双题对立) */
    private int exampleType;
    /** 公式1 */
    private String formulaOne;
    /** 公式2 */
    private String formulaTwo;
}
