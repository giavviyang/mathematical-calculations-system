package com.fudian.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.Students;
import com.fudian.business.mapper.ExercisesTrainSetMapper;
import com.fudian.business.mapper.StudentKnowledgeRelationMapper;
import com.fudian.business.pojo.*;
import com.fudian.business.service.*;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.AppletUtils;
import com.fudian.common.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @author xuchenghong
 */

@Service
public class StudentKnowledgeRelationServiceImpl extends ServiceImpl<StudentKnowledgeRelationMapper, StudentKnowledgeRelation> implements StudentKnowledgeRelationService {


    @Resource
    private StudentAnswerService studentAnswerService;
    @Resource
    private ExercisesTrainSetMapper exercisesTrainSetMapper;
    @Resource
    private KnowledgeListClassificationService knowledgeListClassificationService;
    @Resource
    private KnowledgeListScheduleService knowledgeListScheduleService;
    @Resource
    private LevelKnowledgeService levelKnowledgeService;
    @Resource
    private StudentsService studentsService;

    /**
     * 分页查询学生知识点关联表
     *
     * @param unlock
     * @return
     */
    @Override
    public CommonGridResult pagingSelectStudentLevelRelation(StudentKnowledgeRelation unlock) {
        LambdaQueryWrapper<StudentKnowledgeRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentKnowledgeRelation::getRelationType, unlock.getRelationType());
        if (unlock.getIsLock() == 0) {
            queryWrapper.ne(StudentKnowledgeRelation::getIsLock, 0);
        } else {
            queryWrapper.eq(StudentKnowledgeRelation::getIsLock, unlock.getIsLock());
        }
        if (unlock.getStudentName() != null && !unlock.getStudentName().isEmpty()) {
            queryWrapper.like(StudentKnowledgeRelation::getStudentName, unlock.getStudentName());
        }
        if (unlock.getDeptId() != null && !unlock.getDeptId().isEmpty()) {
            LambdaQueryWrapper<Students> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Students::getClassRoomId, unlock.getDeptId());
            wrapper.eq(Students::getStudentStatus, 0);

