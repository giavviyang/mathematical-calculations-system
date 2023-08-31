package com.fudian.common.pojo;

import com.fudian.common.enumns.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 〈通用异常返回结果类〉
 *
 * @author Mr.Hou
 * @create 2019/6/26
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonExceptionResult {

    private int code;

    private  String msg;

    private Long timestamp;

    public CommonExceptionResult(ExceptionEnum em){
        this.code = em.getCode();
        this.msg =  em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }

    public CommonExceptionResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp = System.currentTimeMillis();;
    }
}
