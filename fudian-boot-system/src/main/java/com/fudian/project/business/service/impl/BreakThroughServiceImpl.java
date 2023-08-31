package com.fudian.project.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.WrongQuestion;
import com.fudian.business.mapper.KnowledgeListClassificationMapper;
import com.fudian.business.pojo.*;
import com.fudian.business.service.*;
import com.fudian.business.vo.LevelVo;
import com.fudian.business.vo.Subject;
import com.fudian.business.vo.SubjectList;
import com.fudian.business.vo.TrainResult;
import com.fudian.common.utils.AppletUtils;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.business.service.BreakThroughService;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = {"BreakThroughServiceImpl"})
public class BreakThroughServiceImpl implements BreakThroughService {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private StudentsService studentsService;
    @Autowired
    private StudentKnowledgeRelationService studentKnowledgeRelationService;

    @Resource
    private KnowledgeListClassificationMapper knowledgeListClassificationMapper;

    @Autowired
    private LevelTableService levelTableService;
    @Resource
    private ExercisesTrainSetService exercisesTrainSetService;

    @Autowired
    private SysDeptService sysDeptService;

    @Autowired
    private WrongQuestionListService wrongQuestionListService;
    @Autowired
    private StudentAnswerService studentAnswerService;

    /**
     * 关卡列表
     *
     * @return
     */
    @Override
    public JSONArray queryLevelList() {
        HttpServletRequest request = ServletUtils.getRequest();
        Students student = tokenService.getLoginUser(request).getStudent();

        //查询年级
        SysDept thisGrade = sysDeptService.selectRoomClassGrade(student.getClassRoomId());

        int thisOrderNum = thisGrade.getOrderNum();
        //年级对象集合
        JSONArray jsonArrayDept = new JSONArray();
        //查询学生关卡信息
        List<LevelVo> levelList = levelTableService.getStudentLevelList(student.getStudentId());
        //定义一个最大闯关数
        boolean levelNum = true;
        //获取学生最高闯关数
        TreeMap<Integer, List<LevelVo>> depts = levelList.stream().collect(Collectors.groupingBy(LevelVo::getOrderNum,TreeMap::new,Collectors.toList()));
        for (Map.Entry<Integer, List<LevelVo>> deptMap : depts.entrySet()) {
            //创建一个关卡年级对象
            JSONObject deptObject = new JSONObject();
            LevelVo dept = deptMap.getValue().get(0);
            deptObject.put("gradeName",dept.getDeptName());
            deptObject.put("gradeNum",dept.getOrderNum());
            JSONArray levelArray = new JSONArray();
            for (LevelVo levelVo : deptMap.getValue()) {
                JSONObject jsonObjectLevel = new JSONObject();
                //关卡id
                jsonObjectLevel.put("levelId", levelVo.getLevelId());
                //关卡名称
                jsonObjectLevel.put("levelName", levelVo.getLevelName()+"");

                //星星数（或分数）
                jsonObjectLevel.put("starsNum", levelVo.getHighestScore());
                //判断大于最高闯关关卡
                if (thisOrderNum <= levelVo.getOrderNum()) {
                    //关卡是否锁定
                    if (levelNum) {
                        jsonObjectLevel.put("isLock", 0);
                        levelNum = false;
                        if (levelVo.getHighestScore()>= 80){
                            levelNum = true;
                        }
                    } else {
                        jsonObjectLevel.put("isLock", 3);
                    }
                } else {
                    //关卡是否锁定
                    jsonObjectLevel.put("isLock", 0);
                }
                levelArray.add(jsonObjectLevel);
            }
            deptObject.put("levelList",levelArray);
            jsonArrayDept.add(deptObject);
        }
        return jsonArrayDept;
    }

