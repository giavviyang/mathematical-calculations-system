package com.fudian.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.business.pojo.LevelTableExercises;
import com.fudian.business.pojo.LevelTableExercises;
import com.fudian.common.pojo.CommonGridResult;

import java.util.List;

/**
 * 关卡
 * @author xuchenghong
 */
public interface LevelTableExercisesService extends IService<LevelTableExercises> {

    /**
     * 分页查询关卡
     *
     * @param levelTableExercises
     * @return
     */
    CommonGridResult pagingSelectLevelTableExercises(LevelTableExercises levelTableExercises);

    /**
     * 查询关卡
     *
     * @param levelTableExercises
     * @return
     */
    List<LevelTableExercises> selectLevelTableExercises(LevelTableExercises levelTableExercises);

    /**
     * 添加关卡
     *
     * @param levelTableExercises
     * @return
     */
    int insertLevelTableExercises(LevelTableExercises levelTableExercises);

    /**
     * 修改关卡
     *
     * @param levelTableExercises
     * @return
     */
    int updateLevelTableExercisesById(LevelTableExercises levelTableExercises);

    /**
     * 批量删除关卡
     *
     * @param id
     * @return
     */
    int deleteLevelTableExercisesById(String id);
    /**
     * 批量删除关卡
     *
     * @param ids
     * @return
     */
    int deleteLevelTableExercisesById(List<String> ids);
}
