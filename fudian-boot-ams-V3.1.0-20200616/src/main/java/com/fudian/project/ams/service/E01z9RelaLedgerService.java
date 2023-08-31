package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.pojo.E01z9Ledger;
import com.fudian.project.ams.pojo.E01z9RelaLedger;

import java.util.List;
import java.util.Map;

/**
* @Author: WangYuZhuo
* @Date: 2020/3/13 10:32
* @Desc:
*/
public interface E01z9RelaLedgerService extends IService<E01z9RelaLedger>{

    List<Map<String,Object>> selectViewDetails( String e01z9Id);

    List<Map<String,Object>> selectA01sByE01z9Id(String e01z9Id);

    /**
     * 借阅明细台账
     * @param e01z9Ledger 批次信息
     * @return
     */
    CommonGridResult queryE01z9List(E01z9Ledger e01z9Ledger);
}
