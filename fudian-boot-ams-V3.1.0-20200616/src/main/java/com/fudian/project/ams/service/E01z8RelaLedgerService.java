package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.pojo.E01z8Ledger;
import com.fudian.project.ams.pojo.E01z8RelaLedger;

import java.util.List;
import java.util.Map;

/**
* @Author: WangYuZhuo
* @Date: 2020/3/13 10:32
* @Desc:
*/
public interface E01z8RelaLedgerService extends IService<E01z8RelaLedger>{
    List<Map<String,Object>> selectViewDetails(String e01z8Id);


    List<Map<String,Object>> selectA01sByE01z8Id(String e01z8Id);

    /**
     * 电子明细台账
     * @param e01z8Ledger 批次信息
     * @return
     */
    CommonGridResult queryE01z8List(E01z8Ledger e01z8Ledger);
}
