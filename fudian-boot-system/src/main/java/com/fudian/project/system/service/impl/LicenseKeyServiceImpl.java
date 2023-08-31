package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.common.utils.data.DataStoreWrapper;
import com.fudian.project.system.mapper.LicenseKeyMapper;
import com.fudian.project.system.pojo.LicenseKey;
import com.fudian.project.system.service.LicenseKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Author: WangYuZhuo
* @Date: 2020/7/20 18:21
* @Desc:
*/
@Service
@Slf4j
public class LicenseKeyServiceImpl extends ServiceImpl<LicenseKeyMapper, LicenseKey> implements LicenseKeyService {

    public static final String LICENSE_KEY = "licenses";

    @Autowired
    private DataStoreWrapper cache;

    @Override
    public List<LicenseKey> getLicenses() {
        List<LicenseKey> licenseKeys = cache.getCacheList(LICENSE_KEY);
        if(licenseKeys == null || licenseKeys.size() == 0){
            log.info("========get license from amsDB====");
            licenseKeys = this.list();
            cache.setCacheList(LICENSE_KEY,licenseKeys);
        }
        return licenseKeys;
    }

    @Override
    public void flushRedis() {
        cache.deleteObject(LICENSE_KEY);
        List<LicenseKey> licenseKeys = this.list();
        if(licenseKeys.size() == 0){
            return;
        }
        cache.setCacheList(LICENSE_KEY,licenseKeys);
    }
}
