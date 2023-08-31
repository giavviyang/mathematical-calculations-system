package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.KnowledgeList;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;


/**
 * @author xuchenghong
 */

public interface KnowledgeListService extends IService<KnowledgeList> {

    /**
     * 分页查询知识类型表
     *
     * @param knowledgeList
     * @return
     */
    CommonGridResult pagingSelectKnowledgeList(KnowledgeList knowledgeList);

    /**
     * 查询知识类型表
     *
     * @return
     */
    List<KnowledgeList> selectKnowledgeList();

    /**
     * 查询知识类型表
     *
     * @return
     */
    List<KnowledgeList> selectKnowledgeList(KnowledgeList knowledgeList);

    /**
     * 添加知识类型表
     *
     * @param knowledgeList
     * @return
     */
    int insertKnowledgeList(KnowledgeList knowledgeList);

    /**
     * 修改知识类型表
     *
     * @param knowledgeList
     * @return
     */
    int updateKnowledgeListById(KnowledgeList knowledgeList);

    /**
     * 批量删除知识类型表
     *
     * @param ids
     * @return
     */
    int deleteKnowledgeList(List<String> ids);

    /**
     * 获取数域 0、运算符 1、进位类型 2
     *
     * @param type
     * @return
     */
    List<KnowledgeList> selectType(KnowledgeList type);

}
