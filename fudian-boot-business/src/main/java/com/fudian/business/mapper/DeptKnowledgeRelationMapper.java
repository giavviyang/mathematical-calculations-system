package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.DeptKnowledgeRelation;
import com.fudian.business.vo.QuestionSettingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 班级知识点关联表
 * 作用：判断班级知识点训练出题方式是否随机
 * @author xuchenghong
 */
@Mapper
public interface DeptKnowledgeRelationMapper extends BaseMapper<DeptKnowledgeRelation> {

    List<QuestionSettingVo> pageSelectDeptKnowledge(@Param("questionSettingVo") QuestionSettingVo questionSettingVo);
    /**
     * 判断当前年级是否随机生成习题 0 随机，1 统一
     * @param classId
     * @param classificationId
     * @return
     */
    Integer selectDeptSetTopicMode(@Param("classId") String classId,@Param("classificationId")String classificationId);
}
