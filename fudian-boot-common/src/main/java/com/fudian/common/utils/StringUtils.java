package com.fudian.common.utils;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

/**
 * 功能描述:
 * <字符串工具类>
 *
 * @since: 1.0.0
 * @Author Mr.Hou
 * @Date 2019/11/20 3:28 下午
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';


    public static String ClobToString(Clob clob)  {

        String reString = "";
        if(clob == null){
            return reString;
        }
        Reader is = null;// 得到流
        try {
            is = clob.getCharacterStream();
            BufferedReader br = new BufferedReader(is);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();
            while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
                sb.append(s);
                s = br.readLine();
            }
            reString = sb.toString();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return reString;
        }



    }

    /**
     * 获取参数不为空值
     *
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {

        return value != null ? value : defaultValue;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     *                * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return ObjectUtil.isNull(object);
    }

    /**
     * * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {

        if (str == null) {
            return NULLSTR;
        }
        return StrUtil.sub(str,start,str.length());
//
//        if (start < 0) {
//            start = str.length() + start;
//        }
//
//        if (start < 0) {
//            start = 0;
//        }
//        if (start > str.length()) {
//            return NULLSTR;
//        }
//
//        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }
//
//        if (end < 0) {
//            end = str.length() + end;
//        }
//        if (start < 0) {
//            start = str.length() + start;
//        }
//
//        if (end > str.length()) {
//            end = str.length();
//        }
//
//        if (start > end) {
//            return NULLSTR;
//        }
//
//        if (start < 0) {
//            start = 0;
//        }
//        if (end < 0) {
//            end = 0;
//        }
//
//        return str.substring(start, end);
        return StrUtil.sub(str,start,end);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * 字符串转set
     *
     * @param str 字符串
     * @param sep 分隔符
     * @return set集合
     */
    public static final Set<String> str2Set(String str, String sep) {
        return new HashSet<String>(str2List(str, sep, true, false));
    }

    /**
     * 字符串转list
     *
     * @param str         字符串
     * @param sep         分隔符
     * @param filterBlank 过滤纯空白
     * @param trim        去掉首尾空白
     * @return list集合
     */
    public static final List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
//        CollUtil.newArrayList();
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(str)) {
            return list;
        }

        // 过滤空白字符串
        if (filterBlank && StringUtils.isBlank(str)) {
            return list;
        }
        String[] split = null;
        if(trim){
            StrUtil.splitTrim(str,sep);
        }else{
            StrUtil.split(str,sep);
        }


//        for (String string : split) {
//            if (filterBlank && StringUtils.isBlank(string)) {
//                continue;
//            }
//            if (trim) {
//                string = string.trim();
//            }
//            list.add(string);
//        }
        list = CollUtil.newArrayList(split);
        return list;
    }

    /**
     * 下划线转驼峰命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        return StrUtil.toCamelCase(str);
//        StringBuilder sb = new StringBuilder();
//        // 前置字符是否大写
//        boolean preCharIsUpperCase = true;
//        // 当前字符是否大写
//        boolean curreCharIsUpperCase = true;
//        // 下一字符是否大写
//        boolean nexteCharIsUpperCase = true;
//        for (int i = 0; i < str.length(); i++) {
//            char c = str.charAt(i);
//            if (i > 0) {
//                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
//            } else {
//                preCharIsUpperCase = false;
//            }
//
//            curreCharIsUpperCase = Character.isUpperCase(c);
//
//            if (i < (str.length() - 1)) {
//                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
//            }
//
//            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
//                sb.append(SEPARATOR);
//            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
//                sb.append(SEPARATOR);
//            }
//            sb.append(Character.toLowerCase(c));
//        }
//
//        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
//        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        }
        String value = toUnderScoreCase(name);
        //将首字母转换为大写
        value = value.substring(0, 1).toUpperCase() + value.substring(1);
//        else if (!name.contains("_")) {
//            // 不含下划线，仅将首字母大写
//            return name.substring(0, 1).toUpperCase() + name.substring(1);
//        }
//        // 用下划线将原始字符串分割
//        String[] camels = name.split("_");
//        for (String camel : camels) {
//            // 跳过原始字符串中开头、结尾的下换线或双重下划线
//            if (camel.isEmpty()) {
//                continue;
//            }
//            // 首字母大写
//            result.append(camel.substring(0, 1).toUpperCase());
//            result.append(camel.substring(1).toLowerCase());
//        }
//        return result.toString();
        return value;
    }

    /**
     * 驼峰式命名法 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
       return toUnderScoreCase(s);
    }

    /**
     * 将目标字符串首字母变为大写
     * @param str 目标字符串
     * @return 首字母变为大写的字符串
     */
    public static String firstChar2UpperCase(String str){

        return StrUtil.upperFirst(str);
    }

    public static String uuid() {
        return IdUtil.simpleUUID();
    }
}
