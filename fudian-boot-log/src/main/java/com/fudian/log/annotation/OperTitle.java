package com.fudian.log.annotation;

import java.lang.annotation.*;

/**
 *
 * @Desc 添加操作信息
 * @author wangyuzhuo
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperTitle
{
    /**
     *
     * @return 操作模块
     */
    String value() ;



}
