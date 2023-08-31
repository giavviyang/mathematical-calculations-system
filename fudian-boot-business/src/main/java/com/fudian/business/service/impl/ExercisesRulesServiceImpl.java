package com.fudian.business.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.business.mapper.ExercisesRulesMapper;
import com.fudian.business.mapper.KnowledgeListClassificationMapper;
import com.fudian.business.mapper.KnowledgeListMapper;
import com.fudian.business.pojo.ExercisesRules;
import com.fudian.business.pojo.KnowledgeList;
import com.fudian.business.pojo.KnowledgeListClassification;
import com.fudian.business.service.ExercisesRulesService;
import com.fudian.business.utils.CalculationUtils;
import com.fudian.business.utils.GenerateUtils;
import com.fudian.business.utils.GenerateUtils2;
import com.fudian.common.pojo.CommonResult;
import com.fudian.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ExercisesRulesServiceImpl extends ServiceImpl<ExercisesRulesMapper, ExercisesRules> implements ExercisesRulesService {

    @Resource
    private KnowledgeListClassificationMapper knowledgeListClassificationMapper;

    @Resource
    private KnowledgeListMapper knowledgeListMapper;

    @Resource
    private ExercisesRulesMapper exercisesRulesMapper;


    @Override
    public CommonResult generateExercises(String classfitionId,Integer number) {
        List<Map<String,String>>  mapList = new ArrayList<>();

        //依据知识点id查询当前的分类知识点信息 确定知识当前知识点要生成的题目数
        KnowledgeListClassification knowledgeListClassification = knowledgeListClassificationMapper.selectById(classfitionId);
        if (knowledgeListClassification == null) {
            return CommonResult.error("当前知识点不存在");
        }
        int questionsNumber = 0;
        if (number!=null){
            questionsNumber = number;
        }else{
            //查询出当前的题目数量
            questionsNumber = knowledgeListClassification.getQuestionsNumber();
        }

        //根据knowledge_id获取当前数据的数域类型
        LambdaQueryWrapper<KnowledgeList>  klWrapper = new LambdaQueryWrapper<>();
        klWrapper.eq(KnowledgeList::getKnowledgeId,knowledgeListClassification.getKnowledgeId());
        KnowledgeList knowledgeList = knowledgeListMapper.selectOne(klWrapper);

        //根据当前的classfitionId查询当前的生成规则列表
        LambdaQueryWrapper<ExercisesRules> rulesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        rulesLambdaQueryWrapper.eq(ExercisesRules::getPid,classfitionId);
        List<ExercisesRules> exercisesRules = exercisesRulesMapper.selectList(rulesLambdaQueryWrapper);
        ExercisesRules rule = exercisesRules.get(0);
        if(knowledgeList.getKnowledgeNumField().equals("1")){ //当前为整数
            if (knowledgeList.getKnowledgeOperation().equals("1")){//当前为加法
                //获取到当前加法生成第一位和第二位数据集
//                Map<String, List<String>> stringListMap = GenerateUtils.generateAddValue(rule.getNumFirst(),
//                        rule.getFirstMin(),
//                        rule.getNumFirstLen(),
//                        rule.getNumSecond(),
//                        rule.getSecondMin(),
//                        rule.getNumSecondLen(),
//                        knowledgeListClassification.getClassificationDatarange(),
//                        rule.getCarryRulers(),
//                        rule.getDigitPostion(),
//                        questionsNumber);
                Map<String, List<String>> stringListMap = GenerateUtils2.generateAddValue(rule,
                        knowledgeListClassification.getClassificationDatarange(),questionsNumber);
                //拼接数据
                mapList = assContainer(mapList,stringListMap,knowledgeList,classfitionId,knowledgeList.getKnowledgeOperation());
            }else if (knowledgeList.getKnowledgeOperation().equals("2")){ //减法
//                Map<String, List<String>> stringListMap = GenerateUtils.generateSubtractValue(rule.getNumFirst(),
//                        rule.getFirstMin(),
//                        rule.getNumFirstLen(),
//                        rule.getNumSecond(),
//                        rule.getNumSecondLen(),
//                        knowledgeListClassification.getClassificationDatarange(),
//                        rule.getCarryRulers(),
//                        rule.getDigitPostion(),
//                        questionsNumber);
                Map<String, List<String>> stringListMap = GenerateUtils2.generateSubtractValue(rule,
                        knowledgeListClassification.getClassificationDatarange(),questionsNumber);

                mapList = assContainer(mapList,stringListMap,knowledgeList,classfitionId,knowledgeList.getKnowledgeOperation());
            }else if (knowledgeList.getKnowledgeOperation().equals("3")){//乘法
                Map<String, List<String>> stringListMap = GenerateUtils.generateMultiValue1(rule.getNumFirst(),
                        rule.getFirstMin(),
                        rule.getNumFirstLen(),
                        rule.getNumSecond(),
                        rule.getNumSecondLen(),
                        rule.getSecondMin(),
                        knowledgeListClassification.getClassificationDatarange(),
                        rule.getCarryRulers(),
                        rule.getDigitPostion(),
                        questionsNumber);

                mapList = assContainer(mapList,stringListMap,knowledgeList,classfitionId,knowledgeList.getKnowledgeOperation());
            }else {//除法
                Map<String, List<String>> stringListMap = GenerateUtils.generateDivisionValue1(rule.getNumFirst(),
                        rule.getFirstMin(),
                        rule.getNumFirstLen(),
                        rule.getNumSecond(),
                        rule.getNumSecondLen(),
                        rule.getSecondMin(),
                        knowledgeListClassification.getClassificationDatarange(),
                        rule.getCarryRulers(),
                        rule.getDigitPostion(),
                        questionsNumber);
                mapList = assContainer(mapList,stringListMap,knowledgeList,classfitionId,knowledgeList.getKnowledgeOperation());
            }
        }else if(knowledgeList.getKnowledgeNumField().equals("2")){  //当前为小数

            if (knowledgeList.getKnowledgeOperation().equals("1")){ //加法
                Map<String, List<String>> stringListMap = GenerateUtils.generateAddValue1(rule.getNumFirst(), rule.getFirstMin(),
                        rule.getNumFirstLen(), rule.getNumSecond(),rule.getSecondMin(),
                        rule.getNumSecondLen(), knowledgeListClassification.getClassificationDatarange(),
                        rule.getCarryRulers(), rule.getDigitPostion(), questionsNumber);
                List<String> firstList = stringListMap.get("first");
                List<String> secondList = stringListMap.get("second");
                String firstMagnify = rule.getFirstMagnify();
                List<String>  firstValues = new ArrayList<>();
                firstList.forEach(f->{
                    if (Double.parseDouble(firstMagnify)!=0){
                        BigDecimal bigDecimal = new BigDecimal(f);
                        BigDecimal value = bigDecimal.multiply(new BigDecimal(firstMagnify));
                        BigDecimal bigDecimal1 = value.stripTrailingZeros();
                        String v = bigDecimal1.toString();
                        firstValues.add(v);
                    }else {
                        firstValues.add(f);
                    }
                });

                String secondMagnify = rule.getSecondMagnify();
                List<String>  secondValues = new ArrayList<>();
                secondList.forEach(s -> {
                    if (Double.parseDouble(secondMagnify)!=0){
                        BigDecimal bigDecimal = new BigDecimal(s);
                        BigDecimal value = bigDecimal.multiply(new BigDecimal(secondMagnify));
                        BigDecimal bigDecimal1 = value.stripTrailingZeros();
                        String v = bigDecimal1.toString();
                        secondValues.add(v);
                    }else {
                        secondValues.add(s);
                    }
                });
                mapList  =  assDecimalContainer2(mapList,firstValues, secondValues,
                        knowledgeList,classfitionId, knowledgeList.getKnowledgeOperation());
            }else if (knowledgeList.getKnowledgeOperation().equals("2")){//减法
                Map<String, List<String>> stringListMap = GenerateUtils.generateSubtractValue2(rule.getNumFirst(), rule.getFirstMin(),
                        rule.getNumFirstLen(), rule.getNumSecond(),
                        rule.getNumSecondLen(), rule.getSecondMin(),knowledgeListClassification.getClassificationDatarange(),
                        rule.getCarryRulers(), rule.getDigitPostion(), questionsNumber,rule.getFirstMagnify(),rule.getSecondMagnify());
                List<String> firstList = stringListMap.get("first");
                List<String> secondList = stringListMap.get("second");
                String firstMagnify = rule.getFirstMagnify();
                List<Double>  firstValues = new ArrayList<>();
                firstList.forEach(f->{
                    if (Double.parseDouble(firstMagnify)!=0){
                        BigDecimal bigDecimal = new BigDecimal(f);
                        BigDecimal value = bigDecimal.multiply(new BigDecimal(String.valueOf(firstMagnify)));
                        BigDecimal bigDecimal1 = value.stripTrailingZeros();
                        double v = bigDecimal1.doubleValue();
                        firstValues.add(v);
                    }else {
                        firstValues.add(Double.valueOf(f));
                    }
                });

                String secondMagnify = rule.getSecondMagnify();
                List<Double>  secondValues = new ArrayList<>();
                secondList.forEach(s -> {
                    if (Double.parseDouble(secondMagnify)!=0){
                        BigDecimal bigDecimal = new BigDecimal(s);
                        BigDecimal value = bigDecimal.multiply(new BigDecimal(String.valueOf(secondMagnify)));
                        BigDecimal bigDecimal1 = value.stripTrailingZeros();
                        double v = bigDecimal1.doubleValue();
                        secondValues.add(v);
                    }else {
                        secondValues.add(Double.valueOf(s));
                    }
                });
                mapList  =  assDecimalContainer(mapList,firstValues, secondValues,
                        knowledgeList,classfitionId, knowledgeList.getKnowledgeOperation());

            }else if (knowledgeList.getKnowledgeOperation().equals("3")){
                //乘法
                Map<String, List<String>> stringListMap = GenerateUtils.generateMultiValue1(rule.getNumFirst(), rule.getFirstMin(),
                        rule.getNumFirstLen(), rule.getNumSecond(),
                        rule.getNumSecondLen(), rule.getSecondMin(),knowledgeListClassification.getClassificationDatarange(),
                        rule.getCarryRulers(), rule.getDigitPostion(), questionsNumber);
                List<String> firstList = stringListMap.get("first");
                List<String> secondList = stringListMap.get("second");

//                int count = (int)(Math.random()*questionsNumber)+1;
                //倍率
                String firstMagnify = rule.getFirstMagnify();
                String secondMagnify = rule.getSecondMagnify();
                String[] split = null;
                String[] split2 = null;
                if (!StringUtils.isEmpty(firstMagnify) && !"0".equals(firstMagnify) && !"0.0".equals(firstMagnify)) {
                    split = firstMagnify.split("[|]");
                }
                if (!StringUtils.isEmpty(secondMagnify) && !"0".equals(secondMagnify) && !"0.0".equals(secondMagnify)) {
                    split2 = firstMagnify.split("[|]");
                }

                List<int[]> randomList = getRandomList(firstList.size(), split, split2);

                List<String>  firstValues = new ArrayList<>();
                executeDecimal(firstList,firstValues,split,randomList,0);
                List<String>  secondValues = new ArrayList<>();
                executeDecimal(secondList,secondValues,split2,randomList,1);

                mapList  =  assDecimalContainer2(mapList,firstValues, secondValues,
                        knowledgeList,classfitionId, knowledgeList.getKnowledgeOperation());

            }else {
                //除法
//                Map<String, List<String>> stringListMap = GenerateUtils.generateMultiValue(rule.getNumFirst(), rule.getFirstMin(),
//                        rule.getNumFirstLen(), rule.getNumSecond(),
//                        rule.getNumSecondLen(), knowledgeListClassification.getClassificationDatarange(),
//                        rule.getCarryRulers(), rule.getDigitPostion(), questionsNumber);
                Map<String, List<String>> stringListMap = GenerateUtils.generateDivisionValue2(rule.getNumFirst(),
                        rule.getFirstMin(),
                        rule.getNumFirstLen(),
                        rule.getNumSecond(),
                        rule.getNumSecondLen(),
                        rule.getSecondMin(),
                        knowledgeListClassification.getClassificationDatarange(),
                        rule.getCarryRulers(),
                        rule.getDigitPostion(),
                        questionsNumber);
                List<String> firstList = stringListMap.get("first");
                List<String> secondList = stringListMap.get("second");

                //倍率
                String firstMagnify = rule.getFirstMagnify();
                String secondMagnify = rule.getSecondMagnify();
                String[] split = null;
                String[] split2 = null;
                if (!StringUtils.isEmpty(firstMagnify) && !"0".equals(firstMagnify) && !"0.0".equals(firstMagnify)) {
                    split = firstMagnify.split("[|]");
                }
                if (!StringUtils.isEmpty(secondMagnify) && !"0".equals(secondMagnify) && !"0.0".equals(secondMagnify)) {
                    split2 = firstMagnify.split("[|]");
                }

                List<int[]> randomList = getRandomList(firstList.size(), split, split2);

                List<String>  firstValues = new ArrayList<>();
                executeDecimal(firstList,firstValues,split,randomList,0);
                List<String>  secondValues = new ArrayList<>();
                executeDecimal(secondList,secondValues,split2,randomList,1);

                mapList  =  assDecimalContainer2(mapList,firstValues, secondValues,
                        knowledgeList,classfitionId, knowledgeList.getKnowledgeOperation());
            }
        }else {
            //当前为分数
            if (knowledgeList.getKnowledgeOperation().equals("1")){
                //分数加法
                Map<String, List<String>> stringListMap = GenerateUtils.fractionAdd(rule.getMolecule(), rule.getDenominator(),
                        rule.getCarryRulers(), rule.getDigitPostion(),
                        questionsNumber);
                List<String> firstList = stringListMap.get("first");
                List<String> secondList = stringListMap.get("second");
                mapList = assFractionAddResult( mapList,firstList,classfitionId, knowledgeList,secondList);
            }else if (knowledgeList.getKnowledgeOperation().equals("2")){//分数减法
                Map<String, List<String>> stringListMap = GenerateUtils.fractionSubtract(rule.getMolecule(), rule.getDenominator(),
                        rule.getCarryRulers(), rule.getDigitPostion(),
                        questionsNumber);
                List<String> firstList = stringListMap.get("first");
                List<String> secondList = stringListMap.get("second");
                mapList = assFractionSubtractResult( mapList,firstList,secondList,classfitionId, knowledgeList);
            }else if (knowledgeList.getKnowledgeOperation().equals("3")){  //分数乘法
//                Map<String, List<String>> stringListMap = GenerateUtils.fractionMultiplication(rule,
//                        questionsNumber);
//                List<String> firstList = stringListMap.get("first");
//                List<String> secondList = stringListMap.get("second");
//                mapList = assFractionMultiplicationResult(mapList,firstList,classfitionId, knowledgeList,secondList,rule);
                Map<String, List<String>> stringListMap = GenerateUtils2.fractionMultiplication(rule,
                        questionsNumber);
                List<String> firstList = stringListMap.get("first");
                List<String> secondList = stringListMap.get("second");
                List<String> result = stringListMap.get("result");
                mapList = assFractionMultiplicationResult(mapList,firstList,classfitionId, knowledgeList,secondList,result);
            }else {//分数除法
                Map<String, List<String>> stringListMap = GenerateUtils.fractionDivision(rule.getNumFirst(),
                        rule.getNumSecond(),
                        questionsNumber);

                List<String> firstList = stringListMap.get("first");
                List<String> secondList = stringListMap.get("second");
                mapList = assFractionDivisionResult( mapList,firstList,classfitionId, knowledgeList,secondList);
            }

        }
        //习题去重
        mapList = mapList.stream().
                collect(Collectors.collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(o -> o.get("first") + ":" + o.get("second")))), ArrayList::new));
        int stop = 0;
        for (int i = 0; i < 2;stop++) {//迭代生成不重复习题，但是循环次数不能超过两万次
            if (mapList.size()<questionsNumber&&stop!=20000){
                List<Map<String,String>> newMapList = (List<Map<String,String>>)generateExercises(classfitionId,questionsNumber-mapList.size()).getData();
                for (Map<String, String> stringStringMap : newMapList) {
                    mapList.add(stringStringMap);
                }
                mapList = mapList.stream().
                        collect(Collectors.collectingAndThen(Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(o -> o.get("first") + ":" + o.get("second")))), ArrayList::new));
            }else {
                i=2;
            }
        }
        return new CommonResult(200,"成功",mapList);
    }

    public List<Map<String, String>> assFractionSubtractResult(List<Map<String, String>> mapList, List<String> firstList, List<String> secondList, String classfitionId, KnowledgeList knowledgeList) {
        for (int i = 0; i < firstList.size(); i++) {
            Map<String, String> remap = new HashMap<>();
            remap.put("first", firstList.get(i));
            remap.put("second", secondList.get(i));
            remap.put("operate", knowledgeList.getKnowledgeOperation());
            remap.put("classifyId", classfitionId);
            //计算当前的结果，
            String[] firstValues = firstList.get(i).split("/");
            String[] secondValues = secondList.get(i).split("/");
            if (knowledgeList.getKnowledgeType().equals("1")){//同分母
                int  result = Integer.parseInt(firstValues[0]) - Integer.parseInt(secondValues[0]);
                String approximately = approximately(result, Integer.parseInt(firstValues[1]));
                remap.put("result",approximately);
            }else {//异分母
                int firstValue =Integer.valueOf(firstValues[1]);
                int secondValue =Integer.valueOf(secondValues[1]);
                int x = 0;
                if (firstValue>secondValue){
                    x = firstValue%secondValue;
                    if(x==0){
                        int y = firstValue/secondValue;
                        int result = Integer.valueOf(firstValues[0])-Integer.valueOf(secondValues[0])*y;
                        String approximately = approximately(result, firstValue);
                        remap.put("result",approximately);
                    }else {
                        int fm1 =  firstValue*secondValue;
                        int  fz1 =Integer.valueOf(firstValues[0])*secondValue;
                        int  fz2= Integer.valueOf(secondValues[0])*firstValue;
                        int  result = fz1-fz2;
                        String approximately = approximately(result, fm1);
                        remap.put("result",approximately);
                    }

                }else {
                    x = secondValue%firstValue;
                    if(x==0){
                        int y = secondValue/firstValue;
                        int result = Integer.valueOf(firstValues[0])*y-Integer.valueOf(secondValues[0]);
                        String calculate = CalculationUtils.simplifiedValue(result + "/" + secondValues[1]);
//                        String approximately = approximately(result, firstValue);
                        remap.put("result",calculate);
                    }else {
                        int fm1 =  firstValue*secondValue;
                        int  fz1 =Integer.valueOf(firstValues[0])*secondValue;
                        int  fz2= Integer.valueOf(secondValues[0])*firstValue;
                        int  result = fz1-fz2;
                        //两个数分子为1，分母互质，不可能再简化
//                        String approximately = approximately(result, fm1);
                        remap.put("result",result+"/"+fm1);
                    }
                }
            }

            mapList.add(remap);
        }
        return mapList;


    }

    public List<Map<String, String>> assFractionAddResult(List<Map<String, String>> mapList, List<String> firstList,
                                                          String classfitionId, KnowledgeList knowledgeList, List<String> secondList) {

        for (int i = 0; i < firstList.size(); i++) {
            Map<String, String> remap = new HashMap<>();
            remap.put("first", firstList.get(i));
            remap.put("second", secondList.get(i));
            remap.put("operate", knowledgeList.getKnowledgeOperation());
            remap.put("classifyId", classfitionId);
            //计算当前的结果，
            String[] firstValues = firstList.get(i).split("/");
            String[] secondValues = secondList.get(i).split("/");
            if (knowledgeList.getKnowledgeType().equals("1")){//同分母
                int  result = Integer.parseInt(firstValues[0]) + Integer.parseInt(secondValues[0]);
                String approximately = approximately(result, Integer.parseInt(firstValues[1]));
                String[] split = approximately.split("/");
                if (split.length == 2 && Integer.valueOf(split[1]) == 1) {
                    approximately = split[0];
                }
                remap.put("result",approximately);
            }else {//异分母
                int firstValue =Integer.valueOf(firstValues[1]);
                int secondValue =Integer.valueOf(secondValues[1]);
                int x = 0;
                if (firstValue>secondValue){
                    int max = Math.max(firstValue, secondValue);
                    int min = Math.min(firstValue, secondValue);
                    x = max % min;
//                    x = firstValue%secondValue;
                    if(x==0) {
                        int y = max/min;
                        int result;
                        if (firstValue == max) {
                            result = Integer.valueOf(firstValues[0])+Integer.valueOf(secondValues[0])*y;
                        } else {
                            result = Integer.valueOf(firstValues[0])*y+Integer.valueOf(secondValues[0]);
                        }
                        String approximately = approximately(result, max);
                        remap.put("result",approximately);
                    } else {
                        int fm1 = firstValue*secondValue;
                        int fz1 = Integer.valueOf(firstValues[0])*secondValue;
                        int fz2 = Integer.valueOf(secondValues[0])*firstValue;
                        int result = fz1 + fz2;
//                        String approximately = approximately(result, fm1);
                        remap.put("result",result+"/"+fm1);
                    }

                }else {
                    int max = Math.max(firstValue, secondValue);
                    int min = Math.min(firstValue, secondValue);
                    x = max % min;
//                    x = secondValue%firstValue;
                    if(x==0) {
                        int y = max/min;
                        int result;
                        if (firstValue == max) {
                            result = Integer.valueOf(firstValues[0])+Integer.valueOf(secondValues[0])*y;
                        } else {
                            result = Integer.valueOf(firstValues[0])*y+Integer.valueOf(secondValues[0]);
                        }
                        String approximately = approximately(result, max);
                        remap.put("result",approximately);
                    }else {
                        int fm1 =  firstValue*secondValue;
                        int fz1 = Integer.valueOf(firstValues[0])*secondValue;
                        int fz2 = Integer.valueOf(secondValues[0])*firstValue;
                        int result = fz1+fz2;
//                        String approximately = approximately(result, fm1);
                        remap.put("result",result+"/"+fm1);
                    }
                }
            }

            mapList.add(remap);
        }
        return mapList;
    }

    public  String  approximately(int result,int y){
        int  x = result;
        if (result>y){
            x=y;
        }
        for (; x >= 1; --x){
            if (result%x == 0 && y%x == 0) {
                break;
            }
        }
        result /= x;
        int z = y/x;
        if (y==1){
            return String.valueOf(result);
        }else {
            return  result+"/"+z;
        }


    }

    /**
     * 返回分数乘法结果
     */
    public List<Map<String, String>> assFractionMultiplicationResult(List<Map<String, String>> mapList, List<String> firstList,
                                                                     String classfitionId,KnowledgeList knowledgeList,
                                                                     List<String> secondList, List<String> result) {
        for (int i = 0; i < firstList.size(); i++) {
            Map<String,String>  map = new HashMap<>();
            map.put("first",firstList.get(i));
            map.put("second",secondList.get(i));
            map.put("operate",knowledgeList.getKnowledgeOperation());
            map.put("classifyId",classfitionId);
            map.put("result", result.get(i));
            mapList.add(map);
        }
        return  mapList;

    }

    public List<Map<String, String>> assFractionDivisionResult(List<Map<String, String>> mapList, List<String> firstList,
                                                               String classfitionId,KnowledgeList knowledgeList,
                                                               List<String> secondList) {
        for (int i = 0; i < firstList.size(); i++) {
            Map<String,String>  map = new HashMap<>();
            map.put("first",firstList.get(i).toString());
            map.put("second",secondList.get(i).toString());
            map.put("operate",knowledgeList.getKnowledgeOperation());
            map.put("classifyId",classfitionId);
            String value = CalculationUtils.simplifiedValue(firstList.get(i) + "/" + secondList.get(i));
            map.put("result", value);

            mapList.add(map);
        }
        return  mapList;

    }

    private List<Map<String, String>> assDecimalContainer(List<Map<String, String>> mapList,List<Double>  firstValues,
                                                          List<Double>  secondValues,KnowledgeList knowledgeList,
                                                          String classfitionId, String operate) {

        for (int i = 0; i < firstValues.size(); i++) {
            Map<String,String>  map = new HashMap<>();
            String first = CalculationUtils.removeZero(firstValues.get(i).toString());
            map.put("first",first);
            map.put("second",secondValues.get(i).toString());
            map.put("operate",knowledgeList.getKnowledgeOperation());
            map.put("classifyId",classfitionId);
            if (operate.equals("1")){
                BigDecimal add = new BigDecimal(String.valueOf(firstValues.get(i))).add(new BigDecimal(String.valueOf(secondValues.get(i))));
                BigDecimal bigDecimal1 = add.stripTrailingZeros();
                String s = bigDecimal1.toPlainString();
                map.put("result", s);
            }else if (operate.equals("2")){
                BigDecimal add = new BigDecimal(String.valueOf(firstValues.get(i))).subtract(new BigDecimal(String.valueOf(secondValues.get(i))));
                BigDecimal bigDecimal1 = add.stripTrailingZeros();
                String s = bigDecimal1.toPlainString();
                map.put("result", s);
            }else if (operate.equals("3")){
                BigDecimal add = new BigDecimal(String.valueOf(firstValues.get(i))).multiply(new BigDecimal(String.valueOf(secondValues.get(i))));
                BigDecimal bigDecimal1 = add.stripTrailingZeros();
                String s = bigDecimal1.toPlainString();
                map.put("result", s);
//                map.put("result", firstValues.get(i) * secondValues.get(i)+"");
            }else {

                map.put("result", firstValues.get(i) / secondValues.get(i)+"");
            }
            mapList.add(map);
        }
        return  mapList;
    }

    /**
     * 将整数存在倍率的值转成小数
     */
    private void executeDecimal(List<String> numList,List<String> saveList,String[] magnify,List<int[]> li,int digit) {
        if (magnify != null) {
            for (int i=0; i<numList.size(); i++) {
                if (digit == 1 && li.get(i)[digit] == -2) {
                    int k = (int)(Math.random()*2);
                    String p = k>0?"0.01":"0.1";
                    BigDecimal multiply = new BigDecimal(numList.get(i)).multiply(new BigDecimal(p));
                    saveList.add(multiply.toString());
                } else {
                    BigDecimal multiply = new BigDecimal(numList.get(i)).multiply(new BigDecimal(magnify[li.get(i)[digit]]));
                    if (!"1".equals(magnify[li.get(i)[digit]]) && multiply.doubleValue() >= 10) {
                        String value = "0";
                        int ii = 0;
                        while (!value.contains(".")) {
                            ii++;
                            if (ii > 10) {
                                value = "0.5";
                                break;
                            }
                            multiply = multiply.multiply(new BigDecimal("0.1"));
                            value = multiply.toString();
                            value = CalculationUtils.removeZero(value);
                        }
                        saveList.add(value);
                    } else {
                        String value = multiply.toString();
                        value = CalculationUtils.removeZero(value);
                        saveList.add(value);
                    }
                }
            }
        } else {
            for (int i=0; i<numList.size(); i++) {
                if (digit == 1 && li.get(i)[digit] == -2) {
                    int k = (int)(Math.random()*2);
                    String p = k>0?"0.01":"0.1";
                    BigDecimal multiply = new BigDecimal(numList.get(i)).multiply(new BigDecimal(p));
                    saveList.add(multiply.toString());
                } else {
                    saveList.add(numList.get(i));
                }
            }
        }
    }

    public List<int[]> getRandomList(int num,String[] magnify,String[] magnify2) {
        List<int[]> li = new ArrayList<>();
        for (int i=0;i<num;i++) {
            int[] ints = new int[2];
            int x = 0;
            if (magnify != null) {
                int j = (int)(Math.random()*magnify.length);
                ints[0] = j;
                if ("1".equals(magnify[j])) {
                    x = -1;
                }
            } else {
                ints[0] = -1;
            }
            if (magnify2 != null) {
                if (x == -1) {
                    int k = 0;
                    int frequency = 0;
                    ints[1] = k;
                    while ("1".equals(magnify2[k])) {
                        frequency++;
                        if (frequency > 1000) {
                            ints[1] = -2;
                            break;
                        }
                        k = (int)(Math.random()*magnify2.length);
                        ints[1] = k;
                    }
                } else {
                    int k = (int)(Math.random()*magnify2.length);
                    ints[1] = k;
                }
            } else {
                if (x == -1) {
                    ints[1] = -2;
                } else {
                    ints[1] = -1;
                }
            }
            li.add(ints);
        }
        return li;
    }

    /**
     * 小数乘法分支，目前只适配【小数*整数】
     */
    private List<Map<String, String>> assDecimalContainer2(List<Map<String, String>> mapList,List<String>  firstValues,
                                                          List<String>  secondValues,KnowledgeList knowledgeList,
                                                          String classfitionId, String operate) {

        for (int i = 0; i < firstValues.size(); i++) {
            Map<String,String>  map = new HashMap<>();
            map.put("first",firstValues.get(i));
            map.put("second",secondValues.get(i));
            map.put("operate",knowledgeList.getKnowledgeOperation());
            map.put("classifyId",classfitionId);
            if (operate.equals("1")){
                BigDecimal add = new BigDecimal(String.valueOf(firstValues.get(i))).add(new BigDecimal(String.valueOf(secondValues.get(i))));
                BigDecimal bigDecimal1 = add.stripTrailingZeros();
                String s = bigDecimal1.toPlainString();
                map.put("result", s);
            }else if (operate.equals("2")){
                BigDecimal add = new BigDecimal(String.valueOf(firstValues.get(i))).subtract(new BigDecimal(String.valueOf(secondValues.get(i))));
                BigDecimal bigDecimal1 = add.stripTrailingZeros();
                String s = bigDecimal1.toPlainString();
                map.put("result", s);
            }else if (operate.equals("3")){
                BigDecimal add = new BigDecimal(String.valueOf(firstValues.get(i))).multiply(new BigDecimal(String.valueOf(secondValues.get(i))));
                BigDecimal bigDecimal1 = add.stripTrailingZeros();
                String s = bigDecimal1.toPlainString();
                map.put("result", s);
            }else {
                map.put("result", new BigDecimal(firstValues.get(i)).divide(new BigDecimal(secondValues.get(i))).toPlainString());
            }
            mapList.add(map);
        }
        return  mapList;
    }


    public List<Map<String,String>>   assContainer( List<Map<String,String>> mapList, Map<String, List<String>> stringListMap,
                                                    KnowledgeList  knowledgeList, String  classfitionId, String  operate){
        List<String> firstList = stringListMap.get("first");
        List<String> secondList = stringListMap.get("second");
        List<String> threeList = stringListMap.get("three");
        for (int i = 0; i < firstList.size(); i++) {
            Map<String,String>  map = new HashMap<>();
            map.put("first",firstList.get(i));
            map.put("second",secondList.get(i));
            if (threeList != null && threeList.size() > 0) {
                map.put("three",threeList.get(i));
            }
            map.put("operate",knowledgeList.getKnowledgeOperation());
            map.put("classifyId",classfitionId);
            if (operate.equals("1")){
                String str = "";
                if (threeList != null && threeList.size() > 0) {
                    str = Integer.parseInt(firstList.get(i))+Integer.parseInt(secondList.get(i))
                            +Integer.parseInt(threeList.get(i))+"";
                } else {
                    str = Integer.parseInt(firstList.get(i))+Integer.parseInt(secondList.get(i)) + "";
                }
                map.put("result", str);
            }else if (operate.equals("2")){
                String str = "";
                if (threeList != null && threeList.size() > 0) {
                    str = Integer.parseInt(firstList.get(i))-Integer.parseInt(secondList.get(i))
                            -Integer.parseInt(threeList.get(i))+"";
                } else {
                    str = Integer.parseInt(firstList.get(i))-Integer.parseInt(secondList.get(i)) + "";
                }
                map.put("result", str);
            }else if (operate.equals("3")){
                map.put("result", Integer.parseInt(firstList.get(i)) * Integer.parseInt(secondList.get(i))+"");
            }else {
                map.put("result", Integer.parseInt(firstList.get(i)) / Integer.parseInt(secondList.get(i))+"");
            }

            mapList.add(map);
        }

        return  mapList;
    }
}
