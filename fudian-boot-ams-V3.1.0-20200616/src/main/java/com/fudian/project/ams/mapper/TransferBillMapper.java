package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.TransferBill;
import com.fudian.project.ams.pojo.TransferBillExample;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TransferBillMapper extends BaseMapper<TransferBill> {
    long countByExample(TransferBillExample example);

    int deleteByExample(TransferBillExample example);

    List<TransferBill> selectByExample(TransferBillExample example);

    int updateByExampleSelective(@Param("record") TransferBill record, @Param("example") TransferBillExample example);

    int updateByExample(@Param("record") TransferBill record, @Param("example") TransferBillExample example);

    int updateBatch(List<TransferBill> list);

    int batchInsert(@Param("list") List<TransferBill> list);

    int insertOrUpdate(TransferBill record);

    int insertOrUpdateSelective(TransferBill record);

    List<Map<String,Object>> queryBillsByMap(Map<String,Object> map);

    List<Map<String,Object>>  highDetail(HashMap<String, String> data);
}
