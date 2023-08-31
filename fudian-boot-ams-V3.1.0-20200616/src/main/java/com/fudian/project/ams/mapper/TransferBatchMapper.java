package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.TransferBatch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TransferBatchMapper extends BaseMapper<TransferBatch> {
    int updateBatch(List<TransferBatch> list);

    int updateBatchSelective(List<TransferBatch> list);

    int batchInsert(@Param("list") List<TransferBatch> list);

    int insertOrUpdate(TransferBatch record);

    int insertOrUpdateSelective(TransferBatch record);

    List<TransferBatch> selectByMaps(Map<String, Object> maps);

    List<Map<String,Object>> summaryTransferBathc();
}
