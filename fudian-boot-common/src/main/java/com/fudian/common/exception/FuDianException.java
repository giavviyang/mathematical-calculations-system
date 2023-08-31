package com.fudian.common.exception;

import com.fudian.common.enumns.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 〈异常类〉
 *
 * @author Mr.Hou
 * @create 2019/6/26
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FuDianException extends RuntimeException {

    private ExceptionEnum exceptionEnum;

    private String exceptionMsg=null;

    @Override
    public String getMessage() {
        if (exceptionEnum!=null){
            return exceptionEnum.getMsg();
        }else{
            return exceptionMsg;
        }
    }

    public FuDianException(String exceptionMsg ){
        this.exceptionMsg = exceptionMsg;
    }
    public FuDianException(ExceptionEnum exceptionEnum ){
        this.exceptionEnum = exceptionEnum;
    }
}
