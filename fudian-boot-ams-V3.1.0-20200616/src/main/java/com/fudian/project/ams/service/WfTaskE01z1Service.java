package com.fudian.project.ams.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.project.ams.pojo.WfTaskE01z1;

import java.util.List;
import java.util.Map;

public interface WfTaskE01z1Service extends IService<WfTaskE01z1> {



    int updateBatch(List<WfTaskE01z1> list);

    int batchInsert(List<WfTaskE01z1> list);

    int insertOrUpdate(WfTaskE01z1 record);

    int insertOrUpdateSelective(WfTaskE01z1 record);

    /**
     * 根据ID 查询材料
     *
     * @param e01z1Id
     * @return
     */
    Map<String, String> selectE01z1ViewById(String e01z1Id);

    boolean deleteTaskE01z1ByTaskId(String taskId, String e01z1Id);

    int updateBatchSelective(List<WfTaskE01z1> list);

    int updateStatus(List<String> list,String taskId, String status);
}

