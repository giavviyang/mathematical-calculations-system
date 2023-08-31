package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.KnowledgeListMapper;
import com.fudian.business.pojo.KnowledgeList;
import com.fudian.business.pojo.KnowledgeListClassification;
import com.fudian.business.service.KnowledgeListService;
import com.fudian.common.pojo.CommonGridResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @author xuchenghong
 */

@Service
public class KnowledgeListServiceImpl extends ServiceImpl<KnowledgeListMapper, KnowledgeList> implements KnowledgeListService {

    @Resource
    private KnowledgeListMapper knowledgeListMapper;

    /**
     * 分页查询知识类型表
     *
     * @param knowledgeList
     * @return
     */
    @Override
    public CommonGridResult pagingSelectKnowledgeList(KnowledgeList knowledgeList){
        LambdaQueryWrapper<KnowledgeList> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(knowledgeList.getPageNum(),knowledgeList.getPageSize());
        List<KnowledgeList> knowledgeLists = knowledgeListMapper.selectList(queryWrapper);

        Page page = (Page)knowledgeLists;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(knowledgeList.getPageNum(), knowledgeList.getPageSize(), total, knowledgeLists);
        return commonGridResult;
    }

    /**
     * 查询知识类型表
     *
     * @return
     */
    @Override
    public List<KnowledgeList> selectKnowledgeList(){
        LambdaQueryWrapper<KnowledgeList> queryWrapper = new LambdaQueryWrapper<>();

        List<KnowledgeList> knowledgeLists = knowledgeListMapper.selectList(queryWrapper);
        return knowledgeLists;
    }
    /**
     * 查询知识类型表
     *
     * @return
     */
    @Override
    public List<KnowledgeList> selectKnowledgeList(KnowledgeList knowledgeList){
        LambdaQueryWrapper<KnowledgeList> queryWrapper = new LambdaQueryWrapper<>();

        if (knowledgeList.getKnowledgeNumField() != null){
            queryWrapper.eq(KnowledgeList::getKnowledgeNumField,knowledgeList.getKnowledgeNumField());
        }
        if (knowledgeList.getKnowledgeOperation() != null){
            queryWrapper.eq(KnowledgeList::getKnowledgeOperation,knowledgeList.getKnowledgeOperation());
        }
        if (knowledgeList.getKnowledgeType() != null){
            queryWrapper.eq(KnowledgeList::getKnowledgeType,knowledgeList.getKnowledgeType());
        }

        List<KnowledgeList> knowledgeLists = knowledgeListMapper.selectList(queryWrapper);
        return knowledgeLists;
    }

    /**
     * 添加知识类型表
     *
     * @param knowledgeList
     * @return
     */
    @Override
    public int insertKnowledgeList(KnowledgeList knowledgeList){
        String uuid = UUID.randomUUID().toString();
        knowledgeList.setKnowledgeId(uuid);
        int insert = knowledgeListMapper.insert(knowledgeList);
        return insert;
    }

    /**
     * 修改知识类型表
     *
     * @param knowledgeList
     * @return
     */
    @Override
    public int updateKnowledgeListById(KnowledgeList knowledgeList){

        int i = knowledgeListMapper.updateById(knowledgeList);
        return i;
    }

    /**
     * 批量删除知识类型表
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteKnowledgeList(List<String> ids){
        int insert = knowledgeListMapper.deleteBatchIds(ids);
        return insert;
    }

    /**
     * 获取数域 、运算符 、进位类型
     *
     * @param type
     * @return
     */
    @Override
    public List<KnowledgeList> selectType(KnowledgeList type){

        List<KnowledgeList> list = null;

        LambdaQueryWrapper<KnowledgeList> queryWrapper = new LambdaQueryWrapper<>();


        if (type == null || type.getKnowledgeNumField() == null){
            queryWrapper.groupBy(KnowledgeList::getKnowledgeNumField);

            list = knowledgeListMapper.selectList(queryWrapper);
            return list;
        }
        if (type.getKnowledgeNumField() != null && type.getKnowledgeOperation() == null){
            queryWrapper.eq(KnowledgeList::getKnowledgeNumField,type.getKnowledgeNumField());
            queryWrapper.groupBy(KnowledgeList::getKnowledgeOperation);

            list = knowledgeListMapper.selectList(queryWrapper);
            return list;
        }
        if (type.getKnowledgeOperation() != null && type.getKnowledgeType() == null){
            queryWrapper.eq(KnowledgeList::getKnowledgeNumField,type.getKnowledgeNumField());
            queryWrapper.eq(KnowledgeList::getKnowledgeOperation,type.getKnowledgeOperation());

            list = knowledgeListMapper.selectList(queryWrapper);
            return list;
        }
        return list;
    }
}
