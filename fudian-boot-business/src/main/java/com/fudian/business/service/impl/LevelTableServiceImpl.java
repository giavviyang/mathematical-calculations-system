package com.fudian.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.LevelTableMapper;
import com.fudian.business.pojo.ExercisesTrainSet;
import com.fudian.business.pojo.LevelKnowledge;
import com.fudian.business.pojo.LevelTable;
import com.fudian.business.service.*;
import com.fudian.business.vo.LevelVo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 关卡信息
 *
 * @author xuchenghong
 */
@Service
@Slf4j
@CacheConfig(cacheNames = {"BreakThroughServiceImpl"})
public class LevelTableServiceImpl extends ServiceImpl<LevelTableMapper, LevelTable> implements LevelTableService {

    @Resource
    private LevelTableMapper levelTableMapper;

    @Autowired
    private LevelKnowledgeService levelKnowledgeService;

    @Autowired
    private KnowledgeListClassificationService knowledgeListClassificationService;

    @Autowired
    private ExercisesRulesService exercisesRulesService;

    @Autowired
    private ExercisesTrainSetService exercisesTrainSetService;

    /**
     * 分页查询关卡信息
     *
     * @param levelTable
     * @return
     */
    @Override
    public CommonGridResult pagingSelectLevelTable(LevelTable levelTable) {
        LambdaQueryWrapper<LevelTable> queryWrapper = new LambdaQueryWrapper<>();

        PageHelper.startPage(levelTable.getPageNum(), levelTable.getPageSize());
        List<LevelTable> levelTables = levelTableMapper.selectList(queryWrapper);

        Page page = (Page) levelTables;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(levelTable.getPageNum(), levelTable.getPageSize(), total, levelTables);
        return commonGridResult;
    }

    /**
     * 查询关卡信息
     *
     * @param levelTable
     * @return
     */
    @Override
    public List<LevelTable> selectLevelTable(LevelTable levelTable) {
        LambdaQueryWrapper<LevelTable> queryWrapper = new LambdaQueryWrapper<>();
        List<LevelTable> levelTables = levelTableMapper.selectList(queryWrapper);
        return levelTables;
    }

    /**
     * 添加关卡信息
     *
     * @param levelTable
     * @return
     */
    @Override
    public int insertLevelTable(LevelTable levelTable) {
        String uuid = UUID.randomUUID().toString();
        levelTable.setLevelId(uuid);
        int insert = levelTableMapper.insert(levelTable);
        return insert;
    }

    /**
     * 修改关卡信息
     *
     * @param levelTable
     * @return
     */
    @Override
    public int updateLevelTableById(LevelTable levelTable) {

        int i = levelTableMapper.updateById(levelTable);
        return i;
    }

