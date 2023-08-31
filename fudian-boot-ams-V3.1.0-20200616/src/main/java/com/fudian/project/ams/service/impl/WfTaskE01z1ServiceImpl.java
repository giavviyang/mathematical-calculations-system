package com.fudian.project.ams.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.project.ams.mapper.WfTaskE01z1Mapper;
import com.fudian.project.ams.pojo.WfTask;
import com.fudian.project.ams.pojo.WfTaskE01z1;
import com.fudian.project.ams.service.WfTaskE01z1Service;
import com.fudian.project.ams.service.WfTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WfTaskE01z1ServiceImpl extends ServiceImpl<WfTaskE01z1Mapper, WfTaskE01z1> implements WfTaskE01z1Service {

    @Autowired
    private WfTaskE01z1Mapper wfTaskE01z1Mapper;

    @Autowired
    private WfTaskService wfTaskService;

    @Override
    public int updateBatch(List<WfTaskE01z1> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<WfTaskE01z1> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(WfTaskE01z1 record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(WfTaskE01z1 record) {
        return baseMapper.insertOrUpdateSelective(record);
    }

    @Override
    public Map<String, String> selectE01z1ViewById(String e01z1Id) {
        return baseMapper.selectE01z1ViewById(e01z1Id);
    }

    @Override
    public boolean deleteTaskE01z1ByTaskId(String taskId, String e01z1Id) {
        LambdaQueryWrapper<WfTaskE01z1> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(WfTaskE01z1::getE01z1Id, e01z1Id);
        lambdaQueryWrapper.eq(WfTaskE01z1::getTaskId, taskId);

        //查询该任务下存不存在材料，如果不存在则删除该任务
        LambdaQueryWrapper<WfTaskE01z1> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(WfTaskE01z1::getTaskId, taskId);
        this.remove(lambdaQueryWrapper);
        //如果该任务中的详情为空则删除该任务
        List<WfTaskE01z1> list = this.list(lambdaQueryWrapper2);
        if(list.size() == 0){
            //删除任务
            wfTaskService.removeById(taskId);
        }

        return true;
    }

    @Override
    public int updateBatchSelective(List<WfTaskE01z1> list) {
        return baseMapper.updateBatchSelective(list);
    }

    @Override
    public int updateStatus(List<String> list,String taskId, String status) {
        return wfTaskE01z1Mapper.updateStatus(list,taskId,status);
    }
}

