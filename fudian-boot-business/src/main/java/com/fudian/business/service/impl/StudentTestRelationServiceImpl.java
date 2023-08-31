package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.StudentTestRelationMapper;
import com.fudian.business.pojo.StudentTestRelation;
import com.fudian.business.service.StudentTestRelationService;
import com.fudian.common.utils.AppletUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author xuchenghong
 */

@Service
public class StudentTestRelationServiceImpl extends ServiceImpl<StudentTestRelationMapper, StudentTestRelation> implements StudentTestRelationService {

    /**
     * 批量删除根据testId
     * @param testIds
     * @return
     */
    @Override
    public int deleteByTestIds(@Param("testIds") List<String> testIds){
        return baseMapper.deleteByTestIds(testIds);
    }

    /**
     * 删除根据testId
     * @param testId
     * @return
     */
    @Override
    public int deleteByTestId(@Param("testId") String testId){
        return baseMapper.deleteByTestId(testId);
    }

    /**
     * 获取正确率或用时拍行
     * @param studentTestRelation
     * @return
     */
    @Override
    public double obtainRanking(StudentTestRelation studentTestRelation){
        return AppletUtils.retainTwoDecimal(baseMapper.obtainRanking(studentTestRelation));
    }

}
