package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z9;
import com.fudian.project.ams.pojo.E01z9Rela;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *@author lutao
 *@create 2020/1/8
 */

@Mapper
public interface E01z9RelaMapper extends BaseMapper<E01z9Rela> {
    int updateBatch(List<E01z9Rela> list);

    int updateBatchSelective(List<E01z9Rela> list);

    int batchInsert(@Param("list") List<E01z9Rela> list);

    int insertOrUpdate(E01z9Rela record);

    int insertOrUpdateSelective(E01z9Rela record);

    List<Map<String,Object>> selectViewDetails(E01z9 e01z9);
}