    /**
     * 批量删除关卡信息
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteLevelTableById(List<String> ids) {
        int insert = levelTableMapper.deleteBatchIds(ids);

        return insert;
    }

    @Override
    public CommonGridResult queryList(Map<String, Object> map) {
        CommonGridResult commonGridResult = new CommonGridResult();
        Integer pageNum = Integer.valueOf(map.get("pageNum").toString());
        Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
        Page page = PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> maps = levelTableMapper.queryList(map);
        for (Map<String, Object> map1 : maps) {
            List<Map<String, Object>> maps1 = knowledgeListClassificationService.queryListByLevelId(map1.get("levelId").toString());
            map1.put("knowledgeList", maps1);
        }
        commonGridResult.setRows(page.getResult());
        commonGridResult.setPageSize(page.getPageSize());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setTotal(page.getTotal());
        return commonGridResult;
    }

    @Transactional
    @Override
    @CacheEvict(keyGenerator = "delAllKeyGenerator", allEntries = true)
    public CommonResult saveLevelTable(Map<String, Object> map) {
        String levelId = "";
        String levelName = map.get("levelName").toString();
        String levelTime = map.get("levelTime").toString();
        String levelNumTopic = map.get("levelNumTopic").toString();
        String gradeId = map.get("gradeId").toString();
        if (map.get("levelId") != null) {
            levelId = map.get("levelId").toString();
            //编辑时判断关卡等级是否发生变动，如果发生变动，则判断变动后的关卡等级是否与年级下其他关卡重复
            LambdaQueryWrapper<LevelTable> levelTableLambdaQueryWrapper = new LambdaQueryWrapper<>();
            levelTableLambdaQueryWrapper.eq(LevelTable::getLevelId,levelId);
            LevelTable one = this.getOne(levelTableLambdaQueryWrapper);
            if (!one.getLevelName().equals(levelName)){
                LambdaQueryWrapper<LevelTable> levelTableLambdaQueryWrapper2 = new LambdaQueryWrapper<>();
                levelTableLambdaQueryWrapper2.eq(LevelTable::getLevelName,levelName);
                levelTableLambdaQueryWrapper2.eq(LevelTable::getGradeNum,gradeId);
                if (this.getOne(levelTableLambdaQueryWrapper2)!=null){
                    return CommonResult.error("该年级下关卡已存在，请重新输入关卡等级");
                };
            }
        } else {
            levelId = StringUtils.uuid();
            //新增时判断关卡等级是否重复
            LambdaQueryWrapper<LevelTable> levelTableLambdaQueryWrapper = new LambdaQueryWrapper<>();
            levelTableLambdaQueryWrapper.eq(LevelTable::getLevelName,levelName);
            levelTableLambdaQueryWrapper.eq(LevelTable::getGradeNum,gradeId);
            if (this.getOne(levelTableLambdaQueryWrapper)!=null){
                return CommonResult.error("该年级下关卡已存在，请重新输入关卡等级");
            };
        }
        LevelTable levelTable = new LevelTable();
        levelTable.setLevelId(levelId);
        levelTable.setLevelName(levelName);
        levelTable.setLevelTime(levelTime);
        levelTable.setLevelNumTopic(levelNumTopic);
        if (StringUtils.isNotEmpty(gradeId)) {
            levelTable.setGradeNum(gradeId);
        }
        List<Map<String, Object>> knowledgeList = (List<Map<String, Object>>) map.get("knowledgeList");
        List<LevelKnowledge> levelKnowledges = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : knowledgeList) {
            LevelKnowledge levelKnowledge = new LevelKnowledge();
            levelKnowledge.setLevelId(levelId);
            levelKnowledge.setClassificationId(stringObjectMap.get("classificationId").toString());
            levelKnowledge.setPercentage(stringObjectMap.get("percentage").toString());
            levelKnowledges.add(levelKnowledge);
        }
        LambdaQueryWrapper<LevelKnowledge> levelKnowledgeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        levelKnowledgeLambdaQueryWrapper.eq(LevelKnowledge::getLevelId, levelId);
        levelKnowledgeService.remove(levelKnowledgeLambdaQueryWrapper);
        levelKnowledgeService.saveBatch(levelKnowledges);
        this.saveOrUpdate(levelTable);
        //编辑关卡信息后关卡关联的知识点可能发生改变，这时之前闯关产生的未做的习题要进行删除
        LambdaQueryWrapper<ExercisesTrainSet> exercisesTrainSetLambdaQueryWrapper = new LambdaQueryWrapper<>();
        exercisesTrainSetLambdaQueryWrapper.eq(ExercisesTrainSet::getLevelId,levelId);
        exercisesTrainSetService.remove(exercisesTrainSetLambdaQueryWrapper);
        return CommonResult.success("保存成功");
    }


    /**
     * 删除关卡信息
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    @CacheEvict(keyGenerator = "delAllKeyGenerator", allEntries = true)
    public int deleteLevelTableById(String ids) {
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        //删除关卡表数据
        int insert = levelTableMapper.deleteBatchIds(strings);
        //删除关卡知识点关联表数据
        LambdaQueryWrapper<LevelKnowledge> levelKnowledgeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        levelKnowledgeLambdaQueryWrapper.in(LevelKnowledge::getLevelId, strings);
        levelKnowledgeService.remove(levelKnowledgeLambdaQueryWrapper);
        return insert;
    }

    /**
     * 通过学生id和年级id 获取当前学生在当前次年级中的最大闯关是那一关
     *
     * @param gradeId
     * @param studentId
     * @return
     */
    @Override
    public int selectMaxPasses(String gradeId, String studentId) {
        Integer insert = levelTableMapper.selectMaxPasses(gradeId, studentId);
        if (insert == null) {
            insert = 0;
        }
        return insert;
    }
    /**
     * 通过年级id 获取当前年级中的最大关卡
     *
     * @param gradeId
     * @return
     */
    @Override
    public int selectMaxCheckpoints(String gradeId) {
        Integer insert = levelTableMapper.selectMaxCheckpoints(gradeId);
        if (insert == null) {
            insert = 0;
        }
        return insert;
    }

