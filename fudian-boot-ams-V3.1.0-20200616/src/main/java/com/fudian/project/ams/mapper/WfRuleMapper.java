package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.WfRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WfRuleMapper extends BaseMapper<WfRule> {
    int updateBatch(List<WfRule> list);

    int updateBatchSelective(List<WfRule> list);

    int batchInsert(@Param("list") List<WfRule> list);

    int insertOrUpdate(WfRule record);

    int insertOrUpdateSelective(WfRule record);
}