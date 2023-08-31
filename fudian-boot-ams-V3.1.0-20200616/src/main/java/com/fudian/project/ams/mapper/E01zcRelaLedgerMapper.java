package com.fudian.project.ams.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.ams.pojo.E01zcRelaLedger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* @Author: WangYuZhuo
* @Date: 2020/3/13 10:32
* @Desc:
*/
@Mapper
public interface E01zcRelaLedgerMapper extends BaseMapper<E01zcRelaLedger> {
    List<Map<String, Object>> queryBillsByMap(Map<String, Object> map);
}
