package com.fudian.api.room.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fudian.api.room.enums.RoomEnmu;
import com.fudian.api.room.pojo.CustomPositionA01;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 17:12
 * @Desc:
 */
public interface CustomPositionA01Api {
    public RoomEnmu.A01Up addPosA01(String a01Id, String posId);

    public List<CustomPositionA01> list(Wrapper<CustomPositionA01> queryWrapper);

    public boolean updateBatchById(List<CustomPositionA01> a01List );

    public int updateBatch(List<CustomPositionA01> list);

    public RoomEnmu.A01Down deletePositionA01s(List<String> a01Ids);
}
