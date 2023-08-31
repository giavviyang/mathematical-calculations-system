package com.fudian.filestorage.factory;

import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.service.FileStorageService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 文件存储工厂类
 * @ClassName FileStorageFactory
 * @Author mr.hou
 * @Date 2019/12/28 2:23 下午
 * @Version 1.0.0
 **/
@Component
public class FileStorageFactory implements ApplicationContextAware {

    private static Map<FileStorageType, FileStorageService> fileStorageBeanMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {


        Map<String, FileStorageService> map = applicationContext.getBeansOfType(FileStorageService.class);

        fileStorageBeanMap = new HashMap<>();

        map.forEach((key, value) -> fileStorageBeanMap.put(value.getType(), value));

        }

    public static <T extends FileStorageService>T getFileStorage(FileStorageType type){
        return (T)fileStorageBeanMap.get(type);
    }
}
