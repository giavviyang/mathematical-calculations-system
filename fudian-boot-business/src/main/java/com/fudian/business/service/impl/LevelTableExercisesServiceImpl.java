package com.fudian.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.LevelTableExercisesMapper;
import com.fudian.business.pojo.LevelTableExercises;
import com.fudian.business.service.LevelTableExercisesService;
import com.fudian.common.pojo.CommonGridResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


/**
 * @author xuchenghong
 */

@Service
public class LevelTableExercisesServiceImpl extends ServiceImpl<LevelTableExercisesMapper, LevelTableExercises> implements LevelTableExercisesService {

    @Resource
    private LevelTableExercisesMapper levelTableExercisesMapper;

    /**
     * 分页查询关卡关联
     *
     * @param levelTableExercises
     * @return
     */
    @Override
    public CommonGridResult pagingSelectLevelTableExercises(LevelTableExercises levelTableExercises) {
        LambdaQueryWrapper<LevelTableExercises> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(levelTableExercises.getPageNum(), levelTableExercises.getPageSize());
        List<LevelTableExercises> levelTableExercisess = levelTableExercisesMapper.selectList(queryWrapper);

        Page page = (Page) levelTableExercisess;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(levelTableExercises.getPageNum(), levelTableExercises.getPageSize(), total, levelTableExercisess);
        return commonGridResult;
    }

    /**
     * 查询关卡关联
     *
     * @param levelTableExercises
     * @return
     */
    @Override
    public List<LevelTableExercises> selectLevelTableExercises(LevelTableExercises levelTableExercises) {
        LambdaQueryWrapper<LevelTableExercises> queryWrapper = new LambdaQueryWrapper<>();

        List<LevelTableExercises> levelTableExercisess = levelTableExercisesMapper.selectList(queryWrapper);
        return levelTableExercisess;
    }

    /**
     * 添加关卡关联
     *
     * @param levelTableExercises
     * @return
     */
    @Override
    public int insertLevelTableExercises(LevelTableExercises levelTableExercises) {
        String uuid = UUID.randomUUID().toString();
//        levelTableExercises.setid(uuid);
        int insert = levelTableExercisesMapper.insert(levelTableExercises);
        return insert;
    }

    /**
     * 修改关卡关联
     *
     * @param levelTableExercises
     * @return
     */
    @Override
    public int updateLevelTableExercisesById(LevelTableExercises levelTableExercises) {

        int i = levelTableExercisesMapper.updateById(levelTableExercises);
        return i;
    }

    /**
     * 批量删除关卡关联
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteLevelTableExercisesById(List<String> ids) {
        int insert = levelTableExercisesMapper.deleteBatchIds(ids);
        return insert;
    }

    /**
     * 删除关卡关联
     *
     * @param id
     * @return
     */
    @Override
    public int deleteLevelTableExercisesById(String id) {
        int insert = levelTableExercisesMapper.deleteById(id);
        return insert;
    }
}
