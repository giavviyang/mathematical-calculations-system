package com.fudian.common.threadLocal;

/**
 * 数据权限拦截器配置
 */
public class DataPermissionConfig {
    private static ThreadLocal<String>
            dataPermissionConfig = new ThreadLocal<>();//数据权限拦截

    private static ThreadLocal<Boolean>
            classIfyConfig = new ThreadLocal<Boolean>();//材料分类拦截

    public static void setType(String type){
        dataPermissionConfig.set(type);
    }
    public static String getType(){
        String returnString = dataPermissionConfig.get();
        dataPermissionConfig.remove();
        return returnString;
    }

    public static void isClassIfy(Boolean b){
        classIfyConfig.set(b);
    }
    public static Boolean getIsClassIfy(){
        Boolean b = classIfyConfig.get();
        classIfyConfig.remove();
        return b==null?false:b;
    }

}
