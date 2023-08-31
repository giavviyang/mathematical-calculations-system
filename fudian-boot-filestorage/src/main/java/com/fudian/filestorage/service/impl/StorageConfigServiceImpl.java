package com.fudian.filestorage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.utils.StringUtils;
import com.fudian.filestorage.entity.StorageConfig;
import com.fudian.filestorage.enums.FileStorageType;
import com.fudian.filestorage.factory.FileStorageFactory;
import com.fudian.filestorage.mapper.StorageConfigMapper;
import com.fudian.filestorage.service.FileStorageService;
import com.fudian.filestorage.service.StorageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StorageConfigServiceImpl extends ServiceImpl<StorageConfigMapper, StorageConfig> implements StorageConfigService {

    @Autowired
    private StorageConfigMapper storageConfigMapper;

    /**
     * 启用配置
     * @param id
     * @return
     */
    @Override
    @Transactional
    public void active(String id) {

        //查询所有已启用的配置
        LambdaQueryWrapper<StorageConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StorageConfig::getActive,"1");
        List<StorageConfig> configList = storageConfigMapper.selectList(queryWrapper);

        //理论上只有一个已启用的配置  以防万一还是全部遍历进行修改
        if (configList != null && configList.size() > 0) {
            for (StorageConfig config : configList) {
                config.setActive("0");
                storageConfigMapper.updateById(config);
            }
        }

        StorageConfig storageConfig = new StorageConfig();
        storageConfig.setId(id);
        storageConfig.setActive("1");
        storageConfigMapper.updateById(storageConfig);

        //查询所有已启用的配置
        StorageConfig config = storageConfigMapper.selectById(id);
        String type = config.getType();

        //测试连接并刷新配置
        boolean flag = false;
        if ("0".equals(type)) {
            FileStorageService fileStorage = FileStorageFactory.getFileStorage(FileStorageType.FTP);
            boolean testFlag = fileStorage.testIsConnect(config.getIp(), config.getPort().intValue(), config.getUsername(), config.getPassword());
            if (!testFlag) {
                throw new RuntimeException("连接不可用");
            }
            flag = fileStorage.flushConfig();
        }
        if ("1".equals(type)) {
            FileStorageService fileStorage = FileStorageFactory.getFileStorage(FileStorageType.MongoDB);
            boolean testFlag = fileStorage.testIsConnect(config.getIp(), config.getPort().intValue(), config.getUsername(), config.getPassword());
            if (!testFlag) {
                throw new RuntimeException("连接不可用");
            }
            flag = fileStorage.flushConfig();
        }
        if ("2".equals(type)) {
            FileStorageService fileStorage = FileStorageFactory.getFileStorage(FileStorageType.FastDFS);
            boolean testFlag = fileStorage.testIsConnect(config.getIp(), config.getPort().intValue(), config.getUsername(), config.getPassword());
            if (!testFlag) {
                throw new RuntimeException("连接不可用");
            }
            flag = fileStorage.flushConfig();
        }
        if ("3".equals(type)) {
            FileStorageService fileStorage = FileStorageFactory.getFileStorage(FileStorageType.LOCAL);
            boolean testFlag = fileStorage.testIsConnect("", 0, "", "");
            if (!testFlag) {
                throw new RuntimeException("连接不可用");
            }
            flag = fileStorage.flushConfig();
        }

        if (!flag) {
            throw new RuntimeException("启用连接失败");
        }
    }

    /**
     * 根据条件查询
     * @param storageConfig
     * @return
     */
    @Override
    public List<StorageConfig> select(StorageConfig storageConfig) {

        LambdaQueryWrapper<StorageConfig> queryWrapper = new LambdaQueryWrapper<>();
        //配置名称
        if (StringUtils.isNotBlank(storageConfig.getName())) {
            queryWrapper.like(StorageConfig::getName,storageConfig.getName());
        }
        //ip地址
        if (StringUtils.isNotBlank(storageConfig.getIp())) {
            queryWrapper.eq(StorageConfig::getIp,storageConfig.getIp());
        }
        //端口
        if (storageConfig.getPort() != null) {
            queryWrapper.eq(StorageConfig::getPort,storageConfig.getPort().intValue());
        }
        //容器类型
        if (StringUtils.isNotBlank(storageConfig.getType())) {
            queryWrapper.eq(StorageConfig::getType,storageConfig.getType());
        }
        //启用状态
        if (StringUtils.isNotBlank(storageConfig.getActive())) {
            queryWrapper.eq(StorageConfig::getActive,storageConfig.getActive());
        }
        //启用状态
        if (storageConfig.getCreateTime() != null) {
            queryWrapper.eq(StorageConfig::getCreateTime,storageConfig.getCreateTime());
        }
        return storageConfigMapper.selectList(queryWrapper);
    }

    @Override
    public boolean test(StorageConfig config) {
        boolean flag = false;
        String type = config.getType();
        if ("0".equals(type)) {
            FileStorageService fileStorage = FileStorageFactory.getFileStorage(FileStorageType.FTP);
            boolean testFlag = fileStorage.testIsConnect(config.getIp(), config.getPort().intValue(), config.getUsername(), config.getPassword());
            if (!testFlag) {
                throw new RuntimeException("连接不可用");
            }
            flag = fileStorage.flushConfig();
        }
        if ("1".equals(type)) {
            FileStorageService fileStorage = FileStorageFactory.getFileStorage(FileStorageType.MongoDB);
            boolean testFlag = fileStorage.testIsConnect(config.getIp(), config.getPort().intValue(), config.getUsername(), config.getPassword());
            if (!testFlag) {
                throw new RuntimeException("连接不可用");
            }
            flag = fileStorage.flushConfig();
        }
        if ("2".equals(type)) {
            FileStorageService fileStorage = FileStorageFactory.getFileStorage(FileStorageType.FastDFS);
            boolean testFlag = fileStorage.testIsConnect(config.getIp(), config.getPort().intValue(), config.getUsername(), config.getPassword());
            if (!testFlag) {
                throw new RuntimeException("连接不可用");
            }
            flag = fileStorage.flushConfig();
        }
        if ("3".equals(type)) {
            FileStorageService fileStorage = FileStorageFactory.getFileStorage(FileStorageType.LOCAL);
            boolean testFlag = fileStorage.testIsConnect("", 0, "", "");
            if (!testFlag) {
                throw new RuntimeException("连接不可用");
            }
            flag = fileStorage.flushConfig();
        }
        return flag;
    }
}
