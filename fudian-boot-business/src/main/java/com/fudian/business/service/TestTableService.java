package com.fudian.business.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.TestTable;
import com.fudian.business.vo.TestClassificationAnalysisVo;
import com.fudian.business.vo.TestStudentAnalysisVo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;

import java.util.List;
import java.util.Map;

/**
 * 课堂试卷
 *
 * @author xuchenghong
 */
public interface TestTableService extends IService<TestTable> {

    /**
     * 分页查询课堂试卷
     *
     * @param testTable
     * @return
     */
    CommonGridResult pagingSelectTestTable(TestTable testTable);
    /**
     * 添加课堂测试相关对应数据
     *
     * @param testTable
     * @return
     */
    CommonResult<?> addTestTable(TestTable testTable);
    /**
     * 删除课堂测试相关对应数据
     *
     * @param testId
     * @return
     */
    CommonResult<?> removeTestTable(String testId);
    /**
     * 批量删除课堂测试相关对应数据
     *
     * @param testIds
     * @return
     */
    CommonResult<?> batchRemoveTestTable(List<String> testIds);
    /**
     * 根据课堂测试id生成对应的知识点习题
     *
     * @param testId
     * @return
     */
    List<Map<String, String>> getExercisesByTestId(String testId);

    /**
     * 查询可练习的课堂测试题目
     *
     * @return
     */
     CommonResult<?> queryTestList();
    /**
     * 根据课堂测试统一生成习题或是随机生成习题来生成对应习题
     *
     * @param testId 课堂测试id
     * @return 习题数量，课堂测试名称，课堂测试id，课堂测试习题集合
     */
     CommonResult<?> generateTestTopic(String testId);
    /**
     * 答案提交
     * @param batchId 批次id
     * @param json 答题答案
     * @param second 答题用时
     * @return
     */
    JSONObject submitAnswerResult(String batchId, String json, int second);
    /**
     * 错题答案提交
     * @param batchId 批次id
     * @param json 答题答案
     * @param second 答题用时
     * @return
     */
    JSONObject submitErrorAnswerResult(String batchId, String json, int second);

    /**
     * 分页查询课堂统一错题
     *
     * @param testClassifVo
     * @return
     */
    CommonGridResult queryClassifyErrorTopic(TestClassificationAnalysisVo testClassifVo);
    /**
     * 分页查询课堂错题
     *
     * @param testStudentfVo
     * @return
     */
    CommonGridResult queryStudentErrorTopic(TestStudentAnalysisVo testStudentfVo);
}
