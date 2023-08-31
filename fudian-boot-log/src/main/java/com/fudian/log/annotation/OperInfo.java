package com.fudian.log.annotation;

import java.lang.annotation.*;

/**
 *
 * @Desc 添加操作信息
 * @author wangyuzhuo
 */
@Target({ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperInfo
{
    /**
     *
     * @return 操作信息
     */
    String info() ;

    /**
     *
     * @return 操作信息中的参数(请求中的参数key)
     */
    String[] params() default {};

//    /**
//     *
//     * @return 操作模块
//     */
//    String title() default "";

    String[] ignore() default {};

}
