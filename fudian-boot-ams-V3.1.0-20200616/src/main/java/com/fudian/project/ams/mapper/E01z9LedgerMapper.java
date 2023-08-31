package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z9Ledger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface E01z9LedgerMapper extends BaseMapper<E01z9Ledger> {
    int updateBatch(List<E01z9Ledger> list);

    int batchInsert(@Param("list") List<E01z9Ledger> list);

    int insertOrUpdate(E01z9Ledger record);

    int insertOrUpdateSelective(E01z9Ledger record);
}
