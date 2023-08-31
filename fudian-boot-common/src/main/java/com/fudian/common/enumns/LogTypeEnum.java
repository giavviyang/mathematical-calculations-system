package com.fudian.common.enumns;

/**
 * @Description 日志类型
 * @ClassName LogType
 * @Author mr.hou
 * @Date 2020/4/6 2:20 下午
 * @Version 1.0.0
 **/
public enum LogTypeEnum {

    /**
     * 登录日志
     */
    LOGIN(1, "登录日志"),
    /**
     * 操作日志
     */
    OPERATE(0, "操作日志");


    private final int code;
    private final String info;

    LogTypeEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
