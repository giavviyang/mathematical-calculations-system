package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.StudentKnowledgeRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author xuchenghong
 */

@Mapper
public interface StudentKnowledgeRelationMapper extends BaseMapper<StudentKnowledgeRelation> {

    /**
     * 查询知识点错题本锁定数据
     * @param unlock
     * @return
     */
    List<StudentKnowledgeRelation> pagingSelect(@Param("unlock") StudentKnowledgeRelation unlock);
    /**
     * 查询知识点错题本锁定数据总条数
     * @param unlock
     * @return
     */
    Integer pagingSelectCount(@Param("unlock") StudentKnowledgeRelation unlock);
    /**
     * 查询闯关锁定数据
     * @param unlock
     * @return
     */
    List<StudentKnowledgeRelation> pagingSelectLevel(@Param("unlock") StudentKnowledgeRelation unlock);
    /**
     * 查询知识点错题本锁定数据总条数
     * @param unlock
     * @return
     */
    Integer pagingSelectLevelCount(@Param("unlock") StudentKnowledgeRelation unlock);
}
