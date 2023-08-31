package com.fudian.common.utils.mathematics;

import com.alibaba.fastjson.JSONArray;
import com.fudian.common.pojo.CommonResult;

import java.util.*;

public class IntegerAddition {
    /**
     * 整数 加法 +-×÷
     *
     * @param a 个位进位，十位进位，连续进位 123
     * @param b 生成题数
     * @param c 进位 不进位
     * @param d 范围
     * @param e 几位数相运算  123
     * @return
     */
    private final static Random random = new Random();

    public static Set<String> integerAddition(int a, int b, int c, int d, String e) {
        //整数 加法
        //几位数相运算  123
        List<Integer> lists = JSONArray.parseArray(e, Integer.class);
        //范围长度
        String len = String.valueOf(d);
        //此范围内最大可以哪一位进位
        int jinWeiMax = -1;
        int fanWeiTou = Integer.valueOf(len.substring(0, 1));
        if (fanWeiTou > 1) {
            jinWeiMax = len.length() - 1;
        } else {
            jinWeiMax = len.length() - 2;
        }
        if (a > jinWeiMax) {
            a = jinWeiMax;
        }
        /*//超过范围 强制改变数的数位
        for (int i1 = 0; i1 < lists.size(); i1++) {
            if (lists.get(i1) > len.length()) {
                lists.set(i1, len.length());
            }
        }*/

        int xunHuanCiShu = 0;
        Set<String> set = new HashSet<>();
        if (c == 1) {
            //不进位
            //生成题
            while (set.size() <= b) {
                //循环超过10000此退出 避免死循环
                if (xunHuanCiShu > 10000) break;
                //生成随机数集合
                List<List<Integer>> ints = new ArrayList<>();

                //生成几位相运算的随机数
                for (int j = 0; j < lists.size(); j++) {
                    //随机数拆分集合
                    ArrayList<Integer> integers = new ArrayList<>();
                    //当前需要生成的随机数是几位数 ，是几位生成几个随机数
                    for (int k = 1; k <= lists.get(j); k++) {
                        if (len.length() < k) {
                            throw new RuntimeException();
                        }
                        int tongWeiHe = 0;
                        //查询有几个位数比本身多的
                        int changDuDuo = 0;
                        for (int l = j + 1; l < lists.size(); l++) {
                            if (lists.get(l) >= k) {
                                changDuDuo++;
                            }
                        }
                        //生成数字长度等于范围
                        if (len.length() == k) {
                            int integer = fanWeiTou;
                            changDuDuo = 10 - integer + changDuDuo;
                        }
                        //生成随机数集合是否为空，不为空获取相同位数相加-随机数生成范围
                        if (ints.size() != 0) {
                            for (List<Integer> anInt : ints) {
                                if (anInt.size() >= k) {
                                    Integer integer = anInt.get(k - 1);
                                    tongWeiHe += integer;
                                }
                            }
                        }
                        int nextInt = 0;
                        if (k == lists.get(j)) {
                            nextInt = random.nextInt(10 - changDuDuo - tongWeiHe - 1) + 1;
                        } else {
                            nextInt = random.nextInt(10 - changDuDuo - tongWeiHe);
                        }
                        integers.add(nextInt);
                    }
                    ints.add(integers);
                }
                int result = 0;
                StringBuffer strings = new StringBuffer();
                for (List<Integer> anInt : ints) {
                    String string = "";
                    for (Integer integer : anInt) {
                        string = integer + string;
                    }
                    Integer integer = Integer.parseInt(string);
                    if (strings.length() < 1) {
                        strings.append(integer);
                    } else {
                        strings.append("+").append(integer);
                    }
                    result += integer;
                }
                strings.append("=").append(result);
                set.add(strings.toString());
                xunHuanCiShu++;
            }
        } else if (c == 2) {
            //进位
            //生成题
            while (set.size() <= b) {
                if (xunHuanCiShu > 10000) break;

                //生成随机数集合
                List<List<Integer>> ints = new ArrayList<>();
                //设置可以生成几个 个十百位
                int jinWeiShu = fanWeiTou;
                //生成几个相运算的数
                for (int j = 0; j < lists.size(); j++) {
                    //获取当前位数是几位数
                    int thisSeveralDigits = lists.get(j);
                    //是零位数 则随机赋值几位数（个，十，百位。。）
                    if (thisSeveralDigits == 0) {

                        int i = random.nextInt(len.length() - 1) + 1;
                        //设置可以生成几个 个十百位
                        if (jinWeiShu - jinWeiMax > 0) {
                            i++;
                        }
                        thisSeveralDigits = i;
                    }
                    //随机数拆分集合
                    ArrayList<Integer> integers = new ArrayList<>();
                    //当前需要生成的随机数是几位数 ，是几位生成几个随机数
                    for (int k = 1; k <= thisSeveralDigits; k++) {
                        if (len.length() < k) {
                            throw new RuntimeException();
                        }
                        //查询有几个位数比本身多的 有几个从生成随机数范围中减去几
                        int changDuDuo = 0;
                        for (int l = j + 1; l < lists.size(); l++) {
                            if (lists.get(l) >= k || lists.get(l) == 0 && jinWeiMax >= k) {
                                changDuDuo++;
                            }
                        }
                        //判断是否和范围是同位数（个，十，百位）是同位数则 控制取值范围
                        if (len.length() == k) {
                            int integer = Integer.valueOf(len.substring(0, 1));
                            changDuDuo = 10 - integer + changDuDuo;
                        }
                        //生成随机数集合是否为空 不为空则 同位相加 （个位，十位，百位）
                        int tongWeiHe = 0;
                        for (List<Integer> anInt : ints) {
                            if (anInt.size() >= k) {
                                Integer integer = anInt.get(k - 1);
                                tongWeiHe += integer;
                            }
                        }
                        //判断是否进位 进位则 最后一组随机数 以前几组同位随机数相加必须 >= 10
                        int i = 1;
                        boolean isJinWei = (a == 0 || a == k) && changDuDuo == 0 && jinWeiMax >= k;
                        if (isJinWei && tongWeiHe != 0) {
                            tongWeiHe = 10 - tongWeiHe;
                            i += tongWeiHe;

                        }
                        //前一位进位且当前位数不进位 则当前位数生成随机数范围-1
                        boolean isJinWeiLast = a + 1 == k && a != 0 || a == 0 && jinWeiMax + 1 == k;
                        if (isJinWeiLast) {
                            changDuDuo = changDuDuo + 1;
                        }
                        int nextInt = 0;
                        //判断是否是一组数字的最高位 最高位不为0
                        boolean sui = changDuDuo < 9 && tongWeiHe < 9;
                        if (sui) {
                            nextInt = random.nextInt(10 - changDuDuo - tongWeiHe - 1) + i;

                        } else if (changDuDuo == 9) {
                            changDuDuo--;
                            jinWeiMax--;
                            nextInt = random.nextInt(10 - changDuDuo - tongWeiHe - 1) + i;
                        }
                        integers.add(nextInt);
                    }
                    ints.add(integers);
                }
                //结果
                int result = 0;
                //一道题的拼接串
                StringBuffer strings = new StringBuffer();
                for (List<Integer> anInt : ints) {
                    String string = "";
                    for (int integer : anInt) {
                        string = integer + string;
                    }
                    Integer integer = Integer.parseInt(string);
                    if (strings.length() < 1) {
                        strings.append(integer);
                    } else {
                        strings.append("+").append(integer);
                    }
                    result += integer;
                }
                strings.append("=").append(Integer.valueOf(result));
                set.add(strings.toString());
                xunHuanCiShu++;
            }
        }
        return set;
    }

    /**
     * 整数 减法 +-×÷
     *
     * @param a 个位退位，十位退位，连续退位 123
     * @param b 生成题数
     * @param c 退位 不退位
     * @param d 范围
     * @param e 几位数相运算  123
     * @return
     */
    public static Set<String> integerSubtraction(int a, int b, int c, int d, String e) {
        Set<String> set = IntegerAddition.integerAddition(a, b, c, d, e);
        HashSet<String> result = new HashSet<>();
        for (String ti : set) {
            //+号 替换成 -号
            ti = ti.replace("+", "-");
            //拆分 取 =号之后的结果
            String[] split = ti.split("=");
            //获取第一个-号之前的数值
            String substring = split[0].substring(0, split[0].indexOf("-"));
            //把 替换被除数为结果
            String s = split[0].replaceFirst(substring, split[1]);
            //拼接 减法的 商
            ti = s + "=" + substring;
            result.add(ti);
        }
        return result;
    }
}
