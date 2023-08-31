package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.system.pojo.SysDictData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictDataMapper extends BaseMapper<SysDictData> {
    int updateBatch(List<SysDictData> list);

    int batchInsert(@Param("list") List<SysDictData> list);

    int insertOrUpdate(SysDictData record);

    int insertOrUpdateSelective(SysDictData record);
}
