package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z9;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lutao
 * @create 2020/1/15
 */

@Mapper
public interface E01z9Mapper extends BaseMapper<E01z9> {
    int updateBatch(List<E01z9> list);

    int updateBatchSelective(List<E01z9> list);

    int batchInsert(@Param("list") List<E01z9> list);

    int insertOrUpdate(E01z9 record);

    int insertOrUpdateSelective(E01z9 record);

    List<Map<String,Object>> summaryE01z9();
}
