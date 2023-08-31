package com.fudian.api.ams.service;

import com.fudian.api.ams.pojo.E01z1;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 16:36
 * @Desc:
 */
public interface E01zcRelaApi {

    public boolean judgeAthor(String e01z1Id);

    public List<String> getA01Author();

    public List<E01z1> getE01zcRelaByE01z1Id(String a01Id);

}
