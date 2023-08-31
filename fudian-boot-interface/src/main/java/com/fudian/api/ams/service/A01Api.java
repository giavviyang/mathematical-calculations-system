package com.fudian.api.ams.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.fudian.api.ams.pojo.A01;

import java.util.List;
import java.util.Map;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/6/28 15:59
 * @Desc:
 */
public interface A01Api{
    public List<A01> list(Wrapper<A01> queryWrapper);

    public List<Map<String, Object>> selectA01ByDeptIdAndPosiIsNull(String deptId, String a0101, String state);
}
