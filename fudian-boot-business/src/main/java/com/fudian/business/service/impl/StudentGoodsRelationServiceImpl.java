package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.StudentGoodsRelationMapper;
import com.fudian.business.pojo.StudentGoodsRelation;
import com.fudian.business.service.StudentGoodsRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 学校
 *
 * @author xuchenghong
 */
@Service
public class StudentGoodsRelationServiceImpl extends ServiceImpl<StudentGoodsRelationMapper, StudentGoodsRelation> implements StudentGoodsRelationService {

    @Resource
    private StudentGoodsRelationMapper studentGoodsRelationMapper;

    /**
     * 根据 学生id  查询学生所拥有的商品id
     * @param id
     * @return
     */
    @Override
    public List<String> selectByStudentId(String id){
        LambdaQueryWrapper<StudentGoodsRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentGoodsRelation::getStudentId,id);
        List<StudentGoodsRelation> goodsRelationList = studentGoodsRelationMapper.selectList(queryWrapper);
        List<String> list = new ArrayList<>();
        for (StudentGoodsRelation studentGoodsRelation : goodsRelationList) {
            String goodsId = studentGoodsRelation.getGoodsId();
            list.add(goodsId);
        }
        return list;
    }


    /**
     * 新增学生商品关联数据
     * @param studentGoodsRelation
     * @return
     */
    @Override
    public int insertStudentGoodsRelation(StudentGoodsRelation studentGoodsRelation){
        int insert = studentGoodsRelationMapper.insert(studentGoodsRelation);
        return insert;
    }
}
