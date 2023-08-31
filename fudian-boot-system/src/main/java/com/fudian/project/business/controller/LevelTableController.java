package com.fudian.project.business.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fudian.business.pojo.LevelTable;
import com.fudian.business.service.LevelTableService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.log.annotation.OperInfo;
import com.fudian.log.annotation.OperTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 关卡管理
 *
 * @author xuchenghong
 */
@RestController
@RequestMapping(value = "levelTable")
@OperTitle("关卡管理")
public class LevelTableController {
    @Autowired
    private LevelTableService levelTableService;

    /**
     * 分页查询关卡信息
     *
     * @param levelTable
     * @return
     */
    @PostMapping("/pagingQueryLevelTable")
    @OperInfo(info = "分页查询关卡信息")
    public CommonGridResult pagingQueryLevelTable(LevelTable levelTable) {
        CommonGridResult commonGridResult = levelTableService.pagingSelectLevelTable(levelTable);
        return commonGridResult;
    }
    /**
     * 查询关卡信息
     *
     * @param levelTable
     * @return
     */
    @PostMapping("/queryLevelTable")
    @OperInfo(info = "查询关卡信息")
    public CommonResult<LevelTable> queryLevelTable(LevelTable levelTable) {
        List<LevelTable> levelTables = levelTableService.selectLevelTable(levelTable);
        return CommonResult.success(levelTables);
    }

    /**
     * 添加关卡信息
     *
     * @param levelTable
     * @return
     */
    @PostMapping("/addLevelTable")
    @OperInfo(info = "添加关卡信息")
    public CommonResult addLevelTable(LevelTable levelTable) {

        int i = levelTableService.insertLevelTable(levelTable);
        if (i > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }

    /**
     * 根据id 修改关卡信息
     *
     * @param levelTable
     * @return
     */
    @PostMapping("/modifyLevelTableById")
    @OperInfo(info = "根据id 修改关卡信息")
    public CommonResult modifyLevelTableById(LevelTable levelTable) {

        int i = levelTableService.updateLevelTableById(levelTable);
        if (i > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }

    /**
     * 根据ids 删除关卡信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/removeLevelTableById")
    @OperInfo(info = "根据ids 删除关卡信息")
    public CommonResult removeLevelTableById(String ids) {

        int i = levelTableService.deleteLevelTableById(ids);
        if (i > 0) {
            return CommonResult.success();
        } else {
            return CommonResult.error();
        }
    }


    @GetMapping("queryLevelTableList")
    public CommonGridResult queryLevelTableList(@RequestParam Map<String,Object> map){
        return levelTableService.queryList(map);
    }

    @PostMapping("saveLevelTable")
    @OperInfo(info = "修改/新增关卡和关卡关联知识点信息")
    public CommonResult saveLevelTable(@RequestBody Map<String,Object> map){
        return levelTableService.saveLevelTable(map);
    }

    /*
    *根据关卡id获取习题
    */
    @GetMapping("getExercisesByLevelId")
    @OperInfo(info = "修改/根据关卡id获取习题")
    public CommonResult getExercisesByLevelId(String levelId){
        return levelTableService.getExercisesByLevelId(levelId);
    }

    /*
     *获取最大关卡等级
     */
    @GetMapping("getMaxLevelNum")
    @OperInfo(info = "修改/新增时根据班级id获取习题")
    public CommonResult getMaxLevelNum(String gradeId){
        return levelTableService.getMaxLevelNum(gradeId);
    }
}