            List<Students> list = studentsService.list(wrapper);
            if (list == null || list.size() <= 0) {
                return new CommonGridResult(unlock.getPageNum(), unlock.getPageSize(), 0, null);
            }
            List<String> collect = list.stream().map(Students::getStudentId).collect(Collectors.toList());
            queryWrapper.in(StudentKnowledgeRelation::getStudentId, collect);
        }
        //根据条件查询学生关联的知识点和关卡
        PageHelper.startPage(unlock.getPageNum(), unlock.getPageSize());
        List<StudentKnowledgeRelation> studentKnowledgeRelations = baseMapper.selectList(queryWrapper);
        JSONArray jsonArray = new JSONArray();
        //循环知识点获取关卡 查询对应的错题
        for (StudentKnowledgeRelation studentKnowledgeRelation : studentKnowledgeRelations) {
            JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(JSON.toJSONString(studentKnowledgeRelation));
            LambdaQueryWrapper<StudentAnswer> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(StudentAnswer::getType, studentKnowledgeRelation.getRelationType());
            wrapper.eq(StudentAnswer::getStudentId, studentKnowledgeRelation.getStudentId());
            wrapper.eq(StudentAnswer::getClassificationId, studentKnowledgeRelation.getRelationId());
            wrapper.orderByDesc(StudentAnswer::getCreateTime).last("limit 0,5");
            List<StudentAnswer> list = studentAnswerService.list(wrapper);
            //取平均正确率
            double sum = list.stream().mapToDouble(StudentAnswer::getCorrecRate).sum();
            double totalNum = list.stream().mapToDouble(StudentAnswer::getTotalNum).sum();

            double decimal = AppletUtils.retainTwoDecimal((sum / 5));
            jsonObject.put("correcRate", (int) (decimal * 100));
            //获取错题详情
            List<String> collect = list.stream().map(StudentAnswer::getBatchId).collect(Collectors.toList());
            List<ExercisesTrainSet> errorExercises = exercisesTrainSetMapper.getErrorExercises(collect);

            if (unlock.getRelationType() == 1) {

                LambdaQueryWrapper<LevelKnowledge> levelKnowledgeQuery = new LambdaQueryWrapper<>();
                levelKnowledgeQuery.eq(LevelKnowledge::getLevelId, studentKnowledgeRelation.getRelationId());
                List<LevelKnowledge> levelKnowledgeList = levelKnowledgeService.list(levelKnowledgeQuery);

                JSONArray knowledgeJsonArray = new JSONArray();
                Map<String, List<ExercisesTrainSet>> listMap = errorExercises.stream().collect(Collectors.groupingBy(ExercisesTrainSet::getClassificationId));
                for (Map.Entry<String, List<ExercisesTrainSet>> stringListEntry : listMap.entrySet()) {
                    JSONObject knowledgeJsonObject = new JSONObject();
                    String key = stringListEntry.getKey();
                    KnowledgeListClassification byId = knowledgeListClassificationService.getById(key);
                    LambdaQueryWrapper<KnowledgeListSchedule> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.eq(KnowledgeListSchedule::getClassificationId, byId.getClassificationId());
                    KnowledgeListSchedule one = knowledgeListScheduleService.getOne(lambdaQueryWrapper);
                    knowledgeJsonObject.put("diagnosisMsg", one.getDiagnosisMsg());
                    if (StringUtils.isNotEmpty(byId.getClassificationNickname())) {
                        knowledgeJsonObject.put("knowledgeName", byId.getClassificationNickname());
                    } else {
                        knowledgeJsonObject.put("knowledgeName", byId.getClassificationMsg());
                    }
                    List<ExercisesTrainSet> value = stringListEntry.getValue();
                    //知识点在关卡中占比正确率
                    for (LevelKnowledge levelKnowledge : levelKnowledgeList) {
                        if (levelKnowledge.getClassificationId().equals(byId.getClassificationId())) {
                            double correcRate = 1 - ((1 / (totalNum * Integer.valueOf(levelKnowledge.getPercentage()) / 100)) * value.size());
                            BigDecimal correcR = new BigDecimal(correcRate);
                            correcRate = correcR.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            knowledgeJsonObject.put("correcRate", (int) (correcRate * 100));
                            knowledgeJsonObject.put("proportion", levelKnowledge.getPercentage());
                            knowledgeJsonObject.put("errorList", value);
                        }
                    }
                    knowledgeJsonArray.add(knowledgeJsonObject);
                }
                //知识点数据集合
                jsonObject.put("list", knowledgeJsonArray);
            } else {
                //知识点错题集合
                jsonObject.put("errorList", errorExercises);
                LambdaQueryWrapper<KnowledgeListSchedule> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(KnowledgeListSchedule::getClassificationId, studentKnowledgeRelation.getRelationId());
                KnowledgeListSchedule one = knowledgeListScheduleService.getOne(lambdaQueryWrapper);
                jsonObject.put("diagnosisMsg", one.getDiagnosisMsg());
            }
            jsonArray.add(jsonObject);
        }

        Page page = (Page) studentKnowledgeRelations;
        long total = page.getTotal();
        CommonGridResult commonGridResult = new CommonGridResult(unlock.getPageNum(), unlock.getPageSize(), total, jsonArray);
        return commonGridResult;
    }
    /**
     * 分页查询学生知识点关联表
     *
     * @param unlock
     * @return
     */
    @Override
    public CommonGridResult pagingSelectStudentKnowledgeRelation(StudentKnowledgeRelation unlock) {

        List<StudentKnowledgeRelation> jsonObjects;
        Integer totla;
        if (unlock.getRelationType() != 1) {
            jsonObjects = baseMapper.pagingSelect(unlock);

            totla = baseMapper.pagingSelectCount(unlock);
        } else {
            jsonObjects = baseMapper.pagingSelectLevel(unlock);

            totla = baseMapper.pagingSelectLevelCount(unlock);
        }

        CommonGridResult commonGridResult = new CommonGridResult(unlock.getPageNum(), unlock.getPageSize(), totla, jsonObjects);
        return commonGridResult;
    }

    /**
     * 查询学生知识点
     *
     * @param knowledgeId
     * @return
     */
    @Override
    public StudentKnowledgeRelation selectStudentKnowledgeById(String knowledgeId, String studentId) {

        LambdaQueryWrapper<StudentKnowledgeRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentKnowledgeRelation::getStudentId, studentId);
        queryWrapper.eq(StudentKnowledgeRelation::getRelationId, knowledgeId);
        queryWrapper.eq(StudentKnowledgeRelation::getRelationType, 0);
        StudentKnowledgeRelation studentKnowledgeRelation = baseMapper.selectOne(queryWrapper);

        return studentKnowledgeRelation;
    }

    /**
     * 查询掌握 知识点|关卡 数量
     *
     * @param
     * @return
     */
    @Override
    public int selectKnowledgeNum(String studentId, int type) {
        LambdaQueryWrapper<StudentKnowledgeRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StudentKnowledgeRelation::getStudentId, studentId);
        queryWrapper.eq(StudentKnowledgeRelation::getRelationType, type);
        if (type == 0) {
            queryWrapper.eq(StudentKnowledgeRelation::getIsMaster, 1);
        }
        int count = baseMapper.selectCount(queryWrapper);
        return count;
    }

    /**
     * 添加学生知识点关联表
     *
     * @param studentKnowledgeRelation
     * @return
     */
    @Override
    public int insertStudentKnowledgeRelation(StudentKnowledgeRelation studentKnowledgeRelation) {
        String uuid = UUID.randomUUID().toString();
        studentKnowledgeRelation.setId(uuid);
        int insert = baseMapper.insert(studentKnowledgeRelation);
        return insert;
    }

    /**
     * 修改学生知识点关联表
     *
     * @param studentKnowledgeRelation
     * @return
     */
    @Override
    public int updateStudentKnowledgeRelationById(StudentKnowledgeRelation studentKnowledgeRelation) {

        int i = baseMapper.updateById(studentKnowledgeRelation);
        return i;
    }

    /**
     * 根据id批量修改锁定状态
     *
     * @param ids
     * @return
     */
    @Override
    public int updateUnlocks(List<String> ids) {
        UpdateWrapper<StudentKnowledgeRelation> wrapper = new UpdateWrapper<>();
        wrapper.in("id", ids);
        wrapper.set("is_lock", 0);
        wrapper.set("error_num", 0);
        wrapper.set("correct_num", 0);
        int update = baseMapper.update(null, wrapper);
        return update;
    }

    /**
     * 批量删除学生知识点关联表
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteStudentKnowledgeRelationById(List<String> ids) {
        int insert = baseMapper.deleteBatchIds(ids);
        return insert;
    }

    /**
     * 删除学生知识点关联表
     *
     * @param id
     * @return
     */
    @Override
    public int deleteStudentKnowledgeRelationById(String id) {
        int insert = baseMapper.deleteById(id);
        return insert;
    }

    /**
     * 查询学生知识点关联表
     *
     * @param student
     * @param relationId
     * @param type
     * @return
     */
    @Override
    public StudentKnowledgeRelation selectStudentRelationBy(Students student, String relationId, int type) {
        //是否进行锁定（不锁定，错5次锁，对5次锁） 错题本查询
        LambdaQueryWrapper<StudentKnowledgeRelation> wrapperRelation = new LambdaQueryWrapper<>();
        wrapperRelation.eq(StudentKnowledgeRelation::getStudentId, student.getStudentId());
        wrapperRelation.eq(StudentKnowledgeRelation::getRelationId, relationId);
        wrapperRelation.eq(StudentKnowledgeRelation::getRelationType, type);
        StudentKnowledgeRelation one = baseMapper.selectOne(wrapperRelation);
        if (one == null) {
            String uuid = UUID.randomUUID().toString();
            one = new StudentKnowledgeRelation();
            one.setId(uuid);
            one.setStudentId(student.getStudentId());
            one.setStudentName(student.getStudentName());
            one.setRelationId(relationId);
            one.setRelationType(type);
            one.setDeptId(student.getClassRoomId());
            one.setIsMaster(0);
            one.setIsLock(0);
            one.setHighestScore(0);
            one.setErrorNum(0);
            one.setCorrectNum(0);
            one.setHighestCoinsNum(0);
        }
        return one;
    }
}
