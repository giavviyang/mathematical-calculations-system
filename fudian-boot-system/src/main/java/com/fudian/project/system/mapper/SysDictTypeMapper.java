package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.system.pojo.SysDictType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
    int updateBatch(List<SysDictType> list);

    int batchInsert(@Param("list") List<SysDictType> list);

    int insertOrUpdate(SysDictType record);

    int insertOrUpdateSelective(SysDictType record);

}
