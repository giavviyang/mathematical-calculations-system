package com.fudian.api.system.service;

import com.fudian.api.system.pojo.WatermarkConfig;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/29 10:26
 * @Desc:
 */
public interface WatermarkConfigApi {

    List<WatermarkConfig> select(WatermarkConfig config);
}
