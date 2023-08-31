package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.ExercisesRules;
import com.fudian.common.pojo.CommonResult;

import java.util.List;
import java.util.Map;


public interface ExercisesRulesService extends IService<ExercisesRules> {

    /**
     * 依据分类id生成题目（如果传入的number为null则通过知识点id查找知识点对应的题数，否则按照传入的number生成对应题数）
     *
     * @param classfitionId number
     * @return
     */
    CommonResult generateExercises(String classfitionId, Integer number);
}
