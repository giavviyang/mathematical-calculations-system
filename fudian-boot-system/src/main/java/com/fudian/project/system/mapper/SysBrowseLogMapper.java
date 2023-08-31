package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.SysBrowseLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
  *@author lutao
  *@create 2020/3/9
*/

public interface SysBrowseLogMapper extends BaseMapper<SysBrowseLog> {
    int updateByPrimaryKeySelective(SysBrowseLog record);

    int updateByPrimaryKey(SysBrowseLog record);

    int updateBatch(List<SysBrowseLog> list);

    int batchInsert(@Param("list") List<SysBrowseLog> list);
}
