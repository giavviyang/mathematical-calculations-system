package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.ams.pojo.WfTask;
import com.fudian.project.ams.pojo.WfTaskExample;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface WfTaskService extends IService<WfTask> {


    long countByExample(WfTaskExample example);

    int deleteByExample(WfTaskExample example);

    List<WfTask> selectByExample(WfTaskExample example);

    int updateByExampleSelective(WfTask record, WfTaskExample example);

    int updateByExample(WfTask record, WfTaskExample example);

    int updateBatch(List<WfTask> list);

    int batchInsert(List<WfTask> list);

    int insertOrUpdate(WfTask record);

    int insertOrUpdateSelective(WfTask record);


    Page queryWfTask(WfTask wfTask);


    boolean deleteTaskByIds(String ids);



    int updateBatchSelective(List<WfTask> list);

    boolean updateE01z1114ByE01z1Id(String taskId, String id,Integer e01z114);

    boolean updateStatus(String params);
}


