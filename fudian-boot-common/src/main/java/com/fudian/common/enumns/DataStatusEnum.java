package com.fudian.common.enumns;


/**
 * @Description 数据状态
 * @ClassName DataStatus
 * @Author mr.hou
 * @Date 2020/4/6 2:20 下午
 * @Version 1.0.0
 **/
public enum DataStatusEnum {
    /**
     * 正常
     */
    OK(1, "正常"),
    /**
     * 禁用
     */
    DISABLE(0, "停用"),
    /**
     * 删除
     */
    DELETED(-1, "删除");

    private final int code;
    private final String info;

    DataStatusEnum(int code, String info) {
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
