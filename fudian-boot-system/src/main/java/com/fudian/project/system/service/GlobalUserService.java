package com.fudian.project.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.fudian.project.system.pojo.GlobalUser;

import java.util.List;

public interface GlobalUserService extends IService<GlobalUser> {


    int updateBatch(List<GlobalUser> list);

    int batchInsert(List<GlobalUser> list);

    int insertOrUpdate(GlobalUser record);

    int insertOrUpdateSelective(GlobalUser record);

}
