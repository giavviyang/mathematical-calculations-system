package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z2;
import com.fudian.project.ams.pojo.E01z2Example;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface E01z2Mapper extends BaseMapper<E01z2> {
    long countByExample(E01z2Example example);

    int deleteByExample(E01z2Example example);

    List<E01z2> selectByExample(E01z2Example example);

    int updateByExampleSelective(@Param("record") E01z2 record, @Param("example") E01z2Example example);

    int updateByExample(@Param("record") E01z2 record, @Param("example") E01z2Example example);

    int updateBatch(List<E01z2> list);

    int batchInsert(@Param("list") List<E01z2> list);

    int insertOrUpdate(E01z2 record);

    int insertOrUpdateSelective(E01z2 record);

    List<Map<String,Object>> queryE01z2s(Map<String,String> map);

    List<Map<String,Object>> summaryE01z2();
}
