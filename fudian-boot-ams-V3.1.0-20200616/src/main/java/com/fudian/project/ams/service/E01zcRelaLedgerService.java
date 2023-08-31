package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.pojo.E01zcLedger;
import com.fudian.project.ams.pojo.E01zcRelaLedger;

/**
* @Author: WangYuZhuo
* @Date: 2020/3/13 10:32
* @Desc:
*/
public interface E01zcRelaLedgerService extends IService<E01zcRelaLedger>{

        /**
         * 电子明细台账
         * @param e01zcLedger 批次信息
         * @return
         */
        CommonGridResult queryE01zcList(E01zcLedger e01zcLedger) ;
    }
