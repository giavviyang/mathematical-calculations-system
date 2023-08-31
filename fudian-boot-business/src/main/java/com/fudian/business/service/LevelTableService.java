package com.fudian.business.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.LevelTable;
import com.fudian.business.vo.LevelVo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;

import java.util.List;
import java.util.Map;

/**
 * 关卡信息
 *
 * @author xuchenghong
 */
public interface LevelTableService extends IService<LevelTable> {

    /**
     * 分页查询关卡信息
     *
     * @param levelTable
     * @return
     */
    CommonGridResult pagingSelectLevelTable(LevelTable levelTable);

    /**
     * 查询关卡信息
     *
     * @param levelTable
     * @return
     */
    List<LevelTable> selectLevelTable(LevelTable levelTable);

    /**
     * 添加关卡信息
     *
     * @param levelTable
     * @return
     */
    int insertLevelTable(LevelTable levelTable);

    /**
     * 修改关卡信息
     *
     * @param levelTable
     * @return
     */
    int updateLevelTableById(LevelTable levelTable);

    /**
     * 删除关卡信息
     *
     * @param id
     * @return
     */
    int deleteLevelTableById(String ids);

    /**
     * 批量删除关卡信息
     *
     * @param ids
     * @return
     */
    int deleteLevelTableById(List<String> ids);

    CommonGridResult queryList(Map<String, Object> map);

    CommonResult saveLevelTable(Map<String, Object> map);

    /**
     * 通过学生id和年级id 获取当前学生在当前次年级中的最大闯关是那一关
     *
     * @param gradeId
     * @param studentId
     * @return
     */
    int selectMaxPasses(String gradeId, String studentId);

    /**
     * 通过年级id 获取当前年级中的最大关卡
     *
     * @param gradeId
     * @return
     */
    int selectMaxCheckpoints(String gradeId);

    CommonResult getExercisesByLevelId(String levelId);

    CommonResult getMaxLevelNum(String gradeId);


    /**
     * 获取学生-关卡信息
     *
     * @param studentId 学生id
     * @return 关卡信息
     */
    List<LevelVo> getStudentLevelList(String studentId);

    /**
     * 获取关卡关联知识点进位
     *
     * @param levelId 关卡第
     * @return 关联知识点进位
     */
    Map<String ,Integer> getKnowledgeType(String levelId);

}
