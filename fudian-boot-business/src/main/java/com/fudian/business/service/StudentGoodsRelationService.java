package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.StudentGoodsRelation;

import java.util.List;

/**
 * 学校
 *
 * @author xuchenghong
 */
public interface StudentGoodsRelationService extends IService<StudentGoodsRelation> {

    /**
     * 查询学生所拥有的商品id
     * @param id
     * @return
     */
    List<String> selectByStudentId(String id);
    /**
     * 新增学生商品关联数据
     * @param studentGoodsRelation
     * @return
     */
    int insertStudentGoodsRelation(StudentGoodsRelation studentGoodsRelation);
}
