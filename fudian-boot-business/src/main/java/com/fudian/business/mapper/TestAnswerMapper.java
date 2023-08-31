package com.fudian.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.business.pojo.TestAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author xuchenghong
 */

@Mapper
public interface TestAnswerMapper extends BaseMapper<TestAnswer> {
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
     * 删除学生没有答题的题
     * @param studentId 学生id
     * @param testId 课堂测试id
     */
    void removeAbandonExercises(@Param("studentId")String studentId,@Param("testId")String testId);
}
