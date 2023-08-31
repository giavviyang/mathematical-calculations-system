package com.fudian.common.utils.session;

import cn.hutool.extra.spring.SpringUtil;
import com.fudian.common.enumns.RedisEnum;
import com.fudian.common.utils.ServletUtils;
import com.fudian.common.utils.data.DataStoreService;
import com.fudian.common.utils.spring.SpringUtils;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/7/2 11:40
 * @Desc: session实现数据存储，存储策略为从session存取，如果session为空就存在在webApplication里面
 */
@Component("session")
public class SessionCache implements DataStoreService {


    @Autowired
    WebApplicationContext webApplicationContext;

    @Override
    public <T> T setCacheObject(String key, T value) {
        return setCacheObject(key, value,null,null);
    }

    @Override
    public <T> T setCacheObject(String key, T value, RedisEnum redisEnum) {
        return setCacheObject(key, value,null,null);
    }

    @Override
    public <T> T setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        HttpSession httpSession = ServletUtils.getSession();
        //session为空则去application中存
        if(httpSession != null){
            httpSession.setAttribute(key,value);
//            webApplicationContext.getServletContext().setAttribute(key,value);
        }
        return value;
    }

    @Override
    public <T> T getCacheObject(String key) {
        T t = null;
        HttpSession httpSession = ServletUtils.getSession();
        //session为空则去application中取
        if(httpSession != null){
            t = (T) httpSession.getAttribute(key);
//            t = (T) webApplicationContext.getServletContext().getAttribute(key);
        }
        return t;
    }

    @Override
    public void deleteObject(String key) {
        HttpSession httpSession = ServletUtils.getSession();
        //session为空则去application中删除
        if(httpSession == null){

        }else{
            httpSession.removeAttribute(key);
        }
    }

    /**
     * 不做实现
     * @param collection
     */
    @Override
    public void deleteObject(Collection collection) {

    }

    @Override
    public <T> List<T> setCacheList(String key, List<T> dataList) {
        setCacheObject(key,dataList);
        return dataList;
    }

    @Override
    public <T> List<T> getCacheList(String key) {
        return getCacheObject(key);
    }

    @Override
    public <T> Set<T> setCacheSet(String key, Set<T> dataSet) {
        setCacheObject(key,dataSet);
        return dataSet;
    }

    @Override
    public <T> Set<T> getCacheSet(String key) {
        return getCacheObject(key);
    }

    @Override
    public <T> Map<String, T> setCacheMap(String key, Map<String, T> dataMap) {
        setCacheObject(key,dataMap);
        return dataMap;
    }

    @Override
    public <T> Map<String, T> getCacheMap(String key) {
        return getCacheObject(key);
    }

    @Override
    public Collection<String> keys(String pattern) {
        return null;
    }
}
