package com.fudian.api.ams.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fudian.api.ams.pojo.A01;
import com.fudian.api.ams.pojo.E01z1;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 16:34
 * @Desc:
 */
public interface E01z1Api {

    public List<E01z1> list(Wrapper<E01z1> queryWrapper);

    List<A01> selectUserByE01ziIds(String[] ids);

}
