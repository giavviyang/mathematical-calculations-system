package com.fudian.api.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WrongQuestion {

    //主键
    private String id;
    //错误答题
    private String error;
    //正确答题
    private String right;
    //诊断文本
    private String text;

}
