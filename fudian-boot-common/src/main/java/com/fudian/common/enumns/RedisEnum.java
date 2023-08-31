package com.fudian.common.enumns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @Author: WangYuZhuo
 * @Date: 2019/12/25 17:18
 * @Desc:
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RedisEnum {

    /**
     * redis超时时间
     */
    SHORT(10 * 60,TimeUnit.SECONDS),
    NORMAL(20 * 60 ,TimeUnit.SECONDS),
    LONG(30 * 60 ,TimeUnit.SECONDS);

    private long timeOutLength;

    private TimeUnit type;
}
