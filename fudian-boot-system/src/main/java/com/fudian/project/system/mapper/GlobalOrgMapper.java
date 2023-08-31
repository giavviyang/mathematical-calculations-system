package com.fudian.project.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fudian.api.system.pojo.GlobalOrg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GlobalOrgMapper extends BaseMapper<GlobalOrg> {
    int updateBatch(List<GlobalOrg> list);

    int batchInsert(@Param("list") List<GlobalOrg> list);

    int insertOrUpdate(GlobalOrg record);

    int insertOrUpdateSelective(GlobalOrg record);
}