    /**
     * 知识点练习错题重做答案提交
     *
     * @return
     */
    @Transactional
    @Override
    public TrainResult submitErrorAnswerResult(String id, String json, int second) {

        //通过token获取学生信息
        Students students = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
        Students student = studentsService.selectStudentsByStudentId(students.getStudentId());

        //创建返回对象
        TrainResult trainResult = new TrainResult();

        //计算正确题数
        Map<String, String> parse = JSON.parseObject(json, Map.class);
        Set<String> set = parse.keySet();
        //查询所有习题
        LambdaQueryWrapper<ExercisesTrainSet> wrapperExercises = new LambdaQueryWrapper<>();
        wrapperExercises.in(ExercisesTrainSet::getBatchId, id);
        wrapperExercises.in(ExercisesTrainSet::getStudentId, student.getStudentId());
        List<ExercisesTrainSet> trainSets = exercisesTrainSetService.list(wrapperExercises);

        //获取随便一条数据判断是那种类型的习题
        ExercisesTrainSet trainSetOne = null;
        //计算正确题数
        int correct = 0;
        //储存答案 判断正确题数
        List<ExercisesTrainSet> errors = new ArrayList<>();
        List<ExercisesTrainSet> success = new ArrayList<>();
        List<String> successIds = new ArrayList<>();
        for (ExercisesTrainSet trainSet : trainSets) {
            if (parse.containsKey(trainSet.getExercisesId())) {
                String value = parse.get(trainSet.getExercisesId());
                boolean result = false;
                try {
                    if (value.indexOf("/") != -1 && trainSet.getExercisesAnswers().indexOf("/") != -1) {
                        String[] splitA = value.split("/");
                        String[] splitB = trainSet.getExercisesAnswers().split("/");
                        result = splitA[0].equals(splitB[0]) && splitA[1].equals(splitB[1]);
                    } else if (value.indexOf("/") == -1 && trainSet.getExercisesAnswers().indexOf("/") == -1) {
                        result = (double) Double.valueOf(value) == (double) Double.valueOf(trainSet.getExercisesAnswers());
                    } else {
                        result = false;
                    }
                } catch (NumberFormatException e) {
                    result = false;
                }

                trainSet.setIsError(1);
                //存储答案
                trainSet.setErrorAnswers(   parse.get(trainSet.getExercisesId()));
                trainSet.setStudentId(student.getStudentId());
                if (value.equals(trainSet.getExercisesAnswers()) || result) {
                    correct++;
                    trainSet.setIsError(0);
                    successIds.add(trainSet.getExercisesId());
                } else {
                    //错题数据
                    errors.add(trainSet);
                }
                success.add(trainSet);
                if (trainSetOne == null) {
                    trainSetOne = trainSet;
                }
            }
        }
        exercisesTrainSetService.updateBatchById(success);
        //关卡数据
        LevelTable levelById = levelTableService.getById(trainSetOne.getLevelId());
        int type = trainSetOne.getType();
        //判断关卡是否存在
        if (levelById == null) {
            return trainResult;
        }
        String relationId = levelById.getLevelId();
        trainResult.setId(relationId);
        LambdaQueryWrapper<LevelTable> queryWrapper = new LambdaQueryWrapper<>();
        Integer integer = Integer.valueOf(levelById.getLevelName()) + 1;

        queryWrapper.eq(LevelTable::getLevelName, integer);
        queryWrapper.eq(LevelTable::getGradeNum, levelById.getGradeNum());
        LevelTable levelTable = levelTableService.getOne(queryWrapper);
        if (levelTable != null) {
            trainResult.setNextId(levelTable.getLevelId());
        }

        //得分计算 100 除以 出题数 = 平均每题每分  X 正确题数
        trainResult.setFraction((int) (100.0 / trainSets.size() * trainSets.stream().filter(trainSet -> trainSet.getIsError() == 0).count()));
        //正确率（0-1之间保留2位小数）
        trainResult.setCorrectRate(AppletUtils.retainTwoDecimal(1.0 / set.size() * correct));

        //标题
        if (trainResult.getCorrectRate() >= 0.8) {
            trainResult.setTitle("闯关成功！");
        } else {
            trainResult.setTitle("闯关失败！");
        }
        //时长
        trainResult.setDuration(second);
        //匀速（1位小数）
        trainResult.setAverage(AppletUtils.retainTwoDecimal(Double.valueOf(second) / set.size()));

        //是否进行锁定（不锁定，错5次锁，对5次锁） 错题本查询
        StudentKnowledgeRelation one = studentKnowledgeRelationService.selectStudentRelationBy(student, relationId, type);
        //最高分
        if (trainResult.getFraction() > one.getHighestScore()) {
            one.setHighestScore(trainResult.getFraction());
        }
        studentKnowledgeRelationService.saveOrUpdate(one);

        int isLock = one.getIsLock();
        trainResult.setIsLock(isLock);

        //错题本数据存储 和 返回对象错题数据储存
        List<JSONObject> jsonObjects = new ArrayList<>();
        if (errors.size() > 0) {
            //查询错题中存在多少个知识点
            Map<String, List<ExercisesTrainSet>> collect = errors.stream().collect(Collectors.groupingBy(ExercisesTrainSet::getClassificationId));
            Set<String> keySet = collect.keySet();

            Map<String, JSONObject> knowledgeMap = new HashMap<>();
            //查询知识点相关和诊断数据
            List<JSONObject> knowledgeAndSchedule = knowledgeListClassificationMapper.getKnowledgeAndSchedule(new ArrayList<String>(keySet));
            knowledgeAndSchedule.forEach(josnList->{
                knowledgeMap.put(josnList.get("classificationId").toString(),josnList);
            });

            for (String classificationId : keySet) {
                //获取知识点对应信息
                JSONObject classification = knowledgeMap.get(classificationId);
                //知识点数据
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", classificationId);
                jsonObject.put("name", classification.get("classificationMsg"));
                jsonObject.put("nickname", classification.get("classificationNickname"));


                JSONArray jsonArray = new JSONArray();
                List<ExercisesTrainSet> exercisesTrainSets = collect.get(classificationId);
                for (ExercisesTrainSet exercisesTrainSet : exercisesTrainSets) {
                    JSONObject jsonObjectError = new JSONObject();

                    String errorText = exercisesTrainSet.getExercisesNum() + "=" + parse.get(exercisesTrainSet.getExercisesId());
                    String correctText = exercisesTrainSet.getExercisesNum() + "=" + exercisesTrainSet.getExercisesAnswers();

                    jsonObjectError.put("errorSubject", AppletUtils.analysiErrorFormula(errorText));
                    jsonObjectError.put("correctSubject", AppletUtils.analysiErrorFormula(correctText));
                    //诊断文本 需要查询
                    jsonObjectError.put("diagnosis", classification.get("diagnosisMsg"));
                    jsonArray.add(jsonObjectError);
                }
                jsonObject.put("number", jsonArray.size());
                jsonObject.put("jsonArray", jsonArray);
                jsonObjects.add(jsonObject);
            }
        }

        if (successIds.size() > 0) {
            //删除正确的错题本数据
            LambdaQueryWrapper<WrongQuestionList> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.in(WrongQuestionList::getExercisesId, successIds);
            wrongQuestionListService.remove(deleteWrapper);
        }
        trainResult.setIsLock(isLock);
        studentsService.updateById(student);

        //题相关知识点列表
        trainResult.setList(jsonObjects);

        LambdaQueryWrapper<StudentAnswer> studentAnswerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentAnswerLambdaQueryWrapper.eq(StudentAnswer::getStudentId,student.getStudentId());
        studentAnswerLambdaQueryWrapper.eq(StudentAnswer::getBatchId,id);
        studentAnswerLambdaQueryWrapper.eq(StudentAnswer::getType,type);
        StudentAnswer studentAnswer = studentAnswerService.getOne(studentAnswerLambdaQueryWrapper);
        //题相关知识点列表
        studentAnswer.setAnswerTime(studentAnswer.getAnswerTime()+second);
        studentAnswer.setCorrecNum(correct);
        studentAnswer.setErrorNum(errors.size());
        studentAnswer.setCorrecRate(AppletUtils.retainTwoDecimal(1.0 * successIds.size() / success.size()));
        studentAnswer.setTimeProportion(1.0 * studentAnswer.getAnswerTime()/(studentAnswer.getTotalNum()+set.size()));
        studentAnswerService.updateById(studentAnswer);

        return trainResult;
    }

}
