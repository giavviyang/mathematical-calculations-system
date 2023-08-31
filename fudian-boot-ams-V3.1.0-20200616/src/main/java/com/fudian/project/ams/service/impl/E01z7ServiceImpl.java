package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.project.ams.mapper.E01z7Mapper;
import com.fudian.project.ams.pojo.E01z7;
import com.fudian.project.ams.pojo.E01z7Example;
import com.fudian.project.ams.service.E01z7Service;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class E01z7ServiceImpl extends ServiceImpl<E01z7Mapper, E01z7> implements E01z7Service {

    @Override
    public long countByExample(E01z7Example example) {
        return baseMapper.countByExample(example);
    }
    @Override
    public int deleteByExample(E01z7Example example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public List<E01z7> selectByExample(E01z7Example example) {
        return baseMapper.selectByExample(example);
    }
    @Override
    public int updateByExampleSelective(E01z7 record, E01z7Example example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Override
    public int updateByExample(E01z7 record, E01z7Example example) {
        return baseMapper.updateByExample(record,example);
    }
    @Override
    public int updateBatch(List<E01z7> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<E01z7> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(E01z7 record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(E01z7 record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
