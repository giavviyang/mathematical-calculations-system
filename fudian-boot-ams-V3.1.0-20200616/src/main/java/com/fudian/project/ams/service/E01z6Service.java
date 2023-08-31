package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.project.ams.pojo.E01z6;
import com.fudian.project.ams.pojo.E01z6Example;

import java.util.List;

public interface E01z6Service extends IService<E01z6>{


    long countByExample(E01z6Example example);

    int deleteByExample(E01z6Example example);

    List<E01z6> selectByExample(E01z6Example example);

    int updateByExampleSelective(E01z6 record, E01z6Example example);

    int updateByExample(E01z6 record, E01z6Example example);

    int updateBatch(List<E01z6> list);

    int batchInsert(List<E01z6> list);

    int insertOrUpdate(E01z6 record);

    int insertOrUpdateSelective(E01z6 record);

}
