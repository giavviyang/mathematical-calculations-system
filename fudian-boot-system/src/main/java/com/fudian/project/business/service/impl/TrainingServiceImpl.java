package com.fudian.project.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fudian.api.system.pojo.Students;
import com.fudian.api.system.pojo.SysDept;
import com.fudian.api.system.pojo.SysDictionary;
import com.fudian.api.system.pojo.SysUser;
import com.fudian.business.mapper.ExercisesTrainSetMapper;
import com.fudian.business.mapper.KnowledgeExercisesTrainMapper;
import com.fudian.business.mapper.KnowledgeListClassificationMapper;
import com.fudian.business.pojo.*;
import com.fudian.business.service.*;
import com.fudian.business.vo.*;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.AppletUtils;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.business.service.TrainingService;
import com.fudian.project.system.security.service.TokenService;
import com.fudian.project.system.service.SysDeptService;
import com.fudian.project.system.service.SysDictionaryService;
import com.fudian.project.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {

    @Resource
    private KnowledgeListClassificationService knowledgeListClassificationService;
    @Resource
    private KnowledgeListService knowledgeListService;
    @Resource
    private KnowledgeListScheduleService knowledgeListScheduleService;
    @Resource
    private PopupService popupService;
    @Resource
    private ExercisesTrainSetService exercisesTrainSetService;
    @Resource
    private ExercisesTrainSetMapper exercisesTrainSetMapper;
    @Resource
    private StudentAnswerService studentAnswerService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private StudentsService studentsService;
    @Autowired
    private StudentKnowledgeRelationService studentKnowledgeRelationService;
    @Autowired
    private WrongQuestionListService wrongQuestionListService;
    @Autowired
    private LevelTableService levelTableService;
    @Autowired
    private ExercisesRulesService exercisesRulesService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private LevelKnowledgeService levelKnowledgeService;
    @Autowired
    private NewsNoticeService newsNoticeService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDeptService sysDeptService;
    @Resource
    private KnowledgeExercisesTrainMapper knowledgeExercisesTrainMapper;
    @Resource
    private DeptKnowledgeRelationService deptKnowledgeRelationService;
    @Resource
    private KnowledgeListClassificationMapper knowledgeListClassificationMapper;


    /**
     * 查询类型 整数，小数，分数
     *
     * @return
     */
    @Override
    public List<Classification> selectTrainData() {


        HttpServletRequest request = ServletUtils.getRequest();
        //获取服务ip
        String serverName = request.getServerName();
        //获取服务端口号
        int serverPort = request.getServerPort();

        //通过token获取学生信息
        Students student = tokenService.getLoginUser(request).getStudent();
        //获取一级知识点数据
        List<KnowledgeVo> knowledgeVos = knowledgeListClassificationService.selectKnowledgeListClassification(student.getStudentId());

        //获取二级知识点数据
        List<KnowledgeVo> knowledgeVoTow = knowledgeListClassificationService.selectKnowledgeListClassificationTow(student.getStudentId());

        Map<String, List<KnowledgeVo>> collect = knowledgeVoTow.stream().collect(Collectors.groupingBy(KnowledgeVo::getPid));

        //查询字典中的知识点分类
        LambdaQueryWrapper<SysDictionary> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictionary::getDictType, "classfiy");
        List<SysDictionary> dictionaries = sysDictionaryService.list(wrapper);

        //获取知识点分类标识 和 别名
        Map<String, String> map = new HashMap<>();
        dictionaries.forEach((dict) -> {
            map.put(dict.getDictCode(), dict.getFullName());
        });

        //获取有多少个数域
        Map<String, List<KnowledgeVo>> numFieldMaps = knowledgeVos.stream().collect(Collectors.groupingBy(KnowledgeVo::getKnowledgeNumField));

        //创建返回对象
        List<Classification> list = new ArrayList<>();

        //循环数域 存储数域相关的返回数据
        for (Map.Entry<String, List<KnowledgeVo>> fields : numFieldMaps.entrySet()) {
            //存储数域值
            Classification resultField = new Classification();
            //数域下的运算数据
            List<KnowledgeVo> fieldList = fields.getValue();
            KnowledgeVo field = fieldList.get(0);
            resultField.setName(map.get(field.getKnowledgeNumField()));
            resultField.setId(field.getKnowledgeId());

            //根据数域下的运算数据获取运算符分类
            Map<String, List<KnowledgeVo>> operMaps = fieldList.stream().collect(Collectors.groupingBy(KnowledgeVo::getKnowledgeOperation));

            //创建返回二级对象
            List<Classification> listOperation = new ArrayList<>();
            //遍历运算类型
            for (Map.Entry<String, List<KnowledgeVo>> opers : operMaps.entrySet()) {
                //存储运算数据
                Classification resultOper = new Classification();
                //数域下的运算数据
                List<KnowledgeVo> operList = opers.getValue();
                KnowledgeVo oper = operList.get(0);
                resultOper.setName(map.get(oper.getKnowledgeNumField() + "-" + oper.getKnowledgeOperation()));
                resultOper.setId(oper.getKnowledgeId());

                //图片地址拼接+id
                String img = "https://" + serverName + ":" + serverPort + "/applet/file/ioReadImage?fileId=" + oper.getIcon();
                resultOper.setIcon(img);

                //根据数域下的运算数据获取运算符分类
                Map<String, List<KnowledgeVo>> typeMaps = operList.stream().collect(Collectors.groupingBy(KnowledgeVo::getKnowledgeType));
                //进位关系
                List<Classification> listType = new ArrayList<>();
                //遍历类型 进位关系
                for (Map.Entry<String, List<KnowledgeVo>> typeMap : typeMaps.entrySet()) {
                    //存储 进位关系
                    Classification resultType = new Classification();
                    //数域下的进位关系
                    List<KnowledgeVo> typeList = typeMap.getValue();
                    KnowledgeVo type = typeList.get(0);
                    resultType.setName(map.get(type.getKnowledgeNumField() + "-" + type.getKnowledgeOperation() + "-" + type.getKnowledgeType()));
                    resultType.setId(oper.getKnowledgeId());

                    //进位关系下的一级知识点数据集合
                    List<Classification> listOne = new ArrayList<>();
                    //遍历进位关系下的一级知识点数据集合
                    for (KnowledgeVo one : typeList) {
                        //存储 一级知识点
                        Classification resultOne = new Classification();
                        //id
                        resultOne.setId(one.getClassificationId());
                        //名称
                        resultOne.setName(one.getClassificationMsg());
                        //是否有弹窗
                        resultOne.setDialog(one.getDialog());
                        //锁定状态
                        resultOne.setLockType(one.getLockType());
                        //获取最高分
                        resultOne.setGrade(one.getGrade());
                        //判断是否有子知识点
                        /*if (one.getIsSubclass() == 0) {
                            listOne.add(resultOne);
                            continue;
                        }*/

                        List<Classification> listTow = new ArrayList<>();
                        List<KnowledgeVo> towList = collect.get(one.getClassificationId());
                        if (one.getIsSubclass() == 0 || towList == null || towList.size() == 0) {
                            resultOne.setList(listTow);
                            listOne.add(resultOne);
                            continue;
                        }
                        for (KnowledgeVo tow : towList) {
                            //存储 一级知识点
                            Classification resultTow = new Classification();
                            //id
                            resultTow.setId(tow.getClassificationId());
                            //名称
                            resultTow.setName(tow.getClassificationMsg());
                            //是否有弹窗
                            resultTow.setDialog(tow.getDialog());
                            //锁定状态
                            resultTow.setLockType(tow.getLockType());
                            //获取最高分
                            resultTow.setGrade(tow.getGrade());

                            listTow.add(resultTow);
                        }

                        resultOne.setList(listTow);
                        listOne.add(resultOne);
                    }
                    resultType.setList(listOne);
                    listType.add(resultType);
                }
                resultOper.setList(listType);
                listOperation.add(resultOper);
            }
            resultField.setList(listOperation);
            list.add(resultField);
        }
        return list;
    }


    /**
     * 查询例题弹窗
     *
     * @return
     */
    @Override
    public KnowledgePopup selectKnowledgePopup(String knowledgeId) {
        KnowledgePopup knowledgePopup = new KnowledgePopup();

        //根据知识点id查询知识点数据
        KnowledgeListClassification knowledge = knowledgeListClassificationService.selectKnowledgeListClassificationById(knowledgeId);
        //题名
        if (knowledge.getExampleTitle() != null) {
            knowledgePopup.setTitle(knowledge.getExampleTitle());
        } else {
            knowledgePopup.setTitle("与整数运算的对应");
        }

        //根据知识点id查询例题内容
        Popup popup = new Popup();
        popup.setClassificationId(knowledgeId);
        List<Popup> popups = popupService.selectPopup(popup);

        List<Example> list = new ArrayList<>();
        for (Popup po : popups) {
            Example example = new Example();
            example.setContent(po.getContent());
            example.setExampleType(po.getType());

            //拆解公式1
            example.setFormulaOne(AppletUtils.analysiFormula(po.getFormulaOne()));

            //拆解公式2÷
            if (po.getType() == 1) {
                example.setFormulaTwo(AppletUtils.analysiFormula(po.getFormulaTwo()));
            } else {
                example.setFormulaTwo(null);
            }

            list.add(example);
        }

        knowledgePopup.setList(list);

        return knowledgePopup;
    }


    /**
     * 根据知识点id/关卡id查询题目列表
     *
     * @return
     */
    @Override
    public CommonResult selectSubjectListById(String id, int type) {
        //返回数据
        Map<String, Object> map = new HashMap<>();
        //获取当前登录学生信息
        Students student = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();

        //根据学生、知识点id/关卡id查询是否有对应未做的习题
        exercisesTrainSetMapper.batchDeleteExercisesSet(student.getStudentId(), type, id);
        //获取习题列表
        //习题列表
        List<Map<String, String>> objects = new ArrayList<>();
        //知识点标题
        String title = "";
        //题目数量
        Integer number = null;
        //习题批次id
        String batchId = StringUtils.uuid();
        //判断是否有未做的题目//没有未做的习题则生成
        List<ExercisesTrainSet> insertLists = new ArrayList<>();
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, Integer> knowledgeTypeMap = null;
        int isRandom = 0;
        if (type == 0 || type == 2) {
            knowledgeTypeMap = new HashMap<>();
            //知识点生成习题
            //获取知识点信息
            KnowledgeListClassification knowledgeListClassification = knowledgeListClassificationService.getById(id);
            KnowledgeList KnowledgeList = knowledgeListService.getById(knowledgeListClassification.getKnowledgeId());
            knowledgeTypeMap.put(knowledgeListClassification.getClassificationId(), Integer.valueOf(KnowledgeList.getKnowledgeType()));
            int flag = deptKnowledgeRelationService.queryDeptSetTopicMode(student.getClassRoomId(), id);
            if (flag == 1 && type == 0) {
                data = knowledgeExercisesTrainMapper.getUnifiedExercises(id);
                isRandom = 1;
            } else {
                data = (List<Map<String, String>>) exercisesRulesService.generateExercises(id, null).getData();
            }
            title = knowledgeListClassification.getClassificationMsg();
            number = knowledgeListClassification.getQuestionsNumber();
        } else if (type == 1) {
            knowledgeTypeMap = levelTableService.getKnowledgeType(id);
            //关卡生成习题
            LevelTable levelTable = levelTableService.getById(id);
            title = "第" + levelTable.getLevelName() + "关";
            number = Integer.valueOf(levelTable.getLevelNumTopic());
            data = (List<Map<String, String>>) levelTableService.getExercisesByLevelId(id).getData();
        }
        Date date = new Date();
        for (Map<String, String> datum : data) {
            String exercisesId = StringUtils.uuid();
            Map<String, String> stringStringMap = new HashMap<>();
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
            stringStringMap.put("id", exercisesId);
            if (StringUtils.isNotEmpty(three)) {
                stringStringMap.put("formula", AppletUtils.analysiFormula(first + operate + second + operate + three));
            } else {
                stringStringMap.put("formula", AppletUtils.analysiFormula(first + operate + second));
            }
            stringStringMap.put("resultType", "" + result.indexOf("/"));
            stringStringMap.put("type", Integer.valueOf(type).toString());
            stringStringMap.put("result", result);
            //放入习题信息
            objects.add(stringStringMap);

            String levelId = "";
            if (type == 1) {
                levelId = id;
            }
            Integer knowledgeType = knowledgeTypeMap.get(datum.get("classifyId"));
            ExercisesTrainSet exercisesTrainSet = new ExercisesTrainSet();
            exercisesTrainSet.setExercisesId(exercisesId);
            exercisesTrainSet.setStudentId(student.getStudentId());
            exercisesTrainSet.setClassId(student.getClassRoomId());
            if (StringUtils.isNotEmpty(three)) {
                exercisesTrainSet.setExercisesNum(first + operate + second + operate + three);
            } else {
                exercisesTrainSet.setExercisesNum(first + operate + second);
            }
            exercisesTrainSet.setExercisesAnswers(result);
            exercisesTrainSet.setClassificationId(datum.get("classifyId"));
            exercisesTrainSet.setBatchId(batchId);
            exercisesTrainSet.setType(type);
            exercisesTrainSet.setIsRandom(isRandom);
            exercisesTrainSet.setCreateTime(date);
            exercisesTrainSet.setExercisesType(knowledgeType);
            if (StringUtils.isNotEmpty(levelId)) {
                exercisesTrainSet.setLevelId(levelId);
            }
            insertLists.add(exercisesTrainSet);
        }
        //插入习题数据
        exercisesTrainSetService.saveBatch(insertLists);
        map.put("id", batchId);
        map.put("title", title);
        map.put("number", number.toString());
        map.put("list", objects);
        return CommonResult.success(map);
    }


    /**
     * 答案提交
     *
     * @return
     */
    @Transactional
    @Override
    public TrainResult submitAnswerResult(String id, String json, int second) {

        //通过token获取学生信息
        Students students = tokenService.getLoginUser(ServletUtils.getRequest()).getStudent();
        Students student = studentsService.selectStudentsByStudentId(students.getStudentId());

        //创建返回对象
        TrainResult trainResult = new TrainResult();
        //知识点数据
        KnowledgeListClassification knowledge = null;
        //关卡数据
        LevelTable levelById = null;
        //查询所有习题
        LambdaQueryWrapper<ExercisesTrainSet> wrapperExercises = new LambdaQueryWrapper<>();
        wrapperExercises.eq(ExercisesTrainSet::getBatchId, id);
        wrapperExercises.in(ExercisesTrainSet::getStudentId, student.getStudentId());
        List<ExercisesTrainSet> trainSets = exercisesTrainSetService.list(wrapperExercises);

        //获取随便一条数据判断是那种类型的习题
        ExercisesTrainSet trainSetOne = null;
        //计算正确题数
        Map<String, String> parse = JSON.parseObject(json, Map.class);
        Set<String> set = parse.keySet();
        //出题数  set.size()
        //计算正确题数
        int correct = 0;
        //储存答案 判断正确题数
        List<ExercisesTrainSet> errors = new ArrayList<>();
        List<ExercisesTrainSet> success = new ArrayList<>();
        List<ExercisesTrainSet> list = new ArrayList<>();
        for (ExercisesTrainSet trainSet : trainSets) {
            if (parse.containsKey(trainSet.getExercisesId())) {
                String value = parse.get(trainSet.getExercisesId());
                boolean result = false;
                try {
                    if (value.contains("/") && trainSet.getExercisesAnswers().contains("/")) {
                        String[] splitA = value.split("/");
                        String[] splitB = trainSet.getExercisesAnswers().split("/");
                        result = splitA[0].equals(splitB[0]) && splitA[1].equals(splitB[1]);
                    } else if (!value.contains("/") && !trainSet.getExercisesAnswers().contains("/")) {
                        result = Double.parseDouble(value) == Double.parseDouble(trainSet.getExercisesAnswers());
                    } else {
                        result = false;
                    }
                } catch (NumberFormatException e) {
                    result = false;
                }

                trainSet.setIsError(1);
                //存储答案
                trainSet.setErrorAnswers(parse.get(trainSet.getExercisesId()));
                trainSet.setStudentId(student.getStudentId());
                if (value.equals(trainSet.getExercisesAnswers()) || result) {
                    correct++;
                    trainSet.setIsError(0);
                    success.add(trainSet);
                } else {
                    //错题数据
                    errors.add(trainSet);
                }
                list.add(trainSet);
                if (trainSetOne == null) {
                    trainSetOne = trainSet;
                }
            }
        }
        exercisesTrainSetService.updateBatchById(list);

        int type = trainSetOne.getType();
        String relationId = "";
        String relationName = "";
        //查询对应知识点或关卡数据 设置返回对象id
        if (type == 0 || type == 2) {
            //根据知识点id查询知识点数据
            knowledge = knowledgeListClassificationService.selectKnowledgeListClassificationById(trainSetOne.getClassificationId());
            relationId = knowledge.getClassificationId();
            //判断知识点是否存在
            if (knowledge == null) {
                return trainResult;
            }
            //标题
            trainResult.setTitle(knowledge.getClassificationMsg());
            trainResult.setId(knowledge.getClassificationId());
            if (StringUtils.isNotEmpty(knowledge.getClassificationNickname())) {
                relationName = knowledge.getClassificationNickname();
            } else {
                relationName = knowledge.getClassificationMsg();
            }
            if (type == 2) {
                relationName = relationName + "错题本";
            }
        } else if (type == 1) {
            levelById = levelTableService.getById(trainSetOne.getLevelId());
            relationId = levelById.getLevelId();
            //判断关卡是否存在
            if (levelById == null) {
                return trainResult;
            }
            trainResult.setId(levelById.getLevelId());
            SysDept sysDept = sysDeptService.getById(levelById.getGradeNum());
            relationName = sysDept.getDeptName() + "-第" + levelById.getLevelName() + "关";
            LambdaQueryWrapper<LevelTable> queryWrapper = new LambdaQueryWrapper<>();
            Integer integer = Integer.parseInt(levelById.getLevelName()) + 1;

            queryWrapper.eq(LevelTable::getLevelName, integer);
            queryWrapper.eq(LevelTable::getGradeNum, levelById.getGradeNum());
            LevelTable one = levelTableService.getOne(queryWrapper);
            if (one != null) {
                trainResult.setNextId(one.getLevelId());
            }
        }

        //得分计算 100 除以 出题数 = 平均每题每分  X 正确题数
        trainResult.setFraction((int) (100.0 / set.size() * correct));
        //正确率（0-1之间保留2位小数）
        trainResult.setCorrectRate(AppletUtils.retainTwoDecimal(1.0 / set.size() * correct));

        //用时比例
        double coinsTime = 0.0;
        if (type == 0 || type == 2) {
            coinsTime = 1.0 / knowledge.getClassificationTime() * second;
        } else if (type == 1) {
            coinsTime = 1.0 / Double.parseDouble(levelById.getLevelTime()) * second;
            //标题
            if (trainResult.getCorrectRate() >= 0.8) {
                trainResult.setTitle("闯关成功！");
            } else {
                trainResult.setTitle("闯关失败！");
            }
        }
        //金币数
        int coinsNum = 0;
        if (trainResult.getCorrectRate() >= 0.8) {
            if (coinsTime <= 0.5) {
                coinsNum = 20;
            } else if (coinsTime > 0.5 && coinsTime <= 0.6) {
                coinsNum = 18;
            } else if (coinsTime > 0.6 && coinsTime <= 0.7) {
                coinsNum = 16;
            } else if (coinsTime > 0.7 && coinsTime <= 0.8) {
                coinsNum = 14;
            } else if (coinsTime > 0.8 && coinsTime <= 0.9) {
                coinsNum = 12;
            } else if (coinsTime > 0.9 && coinsTime <= 1.0) {
                coinsNum = 10;
            }
            coinsNum = (int) ((100.0 / set.size() * correct) - 80 + coinsNum);

        }
        //时长
        trainResult.setDuration(second);
        //匀速（1位小数）
        trainResult.setAverage(AppletUtils.retainTwoDecimal(1.0 * second / set.size()));
        //正确率排行（0-1之间保留2位小数）
        StudentAnswer answer = new StudentAnswer();
        answer.setCorrecRate(trainResult.getCorrectRate());
        answer.setClassificationId(relationId);
        answer.setAnswerTime(second);
        answer.setType(type);
        answer.setClassId(student.getClassRoomId());
        answer.setBatchId(id);
        Double aDouble = studentAnswerService.correctRateRanking(answer);
        Double bDouble = studentAnswerService.correctTimeRanking(answer);
        if (aDouble != null) {
            trainResult.setCorrectRateRanking(AppletUtils.retainTwoDecimal(aDouble));
        } else {
            trainResult.setCorrectRateRanking(1.0);
        }
        //用时排行（0-1之间保留2位小数）
        if (bDouble != null) {
            trainResult.setTimeRanking(AppletUtils.retainTwoDecimal(bDouble));
        } else {
            trainResult.setTimeRanking(1.0);
        }

        //是否进行锁定（不锁定，错5次锁，对5次锁） 错题本查询
        StudentKnowledgeRelation one = studentKnowledgeRelationService.selectStudentRelationBy(student, relationId, type);
        one.setRelationName(relationName);
        int isLock = 0;
        if (trainResult.getCorrectRate() >= 0.8) {
            one.setCorrectNum(one.getCorrectNum() + 1);
            one.setIsMaster(1);
            if (type == 1) {
                List<LevelKnowledge> levelKnowledges = levelKnowledgeService.selectLevelKnowledgeByLevelId(relationId);
                Map<String, List<ExercisesTrainSet>> collect = success.stream().collect(Collectors.groupingBy(ExercisesTrainSet::getClassificationId));
                for (LevelKnowledge levelKnowledge : levelKnowledges) {
                    if (collect.containsKey(levelKnowledge.getClassificationId())) {
                        List<ExercisesTrainSet> exercisesTrainSets = collect.get(levelKnowledge.getClassificationId());
                        Map<Integer, List<ExercisesTrainSet>> exercises = exercisesTrainSets.stream().collect(Collectors.groupingBy(ExercisesTrainSet::getIsError));
                        if (exercises.get(0) != null && exercises.get(0).size() > 0) {
                            double successRate = 1.0 * exercises.get(0).size() / exercisesTrainSets.size();
                            //知识点出题数
                            if (Integer.parseInt(levelKnowledge.getPercentage()) >= 50 && successRate >= 0.8 || Integer.parseInt(levelKnowledge.getPercentage()) < 50 && successRate >= 1.0) {
                                //查询关联知识点信息
                                StudentKnowledgeRelation studentKnowledgeRelation = studentKnowledgeRelationService.selectStudentRelationBy(student, levelKnowledge.getClassificationId(), 0);
                                if (studentKnowledgeRelation == null) {
                                    studentKnowledgeRelation.setIsMaster(1);
                                    studentKnowledgeRelation.setRelationId(levelKnowledge.getClassificationId());
                                    studentKnowledgeRelation.setStudentId(students.getStudentId());
                                    studentKnowledgeRelation.setStudentName(students.getStudentName());
                                    studentKnowledgeRelation.setRelationType(0);
                                    studentKnowledgeRelation.setDeptId(students.getClassRoomId());
                                    studentKnowledgeRelationService.saveOrUpdate(studentKnowledgeRelation);
                                    //题相关知识点列表
                                    String uuid = StringUtils.uuid();
                                    StudentAnswer studentAnswer = new StudentAnswer();
                                    studentAnswer.setStudentId(student.getStudentId());
                                    studentAnswer.setClassificationId(levelKnowledge.getClassificationId());
                                    studentAnswer.setAnswerTime(second / levelKnowledges.size());
                                    studentAnswer.setCorrecNum(exercises.get(0) == null ? 0 : exercises.get(0).size());
                                    studentAnswer.setErrorNum(exercises.get(1) == null ? 0 : exercises.get(1).size());
                                    studentAnswer.setOriginalTitle(json);
                                    studentAnswer.setType(4);
                                    studentAnswer.setTotalNum(set.size());
                                    studentAnswer.setCorrecRate(successRate);
                                    studentAnswer.setTimeProportion(1.0 * second / levelKnowledges.size() / exercisesTrainSets.size());
                                    studentAnswer.setBatchId(uuid);
                                    studentAnswerService.insertStudentAnswer(studentAnswer);
                                }
                            }
                        }
                    }
                }
                //levelKnowledges
            } else if (type == 2 && errors.size() == 0) {
                //错题本练习无错题 则掌握当前知识点
                StudentKnowledgeRelation oneError = studentKnowledgeRelationService.selectStudentRelationBy(student, relationId, 0);

                oneError.setIsMaster(1);
                oneError.setRelationId(relationId);
                oneError.setRelationName(relationName);
                oneError.setStudentId(students.getStudentId());
                oneError.setStudentName(students.getStudentName());
                oneError.setRelationType(0);
                oneError.setDeptId(students.getClassRoomId());
                studentKnowledgeRelationService.saveOrUpdate(oneError);
            }
            //正确次数是否大于指定数 大于则锁定
            if (one.getCorrectNum() >= 5) {
                one.setIsLock(2);
                isLock = 2;
                one.setLockTime(new Date());
                one.setErrorNum(0);
                /*newsNoticeGenerate(student, type, relationName);*/
            }
        } else {
            //错误次数
            one.setErrorNum(one.getErrorNum() + 1);
            if (one.getErrorNum() >= 5) {
                if (type == 1) {
                    Set<String> classifName = new HashSet<>();
                    //查询所有的错题对应知识点
                    Set<String> collect = trainSets.stream().map(ExercisesTrainSet::getClassificationId).collect(Collectors.toSet());
                    Collection<KnowledgeListClassification> classifications = knowledgeListClassificationService.listByIds(collect);
                    //根据错题查找对应知识点
                    for (KnowledgeListClassification classification : classifications) {
                        classifName.add(classification.getClassificationMsg());
                    }
                    trainResult.setClassification(classifName);
                }
                one.setIsLock(1);
                isLock = 1;
                newsNoticeGenerateError(student, type, relationName);
                one.setLockTime(new Date());
            }
        }
        trainResult.setIsLock(isLock);
        //最高分
        if (trainResult.getFraction() > one.getHighestScore()) {
            one.setHighestScore(trainResult.getFraction());
        }
        //判断本次金币数是否比历史金币数高
        if (type == 1) {
            coinsNum = (int) coinsNum / 2;
            if (coinsNum > one.getHighestCoinsNum()) {
                trainResult.setCoins(coinsNum);
                student.setCoinsNum(student.getCoinsNum() + (coinsNum - one.getHighestCoinsNum()));
                one.setHighestCoinsNum(coinsNum);
            }
        } else {
            trainResult.setCoins(coinsNum);
            student.setCoinsNum(student.getCoinsNum() + coinsNum);
        }
        studentsService.updateById(student);
        studentKnowledgeRelationService.saveOrUpdate(one);
        //错题数量
        trainResult.setErrorNum(errors.size());


        //错题本数据存储 和 返回对象错题数据储存
        List<JSONObject> jsonObjects = new ArrayList<>();
        if (errors.size() > 0) {
            //创建错题返回对象
            JSONObject errorResultObject = new JSONObject();
            errorResultObject.put("number", errors.size());
            errorResultObject.put("id", id);
            errorResultObject.put("title", relationName);
            JSONArray errorResultArray = new JSONArray();
            //查询错题中存在多少个知识点
            Map<String, List<ExercisesTrainSet>> collect = errors.stream().collect(Collectors.groupingBy(ExercisesTrainSet::getClassificationId));
            Set<String> keySet = collect.keySet();

            //获取关卡关联的知识点相关信息
            Map<String, JSONObject> knowledgeMap = new HashMap<>();
            List<JSONObject> knowledgeAndSchedule = knowledgeListClassificationMapper.getKnowledgeAndSchedule(new ArrayList<String>(keySet));
            knowledgeAndSchedule.forEach(josnList -> {
                knowledgeMap.put(josnList.get("classificationId").toString(), josnList);
            });


            //错题insert集合
            ArrayList<WrongQuestionList> wrongQuestionLists = new ArrayList<>();
            for (String classificationId : keySet) {

                //获取知识点对应信息
                JSONObject classification = knowledgeMap.get(classificationId);
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("id", classificationId);
                jsonObject.put("name", classification.get("classificationMsg"));
                jsonObject.put("nickname", classification.get("classificationNickname"));


                JSONArray jsonArray = new JSONArray();
                List<ExercisesTrainSet> exercisesTrainSets = collect.get(classificationId);
                for (ExercisesTrainSet exercisesTrainSet : exercisesTrainSets) {
                    JSONObject jsonObjectError = new JSONObject();
                    //返回二次做题的错题
                    JSONObject errorObject = new JSONObject();
                    errorObject.put("id", exercisesTrainSet.getExercisesId());
                    errorObject.put("formula", AppletUtils.analysiFormula(exercisesTrainSet.getExercisesNum()));
                    errorObject.put("type", classification.get("knowledgeNumField"));
                    errorObject.put("result", exercisesTrainSet.getExercisesAnswers());
                    errorObject.put("resultType", exercisesTrainSet.getExercisesAnswers().indexOf("/"));
                    errorResultArray.add(errorObject);

                    //创建错题本对象 存储 错题本
                    WrongQuestionList questionList = new WrongQuestionList();
                    //给错题本对象赋值
                    questionList.setStudentId(student.getStudentId());
                    questionList.setClassificationId(exercisesTrainSet.getClassificationId());

                    String errorText = exercisesTrainSet.getExercisesNum() + "=" + parse.get(exercisesTrainSet.getExercisesId());
                    String correctText = exercisesTrainSet.getExercisesNum() + "=" + exercisesTrainSet.getExercisesAnswers();
                    questionList.setErrorContent(correctText + "|" + errorText + "|" + classification.get("diagnosisMsg"));
                    //数域
                    questionList.setNumfieldId("numfield" + classification.get("knowledgeNumField"));
                    //运算方式
                    questionList.setAlgorithmId("algorithm" + classification.get("knowledgeOperation"));
                    questionList.setBatchId(exercisesTrainSet.getBatchId());
                    questionList.setCreateTime(new Date());
                    questionList.setExercisesId(exercisesTrainSet.getExercisesId());
                    questionList.setDeleted(0);
                    questionList.setType(type);
                    String uuid = UUID.randomUUID().toString();
                    questionList.setId(uuid);
                    wrongQuestionLists.add(questionList);

                    jsonObjectError.put("errorSubject", AppletUtils.analysiErrorFormula(errorText));
                    //诊断文本 需要查询
                    jsonObjectError.put("diagnosis", classification.get("diagnosisMsg"));
                    jsonArray.add(jsonObjectError);
                }
                jsonObject.put("number", jsonArray.size());
                jsonObject.put("jsonArray", jsonArray);
                jsonObjects.add(jsonObject);
            }
            errorResultObject.put("list", errorResultArray);
            trainResult.setErrorResultObject(errorResultObject);
            //存储错题
            wrongQuestionListService.saveBatch(wrongQuestionLists);
        } else if (type == 2) {
            //清空错题本
            UpdateWrapper<WrongQuestionList> wrapperWrong = new UpdateWrapper<>();
            wrapperWrong.eq("student_id", student.getStudentId());
            wrapperWrong.eq("classification_id", knowledge.getClassificationId());
            wrapperWrong.set("deleted", 1);
            wrongQuestionListService.update(wrapperWrong);
        }

        //题相关知识点列表
        trainResult.setList(jsonObjects);
        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setStudentId(student.getStudentId());
        studentAnswer.setClassificationId(relationId);
        studentAnswer.setAnswerTime(second);
        studentAnswer.setCorrecNum(correct);
        studentAnswer.setErrorNum(errors.size());
        studentAnswer.setOriginalTitle(json);
        studentAnswer.setType(type);
        studentAnswer.setTotalNum(set.size());
        studentAnswer.setCorrecRate(trainResult.getCorrectRate());
        studentAnswer.setTimeProportion(trainResult.getAverage());
        studentAnswer.setBatchId(id);
        studentAnswer.setIsRandom(trainSetOne.getIsRandom());
        studentAnswerService.insertStudentAnswer(studentAnswer);


        return trainResult;
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
                    if (value.contains("/") && trainSet.getExercisesAnswers().contains("/")) {
                        String[] splitA = value.split("/");
                        String[] splitB = trainSet.getExercisesAnswers().split("/");
                        result = splitA[0].equals(splitB[0]) && splitA[1].equals(splitB[1]);
                    } else if (!value.contains("/") && !trainSet.getExercisesAnswers().contains("/")) {
                        result = Double.parseDouble(value) == Double.parseDouble(trainSet.getExercisesAnswers());
                    } else {
                        result = false;
                    }
                } catch (NumberFormatException e) {
                    result = false;
                }
                trainSet.setIsError(1);
                //存储答案
                trainSet.setErrorAnswers(parse.get(trainSet.getExercisesId()));
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
        //保存习题
        exercisesTrainSetService.updateBatchById(success);

        int type = trainSetOne.getType();

        //知识点数据
        KnowledgeListClassification knowledge = knowledgeListClassificationService.selectKnowledgeListClassificationById(trainSetOne.getClassificationId());
        String relationId = knowledge.getClassificationId();
        //判断知识点是否存在
        if (knowledge == null) {
            return trainResult;
        }
        //标题
        trainResult.setId(relationId);
        if (StringUtils.isNotEmpty(knowledge.getClassificationNickname())) {
            trainResult.setTitle(knowledge.getClassificationNickname());
        } else {
            trainResult.setTitle(knowledge.getClassificationMsg());
        }
        //得分计算 100 除以 出题数 = 平均每题每分  X 正确题数
        trainResult.setFraction((int) (100.0 / trainSets.size() * trainSets.stream().filter(trainSet -> trainSet.getIsError() == 0).count()));
        //正确率（0-1之间保留2位小数）
        trainResult.setCorrectRate(AppletUtils.retainTwoDecimal(1.0 / set.size() * correct));

        //时长
        trainResult.setDuration(second);
        //匀速（1位小数）
        trainResult.setAverage(AppletUtils.retainTwoDecimal(1.0 * second / set.size()));

        //是否进行锁定（不锁定，错5次锁，对5次锁） 错题本查询
        StudentKnowledgeRelation one = studentKnowledgeRelationService.selectStudentRelationBy(student, relationId, type);
        //最高分
        if (trainResult.getFraction() > one.getHighestScore()) {
            one.setHighestScore(trainResult.getFraction());
        }
        studentKnowledgeRelationService.saveOrUpdate(one);
        //错题数量
        trainResult.setErrorNum(errors.size());

        //错题本数据存储 和 返回对象错题数据储存
        List<JSONObject> jsonObjects = new ArrayList<>();
        if (errors.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", relationId);
            jsonObject.put("name", knowledge.getClassificationMsg());
            jsonObject.put("nickname", knowledge.getClassificationNickname());
            KnowledgeListSchedule schedule = knowledgeListScheduleService.getOne(new LambdaQueryWrapper<KnowledgeListSchedule>().eq(KnowledgeListSchedule::getClassificationId, relationId));
            JSONArray jsonArray = new JSONArray();
            for (ExercisesTrainSet exercisesTrainSet : errors) {
                JSONObject jsonObjectError = new JSONObject();
                String errorText = exercisesTrainSet.getExercisesNum() + "=" + parse.get(exercisesTrainSet.getExercisesId());
                String correcText = exercisesTrainSet.getExercisesNum() + "=" + exercisesTrainSet.getExercisesAnswers();

                jsonObjectError.put("errorSubject", AppletUtils.analysiErrorFormula(errorText));
                jsonObjectError.put("correcSubject", AppletUtils.analysiErrorFormula(correcText));
                //诊断文本 需要查询
                jsonObjectError.put("diagnosis", schedule.getDiagnosisMsg());
                jsonArray.add(jsonObjectError);
            }
            jsonObject.put("number", jsonArray.size());
            jsonObject.put("jsonArray", jsonArray);
            jsonObjects.add(jsonObject);
        }


        if (successIds.size() > 0) {
            //删除正确的错题本数据
            LambdaQueryWrapper<WrongQuestionList> deleteWrapper = new LambdaQueryWrapper<>();
            deleteWrapper.in(WrongQuestionList::getExercisesId, successIds);
            wrongQuestionListService.remove(deleteWrapper);
        }
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



    /******************  分割线  ********************/


    public void newsNoticeGenerateError(Students student, int type, String relationName) {
        List<SysUser> sysUsers = sysUserService.querySysUserByClassId(student.getClassRoomId());
        if (sysUsers == null || sysUsers.size() == 0) {
            return;
        }
        ArrayList<NewsNotice> newsNotices = new ArrayList<>();
        for (SysUser sysUser : sysUsers) {
            NewsNotice newsNotice = new NewsNotice();
            newsNotice.setId(UUID.randomUUID().toString());
            newsNotice.setSenderId(student.getStudentId());
            newsNotice.setSenderName(student.getStudentName());
            newsNotice.setAddresseeId(sysUser.getUserId());
            newsNotice.setAddresseeName(sysUser.getUserName());
            newsNotice.setNewsType(type);
            if (type == 1) {
                newsNotice.setNewsContent("尊敬的" + sysUser.getUserName() + "教师，您的学生" + student.getStudentName() + "，练习" + relationName + "失败5次，已被锁定，请求您线下教学。");
            } else {
                newsNotice.setNewsContent("尊敬的" + sysUser.getUserName() + "教师，您的学生" + student.getStudentName() + "，练习" + relationName + "失败5次，请求您线下教学。");
            }
            newsNotice.setNoticePortrait(student.getStudentPortrait());
            newsNotice.setCreateTime(new Date());
            newsNotice.setIsRead(0);
            newsNotices.add(newsNotice);
        }
        newsNoticeService.saveBatch(newsNotices);
        NewsNotice newsNotice = new NewsNotice();
        newsNotice.setId(UUID.randomUUID().toString());
        newsNotice.setSenderId("admin");
        newsNotice.setSenderName("系统通知");
        newsNotice.setAddresseeId(student.getStudentId());
        newsNotice.setAddresseeName(student.getStudentName());
        newsNotice.setNewsType(type);
        if (type == 1) {
            newsNotice.setNewsContent("系统通知，" + relationName + "重复5次未通过，建议请教老师哦~~~");
        } else {
            newsNotice.setNewsContent("系统通知，" + relationName + "重复5次未通过，已被锁定，建议请教老师哦~~~");
        }
        newsNotice.setNoticePortrait("systemImage");
        newsNotice.setCreateTime(new Date());
        newsNotice.setIsRead(0);
        newsNoticeService.save(newsNotice);
    }


}
