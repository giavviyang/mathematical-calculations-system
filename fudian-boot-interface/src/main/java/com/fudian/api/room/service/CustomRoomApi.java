package com.fudian.api.room.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fudian.api.room.pojo.CustomPosition;
import com.fudian.api.room.pojo.CustomRoom;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 17:12
 * @Desc:
 */
public interface CustomRoomApi {

    public List<CustomRoom> list(Wrapper<CustomRoom> wrapper);

}
