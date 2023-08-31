package com.fudian.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.SysDictionary;
import com.fudian.api.system.service.SysDictionaryApi;
import com.fudian.business.dto.MasterSituationStatisticsDTO;
import com.fudian.business.mapper.ExercisesTrainSetMapper;
import com.fudian.business.mapper.KnowledgeListClassificationMapper;
import com.fudian.business.mapper.StudentAnswerMapper;
import com.fudian.business.mapper.WrongQuestionListMapper;
import com.fudian.business.pojo.BreakThroughDetail;
import com.fudian.business.pojo.BreakthroughStatistics;
import com.fudian.business.pojo.ExercisesTrainSet;
import com.fudian.business.service.AcademicSituationAnalysisService;
import com.fudian.common.pojo.CommonGridResult;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import com.fudian.common.vo.MasterSituationVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AcademicSituationAnalysisServiceImpl extends ServiceImpl<ExercisesTrainSetMapper, ExercisesTrainSet>
        implements AcademicSituationAnalysisService {


    @Resource
    private StudentAnswerMapper studentAnswerMapper;

    @Resource
    private KnowledgeListClassificationMapper knowledgeListClassificationMapper;

    @Autowired
    private KnowledgeListClassificationServiceImpl knowledgeListClassificationService;

    @Resource
    private WrongQuestionListMapper wrongQuestionListMapper;

    @Autowired
    private SysDictionaryApi sysDictionaryApi;

    private static final HashMap<String,Integer> numberTypes = new HashMap<String,Integer>(){{
            put("first", 1);put("second", 2);put("third", 3);
    }};

    @Override
    public CommonResult getAlgorithmAmount(String studentId, String numberType) {
        if (StringUtils.isEmpty(studentId) || StringUtils.isEmpty(numberType) || numberTypes.get(numberType) == null) {
            return CommonResult.error();
        }
        List<Map<String,Object>> mapList = baseMapper.getAlgorithmAmount(studentId,numberTypes.get(numberType));
        Map<String, Object> map = new HashMap<>();
        int i = 0;
        for (Map<String, Object> stringObjectMap : mapList) {
            i++;
            map.put("algorithm"+i,stringObjectMap.get("num"));
        }
        return CommonResult.success(map);
    }

    @Override
    public CommonResult getKnowledgeAmount(String studentId, String numberType,String algorithm) {
        if (StringUtils.isEmpty(studentId) || StringUtils.isEmpty(numberType) || numberTypes.get(numberType) == null)
            return CommonResult.error("找不到该学生");
        LambdaQueryWrapper<SysDictionary> sysDictionaryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getDictType,"classfiy");
        sysDictionaryLambdaQueryWrapper.eq(SysDictionary::getEparentCode,numberTypes.get(numberType).toString());
        List<SysDictionary> list = sysDictionaryApi.list(sysDictionaryLambdaQueryWrapper);

        Map returnMap = new HashMap<>();
        int i = 0;
        for (SysDictionary sysDictionary : list) {
            i++;
            String substring = sysDictionary.getDictCode().substring(sysDictionary.getDictCode().length() - 1);
            List<Map<String,Object>> mapList
                    = baseMapper.getKnowledgeAmount(studentId, numberTypes.get(numberType),substring);
            List<Map<String, Object>> mapList1 = new ArrayList<>();
            for (Map<String, Object> stringObjectMap : mapList) {
                Map<String, Object> map = new HashMap<>();
                map.put("text",stringObjectMap.get("classificationMsg"));
                if (stringObjectMap.get("score") == null){
                    map.put("max",0);
                } else {
                    map.put("max",stringObjectMap.get("score"));
                }
                mapList1.add(map);
            }
            returnMap.put("algorithm"+i,mapList1);
        }


        return CommonResult.success(returnMap);
    }

    @Override
    public CommonResult getMasterSituation(String studentId) {
        if (StringUtils.isEmpty(studentId)) {
            CommonResult.error("该学生不存在");
        }
        Map<String, Object> map = new HashMap();
        // 已练习知识点
        List<String> practiced = new ArrayList<>();
        /**
         * 未掌握知识点，统计知识点最近10次的练习情况
         */
        List<MasterSituationVO> notMastered = studentAnswerMapper.getKnowledgePointExerciseBatchIDs(studentId, 0);
        for (MasterSituationVO master:notMastered) {
            practiced.add(master.getClassificationId());
            if (master.getBatchIds() == null || "".equals(master.getBatchIds())) {
                continue;
            }
            List<String> collect = Arrays.stream(master.getBatchIds().split(",")).collect(Collectors.toList());
            MasterSituationStatisticsDTO dto = studentAnswerMapper.accuracyStatistics(collect);
            master.setCount(dto.getCount());
            master.setCorrectRate(dto.getCorrectRate());
            master.setAverageTime(dto.getAverageTime());
            List<MasterSituationStatisticsDTO> maps = wrongQuestionListMapper.wrongQuestionsInAcquiringKnowledgePoints(collect);
            List<MasterSituationVO.WrongQuestionDetails> details = new ArrayList<>();
            for (MasterSituationStatisticsDTO dto2:maps) {
                details.add(new MasterSituationVO.WrongQuestionDetails(dto2.getReason(),dto2.getError()));
            }
            master.setDetails(details);
        }
        map.put("state0",notMastered);
        /**
         * 已掌握知识点，统计知识点最近10次的练习情况
         */
        List<MasterSituationVO> mastered = studentAnswerMapper.getKnowledgePointExerciseBatchIDs(studentId, 1);
        for (MasterSituationVO master:mastered) {
            practiced.add(master.getClassificationId());
            if (master.getBatchIds() == null || "".equals(master.getBatchIds())) {
                continue;
            }
            List<String> collect = Arrays.stream(master.getBatchIds().split(",")).collect(Collectors.toList());
            MasterSituationStatisticsDTO dto = studentAnswerMapper.accuracyStatistics(collect);
            master.setCount(dto.getCount());
            master.setCorrectRate(dto.getCorrectRate());
            master.setAverageTime(dto.getAverageTime());
            List<MasterSituationStatisticsDTO> maps = wrongQuestionListMapper.wrongQuestionsInAcquiringKnowledgePoints(collect);
            List<MasterSituationVO.WrongQuestionDetails> details = new ArrayList<>();
            for (MasterSituationStatisticsDTO dto2:maps) {
                details.add(new MasterSituationVO.WrongQuestionDetails(dto2.getReason(),dto2.getError()));
            }
            master.setDetails(details);
        }
        map.put("state1",mastered);
        // 所有知识点
        List<Map<String, String>> allKnowledgePoints = knowledgeListClassificationMapper.getAllKnowledgePoints();
        /**
         * 未练习知识点
         */
        List<String> notPracticed = new ArrayList<>();
        for (Map<String,String> knowledgePoint:allKnowledgePoints) {
            if (!practiced.contains(knowledgePoint.get("classificationId"))) {
                notPracticed.add(knowledgePoint.get("classificationMsg"));
            }
        }
        map.put("state2",notPracticed);
        return CommonResult.success(map);
//        Map<String, Object> map = new HashMap();
//        List<WrongQuestionListDTO> wrongs = wrongQuestionListMapper.getErrorContent(studentId);
//        Map<String, String> detailMap;
//        List<MasterSituationVO> masterSituation = studentAnswerMapper.getMasterSituation(studentId);
//        for (MasterSituationVO masterSituationVO : masterSituation) {
//            String ancestorMsgByClassificationId = knowledgeListClassificationService.getAncestorMsgByClassificationId(masterSituationVO.getClassificationId());
//            masterSituationVO.setClassificationMsg(ancestorMsgByClassificationId);
//        }
//        List<MasterSituationVO> newMasterSituation;
//        int state;
//        for (MasterSituationVO master:masterSituation) {
//            if (master.getCorrectRate() < 80) {
//                state = 1;
//            } else {
//                state = 0;
//            }
//            master.setState(state);
////            newMasterSituation = (List<MasterSituationVO>) map.get("state"+master.getState());
//            newMasterSituation = (List<MasterSituationVO>) map.get("state"+state);
//            if (newMasterSituation == null) {
//                newMasterSituation = new ArrayList<>();
////                map.put("state"+master.getState(),newMasterSituation);
//                map.put("state"+state,newMasterSituation);
//            }
//            detailMap = new HashMap<>();
//            //将错题分别指向对应的知识点
//            for (WrongQuestionListDTO wrong:wrongs) {
//                if (wrong.getClassificationId().equals(master.getClassificationId())) {
//                    String errorContent = wrong.getErrorContent();
//                    String[] split = errorContent.split("[,|]");
//                    if (split.length > 2) {
//                        String content = detailMap.get(split[2]);
//                        if (StringUtils.isEmpty(content)) {
//                            detailMap.put(split[2],split[1]);
//                        } else {
//                            detailMap.put(split[2],content+" "+split[1]);
//                        }
//                    }
//                }
//            }
//            detailMap.forEach((k,v) -> master.setDetails(new ArrayList<MasterSituationVO.WrongQuestionDetails>(){{
//                add(new MasterSituationVO.WrongQuestionDetails(k,v));
//            }}));
//            newMasterSituation.add(master);
//        }
//        List<String> strings = new ArrayList<>();
//        List<Map<String,Object>> knowledgeList = knowledgeListClassificationMapper.getNoKnowledgeOfPractice(studentId);
//        for (Map<String, Object> stringObjectMap : knowledgeList) {
//            String ancestorMsgByClassificationId = knowledgeListClassificationService.getAncestorMsgByClassificationId(stringObjectMap.get("classification_id").toString());
//            strings.add(ancestorMsgByClassificationId);
//        }
//        map.put("state"+2,strings);
//        return CommonResult.success(map);
    }

    @Override
    public CommonGridResult getKnowledgeAnalysis(Map<String,Object> map) {
        CommonGridResult commonGridResult = new CommonGridResult();
        Page page = PageHelper.startPage(Integer.valueOf(map.get("pageNum").toString()),Integer.valueOf(map.get("pageSize").toString()));
        List<MasterSituationVO> masterSituation = studentAnswerMapper.getMasterSituationByClassId(map);


        List<String> listId = masterSituation.stream().map(MasterSituationVO::getClassificationId).collect(Collectors.toList());
        map.put("classIdList",listId);
//        PageHelper.startPage(1,99999);
        List<String> strings = knowledgeListClassificationMapper.getUnknownKnowledgeByClassId(map);
        List<Object> objects = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("practiced",masterSituation);
        jsonObject.put("notPracticed",strings);
        commonGridResult.setPageSize(page.getPageSize());
        commonGridResult.setPageNum(page.getPageNum());
        commonGridResult.setTotal(page.getTotal());
        objects.add(jsonObject);
        commonGridResult.setRows(objects);
        return commonGridResult;
    }

    @Override
    public CommonResult getBreakThroughAnalysis(String studentId) {
        if (StringUtils.isEmpty(studentId)) {
            CommonResult.error("学生ID为空");
        }
        Map<String, Integer> map;//每个关卡，每个知识点的总条数
        Map<String, String> map2;//关卡每个知识点的所有错题
        Map<String, Integer> map3;//关卡每个知识点的正确率
        Map<String, String> map4;//知识点id:知识点名称
        Map<String, Integer> map5;//历史每个关卡，每个知识点的总条数
        Map<String, String> map6;//历史关卡每个知识点的所有错题
        Map<String, Integer> map7;//历史关卡每个知识点的正确率
        List<BreakThroughDetail> details;//所有知识点统计结果
        List<BreakThroughDetail> historyDetails;//所有知识点统计结果
        BreakThroughDetail detail;
        BreakThroughDetail historyDetail;
        List<BreakthroughStatistics> breakthroughStatisticsList = studentAnswerMapper.getBreakthroughStatisticsByStudentId(studentId);
        // 计算各个关卡每个知识点的正确率和错题
        for (BreakthroughStatistics statis:breakthroughStatisticsList) {
            map = new HashMap<>();
            map2 = new HashMap<>();
            map3 = new HashMap<>();
            map4 = new HashMap<>();
            map5 = new HashMap<>();
            map6 = new HashMap<>();
            map7 = new HashMap<>();
            // 最新的关卡批次id
            List<String> list1 = new ArrayList<>();
            list1.add(statis.getBatchId());
            List<BreakThroughDetail> breakThroughTopic = baseMapper.getBreakThroughTopic(studentId, list1);
            // 记录知识点id和知识点名称对应关系；记录每个关卡，每个知识点的总条数
            for (BreakThroughDetail topic:breakThroughTopic) {
                map4.put(topic.getClassificationId(),topic.getClassificationMsg());
                Integer integer = map.get(topic.getClassificationId());
                if (integer == null) {
                    map.put(topic.getClassificationId(), topic.getCount());
                } else {
                    map.put(topic.getClassificationId(), integer + topic.getCount());
                }
            }
            for (BreakThroughDetail topic:breakThroughTopic) {
                // 如果是0，代表这个知识点有错题
                if (topic.getIsCorrect() == 0) {
                    map2.put(topic.getClassificationId(),topic.getWrongQuestion());
                } else if (topic.getIsCorrect() == 1) {
                    // 计算关卡每个知识点的正确率
                    Integer integer = map.get(topic.getClassificationId());
                    if (integer != null) {
                        int correctRate = Math.round(topic.getCount() / Float.valueOf(integer) * 100);
                        map3.put(topic.getClassificationId(),correctRate);
                    }
                }
            }
            details = new ArrayList<>();
            for (String m:map.keySet()) {
                detail = new BreakThroughDetail();
                detail.setClassificationMsg(map4.get(m));//关卡知识点名称
                int proportion = Math.round(map.get(m) / Float.valueOf(statis.getTotalNum()) * 100);
                detail.setProportion(proportion);//关卡知识点占比
                detail.setCorrectRate(map3.get(m)==null?0:map3.get(m));//关卡知识点正确率
                detail.setWrongQuestion(map2.get(m)==null?"":map2.get(m));//关卡知识点错题
                details.add(detail);
            }
            statis.setDetails(details);

            // 最近的10次关卡批次id
            List<String> batchIds = studentAnswerMapper.getBatchIdByLevelId(studentId, statis.getLevelId());
//            batchIds.remove(0);
            if (batchIds != null && batchIds.size() > 0) {
                List<BreakThroughDetail> breakThroughTopic2 = baseMapper.getBreakThroughTopic(studentId, batchIds);
                // 记录知识点id和知识点名称对应关系；记录每个关卡，每个知识点的总条数
                for (BreakThroughDetail topic2:breakThroughTopic2) {
                    Integer integer = map5.get(topic2.getClassificationId());
                    if (integer == null) {
                        map5.put(topic2.getClassificationId(), topic2.getCount());
                    } else {
                        map5.put(topic2.getClassificationId(), integer + topic2.getCount());
                    }
                }
                for (BreakThroughDetail topic2:breakThroughTopic2) {
                    // 如果是0，代表这个知识点有错题
                    if (topic2.getIsCorrect() == 0) {
                        map6.put(topic2.getClassificationId(),topic2.getWrongQuestion());
                    } else if (topic2.getIsCorrect() == 1) {
                        // 计算关卡每个知识点的正确率
                        Integer integer = map5.get(topic2.getClassificationId());
                        if (integer != null) {
                            int correctRate = Math.round(topic2.getCount() / Float.valueOf(integer) * 100);
                            map7.put(topic2.getClassificationId(),correctRate);
                        }
                    }
                }
            }
            historyDetails = new ArrayList<>();
            for (String m:map4.keySet()) {
                historyDetail = new BreakThroughDetail();
                historyDetail.setClassificationMsg(map4.get(m));//关卡知识点名称
                historyDetail.setCorrectRate(map7.get(m)==null?0:map7.get(m));//关卡知识点正确率
                historyDetail.setWrongQuestion(map6.get(m)==null?"":map6.get(m));//关卡知识点错题
                historyDetails.add(historyDetail);
            }
            statis.setHistoryDetails(historyDetails);
        }
        return CommonResult.success(breakthroughStatisticsList);
    }

}
