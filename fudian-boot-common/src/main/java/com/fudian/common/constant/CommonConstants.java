package com.fudian.common.constant;

/**
 * @Description 通用常量信息
 * @ClassName CommonConstants
 * @Author mr.hou
 * @Date 2020/4/6 2:11 下午
 * @Version 1.0.0
 **/
public class CommonConstants {


    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "fdams_login_tokens:";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "fdams_login_user";


    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "fdams ";


    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";


    /**
     * 常量  是
     */
    public static final int YES = 1;

    /**
     * 常量  否
     */
    public static final int NO = 0;

}
