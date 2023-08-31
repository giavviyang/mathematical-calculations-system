package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.Students;
import com.fudian.business.pojo.StudentKnowledgeRelation;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;

/**
 * 学生知识点关联表
 * @author xuchenghong
 */
public interface StudentKnowledgeRelationService extends IService<StudentKnowledgeRelation> {

    /**
     * 分页查询学生关卡知识点关联表
     *
     * @param studentKnowledgeRelation
     * @return
     */
    CommonGridResult pagingSelectStudentLevelRelation(StudentKnowledgeRelation studentKnowledgeRelation);
    /**
     * 分页查询学生知识点关联表
     *
     * @param studentKnowledgeRelation
     * @return
     */
    CommonGridResult pagingSelectStudentKnowledgeRelation(StudentKnowledgeRelation studentKnowledgeRelation);

    /**
     * 查询学生知识点关联表
     *
     * @param knowledgeId
     * @return
     */
    StudentKnowledgeRelation selectStudentKnowledgeById(String knowledgeId,String studentId);
    /**
     * 查询掌握知识点数量
     *
     * @param
     * @return
     */
    int selectKnowledgeNum(String studentId,int type);

    /**
     * 添加学生知识点关联表
     *
     * @param studentKnowledgeRelation
     * @return
     */
    int insertStudentKnowledgeRelation(StudentKnowledgeRelation studentKnowledgeRelation);

    /**
     * 修改学生知识点关联表
     *
     * @param studentKnowledgeRelation
     * @return
     */
    int updateStudentKnowledgeRelationById(StudentKnowledgeRelation studentKnowledgeRelation);
    /**
     * 根据id批量修改锁定状态
     *
     * @param ids
     * @return
     */
    int updateUnlocks(List<String> ids);

    /**
     * 批量删除学生知识点关联表
     *
     * @param id
     * @return
     */
    int deleteStudentKnowledgeRelationById(String id);
    /**
     * 批量删除学生知识点关联表
     *
     * @param ids
     * @return
     */
    int deleteStudentKnowledgeRelationById(List<String> ids);

    /**
     * 查询学生知识点关联表
     * @param student
     * @param relationId
     * @param type
     * @return
     */
    StudentKnowledgeRelation selectStudentRelationBy(Students student, String relationId, int type);
}
