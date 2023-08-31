package com.fudian.common.utils;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 * @author xuchenghong
 */
public class AppletUtils {
    /**
     * 解析公式
     *
     * @param formula
     * @return
     */
    private static final Pattern pattern = Pattern.compile("[\\+|\\-|\\×|\\÷|\\=]");

    public static String analysiFormula(String formula) {
        //判断字符串中是否存在等号
        boolean contains = formula.contains("=");
        //  +  -  ×  ÷
        //拆分字符串
        String[] split = pattern.split(formula);
        //获取存在的分割字符
        Matcher matcher = pattern.matcher(formula);

        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (matcher.find()) {
            stringBuffer.append("'" + split[i] + "',")
                    .append("'" + matcher.group() + "',");
            i++;
        }
        stringBuffer.append("'" + split[i] + "',");
        if (contains) {
            stringBuffer.append("''");
        } else {
            stringBuffer.append("'='");
        }
        return stringBuffer.toString();
    }

    /**
     * 错题回显
     * @param formula
     * @return
     */
    public static String analysiErrorFormula(String formula) {
        //  +  -  ×  ÷  11 + 11 + 22 = 33
        //拆分字符串
        String[] split = pattern.split(formula);
        //获取存在的分割字符
        Matcher matcher = pattern.matcher(formula);

        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (matcher.find()) {
            stringBuffer.append(split[i]).append("#" + matcher.group() + "#");
            i++;
        }
        if (split.length-1 == i){
            stringBuffer.append(split[i]);
        }
        return stringBuffer.toString();
    }

    /**
     * 保留两位小数
     *
     * @param
     * @return
     */
    public static double retainTwoDecimal(double dou) {
        BigDecimal b = new BigDecimal(dou);
        double decimal = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String value = String.valueOf(decimal);
        int i = value.indexOf(".");
        if (i != -1){
            int length = value.substring(i + 1).length();
            value = value.substring(0, i+(length>=2?3:length+1));
            decimal = Double.valueOf(value);
        }
        return decimal;
    }

}
