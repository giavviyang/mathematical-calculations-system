package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.TestAnswerMapper;
import com.fudian.business.pojo.TestAnswer;
import com.fudian.business.service.TestAnswerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author xuchenghong
 */

@Service
public class TestAnswerServiceImpl extends ServiceImpl<TestAnswerMapper, TestAnswer> implements TestAnswerService {

    /**
     * 批量删除根据testId
     * @param testIds
     * @return
     */
    @Override
    public int deleteByTestIds(@Param("testIds") List<String> testIds){
        return this.baseMapper.deleteByTestIds(testIds);
    }

    /**
     * 删除根据testId
     * @param testId
     * @return
     */
    @Override
    public int deleteByTestId(@Param("testId") String testId){
        return this.baseMapper.deleteByTestId(testId);
    }

    /**
     * 删除学生没有答题的题
     * @param studentId 学生id
     * @param testId 课堂测试id
     */
    @Override
    public void removeAbandonExercises(String studentId,String testId){
        this.baseMapper.removeAbandonExercises(studentId,testId);
    }

}
