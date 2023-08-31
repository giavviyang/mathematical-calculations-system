package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z8Ledger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface E01z8LedgerMapper extends BaseMapper<E01z8Ledger> {
    int updateBatch(List<E01z8Ledger> list);

    int batchInsert(@Param("list") List<E01z8Ledger> list);

    int insertOrUpdate(E01z8Ledger record);

    int insertOrUpdateSelective(E01z8Ledger record);
}
