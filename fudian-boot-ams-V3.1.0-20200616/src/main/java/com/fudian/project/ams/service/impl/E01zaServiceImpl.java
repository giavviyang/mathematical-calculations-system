package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.project.ams.mapper.E01zaMapper;
import com.fudian.project.ams.pojo.E01za;
import com.fudian.project.ams.pojo.E01zaExample;
import com.fudian.project.ams.service.E01zaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class E01zaServiceImpl extends ServiceImpl<E01zaMapper, E01za> implements E01zaService {

    @Override
    public long countByExample(E01zaExample example) {
        return baseMapper.countByExample(example);
    }
    @Override
    public int deleteByExample(E01zaExample example) {
        return baseMapper.deleteByExample(example);
    }
    @Override
    public List<E01za> selectByExample(E01zaExample example) {
        return baseMapper.selectByExample(example);
    }
    @Override
    public int updateByExampleSelective(E01za record, E01zaExample example) {
        return baseMapper.updateByExampleSelective(record,example);
    }
    @Override
    public int updateByExample(E01za record, E01zaExample example) {
        return baseMapper.updateByExample(record,example);
    }
    @Override
    public int updateBatch(List<E01za> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<E01za> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(E01za record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(E01za record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
