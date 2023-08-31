package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01z8RelaLedger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @Author: WangYuZhuo
* @Date: 2020/3/13 10:32
* @Desc: 
*/
@Mapper
public interface E01z8RelaLedgerMapper extends BaseMapper<E01z8RelaLedger> {

    List<Map<String,Object>> selectViewDetails(@Param("e01z8Id") String e01z8Id);

    List<Map<String,Object>> selectA01ByIds(@Param("ids") List<String> ids);

    List<Map<String, Object>> queryBillsByMap(Map<String, Object> map);
}
