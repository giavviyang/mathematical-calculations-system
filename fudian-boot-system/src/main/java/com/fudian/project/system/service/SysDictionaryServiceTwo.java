package com.fudian.project.system.service;

import com.fudian.api.system.pojo.SysDictionary;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/7/1 19:17
 * @Desc:
 */
public interface SysDictionaryServiceTwo  {

    CommonResult deleteByIds(String json);

    CommonGridResult queryTreeByIdNew(SysDictionary sysDictionary);
}
