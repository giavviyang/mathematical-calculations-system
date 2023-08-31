package com.fudian.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.api.system.service.TokenApi;
import com.fudian.business.mapper.*;
import com.fudian.business.pojo.*;
import com.fudian.business.service.*;
import com.fudian.business.vo.TestClassificationAnalysisVo;
import com.fudian.business.vo.TestStudentAnalysisVo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.AppletUtils;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author xuchenghong
 */

@Service
public class TestTableServiceImpl extends ServiceImpl<TestTableMapper, TestTable> implements TestTableService {


    @Resource
    private AudienceMapper audienceMapper;
    @Resource
    private StudentTestRelationService studentTestRelationService;
    @Resource
    private TestExercisesTrainMapper testExercisesTrainMapper;
    @Resource
    private TestKnowledgeRelationMapper testKnowledgeRelationMapper;
    @Resource
    private ExercisesRulesService exercisesRulesService;
    @Resource
    private TestExercisesTrainService testExercisesTrainService;
    @Resource
    private TestAnswerService testAnswerService;
    @Resource
    private KnowledgeListClassificationService knowledgeListClassificationService;
    @Resource
    private KnowledgeListService knowledgeListService;
    @Resource
    private TokenApi tokenApi;
    @Resource
    private StudentsService studentsService;
    @Resource
    private KnowledgeListClassificationMapper  knowledgeListClassificationMapper;

    /**
     * 分页查询
     *
     * @param testTable
     * @return
     */
    @Override
    public CommonGridResult pagingSelectTestTable(TestTable testTable) {
        SysUser user = tokenApi.getLoginUser(ServletUtils.getRequest()).getUser();
        testTable.setCreatorId(user.getUserId());
        List<TestTable> testTables = baseMapper.pagingQueryTestTable(testTable);
        int total = baseMapper.pagingQueryTestTableCount(testTable);
        CommonGridResult commonGridResult = new CommonGridResult(testTable.getPageNum(), testTable.getPageSize(), total, testTables);
        return commonGridResult;
    }

