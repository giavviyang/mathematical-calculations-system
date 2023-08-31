package com.fudian.project.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.project.system.pojo.GlobalUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GlobalUserMapper extends BaseMapper<GlobalUser> {
    int updateBatch(List<GlobalUser> list);

    int batchInsert(@Param("list") List<GlobalUser> list);

    int insertOrUpdate(GlobalUser record);

    int insertOrUpdateSelective(GlobalUser record);
}
