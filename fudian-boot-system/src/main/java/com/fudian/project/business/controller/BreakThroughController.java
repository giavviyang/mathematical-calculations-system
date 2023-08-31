package com.fudian.project.business.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.business.pojo.ExercisesTrainSet;
import com.fudian.business.pojo.TestAnswer;
import com.fudian.business.service.ExercisesTrainSetService;
import com.fudian.business.vo.TrainResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.project.business.service.BreakThroughService;
import com.fudian.project.business.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 小程序闯关
 *
 * @author xuchenghong
 */
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping(value = "applet/breakThrough")
public class BreakThroughController {

    @Autowired
    private BreakThroughService breakThroughService;
    @Autowired
    private TrainingService trainingService;

    /**
     * 查询关卡列表
     *
     * @return
     */
    @PostMapping("queryLevelList")
    public CommonResult<?> queryLevelList() {
        JSONArray jsonArray = null;
        try {
            jsonArray = breakThroughService.queryLevelList();
            if (jsonArray.size() == 0) {
                return CommonResult.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error("查询失败！");
        }
        return CommonResult.success(jsonArray);
    }

    /**
     * 提交习题
     *
     * @param
     * @return
     */
    @PostMapping("/submitAnswerResult")
    public CommonResult<?> submitAnswerResult(String id, String json, int second) {
        TrainResult trainResult = null;
        try {
            //trainResult = breakThroughService.submitAnswerResult(id, json, second);
            trainResult = trainingService.submitAnswerResult(id, json, second);
            if (trainResult == null) {
                return CommonResult.success("暂无数据！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error("提交失败！");
        }
        return CommonResult.success(trainResult);
    }

    /**
     * 关卡练习错题重做答案提交
     *
     * @param
     * @return
     */
    @PostMapping("/submitErrorAnswerResult")
    public CommonResult<?> submitErrorAnswerResult(String id, String json, int second) {
        try {
            TrainResult trainResult = breakThroughService.submitErrorAnswerResult(id, json, second);
            if (trainResult == null) {
                return CommonResult.success("暂无数据！");
            }
            return CommonResult.success(trainResult);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }

    @Autowired
    private ExercisesTrainSetService exercisesTrainSetService;
    /**
     *
     *
     * @param batchId 批次id
     * @param second  答题用时
     * @return
     */
    @PostMapping(value = "/submitAnswerResultTest")
    public CommonResult<?> submitAnswerResultTest(String batchId, Integer second) {
        //batchId 根据批次id 查询习题
        LambdaQueryWrapper<ExercisesTrainSet> wrapperExercises = new LambdaQueryWrapper<>();
        wrapperExercises.eq(ExercisesTrainSet::getBatchId, batchId);
        List<ExercisesTrainSet> trainSets = exercisesTrainSetService.list(wrapperExercises);

        JSONObject jsonObject1 = new JSONObject();
        for (ExercisesTrainSet trainSet : trainSets) {
            String exercisesId = trainSet.getExercisesId();
            String  answer = trainSet.getExercisesAnswers();
            jsonObject1.put(exercisesId,"11");
        }
        String json = jsonObject1.toJSONString();
        TrainResult jsonObject = trainingService.submitAnswerResult(batchId, json, second);
        return CommonResult.success(jsonObject);
    }
    /**
     *
     *
     * @param batchId 批次id
     * @param second  答题用时
     * @return
     */
    @PostMapping(value = "/submitErrorAnswerResultTest")
    public CommonResult<?> submitErrorAnswerResultTest(String batchId,  Integer second) {
        //batchId 根据批次id 查询习题
        LambdaQueryWrapper<ExercisesTrainSet> wrapperExercises = new LambdaQueryWrapper<>();
        wrapperExercises.eq(ExercisesTrainSet::getBatchId, batchId);
        wrapperExercises.eq(ExercisesTrainSet::getIsError, 1);
        List<ExercisesTrainSet> trainSets = exercisesTrainSetService.list(wrapperExercises);

        JSONObject jsonObject1 = new JSONObject();
        for (ExercisesTrainSet trainSet : trainSets) {
            String exercisesId = trainSet.getExercisesId();
            String  answer = trainSet.getExercisesAnswers();
            jsonObject1.put(exercisesId,answer);
        }
        String json = jsonObject1.toJSONString();
        TrainResult jsonObject = breakThroughService.submitErrorAnswerResult(batchId, json, second);
        return CommonResult.success(jsonObject);
    }
}
