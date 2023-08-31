package com.fudian.api.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fudian.api.system.pojo.SysDictionary;

import java.util.List;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 16:16
 * @Desc:
 */
public interface SysDictionaryApi {

    public List<SysDictionary> list(Wrapper<SysDictionary> queryWrapper);

    public SysDictionary getOne(Wrapper<SysDictionary> queryWrapper);

}
