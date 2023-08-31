package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.TestKnowledgeRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author xuchenghong TestKnowledgeRelation
 */

@Mapper
public interface TestKnowledgeRelationMapper extends BaseMapper<TestKnowledgeRelation> {


    /**
     * 批量添加
     * @author xuchenghong
     */
    int batchAdd(@Param("testId") String testId, @Param("knowledges") List<TestKnowledgeRelation> knowledges);


    /**
     * 批量删除根据testId
     * @param testIds
     * @return
     */
    int deleteByTestIds(@Param("testIds") List<String> testIds);

    /**
     * 删除根据testId
     * @param testId
     * @return
     */
    int deleteByTestId(@Param("testId") String testId);

}
