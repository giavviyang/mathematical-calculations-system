package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.DeptKnowledgeRelationMapper;
import com.fudian.business.pojo.DeptKnowledgeRelation;
import com.fudian.business.service.DeptKnowledgeRelationService;
import com.fudian.business.vo.QuestionSettingVo;
import com.fudian.common.pojo.CommonGridResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班级知识点关联表
 * 作用：判断班级知识点训练出题方式是否随机
 *
 * @author xuchenghong
 */

@Service
public class DeptKnowledgeRelationServiceImpl extends ServiceImpl<DeptKnowledgeRelationMapper, DeptKnowledgeRelation> implements DeptKnowledgeRelationService {

    /**
     * 知识点出题设置分页查询功能
     * @param questionSettingVo
     * @return
     */
    @Override
    public CommonGridResult pagequeryDeptKnowledge(QuestionSettingVo questionSettingVo) {
        PageHelper.startPage(questionSettingVo.getPageNum(), questionSettingVo.getPageSize());
        List<QuestionSettingVo> list = baseMapper.pageSelectDeptKnowledge(questionSettingVo);
        Page page = (Page) list;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(questionSettingVo.getPageNum(), questionSettingVo.getPageSize(), total, list);
        return commonGridResult;
    }
    /**
     * 判断当前年级是否随机生成习题 0 随机，1 统一
     * @param classId
     * @param classificationId
     * @return
     */
    @Override
    public int queryDeptSetTopicMode(String classId,String classificationId) {
        Integer integer = baseMapper.selectDeptSetTopicMode(classId, classificationId);
        return integer==null?0:integer;
    }
}
