package com.fudian.project.ams.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.fudian.api.ams.pojo.E01z1;
import com.fudian.api.system.pojo.LoginUser;
import com.fudian.api.system.service.TokenApi;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.ams.mapper.E01z1Mapper;
import com.fudian.project.ams.mapper.WfTaskMapper;
import com.fudian.project.ams.pojo.*;
import com.fudian.project.ams.service.E01z1Service;
import com.fudian.project.ams.service.WfTaskE01z1Service;
import com.fudian.project.ams.service.WfTaskService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WfTaskServiceImpl extends ServiceImpl<WfTaskMapper, WfTask> implements WfTaskService {

    @Autowired
    private TokenApi tokenService;

    @Autowired
    WfTaskE01z1Service wfTaskE01z1Service;

    @Autowired
    WfTaskMapper wfTaskMapper;

    @Autowired
    E01z1Mapper e01z1Mapper;

    @Autowired
    E01z1Service e01z1Service;

    @Override
    public long countByExample(WfTaskExample example) {
        return baseMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(WfTaskExample example) {
        return baseMapper.deleteByExample(example);
    }

    @Override
    public List<WfTask> selectByExample(WfTaskExample example) {
        return baseMapper.selectByExample(example);
    }

    @Override
    public int updateByExampleSelective(WfTask record, WfTaskExample example) {
        return baseMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(WfTask record, WfTaskExample example) {
        return baseMapper.updateByExample(record, example);
    }

    @Override
    public int updateBatch(List<WfTask> list) {
        return baseMapper.updateBatch(list);
    }

    @Override
    public int batchInsert(List<WfTask> list) {
        return baseMapper.batchInsert(list);
    }

    @Override
    public int insertOrUpdate(WfTask record) {
        return baseMapper.insertOrUpdate(record);
    }

    @Override
    public int insertOrUpdateSelective(WfTask record) {
        return baseMapper.insertOrUpdateSelective(record);
    }



    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/26 11:39
    * @Return: 查询任务列表
    * @Param:
    */
    @Override
    public Page queryWfTask(WfTask wfTask) {
        PageHelper.startPage(wfTask.getPageNum(),wfTask.getPageSize());
//        LambdaQueryWrapper<WfTask> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        if(StringUtils.isNotEmpty(wfTask.getCreateName())){
//            lambdaQueryWrapper.like(WfTask::getCreateName,wfTask.getCreateName());
//        }
//        if(wfTask.getBeginTime() != null){
//            lambdaQueryWrapper.ge(WfTask::getCreateTime,wfTask.getBeginTime());
//        }
//        if(wfTask.getEndTime() != null){
//            lambdaQueryWrapper.le(WfTask::getCreateTime,wfTask.getEndTime());
//        }
//        if(wfTask.getStatus() != null){
//            lambdaQueryWrapper.eq(WfTask::getStatus,wfTask.getStatus());
//        }
//        if(wfTask.getDigitizationType() != null){
//            lambdaQueryWrapper.eq(WfTask::getDigitizationType,wfTask.getDigitizationType());
//        }
//
//        if(wfTask.getTaskType() != null){
//            lambdaQueryWrapper.eq(WfTask::getTaskType,wfTask.getTaskType());
//        }
//        lambdaQueryWrapper.orderByAsc(WfTask::getCreateTime);
        List<WfTaskAndA01Vo> wfTaskList = this.baseMapper.quertTaskList(wfTask);
        Page page = (Page)wfTaskList;
        return page;
    }


    /**
    * @Author: wangyuzhuo
    * @Date: 2020/3/26 11:38
    * @Return: 删除任务
    * @Param:
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTaskByIds(String ids) {
        String id[] = ids.split(",");
        boolean b = this.removeByIds(Arrays.asList(id));
        //删除子表
        LambdaQueryWrapper<WfTaskE01z1> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(WfTaskE01z1::getTaskId,id);
        b = wfTaskE01z1Service.remove(lambdaQueryWrapper);
        return b;
    }




    @Override
    public int updateBatchSelective(List<WfTask> list) {
        return 0;
    }

    /**
     * 修改材料页数
     * @param taskId
     * @param id
     * @param e01z114
     * @return
     */
    @Override
    @Transactional
    public boolean updateE01z1114ByE01z1Id(String taskId, String id,Integer e01z114) {
        E01z1 e = e01z1Service.selectById(id);
        if (e == null || e01z114 == null ||  e01z114 < 0) {
            return false;
        }
        int i = e01z114 - e.getE01z114();
        e.setE01z114(e01z114);
        boolean b = e01z1Service.updateById(e);

        WfTask wfTask = wfTaskMapper.selectById(taskId);
        if (wfTask.getPageNum() != null) {
            wfTask.setPageNum(wfTask.getPageNum() + i);
            wfTaskMapper.updateById(wfTask);
        }

        return b;
    }

    /**
     * 修改处理材料处理状态
     * @param params
     * @return
     */
    @Override
    @Transactional
    public boolean updateStatus(String params) {

        JSONArray jsonArray = JSONArray.parseArray(params);
        //修改状态
        String taskId = jsonArray.getJSONObject(0).getString("taskId");

        //判断数量
        LambdaQueryWrapper<WfTaskE01z1> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WfTaskE01z1::getTaskId,taskId);
        wrapper.eq(WfTaskE01z1::getStatus,"0");
        //还未成功的
        int count = wfTaskE01z1Service.count(wrapper);
        //失败的
        int count1 = jsonArray.size();
        if (count == count1) {
            WfTask wfTask = new WfTask();
            wfTask.setId(taskId);
            wfTask.setStatus(2);
            wfTaskMapper.updateById(wfTask);
        }

        List<String> list = new ArrayList<>();
        for (int i=0; i<jsonArray.size();i++) {
            list.add(jsonArray.getJSONObject(i).getString("id"));
        }
        int i = wfTaskE01z1Service.updateStatus(list,taskId,"1");

        return SqlHelper.retBool(i);
    }
}

