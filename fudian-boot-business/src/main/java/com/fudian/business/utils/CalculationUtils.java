package com.fudian.business.utils;

import com.fudian.common.utils.StringUtils;

public class CalculationUtils {

    /**
     * 获取最大公约数 a >= b
     * @return int
     */
    public static int getMaxCommonFactor(int a, int b) {
        int i = a % b;
        int mcf;
        if (i == 0) {
            mcf = b;
        } else {
            a = b;
            b = i;
            mcf = getMaxCommonFactor(a, b);
        }
        return mcf;
    }

    /**
     * 获取最大公约数 c >= (a,b)公约数
     * @return int
     */
    public static int getMaxCommonFactor(int a, int b, int c) {
        int d = getMaxCommonFactor(a, b);
        int i = c % d;
        int mcf;
        if (i == 0) {
            mcf = d;
        } else {
            c = getMaxCommonFactor(a, b);
            d = i;
            mcf = getMaxCommonFactor(c, d);
        }
        return mcf;
    }

    /**
     * 获取最小公倍数 a >= b
     * @return int
     */
    public static int getLeastCommonMultiple(int a, int b) {
        int mcf = getMaxCommonFactor(a, b);
        a /= mcf;
        b /= mcf;
        int lcm = a * b * mcf;
        return lcm;
    }

    /**
     * 获取最小公倍数 a >= b >= c
     * @return int
     */
    public static int getLeastCommonMultiple(int a, int b, int c) {
        int mcf = getMaxCommonFactor(a, b, c);
        int lcm = 1;
        a /= mcf;
        b /= mcf;
        c /= mcf;
        int m = getMaxCommonFactor(a, b);
        int k = getMaxCommonFactor(a, c);
        int l = getMaxCommonFactor(b, c);
        if (m != 1) {
            a /= m;
            b /= m;
            lcm = a * b * c * m * mcf;
        } else if (k != 1) {
            a /= k;
            c /= k;
            lcm = a * b * c * k * mcf;
        } else if (l != 1) {
            b /= l;
            c /= l;
            lcm = a * b * c * l * mcf;
        } else if (m == 1 && k == 1 && l == 1) {
            lcm = a * b * c * mcf;
        }
        return lcm;
    }

    /**
     * 是否可简化（约分）,0-可约分,1-不可约分
     */
    public static int isSimplify(int a,int b) {
        int max = Math.max(a, b);
        int min = Math.min(a, b);
        int h = max % min;
        if (min == 1) {
            return 1;
        } else {
            if (h == 0) {
                return 0;
            } else {
                if (h == 1) {
                    return 1;
                } else {
                    a = min;
                    b = h;
                    h = isSimplify(a, b);
                    return h;
                }
            }
        }
    }

    /**
     * 约分
     */
    public static String simplifiedValue(String fraction) {
        if (StringUtils.isEmpty(fraction) || fraction.indexOf('/') == -1) {
            return "";
        }
        String[] split = fraction.split("/");
        Integer fm = Integer.valueOf(split[1]);
        Integer fz = Integer.valueOf(split[0]);
        int mcf = getMaxCommonFactor(fm, fz);
        if (fm == mcf) {
            return String.valueOf(fz/mcf);
        } else {
            return fz/mcf+"/"+fm/mcf;
        }
    }

    /**
     * 小数去零
     */
    public static String removeZero (String decimal) {
        if ("".equals(decimal) || decimal == null) {
            return "";
        }
        String[] split = decimal.split("[.]");
        if (split != null && split.length == 2) {
            Integer integer = Integer.valueOf(split[1]);
            if (integer == 0) {
                return split[0];
            } else {
                if (integer % 10 == 0) {
                    return removeZero(split[0]+"."+integer/10);
                } else {
                    return split[0]+"."+split[1];
                }
            }
        } else {
            return decimal;
        }
    }

    /**
     * 判断小数部分是否有进位
     * true--进位/退位，false--不进位/不退位
     * 算法：1-加、2-减、3-乘、4-除
     */
    public static boolean isCarryOrAbdicate (String a, String b, int tag) {
        if ("".equals(a) || a == null || "".equals(b) || b == null) return false;
        String max = String.valueOf(Math.max(Double.valueOf(a), Double.valueOf(b)));
        String min = String.valueOf(Math.min(Double.valueOf(a), Double.valueOf(b)));
        String[] split = max.split("[.]");
        String[] split2 = min.split("[.]");
        if (split.length > 2 || split2.length < 1) return false;
        if(integerCarry(split[0],split2[0],tag)){
            return true;
        }
        if (split.length == 2 && split2.length == 2) {
            if(decimalCarry(split[1],split2[1],tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断整数部分是否有进位
     * true--进位，false--不进位
     */
    private static boolean integerCarry(String max, String min, int tag) {
        int bj = Integer.valueOf(max);
        int bj2 = Integer.valueOf(min);
        int val = 0;
        int val2 = 0;
        for (int i = 0; i < max.length(); i++) {
            if (i != 0) {
                bj = bj / 10;
                bj2 = bj2 / 10;
            }
            val = bj % 10;
            val2 = bj2 % 10;
            if (tag == 1) {
                if (val + val2 >= 10){
                    return true;
                }
            } else {
                if (val - val2 < 0){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断小数部分是否有进位
     * true--进位，false--不进位
     */
    private static boolean decimalCarry(String max, String min, int tag) {
        for (int i = 0; i < max.length(); i++) {
            Integer val = Integer.valueOf(max.substring(i,i+1));
            if (i+1 <= min.length()) {
                Integer val2 = Integer.valueOf(min.substring(i,i+1));
                if (tag == 1) {
                    if (val + val2 >= 10) {
                        return true;
                    }
                } else {
                    if (val - val2 < 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
