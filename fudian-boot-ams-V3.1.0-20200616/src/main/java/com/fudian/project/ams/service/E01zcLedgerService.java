package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.pojo.E01zc;
import com.fudian.project.ams.pojo.E01zcLedger;

/**
* @Author: WangYuZhuo
* @Date: 2020/3/13 10:32
* @Desc:
*/
public interface E01zcLedgerService extends IService<E01zcLedger>{

    CommonGridResult queryE01zcLedgerService(E01zcLedger e01zcLedger, Integer pageNum, Integer pageSize);

    boolean updateE01zcAndRela(E01zc e01zc);

    boolean InsertE01zcAndRela(E01zc e01zc);


}
