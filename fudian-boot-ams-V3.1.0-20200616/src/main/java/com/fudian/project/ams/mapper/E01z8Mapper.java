package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z8;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface E01z8Mapper extends BaseMapper<E01z8> {
    int updateBatch(List<E01z8> list);

    int updateBatchSelective(List<E01z8> list);

    int batchInsert(@Param("list") List<E01z8> list);

    int insertOrUpdate(E01z8 record);

    int insertOrUpdateSelective(E01z8 record);

    List<Map<String,Object>> summaryE01z8();
}
