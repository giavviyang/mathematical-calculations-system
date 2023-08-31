package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.project.ams.pojo.E01za;
import com.fudian.project.ams.pojo.E01zaExample;

import java.util.List;

public interface E01zaService extends IService<E01za>{


    long countByExample(E01zaExample example);

    int deleteByExample(E01zaExample example);

    List<E01za> selectByExample(E01zaExample example);

    int updateByExampleSelective(E01za record, E01zaExample example);

    int updateByExample(E01za record, E01zaExample example);

    int updateBatch(List<E01za> list);

    int batchInsert(List<E01za> list);

    int insertOrUpdate(E01za record);

    int insertOrUpdateSelective(E01za record);

}
