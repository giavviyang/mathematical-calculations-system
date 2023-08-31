package com.fudian.api.system.enums;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/26 18:16
 * @Desc:
 */

public enum LoginStatusEnum {

    /**
     * 成功
     */
    SUCCESS("0"),
    /**
     * 失败
     */
    FAILD("1");

    private String status;

    LoginStatusEnum(String s) {
        this.status = s;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
