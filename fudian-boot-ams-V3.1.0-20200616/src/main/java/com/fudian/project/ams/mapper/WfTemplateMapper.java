package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.WfTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WfTemplateMapper extends BaseMapper<WfTemplate> {
    int updateBatch(List<WfTemplate> list);

    int updateBatchSelective(List<WfTemplate> list);

    int batchInsert(@Param("list") List<WfTemplate> list);

    int insertOrUpdate(WfTemplate record);

    int insertOrUpdateSelective(WfTemplate record);
}