    @Override
    public CommonResult getExercisesByLevelId(String levelId) {
        List<Map<String, String>> lists = new ArrayList<>();
        //获取关卡总题数
        LambdaQueryWrapper<LevelTable> levelTableLambdaQueryWrapper = new LambdaQueryWrapper<>();
        levelTableLambdaQueryWrapper.eq(LevelTable::getLevelId, levelId);
        LevelTable one = this.getOne(levelTableLambdaQueryWrapper);
        Integer levelNumTopic = Integer.valueOf(one.getLevelNumTopic());

        //获取关卡下所有知识点，并且根据知识点占比计算知识点题数
        LambdaQueryWrapper<LevelKnowledge> levelKnowledgeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        levelKnowledgeLambdaQueryWrapper.eq(LevelKnowledge::getLevelId, levelId);
        List<LevelKnowledge> list = levelKnowledgeService.list(levelKnowledgeLambdaQueryWrapper);
        for (LevelKnowledge levelKnowledge : list) {
            //根据关卡总题数和知识点占百分比获取知识点要生成的题数
            Integer percentage = Integer.valueOf(levelKnowledge.getPercentage());
            double v = levelNumTopic * (percentage * 0.01);
            //将知识点题数四舍五入转换为整数
            BigDecimal b = new BigDecimal(v);
            Double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Integer number = f1.intValue();

            List<Map<String, String>> list1 = (List<Map<String, String>>)exercisesRulesService.generateExercises(levelKnowledge.getClassificationId(), number).getData();
            for (Map<String, String> stringStringMap : list1) {
                lists.add(stringStringMap);
            }
        }
        int size = lists.size();
        Integer number = Integer.valueOf(one.getLevelNumTopic());
        if (size>number){//如果生成题数大于关卡指定题数，则随机删除多出的题数
            int i = size - number;
            for (int j = 0; j < i; j++) {
                lists.remove(size-j);
            }
        }else if(lists.size()<Integer.valueOf(one.getLevelNumTopic())){//如果生成题数小于关卡指定题数，则随机添加题数
            int i = number-size;
            List<Map<String, String>> list1 = (List<Map<String, String>>)exercisesRulesService.generateExercises(list.get(0).getClassificationId(), i).getData();
            for (Map<String, String> stringStringMap : list1) {
                lists.add(stringStringMap);
            }
        }
        return CommonResult.success(lists);
    }

    @Override
    public CommonResult getMaxLevelNum(String gradeId) {
        LambdaQueryWrapper<LevelTable> levelTableLambdaQueryWrapper = new LambdaQueryWrapper<>();
        levelTableLambdaQueryWrapper.eq(LevelTable::getGradeNum,gradeId);
        List<LevelTable> list = this.list(levelTableLambdaQueryWrapper);
        if (list.size()>0){
            return CommonResult.success(levelTableMapper.getMaxLevelNum(gradeId));
        }else{
            return CommonResult.success(0);
        }
    }
    /**
     * 获取学生-关卡信息
     * @param studentId 学生id
     * @return 关卡信息
     */
    @Override
    public List<LevelVo> getStudentLevelList(String studentId){
        return baseMapper.getStudentLevelList(studentId);
    }

    /**
     * 获取关卡关联知识点进位
     *
     * @param levelId 关卡第
     * @return 关联知识点进位
     */
    @Override
    public Map<String ,Integer> getKnowledgeType(String levelId){
        List<JSONObject> knowledgeType = baseMapper.getKnowledgeType(levelId);
        HashMap<String, Integer> map = new HashMap<>();
        knowledgeType.forEach(list->{
            map.put(list.get("key").toString(),Integer.valueOf(list.get("value").toString()));
        });
        return map;
    }

}
