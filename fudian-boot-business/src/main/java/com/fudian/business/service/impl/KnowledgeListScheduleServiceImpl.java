package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.KnowledgeListScheduleMapper;
import com.fudian.business.pojo.KnowledgeListSchedule;
import com.fudian.business.service.KnowledgeListScheduleService;
import com.fudian.common.pojo.CommonGridResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


/**
 * @author xuchenghong
 */

@Service
public class KnowledgeListScheduleServiceImpl extends ServiceImpl<KnowledgeListScheduleMapper, KnowledgeListSchedule> implements KnowledgeListScheduleService {
    @Resource
    private KnowledgeListScheduleMapper knowledgeListScheduleMapper;

    /**
     * 分页查询诊断列表
     *
     * @param knowledgeListSchedule
     * @return
     */
    @Override
    public CommonGridResult pagingSelectKnowledgeListSchedule(KnowledgeListSchedule knowledgeListSchedule){
        LambdaQueryWrapper<KnowledgeListSchedule> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(knowledgeListSchedule.getPageNum(),knowledgeListSchedule.getPageSize());
        List<KnowledgeListSchedule> knowledgeListSchedules = knowledgeListScheduleMapper.selectList(queryWrapper);

        Page page = (Page)knowledgeListSchedules;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(knowledgeListSchedule.getPageNum(), knowledgeListSchedule.getPageSize(), total, knowledgeListSchedules);
        return commonGridResult;
    }

    /**
     * 查询诊断列表
     *
     * @param knowledgeListSchedule
     * @return
     */
    @Override
    public List<KnowledgeListSchedule> selectKnowledgeListSchedule(KnowledgeListSchedule knowledgeListSchedule){
        LambdaQueryWrapper<KnowledgeListSchedule> queryWrapper = new LambdaQueryWrapper<>();

        List<KnowledgeListSchedule> knowledgeListSchedules = knowledgeListScheduleMapper.selectList(queryWrapper);
        return knowledgeListSchedules;
    }

    /**
     * 添加诊断列表
     *
     * @param knowledgeListSchedule
     * @return
     */
    @Override
    public int insertKnowledgeListSchedule(KnowledgeListSchedule knowledgeListSchedule){
        String uuid = UUID.randomUUID().toString();
        knowledgeListSchedule.setDiagnosisId(uuid);
        int insert = knowledgeListScheduleMapper.insert(knowledgeListSchedule);
        return insert;
    }

    /**
     * 修改诊断列表
     *
     * @param knowledgeListSchedule
     * @return
     */
    @Override
    public int updateKnowledgeListScheduleById(KnowledgeListSchedule knowledgeListSchedule){

        int i = knowledgeListScheduleMapper.updateById(knowledgeListSchedule);
        return i;
    }

    /**
     * 批量删除诊断列表
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteKnowledgeListSchedule(List<String> ids){
        int insert = knowledgeListScheduleMapper.deleteBatchIds(ids);
        return insert;
    }
}