    /**
     * 添加课堂测试相关对应数据
     *
     * @param testTable
     * @return
     */
    @Override
    public CommonResult<?> addTestTable(TestTable testTable) {
        try {
            SysUser user = tokenApi.getLoginUser(ServletUtils.getRequest()).getUser();
            //补全测试表字段
            if (StringUtils.isEmpty(testTable.getId())) {
                testTable.setId(StringUtils.uuid());
            } else {
                testTable.setUpdateBy(user.getUserId());
                testTable.setUpdateTime(new Date());
                //先删除已存在的关联的班级关系
                audienceMapper.deleteByTestId(testTable.getId());
                //先删除已存在的关联的知识点关系
                testKnowledgeRelationMapper.deleteByTestId(testTable.getId());
            }
            testTable.setCreator(user.getUserName());
            testTable.setCreatorId(user.getUserId());
            testTable.setCreateTime(new Date());
            this.saveOrUpdate(testTable);
            //重新绑定新的班级关系
            audienceMapper.batchAdd(testTable.getId(), testTable.getClassRoomId());
            //绑定知识点
            testKnowledgeRelationMapper.batchAdd(testTable.getId(), testTable.getClassification());
            //如果是新增数据同时是统一生成习题在此处调用生成方法生成
            if (testTable.getTopicMode() == 1) {
                testExercisesTrainService.remove(new QueryWrapper<TestExercisesTrain>().eq("test_id", testTable.getId()));
                List<TestExercisesTrain> insertLists = new ArrayList<>();
                //调用生成方法this.getExercisesByTestId
                List<Map<String, String>> data = getExercisesByTestId(testTable.getId());
                List<String> classificationIdList = new ArrayList<>();
                data.forEach(map->{
                    classificationIdList.add(map.get("classifyId"));
                });
                //获取课堂测试关联的知识点相关信息
                Map<String, String> knowledgeMap = new HashMap<>();
                List<JSONObject> knowledgeAndSchedule = knowledgeListClassificationMapper.getKnowledgeAndSchedule(classificationIdList);
                knowledgeAndSchedule.forEach(josnList->{
                    knowledgeMap.put(josnList.get("classificationId").toString(),josnList.get("knowledgeNumField").toString());
                });


                for (Map<String, String> datum : data) {
                    String exercisesId = StringUtils.uuid();
                    String first = datum.get("first");
                    String second = datum.get("second");
                    String operate = datum.get("operate");
                    String three = datum.get("three");
                    switch (operate) {
                        case "1":
                            operate = "+";
                            break;
                        case "2":
                            operate = "-";
                            break;
                        case "3":
                            operate = "×";
                            break;
                        case "4":
                            operate = "÷";
                            break;
                        default:
                            break;
                    }
                    String result = datum.get("result");

                    TestExercisesTrain testExercisesTrain = new TestExercisesTrain();
                    testExercisesTrain.setExercisesId(exercisesId);
                    testExercisesTrain.setTestId(testTable.getId());
                    testExercisesTrain.setClassificationId(datum.get("classifyId"));
                    if (StringUtils.isNotEmpty(three)) {
                        testExercisesTrain.setExercisesNum(first + operate + second + operate + three);
                    } else {
                        testExercisesTrain.setExercisesNum(first + operate + second);
                    }
                    testExercisesTrain.setExercisesAnswers(result);
                    testExercisesTrain.setExercisesType(Integer.valueOf(knowledgeMap.get(datum.get("classifyId"))));
                    insertLists.add(testExercisesTrain);
                }
                //插入习题数据
                testExercisesTrainService.saveBatch(insertLists);


            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonResult.error();
        }
        return CommonResult.success();
    }

    /**
     * 删除课堂测试相关对应数据
     *
     * @param testId
     * @return
     */
    @Override
    public CommonResult<?> removeTestTable(String testId) {
        try {
            //先删除已存在的关联的班级关系
            audienceMapper.deleteByTestId(testId);
            //先删除已存在的关联的知识点关系
            testKnowledgeRelationMapper.deleteByTestId(testId);
            //删除习题表
            testExercisesTrainMapper.deleteByTestId(testId);
            //删除答题表
            testAnswerService.deleteByTestId(testId);
            //删除课堂测试学生关联表
            studentTestRelationService.deleteByTestId(testId);
            //删除课堂测试表
            baseMapper.deleteByTestId(testId);
        } catch (Exception e) {
            e.printStackTrace();
            CommonResult.error();
        }
        return CommonResult.success();
    }

    /**
     * 删除课堂测试相关对应数据
     *
     * @param testIds
     * @return
     */
    @Override
    public CommonResult<?> batchRemoveTestTable(List<String> testIds) {
        try {
            //删除课堂测试表
            baseMapper.deleteByTestIds(testIds);
            //先删除已存在的关联的班级关系
            audienceMapper.deleteByTestIds(testIds);
            //先删除已存在的关联的知识点关系
            testKnowledgeRelationMapper.deleteByTestIds(testIds);
            //删除习题表
            testExercisesTrainMapper.deleteByTestIds(testIds);
            //删除答题表
            testAnswerService.deleteByTestIds(testIds);
            //删除课堂测试学生关联表
            studentTestRelationService.deleteByTestIds(testIds);
        } catch (Exception e) {
            e.printStackTrace();
            CommonResult.error();
        }
        return CommonResult.success();
    }


    /**
     * 根据课堂测试id中的关联知识点和占比生成对应的知识点习题
     *
     * @param testId
     * @return
     */
    @Override
    public List<Map<String, String>> getExercisesByTestId(String testId) {
        List<Map<String, String>> lists = new ArrayList<>();
        //获取课堂测试总题数
        TestTable one = this.getById(testId);
        Integer levelNumTopic = one.getTestTopicNum();

        //获取课堂测试下所有知识点，并且根据知识点占比计算知识点题数
        LambdaQueryWrapper<TestKnowledgeRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TestKnowledgeRelation::getTestId, testId);
        List<TestKnowledgeRelation> list = testKnowledgeRelationMapper.selectList(queryWrapper);
        for (TestKnowledgeRelation testKnowledgeRelation : list) {
            //根据课堂测试总题数和知识点占百分比获取知识点要生成的题数
            Integer percentage = testKnowledgeRelation.getPercentage();
            double v = levelNumTopic * (percentage * 0.01);
            //将知识点题数四舍五入转换为整数
            BigDecimal b = new BigDecimal(v);
            Double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            Integer number = f1.intValue();

            List<Map<String, String>> list1 = (List<Map<String, String>>) exercisesRulesService.generateExercises(testKnowledgeRelation.getClassificationId(), number).getData();
            for (Map<String, String> stringStringMap : list1) {
                lists.add(stringStringMap);
            }
        }
        int size = lists.size();
        if (size > levelNumTopic) {
            //如果生成题数大于课堂测试指定题数，则随机删除多出的题数
            int i = size - levelNumTopic;
            for (int j = 0; j < i; j++) {
                lists.remove(size - j);
            }
        } else if (lists.size() < levelNumTopic) {//如果生成题数小于课堂测试指定题数，则随机添加题数
            int i = levelNumTopic - size;
            List<Map<String, String>> list1 = (List<Map<String, String>>) exercisesRulesService.generateExercises(list.get(0).getClassificationId(), i).getData();
            for (Map<String, String> stringStringMap : list1) {
                lists.add(stringStringMap);
            }
        }
        return lists;
    }


