package com.fudian.project.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.api.system.pojo.SysLogininfo;

import java.util.List;

public interface SysLogininfoService extends IService<SysLogininfo>{


    int updateBatch(List<SysLogininfo> list);

    int batchInsert(List<SysLogininfo> list);

    int insertOrUpdate(SysLogininfo record);

    int insertOrUpdateSelective(SysLogininfo record);

    /**
     * 功能描述：根据条件获取所有的登录日志
     * @author lutao
     * @param
     * @return :
     * @date    2020/3/5
    */
    List<SysLogininfo> queryAllLog(SysLogininfo sysLogininfo);

}
