package com.fudian.filestorage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.filestorage.entity.StorageConfig;

import java.util.List;

public interface StorageConfigService extends IService<StorageConfig>{

    void active(String id);

    List<StorageConfig> select(StorageConfig storageConfig);

    boolean test(StorageConfig storageConfig);
}
