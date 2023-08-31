package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.StudentTestRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生课堂测试关联表
 *
 * @author xuchenghong
 */
public interface StudentTestRelationService extends IService<StudentTestRelation> {
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
