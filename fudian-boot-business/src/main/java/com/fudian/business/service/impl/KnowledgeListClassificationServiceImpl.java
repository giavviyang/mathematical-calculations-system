package com.fudian.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.SysDictionary;
import com.fudian.api.system.service.SysDictionaryApi;
import com.fudian.business.mapper.*;
import com.fudian.business.pojo.*;
import com.fudian.business.service.*;
import com.fudian.business.vo.KnowledgeVo;
import com.fudian.business.vo.QuestionSettingVo;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.vo.CommonResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author xuchenghong
 */

@Service
@CacheConfig(cacheNames = "TrainingServiceImpl")
public class KnowledgeListClassificationServiceImpl extends ServiceImpl<KnowledgeListClassificationMapper, KnowledgeListClassification> implements KnowledgeListClassificationService {


    @Resource
    private KnowledgeListMapper knowledgeListMapper;

    @Resource
    private KnowledgeListScheduleMapper knowledgeListScheduleMapper;

    @Resource
    private PopupMapper popupMapper;

    @Autowired
    private LevelKnowledgeService levelKnowledgeService;

    @Autowired
    private ExercisesRulesService exercisesRulesService;

    @Autowired
    private ExercisesTrainSetService exercisesTrainSetService;

    @Autowired
    private StudentAnswerService studentAnswerService;

    @Autowired
    private WrongQuestionListService wrongQuestionListService;

    @Autowired
    private SysDictionaryApi sysDictionaryApi;



