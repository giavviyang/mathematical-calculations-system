package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.TestExercisesTrainMapper;
import com.fudian.business.pojo.TestExercisesTrain;
import com.fudian.business.service.TestExercisesTrainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author xuchenghong
 */

@Service
public class TestExercisesTrainServiceImpl extends ServiceImpl<TestExercisesTrainMapper, TestExercisesTrain> implements TestExercisesTrainService {


    @Resource
    private TestExercisesTrainMapper testExercisesTrainMapper;


}
