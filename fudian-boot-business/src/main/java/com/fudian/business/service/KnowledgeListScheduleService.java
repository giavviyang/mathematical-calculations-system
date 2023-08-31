package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.KnowledgeListSchedule;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;


/**
 * @author xuchenghong
 */

public interface KnowledgeListScheduleService extends IService<KnowledgeListSchedule> {
    /**
     * 分页查询诊断列表
     * @param knowledgeListSchedule
     * @return
     */
    CommonGridResult pagingSelectKnowledgeListSchedule(KnowledgeListSchedule knowledgeListSchedule);
    /**
     * 查询诊断列表
     * @param knowledgeListSchedule
     * @return
     */
    List<KnowledgeListSchedule> selectKnowledgeListSchedule(KnowledgeListSchedule knowledgeListSchedule);
    /**
     * 添加诊断列表
     * @param knowledgeListSchedule
     * @return
     */
    int insertKnowledgeListSchedule(KnowledgeListSchedule knowledgeListSchedule);
    /**
     * 修改诊断列表
     * @param knowledgeListSchedule
     * @return
     */
    int updateKnowledgeListScheduleById(KnowledgeListSchedule knowledgeListSchedule);
    /**
     * 批量删除诊断列表
     * @param ids
     * @return
     */
    int deleteKnowledgeListSchedule(List<String> ids);
}
