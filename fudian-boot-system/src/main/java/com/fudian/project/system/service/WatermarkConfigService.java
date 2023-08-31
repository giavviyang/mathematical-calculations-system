package com.fudian.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.WatermarkConfig;

import java.util.List;

public interface WatermarkConfigService extends IService<WatermarkConfig>{


    List<WatermarkConfig> select(WatermarkConfig config);

    boolean active(String id);

    boolean disable(String id);
}
