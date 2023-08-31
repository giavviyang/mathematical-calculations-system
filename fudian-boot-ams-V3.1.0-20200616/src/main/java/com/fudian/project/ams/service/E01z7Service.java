package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.project.ams.pojo.E01z7;
import com.fudian.project.ams.pojo.E01z7Example;

import java.util.List;

public interface E01z7Service extends IService<E01z7>{


    long countByExample(E01z7Example example);

    int deleteByExample(E01z7Example example);

    List<E01z7> selectByExample(E01z7Example example);

    int updateByExampleSelective(E01z7 record, E01z7Example example);

    int updateByExample(E01z7 record, E01z7Example example);

    int updateBatch(List<E01z7> list);

    int batchInsert(List<E01z7> list);

    int insertOrUpdate(E01z7 record);

    int insertOrUpdateSelective(E01z7 record);

}
