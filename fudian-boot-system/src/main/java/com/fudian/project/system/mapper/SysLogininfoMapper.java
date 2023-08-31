package com.fudian.project.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.SysLogininfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysLogininfoMapper extends BaseMapper<SysLogininfo> {
    int updateBatch(List<SysLogininfo> list);

    int batchInsert(@Param("list") List<SysLogininfo> list);

    int insertOrUpdate(SysLogininfo record);

    int insertOrUpdateSelective(SysLogininfo record);
}
