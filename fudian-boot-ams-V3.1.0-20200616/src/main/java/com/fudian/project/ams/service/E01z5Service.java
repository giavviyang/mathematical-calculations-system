package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.project.ams.pojo.E01z5;
import com.fudian.project.ams.pojo.E01z5Example;

import java.util.List;
import java.util.Map;

public interface E01z5Service extends IService<E01z5>{


    long countByExample(E01z5Example example);

    int deleteByExample(E01z5Example example);

    List<E01z5> selectByExample(E01z5Example example);

    int updateByExampleSelective(E01z5 record, E01z5Example example);

    int updateByExample(E01z5 record, E01z5Example example);

    int updateBatch(List<E01z5> list);

    int batchInsert(List<E01z5> list);

    int insertOrUpdate(E01z5 record);

    int insertOrUpdateSelective(E01z5 record);

    int deleteE01z5s(String e01z5Ids);

    /**
     * 分页查询档案接收记录
     * @param e01z5
     * @return
     */
    CommonGridResult queryE01z5List(E01z5 e01z5);

    /**
     * 转入统计
     * @return
     */
    List<Map<String,Object>> summaryE01z5();

}
