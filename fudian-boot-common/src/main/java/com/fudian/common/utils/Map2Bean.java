package com.fudian.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Map2Bean {
    /**
     * map转换成javaBean
     * @param map
     * @return
     */
    public static Object transMap2Bean(Map<String,Object> map, Object obj){
        try{
            //1.获取bean信息
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor prop: properties) {
                String key = prop.getName();
                if(map.containsKey(key) && map.get(key) != null){
                    Object value = map.get(key);
                    Method setMethod = prop.getWriteMethod();
                    setMethod.invoke(obj,value);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return obj;
    }
    public static Map<String,Object> bean2map(Object bean) throws Exception {

        Map<String,Object> map=new HashMap<>();
        BeanInfo info=Introspector.getBeanInfo(bean.getClass(),Object.class);
        PropertyDescriptor []pds=info.getPropertyDescriptors();
        for(PropertyDescriptor pd:pds) {
            String key=pd.getName();
            Object value= pd.getReadMethod().invoke(bean);
            map.put(key, value);
        }
        return map;
    }

}
