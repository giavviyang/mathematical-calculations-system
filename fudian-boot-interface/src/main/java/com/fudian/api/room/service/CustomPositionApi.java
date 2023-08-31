package com.fudian.api.room.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fudian.api.room.pojo.CustomPosition;


import java.util.List;
import java.util.Map;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 17:12
 * @Desc:
 */
public interface CustomPositionApi {

    public List<CustomPosition> list(Wrapper<CustomPosition> wrapper);

    public List<Map<String, Object>> getStruct(String roomId, String column, String section);
}
