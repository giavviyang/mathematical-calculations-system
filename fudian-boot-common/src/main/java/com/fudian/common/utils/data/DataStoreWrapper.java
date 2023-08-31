package com.fudian.common.utils.data;

import com.fudian.common.enumns.RedisEnum;
import com.fudian.common.utils.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: WangYuZhuo
 * @Date: 2020/7/2 14:15
 * @Desc:
 */
@Component
public class DataStoreWrapper implements DataStoreService{

    @Value("${fudian.data.store.name:session}")
    String dataStoreName;

    @PostConstruct
    public void setDataStoreService(){
        dataStoreService = SpringUtils.getBean(dataStoreName);
    }

    DataStoreService dataStoreService;

    @Override
    public <T> T setCacheObject(String key, T value) {
        return dataStoreService.setCacheObject(key, value);
    }

    @Override
    public <T> T setCacheObject(String key, T value, RedisEnum redisEnum) {
        return dataStoreService.setCacheObject(key, value, redisEnum);
    }

    @Override
    public <T> T setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        return dataStoreService.setCacheObject(key, value, timeout, timeUnit);
    }

    @Override
    public <T> T getCacheObject(String key) {
        return dataStoreService.getCacheObject(key);
    }

    @Override
    public void deleteObject(String key) {
        dataStoreService.deleteObject(key);
    }

    @Override
    public void deleteObject(Collection collection) {
        dataStoreService.deleteObject(collection);
    }

    @Override
    public <T> List<T> setCacheList(String key, List<T> dataList) {
        return dataStoreService.setCacheList(key, dataList);
    }

    @Override
    public <T> List<T> getCacheList(String key) {
        return dataStoreService.getCacheList(key);
    }

    @Override
    public <T> Set<T> setCacheSet(String key, Set<T> dataSet) {
        return dataStoreService.setCacheSet(key, dataSet);
    }

    @Override
    public <T> Set<T> getCacheSet(String key) {
        return dataStoreService.getCacheSet(key);
    }

    @Override
    public <T> Map<String, T> setCacheMap(String key, Map<String, T> dataMap) {
        return dataStoreService.setCacheMap(key, dataMap);
    }

    @Override
    public <T> Map<String, T> getCacheMap(String key) {
        return dataStoreService.getCacheMap(key);
    }

    @Override
    public Collection<String> keys(String pattern) {
        return dataStoreService.keys(pattern);
    }
}
