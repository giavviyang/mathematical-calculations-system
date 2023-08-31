package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.TestAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课堂测试答题表
 *
 * @author xuchenghong
 */
public interface TestAnswerService extends IService<TestAnswer> {

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
    void removeAbandonExercises(String studentId,String testId);

}
