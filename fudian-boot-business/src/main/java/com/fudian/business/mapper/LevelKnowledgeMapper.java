package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.LevelKnowledge;
import com.fudian.business.pojo.LevelTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 关卡知识点关联Mapper接口
 *
 * @author WangHao
 * @date 2022-01-01
 */
@Mapper
public interface LevelKnowledgeMapper  extends BaseMapper<LevelKnowledge>
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
     * 删除关卡知识点关联
     *
     * @param levelId 关卡知识点关联主键
     * @return 结果
     */
    public int deleteLevelKnowledgeByLevelId(String levelId);

    /**
     * 批量删除关卡知识点关联
     *
     * @param levelIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLevelKnowledgeByLevelIds(String[] levelIds);
}
