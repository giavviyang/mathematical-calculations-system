package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.DeptKnowledgeRelation;
import com.fudian.business.vo.QuestionSettingVo;
import com.fudian.common.pojo.CommonGridResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 班级知识点关联表
 * 作用：判断班级知识点训练出题方式是否随机
 *
 * @author xuchenghong
 */
public interface DeptKnowledgeRelationService extends IService<DeptKnowledgeRelation> {
    /**
     * 知识点出题设置分页查询功能
     * @param questionSettingVo
     * @return
     */
    CommonGridResult pagequeryDeptKnowledge(QuestionSettingVo questionSettingVo);
    /**
     * 判断当前年级是否随机生成习题 0 随机，1 统一
     * @param classId
     * @return
     */
    int queryDeptSetTopicMode(String classId,String classificationId);
}
