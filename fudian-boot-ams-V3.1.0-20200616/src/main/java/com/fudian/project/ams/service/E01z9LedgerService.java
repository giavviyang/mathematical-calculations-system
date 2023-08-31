package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.E01z9;
import com.fudian.project.ams.pojo.E01z9Ledger;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/3/13 10:32
 * @Desc:
 */
public interface E01z9LedgerService extends IService<E01z9Ledger> {

    CommonGridResult queryE01z9LedgerService(E01z9Ledger e01z9Ledger, Integer pageNum, Integer pageSize);

    boolean updateE01z9AndRela(E01z9 e01z9);

    boolean InsertE01z9AndRela(E01z9 e01z9);

    CommonResult updateByEntityAndList(MultipartFile files, Map<String, Object> data);

    int updateBatch(List<E01z9Ledger> list);

    int batchInsert(List<E01z9Ledger> list);

    int insertOrUpdate(E01z9Ledger record);

    int insertOrUpdateSelective(E01z9Ledger record);
}

