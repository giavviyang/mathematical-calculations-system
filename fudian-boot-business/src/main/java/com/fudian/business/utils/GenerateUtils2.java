package com.fudian.business.utils;

import com.fudian.business.pojo.ExercisesRules;
import com.fudian.common.utils.StringUtils;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateUtils2 {

    /**
     * 整数加法
        * @param rule 题目生成规则
        * @param resultValue 计算结果
        * @param count 题数
     */
    public static Map<String, List<String>> generateAddValue(ExercisesRules rule, int resultValue, int count) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        List<String> threeList = new ArrayList<>();
        int cycles = 0;
        while (cycles < 20000) {
            cycles++;
            //判断第一个数是否符合标准
            int firstValue = (int) (Math.random() * rule.getNumFirst());
            if (firstValue < rule.getFirstMin()) {
                continue;
            }
            //取模尾数不能为0,在进位计算的时候确保结果>10
            //例如两个数计算的时候,第一个结果个位为0,那第二个数无论是什么值,各位都不会进位
            if (firstValue % 10 == 0) {
                continue;
            }
            String s1 = String.valueOf(firstValue);
            if (rule.getNumFirstLen()!=0){
                int len1 = s1.length();
                if (len1!=rule.getNumFirstLen()){
                    continue;
                }
            }
            //判断第二个数是否符合标准
            int secondValue = (int) (Math.random() * rule.getNumSecond());
            if (secondValue <= rule.getSecondMin()) {
                continue;
            }
            String s2 = String.valueOf(secondValue);
            if (rule.getNumSecondLen()!=0){
                int len2 = s2.length();
                if (len2!=rule.getNumSecondLen()){
                    continue;
                }
            }
            //判断第三个数是否符合标准
            int threeValue = 0;
            String s3 = "";
            if (rule.getNumThree() != 0) {
                threeValue = (int) (Math.random() * rule.getNumThree());
                if (threeValue <= rule.getSecondMin()) {
                    continue;
                }
                s3 = String.valueOf(threeValue);
                if (rule.getNumThreeLen()!=0){
                    int len3 = s3.length();
                    if (len3!=rule.getNumThreeLen()){
                        continue;
                    }
                }
            }
            // 验证最大值
            int sum = firstValue + secondValue + threeValue;
            if (sum>resultValue){
                continue;
            }

            if (rule.getCarryRulers()==1){
                int result = getResult(s1, s2, s3, 1);
                if (result>=10){
                    continue;
                }
                firstList.add(s1);
                secondList.add(s2);
                if (!StringUtils.isEmpty(s3)) {
                    threeList.add(s3);
                }
                if (firstList.size()<count){
                    continue;
                }else {
                    break;
                }
            }else {
                //个位进位
                if (rule.getDigitPostion()==1){
                    int result = getResult(s1, s2, s3, 1);
                    if (result<10){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (!StringUtils.isEmpty(s3)) {
                        threeList.add(s3);
                    }
                    if (firstList.size()<count){
                        continue;
                    }else {
                        break;
                    }
                }else if(rule.getDigitPostion()==2){
                    //十位进位
                    int result = getResult(s1, s2, s3, 1);
                    if (result>=10){
                        continue;
                    }
                    int result1 = getResult(s1, s2, s3, 2);
                    if (result1<10){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (!StringUtils.isEmpty(s3)) {
                        threeList.add(s3);
                    }
                    if (firstList.size()<count){
                        continue;
                    }else {
                        break;
                    }
                }else {
                    //十位进位
                    int result = getResult(s1, s2, s3, 1);
                    if (result<10){
                        continue;
                    }
                    int result1 = getResult(s1, s2, s3, 2);
                    if (result1<10){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (!StringUtils.isEmpty(s3)) {
                        threeList.add(s3);
                    }
                    if (firstList.size()<count){
                        continue;
                    }else {
                        break;
                    }
                }
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        if (rule.getNumThree() != 0)
            map.put("three",threeList);
        return map;
    }

    /**
     * 整数减法
     * @author hong 2022/1/7
        * @param rule 题目生成规则
        * @param resultValue 计算结果
        * @param count 题数
     * @return java.util.Map<java.lang.String,java.util.List<java.lang.String>>
     */
    public static Map<String, List<String>>  generateSubtractValue(ExercisesRules rule, int resultValue, int count){
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        List<String> threeList = new ArrayList<>();
        int cycles = 0;
        while(cycles < 20000){
            cycles++;

            //判断第一个数是否符合标准
            int firstValue = (int) (Math.random() * rule.getNumFirst());
            if (firstValue < rule.getFirstMin()) {
                continue;
            }
            //取模尾数不能为0,在进位计算的时候确保结果>10
            //例如两个数计算的时候,第一个结果个位为0,那第二个数无论是什么值,各位都不会进位
            if (firstValue % 10 == 0) {
                continue;
            }
            String s1 = String.valueOf(firstValue);
            if (rule.getNumFirstLen()!=0){
                int len1 = s1.length();
                if (len1!=rule.getNumFirstLen()){
                    continue;
                }
            }
            //判断第二个数是否符合标准
            int secondValue = (int) (Math.random() * rule.getNumSecond());
            if (secondValue <= rule.getSecondMin()) {
                continue;
            }
            String s2 = String.valueOf(secondValue);
            if (rule.getNumSecondLen()!=0){
                int len2 = s2.length();
                if (len2!=rule.getNumSecondLen()){
                    continue;
                }
            }
            //判断第三个数是否符合标准
            int threeValue = 0;
            String s3 = "";
            if (rule.getNumThree() != 0) {
                threeValue = (int) (Math.random() * rule.getNumThree());
                if (threeValue <= rule.getSecondMin()) {
                    continue;
                }
                s3 = String.valueOf(threeValue);
                if (rule.getNumThreeLen()!=0){
                    int len3 = s3.length();
                    if (len3!=rule.getNumThreeLen()){
                        continue;
                    }
                }
            }

            //差值
            int diff = firstValue - secondValue - threeValue;
            if (diff<0){
                continue;
            }

            //不退位
            if (rule.getCarryRulers()==1){
                int diffResult = getDiffResult(s1, s2, s3, 1);
                if (diffResult<0){
                    continue;
                }
                firstList.add(s1);
                secondList.add(s2);
                if (!StringUtils.isEmpty(s3)) {
                    threeList.add(s3);
                }
                if (firstList.size()<count){
                    continue;
                }else {
                    break;
                }
            }else {
                //个位退位
                if (rule.getDigitPostion()==1){
                    int result = getDiffResult(s1, s2, s3, 1);
                    if (result>=0){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (!StringUtils.isEmpty(s3)) {
                        threeList.add(s3);
                    }
                    if (firstList.size()<count){
                        continue;
                    }else {
                        break;
                    }
                }else if(rule.getDigitPostion()==2){
                    //十位进位
                    int result = getDiffResult(s1, s2, s3, 1);
                    //小于0是退位
                    if (result<0){
                        continue;
                    }
                    int result1 = getDiffResult(s1, s2, s3, 2);
                    if (result1>=0){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (!StringUtils.isEmpty(s3)) {
                        threeList.add(s3);
                    }
                    if (firstList.size()<count){
                        continue;
                    }else {
                        break;
                    }
                }else {
                    //连续退位
                    int result = getDiffResult(s1, s2, s3, 1);
                    if (result>0){
                        continue;
                    }
                    int result1 = getDiffResult(s1, s2, s3, 2);
                    if (result1>0){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (!StringUtils.isEmpty(s3)) {
                        threeList.add(s3);
                    }
                    if (firstList.size()<count){
                        continue;
                    }else {
                        break;
                    }
                }
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        if (rule.getNumThree() != 0)
            map.put("three",threeList);
        return map;
    }

    /**
     * 分数乘法
     */
    public static Map<String, List<String>> fractionMultiplication(ExercisesRules rule, int count) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
        int cycles = 0;
        Fraction fraction = new Fraction(rule.getFirstMin(),rule.getNumFirst(),0);
        Fraction fraction2 = new Fraction(rule.getSecondMin(),rule.getNumSecond(),0);
        while (cycles < 20000) {
            cycles++;
            //第一个数
            String first = generateValueByType(fraction,rule.getFirstNumType(),rule.getNumFirst());
            //第二个数
            String second = generateValueByType(fraction2,rule.getSecondNumType(),rule.getNumSecond());
            //结果
            String result = "";
            if (rule.getCarryRulers() == 1) {// 1-不可约分、2-可约分
                if (rule.getFirstNumType() == 3 && rule.getSecondNumType() == 3) {// 1-整数、2-小数、3-分数
                    if (fraction.getNumerator() * fraction2.getNumerator() > 100 ||
                            fraction.getDenominator() * fraction2.getDenominator() > 100) {
                        continue;
                    }
                    int simplify = CalculationUtils.isSimplify(fraction.getNumerator() * fraction2.getNumerator(),
                            fraction.getDenominator() * fraction2.getDenominator());
                    if (simplify == 0) {//0-可约分,1-不可约分
                        continue;
                    }
                    result = fraction.getNumerator() * fraction2.getNumerator() + "/"
                            + fraction.getDenominator() * fraction2.getDenominator();
                } else if (rule.getFirstNumType() == 3 && rule.getSecondNumType() == 1) {// 1-整数、2-小数、3-分数
                    if (Integer.valueOf(second) * fraction.getNumerator() > 100 || Integer.valueOf(second) < 2) {
                        continue;
                    }
                    int simplify = CalculationUtils.isSimplify(Integer.valueOf(second) * fraction.getNumerator(),
                            fraction.getDenominator());
                    if (simplify==0) {//0-可约分,1-不可约分
                        continue;
                    }
                    result = Integer.valueOf(second) * fraction.getNumerator() + "/" + fraction.getDenominator();
                } else if (rule.getFirstNumType() == 1 && rule.getSecondNumType() == 3) {// 1-整数、2-小数、3-分数
                    if (Integer.valueOf(first) * fraction2.getNumerator() > 100 || Integer.valueOf(first) < 2) {
                        continue;
                    }
                    int simplify = CalculationUtils.isSimplify(Integer.valueOf(first) * fraction2.getNumerator(),
                            fraction2.getDenominator());
                    if (simplify==0) {//0-可约分,1-不可约分
                        continue;
                    }
                    result = Integer.valueOf(first) * fraction2.getNumerator() + "/" + fraction2.getDenominator();
                }
            } else if (rule.getCarryRulers() == 2) {// 1-不可约分、2-可约分
                if (rule.getFirstNumType() == 3 && rule.getSecondNumType() == 2) {// 1-整数、2-小数、3-分数
                    if (Integer.valueOf(second) < 10 || Integer.valueOf(second) >= 100) {
                        continue;
                    }
                    if (fraction.getNumerator() % fraction.getDenominator() == 0 ||
                            fraction.getDenominator() % fraction.getNumerator() == 0) {
                        continue;
                    }
                    if (fraction.getNumerator() * Integer.valueOf(second) % fraction.getDenominator() != 0) {
                        continue;
                    }
                    BigDecimal value = new BigDecimal(second);
                    BigDecimal value2 = value.multiply(new BigDecimal(rule.getSecondMagnify()));
                    int value3 = fraction.getNumerator() * Integer.valueOf(second) / fraction.getDenominator();
                    second = value2.toString();
                    result = new BigDecimal(String.valueOf(value3))
                            .multiply(new BigDecimal(rule.getSecondMagnify())).toString();
                } else if (rule.getFirstNumType() == 2 && rule.getSecondNumType() == 3) {// 1-整数、2-小数、3-分数
                    if (Integer.valueOf(first) < 10 || Integer.valueOf(second) >= 100) {
                        continue;
                    }
                    if (fraction2.getNumerator() % fraction2.getDenominator() == 0 ||
                            fraction2.getDenominator() % fraction2.getNumerator() == 0) {
                        continue;
                    }
                    if (fraction2.getNumerator() * Integer.valueOf(first) % fraction2.getDenominator() != 0) {
                        continue;
                    }
                    BigDecimal value = new BigDecimal(first);
                    BigDecimal value2 = value.multiply(new BigDecimal(rule.getFirstMagnify()));
                    first = value2.toString();
                    int value3 = fraction2.getNumerator() * Integer.valueOf(first) / fraction2.getDenominator();
                    result = new BigDecimal(String.valueOf(value3))
                            .multiply(new BigDecimal(rule.getFirstMagnify())).toString();
                } else if (rule.getFirstNumType() == 3 && rule.getSecondNumType() == 1) {// 1-整数、2-小数、3-分数
                    if (Integer.valueOf(second) < 10 && fraction.getDenominator() < 10) {
                        continue;
                    }
                    if (Integer.valueOf(second) >= 10 && fraction.getDenominator() >= 10) {
                        continue;
                    }
                    if (fraction.getNumerator() % fraction.getDenominator() == 0 ||
                            fraction.getDenominator() % fraction.getNumerator() == 0) {
                        continue;
                    }
                    int simplify = CalculationUtils.isSimplify(fraction.getNumerator()*Integer.valueOf(second),
                            fraction.getDenominator());
                    if (simplify == 1) {
                        continue;
                    }
                    String value = CalculationUtils.simplifiedValue(fraction.getNumerator() * Integer.valueOf(second)
                            + "/" + fraction.getDenominator());
                    result = value;
                } else if (rule.getFirstNumType() == 1 && rule.getSecondNumType() == 3) {// 1-整数、2-小数、3-分数
                    if (Integer.valueOf(first) < 10 && fraction2.getDenominator() < 10) {
                        continue;
                    }
                    if (Integer.valueOf(first) >= 10 && fraction2.getDenominator() >= 10) {
                        continue;
                    }
                    if (fraction2.getNumerator() % fraction2.getDenominator() == 0 ||
                            fraction2.getDenominator() % fraction2.getNumerator() == 0) {
                        continue;
                    }
                    int simplify = CalculationUtils.isSimplify(fraction2.getNumerator()*Integer.valueOf(first),
                            fraction2.getDenominator());
                    if (simplify == 1) {
                        continue;
                    }
                    String value = CalculationUtils.simplifiedValue(fraction2.getNumerator() * Integer.valueOf(first)
                            + "/" + fraction2.getDenominator());
                    result = value;
                }
            }
            firstList.add(first);
            secondList.add(second);
            resultList.add(result);
            if (firstList.size() >= count) {
                break;
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        map.put("result",resultList);
        return map;
    }

    /**
     *
     * @param firstValue
     * @param secondValue
     * @param type 1返回个位和结果否则返回十位和结果
     * @return
     */
    private static int getResult(String firstValue, String secondValue, String threeValue, int type){
        int length = firstValue.length();
        int length1 = secondValue.length();
        int length2 = threeValue.length();
        int result = 0;
        if (type==1){
            String s1Sub = firstValue.substring(length - 1, length);
            String s2Sub = secondValue.substring(length1 - 1, length1);
            String s3Sub = "";
            if (!StringUtils.isEmpty(threeValue)) {
                s3Sub = threeValue.substring(length2 - 1, length2);
            }
            int v1 = Integer.parseInt(s1Sub);
            int v2 = Integer.parseInt(s2Sub);
            int v3 = 0;
            if (!StringUtils.isEmpty(s3Sub)) {
                v3 = Integer.parseInt(s3Sub);
            }
            result = v1+v2+v3;
        }else if(type==2){
            String s1Sub = firstValue.substring(length - 2, length - 1);
            String s2Sub = "0";
            if (secondValue.length()>1)
                s2Sub = secondValue.substring(length1 - 2, length1 - 1);
            String s3Sub = "";
            if (!StringUtils.isEmpty(threeValue) && threeValue.length()>1) {
                s3Sub = threeValue.substring(length1 - 2, length1 - 1);
            }
            int v1 = Integer.parseInt(s1Sub);
            int v2 = Integer.parseInt(s2Sub);
            int v3 = StringUtils.isEmpty(s3Sub)?0:Integer.parseInt(s3Sub);
            result = v1+v2+v3;
        }
        return result;
    }

    /**
     *
     * @param firstValue
     * @param secondValue
     * @param type 1返回个位和结果否则返回十位和结果
     * @return
     */
    private static int getDiffResult(String firstValue, String secondValue, String threeValue, int type){
        int length = firstValue.length();
        int length1 = secondValue.length();
        int length2 = threeValue.length();
        int result = 0;
        if (type==1){
            String s1Sub = firstValue.substring(length - 1, length);
            String s2Sub = secondValue.substring(length1 - 1, length1);
            String s3Sub = "";
            if (!StringUtils.isEmpty(threeValue)) {
                s3Sub = threeValue.substring(length2 - 1, length2);
            }
            int v1 = Integer.parseInt(s1Sub);
            int v2 = Integer.parseInt(s2Sub);
            int v3 = 0;
            if (!StringUtils.isEmpty(s3Sub)) {
                v3 = Integer.parseInt(s3Sub);
            }
            result = v1-v2-v3;
        }else if(type==2){
            String s1Sub = firstValue.substring(length - 2, length - 1);
            String s2Sub = "0";
            if (secondValue.length()>1)
                s2Sub = secondValue.substring(length1 - 2, length1 - 1);
            String s3Sub = "";
            if (!StringUtils.isEmpty(threeValue) && threeValue.length()>1)
                s3Sub = threeValue.substring(length1 - 2, length1 - 1);
            int v1 = Integer.parseInt(s1Sub);
            int v2 = Integer.parseInt(s2Sub);
            int v3 = StringUtils.isEmpty(s3Sub)?0:Integer.parseInt(s3Sub);
            result = v1-v2-v3;
        }
        return result;
    }

    /**
     * 根据类型生成数值
     */
    public static String generateValueByType(Fraction fraction,int numType,int maxNum) {
        if (numType == 3) {
            fraction.generateNumber();
            return fraction.getValue();
        } else if (numType == 2) {
            Integer value;
            while (true){
                int value1 = (int)(Math.random()*maxNum)+1;
                String s = String.valueOf(value1);
                String substring = s.substring(s.length() - 1);
                Integer integer = Integer.valueOf(substring);
                if (integer == 0){
                    continue;
                }
                value = value1;
                break;
            }
            return String.valueOf(value);
        } else {
            int value = (int)(Math.random()*maxNum)+1;
            return String.valueOf(value);
        }
    }

    public static String test(Fraction fraction,int numType,int maxNum,double magnify) {
        if (numType == 3) {
            fraction.generateNumber();
            return fraction.getValue();
        } else if (numType == 2) {
            int decimal = (int)(Math.random()*maxNum)+1;
            BigDecimal value = new BigDecimal(String.valueOf(decimal));
            BigDecimal decimal2 = value.multiply(new BigDecimal(String.valueOf(magnify)));
            return decimal2.toString();
        } else {
            int value = (int)(Math.random()*maxNum)+1;
            return String.valueOf(value);
        }
    }

}
