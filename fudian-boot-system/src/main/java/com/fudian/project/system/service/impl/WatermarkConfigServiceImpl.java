package com.fudian.project.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fudian.api.system.pojo.WatermarkConfig;
import com.fudian.api.system.service.WatermarkConfigApi;
import com.fudian.common.utils.StringUtils;
import com.fudian.project.system.mapper.WatermarkConfigMapper;
import com.fudian.project.system.service.WatermarkConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WatermarkConfigServiceImpl extends ServiceImpl<WatermarkConfigMapper, WatermarkConfig> implements WatermarkConfigService, WatermarkConfigApi {

    @Autowired
    private WatermarkConfigMapper watermarkConfigMapper;

    @Override
    public List<WatermarkConfig> select(WatermarkConfig config) {
        LambdaQueryWrapper<WatermarkConfig> queryWrapper = new LambdaQueryWrapper<>();
        //名称
        if (StringUtils.isNotBlank(config.getName())) {
            queryWrapper.like(WatermarkConfig::getName,config.getName());
        }
        //类型
        if (config.getType() != null) {
            queryWrapper.eq(WatermarkConfig::getType,config.getType());
        }
        //是否激活
        if (config.getActive() != null) {
            queryWrapper.eq(WatermarkConfig::getActive,config.getActive());
        }
        return watermarkConfigMapper.selectList(queryWrapper);
    }
    /**
     * 启用配置
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean active(String id) {

        //查询所有已启用的配置
        LambdaQueryWrapper<WatermarkConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WatermarkConfig::getActive,"1");
        List<WatermarkConfig> configList = watermarkConfigMapper.selectList(queryWrapper);

        //理论上只有一个已启用的配置  以防万一还是全部遍历进行修改
        if (configList != null && configList.size() > 0) {
            for (WatermarkConfig config : configList) {
                config.setActive("0");
                watermarkConfigMapper.updateById(config);
            }
        }

        WatermarkConfig watermarkConfig = watermarkConfigMapper.selectById(id);
        watermarkConfig.setActive("1");
        watermarkConfigMapper.updateById(watermarkConfig);

        return true;
    }

    @Override
    public boolean disable(String id) {
        WatermarkConfig watermarkConfig = watermarkConfigMapper.selectById(id);
        watermarkConfig.setActive("0");
        watermarkConfigMapper.updateById(watermarkConfig);
        return true;
    }
}
