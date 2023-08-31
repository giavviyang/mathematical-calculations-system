package com.fudian.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.pojo.TestTable;
import com.fudian.business.service.TestTableService;
import com.fudian.business.vo.TestClassificationAnalysisVo;
import com.fudian.business.vo.TestStudentAnalysisVo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xuchenghong
 */
@RestController
@RequestMapping(value = "classroomTest")
public class TestTableController {

    @Autowired
    private TestTableService testTableService;

    /**
     * 分页查询
     *
     * @param testTable
     * @return
     */
    @PostMapping(value = "/pagingQuery")
    public CommonGridResult pagingQuery(@RequestBody TestTable testTable) {

        CommonGridResult commonGridResult = testTableService.pagingSelectTestTable(testTable);
        return commonGridResult;
    }

    /**
     * 添加课堂测试数据
     *
     * @param testTable
     * @return
     */
    @PostMapping(value = "/addTestTable")
    public CommonResult<?> addTestTable(@RequestBody TestTable testTable) {
        CommonResult<?> commonResult = testTableService.addTestTable(testTable);
        return commonResult;
    }

    /**
     * 删除课堂测试数据
     *
     * @param testId
     * @return
     */
    @PostMapping(value = "/removeTestTable")
    public CommonResult<?> removeTestTable(String testId) {
        CommonResult<?> commonResult = testTableService.removeTestTable(testId);
        return commonResult;
    }

    /**
     * 批量删除课堂测试数据
     *
     * @param jsonIds
     * @return
     */
    @PostMapping(value = "/batchRemoveTestTable")
    public CommonResult<?> batchRemoveTestTable(String jsonIds) {
        List<String> testIds = JSON.parseArray(jsonIds, String.class);
        CommonResult<?> commonResult = testTableService.batchRemoveTestTable(testIds);
        return commonResult;
    }

    /**
     * 修改发布状态
     *
     * @param idList 主键id
     * @param flag   发布回收状态
     * @return
     */
    @GetMapping(value = "/modifyReleaseState")
    public CommonResult<?> modifyReleaseState(String idList, int flag) {
        List<String> testIds = JSON.parseArray(idList, String.class);
        UpdateWrapper<TestTable> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_release", flag).in("id", testIds);
        boolean update = testTableService.update(updateWrapper);
        if (!update) {
            return CommonResult.error();
        }
        return CommonResult.success();
    }
    /**
     * 查询
     *
     * @param testClassifVo 测试题目id
     * @return
     */
    @PostMapping(value = "/queryClassifyErrorTopic")
    public CommonGridResult queryClassifyErrorTopic(TestClassificationAnalysisVo testClassifVo) {
        return testTableService.queryClassifyErrorTopic(testClassifVo);
    }
    /**
     * 查询
     *
     * @param testStudentfVo 测试题目id
     * @return
     */
    @PostMapping(value = "/queryStudentErrorTopic")
    public CommonGridResult queryStudentErrorTopic(TestStudentAnalysisVo testStudentfVo) {
        return testTableService.queryStudentErrorTopic(testStudentfVo);
    }

}
