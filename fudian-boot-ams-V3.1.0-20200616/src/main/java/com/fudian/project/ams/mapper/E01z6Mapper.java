package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z6;
import com.fudian.project.ams.pojo.E01z6Example;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface E01z6Mapper extends BaseMapper<E01z6> {
    long countByExample(E01z6Example example);

    int deleteByExample(E01z6Example example);

    List<E01z6> selectByExample(E01z6Example example);

    int updateByExampleSelective(@Param("record") E01z6 record, @Param("example") E01z6Example example);

    int updateByExample(@Param("record") E01z6 record, @Param("example") E01z6Example example);

    int updateBatch(List<E01z6> list);

    int batchInsert(@Param("list") List<E01z6> list);

    int insertOrUpdate(E01z6 record);

    int insertOrUpdateSelective(E01z6 record);
}
