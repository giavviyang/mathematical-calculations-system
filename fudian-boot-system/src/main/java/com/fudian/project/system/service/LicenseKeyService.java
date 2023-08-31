package com.fudian.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.project.system.pojo.LicenseKey;

import java.util.List;

/**
* @Author: WangYuZhuo
* @Date: 2020/7/20 18:21
* @Desc:
*/
public interface LicenseKeyService extends IService<LicenseKey>{

    public List<LicenseKey> getLicenses();

    public void flushRedis();

}