    /**
     * 查询可练习的课堂测试题目
     *
     * @return
     */
    @Override
    public CommonResult<?> queryTestList() {
        Students student = tokenApi.getLoginUser(ServletUtils.getRequest()).getStudent();
        List<Map<String, Object>> maps = baseMapper.selectTestList(student.getStudentId());
        return CommonResult.success(maps);
    }

    /**
     * 根据课堂测试统一生成习题或是随机生成习题来生成对应习题
     *
     * @param testId 课堂测试id
     * @return 习题数量，课堂测试名称，课堂测试id，课堂测试习题集合
     */
    @Override
    public CommonResult<?> generateTestTopic(String testId) {
        Students student = tokenApi.getLoginUser(ServletUtils.getRequest()).getStudent();

        testAnswerService.removeAbandonExercises(student.getStudentId(),testId);

        TestTable testTable = baseMapper.selectById(testId);
        JSONObject jsonObject = new JSONObject();

        String batchId = StringUtils.uuid();
        Date date = new Date();

        jsonObject.put("id", batchId);
        jsonObject.put("title", testTable.getTestName());
        jsonObject.put("count", testTable.getTestTopicNum());
        JSONArray jsonArray = new JSONArray();
        List<TestAnswer> testAnswers = new ArrayList<>();
        //判断生成方式
        if (testTable.getTopicMode() == 1) {
            //统一生成习题  formula-习题，type-类型（整数，分数，小数），result-习题答案，resultType-答案是否是分数 ， 等于-1不是分数， 大于-1 是分数
            LambdaQueryWrapper<TestExercisesTrain> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TestExercisesTrain::getTestId, testId);
            List<TestExercisesTrain> list = testExercisesTrainMapper.selectList(queryWrapper);
            for (TestExercisesTrain train : list) {
                String exercisesId = StringUtils.uuid();
                //存储集合
                TestAnswer testAnswer = new TestAnswer();
                testAnswer.setExercisesId(exercisesId);
                testAnswer.setTestId(testId);
                testAnswer.setClassificationId(train.getClassificationId());
                testAnswer.setStudentId(student.getStudentId());
                testAnswer.setClassId(student.getClassRoomId());
                testAnswer.setExercisesNum(train.getExercisesNum());
                testAnswer.setExercisesType(train.getExercisesType());
                testAnswer.setExercisesAnswers(train.getExercisesAnswers());
                testAnswer.setCreateTime(date);
                testAnswer.setBatchId(batchId);
                testAnswers.add(testAnswer);
                //返回 习题集合
                JSONObject obj = new JSONObject();
                obj.put("id", exercisesId);
                obj.put("formula", AppletUtils.analysiFormula(train.getExercisesNum()));
                obj.put("type", train.getExercisesType());
                obj.put("result", train.getExercisesAnswers());
                obj.put("resultType", train.getExercisesAnswers().indexOf("/"));
                jsonArray.add(obj);
            }
        } else if (testTable.getTopicMode() == 2) {
            //随机生成习题
            //调用生成方法this.getExercisesByTestId
            List<Map<String, String>> data = getExercisesByTestId(testTable.getId());

            List<String> classificationIdList = new ArrayList<>();
            data.forEach(map->{
                classificationIdList.add(map.get("classifyId"));
            });
            //获取课堂测试关联的知识点相关信息
            Map<String, String> knowledgeMap = new HashMap<>();
            List<JSONObject> knowledgeAndSchedule = knowledgeListClassificationMapper.getKnowledgeAndSchedule(classificationIdList);
            knowledgeAndSchedule.forEach(josnList->{
                knowledgeMap.put(josnList.get("classificationId").toString(),josnList.get("knowledgeNumField").toString());
            });



            for (Map<String, String> datum : data) {
                String exercisesId = StringUtils.uuid();
                String first = datum.get("first");
                String second = datum.get("second");
                String operate = datum.get("operate");
                String three = datum.get("three");
                switch (operate) {
                    case "1":
                        operate = "+";
                        break;
                    case "2":
                        operate = "-";
                        break;
                    case "3":
                        operate = "×";
                        break;
                    case "4":
                        operate = "÷";
                        break;
                    default:
                        break;
                }
                String result = datum.get("result");

                TestAnswer testAnswer = new TestAnswer();
                testAnswer.setExercisesId(exercisesId);
                testAnswer.setTestId(testTable.getId());
                testAnswer.setClassificationId(datum.get("classifyId"));

                testAnswer.setStudentId(student.getStudentId());
                testAnswer.setClassId(student.getClassRoomId());

                if (StringUtils.isNotEmpty(three)) {
                    testAnswer.setExercisesNum(first + operate + second + operate + three);
                } else {
                    testAnswer.setExercisesNum(first + operate + second);
                }
                testAnswer.setExercisesType(Integer.valueOf(knowledgeMap.get(datum.get("classifyId"))));
                testAnswer.setExercisesAnswers(result);
                testAnswer.setCreateTime(date);
                testAnswer.setBatchId(batchId);
                testAnswers.add(testAnswer);

                //返回 习题集合
                JSONObject obj = new JSONObject();
                obj.put("id", exercisesId);
                obj.put("formula", AppletUtils.analysiFormula(testAnswer.getExercisesNum()));
                obj.put("type", testAnswer.getExercisesType());
                obj.put("result", testAnswer.getExercisesAnswers());
                obj.put("resultType", testAnswer.getExercisesAnswers().indexOf("/"));
                jsonArray.add(obj);
            }
        }
        //插入习题数据
        testAnswerService.saveBatch(testAnswers);
        //生成的习题集合
        jsonObject.put("list", jsonArray);
        return CommonResult.success(jsonObject);
    }


    /**
     * 答案提交
     *
     * @param batchId 批次id
     * @param json    答题答案
     * @param second  答题用时
     * @return
     */
    @Override
    public JSONObject submitAnswerResult(String batchId, String json, int second) {
        //通过token获取学生信息
        Students students = tokenApi.getLoginUser(ServletUtils.getRequest()).getStudent();
        Students student = studentsService.selectStudentsByStudentId(students.getStudentId());

        //创建返回对象
        JSONObject resultObject = new JSONObject();

        //batchId 根据批次id 查询习题
        LambdaQueryWrapper<TestAnswer> wrapperExercises = new LambdaQueryWrapper<>();
        wrapperExercises.eq(TestAnswer::getBatchId, batchId);
        wrapperExercises.eq(TestAnswer::getStudentId, student.getStudentId());
        List<TestAnswer> trainSets = testAnswerService.list(wrapperExercises);
        TestTable testTable = baseMapper.selectById(trainSets.get(0).getTestId());
        //获取课堂习题条目
        //解析JSON串 成map id对应答案 类型
        Map<String, String> parse = JSON.parseObject(json, Map.class);
        //习题id set
        Set<String> set = parse.keySet();
        //均速
        double average = AppletUtils.retainTwoDecimal(Double.valueOf(second) / set.size());

        int correct = 0;
        //储存答案 判断正确题数
        List<TestAnswer> errors = new ArrayList<>();
        List<TestAnswer> success = new ArrayList<>();
        for (TestAnswer trainSet : trainSets) {
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
                if (value.equals(trainSet.getExercisesAnswers()) || result) {
                    correct++;
                    trainSet.setIsError(0);
                } else {
                    //错题数据
                    errors.add(trainSet);
                }
                //存储答案
                trainSet.setStudentAnswers(parse.get(trainSet.getExercisesId()));
                trainSet.setStudentId(student.getStudentId());
                trainSet.setAnswerTime(second);
                trainSet.setAvgAnswerTime(average);
                success.add(trainSet);
            }
        }
        testAnswerService.updateBatchById(success);
        //获取需要的参数
        //id
        String id = testTable.getId();
        //标题
        String title = testTable.getTestName();
        //得分
        int score = (int) (100.0 / set.size() * correct);
        //正确率
        double correctRate = AppletUtils.retainTwoDecimal(1.0 / set.size() * correct);
        //用时比例
        double coinsTime = 1.0 / Double.valueOf(testTable.getCountTime()) * second;
        //金币
        int coinsNum = 0;
        int isCorrect = 0;
        if (correctRate >= 0.8) {
            if (coinsTime <= 0.5) {
                coinsNum = 10;
            } else if (coinsTime > 0.5 && coinsTime <= 0.6) {
                coinsNum = 9;
            } else if (coinsTime > 0.6 && coinsTime <= 0.7) {
                coinsNum = 8;
            } else if (coinsTime > 0.7 && coinsTime <= 0.8) {
                coinsNum = 7;
            } else if (coinsTime > 0.8 && coinsTime <= 0.9) {
                coinsNum = 6;
            } else if (coinsTime > 0.9 && coinsTime <= 1.0) {
                coinsNum = 5;
            }
            coinsNum = (int) ((100.0 / set.size() * correct) - 80 + coinsNum) / 2;
            isCorrect = 1;
        }
        //时长  传进来的  second
        //错题数量
        int errorNum = errors.size();
        //是否进行锁定（不锁定，错5次锁，对5次锁）???
        //正确率排行
        StudentTestRelation studentTestRelation = new StudentTestRelation();
        studentTestRelation.setTestId(testTable.getId());
        studentTestRelation.setHighestScore(score);
        studentTestRelation.setSecond(null);
        double correctRateRanking = studentTestRelationService.obtainRanking(studentTestRelation);
        //用时排行
        studentTestRelation.setHighestScore(null);
        studentTestRelation.setSecond(second);
        double secondRanking = studentTestRelationService.obtainRanking(studentTestRelation);
        //知识点名称列表???
        //错题相关知识点列表
        List<JSONObject> errorList = new ArrayList<>();
        if (errors.size() > 0) {
            //创建错题返回对象
            JSONObject errorResultObject = new JSONObject();
            errorResultObject.put("number", errors.size());
            errorResultObject.put("id", batchId);
            errorResultObject.put("title", testTable.getTestName());
            JSONArray errorResultArray = new JSONArray();
            //查询错题中存在多少个知识点
            Map<String, List<TestAnswer>> collect = errors.stream().collect(Collectors.groupingBy(TestAnswer::getClassificationId));
            Set<String> keySet = collect.keySet();
            //获取关联的知识点相关信息
            Map<String, JSONObject> knowledgeMap = new HashMap<>();
            List<JSONObject> knowledgeAndSchedule = knowledgeListClassificationMapper.getKnowledgeAndSchedule(new ArrayList<String>(keySet));
            knowledgeAndSchedule.forEach(josnList->{
                knowledgeMap.put(josnList.get("classificationId").toString(),josnList);
            });


            for (String classificationId : keySet) {
                //获取知识点对应信息
                JSONObject classification = knowledgeMap.get(classificationId);
                //创建错题知识点对象
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", classificationId);
                jsonObject.put("name", classification.get("classificationMsg"));
                jsonObject.put("nickname", classification.get("classificationNickname"));

                //创建知识点对应的错题集合
                JSONArray jsonArray = new JSONArray();
                List<TestAnswer> testAnswers = collect.get(classificationId);
                for (TestAnswer testAnswer : testAnswers) {
                    //返回二次做题的错题
                    JSONObject errorObject = new JSONObject();
                    errorObject.put("id", testAnswer.getExercisesId());
                    errorObject.put("formula", AppletUtils.analysiFormula(testAnswer.getExercisesNum()));
                    errorObject.put("type", testAnswer.getExercisesType());
                    errorObject.put("result", testAnswer.getExercisesAnswers());
                    errorObject.put("resultType", testAnswer.getExercisesAnswers().indexOf("/"));
                    errorResultArray.add(errorObject);

                    JSONObject jsonObjectError = new JSONObject();

                    String errorText = testAnswer.getExercisesNum() + "=" + parse.get(testAnswer.getExercisesId());
                    String correctText = testAnswer.getExercisesNum() + "=" + testAnswer.getExercisesAnswers();

                    jsonObjectError.put("errorSubject", AppletUtils.analysiErrorFormula(errorText));
                    jsonObjectError.put("correctSubject", AppletUtils.analysiErrorFormula(correctText));
                    //诊断文本 需要查询
                    jsonObjectError.put("diagnosis", classification.get("diagnosisMsg"));
                    jsonArray.add(jsonObjectError);
                }
                jsonObject.put("number", jsonArray.size());
                jsonObject.put("jsonArray", jsonArray);
                errorList.add(jsonObject);
            }

            errorResultObject.put("list", errorResultArray);
            resultObject.put("errorResultObject", errorResultObject);
        } else {
            //清空错题本
        }

        //存储学生课堂测试关联表
        LambdaQueryWrapper<StudentTestRelation> studentTestQueryWrapper = new LambdaQueryWrapper<>();
        studentTestQueryWrapper.eq(StudentTestRelation::getTestId, testTable.getId());
        studentTestQueryWrapper.eq(StudentTestRelation::getStudentId, student.getStudentId());
        studentTestRelation = studentTestRelationService.getOne(studentTestQueryWrapper);
        if (studentTestRelation == null) {
            studentTestRelation = new StudentTestRelation();
            studentTestRelation.setId(StringUtils.uuid());
            studentTestRelation.setStudentId(student.getStudentId());
            studentTestRelation.setTestId(testTable.getId());
            studentTestRelation.setHighestScore(score);
            studentTestRelation.setCorrectNum(isCorrect);
            studentTestRelation.setErrorNum(1 - isCorrect);
            studentTestRelation.setDeptId(student.getClassRoomId());
            studentTestRelation.setHighestCoinsNum(coinsNum);
            studentTestRelation.setSecond(second);
            studentTestRelation.setIsLock(0);
            studentTestRelation.setIsMaster(0);
        } else {
            studentTestRelation.setCorrectNum(studentTestRelation.getCorrectNum() + isCorrect);
            studentTestRelation.setErrorNum(studentTestRelation.getErrorNum() + (1 - isCorrect));
            if (studentTestRelation.getHighestCoinsNum() < coinsNum) {
                coinsNum = coinsNum - studentTestRelation.getHighestCoinsNum();
                studentTestRelation.setHighestCoinsNum(coinsNum);
            } else {
                coinsNum = 0;
            }
            if (studentTestRelation.getSecond() > second) {
                studentTestRelation.setSecond(second);
            }
            if (studentTestRelation.getHighestScore() < score) {
                studentTestRelation.setHighestScore(score);
            }
        }
        studentTestRelationService.saveOrUpdate(studentTestRelation);
        //给学生加金币
        if (student.getCoinsNum() < coinsNum) {
            student.setCoinsNum(student.getCoinsNum() + coinsNum);
            studentsService.updateById(student);
        }
        //返回对象
        resultObject.put("id", id);
        resultObject.put("title", title);
        resultObject.put("score", score);
        resultObject.put("coins", coinsNum);
        resultObject.put("duration", second);
        resultObject.put("correctRate", correctRate * 100);
        resultObject.put("average", average);
        resultObject.put("correctRateRanking", correctRateRanking);
        resultObject.put("timeRanking", secondRanking);
        resultObject.put("errorNum", errorNum);
        resultObject.put("list", errorList);
        return resultObject;
    }

    /**
     * 错题答案提交
     *
     * @param batchId 批次id
     * @param json    答题答案
     * @param second  答题用时
     * @return
     */
    @Override
    public JSONObject submitErrorAnswerResult(String batchId, String json, int second) {
        //通过token获取学生信息
        Students students = tokenApi.getLoginUser(ServletUtils.getRequest()).getStudent();
        Students student = studentsService.selectStudentsByStudentId(students.getStudentId());

        //创建返回对象
        JSONObject resultObject = new JSONObject();

        //获取课堂习题条目
        //解析JSON串 成map id对应答案 类型
        Map<String, String> parse = JSON.parseObject(json, Map.class);
        Set<String> set = parse.keySet();
        //batchId 根据批次id 查询习题
        LambdaQueryWrapper<TestAnswer> wrapperExercises = new LambdaQueryWrapper<>();
        wrapperExercises.eq(TestAnswer::getBatchId, batchId);
        wrapperExercises.eq(TestAnswer::getStudentId, student.getStudentId());
        List<TestAnswer> trainSets = testAnswerService.list(wrapperExercises);
        TestTable testTable = baseMapper.selectById(trainSets.get(0).getTestId());

        //计算正确题数int correct =
        int correct = 0;

        //储存答案 判断正确题数
        List<TestAnswer> errors = new ArrayList<>();
        List<TestAnswer> success = new ArrayList<>();
        for (TestAnswer trainSet : trainSets) {
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
                if (value.equals(trainSet.getExercisesAnswers()) || result) {
                    trainSet.setIsError(0);
                    correct++;
                } else {
                    //错题数据
                    errors.add(trainSet);
                }
                //存储答案
                trainSet.setStudentAnswers(parse.get(trainSet.getExercisesId()));
                trainSet.setStudentId(student.getStudentId());
                success.add(trainSet);
            }
        }
        testAnswerService.updateBatchById(success);

        //获取需要的参数
        //id
        String id = testTable.getId();
        //标题
        String title = testTable.getTestName();
        //得分
        int score = (int) (100.0 / trainSets.size() * trainSets.stream().filter(trainSet -> trainSet.getIsError() == 0).count());
        //正确率
        double correctRate = AppletUtils.retainTwoDecimal(100.0 / set.size() * correct);
        //平均用时
        double average = AppletUtils.retainTwoDecimal(Double.valueOf(second) / set.size());
        //金币
        //时长  传进来的  second
        //错题数量
        int errorNum = errors.size();
        //错题相关知识点列表
        List<JSONObject> errorList = new ArrayList<>();
        if (errors.size() > 0) {
            //查询错题中存在多少个知识点
            Map<String, List<TestAnswer>> collect = errors.stream().collect(Collectors.groupingBy(TestAnswer::getClassificationId));
            Set<String> keySet = collect.keySet();

            //获取课堂测试关联的知识点相关信息
            Map<String, JSONObject> knowledgeMap = new HashMap<>();
            List<JSONObject> knowledgeAndSchedule = knowledgeListClassificationMapper.getKnowledgeAndSchedule(new ArrayList<String>(keySet));
            knowledgeAndSchedule.forEach(josnList->{
                knowledgeMap.put(josnList.get("classificationId").toString(),josnList);
            });

            for (String classificationId : keySet) {
                //获取知识点对应信息
                JSONObject classification = knowledgeMap.get(classificationId);
                //创建错题知识点对象
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", classificationId);
                jsonObject.put("name", classification.get("classificationMsg"));
                jsonObject.put("nickname", classification.get("classificationNickname"));

                //创建知识点对应的错题集合
                JSONArray jsonArray = new JSONArray();
                List<TestAnswer> testAnswers = collect.get(classificationId);
                for (TestAnswer testAnswer : testAnswers) {
                    JSONObject jsonObjectError = new JSONObject();

                    String errorText = testAnswer.getExercisesNum() + "=" + parse.get(testAnswer.getExercisesId());
                    String correctText = testAnswer.getExercisesNum() + "=" + testAnswer.getExercisesAnswers();

                    jsonObjectError.put("errorSubject", AppletUtils.analysiErrorFormula(errorText));
                    jsonObjectError.put("correctSubject", AppletUtils.analysiErrorFormula(correctText));
                    //诊断文本 需要查询
                    jsonObjectError.put("diagnosis", classification.get("diagnosisMsg"));
                    jsonArray.add(jsonObjectError);
                }
                jsonObject.put("number", jsonArray.size());
                jsonObject.put("jsonArray", jsonArray);
                errorList.add(jsonObject);
            }
        }
        //返回对象
        //存储学生课堂测试关联表
        LambdaQueryWrapper<StudentTestRelation> studentTestQueryWrapper = new LambdaQueryWrapper<>();
        studentTestQueryWrapper.eq(StudentTestRelation::getTestId, testTable.getId());
        studentTestQueryWrapper.eq(StudentTestRelation::getStudentId, student.getStudentId());
        StudentTestRelation studentTestRelation = studentTestRelationService.getOne(studentTestQueryWrapper);
        if (studentTestRelation.getHighestScore() < score) {
            studentTestRelation.setHighestScore(score);
        }
        studentTestRelationService.saveOrUpdate(studentTestRelation);
        resultObject.put("id", id);
        resultObject.put("title", title);
        resultObject.put("score", score);
        resultObject.put("duration", second);
        resultObject.put("correctRate", correctRate);
        resultObject.put("average", average);
        resultObject.put("errorNum", errorNum);
        resultObject.put("list", errorList);
        return resultObject;
    }


    /**
     * 分页查询课堂统一错题
     *
     * @param testClassifVo
     * @return
     */
    @Override
    public CommonGridResult queryClassifyErrorTopic(TestClassificationAnalysisVo testClassifVo) {
        TestTable testTable = baseMapper.selectById(testClassifVo.getTestId());
        Integer topicMode = testTable.getTopicMode();
        List<TestClassificationAnalysisVo> maps = null;
        if (topicMode == 1) {
            maps = baseMapper.selectErrorIntegratedTopic(testClassifVo);
        } else if (topicMode == 2) {
            maps = baseMapper.selectErrorRandomTopic(testClassifVo);
        }
        int total = baseMapper.selectErrorIntegratedTopicCount(testClassifVo);
        CommonGridResult commonGridResult = new CommonGridResult(testClassifVo.getPageNum(), testClassifVo.getPageSize(), total, maps);
        return commonGridResult;
    }

    /**
     * 分页查询课堂统一错题
     *
     * @param testStudentfVo
     * @return
     */
    @Override
    public CommonGridResult queryStudentErrorTopic(TestStudentAnalysisVo testStudentfVo) {
        SysUser user = tokenApi.getLoginUser(ServletUtils.getRequest()).getUser();
        testStudentfVo.setUserId(user.getUserId());

        List<TestStudentAnalysisVo> maps = baseMapper.selectStudentErrorTopic(testStudentfVo);
        int total = baseMapper.selectStudentErrorTopicCount(testStudentfVo);
        CommonGridResult commonGridResult = new CommonGridResult(testStudentfVo.getPageNum(), testStudentfVo.getPageSize(), total, maps);
        return commonGridResult;
    }


}
