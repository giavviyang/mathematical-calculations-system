package com.fudian.project.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.fudian.business.service.TestTableService;
import com.fudian.common.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 课堂测试
 *
 * @author xuchenghong
 */
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping(value = "applet/classRoomTest")
public class ClassRoomTestController {

    @Autowired
    private TestTableService testTableService;

    /**
     * 查询可练习的课堂测试题目
     *
     * @return
     */
    @GetMapping(value = "/queryTestList")
    public CommonResult<?> queryTestList() {
        CommonResult<?> commonResult = testTableService.queryTestList();
        return commonResult;
    }

    /**
     * 获取课堂测试生成习题
     *
     * @param testId 主键id
     * @return
     */
    @GetMapping(value = "/generateTestTopic")
    public CommonResult<?> generateTestTopic(String testId) {
        CommonResult<?> commonResult = testTableService.generateTestTopic(testId);
        return commonResult;
    }

    /**
     * 测试数据答题
     *
     * @param batchId 批次id
     * @param json    答题答案
     * @param second  答题用时
     * @return
     */
    @PostMapping(value = "/submitAnswerResult")
    public CommonResult<?> submitAnswerResult(String batchId, String json, Integer second) {
        JSONObject jsonObject = testTableService.submitAnswerResult(batchId, json, second);
        return CommonResult.success(jsonObject);
    }

    /**
     * 错题答案提交
     *
     * @param batchId 批次id
     * @param json    答题答案
     * @param second  答题用时
     * @return
     */
    @PostMapping(value = "/submitErrorAnswerResult")
    public CommonResult<?> submitErrorAnswerResult(String batchId, String json, Integer second) {
        JSONObject jsonObject = testTableService.submitErrorAnswerResult(batchId, json, second);
        return CommonResult.success(jsonObject);
    }


    /*@Autowired
    private TestAnswerService testAnswerService;
    *//**
     *
     *
     * @param batchId 批次id
     * @param second  答题用时
     * @return
     *//*
    @PostMapping(value = "/submitAnswerResultTest")
    public CommonResult<?> submitAnswerResultTest(String batchId, Integer second) {
        //batchId 根据批次id 查询习题
        LambdaQueryWrapper<TestAnswer> wrapperExercises = new LambdaQueryWrapper<>();
        wrapperExercises.eq(TestAnswer::getBatchId, batchId);
        List<TestAnswer> trainSets = testAnswerService.list(wrapperExercises);

        JSONObject jsonObject1 = new JSONObject();
        for (TestAnswer trainSet : trainSets) {
            String exercisesId = trainSet.getExercisesId();
            String  answer = trainSet.getExercisesAnswers();
            jsonObject1.put(exercisesId,"11");
        }
        String json = jsonObject1.toJSONString();
        JSONObject jsonObject = testTableService.submitAnswerResult(batchId, json, second);
        return CommonResult.success(jsonObject);
    }
    *//**
     *
     *
     * @param batchId 批次id
     * @param second  答题用时
     * @return
     *//*
    @PostMapping(value = "/submitErrorAnswerResultTest")
    public CommonResult<?> submitErrorAnswerResultTest(String batchId,  Integer second) {
        //batchId 根据批次id 查询习题
        LambdaQueryWrapper<TestAnswer> wrapperExercises = new LambdaQueryWrapper<>();
        wrapperExercises.eq(TestAnswer::getBatchId, batchId);
        List<TestAnswer> trainSets = testAnswerService.list(wrapperExercises);

        JSONObject jsonObject1 = new JSONObject();
        for (TestAnswer trainSet : trainSets) {
            String exercisesId = trainSet.getExercisesId();
            String  answer = trainSet.getExercisesAnswers();
            jsonObject1.put(exercisesId,answer);
        }
        String json = jsonObject1.toJSONString();
        JSONObject jsonObject = testTableService.submitErrorAnswerResult(batchId, json, second);
        return CommonResult.success(jsonObject);
    }*/

}