    /**
     * 查询小程序知识点列表
     * @param studentId 学生id
     * @return 知识点列表
     */
    @Override
    public List<KnowledgeVo> selectKnowledgeListClassification(String studentId) {

        return baseMapper.selectKnowledgeListClassification(studentId);
    }
    /**
     * 查询小程序二级知识点列表
     *
     * @param studentId 学生id
     * @return 二级知识点列表
     */
    @Override
    public List<KnowledgeVo> selectKnowledgeListClassificationTow(String studentId){

        return baseMapper.selectKnowledgeListClassificationTow(studentId);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public KnowledgeListClassification selectKnowledgeListClassificationById(String id) {
        KnowledgeListClassification knowledgeListClassification = baseMapper.selectById(id);
        return knowledgeListClassification;
    }

    @Override
    public List<Map<String, Object>> queryListByLevelId(String levelId) {
        return baseMapper.queryListByLevelId(levelId);
    }

    @Override
    public CommonGridResult selectListByParmas(String classfiyCode, String queryId, Integer pageNum, Integer pageSize) {
        CommonGridResult commonGridResult = null;
        //判断当前的参数的中是否含有"-" 不含则为数域查询
        boolean contains = classfiyCode.contains("-");
        if (contains) {
            //拆分看当前数据中存在多少元素确定当前的查询是否为最底层查询
            String[] strs = classfiyCode.split("-");
            //解析左侧树节点内容
            List<String> knowledgeIds = queryKnowledgeIds(strs);
            if (knowledgeIds != null && knowledgeIds.size() > 0) {
                //生成查询条件
                LambdaQueryWrapper<KnowledgeListClassification> klcWrapper = new LambdaQueryWrapper<>();
                klcWrapper.in(KnowledgeListClassification::getKnowledgeId, knowledgeIds);
                klcWrapper.orderByAsc(KnowledgeListClassification::getOrderNum);
                //分页查询
                commonGridResult = selectKnowledgeListClassfitions(pageNum, pageSize, knowledgeIds, queryId);
            } else {
                commonGridResult = new CommonGridResult(pageNum, pageSize, 0, null);
            }

        } else if (classfiyCode == null || classfiyCode.equals("")) {
            //查询当前所有数据的知识id
            LambdaQueryWrapper<KnowledgeList> klWrapper = new LambdaQueryWrapper<>();
            List<KnowledgeList> knowledgeLists = knowledgeListMapper.selectList(klWrapper);
            List<String> knowledgeIds = knowledgeLists.stream().map(KnowledgeList::getKnowledgeId).collect(Collectors.toList());
            if (knowledgeIds != null && knowledgeIds.size() > 0) {
                commonGridResult = selectKnowledgeListClassfitions(pageNum, pageSize, knowledgeIds, queryId);
            } else {
                commonGridResult = new CommonGridResult(pageNum, pageSize, 0, null);
            }

        } else {
            //查询当前数域的下的所有的知识点数据

            LambdaQueryWrapper<KnowledgeList> klWrapper = new LambdaQueryWrapper<>();
            klWrapper.eq(KnowledgeList::getKnowledgeNumField, Integer.parseInt(classfiyCode));
            List<KnowledgeList> knowledgeLists = knowledgeListMapper.selectList(klWrapper);
            List<String> knowledgeIds = knowledgeLists.stream().map(KnowledgeList::getKnowledgeId).collect(Collectors.toList());
            if (knowledgeIds != null && knowledgeIds.size() > 0) {
                commonGridResult = selectKnowledgeListClassfitions(pageNum, pageSize, knowledgeIds, queryId);
            } else {
                commonGridResult = new CommonGridResult(pageNum, pageSize, 0, null);
            }
        }
        return commonGridResult;
    }


    @Transactional
    @Override
    @CacheEvict(keyGenerator = "delAllKeyGenerator", allEntries = true)
    public CommonResult saveData(String eparentCode, String classificationId, String classificationDatarange,
                                 String classificationTime, String classificationMsg, Integer questionsNumber,
                                 String formula_one, String formula_two, String diagnosisMsg) {
        KnowledgeList knowledgeList = new KnowledgeList();
        //优先存储 knowledgelist
        String uuid = UUID.randomUUID().toString().replace("-", "");
        knowledgeList.setKnowledgeId(uuid);
        String[] splits = eparentCode.split("-");
        knowledgeList.setKnowledgeNumField(splits[0]);
        knowledgeList.setKnowledgeOperation(splits[1]);
        knowledgeList.setKnowledgeType(splits[2]);
        if (splits[1].equals("1")) {
            knowledgeList.setClassificationMsg("addition");
        } else if (splits[1].equals("2")) {
            knowledgeList.setClassificationMsg("subtraction");
        } else if (splits[1].equals("3")) {
            knowledgeList.setClassificationMsg("multiplication");
        } else {
            knowledgeList.setClassificationMsg("division");
        }

        //当前处理Knowledgelistclassifition
        String id = UUID.randomUUID().toString().replace("-", "");
        KnowledgeListClassification knowledgeListClassification = new KnowledgeListClassification();
        knowledgeListClassification.setKnowledgeId(uuid);
        knowledgeListClassification.setClassificationDatarange(Integer.parseInt(classificationDatarange));
        knowledgeListClassification.setClassificationTime(Integer.parseInt(classificationTime));
        if (classificationId != null && !classificationId.equals("")) {
            knowledgeListClassification.setPid(classificationId);
        } else {
            knowledgeListClassification.setPid("000");
            knowledgeListClassification.setIsSubclass(0);
        }

        knowledgeListClassification.setClassificationId(id);
        knowledgeListClassification.setClassificationMsg(classificationMsg);
//        knowledgeListClassification.setClassificationDatarange(classificationDatarange);
        knowledgeListClassification.setQuestionsNumber(questionsNumber);

        KnowledgeListSchedule knowledgeListSchedule;
        int insert2 = 0;
        if (diagnosisMsg != null && !diagnosisMsg.equals("")) {
            //处理当前的诊断信息
            String diagnosis_id = UUID.randomUUID().toString().replace("-", "");
            knowledgeListSchedule = new KnowledgeListSchedule();
            knowledgeListSchedule.setDiagnosisId(diagnosis_id);
            knowledgeListSchedule.setClassificationId(id);
            knowledgeListSchedule.setDiagnosisMsg(diagnosisMsg);
            insert2 = knowledgeListScheduleMapper.insert(knowledgeListSchedule);
        }

        //处理生成的习题 规则名称
        int insert3 = 0;
        if (formula_one != null && !formula_one.equals("") && formula_one != null && !formula_one.equals("")) {
            Popup popup = new Popup();
            popup.setId(UUID.randomUUID().toString().replace("-", ""));
            popup.setClassificationId(id);
            popup.setFormulaOne(formula_one);
            popup.setFormulaTwo(formula_two);
            popup.setContent("");
            if (formula_one != null && !formula_one.equals("")) {
                popup.setType(1);
            } else {
                popup.setType(0);
            }
            insert3 = popupMapper.insert(popup);
        }

        int insert = knowledgeListMapper.insert(knowledgeList);
        int insert1 = baseMapper.insert(knowledgeListClassification);
        CommonResult commonResult = null;
        if (insert > 0 && insert1 > 0 && insert2 > 0 && insert3 > 0) {
            commonResult = new CommonResult(200, "新增成功", "");
        } else {
            commonResult = new CommonResult(200, "新增失败", "");
        }
        return commonResult;
    }


    @Override
    @CacheEvict(keyGenerator = "delAllKeyGenerator", allEntries = true)
    public CommonResult saveData1(String eparentCode, String popups,
                                  KnowledgeListClassification knowledgeListClassification,
                                  String diagnosisMsg) {
        String klcId = UUID.randomUUID().toString().replace("-", "");
        knowledgeListClassification.setClassificationId(klcId);
        KnowledgeList knowledgeList = new KnowledgeList();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        knowledgeList.setKnowledgeId(uuid);
        String[] splits = eparentCode.split("-");
        knowledgeList.setKnowledgeNumField(splits[0]);
        knowledgeList.setKnowledgeOperation(splits[1]);
        knowledgeList.setKnowledgeType(splits[2]);
        if (splits[1].equals("1")) {
            knowledgeList.setClassificationMsg("addition");
        } else if (splits[1].equals("2")) {
            knowledgeList.setClassificationMsg("subtraction");
        } else if (splits[1].equals("3")) {
            knowledgeList.setClassificationMsg("multiplication");
        } else {
            knowledgeList.setClassificationMsg("division");
        }

        //当前处理Knowledgelistclassifition
        knowledgeListClassification.setKnowledgeId(uuid);
        if (StringUtils.isEmpty(knowledgeListClassification.getPid())) {
            knowledgeListClassification.setPid("000");
        } else {
            LambdaUpdateWrapper<KnowledgeListClassification> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(KnowledgeListClassification::getClassificationId, knowledgeListClassification.getPid());
            updateWrapper.set(KnowledgeListClassification::getIsSubclass, 1);
        }

        knowledgeListClassification.setIsSubclass(0);

        KnowledgeListSchedule knowledgeListSchedule = null;
        int insert2 = 0;
        if (diagnosisMsg != null && !diagnosisMsg.equals("")) {
            //处理当前的诊断信息
            String diagnosis_id = UUID.randomUUID().toString().replace("-", "");
            knowledgeListSchedule = new KnowledgeListSchedule();
            knowledgeListSchedule.setDiagnosisId(diagnosis_id);
            knowledgeListSchedule.setClassificationId(klcId);
            knowledgeListSchedule.setDiagnosisMsg(diagnosisMsg);
            insert2 = knowledgeListScheduleMapper.insert(knowledgeListSchedule);
        }

        if (popups != null && !popups.equals("")) {
            List<Popup> popupList = JSONArray.parseArray(popups, Popup.class);
            popupList.forEach(popup -> {
                String p = UUID.randomUUID().toString().replace("-", "");
                popup.setId(p);
                popup.setClassificationId(klcId);
                if (popup.getFormulaOne() != null && !popup.getFormulaOne().equals("") && popup.getFormulaTwo() != null && !popup.getFormulaTwo().equals("")) {
                    popup.setType(1);
                } else {
                    popup.setType(0);
                }
                popupMapper.insert(popup);
            });
        }

        int insert = knowledgeListMapper.insert(knowledgeList);
        knowledgeListClassification.setIsRandom(0);
        int insert1 = baseMapper.insert(knowledgeListClassification);
        CommonResult commonResult = null;
        if (insert > 0 && insert1 > 0 && insert2 > 0) {
            commonResult = new CommonResult(200, "新增成功", "");
        } else {
            commonResult = new CommonResult(200, "新增失败", "");
        }
        return commonResult;
    }

    @Transactional
    @Override
    @CacheEvict(keyGenerator = "delAllKeyGenerator", allEntries = true)
    public CommonResult updateKlcById(String popups,
                                      KnowledgeListClassification knowledgeListClassification,
                                      KnowledgeListSchedule knowledgeListSchedule) {

        int i = knowledgeListScheduleMapper.updateById(knowledgeListSchedule);
        int i1 = baseMapper.updateById(knowledgeListClassification);
        List<Popup> popupList = JSONArray.parseArray(popups, Popup.class);
        popupList.forEach(e -> {
            popupMapper.updateById(e);
        });
        CommonResult commonResult = null;
        if (i > 0 && i1 > 0) {
            commonResult = new CommonResult(200, "新增成功", "");
        } else {
            commonResult = new CommonResult(200, "新增失败", "");
        }
        return commonResult;
    }

    @Transactional
    @Override
    @CacheEvict(keyGenerator = "delAllKeyGenerator", allEntries = true)
    public CommonResult deleteKLCByIds(String ids) {
        CommonResult commonResult = null;
        String[] classifyIds = ids.split("-");
        //当前优先查询数据然后进行删除
        List<KnowledgeListClassification> knowledgeListClassifications = baseMapper.selectBatchIds(Arrays.asList(classifyIds));
        List<String> collect = knowledgeListClassifications.stream().map(KnowledgeListClassification::getKnowledgeId).collect(Collectors.toList());
        int i = knowledgeListMapper.deleteBatchIds(collect);
        int i1 = baseMapper.deleteBatchIds(Arrays.asList(classifyIds));
        int i2 = knowledgeListScheduleMapper.deleteBatchIds(Arrays.asList(classifyIds));
        int i3 = popupMapper.deleteBatchIds(Arrays.asList(classifyIds));
        if (i > 0 && i1 > 0 && i2 > 0 && i3 > 0) {
            commonResult = new CommonResult(200, "新增成功", "");
        } else {
            commonResult = new CommonResult(200, "新增失败", "");
        }
        return commonResult;
    }

    @Transactional
    @Override
    @CacheEvict(keyGenerator = "delAllKeyGenerator", allEntries = true)
    public CommonResult updateKLCByObj(KnowledgeListClassification knowledgeListClassification, String diagnosisMsg, List<Popup> popups) {
        CommonResult commonResult = null;
        int i = baseMapper.updateById(knowledgeListClassification);
        LambdaQueryWrapper<Popup> popupLambdaQueryWrapper = new LambdaQueryWrapper<>();
        popupLambdaQueryWrapper.eq(Popup::getClassificationId, knowledgeListClassification.getClassificationId());
        List<Popup> popupsSelect = popupMapper.selectList(popupLambdaQueryWrapper);
        List<String> collectSelect = popupsSelect.stream().map(Popup::getId).collect(Collectors.toList());
        List<String> collect = popups.stream().map(Popup::getId).collect(Collectors.toList());
        for (Popup popup : popups) {
            if (StringUtils.isEmpty(popup.getId())) {
                popup.setId(StringUtils.uuid());
                popupMapper.insert(popup);
            } else {
                popupMapper.updateById(popup);
            }
        }
        collectSelect.removeAll(collect);
        if (collectSelect.size() > 0) {
            popupMapper.deleteBatchIds(collectSelect);
        }
        LambdaQueryWrapper<KnowledgeListSchedule> klsWrapper = new LambdaQueryWrapper<>();
        klsWrapper.eq(KnowledgeListSchedule::getClassificationId, knowledgeListClassification.getClassificationId());
        KnowledgeListSchedule knowledgeListSchedule = knowledgeListScheduleMapper.selectOne(klsWrapper);
        knowledgeListSchedule.setDiagnosisMsg(diagnosisMsg);
        int i1 = knowledgeListScheduleMapper.updateById(knowledgeListSchedule);
        if (i > 0 && i1 > 0) {
            commonResult = new CommonResult(200, "更新成功", "");
        } else {
            commonResult = new CommonResult(200, "更新失败", "");
        }
        return commonResult;
    }

    private CommonGridResult selectKnowledgeListClassfitions(Integer pageNum, Integer pageSize, List<String> knowledgeIds, String queryId) {
        LambdaQueryWrapper<KnowledgeListClassification> klcWrapper = new LambdaQueryWrapper<>();
        klcWrapper.in(KnowledgeListClassification::getKnowledgeId, knowledgeIds);
        klcWrapper.orderByAsc(KnowledgeListClassification::getOrderNum);
        if (queryId != null && !queryId.equals("")) {
            klcWrapper.eq(KnowledgeListClassification::getPid, queryId);
        } else {
            klcWrapper.eq(KnowledgeListClassification::getPid, "000");
        }
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<KnowledgeListClassification> knowledgeListClassifications = baseMapper.selectList(klcWrapper);
        Page page = (Page) knowledgeListClassifications;
        long total = page.getTotal();
        //根据当前查询到的知识点分类id查询当前的诊断信息
        List<String> classifyIds = knowledgeListClassifications.stream()
                .map(KnowledgeListClassification::getClassificationId)
                .collect(Collectors.toList());
        CommonGridResult commonGridResult = null;
        if (classifyIds != null && classifyIds.size() > 0) {
            //查询出当前的诊断表中的诊断信息
            LambdaQueryWrapper<KnowledgeListSchedule> klsWrapper = new LambdaQueryWrapper<>();
            klsWrapper.in(KnowledgeListSchedule::getClassificationId, classifyIds);
            List<KnowledgeListSchedule> knowledgeListSchedules = knowledgeListScheduleMapper.selectList(klsWrapper);
            knowledgeListClassifications.forEach(e -> {
                for (KnowledgeListSchedule knowledgeListSchedule : knowledgeListSchedules) {
                    if (knowledgeListSchedule.getClassificationId().equals(e.getClassificationId())) {
                        e.setDiagnosisMsg(knowledgeListSchedule.getDiagnosisMsg());
                    }
                }

            });
            //查询当前的Popup文件内容
            LambdaQueryWrapper<Popup> popupWrapper = new LambdaQueryWrapper<>();
            popupWrapper.in(Popup::getClassificationId, classifyIds);
            List<Popup> popups = popupMapper.selectList(popupWrapper);
            knowledgeListClassifications.forEach(e -> {
                String classificationId = e.getClassificationId();
                List<Popup> collect = popups.stream().filter(popup -> popup.getClassificationId().equals(classificationId))
                        .collect(Collectors.toList());
                e.setPopupList(collect);
            });
            //依据当前的数据作为父id
            List<String> classificationIds = knowledgeListClassifications.stream().map(KnowledgeListClassification::getClassificationId).collect(Collectors.toList());
            LambdaQueryWrapper<KnowledgeListClassification> knowledgeListClassificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            knowledgeListClassificationLambdaQueryWrapper.in(KnowledgeListClassification::getPid, classificationIds);
            List<KnowledgeListClassification> klcList = baseMapper.selectList(knowledgeListClassificationLambdaQueryWrapper);
            knowledgeListClassifications.forEach(j -> {
                List<KnowledgeListClassification> knowledgeListClassificationList = new ArrayList<>();
                for (KnowledgeListClassification knowledgeListClassification : klcList) {
                    if (j.getClassificationId().equals(knowledgeListClassification.getPid())) {
                        knowledgeListClassificationList.add(knowledgeListClassification);
                    }
                }
                j.setChildren(knowledgeListClassificationList);

            });
            commonGridResult = new CommonGridResult(pageNum, pageSize, total, knowledgeListClassifications);
        } else {
            commonGridResult = new CommonGridResult(pageNum, pageSize, total, null);
        }
        return commonGridResult;
    }

    private List<String> queryKnowledgeIds(String[] strs) {
        List<String> knowledgeIds = new ArrayList<>();
        if (strs.length > 2) {
            //当前显示到了三级查询
            int numField = Integer.parseInt(strs[0]);//当前为数域
            // 当前为计算方式
            int computeType = Integer.parseInt(strs[1]);
            //当前为分类方式
            int classfiy = Integer.parseInt(strs[2]);
            LambdaQueryWrapper<KnowledgeList> knowledgeListLambdaQueryWrapper = new LambdaQueryWrapper<>();
            knowledgeListLambdaQueryWrapper.eq(KnowledgeList::getKnowledgeNumField, numField)
                    .eq(KnowledgeList::getKnowledgeOperation, computeType)
                    .eq(KnowledgeList::getKnowledgeType, classfiy);
            List<KnowledgeList> knowledgeLists = knowledgeListMapper.selectList(knowledgeListLambdaQueryWrapper);
            knowledgeIds = knowledgeLists.stream().map(KnowledgeList::getKnowledgeId).collect(Collectors.toList());
        } else {
            //二级查询
            int numField = Integer.parseInt(strs[0]);//当前为数域
            // 当前为计算方式
            int computeType = Integer.parseInt(strs[1]);
            LambdaQueryWrapper<KnowledgeList> knowledgeListLambdaQueryWrapper = new LambdaQueryWrapper<>();
            knowledgeListLambdaQueryWrapper.eq(KnowledgeList::getKnowledgeNumField, numField)
                    .eq(KnowledgeList::getKnowledgeOperation, computeType);
            List<KnowledgeList> knowledgeLists = knowledgeListMapper.selectList(knowledgeListLambdaQueryWrapper);
            knowledgeIds = knowledgeLists.stream().map(KnowledgeList::getKnowledgeId).collect(Collectors.toList());
        }
        return knowledgeIds;
    }

    @Override
    public CommonResult getListByPid(String pid) {
        LambdaQueryWrapper<KnowledgeListClassification> knowledgeListClassificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        knowledgeListClassificationLambdaQueryWrapper.eq(KnowledgeListClassification::getPid, pid);
        List<KnowledgeListClassification> list = this.list(knowledgeListClassificationLambdaQueryWrapper);
        return CommonResult.success(list);
    }

    @Transactional
    @Override
    public CommonResult removeInfoByklcIds(String ids) {
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        //判断知识点是否有关联的关卡
        LambdaQueryWrapper<LevelKnowledge> levelKnowledgeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        levelKnowledgeLambdaQueryWrapper.in(LevelKnowledge::getClassificationId, strings);
        List<LevelKnowledge> list = levelKnowledgeService.list(levelKnowledgeLambdaQueryWrapper);
        if (list.size() > 0) {//如果知识点有关联的关卡，则返回提示信息
            //拿出与关卡有关联的知识点id
            List<String> classificationIds = list.stream().map(e -> e.getClassificationId()).distinct().collect(Collectors.toList());
            //根据知识点id获取知识点描述
            LambdaQueryWrapper<KnowledgeListClassification> knowledgeListClassificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            knowledgeListClassificationLambdaQueryWrapper.in(KnowledgeListClassification::getClassificationId, classificationIds);
            List<KnowledgeListClassification> list1 = this.list(knowledgeListClassificationLambdaQueryWrapper);
            //拼接返回信息
            List<String> KnowledgeListClassificationMsgs = list1.stream().map(e -> e.getClassificationMsg()).distinct().collect(Collectors.toList());
            String resultMsg = "";
            for (String knowledgeListClassificationMsg : KnowledgeListClassificationMsgs) {
                resultMsg += knowledgeListClassificationMsg + ",";
            }
            //返回提示信息
            return new CommonResult(500, "知识点：" + resultMsg + "与关卡有关联，无法删除");
        }
        LambdaQueryWrapper<KnowledgeListClassification> knowledgeListClassificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        knowledgeListClassificationLambdaQueryWrapper.in(KnowledgeListClassification::getClassificationId, strings);
        List<KnowledgeListClassification> list1 = this.list(knowledgeListClassificationLambdaQueryWrapper);
        //如果知识点没有关联的关卡，删除知识点以及关联表信息

        //删除知识点分类表关联数据knowledge_list
        LambdaQueryWrapper<KnowledgeList> delete1 = new LambdaQueryWrapper<>();
        delete1.in(KnowledgeList::getKnowledgeId, list1.stream().map(e -> e.getKnowledgeId()).distinct().collect(Collectors.toList()));
        knowledgeListMapper.delete(delete1);
        //删除知识点表关联数据knowledge_list_classification
        LambdaQueryWrapper<KnowledgeListClassification> delete2 = new LambdaQueryWrapper<>();
        this.removeByIds(strings);
        //删除知识点规则表关联数据exercises_rules
        LambdaQueryWrapper<ExercisesRules> delete3 = new LambdaQueryWrapper<>();
        delete3.in(ExercisesRules::getPid, strings);
        exercisesRulesService.remove(delete3);
        //删除知识点诊断表关联数据knowledge_list_schedule
        LambdaQueryWrapper<KnowledgeListSchedule> delete4 = new LambdaQueryWrapper<>();
        delete4.in(KnowledgeListSchedule::getClassificationId, strings);
        knowledgeListScheduleMapper.delete(delete4);
        //删除知识点弹窗表关联数据popup
        LambdaQueryWrapper<Popup> delete5 = new LambdaQueryWrapper<>();
        delete5.in(Popup::getClassificationId, strings);
        popupMapper.delete(delete5);
        //删除习题表关联数据exercises_train_set
        LambdaQueryWrapper<ExercisesTrainSet> delete6 = new LambdaQueryWrapper<>();
        delete6.in(ExercisesTrainSet::getClassificationId, strings);
        exercisesTrainSetService.remove(delete6);
        //删除习题答案提交表关联数据student_answer
        LambdaQueryWrapper<StudentAnswer> delete7 = new LambdaQueryWrapper<>();
        delete7.in(StudentAnswer::getClassificationId, strings);
        studentAnswerService.remove(delete7);
        //删除错题本关联数据wrong_question_list
        LambdaQueryWrapper<WrongQuestionList> delete8 = new LambdaQueryWrapper<>();
        delete8.in(WrongQuestionList::getClassificationId, strings);
        wrongQuestionListService.remove(delete8);
        return CommonResult.success("删除成功");
    }

    @Transactional
    @Override
    public CommonResult setOrderNum(String ids) {
        String[] split = ids.split(",");
        List<String> strings = Arrays.asList(split);
        for (int i = 0; i < strings.size(); i++) {
            LambdaUpdateWrapper<KnowledgeListClassification> klcWrapper = new LambdaUpdateWrapper<>();
            klcWrapper.eq(KnowledgeListClassification::getClassificationId, strings.get(i));
            klcWrapper.set(KnowledgeListClassification::getOrderNum, i);
            this.update(klcWrapper);
        }
        return CommonResult.success();
    }


    /*
     * 根据知识点id获取祖级知识点信息
     * */
    @Override
    public String getAncestorMsgByClassificationId(String classificationId) {
        String ancestorMsg = "";
        LambdaQueryWrapper<KnowledgeListClassification> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KnowledgeListClassification::getClassificationId, classificationId);
        KnowledgeListClassification one = this.getOne(queryWrapper);
        String classificationId2 = "";
        for (int i = 0; i < 2; ) {
            ancestorMsg = one.getClassificationMsg() + ancestorMsg;
            if (!one.getPid().equals("000")) {
                classificationId2 = one.getPid();
                LambdaQueryWrapper<KnowledgeListClassification> queryWrapper2 = new LambdaQueryWrapper<>();
                queryWrapper2.eq(KnowledgeListClassification::getClassificationId, classificationId2);
                one = this.getOne(queryWrapper2);
            } else {
                i = 2;
            }
        }
        String knowledgeId = one.getKnowledgeId();
        KnowledgeList knowledgeList = knowledgeListMapper.selectById(knowledgeId);
        if (knowledgeList != null) {
            String knowledgeNumField = knowledgeList.getKnowledgeNumField();
            String knowledgeOperation = knowledgeList.getKnowledgeOperation();
            String knowledgeType = knowledgeList.getKnowledgeType();
            LambdaQueryWrapper<SysDictionary> sysDictionaryWrapper1 = new LambdaQueryWrapper<>();
            sysDictionaryWrapper1.eq(SysDictionary::getDictType, "classfiy");
            sysDictionaryWrapper1.eq(SysDictionary::getDictCode, knowledgeNumField + "-" + knowledgeOperation + "-" + knowledgeType);
            SysDictionary one1 = sysDictionaryApi.getOne(sysDictionaryWrapper1);
            ancestorMsg = one1.getFullName() + ancestorMsg;

            LambdaQueryWrapper<SysDictionary> sysDictionaryWrapper2 = new LambdaQueryWrapper<>();
            sysDictionaryWrapper2.eq(SysDictionary::getDictType, "classfiy");
            sysDictionaryWrapper2.eq(SysDictionary::getDictCode, knowledgeNumField + "-" + knowledgeOperation);
            SysDictionary one2 = sysDictionaryApi.getOne(sysDictionaryWrapper2);

            ancestorMsg = one2.getFullName() + ancestorMsg;

            LambdaQueryWrapper<SysDictionary> sysDictionaryWrapper3 = new LambdaQueryWrapper<>();
            sysDictionaryWrapper3.eq(SysDictionary::getDictType, "classfiy");
            sysDictionaryWrapper3.eq(SysDictionary::getDictCode, knowledgeNumField);
            SysDictionary one3 = sysDictionaryApi.getOne(sysDictionaryWrapper3);
            ancestorMsg = one3.getFullName() + ancestorMsg;

        }

        return ancestorMsg;
    }


