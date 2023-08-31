package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z5;
import com.fudian.project.ams.pojo.E01z5Example;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface E01z5Mapper extends BaseMapper<E01z5> {
    long countByExample(E01z5Example example);

    int deleteByExample(E01z5Example example);

    List<E01z5> selectByExample(E01z5Example example);

    int updateByExampleSelective(@Param("record") E01z5 record, @Param("example") E01z5Example example);

    int updateByExample(@Param("record") E01z5 record, @Param("example") E01z5Example example);

    int updateBatch(List<E01z5> list);

    int batchInsert(@Param("list") List<E01z5> list);

    int insertOrUpdate(E01z5 record);

    int insertOrUpdateSelective(E01z5 record);

    List<Map<String,Object>> queryByMap(Map<String,String> map);


    List<Map<String,Object>> summaryE01z5();
}
