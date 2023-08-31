package com.fudian.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.LevelKnowledgeMapper;
import com.fudian.business.mapper.LevelTableMapper;
import com.fudian.business.pojo.LevelKnowledge;
import com.fudian.business.pojo.LevelTable;
import com.fudian.business.service.LevelKnowledgeService;
import com.fudian.business.service.LevelTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 关卡知识点关联Service业务层处理
 *
 * @author WangHao
 * @date 2022-01-01
 */
@Service
public class LevelKnowledgeServiceImpl extends ServiceImpl<LevelKnowledgeMapper, LevelKnowledge> implements LevelKnowledgeService
{
    @Autowired
    private LevelKnowledgeMapper levelKnowledgeMapper;

    /**
     * 查询关卡知识点关联
     *
     * @param levelId 关卡知识点关联主键
     * @return 关卡知识点关联
     */
    @Override
    public List<LevelKnowledge> selectLevelKnowledgeByLevelId(String levelId)
    {
        return levelKnowledgeMapper.selectLevelKnowledgeByLevelId(levelId);
    }

    /**
     * 查询关卡知识点关联列表
     *
     * @param levelKnowledge 关卡知识点关联
     * @return 关卡知识点关联
     */
    @Override
    public List<LevelKnowledge> selectLevelKnowledgeList(LevelKnowledge levelKnowledge)
    {
        return levelKnowledgeMapper.selectLevelKnowledgeList(levelKnowledge);
    }

    /**
     * 新增关卡知识点关联
     *
     * @param levelKnowledge 关卡知识点关联
     * @return 结果
     */
    @Override
    public int insertLevelKnowledge(LevelKnowledge levelKnowledge)
    {
        return levelKnowledgeMapper.insertLevelKnowledge(levelKnowledge);
    }

    /**
     * 修改关卡知识点关联
     *
     * @param levelKnowledge 关卡知识点关联
     * @return 结果
     */
    @Override
    public int updateLevelKnowledge(LevelKnowledge levelKnowledge)
    {
        return levelKnowledgeMapper.updateLevelKnowledge(levelKnowledge);
    }

    /**
     * 批量删除关卡知识点关联
     *
     * @param levelIds 需要删除的关卡知识点关联主键
     * @return 结果
     */
    @Override
    public int deleteLevelKnowledgeByLevelIds(String[] levelIds)
    {
        return levelKnowledgeMapper.deleteLevelKnowledgeByLevelIds(levelIds);
    }

    /**
     * 删除关卡知识点关联信息
     *
     * @param levelId 关卡知识点关联主键
     * @return 结果
     */
    @Override
    public int deleteLevelKnowledgeByLevelId(String levelId)
    {
        return levelKnowledgeMapper.deleteLevelKnowledgeByLevelId(levelId);
    }
}
