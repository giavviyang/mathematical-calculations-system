package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z7;
import com.fudian.project.ams.pojo.E01z7Example;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface E01z7Mapper extends BaseMapper<E01z7> {
    long countByExample(E01z7Example example);

    int deleteByExample(E01z7Example example);

    List<E01z7> selectByExample(E01z7Example example);

    int updateByExampleSelective(@Param("record") E01z7 record, @Param("example") E01z7Example example);

    int updateByExample(@Param("record") E01z7 record, @Param("example") E01z7Example example);

    int updateBatch(List<E01z7> list);

    int batchInsert(@Param("list") List<E01z7> list);

    int insertOrUpdate(E01z7 record);

    int insertOrUpdateSelective(E01z7 record);
}