    @Resource
    private DeptKnowledgeRelationService deptKnowledgeRelationService;
    @Resource
    private KnowledgeExercisesTrainService knowledgeExercisesTrainService;
    /**
     * 知识点出题设置分页查询功能
     * @param questionSettingVo
     * @return
     */
    @Override
    public CommonGridResult pageingQueryQuestionSetting(QuestionSettingVo questionSettingVo){
        return deptKnowledgeRelationService.pagequeryDeptKnowledge(questionSettingVo);
    }
    /**
     * 知识点出题设置统一还是随机出题
     * @param ids
     * @param classId
     * @param flag
     * @return
     */
    @Override
    public CommonResult modifyIsRandom(List<String> ids,String classId,int flag){
        try {
            //修改为统一则生成统一习题 0随机，1统一
            if (flag == 1){
                //删除原有习题
                QueryWrapper<KnowledgeExercisesTrain> deleteWrapper = new QueryWrapper<>();
                deleteWrapper.in("classification_id",ids);
                knowledgeExercisesTrainService.remove(deleteWrapper);

                ArrayList<KnowledgeExercisesTrain> listTrain = new ArrayList<>();
                for (String id : ids) {
                    List<Map<String, String>> data = (List<Map<String, String>>) exercisesRulesService.generateExercises(id, null).getData();
                    for (Map<String, String> datum : data) {
                        KnowledgeExercisesTrain train = new KnowledgeExercisesTrain();
                        train.setExercisesId(StringUtils.uuid());
                        train.setClassificationId(datum.get("classifyId"));
                        train.setFirst(datum.get("first"));
                        train.setSecond(datum.get("second"));
                        train.setOperate(datum.get("operate"));
                        train.setThree(datum.get("three"));
                        train.setResult(datum.get("result"));
                        listTrain.add(train);
                    }
                }
                knowledgeExercisesTrainService.saveBatch(listTrain);
            }
            ArrayList<DeptKnowledgeRelation> list = new ArrayList<>();
            for (String id : ids) {
                DeptKnowledgeRelation relation = new DeptKnowledgeRelation();
                relation.setId(classId+id);
                relation.setClassId(classId);
                relation.setClassificationId(id);
                relation.setIsRandom(flag);
                list.add(relation);
            }
            deptKnowledgeRelationService.saveOrUpdateBatch(list);
            return CommonResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.error();
        }
    }
}
