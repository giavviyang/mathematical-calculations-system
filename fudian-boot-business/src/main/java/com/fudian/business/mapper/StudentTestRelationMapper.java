package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.StudentTestRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author xuchenghong
 */

@Mapper
public interface StudentTestRelationMapper extends BaseMapper<StudentTestRelation> {

    /**
     * 批量删除根据testId
     * @param testIds
     * @return
     */
    int deleteByTestIds(@Param("testIds") List<String> testIds);

    /**
     * 删除根据testId
     * @param testId
     * @return
     */
    int deleteByTestId(@Param("testId") String testId);
    /**
     * 获取正确率或用时拍行
     * @param studentTestRelation
     * @return
     */
    double obtainRanking(@Param("studentTestRelation") StudentTestRelation studentTestRelation);


}
