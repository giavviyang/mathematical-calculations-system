package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.WfTask;
import com.fudian.project.ams.pojo.WfTaskAndA01Vo;
import com.fudian.project.ams.pojo.WfTaskExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WfTaskMapper extends BaseMapper<WfTask> {
    int updateBatch(List<WfTask> list);

    int updateBatchSelective(List<WfTask> list);

    int batchInsert(@Param("list") List<WfTask> list);

    int insertOrUpdate(WfTask record);

    int insertOrUpdateSelective(WfTask record);

    long countByExample(WfTaskExample example);

    int deleteByExample(WfTaskExample example);

    List<WfTask> selectByExample(WfTaskExample example);

    int updateByExampleSelective(@Param("record") WfTask record, @Param("example") WfTaskExample example);

    int updateByExample(@Param("record") WfTask record, @Param("example") WfTaskExample example);

    List<WfTaskAndA01Vo> quertTaskList(WfTask wfTask);
}
