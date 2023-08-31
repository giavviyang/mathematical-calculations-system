package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z8;
import com.fudian.project.ams.pojo.E01z8Rela;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *@author lutao
 *@create 2020/1/8
 */

@Mapper
public interface E01z8RelaMapper extends BaseMapper<E01z8Rela> {
    int updateBatch(List<E01z8Rela> list);

    int updateBatchSelective(List<E01z8Rela> list);

    int batchInsert(@Param("list") List<E01z8Rela> list);

    int insertOrUpdate(E01z8Rela record);

    int insertOrUpdateSelective(E01z8Rela record);

    List<Map<String,Object>> selectViewDetails(E01z8 e01z8);
}
