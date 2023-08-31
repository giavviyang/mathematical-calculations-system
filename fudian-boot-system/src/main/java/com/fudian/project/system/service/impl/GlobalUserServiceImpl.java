package com.fudian.project.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.project.system.mapper.GlobalUserMapper;
import com.fudian.project.system.pojo.GlobalUser;
import com.fudian.project.system.service.GlobalUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalUserServiceImpl extends ServiceImpl<GlobalUserMapper, GlobalUser> implements GlobalUserService {

    @Override
    public int updateBatch(List<GlobalUser> list) {
        return baseMapper.updateBatch(list);
    }
    @Override
    public int batchInsert(List<GlobalUser> list) {
        return baseMapper.batchInsert(list);
    }
    @Override
    public int insertOrUpdate(GlobalUser record) {
        return baseMapper.insertOrUpdate(record);
    }
    @Override
    public int insertOrUpdateSelective(GlobalUser record) {
        return baseMapper.insertOrUpdateSelective(record);
    }
}
