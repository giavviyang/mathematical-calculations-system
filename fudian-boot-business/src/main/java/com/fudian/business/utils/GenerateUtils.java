package com.fudian.business.utils;

import com.fudian.business.pojo.ExercisesRules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据生成集合
 */
public class GenerateUtils {

    /**
     * 加法生成规则
     * @param numFirst      数据第一个参数 一般取值 10 100 ...
     * @param minValue      最小的的一个值 0 10 ...
     * @param firstCharlen  第一个参数的位数 1 2
     * @param numSecond     数据第二个参数  一般取值 10 100 ...
     * @param secondCharlen 第二个参数的位数 1 2
     * @param resultValue   结果范围 100以内的  或者 200以内的
     * @param carryRule     进位规则  0为表内规则  1为不进位   2为进位
     * @param carryNum      具体进位数位  1个位  2十位  3连续进位....
     * @param frequency     出题数
     * @return
     */
    public static Map<String, List<String>> generateAddValue(int numFirst,
                                                             int minValue,
                                                             int firstCharlen,
                                                             int numSecond,
                                                             int secondminValue,
                                                             int secondCharlen,
                                                             int resultValue,
                                                             int carryRule,
                                                             int carryNum,
                                                             int frequency) {
//        int  numFirst = 100;
//        int  minValue = 10;
//        int  firstCharlen =2 ;
//        int  numSecond = 100;
//        int  secondCharlen =2 ;
//        int resultValue = 200;
//        int  carryRule =2;
//        int  carryNum = 2;
//        int  frequency= 20;
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        int cycles = 0;
        while(cycles < 20000){
            cycles++;
            //判断当前的位数是否符合标准
            int firstValue  =  (int)(Math.random() * numFirst);
            if (firstValue<minValue){
                continue;
            }
            //取模尾数不能为0
            if (firstValue%10==0){
                continue;
            }
            String  s1 = String.valueOf(firstValue);
            if (firstCharlen!=0){
                int len1 = s1.length();
                if (len1!=firstCharlen){
                    continue;
                }
            }

            //判断当前的位数是否符合标准
            int secondValue =  (int)(Math.random() * numSecond);
            //取模尾数不能为0
            if (secondValue%10==0){
                continue;
            }
            if (secondValue<=secondminValue){
                continue;
            }
            String  s2 = String.valueOf(secondValue);
            if (secondCharlen!=0){
                int len2 = s2.length();
                if (len2!=secondCharlen){
                    continue;
                }
            }

            int sum = firstValue + secondValue;
            if (sum>resultValue){
                continue;
            }

            if (carryRule==1){
                int result = getResult(s1, s2, 1);
                if (result>=10){
                    continue;
                }
                firstList.add(s1);
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }else {
                //个位进位
                if (carryNum==1){
                    int result = getResult(s1, s2, 1);
                    if (result<10){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }else if(carryNum==2){
                    if (s2.length()>1){
                        //十位进位
                        int result = getResult(s1, s2, 1);
                        if (result>=10){
                            continue;
                        }
                        int result1 = getResult(s1, s2, 2);
                        if (result1<10){
                            continue;
                        }
                    }else {
                        int result1 = getResult(s1, s2, 2);
                        if (result1<10){
                            continue;
                        }
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }else {
//                    //十位进位
                    int result = getResult(s1, s2, 1);
                    if (result<10){
                        continue;
                    }
                    int result1 = getResult(s1, s2, 2);
                    if (result1<10){
                        continue;
                    }
//                    if (s2.length()>1){
//                        //十位进位
//                        int result = getResult(s1, s2, 1);
//                        if (result>=10){
//                            continue;
//                        }
//                        int result1 = getResult(s1, s2, 2);
//                        if (result1<10){
//                            continue;
//                        }
//                    }else {
//                        int result1 = getResult(s1, s2, 2);
//                        if (result1<10){
//                            continue;
//                        }
//                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        return map;
    }

    public static Map<String, List<String>> generateAddValue1(int numFirst,
                                                             int minValue,
                                                             int firstCharlen,
                                                             int numSecond,
                                                             int secondminValue,
                                                             int secondCharlen,
                                                             int resultValue,
                                                             int carryRule,
                                                             int carryNum,
                                                             int frequency) {
//        int  numFirst = 100;
//        int  minValue = 10;
//        int  firstCharlen =2 ;
//        int  numSecond = 100;
//        int  secondCharlen =2 ;
//        int resultValue = 200;
//        int  carryRule =2;
//        int  carryNum = 2;
//        int  frequency= 20;
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        int cycles = 0;
        fd:while(cycles < 20000){
            cycles++;
            //判断当前的位数是否符合标准
            int firstValue  =  (int)(Math.random() * numFirst);
            if (firstValue<minValue){
                continue;
            }
            //取模尾数不能为0
            if (firstValue%10==0){
                continue;
            }
            String  s1 = String.valueOf(firstValue);
            if (firstCharlen!=0){
                int len1 = s1.length();
                if (len1!=firstCharlen){
                    continue;
                }
            }

            //判断当前的位数是否符合标准
            int secondValue =  (int)(Math.random() * numSecond);
            //取模尾数不能为0
            if (secondValue%10==0){
                continue;
            }
            if (secondValue<=secondminValue){
                continue;
            }
            String  s2 = String.valueOf(secondValue);
            if (secondCharlen!=0){
                int len2 = s2.length();
                if (len2!=secondCharlen){
                    continue;
                }
            }

            int sum = firstValue + secondValue;
            if (sum>=resultValue){
                continue;
            }

            if (carryRule==1){
                for (int i=0;i<s1.length();i++){
                    String s1Char = s1.substring(i, i + 1);
                    int a =  Integer.parseInt(s1Char);
                    for (int j=0;j<s2.length();j++){
                        String s2Char = s2.substring(j, j + 1);
                        int b = Integer.parseInt(s2Char);
                        if (a+b>=10){
                            continue fd;
                        }
                    }
                }
                firstList.add(s1);
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }else {
                //个位进位
                if (carryNum == 1) {
                    for (int i = 0; i < s1.length(); i++) {
                        String s1Char = s1.substring(i, i + 1);
                        int a = Integer.parseInt(s1Char);
                        for (int j = 0; j < s2.length(); j++) {
                            String s2Char = s2.substring(j, j + 1);
                            int b = Integer.parseInt(s2Char);
                            if (j == s2.length() - 1) {
                                if (a + b < 10) {
                                    continue fd;
                                }
                            } else {
                                if (a + b >= 10) {
                                    continue fd;
                                }
                            }

                        }
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size() < frequency) {
                        continue;
                    } else {
                        break;
                    }
                } else if (carryNum == 2) {  //十位进位
                    for (int i = 0; i < s1.length(); i++) {
                        String s1Char = s1.substring(i, i + 1);
                        int a = Integer.parseInt(s1Char);
                        if(s2.length()>=2){
                            for (int j = 0; j < s2.length(); j++) {
                                String s2Char = s2.substring(j, j + 1);
                                int b = Integer.parseInt(s2Char);
                                if (j == s2.length() - 2) {
                                    if (a + b < 10) {
                                        continue fd;
                                    }
                                } else {
                                    if (a + b >= 10) {
                                        continue fd;
                                    }
                                }
                            }
                        }else {
                            int b = Integer.parseInt(s2);
                            if (i==s1.length()-2){
                                if (a+b<10){
                                    continue fd;
                                }
                            }else {
                                if (a+b>=10){
                                    continue fd;
                                }
                            }
                        }

                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size() < frequency) {
                        continue;
                    } else {
                        break;
                    }
                } else { //连续进位
                    for (int i = 0; i < s1.length(); i++) {
                        String s1Char = s1.substring(i, i + 1);
                        int a = Integer.parseInt(s1Char);
                        for (int j = 0; j < s2.length(); j++) {
                            String s2Char = s2.substring(j, j + 1);
                            int b = Integer.parseInt(s2Char);
                            if (a + b < 10) {
                                continue fd;
                            }
                        }
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        return map;
    }


    /**
     *
     * @param firstValue
     * @param secondValue
     * @param type 1返回个位和结果否则返回十位和结果
     * @return
     */
    private static int   getResult(String firstValue, String secondValue, int type){
        int length = firstValue.length();
        int length1 = secondValue.length();
        int result = 0;
        if (type==1){
            String s1Sub = firstValue.substring(length - 1, length);
            String s2Sub = secondValue.substring(length1 - 1, length1);
            int v1 = Integer.parseInt(s1Sub);
            int v2 = Integer.parseInt(s2Sub);
            result = v1+v2;
        }else if(type==2){
//            String s1Sub = firstValue.substring(length - 2, length - 1);
//            String s2Sub = secondValue.substring(length1 - 2, length1 - 1);
//            int v1 = Integer.parseInt(s1Sub);
//            int v2 = Integer.parseInt(s2Sub);
//            result = v1+v2;
            String s1Sub = firstValue.substring(length - 2, length - 1);
            if (length1>=2){
                String s2Sub = secondValue.substring(length1 - 2, length1 - 1);
                int v1 = Integer.parseInt(s1Sub);
                int v2 = Integer.parseInt(s2Sub);
                result = v1+v2;
            }else {
                int v1 = Integer.parseInt(s1Sub);
                int v2 = Integer.parseInt(secondValue);
                result = v1+v2;
            }

        }
        return  result;
    }



    /**
     * 减法生成规则
     * @param numFirst      数据第一个参数 一般取值 10 100 ...
     * @param minValue      最小的的一个值 0 10 ...
     * @param firstCharlen  第一个参数的位数 1 2
     * @param numSecond     数据第二个参数  一般取值 10 100 ...
     * @param secondCharlen 第二个参数的位数 1 2
     * @param resultValue   结果范围 100以内的  或者 200以内的
     * @param carryRule     进位规则  0为表内规则  1为不进位   2为进位
     * @param carryNum      具体进位数位  1个位  2十位  3连续进位....
     * @param frequency     出题数
     * @return
     */
    public static Map<String, List<String>>  generateSubtractValue(int numFirst,
                                                                   int minValue,
                                                                   int firstCharlen,
                                                                   int numSecond,
                                                                   int secondCharlen,
                                                                   int resultValue,
                                                                   int carryRule,
                                                                   int carryNum,
                                                                   int frequency){
//        int  numFirst = 200;
//        int  minValue = 0;
//        int  firstCharlen =3 ;
//        int  numSecond = 100;
//        int  secondCharlen =0 ;
//        int resultValue = 200;
//        int  carryRule =2;
//        int  carryNum = 3;
//        int  frequency= 20;
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        while(true){
            //判断当前的位数是否符合标准
            int firstValue  =  (int)(Math.random() * numFirst);
            if (firstValue<minValue){
                continue;
            }
            //取模尾数不能为0
            if (firstValue%10==0){
                continue;
            }
            String  s1 = String.valueOf(firstValue);
            int len1 = s1.length();
            if (len1!=firstCharlen){
                continue;
            }

            //优先判断当前位数是否为null或者0 如果为0时则不数位限制
            int secondValue = (int)(Math.random() * numSecond);
            String  s2 = String.valueOf(secondValue);
            if (secondCharlen!=0){
                //判断当前的位数是否符合标准
                int len2 = s2.length();
                if (len2!=secondCharlen){
                    continue;
                }
            }
            //差值
            int diff = firstValue - secondValue;
            if (diff<0){
                continue;
            }

            //不退位
            if (carryRule==1){
                int diffResult = getDiffResult(s1, s2, 1);
                if (diffResult<0){
                    continue;
                }
                firstList.add(s1);
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }else {
                //个位退位
                if (carryNum==1){
                    int result = getDiffResult(s1, s2, 1);
                    if (result>=0){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }else if(carryNum==2){
                    //十位进位
                    int result = getDiffResult(s1, s2, 1);
                    //小于0是退位
                    if (result<0){
                        continue;
                    }
                    int result1 = getDiffResult(s1, s2, 2);
                    if (result1>=0){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }else {
                    //连续退位
                    int result = getDiffResult(s1, s2, 1);
                    if (result>0){
                        continue;
                    }
                    int result1 = getDiffResult(s1, s2, 2);
                    if (result1>0){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        return map;
    }

    public static Map<String, List<String>> generateSubtractValue1(int numFirst,
                                                                  int firstMin,
                                                                  int firstCharlen,
                                                                  int numSecond,
                                                                  int secondCharlen,
                                                                  int  secondMin,
                                                                  int resultValue,
                                                                  int carryRule,
                                                                  int carryNum,
                                                                  int frequency){
//        int  numFirst = 1000;
//        int  firstMin = 10;
//        int  firstCharlen =0 ;
//        int  numSecond = 100;
//        int  secondCharlen =2 ;
//        int  secondMin =0;
//        int resultValue = 10;
//        int  carryRule =2; // 1为不退位 2为退位
//        int  carryNum = 3; // 1为个位退位  2为十位退位 3为百分退位
//        int  frequency= 10;
            Map<String,List<String>> map = new HashMap<>();
            List<String> firstList = new ArrayList<>();
            List<String> secondList = new ArrayList<>();
            fd:while(true){
                //判断当前的位数是否符合标准
                int firstValue  =  (int)(Math.random() * numFirst);
                if (firstValue<firstMin){
                    continue;
                }
                //取模尾数不能为0
                if (firstValue%10==0){
                    continue;
                }
                String  s1 = String.valueOf(firstValue);
                if (firstCharlen!=0){
                    int len1 = s1.length();
                    if (len1!=firstCharlen){
                        continue;
                    }
                }

                //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                int secondValue = (int)(Math.random() * numSecond);
                //取模尾数不能为0
                if (secondValue%10==0){
                    continue;
                }
                if (secondValue<secondMin){
                    continue;
                }
                String  s2 = String.valueOf(secondValue);
                if (secondCharlen!=0){
                    //判断当前的位数是否符合标准
                    int len2 = s2.length();
                    if (len2!=secondCharlen){
                        continue;
                    }
                }
                //差值
//                int diff = firstValue - secondValue;
//                if (diff<0){
//                    continue;
//                }
                if (s1.length()>=s2.length()){
                    int diff = firstValue - secondValue;
                    if (diff<0){
                        continue;
                    }
                }

                //不退位
                if (carryRule==1){
                    for (int i=0;i<s1.length();i++){
                        if (i+1<=s2.length()){
                            int length = s1.length();
                            int length1 = s2.length();
                            String s1Sub = s1.substring(length - i-1, length- i);
                            String s2Sub = s2.substring(length1 -i-1, length1- i);
                            int v1 = Integer.parseInt(s1Sub);
                            int v2 = Integer.parseInt(s2Sub);
                            int result = v1-v2;
                            if (result<0){
                                continue fd;
                            }
                        }

                    }

                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }else {
                    //个位退位
                    if (carryNum==1){
                        for (int i=0;i<s1.length();i++){
                            if (i+1<=s2.length()){
                                int length = s1.length();
                                int length1 = s2.length();
                                String s1Sub = s1.substring(length - i-1, length- i);
                                String s2Sub = s2.substring(length1 -i-1, length1- i);
                                int v1 = Integer.parseInt(s1Sub);
                                int v2 = Integer.parseInt(s2Sub);
                                int result = v1-v2;
                                if (i==s2.length()-1){//当前为个位
                                    if (result<0){
                                        continue fd;
                                    }
                                }else {
                                    if (result>0){
                                        continue fd;
                                    }
                                }

                            }

                        }
                        firstList.add(s1);
                        secondList.add(s2);
                        if (firstList.size()<frequency){
                            continue;
                        }else {
                            break;
                        }
                    }else if (carryNum==2){
                        for (int i=0;i<s1.length();i++){
                            if (i+1<=s2.length()){
                                int length = s1.length();
                                int length1 = s2.length();
                                String s1Sub = s1.substring(length - i-1, length- i);
                                String s2Sub = s2.substring(length1 -i-1, length1- i);
                                int v1 = Integer.parseInt(s1Sub);
                                int v2 = Integer.parseInt(s2Sub);
                                int result = v1-v2;
                                if (i==s2.length()-1){//当前为个位
                                    if (result>0){
                                        continue fd;
                                    }
                                }else {
                                    if (result<0){
                                        continue fd;
                                    }
                                }

                            }

                        }
                        firstList.add(s1);
                        secondList.add(s2);
                        if (firstList.size()<frequency){
                            continue;
                        }else {
                            break;
                        }

                    }else {
                        //连续退位
                        for (int i=0;i<s1.length();i++){
                            if (i+1<=s2.length()){
                                int length = s1.length();
                                int length1 = s2.length();
                                String s1Sub = s1.substring(length - i-1, length- i);
                                String s2Sub = s2.substring(length1 -i-1, length1- i);
                                int v1 = Integer.parseInt(s1Sub);
                                int v2 = Integer.parseInt(s2Sub);
                                int result = v1-v2;
                                if (result>0){
                                    continue fd;
                                }
                            }
                        }
                        firstList.add(s1);
                        secondList.add(s2);
                        if (firstList.size()<frequency){
                            continue;
                        }else {
                            break;
                        }
                    }
                }
            }
        map.put("first",firstList);
        map.put("second",secondList);
        return map;
    }


    public static Map<String, List<String>> generateSubtractValue2(int numFirst,
                                                                   int firstMin,
                                                                   int firstCharlen,
                                                                   int numSecond,
                                                                   int secondCharlen,
                                                                   int  secondMin,
                                                                   int resultValue,
                                                                   int carryRule,
                                                                   int carryNum,
                                                                   int frequency,String firstM,String secondM){
//        int  numFirst = 1000;
//        int  firstMin = 10;
//        int  firstCharlen =0 ;
//        int  numSecond = 100;
//        int  secondCharlen =2 ;
//        int  secondMin =0;
//        int resultValue = 10;
//        int  carryRule =2; // 1为不退位 2为退位
//        int  carryNum = 3; // 1为个位退位  2为十位退位 3为百分退位
//        int  frequency= 10;
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        int cycles = 0;
        fd:while(cycles < 20000){
            cycles++;
            //判断当前的位数是否符合标准
            int firstValue  =  (int)(Math.random() * numFirst);
            if (firstValue<firstMin){
                continue;
            }
            //取模尾数不能为0
            if (firstValue%10==0){
                continue;
            }
            String  s1 = String.valueOf(firstValue);
            if (firstCharlen!=0){
                int len1 = s1.length();
                if (len1!=firstCharlen){
                    continue;
                }
            }

            //优先判断当前位数是否为null或者0 如果为0时则不数位限制
            int secondValue = (int)(Math.random() * numSecond);
            //取模尾数不能为0
            if (secondValue%10==0){
                continue;
            }
            if (secondValue<secondMin){
                continue;
            }
            String  s2 = String.valueOf(secondValue);
            if (secondCharlen!=0){
                //判断当前的位数是否符合标准
                int len2 = s2.length();
                if (len2!=secondCharlen){
                    continue;
                }
            }
            //差值
//                int diff = firstValue - secondValue;
//                if (diff<0){
//                    continue;
//                }
//            if (s1.length()>=s2.length()){
//                int diff = firstValue - secondValue;
//                if (diff<0){
//                    continue;
//                }
//            }

            BigDecimal firstValueB = new BigDecimal(firstValue);
            if (firstM!=null&&!firstM.equals("")&&!firstM.equals("0.0")&&!firstM.equals("0")){
                firstValueB = firstValueB.multiply(new BigDecimal(firstM));
            }
            BigDecimal secondValueB = new BigDecimal(secondValue);
            if (secondM!=null&&!secondM.equals("")&&!secondM.equals("0.0")&&!secondM.equals("0")){
                secondValueB = secondValueB.multiply(new BigDecimal(secondM));
            }
            if (firstValueB.doubleValue() < secondValueB.doubleValue()) {
                continue fd;
            }

            //不退位
            if (carryRule==1){
                boolean carryOrAbdicate = CalculationUtils.isCarryOrAbdicate(firstValueB.toString(), secondValueB.toString(), 2);
                if (carryOrAbdicate){
                    continue fd;
                }
                firstList.add(s1);
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }else {
                boolean carryOrAbdicate = CalculationUtils.isCarryOrAbdicate(firstValueB.toString(), secondValueB.toString(), 2);
                if (!carryOrAbdicate){
                    continue fd;
                }
                //个位退位
                if (carryNum==1){
//                    for (int i=0;i<s1.length();i++){
//                        if (i+1<=s2.length()){
//                            int length = s1.length();
//                            int length1 = s2.length();
//                            String s1Sub = s1.substring(length - i-1, length- i);
//                            String s2Sub = s2.substring(length1 -i-1, length1- i);
//                            int v1 = Integer.parseInt(s1Sub);
//                            int v2 = Integer.parseInt(s2Sub);
//                            int result = v1-v2;
//                            if (i==s2.length()-1){//当前为个位
//                                if (result<0){
//                                    continue fd;
//                                }
//                            }else {
//                                if (result>0){
//                                    continue fd;
//                                }
//                            }
//
//                        }
//
//                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }else if (carryNum==2){
//                    for (int i=0;i<s1.length();i++){
//                        if (i+1<=s2.length()){
//                            int length = s1.length();
//                            int length1 = s2.length();
//                            String s1Sub = s1.substring(length - i-1, length- i);
//                            String s2Sub = s2.substring(length1 -i-1, length1- i);
//                            int v1 = Integer.parseInt(s1Sub);
//                            int v2 = Integer.parseInt(s2Sub);
//                            int result = v1-v2;
//                            if (i==s2.length()-1){//当前为个位
//                                if (result>0){
//                                    continue fd;
//                                }
//                            }else {
//                                if (result<0){
//                                    continue fd;
//                                }
//                            }
//
//                        }
//
//                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }

                }else {
                    //连续退位
//                    for (int i=0;i<s1.length();i++){
//                        if (i+1<=s2.length()){
//                            int length = s1.length();
//                            int length1 = s2.length();
//                            String s1Sub = s1.substring(length - i-1, length- i);
//                            String s2Sub = s2.substring(length1 -i-1, length1- i);
//                            int v1 = Integer.parseInt(s1Sub);
//                            int v2 = Integer.parseInt(s2Sub);
//                            int result = v1-v2;
//                            if (result>0){
//                                continue fd;
//                            }
//                        }
//                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        return map;
    }
    /**
     *
     * @param firstValue
     * @param secondValue
     * @param type
     * @return
     */
    private static int   getDiffResult(String firstValue, String secondValue, int type){
        int length = firstValue.length();
        int length1 = secondValue.length();
        int result = 0;
        if (type==1){
            String s1Sub = firstValue.substring(length - 1, length);
            String s2Sub = secondValue.substring(length1 - 1, length1);
            int v1 = Integer.parseInt(s1Sub);
            int v2 = Integer.parseInt(s2Sub);
            result = v1-v2;
        }else if(type==2){
            String s1Sub = firstValue.substring(length - 2, length - 1);
            String s2Sub = "";
            if (secondValue.length()>1){
                s2Sub = secondValue.substring(length1 - 2, length1 - 1);

            }else {
                s2Sub=secondValue;
            }
            int v1 = Integer.parseInt(s1Sub);
            int v2 = Integer.parseInt(s2Sub);
            result = v1-v2;
        }
        return  result;
    }


    /**
     * 乘法生成规则
     * @param numFirst      数据第一个参数 一般取值 10 100 ...
     * @param minValue      最小的的一个值 0 10 ...
     * @param firstCharlen  第一个参数的位数 1 2
     * @param numSecond     数据第二个参数  一般取值 10 100 ...
     * @param secondCharlen 第二个参数的位数 1 2
     * @param resultValue   结果范围 100以内的  或者 200以内的
     * @param carryRule     进位规则  0为表内规则  1为不进位   2为进位
     * @param carryNum      具体进位数位  1个位  2十位  3连续进位....
     * @param frequency     出题数
     * @return
     */
    public  static  Map<String, List<String>>  generateMultiValue(int numFirst,
                                                                  int minValue,
                                                                  int firstCharlen,
                                                                  int numSecond,
                                                                  int secondCharlen,
                                                                  int resultValue,
                                                                  int carryRule,
                                                                  int carryNum,
                                                                  int frequency){
//        int  numFirst = 100;
//        int  minValue = 10;
//        int  firstCharlen =2 ;
//        int  numSecond = 10;
//        int  secondCharlen =1 ;
//        int resultValue = 200;
//        int  carryRule =2;
//        int  carryNum = 2;
//        int  frequency= 20;
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        int cycles = 0;
        fd:while(cycles < 20000){
            cycles++;
            //判断当前的位数是否符合标准
            int firstValue  =  (int)(Math.random() * numFirst);
            if (firstValue<minValue){
                continue;
            }
            //取模尾数不能为0
            if (firstValue%10==0){
                continue;
            }
            String  s1 = String.valueOf(firstValue);
            int len1 = s1.length();
            if (firstCharlen != 0 && len1 != firstCharlen){
                continue;
            }

            //优先判断当前位数是否为null或者0 如果为0时则不数位限制
            int secondValue = (int)(Math.random() * numSecond);
            if (secondValue==0){
                continue ;
            }
            if (secondCharlen==2&&secondValue%10 !=0){
                continue fd;
            }
//             if (secondValue%10==0){
//                 continue;
//             }
            String  s2 = String.valueOf(secondValue);
            if (secondCharlen!=0){
                //判断当前的位数是否符合标准
                int len2 = s2.length();
                if (len2!=secondCharlen){
                    continue;
                }
            }

            //表内乘法
            if(carryRule==0){
                firstList.add(s1);
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }else if (carryRule==1){
                //不进位
                if (secondCharlen!=2){
                    for (int i = 0; i < s1.length(); i++) {
                        if (i == 0) {
                            int diffResult = getMultiResult(s1, s2, i+1);
                            if (diffResult>resultValue){
                                continue fd;
                            }
                        } else if (i == 1){
                            int diffResult = getMultiResult(s1, s2, i+1);
                            if (diffResult>resultValue){
                                continue fd;
                            }
                        }else {
                            int diffResult = getMultiResult(s1, s2, i+1);
                            if (diffResult>resultValue){
                                continue fd;
                            }
                        }

                    }
                }else {
                    String s3 =  s2.substring(s2.length() - 2, s2.length() - 1);
                    for (int i = 0; i < s1.length(); i++) {
                        if (i == 0) {
                            int diffResult = getMultiResult(s1, s3, i+1);
                            if (diffResult>resultValue){
                                continue fd;
                            }
                        } else if (i == 1){
                            int diffResult = getMultiResult(s1, s3, i+1);
                            if (diffResult>resultValue){
                                continue fd;
                            }
                        }else {
                            int diffResult = getMultiResult(s1, s3, i+1);
                            if (diffResult>resultValue){
                                continue fd;
                            }
                        }

                    }
                }
                firstList.add(s1);
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }else {
                int multiAllResult = getMultiAllResult(s1, s2);
                if (resultValue!=0&&multiAllResult>resultValue){
                    continue ;
                }
                //乘法个位进位
                if (carryNum==1){
                    int result = getMultiResult(s1, s2, 1);
                    if (result<10){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }else if(carryNum==2){
                    if (resultValue!=0&&multiAllResult>resultValue){
                        continue ;
                    }
                    //乘法十位进位
                    int result = getMultiResult(s1, s2, 1);
                    //小于0是退位
                    if (result>=10){
                        continue;
                    }
                    int result1 = getMultiResult(s1, s2, 2);
                    if (result1<10){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }else {
                    //连续进位
                    int result = getMultiResult(s1, s2, 1);
                    if (result<=10){
                        continue;
                    }
                    int result1 = getMultiResult(s1, s2, 2);
                    if (result1<=0){
                        continue;
                    }
                    firstList.add(s1);
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        return map;
    }


    public  static  Map<String, List<String>>  generateMultiValue1(int numFirst,
                                                                      int firstMin,
                                                                      int firstCharlen,
                                                                      int numSecond,
                                                                      int secondCharlen,
                                                                      int  secondMin,
                                                                      int resultValue,
                                                                      int carryRule,
                                                                      int carryNum,
                                                                      int frequency){
//        int  numFirst = 1000;
//        int  minValue = 100;
//        int  firstCharlen =3 ;
//        int  numSecond = 100;
//        int  secondCharlen =2 ;
//        int  secondMin = 1;
//        int resultValue = 10000;
//        int  carryRule =1;      //1为不进位 2为进位
//        int  carryNum = 2;
//        int  frequency= 20;
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        int cycles = 0;
        fd:while(cycles < 20000){
            cycles++;
            //判断当前的位数是否符合标准
            int firstValue  =  (int)(Math.random() * numFirst);
            if (firstValue<firstMin){
                continue;
            }
            //取模尾数不能为0
            if (firstValue%10==0){
                continue;
            }
            String  s1 = String.valueOf(firstValue);
            int len1 = s1.length();
            if (firstCharlen != 0 && len1!=firstCharlen){
                continue;
            }

            //优先判断当前位数是否为null或者0 如果为0时则不数位限制
            int secondValue = (int)(Math.random() * numSecond);
            if (secondValue<=secondMin){
                continue ;
            }
            if (secondCharlen==2&&secondValue%10 !=0){
                continue fd;
            }
            String  s2 = String.valueOf(secondValue);
            if (secondCharlen!=0){
                //判断当前的位数是否符合标准
                int len2 = s2.length();
                if (len2!=secondCharlen){
                    continue;
                }
            }
            if (firstValue*secondValue>resultValue){
                continue ;
            }

            //表内乘法
            if(carryRule==0){
                firstList.add(s1);
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }else if (carryRule==1){
                for (int i = 0; i < s1.length(); i++) {
                    String first = s1.substring(i, i + 1);
                    for (int j=0;j<s2.length();j++) {
                        String second = s2.substring(j, j + 1);
                        int  result = Integer.parseInt(first) * Integer.parseInt(second);
                        if (result>=10){
                            continue fd;
                        }
                    }
                }
                firstList.add(s1);
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }else {
                int multiAllResult = getMultiAllResult(s1, s2);
                if (multiAllResult>resultValue){
                    continue ;
                }
                int k = 0;
                for (int i = 0; i < s1.length(); i++) {
                    int pow = new Double(Math.pow(10, i)).intValue();
                    int i1 = Integer.valueOf(s1) / pow % 10;
                    int j1 = Integer.valueOf(s2) / pow % 10;
                    int  result = i1 * j1;
                    if (result>=10){
                        k++;
                        break;
                    }
                }
                if (k == 0) {
                    continue;
                }
                firstList.add(s1);
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }
        }
//        System.out.println(firstList);
//        System.out.println(secondList);
        map.put("first",firstList);
        map.put("second",secondList);
        return map;
    }

    private static int   getMultiAllResult(String firstValue, String secondValue){
        int first  =Integer.parseInt(firstValue);
        int second  =Integer.parseInt(secondValue);
        int  result  = first * second;
        return result;
    }

    private static int   getMultiResult(String firstValue, String secondValue, int type){
        int length = firstValue.length();
        int multi  =Integer.parseInt(secondValue);
        int result = 0;
        if (type==1){
            String s1Sub = firstValue.substring(length - 1, length);
            int v1 = Integer.parseInt(s1Sub);
            result = v1 * multi;
        }else if(type==2){
            String s2Sub = firstValue.substring(length - 2, length - 1);
            int v2 = Integer.parseInt(s2Sub);
            result = v2 * multi;
        }else {
            String s3Sub = firstValue.substring(length - 3, length - 2);
            int v3 = Integer.parseInt(s3Sub);
            result = v3 * multi;
        }
        return  result;
    }

    public  static   Map<String, List<String>>  generateDivisionValue(int numFirst,
                                                 int minValue,
                                                 int firstCharlen,
                                                 int numSecond,
                                                 int secondCharlen,
                                                 int resultValue,
                                                 int carryRule,
                                                 int carryNum,
                                                 int frequency){
//        int  numFirst = 10;
//        int  minValue = 0;
//        int  firstCharlen =1 ;
//        int  numSecond = 100;
//        int  secondCharlen =2 ;
//        int resultValue = 100;
//        int  carryRule =2;
//        int  carryNum = 3;
//        int  frequency= 20;
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        fd:while (true){
            //判断当前的位数是否符合标准
            int firstValue  =  (int)(Math.random() * numFirst);

            if (firstValue<minValue){
                continue;
            }
            //取模尾数不能为0
            if (firstValue%10==0){
                continue;
            }
            String  s1 = String.valueOf(firstValue);
            int len1 = s1.length();
            if (len1!=firstCharlen){
                continue;
            }

            //优先判断当前位数是否为null或者0 如果为0时则不数位限制
            int secondValue = (int)(Math.random() * numSecond);
//            if (secondCharlen==2&&secondValue%10 !=0){
//                continue;
//            }
            if (secondValue%10==0){
                continue;
            }
            String  s2 = String.valueOf(secondValue);
            if (secondCharlen!=0){
                //判断当前的位数是否符合标准
                int len2 = s2.length();
                if (len2!=secondCharlen){
                    continue;
                }
            }
            if(carryRule==0){
                //表内除法
                int multiAllResult = getMultiAllResult(s1, s2);
                firstList.add(String.valueOf(multiAllResult));
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }else  if (carryRule==1){
                //不退位除法
                if (secondCharlen!=2){
                    for (int i = 0; i < s1.length(); i++) {
                        if (i == 0) {
                            int diffResult = getMultiResult(s1, s2, i+1);
                            if (diffResult>resultValue){
                                continue fd;
                            }
                        } else if (i == 1){
                            int diffResult = getMultiResult(s1, s2, i+1);
                            if (diffResult>resultValue){
                                continue fd;
                            }
                        }else {
                            int diffResult = getMultiResult(s1, s2, i+1);
                            if (diffResult>resultValue){
                                continue fd;
                            }
                        }

                    }
                }else {
                    if (secondCharlen>=2){
                        for (int j = 0; j < secondCharlen; j++) {
                            String s3 =  s2.substring(j, j+1);
                            for (int i = 0; i < s1.length(); i++) {
                                if (i == 0) {
                                    int diffResult = getMultiResult(s1, s3, i+1);
                                    if (diffResult>=10){
                                        continue fd;
                                    }
                                } else if (i == 1){
                                    int diffResult = getMultiResult(s1, s3, i+1);
                                    if (diffResult>=10){
                                        continue fd;
                                    }
                                }else {
                                    int diffResult = getMultiResult(s1, s3, i+1);
                                    if (diffResult>=10){
                                        continue fd;
                                    }
                                }

                            }
                        }
                    }else {
                        for (int i = 0; i < s1.length(); i++) {
                            if (i == 0) {
                                int diffResult = getMultiResult(s1, s2, i+1);
                                if (diffResult>=10){
                                    continue fd;
                                }
                            } else if (i == 1){
                                int diffResult = getMultiResult(s1, s2, i+1);
                                if (diffResult>=10){
                                    continue fd;
                                }
                            }else {
                                int diffResult = getMultiResult(s1, s2, i + 1);
                                if (diffResult >=10) {
                                    continue fd;
                                }
                            }   }
                    }

                }
                int multiAllResult = getMultiAllResult(s1, s2);
                if (multiAllResult>=100){
                    continue ;
                }
                firstList.add(String.valueOf(multiAllResult));
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }else {
                int multiAllResult = getMultiAllResult(s1, s2);
                if (resultValue!=0&&multiAllResult>resultValue){
                    continue ;
                }
                //乘法个位进位
                if (carryNum==1){
                    int result = getMultiResult(s1, s2, 1);
                    if (result<10){
                        continue;
                    }
                    firstList.add(String.valueOf(multiAllResult));
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }else {
                    //连续退位
                    int result = getMultiResult(s2, s1, 1);
                    if (result<=10){
                        continue;
                    }
                    int result1 = getMultiResult(s2, s1, 2);
                    if (result1<=0){
                        continue;
                    }
                    firstList.add(String.valueOf(multiAllResult));
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }

            }

        }
        map.put("first",firstList);
        map.put("second",secondList);
        return map;
    }

    public  static    Map<String, List<String>>   generateDivisionValue1(int numFirst,
                                                                          int firstMin,
                                                                          int firstCharlen,
                                                                          int numSecond,
                                                                          int secondCharlen,
                                                                          int secondMin,
                                                                          int resultValue,
                                                                          int carryRule,
                                                                          int carryNum,
                                                                          int frequency){
                        //        int  numFirst = 100;
                        //        int  minValue = 10;
                        //        int  firstCharlen =2 ;
                        //        int  numSecond = 10;
                        //        int  secondCharlen =1 ;
                        //        int resultValue = 100;
                        //        int  carryRule =1; //0表示表内除法  1表示不退位除法  2表示退位除法
                        //        int  carryNum = 1; // 1表示两位数/一位数   2表示两位数/两位数 3表示整千整百/整十
                        //        int  frequency= 20;
                        //        int  secondMin = 1;
                                Map<String,List<String>> map = new HashMap<>();
                                List<String> firstList = new ArrayList<>();
                                List<String> secondList = new ArrayList<>();
                                if(carryRule==0){
                                    while (true){
                                        int firstValue  =  (int)(Math.random() * numFirst);
                                        if (firstValue<firstMin){
                                            continue;
                                        }
                                        //取模尾数不能为0
                                        if (firstValue%10==0){
                                            continue;
                                        }
                                        String  s1 = String.valueOf(firstValue);
                                        if (firstCharlen!=0){
                                            int len1 = s1.length();
                                            if (len1!=firstCharlen){
                                                continue;
                                            }
                                        }
                                        //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                                        int secondValue = (int)(Math.random() * numSecond);
                                        if (secondValue%10==0){ continue; }
                                        String  s2 = String.valueOf(secondValue);
                                        if (secondCharlen!=0){
                                            //判断当前的位数是否符合标准
                                            int len2 = s2.length();
                                            if (len2!=secondCharlen){
                                                continue;
                                            }
                                        }
                                        int multiAllResult = getMultiAllResult(s1, s2);
                                        firstList.add(String.valueOf(multiAllResult));
                                        secondList.add(s2);
                                        if (firstList.size()<frequency){
                                            continue;
                                        }else {
                                            break;
                                        }
                                    }
                                }else if (carryRule ==1){
                                    fd:while (true){
                                        int firstValue  =  (int)(Math.random() * numFirst);
                                        if (firstValue<firstMin){ continue; }
                                        //取模尾数不能为0
                                        if (firstValue%10==0){ continue; }
                                        String  s1 = String.valueOf(firstValue);
                                        if (firstCharlen!=0){
                                            int len1 = s1.length();
                                            if (len1!=firstCharlen){ continue; }
                                        }
                                        //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                                        int secondValue = (int)(Math.random() * numSecond);
                                        if (secondMin!=0){
                                            if (secondValue<=secondMin){continue;}
                                        }
                                        if (secondValue%10==0){ continue; }
                                        String  s2 = String.valueOf(secondValue);
                                        if (secondCharlen!=0){
                                            //判断当前的位数是否符合标准
                                            int len2 = s2.length();
                                            if (len2!=secondCharlen){
                                                continue;
                                            }
                                        }

                                        if (carryNum==1){//两位数÷一位数
                                            for (int i = 0; i < s1.length(); i++) {
                                                int diffResult = getMultiResult(s1, s2, i+1);
                                                if (diffResult>10){
                                                    continue fd;
                                                }
                                            }
                                            int multiAllResult = getMultiAllResult(s1, s2);
                                            if (multiAllResult>=resultValue){
                                                continue ;
                                            }
                                            firstList.add(String.valueOf(multiAllResult));
                                            secondList.add(s2);
                                            if (firstList.size()<frequency){
                                                continue;
                                            }else {
                                                break;
                                            }
                                        }else {//两位数÷两位数
                                            int result = getMultiResult(s1, s2, 1);
                                            if (result>10){
                                                continue;
                                            }
                                            int multiAllResult = getMultiAllResult(s1, s2);
                                            if (multiAllResult>=resultValue){
                                                continue ;
                                            }
                                            if (firstValue==multiAllResult){
                                                continue fd;
                                            }
                                            firstList.add(String.valueOf(multiAllResult));
                                            secondList.add(s1);
                                            if (firstList.size()<frequency){
                                                continue;
                                            }else {
                                                break;
                                            }
                                        }
                                    }
                                }else{
                                    if (carryNum==1){
                                        fd:while (true){
                                            int firstValue  =  (int)(Math.random() * numFirst);
                                            if (firstValue<firstMin){ continue; }
                                            //取模尾数不能为0
                                            if (firstValue%10==0){ continue; }
                                            String  s1 = String.valueOf(firstValue);
                                            if (firstCharlen!=0){
                                                int len1 = s1.length();
                                                if (len1!=firstCharlen){ continue; }
                                            }
                                            //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                                            int secondValue = (int)(Math.random() * numSecond);
                                            if (secondValue%10==0){ continue; }
                                            String  s2 = String.valueOf(secondValue);
                                            if (secondCharlen!=0){
                                                //判断当前的位数是否符合标准
                                                int len2 = s2.length();
                                                if (len2!=secondCharlen){
                                                    continue;
                                                }
                                            }

                                            int result = getMultiResult(s1, s2, 1);
                                            if (result<10){
                                                continue;
                                            }
                                            int multiAllResult = firstValue*secondValue;
                                            if (multiAllResult>resultValue){
                                                continue ;
                                            }
                                            //退位除法 两位/一位
                                            firstList.add(String.valueOf(multiAllResult));
                                            secondList.add(s2);
                                            if (firstList.size()<frequency){
                                                continue;
                                            }else {
                                                break;
                                            }
                                        }

                                    }else if (carryNum ==2){
                                        fd:while (true){
                                            int firstValue  =  (int)(Math.random() * numFirst);
                                            if (firstValue<firstMin){ continue; }
                                            //取模尾数不能为0
                                            if (firstValue%10==0){ continue; }
                                            String  s1 = String.valueOf(firstValue);
                                            if (firstCharlen!=0){
                                                int len1 = s1.length();
                                                if (len1!=firstCharlen){ continue; }
                                            }
                                            //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                                            int secondValue = (int)(Math.random() * numSecond);
                                            if (secondValue%10==0){ continue; }
                                            String  s2 = String.valueOf(secondValue);
                                            if (secondCharlen!=0){
                                                //判断当前的位数是否符合标准
                                                int len2 = s2.length();
                                                if (len2!=secondCharlen){
                                                    continue;
                                                }
                                            }

                                            int result = getMultiResult(s1, s2, 1);
                                            if (result<10){
                                                continue;
                                            }
                                            int multiAllResult = firstValue*secondValue;
                                            if (multiAllResult>resultValue){
                                                continue ;
                                            }
                                            //退位除法 两位/一位
                                            firstList.add(String.valueOf(multiAllResult));
                                            secondList.add(s1);
                                            if (firstList.size()<frequency){
                                                continue;
                                            }else {
                                                break;
                                            }
                                        }

                                    }else if (carryNum==3){
                                        int cycles = 0;
                                        while (cycles < 20000) {
                                            cycles++;
                                            int firstValue = (int) (Math.random()*numFirst);
                                            if (firstValue % 10 != 0 || firstValue == 0) {
                                                continue;
                                            }
                                            int secondValue = (int) (Math.random()*numSecond);
                                            if (secondValue == 0) continue;
                                            String lastChar = String.valueOf(secondValue);
                                            char ch = lastChar.charAt(lastChar.length() - 1);
                                            if (ch != '0' && ch != '2' && ch != '5') {
                                                continue;
                                            }
                                            if (firstValue % secondValue != 0) {
                                                continue;
                                            }
                                            if (firstValue / secondValue % 10 == 0) {
                                                continue;
                                            }
                                            firstList.add(String.valueOf(firstValue));
                                            secondList.add(String.valueOf(secondValue));
                                            if (firstList.size()<frequency){
                                                continue;
                                            }else {
                                                break;
                                            }
                                        }
                                    }else {
                                        //整千整百数除整十数
                                        fd:while (true){
                                            int firstValue  =  (int)(Math.random() * numFirst);
                                            if (firstValue<firstMin){ continue; }
                                            //取模尾数不能为0
                                            if (firstValue%10!=0){ continue; }
                                            String  s1 = String.valueOf(firstValue);
                                            if (firstCharlen!=0){
                                                int len1 = s1.length();
                                                if (len1!=firstCharlen){ continue; }
                                            }
                                            //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                                            int secondValue = (int)(Math.random() * numSecond);
                                            if (secondValue%10!=0){ continue; }
                                            String  s2 = String.valueOf(secondValue);
                                            if (secondCharlen!=0){
                                                //判断当前的位数是否符合标准
                                                int len2 = s2.length();
                                                if (len2!=secondCharlen){
                                                    continue;
                                                }
                                            }
                                            String f = String.valueOf(firstValue / 10);
                                            String s = String.valueOf(secondValue / 10);
                                            //                System.out.println("第一"+f);
                                            //                System.out.println("第二"+s);
                                            int result = getMultiResult(f, s, 1);
                                            if (result<10){
                                                continue;
                                            }
                                            int multiAllResult = firstValue*secondValue;
                                            if (multiAllResult>=resultValue){
                                                continue ;
                                            }
                                            firstList.add(String.valueOf(multiAllResult));
                                            secondList.add(s2);
                                            if (firstList.size()<frequency){
                                                continue;
                                            }else {
                                                break;
                                            }
                                        }
                                        }
                                    }
//                                }
//                                System.out.println(firstList);
//                                System.out.println(secondList);
                                map.put("first",firstList);
                                map.put("second",secondList);
                                return map;
                            }


    public  static    Map<String, List<String>>   generateDivisionValue2(int numFirst,
                                                                         int firstMin,
                                                                         int firstCharlen,
                                                                         int numSecond,
                                                                         int secondCharlen,
                                                                         int secondMin,
                                                                         int resultValue,
                                                                         int carryRule,
                                                                         int carryNum,
                                                                         int frequency){
        //        int  numFirst = 100;
        //        int  minValue = 10;
        //        int  firstCharlen =2 ;
        //        int  numSecond = 10;
        //        int  secondCharlen =1 ;
        //        int resultValue = 100;
        //        int  carryRule =1; //0表示表内除法  1表示不退位除法  2表示退位除法
        //        int  carryNum = 1; // 1表示两位数/一位数   2表示两位数/两位数 3表示整千整百/整十
        //        int  frequency= 20;
        //        int  secondMin = 1;
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        if(carryRule==0){
            while (true){
                int firstValue  =  (int)(Math.random() * numFirst);
                if (firstValue<firstMin){
                    continue;
                }
                //取模尾数不能为0
                if (firstValue%10==0){
                    continue;
                }
                String  s1 = String.valueOf(firstValue);
                if (firstCharlen!=0){
                    int len1 = s1.length();
                    if (len1!=firstCharlen){
                        continue;
                    }
                }
                //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                int secondValue = (int)(Math.random() * numSecond);
                if (secondValue%10==0){ continue; }
                String  s2 = String.valueOf(secondValue);
                if (secondCharlen!=0){
                    //判断当前的位数是否符合标准
                    int len2 = s2.length();
                    if (len2!=secondCharlen){
                        continue;
                    }
                }
                int multiAllResult = getMultiAllResult(s1, s2);
                firstList.add(String.valueOf(multiAllResult));
                secondList.add(s2);
                if (firstList.size()<frequency){
                    continue;
                }else {
                    break;
                }
            }
        }else if (carryRule ==1){
            fd:while (true){
                int firstValue  =  (int)(Math.random() * numFirst);
                if (firstValue<firstMin){ continue; }
                //取模尾数不能为0
                if (firstValue%10==0){ continue; }
                String  s1 = String.valueOf(firstValue);
                if (firstCharlen!=0){
                    int len1 = s1.length();
                    if (len1!=firstCharlen){ continue; }
                }
                //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                int secondValue = (int)(Math.random() * numSecond);
                if (secondMin!=0){
                    if (secondValue<=secondMin){continue;}
                }
                if (secondValue%10==0){ continue; }
                String  s2 = String.valueOf(secondValue);
                if (secondCharlen!=0){
                    //判断当前的位数是否符合标准
                    int len2 = s2.length();
                    if (len2!=secondCharlen){
                        continue;
                    }
                }

                if (carryNum==1){//两位数÷一位数
                    for (int i = 0; i < s1.length(); i++) {
                        int diffResult = getMultiResult(s1, s2, i+1);
                        if (diffResult>10){
                            continue fd;
                        }
                    }
                    int multiAllResult = getMultiAllResult(s1, s2);
                    if (multiAllResult>=resultValue){
                        continue ;
                    }
                    if (multiAllResult%10==0){
                        continue fd;
                    }
                    if (multiAllResult<=10){
                        continue fd;
                    }
                    firstList.add(String.valueOf(multiAllResult));
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }else {//两位数÷两位数
                    int result = getMultiResult(s1, s2, 1);
                    if (result>10){
                        continue;
                    }
                    int multiAllResult = getMultiAllResult(s1, s2);
                    if (multiAllResult>=resultValue){
                        continue ;
                    }
                    if (firstValue==multiAllResult){
                        continue fd;
                    }
                    firstList.add(String.valueOf(multiAllResult));
                    secondList.add(s1);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }
            }
        }else{
            if (carryNum==1){
                fd:while (true){
                    int firstValue  =  (int)(Math.random() * numFirst);
                    if (firstValue<firstMin){ continue; }
                    //取模尾数不能为0
                    if (firstValue%10==0){ continue; }
                    String  s1 = String.valueOf(firstValue);
                    if (firstCharlen!=0){
                        int len1 = s1.length();
                        if (len1!=firstCharlen){ continue; }
                    }
                    //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                    int secondValue = (int)(Math.random() * numSecond);
                    if (secondValue%10==0){ continue; }
                    String  s2 = String.valueOf(secondValue);
                    if (secondCharlen!=0){
                        //判断当前的位数是否符合标准
                        int len2 = s2.length();
                        if (len2!=secondCharlen){
                            continue;
                        }
                    }

                    int result = getMultiResult(s1, s2, 1);
                    if (result<10){
                        continue;
                    }
                    int multiAllResult = firstValue*secondValue;
                    if (multiAllResult>resultValue){
                        continue ;
                    }
                    if (multiAllResult%10==0){
                        continue ;
                    }
                    //退位除法 两位/一位
                    firstList.add(String.valueOf(multiAllResult));
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }

            }else if (carryNum ==2){
                fd:while (true){
                    int firstValue  =  (int)(Math.random() * numFirst);
                    if (firstValue<firstMin){ continue; }
                    //取模尾数不能为0
                    if (firstValue%10==0){ continue; }
                    String  s1 = String.valueOf(firstValue);
                    if (firstCharlen!=0){
                        int len1 = s1.length();
                        if (len1!=firstCharlen){ continue; }
                    }
                    //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                    int secondValue = (int)(Math.random() * numSecond);
                    if (secondValue%10==0){ continue; }
                    String  s2 = String.valueOf(secondValue);
                    if (secondCharlen!=0){
                        //判断当前的位数是否符合标准
                        int len2 = s2.length();
                        if (len2!=secondCharlen){
                            continue;
                        }
                    }

                    int result = getMultiResult(s1, s2, 1);
                    if (result<10){
                        continue;
                    }
                    int multiAllResult = firstValue*secondValue;
                    if (multiAllResult>resultValue){
                        continue ;
                    }
                    if (multiAllResult%10==0){
                        continue ;
                    }
                    firstList.add(String.valueOf(multiAllResult));
                    secondList.add(s1);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }

            }else if (carryNum==3){
                int cycles = 0;
                while (cycles < 20000) {
                    cycles++;
                    int firstValue = (int) (Math.random()*numFirst);
                    if (firstValue % 10 != 0 || firstValue == 0) {
                        continue;
                    }
                    int secondValue = (int) (Math.random()*numSecond);
                    if (secondValue == 0) continue;
                    String lastChar = String.valueOf(secondValue);
                    char ch = lastChar.charAt(lastChar.length() - 1);
                    if (ch != '0' && ch != '2' && ch != '5') {
                        continue;
                    }
                    if (firstValue % secondValue != 0) {
                        continue;
                    }
                    if (firstValue / secondValue % 10 == 0) {
                        continue;
                    }
                    firstList.add(String.valueOf(firstValue));
                    secondList.add(String.valueOf(secondValue));
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }
            }else {
                //整千整百数除整十数
                fd:while (true){
                    int firstValue  =  (int)(Math.random() * numFirst);
                    if (firstValue<firstMin){ continue; }
                    //取模尾数不能为0
                    if (firstValue%10!=0){ continue; }
                    String  s1 = String.valueOf(firstValue);
                    if (firstCharlen!=0){
                        int len1 = s1.length();
                        if (len1!=firstCharlen){ continue; }
                    }
                    //优先判断当前位数是否为null或者0 如果为0时则不数位限制
                    int secondValue = (int)(Math.random() * numSecond);
                    if (secondValue%10!=0){ continue; }
                    String  s2 = String.valueOf(secondValue);
                    if (secondCharlen!=0){
                        //判断当前的位数是否符合标准
                        int len2 = s2.length();
                        if (len2!=secondCharlen){
                            continue;
                        }
                    }
                    String f = String.valueOf(firstValue / 10);
                    String s = String.valueOf(secondValue / 10);
                    //                System.out.println("第一"+f);
                    //                System.out.println("第二"+s);
                    int result = getMultiResult(f, s, 1);
                    if (result<10){
                        continue;
                    }
                    int multiAllResult = firstValue*secondValue;
                    if (multiAllResult>resultValue){
                        continue ;
                    }
                    firstList.add(String.valueOf(multiAllResult));
                    secondList.add(s2);
                    if (firstList.size()<frequency){
                        continue;
                    }else {
                        break;
                    }
                }
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        return map;
    }

    /**
     * 加法
     * @param molecule
     * @param denominator
     * @param carryRule
     * @param carryNum
     * @param frequency
     * @return
     */
    public   static   Map<String, List<String>>  fractionAdd(int molecule,int denominator,int carryRule, int carryNum,int frequency){
//        int molecule  = 4; //分子 在上
//        int denominator = 50; // 分母 在下
//        int carryRule = 2;//同分母  大于1为异分母
//        int carryNum = 2; //分母互质  否则不是互质 为分母倍数
//        int frequency=10;
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        int cycles = 0;
        while(cycles < 20000){
            cycles++;
            if (carryRule==1){  //同分母
                int  firstMolecule  =   (int)(Math.random() *  molecule);
                int  secondMolecule =   (int)(Math.random() *  molecule);
                if(secondMolecule%10==0&&secondMolecule==0){
                    continue;
                }
                if(firstMolecule%10==0&&firstMolecule==0){
                    continue;
                }
                int  denominatorValue  =   (int)(Math.random() *  denominator);
                //同分母
                if (denominatorValue%10==0){
                    continue;
                }
                if(denominatorValue < 2){
                    continue;
                }
                int simplify = CalculationUtils.isSimplify(firstMolecule, secondMolecule);
                if (simplify == 0) {
                    continue;
                }
                int result = firstMolecule+secondMolecule;
                if (result>molecule){
                    continue;
                }
                if (result%denominatorValue!=0){
                    continue;
                }
                firstList.add(firstMolecule+"/"+denominatorValue);
                secondList.add(secondMolecule+"/"+denominatorValue);
            }else {    //异分母
                //分母互质
                if(carryNum==1){
                    int  firstDenominator  =   (int)(Math.random() *  denominator);
                    int  secondDenominator =   (int)(Math.random() *  denominator);
                    if (firstDenominator*secondDenominator>100){
                        continue;
                    }
                    if (firstDenominator<3 || secondDenominator<3){
                        continue;
                    }
                    int simplify = CalculationUtils.isSimplify(firstDenominator, secondDenominator);
                    if (simplify == 0) {
                        continue;
                    }
                    int moleculeValue = (int)(Math.random() * molecule)+1;
                    if (moleculeValue == 0){
                        continue;
                    }
                    firstList.add(moleculeValue+"/"+firstDenominator);
                    secondList.add(moleculeValue+"/"+secondDenominator);
                }else { //分母倍数
                    int  firstDenominator  =   (int)(Math.random() *  denominator);
                    int  secondDenominator =   (int)(Math.random() *  denominator);
                    if (firstDenominator < 2 || secondDenominator < 2){
                        continue;
                    }
                    int max = Math.max(firstDenominator, secondDenominator);
                    int min = Math.min(firstDenominator, secondDenominator);
                    if (max % min != 0){
                        continue;
                    }
                    if (max / min < 2 || max / min > 5){
                        continue;
                    }
                    //分子
                    int firstMolecule = 0;
                    int secondMolecule = 0;
                    if (firstDenominator >= 20 || secondDenominator >= 20) {
                        firstMolecule = 1;
                        secondMolecule = 1;
                    }else {
                        firstMolecule = (int)(Math.random() * molecule)+1;
                        secondMolecule = (int)(Math.random() * molecule)+1;
                    }
                    int simplify = CalculationUtils.isSimplify(firstMolecule, firstDenominator);
                    int simplify1 = CalculationUtils.isSimplify(secondMolecule, secondDenominator);
                    if (simplify == 0 || simplify1 == 0) {
                        continue;
                    }
                    firstList.add(firstMolecule+"/"+firstDenominator);
                    secondList.add(secondMolecule+"/"+secondDenominator);
                }
            }
            if (firstList.size()<frequency){
                continue;
            }else {
                break;
            }

        }
        map.put("first",firstList);
        map.put("second",secondList);
        return  map;


    }


    /**
     * 减法
     * @return
     */
    public  static    Map<String, List<String>>  fractionSubtract(int molecule,int denominator,int carryRule, int carryNum,int frequency){
//        int molecule  = 4; //分子 在上
//        int denominator = 50; // 分母 在下
//        int carryRule = 2;//同分母  大于1为异分母
//        int carryNum = 2; //分母互质  否则不是互质 为分母倍数
//        int frequency=10;

        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        int cycles = 0;
        while(cycles < 20000){
            cycles++;
            if (carryRule==1) {  //同分母
                int  firstMolecule  =   (int)(Math.random() *  molecule);
                int  secondMolecule =   (int)(Math.random() *  molecule);
                if(secondMolecule==0 || firstMolecule==0){
                    continue;
                }
                int denominatorValue =  (int)(Math.random() *  denominator);
                //同分母
                if (denominatorValue > 9 || denominatorValue < 2){
                    continue;
                }
                int result = firstMolecule-secondMolecule;
                if (result <= 0){
                    continue;
                }
                if (result%denominatorValue==0){
                    continue;
                }
                firstList.add(firstMolecule+"/"+denominatorValue);
                secondList.add(secondMolecule+"/"+denominatorValue);
            }else {

                if(carryNum==1){//分母互质
                    int  firstDenominator  =   (int)(Math.random() *  denominator);
                    int  secondDenominator =   (int)(Math.random() *  denominator);
                    if (firstDenominator*secondDenominator>100){
                        continue;
                    }
                    if (firstDenominator<3 || secondDenominator<3){
                        continue;
                    }
                    int simplify = CalculationUtils.isSimplify(firstDenominator, secondDenominator);
                    if (simplify == 0) {
                        continue;
                    }
                    if (secondDenominator<firstDenominator){
                        continue;
                    }
                    int  moleculeValue = (int)(Math.random() * molecule)+1;
                    if (moleculeValue == 0){
                        continue;
                    }
                    firstList.add(moleculeValue+"/"+firstDenominator);
                    secondList.add(moleculeValue+"/"+secondDenominator);
                }else { //分母倍数
                    int  firstDenominator  =   (int)(Math.random() *  denominator);
                    int  secondDenominator =   (int)(Math.random() *  denominator);
                    if (firstDenominator < 2 || secondDenominator < 2){
                        continue;
                    }
                    if (secondDenominator % firstDenominator != 0){
                        continue;
                    }
                    if (secondDenominator / firstDenominator < 2 || secondDenominator / firstDenominator > 5){
                        continue;
                    }
                    //分子
                    int firstMolecule = 0;
                    int secondMolecule = 0;
                    if (firstDenominator >= 20 || secondDenominator >= 20) {
                        firstMolecule = 1;
                        secondMolecule = 1;
                    }else {
                        firstMolecule = (int)(Math.random() * molecule)+1;
                        secondMolecule = (int)(Math.random() * molecule)+1;
                    }
                    if (secondDenominator / firstDenominator * firstMolecule <= secondMolecule) {
                        continue;
                    }
                    int simplify = CalculationUtils.isSimplify(firstMolecule, firstDenominator);
                    int simplify1 = CalculationUtils.isSimplify(secondMolecule, secondDenominator);
                    if (simplify == 0 || simplify1 == 0) {
                        continue;
                    }
                    firstList.add(firstMolecule+"/"+firstDenominator);
                    secondList.add(secondMolecule+"/"+secondDenominator);
                }

            }
            if (firstList.size()<frequency){
                continue;
            }else {
                break;
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        return  map;
    }

    /**
     * 分数乘法
     */
    public static Map<String, List<String>> fractionMultiplication(ExercisesRules rule, int count) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        int cycles = 0;
        if (rule.getCarryRulers() == 1) {// 1-不可约分、2-可约分
            if (rule.getFirstNumType() == 3 && rule.getSecondNumType() == 3) {// 1-整数、2-小数、3-分数
                Fraction fraction = new Fraction(rule.getFirstMin(),rule.getNumFirst(),0);
                Fraction fraction2 = new Fraction(rule.getSecondMin(),rule.getNumSecond(),0);
                while (cycles < 20000) {
                    cycles++;
                    fraction.generateNumber();
                    int numerator1 = fraction.getNumerator();
                    int denominator1 = fraction.getDenominator();

                    fraction2.generateNumber();
                    int numerator2 = fraction2.getNumerator();
                    int denominator2 = fraction2.getDenominator();

                    if (numerator1 * numerator2 > 100 || denominator1 * denominator2 > 100) {
                        continue;
                    }
                    int simplify = CalculationUtils.isSimplify(numerator1 * numerator2, denominator1 * denominator2);
                    if (simplify == 0) {
                        continue;
                    }
                    firstList.add(numerator1+"/"+denominator1);
                    secondList.add(numerator2+"/"+denominator2);
//                    System.out.println(numerator1+"/"+denominator1+" * "+numerator2+"/"+denominator2+" = "+numerator1 * numerator2+"/"+denominator1 * denominator2);
                    if (firstList.size() >= count) {
                        break;
                    }
                }
            } else if ((rule.getFirstNumType() == 3 && rule.getSecondNumType() == 1) ||
                    (rule.getFirstNumType() == 1 && rule.getSecondNumType() == 3)) {// 1-整数、2-小数、3-分数
                Fraction fraction;
                if (rule.getFirstNumType() == 3) {
                    fraction = new Fraction(rule.getFirstMin(),rule.getNumFirst(),0);
                } else {
                    fraction = new Fraction(rule.getSecondMin(),rule.getNumSecond(),0);
                }
                while (cycles < 20000) {
                    cycles++;
                    fraction.generateNumber();
                    int simplify = CalculationUtils.isSimplify(fraction.getNumerator(), fraction.getDenominator());
                    if (simplify==0) {
                        continue;
                    }
                    int ss = (int)(Math.random()*100)+1;
                    if (ss * fraction.getNumerator() > 100 || ss < 2) {
                        continue;
                    }
                    int simplify1 = CalculationUtils.isSimplify(ss * fraction.getNumerator(), fraction.getDenominator());
                    if (simplify1==0) {
                        continue;
                    }
                    if (rule.getFirstNumType() == 3) {
                        firstList.add(fraction.getValue());
                        secondList.add(String.valueOf(ss));
                    } else {
                        firstList.add(String.valueOf(ss));
                        secondList.add(fraction.getValue());
                    }
//                    System.out.println(fraction.getValue()+" * "+ss+" = "+fraction.getNumerator()*ss+"/"+fraction.getDenominator());
                    if (firstList.size() >= count) {
                        break;
                    }
                }
            }
        }else if (rule.getCarryRulers() == 2) {// 1-不可约分、2-可约分
            if ((rule.getFirstNumType() == 3 && rule.getSecondNumType() == 2) ||
                    (rule.getFirstNumType() == 2 && rule.getSecondNumType() == 3)) {// 1-整数、2-小数、3-分数
                Fraction fraction;
                if (rule.getFirstNumType() == 3) {
                    fraction = new Fraction(rule.getFirstMin(),rule.getNumFirst(),0);
                } else {
                    fraction = new Fraction(rule.getSecondMin(),rule.getNumSecond(),0);
                }
                while (cycles < 20000) {
                    cycles++;
                    fraction.generateNumber();
                    int simplify = CalculationUtils.isSimplify(fraction.getNumerator(), fraction.getDenominator());
                    if (simplify == 0) {
                        continue;
                    }
                    if (fraction.getDenominator() >= 10) {
                        continue;
                    }
                    int ss = (int)(Math.random()*100);
                    if (ss >= 100 || ss < 10) {
                        continue;
                    }
                    int simplify1 = CalculationUtils.isSimplify(fraction.getDenominator(), ss);
                    if (simplify1==1) {
                        continue;
                    }
                    if (rule.getFirstNumType() == 3) {
                        BigDecimal value = new BigDecimal(rule.getSecondMagnify());
                        BigDecimal multiply = value.multiply(new BigDecimal(String.valueOf(ss)));
                        firstList.add(fraction.getValue());
                        secondList.add(multiply.toString());
                    } else {
                        BigDecimal value = new BigDecimal(rule.getFirstMagnify());
                        BigDecimal multiply = value.multiply(new BigDecimal(String.valueOf(ss)));
                        firstList.add(multiply.toString());
                        secondList.add(fraction.getValue());
                    }
//                    System.out.println(fraction.getValue()+" * "+String.format("%.1f",ss*0.1)+" = "+ String.format("%.1f",fraction.getNumerator() * ss *0.1)+"/"+fraction.getDenominator());
                    if (firstList.size() > count) {
                        break;
                    }
                }
            } else if ((rule.getFirstNumType() == 3 && rule.getSecondNumType() == 1) ||
                    (rule.getFirstNumType() == 1 && rule.getSecondNumType() == 3)) {// 1-整数、2-小数、3-分数
                Fraction fraction;
                if (rule.getFirstNumType() == 3) {
                    fraction = new Fraction(rule.getFirstMin(),rule.getNumFirst(),0);
                } else {
                    fraction = new Fraction(rule.getSecondMin(),rule.getNumSecond(),0);
                }
                while (cycles < 20000) {
                    cycles++;
                    fraction.generateNumber();
                    int simplify = CalculationUtils.isSimplify(fraction.getNumerator(), fraction.getDenominator());
                    if (simplify==0) {
                        continue;
                    }
                    if (fraction.getDenominator() >= 100) {
                        continue;
                    }
                    int ss = (int)(Math.random()*10)+1;
                    if (ss >= 10) {
                        continue;
                    }
                    int simplify1 = CalculationUtils.isSimplify(fraction.getDenominator(), ss);
                    if (simplify1==1) {
                        continue;
                    }
                    if (rule.getFirstNumType() == 3) {
                        firstList.add(fraction.getValue());
                        secondList.add(String.valueOf(ss));
                    } else {
                        firstList.add(String.valueOf(ss));
                        secondList.add(fraction.getValue());
                    }
//                    System.out.println(fraction.getValue()+" * "+ss+" = "+fraction.getNumerator()*ss+"/"+fraction.getDenominator());
                    if (firstList.size() > 20) {
                        break;
                    }
                }
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        return map;
    }

    /**
     * 分数除法
     */
    public   static   Map<String, List<String>>   fractionDivision(int numFirst, int numSecond,int frequency ){
//        int numFirst  = 50; //分子 在上
//        int numSecond = 20; // 分母 在下
//        int frequency =10;
        Map<String,List<String>> map = new HashMap<>();
        List<String> firstList = new ArrayList<>();
        List<String> secondList = new ArrayList<>();
        int cycles = 0;
        while (cycles < 20000){
            cycles++;
            int  firstValue  =   (int)(Math.random() *  numFirst);
            int  secondValue =   (int)(Math.random() *  numSecond);
            if (firstValue==0){
                continue;
            }
            if (secondValue==0){
                continue;
            }
            if (firstValue%secondValue==0){
                continue;
            }
            int simplify = CalculationUtils.isSimplify(firstValue,secondValue);
            if (simplify == 1) {
                continue;
            }
            firstList.add(firstValue+"");
            secondList.add(secondValue+"");
            if (firstList.size()<frequency){
                continue;
            }else {
                break;
            }
        }
        map.put("first",firstList);
        map.put("second",secondList);
        return  map;
    }

}
