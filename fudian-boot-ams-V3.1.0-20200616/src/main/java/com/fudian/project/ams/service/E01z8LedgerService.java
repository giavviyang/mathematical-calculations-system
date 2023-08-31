package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.E01z8;
import com.fudian.project.ams.pojo.E01z8Ledger;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/13 10:28
 * @Desc:
 */
public interface E01z8LedgerService extends IService<E01z8Ledger> {

    CommonGridResult queryE01z8LedgerService(E01z8Ledger e01z8Ledger, Integer pageNum, Integer pageSize);

    boolean updateE01z8AndRela(E01z8 e01z8);

    boolean InsertE01z8AndRela(E01z8 e01z8);

    CommonResult updateByEntityAndList(MultipartFile files, Map<String, Object> data);

    int updateBatch(List<E01z8Ledger> list);

    int batchInsert(List<E01z8Ledger> list);

    int insertOrUpdate(E01z8Ledger record);

    int insertOrUpdateSelective(E01z8Ledger record);
}


