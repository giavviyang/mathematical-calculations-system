package com.fudian.common.annotation;



import com.fudian.common.enumns.OperateEnum;

import java.lang.annotation.*;

/**
 * 功能描述:
 * <系统日志注解>
 * @return
 * @since:  1.0.0
 * @Author  Mr.Hou
 * @Date    2020/4/6 2:10 下午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

	/**
	 * 模块
	 */
	public String title() default "";


	/**
	 * 日志内容
	 *
	 * @return
	 */
	String value() default "";

	/**
	 * 日志类型
	 *
	 * @return 0:操作日志;1:登录日志;2:定时任务;
	 */
	int logType() default 0;

	/**
	 * 操作类型
	 *
	 */
	OperateEnum operateType() default OperateEnum.OTHER;
}
