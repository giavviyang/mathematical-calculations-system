package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.LevelKnowledge;
import com.fudian.business.pojo.LevelTable;

import java.util.List;

/**
 * 关卡知识点关联Service接口
 *
 * @author WangHao
 * @date 2022-01-01
 */
public interface LevelKnowledgeService extends IService<LevelKnowledge>
{
    /**
     * 查询关卡知识点关联
     *
     * @param levelId 关卡知识点关联主键
     * @return 关卡知识点关联
     */
    public List<LevelKnowledge> selectLevelKnowledgeByLevelId(String levelId);

    /**
     * 查询关卡知识点关联列表
     *
     * @param levelKnowledge 关卡知识点关联
     * @return 关卡知识点关联集合
     */
    public List<LevelKnowledge> selectLevelKnowledgeList(LevelKnowledge levelKnowledge);

    /**
     * 新增关卡知识点关联
     *
     * @param levelKnowledge 关卡知识点关联
     * @return 结果
     */
    public int insertLevelKnowledge(LevelKnowledge levelKnowledge);

    /**
     * 修改关卡知识点关联
     *
     * @param levelKnowledge 关卡知识点关联
     * @return 结果
     */
    public int updateLevelKnowledge(LevelKnowledge levelKnowledge);

    /**
     * 批量删除关卡知识点关联
     *
     * @param levelIds 需要删除的关卡知识点关联主键集合
     * @return 结果
     */
    public int deleteLevelKnowledgeByLevelIds(String[] levelIds);

    /**
     * 删除关卡知识点关联信息
     *
     * @param levelId 关卡知识点关联主键
     * @return 结果
     */
    public int deleteLevelKnowledgeByLevelId(String levelId);
}
